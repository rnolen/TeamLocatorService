/**
 * 
 */
package com.geocent.teamlocator.serviceclient;

import java.util.List;

import com.geocent.teamlocator.dto.LocationDto;
import com.geocent.teamlocator.dto.MemberDto;
import com.geocent.teamlocator.dto.MissionDto;
import com.geocent.teamlocator.dto.TeamDto;
import com.geocent.teamlocator.exception.EntityNotFoundException;
import com.geocent.teamlocator.exception.InvalidMissionException;
import com.geocent.teamlocator.exception.ServiceNotFoundException;
import com.geocent.teamlocator.service.TeamLocatorService;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * @author rnolen
 *
 */
public class TeamLocatorServiceClient implements TeamLocatorService
{
    private TeamLocatorService service;

    public TeamLocatorServiceClient() {
    }
    
    /* (non-Javadoc)
     * @see com.geocent.teamlocator.service.TeamLocatorService#addMission(com.geocent.teamlocator.dto.MissionDto)
     */
    @Override
    public MissionDto addMission( MissionDto mission ) throws InvalidMissionException {
        MissionDto result = null;
        try {
            result = getService().addMission( mission );
        } catch( ServiceNotFoundException e ) {
            Logger.getLogger( TeamLocatorServiceClient.class.getName() ).log( Level.SEVERE, null, e );
        }
        return result;
    }

    /* (non-Javadoc)
     * @see com.geocent.teamlocator.service.TeamLocatorService#getCurrentMission(com.geocent.teamlocator.dto.MemberDto)
     */
    @Override
    public List<MissionDto> getCurrentMission( MemberDto member ) throws EntityNotFoundException {
        List<MissionDto> result = new ArrayList<MissionDto>();
        try {
            result = getService().getCurrentMission( member );
        } catch( ServiceNotFoundException ex ) {
            Logger.getLogger( TeamLocatorServiceClient.class.getName() ).log( Level.SEVERE, null, ex );
        }
        return result;
    }

    @Override
    public List<MissionDto> getMissionByDescription( String desc ) {
        List<MissionDto> result = new ArrayList<MissionDto>();
        try {
            result = getService().getMissionByDescription( desc );
        } catch( ServiceNotFoundException ex ) {
            Logger.getLogger( TeamLocatorServiceClient.class.getName() ).log( Level.SEVERE, null, ex );
        }
        return result;
    }
    
    @Override
    public List<MissionDto> getMissionsForTeam( TeamDto team ) throws EntityNotFoundException {
        List<MissionDto> missions = new ArrayList<MissionDto>();
        try {
            missions = getService().getMissionsForTeam( team );
        } catch( ServiceNotFoundException e ) {
            Logger.getLogger( TeamLocatorServiceClient.class.getName() ).log( Level.SEVERE, null, e );
        }
        return missions;
    }

    /* (non-Javadoc)
     * @see com.geocent.teamlocator.service.TeamLocatorService#addTeam(com.geocent.teamlocator.dto.TeamDto, com.geocent.teamlocator.dto.MissionDto)
     */
    @Override
    public TeamDto addTeam( TeamDto team, MissionDto mission ) throws EntityNotFoundException {
        TeamDto result = null;
        try {
            result = getService().addTeam( team, mission );
        } catch( ServiceNotFoundException ex ) {
            Logger.getLogger( TeamLocatorServiceClient.class.getName() ).log( Level.SEVERE, null, ex );
        }
        return result;
    }

    @Override
    public List<TeamDto> getTeamByName( String name ) {
        List<TeamDto> result = new ArrayList<TeamDto>();
        try {
            result = getService().getTeamByName( name );
        } catch( ServiceNotFoundException ex ) {
            Logger.getLogger( TeamLocatorServiceClient.class.getName() ).log( Level.SEVERE, null, ex );
        }
        return result;
    }

    /* (non-Javadoc)
     * @see com.geocent.teamlocator.service.TeamLocatorService#removeTeam(com.geocent.teamlocator.dto.TeamDto, com.geocent.teamlocator.dto.MissionDto)
     */
    @Override
    public TeamDto removeTeam( TeamDto team, MissionDto mission ) throws EntityNotFoundException, InvalidMissionException {
        TeamDto updatedTeam = null;
        try {
            updatedTeam = getService().removeTeam( team, mission );
        } catch( ServiceNotFoundException e ) {
            Logger.getLogger( TeamLocatorServiceClient.class.getName() ).log( Level.SEVERE, null, e );
        }
        return updatedTeam;
    }

