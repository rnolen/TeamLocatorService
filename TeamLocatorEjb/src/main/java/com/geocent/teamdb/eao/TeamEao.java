/**
 * 
 */
package com.geocent.teamdb.eao;

import java.util.List;

import com.geocent.teamdb.entity.Mission;
import com.geocent.teamdb.entity.Team;
import com.geocent.teamlocator.dto.MissionDto;
import com.geocent.teamlocator.dto.TeamDto;
import com.geocent.teamlocator.exception.EntityNotFoundException;
import javax.ejb.Remote;

/**
 * @author rnolen
 *
 */
@Remote
public interface TeamEao extends Eao
{
    /**
     * Get Team by key
     * @param key
     * @return Team
     */
    public TeamDto getTeam( int key );
    
    /**
     * Get list of all teams
     * @return List of Teams
     */
    public List<TeamDto> getTeams();

    /**
     * Returns team(s) found using the input name
     * @param name
     * @return List containing Team(s) found. 
     */
    public List<TeamDto> getTeamByName( String name );
    
    /**
     * Get list of teams that participated in the passed mission
     * @param mission
     * @return List of Teams
     */
    public List<TeamDto> getTeams( MissionDto mission );
    
    /**
     * Add the passed team to the database
     * @param teamToAdd
     * @return updated TeamDto
     */
    public TeamDto addTeam( TeamDto teamToAdd ) throws EntityNotFoundException;
    
    /**
     * Update the passed team in the database
     * @param teamToUpdate
     * @return updated Team
     */
    public TeamDto updateTeam( TeamDto teamToUpdate );
}
