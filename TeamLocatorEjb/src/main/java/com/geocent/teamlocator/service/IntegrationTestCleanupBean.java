package com.geocent.teamlocator.service;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.geocent.teamdb.eao.IntegrationCleanupEao;
import com.geocent.teamdb.eao.MissionEao;
import com.geocent.teamdb.eao.TeamEao;
import com.geocent.teamdb.util.Converter;
import com.geocent.teamlocator.dto.LocationDto;
import com.geocent.teamlocator.dto.MemberDto;
import com.geocent.teamlocator.dto.MissionDto;
import com.geocent.teamlocator.dto.TeamDto;
import com.geocent.teamlocator.exception.EntityNotFoundException;

/**
 * Session Bean implementation class IntegrationTestCleanupBean
 * 
 * This class is intended ONLY for cleanup following unit/integration tests!!!!!
 */
@Stateless
@LocalBean
public class IntegrationTestCleanupBean implements IntegrationTestCleanup {
    
    @EJB
    private MissionEao missionEao;
    
    @EJB
    private TeamEao teamEao;

    @EJB
    private Converter converter;
    
    @EJB
    private IntegrationCleanupEao cleanupEao;
    
    /**
     * Default constructor. 
     */
    public IntegrationTestCleanupBean() {
        // TODO Auto-generated constructor stub
    }

    @Override
    public void deleteMission( MissionDto missionDto ) throws EntityNotFoundException {
        cleanupEao.deleteMission( missionDto );        
    }

    @Override
    public void deleteMissions( List<MissionDto> missions ) throws EntityNotFoundException {
        for( MissionDto mission : missions ) {
            cleanupEao.deleteMission( mission );
        }
    }

    @Override
    public void deleteTeamMember( MemberDto member ) throws EntityNotFoundException {
        cleanupEao.deleteMember( member );
        
    }

    @Override
    public void deleteTeam( TeamDto teamDto ) throws EntityNotFoundException {
        cleanupEao.deleteTeam( teamDto );
        
    }

    @Override
    public void deleteLocation( LocationDto location ) throws EntityNotFoundException {
        // TODO Auto-generated method stub
        
    }

}
