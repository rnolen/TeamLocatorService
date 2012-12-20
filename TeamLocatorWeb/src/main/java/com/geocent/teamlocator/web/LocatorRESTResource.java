package com.geocent.teamlocator.web;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.geocent.teamlocator.dto.TeamDto;
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
}
