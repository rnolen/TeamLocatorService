package com.geocent.teamlocator.service;

import com.geocent.teamdb.eao.MissionEao;
import com.geocent.teamdb.eao.TeamEao;
import com.geocent.teamlocator.dto.LocationDto;
import com.geocent.teamlocator.dto.MemberDto;
import com.geocent.teamlocator.dto.MissionDto;
import com.geocent.teamlocator.dto.TeamDto;
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

    /**
     * Default constructor. 
     */
    public TeamLocatorServiceBean() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see TeamLocatorService#removeTeam(TeamDto, MissionDto)
     */
    public TeamDto removeTeam(TeamDto team, MissionDto mission) {
        // TODO Auto-generated method stub
			return null;
    }

	/**
     * @see TeamLocatorService#getCurrentMission(MemberDto)
     */
    public MissionDto getCurrentMission(MemberDto member) {
        // TODO Auto-generated method stub
			return null;
    }

	/**
     * @see TeamLocatorService#addTeam(TeamDto, MissionDto)
     */
    public TeamDto addTeam(TeamDto team, MissionDto mission) {
        // TODO Auto-generated method stub
			return null;
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
    public MemberDto addMember(MemberDto member, TeamDto team) {
        // TODO Auto-generated method stub
			return null;
    }

	/**
     * @see TeamLocatorService#getLastLocationForTeam(TeamDto, int)
     */
    public List<LocationDto> getLastLocationForTeam(TeamDto team, int maxRange) {
        List<LocationDto> list = new ArrayList<LocationDto>();
        return list;
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
