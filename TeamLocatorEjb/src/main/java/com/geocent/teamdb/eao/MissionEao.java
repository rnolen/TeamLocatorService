package com.geocent.teamdb.eao;

import java.util.Date;
import java.util.List;

import javax.ejb.Remote;

import com.geocent.teamdb.entity.Mission;
import com.geocent.teamdb.entity.Team;
import com.geocent.teamlocator.dto.MissionDto;
import com.geocent.teamlocator.dto.TeamDto;
import com.geocent.teamlocator.exception.EntityNotFoundException;
import com.geocent.teamlocator.exception.InvalidMissionException;

@Remote
public interface MissionEao extends Eao
{
    /**
     * Get Mission by key 
     * @param key
     * @return Mission
     */
    MissionDto getMission( int key ) throws EntityNotFoundException;
    
    /**
     * Get all missions by date
     * @param missionDate
     * @return List of Missions
     */
    List<MissionDto> getMissions( Date missionDate );
    
    /**
     * Get all missions that the passed team Id participated in
     * @param teamId
     * @return List of missions
     */
    List<MissionDto> getMissions( int teamId );
    
    /**
     * Retrieves the latest Mission for the input team Id. The determination is made using the completed flag. If
     * there is no mission for the team that is not complete, then a null is returned.
     * @param teamId
     * @return
     */
    MissionDto getCurrentMission( int teamId );
    
    /**
     * Add the passed mission to the database
     * @param mission
     * @return Updated Mission
     * @throws InvalidMissionException
     */
    MissionDto addMission( MissionDto mission ) throws InvalidMissionException;
    
    /**
     * Update the passed mission in the database
     * @param mission
     * @return the updated Mission
     */
    MissionDto updateMission( MissionDto mission );
    
    /**
     * Add the passed team to the passed mission
     * @param mission
     * @param teamToAdd
     * @return the updated Mission
     * 
     * PROBABLY DON'T REALLY NEED THIS METHOD - IT CAN BE PART OF BUSINESS LOGIC
     */
    MissionDto addTeamToMission( MissionDto mission, TeamDto teamToAdd );
    
    /**
     * Remove the passed team from the passed mission
     * @param mission
     * @param teamToRemove
     * @return the updated Mission
     * 
     * PROBABLY DON'T REALLY NEED THIS METHOD - IT CAN BE PART OF BUSINESS LOGIC
     */
    MissionDto removeTeamFromMission( MissionDto mission, TeamDto teamToRemove );
}
