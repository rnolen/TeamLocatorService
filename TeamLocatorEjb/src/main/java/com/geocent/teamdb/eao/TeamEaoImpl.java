/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.geocent.teamdb.eao;

import com.geocent.teamdb.entity.Team;
import com.geocent.teamlocator.dto.MissionDto;
import com.geocent.teamlocator.dto.TeamDto;
import java.util.ArrayList;
import java.util.List;
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

    
    @Override
    public TeamDto getTeam( int key ) {
        throw new UnsupportedOperationException( "Not supported yet." );
    }

    @Override
    public List<TeamDto> getTeams() {
        throw new UnsupportedOperationException( "Not supported yet." );
    }

    @Override
    public List<TeamDto> getTeamByName( String name ) {
        List<TeamDto> result = new ArrayList<TeamDto>();
        Query query = em.createQuery( "select t from team t where upper(t.name) = :name" );
        query.setParameter( "name", name.toUpperCase() );
        Team team = (Team) query.getSingleResult();
        result.add( converter.fromEntity( team) );
        throw new UnsupportedOperationException( "Not supported yet." );
    }

    @Override
    public List<TeamDto> getTeams( MissionDto mission ) {
        throw new UnsupportedOperationException( "Not supported yet." );
    }

    @Override
    public TeamDto addTeam( TeamDto teamToAdd ) {
        throw new UnsupportedOperationException( "Not supported yet." );
    }

    @Override
    public TeamDto updateTeam( TeamDto teamToUpdate ) {
        throw new UnsupportedOperationException( "Not supported yet." );
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

}
