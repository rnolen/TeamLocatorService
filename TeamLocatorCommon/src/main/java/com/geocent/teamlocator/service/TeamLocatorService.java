package com.geocent.teamlocator.service;

import java.util.List;

import javax.ejb.Remote;

import com.geocent.teamlocator.dto.LocationDto;
import com.geocent.teamlocator.dto.MemberDto;
import com.geocent.teamlocator.dto.MissionDto;
import com.geocent.teamlocator.dto.TeamDto;
import com.geocent.teamlocator.exception.EntityNotFoundException;
import com.geocent.teamlocator.exception.InvalidMissionException;

@Remote
public interface TeamLocatorService
{
    /**
     * Adds the input Mission to the database along with included Objective
     * @param mission
     * @return updated MissionDto
     * @throws InvalidMissionException if the Mission has no Objective
     */
    public MissionDto addMission( MissionDto mission ) throws InvalidMissionException;
    
    /**
     * Retrieve Mission by description. It should get one Mission, but we don't have a unique constraint on the 
     * description so there could be more than one.
     * 
     * @param desc
     * @return List of Mission object(s) found with the description.
     */
    public List<MissionDto> getMissionByDescription( String desc );

    /**
     * Returns all Missions that the Team has been assigned to
     * @param team
     * @return List of MissionDto objects
     * @throws EntityNotFoundException if the Team is not found
     */
    public List<MissionDto> getMissionsForTeam( TeamDto team ) throws EntityNotFoundException;

    /**
     * Adds the passed Team to the database and adds it to the input Mission
     * @param team
     * @param mission
     * @return Updated TeamDto
     * @throws EntityNotFoundException if the Mission included in the Team is not found
     */
    public TeamDto addTeam( TeamDto team, MissionDto mission ) throws EntityNotFoundException;
    
    /**
     * Retrieve Team(s) based on the input name. We only really expect one team per name, but there's
     * no unique constraint on the name so we're prepared to get more than one
     * @param name
     * @return List of TeamDto objects
     */
    public List<TeamDto> getTeamByName( String name );
    
    /**
     * Removes the passed Team from its Mission
     * @param team to remove
     * @param mission to update
     * @return 
     * @throws EntityNotFoundException if the passed mission is not found
     * @throws InvliadMissionException if the input mission is completed
     */
    public TeamDto removeTeam( TeamDto team, MissionDto mission ) throws EntityNotFoundException, InvalidMissionException;

    /**
     * Adds the input Memeber to the database and assigns it to the input Team
     * @param member
     * @param team
     * @return
     * @throws EntityNotFoundException if the input Team is not found
     */
    public MemberDto addMember( MemberDto member, TeamDto team ) throws EntityNotFoundException;

    /**
     * Get the Member(s) matching the input criteria
     * @param lastName
     * @param middleName
     * @param firstName
     * @return
     */
    public List<MemberDto> getMembers( String lastName, String middleName, String firstName );
    
    /**
     * Reassigns the input Member from their existing team to the input Team.
     * @param member
     * @param team
     * @return
     * @throws EntityNotFoundException if either the input Member or Team is not found
     */
    public MemberDto reassignMember( MemberDto member, TeamDto team ) throws EntityNotFoundException;
    
    /**
     * Gets the current mission for the input Member
     * @param member
     * @return current Mission
     * @throws EntityNotFoundException if the input Member or associated Team is not found
     */
    public List<MissionDto> getCurrentMission( MemberDto member) throws EntityNotFoundException;

    /**
     * Adds the input Location to the database. The Location object contains all necessary information, including
     * the member, team, and mission info. If any of those are not found or not valid, an exception will be thrown.
     * @param memberId
     * @param location
     * @return LocationDto
     * @throws EntityNotFoundException if a Member, Team, or Mission is not found for the input Location
     */
    public LocationDto addMemberLocation( LocationDto location ) throws EntityNotFoundException;
    

    /**
     * Returns a List of Locations for members of the input Team that are within the input range. The list will
     * exclude the passed member and return the last location for all other members of the team based on the current mission.
     * @param member - the member making the request
     * @param maxRange - in meters
     * @return
     * @throws EntityNotFoundException if the Team is not found
     */
    public List<LocationDto> getLastLocationForTeam( MemberDto member, int maxRange ) throws EntityNotFoundException;

    /**
     * Get all members of the passed team
     * @param team
     * @return List of MemberDto objects
     */
    public List<MemberDto> getMembersOfTeam( TeamDto team );
    
}
