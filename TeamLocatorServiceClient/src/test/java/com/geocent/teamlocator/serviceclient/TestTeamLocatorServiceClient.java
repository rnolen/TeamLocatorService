package com.geocent.teamlocator.serviceclient;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.junit.Before;
import org.junit.Test;

import com.geocent.teamlocator.dto.LocationDto;
import com.geocent.teamlocator.dto.MemberDto;
import com.geocent.teamlocator.dto.MissionDto;
import com.geocent.teamlocator.dto.ObjectiveDto;
import com.geocent.teamlocator.dto.TeamDto;
import com.geocent.teamlocator.enums.MemberRank;
import com.geocent.teamlocator.enums.TeamRole;
import com.geocent.teamlocator.exception.EntityNotFoundException;
import com.geocent.teamlocator.exception.InvalidMissionException;
import com.geocent.teamlocator.exception.ServiceNotFoundException;
import com.geocent.teamlocator.service.IntegrationTestCleanup;

public class TestTeamLocatorServiceClient
{
    public static final double UNKNOWN_LOC = -1.0;
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
        MissionDto mission = createMissionWithObjective();
        
        mission = client.addMission( mission );
        Integer missionKey = mission.getId();
        Integer objectiveKey = mission.getObjective().getId();

        assertTrue( "Mission should have valid key", (missionKey != null && missionKey.intValue()>0) );
        assertTrue( "Objective in mission should have valid key", (objectiveKey != null && objectiveKey.intValue()>0) );
        
