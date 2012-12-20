package com.geocent.teamlocator.service;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

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
    
    /**
     * Default constructor. 
     */
    public IntegrationTestCleanupBean() {
        // TODO Auto-generated constructor stub
    }

    @Override
    public void deleteMission( MissionDto mission ) throws EntityNotFoundException {
        missionEao.delete( converter.toEntity( mission ) );        
    }

    @Override
    public void deleteTeamMember( MemberDto member ) throws EntityNotFoundException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void deleteTeam( TeamDto team ) throws EntityNotFoundException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void deleteLocation( LocationDto location ) throws EntityNotFoundException {
        // TODO Auto-generated method stub
        
    }

}