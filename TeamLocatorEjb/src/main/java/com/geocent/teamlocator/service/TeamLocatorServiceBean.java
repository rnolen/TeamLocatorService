package com.geocent.teamlocator.service;

import com.geocent.teamdb.eao.LocationEao;
import com.geocent.teamdb.eao.MemberEao;
import com.geocent.teamdb.eao.MissionEao;
import com.geocent.teamdb.eao.TeamEao;
import com.geocent.teamlocator.dto.LocationDto;
import com.geocent.teamlocator.dto.MemberDto;
import com.geocent.teamlocator.dto.MissionDto;
import com.geocent.teamlocator.dto.TeamDto;
import com.geocent.teamlocator.exception.EntityNotFoundException;
import com.geocent.teamlocator.exception.InvalidMissionException;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.jws.WebService;

/**
 * Session Bean implementation class TeamLocatorServiceBean
 */
@Stateless
@LocalBean
@WebService
public class TeamLocatorServiceBean implements TeamLocatorService {

    public static final double UNKNOWN_LOC = -1.0;
    @EJB protected MissionEao missionEao; 
    @EJB protected TeamEao teamEao;
    @EJB protected MemberEao memberEao;
    @EJB protected LocationEao locationEao;

    /**
     * Default constructor. 
     */
    public TeamLocatorServiceBean() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see TeamLocatorService#removeTeam(TeamDto, MissionDto)
     */
    public TeamDto removeTeam(TeamDto team, MissionDto mission) throws EntityNotFoundException, InvalidMissionException{
        teamEao.removeTeamFromMission( mission, team );
        List<MissionDto> missions = getMissionsForTeam( team );
        team.setMissions( missions );
        return team;
    }

	/**
     * @see TeamLocatorService#addTeam(TeamDto, MissionDto)
     */
    public TeamDto addTeam(TeamDto team, MissionDto mission) throws EntityNotFoundException {
        // Add the mission to the team before calling into the Eao
        team.getMissions().add( mission );
        TeamDto result = teamEao.addTeam( team );
        return result;
    }

	/**
     * @see TeamLocatorService#getTeamByName(String)
     */
    public List<TeamDto> getTeamByName( String name ) {
        System.out.println( "---->TRACE: TeamLocatorService.getTeamByName" );
        List<TeamDto> result = teamEao.getTeamByName( name );
        return result;
    }

	/**
     * @return 
	 * @see TeamLocatorService#addMemberLocation(LocationDto)
     */
    public LocationDto addMemberLocation( LocationDto location ) {
        System.out.println( "---->TRACE: TeamLocatorService.addMemberLocation" );
        return locationEao.addLocation( location );
    }

	/**
     * @throws EntityNotFoundException 
	 * @see TeamLocatorService#reassignMember(MemberDto, TeamDto)
     */
    public MemberDto reassignMember(MemberDto member, TeamDto team) throws EntityNotFoundException {
        // Set the team into the MemberDto object before passing on to the Eao
        member.setTeam( team );
        MemberDto updatedMember = memberEao.saveMember( member );
        return updatedMember;
    }

	/**
     * @see TeamLocatorService#addMember(MemberDto, TeamDto)
     */
    public MemberDto addMember(MemberDto member, TeamDto team) throws EntityNotFoundException {
        // Set the team into the MemberDto object before passing on to the Eao
        member.setTeam( team );
        MemberDto updatedMember = memberEao.saveMember( member );
		return updatedMember;
    }

    @Override
    public List<MemberDto> getMembers( String lastName, String middleName, String firstName ) {
        return memberEao.getMembers( lastName, middleName, firstName );
    }

    /**
     * @see TeamLocatorService#getLastLocationForTeam(Integer, int)
     */
    public List<LocationDto> getLastLocationForTeam(Integer memberId, int maxRange) throws EntityNotFoundException {
        MemberDto member = memberEao.getMember( memberId );
        
        // After retrieving the Member, use the existing operation to get the location list
        return getLastLocationForTeam( member, maxRange );
    }
    
