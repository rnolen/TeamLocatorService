package com.geocent.teamdb.eao;

import com.geocent.teamdb.entity.Mission;
import com.geocent.teamdb.entity.MissionTeam;
import com.geocent.teamdb.entity.Team;
import com.geocent.teamdb.util.Converter;
import com.geocent.teamlocator.dto.MissionDto;
import com.geocent.teamlocator.dto.TeamDto;
import com.geocent.teamlocator.exception.EntityNotFoundException;
import com.geocent.teamlocator.exception.InvalidMissionException;
import java.util.ArrayList;

import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.Query;


/**
 * Session Bean implementation class MissionEaoImpl
 */
@Stateless
@LocalBean
public class MissionEaoImpl extends AbstractEao implements MissionEao {

    private Converter converter;
    /**
     * Default constructor. 
     */
    public MissionEaoImpl() { 
        converter = new Converter();
    }
    
    /**
     * Constructor used when the EntityManager can't be injected (ex: JUnit out of container test)
     * 
     */
    public MissionEaoImpl( EntityManager em ) {
        this.em = em;
        converter = new Converter();
    }

	/**
     * @see MissionEao#getMissions(int)
     */
    @SuppressWarnings( "unchecked" )
    public List<MissionDto> getMissions( TeamDto teamDto ) throws EntityNotFoundException {
        Team team = converter.toEntity( teamDto );
        if( !team.hasId() ) {
            throw new EntityNotFoundException( Team.class, team.getId() );
        }
        List<MissionDto> missionList = new ArrayList<MissionDto>();
        String missionTeamQuery = "SELECT * FROM mission_teams where team_id = ?";

        // Native query that will return list of MissionTeam instances
        Query mtQuery = em.createNativeQuery( missionTeamQuery, MissionTeam.class );
        mtQuery.setParameter( 1,  team.getId() );
        List<MissionTeam> missionTeams = (List<MissionTeam>) mtQuery.getResultList();
        for( MissionTeam mt : missionTeams ) {
            missionList.add( converter.fromEntity(mt.getMission()) );
        }
        
        
        return missionList;
    }

    public MissionDto getCurrentMission( int teamId ) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<MissionDto> getMissionByDescription( String desc ) {
        List<MissionDto> result = new ArrayList<MissionDto>();
        List<Mission> resultList = null;
        Query query = em.createQuery( "select m from Mission m where upper(m.description) = :desc" );
        query.setParameter( "desc", desc.toUpperCase() );
        resultList = (List<Mission>) query.getResultList();
        for( Mission mission : resultList ) {
            result.add( converter.fromEntity(mission) );
        }
        return result;
    }

	/**
     * @see MissionEao#addMission(Mission)
     */
    public MissionDto addMission(MissionDto missionDto) throws InvalidMissionException {
        MissionDto result = null;
        if( missionDto == null ) {
            throw new InvalidMissionException( "Invalid MissionDto passed to addMission" );
        }
        Mission mission = converter.toEntity( missionDto );
        mission = persist( mission );
        result = converter.fromEntity( mission );
        return result;
    }

	/**
     * @see MissionEao#addTeamToMission(MissionDto, TeamDto)
     */
    public MissionDto addTeamToMission(MissionDto mission, TeamDto teamToAdd) {
        // TODO Auto-generated method stub
			return null;
    }

	/**
     * @see MissionEao#removeTeamFromMission(Mission, Team)
     */
    public void removeTeamFromMission(MissionDto mission, TeamDto teamToRemove) {
        Query query = em.createNativeQuery( "DELETE FROM mission_teams WHERE mission_id = ? AND team_id = ?" );
        query.setParameter( 1, mission.getId() );
        query.setParameter( 2, teamToRemove.getId() );
        int rowCount = query.executeUpdate();
        Logger.getLogger( MissionEaoImpl.class.getName() ).log( Level.INFO, "---->DEBUG: removed " + rowCount + " MissionTeam rows" );
    }

	/**
     * @see MissionEao#getMission(int)
     */
    public MissionDto getMission(int key) throws EntityNotFoundException {
        Mission mission = this.get( Mission.class, key );
        if( mission == null ) {
            throw new EntityNotFoundException( Mission.class, key );
        }
        return converter.fromEntity( mission );
    }

	/**
     * @see MissionEao#updateMission(Mission)
     */
    public MissionDto updateMission(MissionDto mission) {
        // TODO Auto-generated method stub
			return null;
    }

	/**
     * @see MissionEao#getMissions(Date)
     */
    public List<MissionDto> getMissions(Date missionDate) {
        // TODO Auto-generated method stub
			return null;
    }

}
