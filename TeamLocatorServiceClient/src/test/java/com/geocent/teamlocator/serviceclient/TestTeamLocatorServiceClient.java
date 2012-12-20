package com.geocent.teamlocator.serviceclient;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.junit.Before;
import org.junit.Test;

import com.geocent.teamlocator.dto.MissionDto;
import com.geocent.teamlocator.dto.ObjectiveDto;
import com.geocent.teamlocator.dto.TeamDto;
import com.geocent.teamlocator.exception.InvalidMissionException;
import com.geocent.teamlocator.exception.ServiceNotFoundException;
import com.geocent.teamlocator.service.IntegrationTestCleanup;

public class TestTeamLocatorServiceClient
{
    private TeamLocatorServiceClient client;
    private IntegrationTestCleanup testCleanup;

    @Before
    public void setUp() throws Exception {
        client = new TeamLocatorServiceClient();
    }

    @Test( expected=InvalidMissionException.class )
    public void testAddMissionWithoutObjective() throws Exception {
        MissionDto mission = createTestMission();
        
        client.addMission( mission );
    }

    @Test
    public void testAddMissionWithObjective() throws Exception {
        ObjectiveDto objective = new ObjectiveDto();
        objective.setDescription( "Test Objective" );
        objective.setLatitude( 0.00 );
        objective.setLongitude( 0.00 );
        
        MissionDto mission = createTestMission();
        mission.setObjective( objective );
        
        mission = client.addMission( mission );
        Integer missionKey = mission.getId();
        Integer objectiveKey = mission.getObjective().getId();

        assertTrue( "Mission should have valid key", (missionKey != null && missionKey.intValue()>0) );
        assertTrue( "Objective in mission should have valid key", (objectiveKey != null && objectiveKey.intValue()>0) );
        
        // Now clean up the added item
        getCleanupService().deleteMission( mission );
    }

    @Test
    public void testAddTeam() {
        fail( "Not yet implemented" );
    }

    @Test
    public void testGetTeamByNameAlpha() {
        List<TeamDto> result = client.getTeamByName( "alpha" );
        assertEquals( "Should have retrieved one result", 1, result.size() );
        assertEquals( "Team name returned should be 'ALPHA'", "ALPHA", result.get( 0 ).getName() );
    }

    @Test
    public void testGetTeamByNameMike() {
        List<TeamDto> result = client.getTeamByName( "mike" );
        assertEquals( "Should be no result", 0, result.size() );
    }

    @Test
    public void testRemoveTeam() {
        fail( "Not yet implemented" );
    }

    @Test
    public void testAddMember() {
        fail( "Not yet implemented" );
    }

    @Test
    public void testRemoveMember() {
        fail( "Not yet implemented" );
    }

    @Test
    public void testGetCurrentMission() {
        fail( "Not yet implemented" );
    }

    @Test
    public void testAddMemberLocation() {
        fail( "Not yet implemented" );
    }

    @Test
    public void testGetLastLocationForTeam() {
        fail( "Not yet implemented" );
    }

    private MissionDto createTestMission() {
        MissionDto mission = new MissionDto();
        mission.setCompleted( false );
        mission.setDescription( "Test Mission" );
        mission.setMissionDate( new Date() );
        return mission;
    }

    private Properties getInitProperties() {
        Properties result = new Properties();
        
        // Need to tell the context where and how to look
        result.setProperty( "java.naming.factory.initial", "com.sun.enterprise.naming.SerialInitContextFactory" );
        result.setProperty( "java.naming.factory.url.pkgs", "com.sun.enterprise.naming" );
        result.setProperty( "java.naming.factory.state", "com.sun.corba.ee.impl.presentation.rmi.JNDIStateFactoryImpl" );
 
        // Should not be necessary for local test (default values), but
        // currently is
        result.setProperty("org.omg.CORBA.ORBInitialHost", "localhost");
        result.setProperty("org.omg.CORBA.ORBInitialPort", "3700");
        return result;
    }

    
    private IntegrationTestCleanup getCleanupService() throws ServiceNotFoundException {

        if( testCleanup == null ) {
            Properties props = getInitProperties();
            try {
                InitialContext ctx = new InitialContext( props );
                testCleanup = (IntegrationTestCleanup) 
                        ctx.lookup( "java:global/TeamLocatorEar/TeamLocatorService-1.0-SNAPSHOT/IntegrationTestCleanupBean!com.geocent.teamlocator.service.IntegrationTestCleanup" );
//                    ctx.lookup( "java:global/com.geocent.teamlocator_TeamLocatorEar_ear_1.0-SNAPSHOT/TeamLocatorService-1.0-SNAPSHOT/TeamLocatorServiceBean!com.geocent.teamlocator.service.TeamLocatorService" );
            } catch( NamingException ex ) {
                Logger.getLogger( TeamLocatorServiceClient.class.getName() ).log( Level.SEVERE, null, ex );
                testCleanup = null;
            }
            if( testCleanup == null ) {
                throw new ServiceNotFoundException( "JNDI lookup failed for TeamLocatorService" );
            }
        }
        
        return testCleanup;
    }
}
