/**
 * 
 */
package com.geocent.teamdb.eao;

import java.util.List;

import com.geocent.teamdb.entity.Location;
import com.geocent.teamdb.entity.Member;
import com.geocent.teamdb.entity.Mission;
import com.geocent.teamdb.entity.Team;

/**
 * @author rnolen
 *
 */
public interface LocationEao
{
    /**
     * Return all locations that were recorded for the passed member
     * @param member
     * @return List of Locations
     */
    public List<Location> getLocations( Member member );
    
    /**
     * Return all locations for all members of the passed team
     * @param team
     * @return List of Locations
     */
    public List<Location> getLocations( Team team );
    
    /**
     * Return all locations for all members of teams that were on the passed mission
     * @param mission
     * @return List of Locations
     */
    public List<Location> getLocations( Mission mission );
    
    /**
     * Gets the last recorded location for the passed team member
     * @param member
     * @return Location
     */
    public Location getLastLocation( Member member );
    
    /**
     * Adds the passed Location to the database
     * @param location
     * @return the updated Location
     */
    public Location addLocation( Location location );

}