	/**
     * @see TeamLocatorService#getLastLocationForTeam(MemberDto, int)
     */
    public List<LocationDto> getLastLocationForTeam(MemberDto member, int maxRange) {
        List<LocationDto> workList = new ArrayList<LocationDto>();
        MissionDto curMission = null;
        List<MissionDto> missions = this.getCurrentMission( member );
        if( missions == null || missions.isEmpty() ) {
            Logger.getLogger( TeamLocatorServiceBean.class.getName() ).severe( "No current mission found for Member Id=" + member.getId() );
            return workList;
        }
        curMission = missions.get( 0 );
        
        List<MemberDto> teamMembers = getMembersOfTeam( member.getTeam() );
        // first step is to get last location of each team member
        LocationDto memberLoc = null;
        for( MemberDto teamMember : teamMembers ) {
            LocationDto loc = locationEao.getLastLocation( teamMember, curMission );
            if( loc == null ) {
                loc = createUnknownLocation( teamMember, curMission );
            }
            // Skip the requesting member
            if( teamMember.getId().equals( member.getId() ) ) {
                memberLoc = loc;
                continue;
            }
            workList.add( loc );
        }
        
        // Now filter the list based on max range
        List<LocationDto> result = new ArrayList<LocationDto>();
        for( LocationDto loc : workList ) {
            double range = distanceBetweenPoints( memberLoc.getLattitude(), memberLoc.getLongitude(), 
                                                  loc.getLattitude(), loc.getLongitude() );
            if( range <= maxRange ) {
                loc.setRange( range );
                result.add( loc );
            }
        }
        
        return result;
    }

    @Override
    public List<MissionDto> getMissionByDescription( String desc ) {
        System.out.println( "---->DEBUG: TeamLocatorServiceBean.getMissionByDescription" );
        return missionEao.getMissionByDescription( desc );
    }

	/**
     * @see TeamLocatorService#getCurrentMission(MemberDto)
     */
    public List<MissionDto> getCurrentMission(MemberDto member) {
        System.out.println( "---->DEBUG: TeamLocatorServiceBean.getCurrentMission" );
        return missionEao.getCurrentMission( member.getTeam().getId() );
    }

    @Override
    public List<MissionDto> getMissionsForTeam( TeamDto team ) throws EntityNotFoundException {
        System.out.println( "---->DEBUG: TeamLocatorServiceBean.getMissionsForTeam" );
        return missionEao.getMissions( team );
    }

	/**
     * @see TeamLocatorService#addMission(MissionDto)
     */
    public MissionDto addMission(MissionDto mission) throws InvalidMissionException {
        MissionDto result;
        if( mission.getObjective() == null ) {
            throw new InvalidMissionException( "Mission MUST contain an Objective" );
        }
        result = missionEao.addMission( mission );
        return result;
    }

    private LocationDto createUnknownLocation( MemberDto teamMember, MissionDto curMission ) {
        // If no location was found for the the team member, then create an UNKNOWN loc object
        LocationDto loc = new LocationDto();
        loc.setLattitude( UNKNOWN_LOC );
        loc.setLongitude( UNKNOWN_LOC );
        loc.setMember( teamMember );
        loc.setMission( curMission );
        loc.setTeam( teamMember.getTeam() );
        
        return loc;
    }

    @Override
    public List<MemberDto> getMembersOfTeam( TeamDto team ) {
        List<MemberDto> teamMembers = new ArrayList<MemberDto>();
        try {
            teamMembers = memberEao.getMembers( team );
        }catch( EntityNotFoundException enfe ) {
            Logger.getLogger( TeamLocatorServiceBean.class.getName() ).severe( "No team found for Team Id=" + team.getId() );
        }
        return teamMembers;
    }

    private long distanceBetweenPoints( double lat1, double lng1, double lat2, double lng2) {
        int radius = 6371; // Radius of the earth in KM
        double latDeg = degToRad( lat2-lat1 );
        double lngDeg = degToRad( lng2-lng1 );
        double a = Math.sin( latDeg/2 ) * Math.sin( latDeg/2 ) + 
                   Math.cos( degToRad(lat1) ) * Math.cos( degToRad(lat2) ) * Math.sin( lngDeg/2 ) * Math.sin( lngDeg/2 );
        double c = 2 * Math.atan2( Math.sqrt(a), Math.sqrt(1-a) );
        double distance = radius * c;
        
        // Return the range in meters
        return Math.round( distance*1000 );
        
    }
    
    private double degToRad( double degrees ) {
        return degrees * (Math.PI / 180);
    }
}