        // Now clean up the added item
        getCleanupService().deleteMission( mission );
    }

    @Test
    public void testGetMissionByDescription() throws Exception {
        String desc  = "RECON LIC";
        
        List<MissionDto> missions = client.getMissionByDescription( desc );

        assertNotNull( "Returned list should not be null", missions );
        assertEquals( "Should be one entry in the list", 1, missions.size() );
        assertEquals( desc, missions.get( 0).getDescription() );
    }

    @Test 
    public void testGetMissionsForTeam() throws Exception {
        List<TeamDto> teams = client.getTeamByName( "alpha" );
        List<MissionDto> missions = client.getMissionsForTeam( teams.get(0) );
        assertNotNull( "Returned list should not be null", missions );
        assertTrue( "List should have one mission", missions.size() == 1 );
    }
    
    @Test
    public void testAddTeam() throws EntityNotFoundException, InvalidMissionException, ServiceNotFoundException {
        // To setup, need to add the mission
        MissionDto mission = createMissionWithObjective();
        mission = client.addMission( mission );
        
        TeamDto team = new TeamDto();
        team.setName( "ZEBRA" );
        
        team = client.addTeam( team, mission );
        assertNotNull( "Team should not be null", team );
        Integer id = team.getId();
        assertTrue( "Team should have a valid Id", (id != null && id.intValue()>0) );
        assertTrue( team.getMissions().size()>0 );
        
        // Now clean up
        getCleanupService().deleteTeam( team );
        getCleanupService().deleteMission( mission );
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
    public void testRemoveTeam()  throws Exception {
        TeamDto team = new TeamDto();
        team.setName( "ZEBRA" );

        List<MissionDto> missionsCleanup = new ArrayList<MissionDto>();
        try {
            for( int i=0; i<3; i++ ) {
                MissionDto mission = createMissionWithObjective();
                mission.setDescription( "Mission " + i );
                mission = client.addMission( mission );
                missionsCleanup.add(  mission  );
                team = client.addTeam( team, mission );
                team.getMissions().clear();
            }
            
            
            // Setup is complete - now we can try the test
            client.removeTeam( team, missionsCleanup.get( 0 ) );
            List<MissionDto> missions = client.getMissionsForTeam( team );
            assertNotNull( "List of missions should not be null", missions );
            assertEquals( "Mission list should have 2 items", 2, missions.size() );
        } finally {
            // Now clean up behind us
            getCleanupService().deleteTeam( team );
            getCleanupService().deleteMissions( missionsCleanup );
        }
    }
        

    @Test
    public void testAddMemberWithValidTeam() throws Exception {
        
        List<TeamDto> teams = client.getTeamByName( "alpha" );
        MemberDto memberDto = new MemberDto();
        memberDto.setFirstName( "Malcolm" );
        memberDto.setLastName( "Pickering" );
        memberDto.setRank( MemberRank.LIEUTENANT );
        memberDto.setRole( TeamRole.XO );
        memberDto.setTeam( teams.get( 0 ) );
        try {
            memberDto = client.addMember( memberDto, teams.get(0) );
            assertNotNull( "Updated member should not be null", memberDto );
            assertTrue( "Updated member should have valid Id", (memberDto.getId() != null && memberDto.getId().intValue()>0) );
            TeamDto team = memberDto.getTeam();
            assertTrue( "Updated member should have the same team Id sent", team.getId().equals(teams.get(0).getId()) );
        } finally {
            // Remove the team member we just added
            getCleanupService().deleteTeamMember( memberDto );
        }
    }

    @Test(expected=EntityNotFoundException.class)
    public void testAddMemberWithInvalidTeam() throws Exception {
        MemberDto memberDto = new MemberDto();
        memberDto.setFirstName( "Malcolm" );
        memberDto.setLastName( "Pickering" );
        memberDto.setRank( MemberRank.LIEUTENANT );
        memberDto.setRole( TeamRole.XO );
        TeamDto teamDto = new TeamDto();
        client.addMember( memberDto, teamDto );
    }

    @Test
    public void testGetMembersByName() {
        List<MemberDto> members = client.getMembers( "mccoy", null, null );
        assertNotNull( "Returned list should not be null", members );
        assertEquals( "Returned list should have one item", 1, members.size() );
        
        members = client.getMembers( null,  "", "alvin" );
        assertNotNull( "Returned list should not be null", members );
        assertEquals( "Returned list should have one item", 1, members.size() );
        
        members = client.getMembers( null,  null,  null );
        assertNotNull( "Returned list should not be null", members );
        assertEquals( "Returned list should have three items", 7, members.size() );
    }
    
    @Test
    public void testReassignMemberWithValidTeam() throws Exception {
        List<MemberDto> members = client.getMembers( "mccoy", null, null );
        MemberDto member = members.get(0);
        TeamDto origTeam = member.getTeam();
        assertEquals( "Original team Id should be 1", 1, origTeam.getId() );
        
        List<TeamDto> teams = client.getTeamByName( "bravo" );
        member = client.reassignMember( member, teams.get( 0 ) );
        assertEquals( "New team should have Id=2", 2, member.getTeam().getId() );
        
        // Finally re-assign back to the original team
        member = client.reassignMember( member, origTeam );
        assertEquals( "Should be back to original team with Id=1", 1, member.getTeam().getId() );
        
    }

    @Test( expected=EntityNotFoundException.class )
    public void testReassignMemberWithInvalidTeam() throws Exception {
        List<MemberDto> members = client.getMembers( "mccoy", null, null );
        MemberDto member = members.get(0);
        TeamDto origTeam = member.getTeam();
        assertEquals( "Original team Id should be 1", 1, origTeam.getId() );

        TeamDto newTeam = new TeamDto();
        member = client.reassignMember( member, newTeam );
        fail( "Should not have reached this point" );
    }

    @Test
    public void testGetCurrentMission() throws Exception {
        GregorianCalendar calendar = new GregorianCalendar();

        List<MemberDto> members = client.getMembers( "mccoy", null, "ken" );
        MemberDto member = members.get( 0 );
        TeamDto team = member.getTeam();
        
        List<MissionDto> missionsCleanup = new ArrayList<MissionDto>();
        try {
            for( int i=0; i<3; i++ ) {
                calendar.add( Calendar.MONTH, (i*-1) );
                MissionDto mission = createMissionWithObjective();
                mission.setMissionDate( calendar.getTime() );
                mission.setCompleted( true );
                mission.setDescription( "Mission " + i );
                mission = client.addMission( mission );
                missionsCleanup.add(  mission  );
                team = client.addTeam( team, mission );
                team.getMissions().clear();
            }

            // All of the above was setup - here's where the test actually starts>>>
            List<MissionDto> missions = client.getCurrentMission( members.get( 0 ) );
            assertEquals( "Should only be one mission returned", 1, missions.size() );
            assertEquals( "Mission Id should be 1", 1, missions.get( 0 ).getId() );
        } finally {
            // Clean up the missions created above
            getCleanupService().deleteMissions( missionsCleanup );
        }
    }

    @Test
    public void testMemberLocationServices() throws Exception {
        List<LocationDto> cleanupList = new ArrayList<LocationDto>();
        try {
            TeamDto team  = client.getTeamByName( "alpha" ).get( 0 );
            List<MemberDto> members = client.getMembersOfTeam( team );
            MissionDto mission = client.getCurrentMission( members.get(0) ).get(0);
            
            List<LocationDto> teamLocations = client.getLastLocationForTeam( members.get( 0 ), 1000 );
            assertNotNull( "List of locations should not be null", teamLocations );
            assertTrue( "Should not have returned any locations", teamLocations.size() == 0 );
            
            // Now widen the range and try again
            teamLocations = client.getLastLocationForTeam( members.get( 0 ), 1500 );
            assertTrue( "Should have gotten 2 locations this time", teamLocations.size() == 2 );

        } finally {
            // Now cleanup behind us
            getCleanupService().deleteLocations( cleanupList );
        }
    }

    @Test
    public void testGetLastLocationForTeamWithNoLocations() throws Exception {
        List<TeamDto> teams = client.getTeamByName( "bravo" );
        TeamDto team = teams.get( 0 );
        List<MemberDto> members = client.getMembersOfTeam( team );
        
        List<LocationDto> teamLocations = client.getLastLocationForTeam( members.get( 0 ), 10 );
        assertNotNull( "List of locations should not be null", teamLocations );
        assertEquals( "Should have returned 3 locations", 3, teamLocations.size() );
        
        boolean allUnknown = true;
        
        for( LocationDto loc : teamLocations ) {
            if( loc.getLattitude() != UNKNOWN_LOC || loc.getLongitude() != UNKNOWN_LOC ) {
                allUnknown = false;
                break;
            }
        }
        assertTrue( "All locations should be UNKNOWN", allUnknown );
    }

    private MissionDto createTestMission() {
        MissionDto mission = new MissionDto();
        mission.setCompleted( false );
        mission.setDescription( "Test Mission" );
        mission.setMissionDate( new Date() );
        return mission;
    }

    private ObjectiveDto createTestObjective() {
        ObjectiveDto objective = new ObjectiveDto();
        objective.setDescription( "Test Objective" );
        objective.setLatitude( 0.00 );
        objective.setLongitude( 0.00 );
        return objective;
    }
    
    private MissionDto createMissionWithObjective() {
        MissionDto mission = createTestMission();
        ObjectiveDto objective = createTestObjective();
        mission.setObjective( objective );
        
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
                    ctx.lookup( "ejb/IntegrationTestCleanupBean#com.geocent.teamlocator.service.IntegrationTestCleanup" );
//                    ctx.lookup( "java:global/TeamLocatorEar-1.0-SNAPSHOT/TeamLocatorService-1.0-SNAPSHOT/IntegrationTestCleanupBean!com.geocent.teamlocator.service.IntegrationTestCleanup" );
//                    ctx.lookup( "java:global/TeamLocatorEar/TeamLocatorService-1.0-SNAPSHOT/IntegrationTestCleanupBean!com.geocent.teamlocator.service.IntegrationTestCleanup" );
//                    ctx.lookup( "java:global/com.geocent.teamlocator_TeamLocatorEar_ear_1.0-SNAPSHOT/TeamLocatorService-1.0-SNAPSHOT/IntegrationTestCleanupBean!com.geocent.teamlocator.service.IntegrationTestCleanup" );
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
