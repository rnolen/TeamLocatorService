/**
 * 
 */
package com.geocent.teamdb.eao;

import java.util.List;

import javax.ejb.Remote;

import com.geocent.teamlocator.dto.LocationDto;
import com.geocent.teamlocator.dto.MemberDto;
import com.geocent.teamlocator.dto.MissionDto;
import com.geocent.teamlocator.dto.TeamDto;

/**
 * @author rnolen
 *
 */
@Remote
public interface LocationEao extends Eao
{
    /**
     * Return all locations that were recorded for the passed member
     * @param member
     * @return List of Locations
     */
    public List<LocationDto> getLocations( MemberDto member );
    
    /**
     * Return all locations for all members of the passed team
     * @param team
     * @return List of Locations
     */
    public List<LocationDto> getLocations( TeamDto team );
    
    /**
     * Return all locations for all members of teams that were on the passed mission
     * @param mission
     * @return List of Locations
     */
    public List<LocationDto> getLocations( MissionDto mission );
    
    /**
     * Gets the last recorded location for the passed team member
     * @param member
     * @param mission
     * @return Location
     */
    public LocationDto getLastLocation( MemberDto member, MissionDto mission );
    
    /**
     * Adds the passed Location to the database
     * @param location
     * @return the updated Location
     */
    public LocationDto addLocation( LocationDto location );

}
