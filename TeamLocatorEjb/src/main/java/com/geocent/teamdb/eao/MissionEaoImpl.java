package com.geocent.teamdb.eao;

import com.geocent.teamdb.entity.Mission;
import com.geocent.teamdb.entity.Team;
import com.geocent.teamdb.util.Converter;
import com.geocent.teamlocator.dto.MissionDto;
import com.geocent.teamlocator.dto.TeamDto;
import com.geocent.teamlocator.exception.EntityNotFoundException;
import com.geocent.teamlocator.exception.InvalidMissionException;
import java.util.ArrayList;

import java.util.Date;
import java.util.List;
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
    public List<MissionDto> getMissions(int teamId) {
        // TODO Auto-generated method stub
			return null;
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
    public MissionDto removeTeamFromMission(MissionDto mission, TeamDto teamToRemove) {
        // TODO Auto-generated method stub
			return null;
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
