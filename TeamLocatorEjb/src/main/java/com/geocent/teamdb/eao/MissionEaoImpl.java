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

    @SuppressWarnings( "unchecked" )
    public List<MissionDto> getCurrentMission( int teamId ) {
        List<MissionDto> result = new ArrayList<MissionDto>();

        // Current business logic in the query is that the mission is considered 'current' if it's not marked as complete
        // There's probably additional business logic that needs to be involved. May need an additional column to indicate
        // 'active', or maybe some calculation based on current date.
        String queryString = "SELECT m.* From mission m, mission_teams t " + 
                                "WHERE upper(m.completed)!= 'Y' AND " + 
                                       "m.id=t.mission_id AND " + 
                                       "t.team_id=?";
        
        Query query = em.createNativeQuery( queryString, Mission.class );
        query.setParameter( 1, teamId );
        List<Mission> missions = (List<Mission>)query.getResultList();
        for( Mission mission : missions ) {
            result.add(  converter.fromEntity( mission ) );
        }
        return result;
    }

    @SuppressWarnings( "unchecked" )
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
