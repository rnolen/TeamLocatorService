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
    public MissionDto addMission( MissionDto mission ) {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see com.geocent.teamlocator.service.TeamLocatorService#addTeam(com.geocent.teamlocator.dto.TeamDto, com.geocent.teamlocator.dto.MissionDto)
     */
    @Override
    public TeamDto addTeam( TeamDto team, MissionDto mission )
            throws EntityNotFoundException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<TeamDto> getTeamByName( String name ) {
        List<TeamDto> result = null;
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
    public TeamDto removeTeam( TeamDto team, MissionDto mission )
            throws EntityNotFoundException, InvalidMissionException {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see com.geocent.teamlocator.service.TeamLocatorService#addMember(com.geocent.teamlocator.dto.MemberDto, com.geocent.teamlocator.dto.TeamDto)
     */
    @Override
    public MemberDto addMember( MemberDto member, TeamDto team )
            throws EntityNotFoundException {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see com.geocent.teamlocator.service.TeamLocatorService#removeMember(com.geocent.teamlocator.dto.MemberDto, com.geocent.teamlocator.dto.TeamDto)
     */
    @Override
    public TeamDto removeMember( MemberDto member, TeamDto team )
            throws EntityNotFoundException {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see com.geocent.teamlocator.service.TeamLocatorService#getCurrentMission(com.geocent.teamlocator.dto.MemberDto)
     */
    @Override
    public MissionDto getCurrentMission( MemberDto member )
            throws EntityNotFoundException {
        // TODO Auto-generated method stub
        return null;
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
//        result.setProperty("org.omg.CORBA.ORBInitialHost", "localhost");
//        result.setProperty("org.omg.CORBA.ORBInitialPort", "3700");
        return result;
    }

    private TeamLocatorService getService() throws ServiceNotFoundException {
        
        Properties props = getInitProperties();
        try {
            InitialContext ctx = new InitialContext( props );
            service = (TeamLocatorService) 
                ctx.lookup( "java:global/com.geocent.teamlocator_TeamLocatorEar_ear_1.0-SNAPSHOT/TeamLocatorService-1.0-SNAPSHOT/TeamLocatorServiceBean!com.geocent.teamlocator.service.TeamLocatorService" );
        } catch( NamingException ex ) {
            Logger.getLogger( TeamLocatorServiceClient.class.getName() ).log( Level.SEVERE, null, ex );
            service = null;
        }
        if( service == null ) {
            throw new ServiceNotFoundException( "JNDI lookup failed for TeamLocatorService" );
        }
        
        return service;
    }
    /*
     * This method is for TEST PURPOSES ONLY!! It must be removed
     */
    public static void main( String[] args ) {
        TeamLocatorServiceClient client = new TeamLocatorServiceClient();
//        List<TeamDto> teams = client.getTeamByName( "Alpha" );
//        if( teams != null && !teams.isEmpty() ) {
//            System.out.println( "---->DEBUG: Found team: " + teams.get(0).getName() );
//        } else {
//            System.out.println( "---->DEBUG: Didn't get ANY teams back!!!" );
//        }
    }
}
