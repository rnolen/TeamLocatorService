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
     * Get all missions that the passed Team participated in
     * @param teamDto
     * @return List of missions
     * @throws EntityNotFoundException if the Team is not valid
     */
    List<MissionDto> getMissions( TeamDto teamDto ) throws EntityNotFoundException;
    
    /**
     * Retrieves the latest Mission for the input team Id. The determination is made using the completed flag. If
     * there is no mission for the team that is not complete, then a null is returned.
     * @param teamId
     * @return
     */
    List<MissionDto> getCurrentMission( int teamId );
    
    /**
     * Retrieve Mission by description. It should get one Mission, but we don't have a unique constraint on the 
     * description so there could be more than one.
     * 
     * @param desc
     * @return List of Mission object(s) found with the description.
     */
    List<MissionDto> getMissionByDescription( String desc );
    
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
    
}