    /* (non-Javadoc)
     * @see com.geocent.teamlocator.service.TeamLocatorService#addMember(com.geocent.teamlocator.dto.MemberDto, com.geocent.teamlocator.dto.TeamDto)
     */
    @Override
    public MemberDto addMember( MemberDto member, TeamDto team ) throws EntityNotFoundException {
        MemberDto result = null;
        try {
            result = getService().addMember( member, team );
        } catch( ServiceNotFoundException e ) {
            Logger.getLogger( TeamLocatorServiceClient.class.getName() ).log( Level.SEVERE, null, e );
        }
        return result;
    }

    /* (non-Javadoc)
     * @see com.geocent.teamlocator.service.TeamLocatorService#getMembers(String, String, String)
     */
    @Override
    public List<MemberDto> getMembers( String lastName, String middleName, String firstName ) {
        List<MemberDto> members = new ArrayList<MemberDto>();
        try {
            members = getService().getMembers( lastName, middleName, firstName );
        } catch( ServiceNotFoundException e ) {
            Logger.getLogger( TeamLocatorServiceClient.class.getName() ).log( Level.SEVERE, null, e );
        }
        
        return members;
        
    }
    
    /* (non-Javadoc)
     * @see com.geocent.teamlocator.service.TeamLocatorService#reassignMember(com.geocent.teamlocator.dto.MemberDto, com.geocent.teamlocator.dto.TeamDto)
     */
    @Override
    public MemberDto reassignMember( MemberDto member, TeamDto team ) throws EntityNotFoundException {
        MemberDto result = null;
        try {
            result = getService().reassignMember( member, team );
        } catch( ServiceNotFoundException e ) {
            Logger.getLogger( TeamLocatorServiceClient.class.getName() ).log( Level.SEVERE, null, e );
        }
        return result;
    }

    /* (non-Javadoc)
     * @see com.geocent.teamlocator.service.TeamLocatorService#addMemberLocation(java.lang.Integer, com.geocent.teamlocator.dto.LocationDto)
     */
    @Override
    public void addMemberLocation( Integer memberId, LocationDto location )
            throws EntityNotFoundException {
        // TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see com.geocent.teamlocator.service.TeamLocatorService#getLastLocationForTeam(com.geocent.teamlocator.dto.TeamDto, int)
     */
    @Override
    public List<LocationDto> getLastLocationForTeam( TeamDto team, int maxRange ) throws EntityNotFoundException {
        try {
            return getService().getLastLocationForTeam( team, maxRange );
        } catch( ServiceNotFoundException snfe ) {
            Logger.getLogger( TeamLocatorServiceClient.class.getName() ).log( Level.SEVERE, null, snfe );
            return null;
        }
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

    private TeamLocatorService getService() throws ServiceNotFoundException {
        if( service == null ) {
            Properties props = getInitProperties();
            try {
                InitialContext ctx = new InitialContext( props );
                service = (TeamLocatorService) 
                        ctx.lookup( "java:global/TeamLocatorEar/TeamLocatorService-1.0-SNAPSHOT/TeamLocatorServiceBean!com.geocent.teamlocator.service.TeamLocatorService" );
//                    ctx.lookup( "java:global/com.geocent.teamlocator_TeamLocatorEar_ear_1.0-SNAPSHOT/TeamLocatorService-1.0-SNAPSHOT/TeamLocatorServiceBean!com.geocent.teamlocator.service.TeamLocatorService" );
            } catch( NamingException ex ) {
                Logger.getLogger( TeamLocatorServiceClient.class.getName() ).log( Level.SEVERE, null, ex );
                service = null;
            }
            if( service == null ) {
                throw new ServiceNotFoundException( "JNDI lookup failed for TeamLocatorService" );
            }
        }
        return service;
    }
    /*
     * This method is for TEST PURPOSES ONLY!! It must be removed
     */
    public static void main( String[] args ) {
        TeamLocatorServiceClient client = new TeamLocatorServiceClient();
        List<TeamDto> teams = client.getTeamByName( "Alpha" );
        if( teams != null && !teams.isEmpty() ) {
            System.out.println( "---->DEBUG: Found team: " + teams.get(0).getName() );
        } else {
            System.out.println( "---->DEBUG: Didn't get ANY teams back!!!" );
        }
    }

}
