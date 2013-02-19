package com.geocent.teamlocator.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.geocent.teamlocator.dto.LocationDto;
import com.geocent.teamlocator.dto.MemberDto;
import com.geocent.teamlocator.dto.MissionDto;
import com.geocent.teamlocator.dto.TeamDto;
import com.geocent.teamlocator.enums.MemberRank;
import com.geocent.teamlocator.enums.TeamRole;
import com.geocent.teamlocator.exception.EntityNotFoundException;
import com.geocent.teamlocator.mixare.model.LocationMix;
import com.geocent.teamlocator.mixare.model.LocationsMix;
import com.geocent.teamlocator.service.TeamLocatorService;

@Path("locator")
@Stateless
public class LocatorRESTResource
{
    @EJB
    private TeamLocatorService service;
    
    @GET
    @Path( "getTeamByName" )
    @Produces( {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML} )
    public List<TeamDto> getTeamByName( @QueryParam(value="name") String name ) {
        System.out.println( "---->DEBUG: LocatorRESTResource.getTeamByName: name=" + name );
        return service.getTeamByName( name );
    }
    
    @GET
    @Path( "getAllTeams" )
    @Produces( {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML} )
    public List<TeamDto> getAllTeams() {
        System.out.println( "---->DEBUG: LocatorRESTResource.getAllTeams" );
        return service.getAllTeams();
    }
    
    @GET
    @Path( "getLastLocationForTeam" )
    @Produces( MediaType.APPLICATION_JSON )
    public LocationsMix getLastLocationForTeam( @QueryParam(value="memberId") Integer memberId, 
                                               @QueryParam(value="maxRange") Integer maxRange ) {
        LocationsMix locationsMix = new LocationsMix();
        List<LocationDto> locations = new ArrayList<LocationDto>();
        
        try {
            locations = service.getLastLocationForTeam( memberId, maxRange );
            locationsMix = mapLocationsToMix( locations );
            
        } catch( EntityNotFoundException enfe ) {
            Logger.getLogger( LocatorRESTResource.class.getName() ).log( Level.SEVERE, null, enfe );
            locationsMix.setStatus( "Fail" );
        }
        return locationsMix;
    }
    
    @POST
    @Path("addTeamMember")
    @Produces( MediaType.APPLICATION_JSON )
    public MemberDto addTeamMember( @FormParam("teamName") String teamName, @FormParam("firstName") String firstName,
                               @FormParam("middleName") String middleName, @FormParam("lastName") String lastName,
                               @FormParam("rank") String rank, @FormParam( "role") String role ) throws EntityNotFoundException {
        System.out.println( "---->DEBUG: addTeamMember: teamName=" + teamName + ", memberName=" + lastName + ", " + firstName + " " + middleName + 
                            ", " + rank + ", " + role );
        
        // Get the team by name
        List<TeamDto> teams = service.getTeamByName( teamName );
        TeamDto team = teams.get( 0 );
        
        // Let's see if this member already exists
        List<MemberDto> members = service.getMembers( lastName, middleName, firstName );
        MemberDto member = new MemberDto();
        if( !members.isEmpty() ) {
            member = members.get( 0 );
        }
        member.setFirstName( firstName );
        member.setLastName( lastName );
        member.setMiddleName( middleName );
        member.setRank( MemberRank.valueOf( rank.toUpperCase() ) );
        if( role != null && !role.isEmpty() ) {
            member.setRole( TeamRole.valueOf( role.toUpperCase() ) );
        } else {
            member.setRole( TeamRole.MEMBER );
        }
        member.setTeam( team );
        service.addMember( member, team );
        return member;
    }
    
    @POST
    @Path("addLocationForMember")
    @Produces( MediaType.APPLICATION_JSON )
    public LocationDto addLocationForMember( @FormParam("memberId") String memberId, @FormParam("lattitude") String lattitude,
                                             @FormParam("longitude") String longitude ) throws EntityNotFoundException {
        // find the member first
        MemberDto member = service.getMemberById( Integer.valueOf(memberId) );
        // get the currently active mission for the team
        MissionDto mission = service.getCurrentMission( member ).get( 0 );
        
        // Now create a location for the member
        LocationDto location = new LocationDto();
        location.setDateStamp( new Date() );
        location.setMember( member );
        location.setLattitude( Double.valueOf( lattitude ) );
        location.setLongitude( Double.valueOf( longitude ) );
        location.setTeam( member.getTeam() );
        location.setMission( mission );
        
        // Add the location - the returned value will have the location Id populated
        location = service.addMemberLocation( location );
        
        return location;
    }

    /**
     * Creates a JSON string using the passed list of LocationDto
     * @param locations
     * @return
     * @deprecated - shouldn't need this but I'm not removing it yet
     */
    private String createReturnString( List<LocationDto> locations ) {
        StringBuilder sb = new StringBuilder();
        sb.append( "{\"status\": \"OK\"," );
        sb.append( "\"num_results\": \"" + locations.size() + "\",");
        sb.append( "\"results\": [");
        int i = 0;
        for( LocationDto location : locations ) {
            sb.append( "{\"id\": \"" + location.getId() +"\",");
            sb.append( "\"lat\": \"" + location.getLattitude() +"\",");
            sb.append( "\"lng\": \"" + location.getLongitude() +"\",");
            sb.append( "\"elevation\": \"" + location.getElevation() +"\",");
            sb.append( "\"title\": \"" + location.getMember().getLastName() +"\",");
            sb.append( "\"distance\": \"" + location.getRange() +"\"}");
            if( i++ < locations.size()-1 ) {
                sb.append( ",");
            }
        }
        sb.append( "]}" );
        return sb.toString();
    }

    /**
     * Map the list of LocationDto objects objects that the Mixare framework can work with
     * 
     * @param locations
     * @return
     */
    private LocationsMix mapLocationsToMix( List<LocationDto> locations ) {
        LocationMix[] locationMixArray = new LocationMix[locations.size()];
        LocationsMix locationsMix = new LocationsMix();
        locationsMix.setStatus( "OK" );
        locationsMix.setNum_results( locations.size() );
        locationsMix.setResults( locationMixArray );
        LocationMix locationMix;
        int i=0;
        for( LocationDto location : locations ) {
            locationMix = new LocationMix();
            locationMix.setId( location.getId() );
            locationMix.setLat( location.getLattitude() );
            locationMix.setLng( location.getLongitude() );
            locationMix.setElevation( location.getElevation() );
            locationMix.setDistance( location.getRange() );
            // The first item in the list should be the objective
            if( i == 0 ) {
                locationMix.setTitle( "Objective=" + location.getMission().getObjective().getDescription() );
            } else {
                locationMix.setTitle( location.getMember().getLastName() );
            }
            locationMixArray[i++] = locationMix;
            System.out.println( "---->DEBUG: location for: " + locationMix.getTitle() + ": lat=" + locationMix.getLat() + ", lng=" + locationMix.getLng() );
        }
        return locationsMix;
        
    }
}
