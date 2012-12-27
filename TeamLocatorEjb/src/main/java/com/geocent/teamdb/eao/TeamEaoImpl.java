/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.geocent.teamdb.eao;

import com.geocent.teamdb.entity.MissionTeam;
import com.geocent.teamdb.entity.Team;
import com.geocent.teamlocator.dto.MissionDto;
import com.geocent.teamlocator.dto.TeamDto;
import com.geocent.teamlocator.exception.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.Query;

/**
 *
 * @author rnolen
 */
@Stateless
@LocalBean
public class TeamEaoImpl extends AbstractEao implements TeamEao {
    @EJB 
    private MissionEao missionEao;

    
    @Override
    public TeamDto getTeam( int key ) {
        throw new UnsupportedOperationException( "Not supported yet." );
    }

    @Override
    public List<TeamDto> getTeams() {
        throw new UnsupportedOperationException( "Not supported yet." );
    }

    @SuppressWarnings( "unchecked" )
    @Override
    public List<TeamDto> getTeamByName( String name ) {
        List<TeamDto> result = new ArrayList<TeamDto>();
        List<Team> resultList = null;
        Query query = em.createQuery( "select t from Team t where upper(t.name) = :name" );
        query.setParameter( "name", name.toUpperCase() );
        resultList = (List<Team>) query.getResultList();
        for( Team team : resultList ) {
            result.add( converter.fromEntity(team) );
        }
        return result;
    }

    @Override
    public List<TeamDto> getTeams( MissionDto mission ) {
        throw new UnsupportedOperationException( "Not supported yet." );
    }

    @Override
    public TeamDto addTeam( TeamDto teamToAdd ) throws EntityNotFoundException {
        TeamDto result = null;
        // First need to get the Mission from the DB
        MissionDto mission = missionEao.getMission( teamToAdd.getMissions().get( 0).getId() );

        // Then add the team to the database
        Team team = converter.toEntity( teamToAdd );
        team = persist( team );
        result = converter.fromEntity( team );
        result.getMissions().add( mission );

        // Then need to create a new MissionTeam object to link the two
        MissionTeam missionTeam = new MissionTeam();
        missionTeam.setMission( converter.toEntity( mission ) );
        missionTeam.setTeam( team );
        missionTeam = persist( missionTeam );
        
        return result;
    }

    @Override
    public TeamDto updateTeam( TeamDto teamToUpdate ) {
        throw new UnsupportedOperationException( "Not supported yet." );
    }

    /**
     * @see TeamEao#removeTeamFromMission(Mission, Team)
     */
    public void removeTeamFromMission(MissionDto mission, TeamDto teamToRemove) {
        Query query = em.createNativeQuery( "DELETE FROM mission_teams WHERE mission_id = ? AND team_id = ?" );
        query.setParameter( 1, mission.getId() );
        query.setParameter( 2, teamToRemove.getId() );
        int rowCount = query.executeUpdate();
        Logger.getLogger( MissionEaoImpl.class.getName() ).log( Level.INFO, "---->DEBUG: removed " + rowCount + " MissionTeam rows" );
    }

}
