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
     */
    public MissionDto addMission( MissionDto mission );

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
     * Removes the input Member from the input Team, but does NOT remove the member from the database.
     * @param member
     * @param team
     * @return
     * @throws EntityNotFoundException if either the input Member or Team is not found
     */
    public TeamDto removeMember( MemberDto member, TeamDto team ) throws EntityNotFoundException;
    
    /**
     * Gets the current mission for the input Member
     * @param member
     * @return current Mission
     * @throws EntityNotFoundException if the input Member is not found
     */
    public MissionDto getCurrentMission( MemberDto member) throws EntityNotFoundException;

    /**
     * Adds the input Location to the database for the passed member Id
     * @param memberId
     * @param location
     * @throws EntityNotFoundException if a Member is not found for the input Id
     */
    public void addMemberLocation( Integer memberId, LocationDto location ) throws EntityNotFoundException;
    

    /**
     * Returns a List of Locations for members of the input Team that are within the input range
     * @param team
     * @param maxRange
     * @return
     * @throws EntityNotFoundException if the Team is not found
     */
    public List<LocationDto> getLastLocationForTeam( TeamDto team, int maxRange ) throws EntityNotFoundException;
    
}
