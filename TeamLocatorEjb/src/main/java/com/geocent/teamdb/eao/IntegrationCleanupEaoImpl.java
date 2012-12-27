/**
 * 
 */
package com.geocent.teamdb.eao;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Query;

import com.geocent.teamdb.entity.Member;
import com.geocent.teamdb.entity.Mission;
import com.geocent.teamdb.entity.Team;
import com.geocent.teamlocator.dto.MemberDto;
import com.geocent.teamlocator.dto.MissionDto;
import com.geocent.teamlocator.dto.TeamDto;
import com.geocent.teamlocator.exception.EntityNotFoundException;

/**
 * @author rnolen
 *
 */
@Stateless
@LocalBean
public class IntegrationCleanupEaoImpl extends AbstractEao implements IntegrationCleanupEao
{

    /* (non-Javadoc)
     * @see com.geocent.teamdb.eao.IntegrationCleanupEao#deleteMission(com.geocent.teamlocator.dto.MissionDto)
     */
    public void deleteMission( MissionDto missionDto ) throws EntityNotFoundException {
        Logger logger = Logger.getLogger( IntegrationCleanupEaoImpl.class.getName() );
        logger.log( Level.INFO, "IntegrationCleanupEaoImpl.deleteMission" );

        // Before deleting the mission itself, we want to delete any MissionTeam references to the mission
        Mission mission = converter.toEntity( missionDto );
        if( !mission.hasId() ) {
            throw new EntityNotFoundException( mission.getClass(), mission.getId() );
        }
        Query query = em.createNativeQuery( "DELETE FROM mission_teams where mission_id = ?" );
        query.setParameter( 1, mission.getId() );
        int rowCount = query.executeUpdate();
        logger.log( Level.INFO, "---->DEBUG: removed " + rowCount + " MissionTeam rows" );
        
        // Now delete the mission
        this.delete( mission );
    }

    /* (non-Javadoc)
     * @see com.geocent.teamdb.eao.IntegrationCleanupEao#deleteTeam(com.geocent.teamlocator.dto.TeamDto)
     */
    public void deleteTeam( TeamDto teamDto ) throws EntityNotFoundException {
        Logger logger = Logger.getLogger( IntegrationCleanupEaoImpl.class.getName() );
        logger.log( Level.INFO, "IntegrationCleanupEaoImpl.deleteTeam" );

        // Before deleting the mission itself, we want to delete any MissionTeam references to the mission
        Team team = converter.toEntity( teamDto );
        if( !team.hasId() ) {
            throw new EntityNotFoundException( team.getClass(), team.getId() );
        }
        Query query = em.createNativeQuery( "DELETE FROM mission_teams where team_id = ?" );
        query.setParameter( 1, team.getId() );
        int rowCount = query.executeUpdate();
        logger.log( Level.INFO, "---->DEBUG: removed " + rowCount + " MissionTeam rows" );
        
        // Now delete the mission
        this.delete( team );
    }

    @Override
    public void deleteMember( MemberDto memberDto ) throws EntityNotFoundException {
        Member member = converter.toEntity( memberDto );
        this.delete( member );
    }

}
