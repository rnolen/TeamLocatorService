/**
 * 
 */
package com.geocent.teamdb.eao;

import javax.ejb.Remote;

import com.geocent.teamlocator.dto.MissionDto;
import com.geocent.teamlocator.dto.TeamDto;
import com.geocent.teamlocator.exception.EntityNotFoundException;

/**
 * @author rnolen
 *
 */
@Remote
public interface IntegrationCleanupEao extends Eao
{
    /**
     * Deletes the input Mission from the database along with any MissionTeam entities that refer to the Mission. 
     * @param missionDto
     * @throws EntityNotFoundException
     */
    void deleteMission( MissionDto missionDto ) throws EntityNotFoundException;
    
    /**
     * Deletes the input Team from the database along with any MissionTeam entities that refer to the Team. 
     * @param teamDto
     * @throws EntityNotFoundException 
     */
    void deleteTeam( TeamDto teamDto ) throws EntityNotFoundException;

}
