package com.geocent.teamlocator.service;

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
    
    @EJB 
    protected MissionEao missionEao; 
    @EJB
    protected TeamEao teamEao;
    @EJB
    protected MemberEao memberEao;

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
        missionEao.removeTeamFromMission( mission, team );
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
     * @see TeamLocatorService#addMemberLocation(Integer, LocationDto)
     */
    public void addMemberLocation(Integer memberId, LocationDto location) {
        // TODO Auto-generated method stub
    }

	/**
     * @see TeamLocatorService#removeMember(MemberDto, TeamDto)
     */
    public TeamDto removeMember(MemberDto member, TeamDto team) {
        // TODO Auto-generated method stub
			return null;
    }

	/**
     * @see TeamLocatorService#addMember(MemberDto, TeamDto)
     */
    public MemberDto addMember(MemberDto member, TeamDto team) throws EntityNotFoundException {
        // Set the team into the MemberDto object before passing on to the Eao
        member.setTeam( team );
        MemberDto updatedMember = memberEao.addMember( member );
		return updatedMember;
    }

	/**
     * @see TeamLocatorService#getLastLocationForTeam(TeamDto, int)
     */
    public List<LocationDto> getLastLocationForTeam(TeamDto team, int maxRange) {
        List<LocationDto> list = new ArrayList<LocationDto>();
        return list;
    }

    @Override
    public List<MissionDto> getMissionByDescription( String desc ) {
        System.out.println( "---->DEBUG: TeamLocatorServiceBean.getMissionByDescription" );
        return missionEao.getMissionByDescription( desc );
    }

	/**
     * @see TeamLocatorService#getCurrentMission(MemberDto)
     */
    public MissionDto getCurrentMission(MemberDto member) {
        // TODO Auto-generated method stub
			return null;
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

}
