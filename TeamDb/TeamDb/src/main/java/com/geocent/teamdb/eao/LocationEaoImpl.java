package com.geocent.teamdb.eao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Query;

import com.geocent.teamdb.entity.Location;
import com.geocent.teamlocator.dto.LocationDto;
import com.geocent.teamlocator.dto.MemberDto;
import com.geocent.teamlocator.dto.MissionDto;
import com.geocent.teamlocator.dto.TeamDto;
import com.geocent.teamlocator.exception.EntityNotFoundException;

/**
 * Session Bean implementation class LocationEaoImpl
 */
@Stateless
@LocalBean
public class LocationEaoImpl extends AbstractEao implements LocationEao {

    /**
     * Default constructor. 
     */
    public LocationEaoImpl() {
        // TODO Auto-generated constructor stub
    }

    @Override
    public List<LocationDto> getLocations( MemberDto member ) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<LocationDto> getLocations( TeamDto team ) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<LocationDto> getLocations( MissionDto mission ) {
        // TODO Auto-generated method stub
        return null;
    }

    @SuppressWarnings( "unchecked" )
    @Override
    public LocationDto getLastLocation( MemberDto member, MissionDto mission ) {
        String queryString = "SELECT * FROM location where member_id = ? AND mission_id = ? ORDER BY date_stamp DESC";
        Query query = em.createNativeQuery( queryString, Location.class );
        query.setParameter( 1,  member.getId() );
        query.setParameter( 2,  mission.getId() );
        List<Location> result = (List<Location>) query.getResultList();
        if( result == null || result.isEmpty() ) {
            return null;
        }
        LocationDto lastLoc = converter.fromEntity( result.get( 0 ) );
        return lastLoc;
    }

    @Override
    public LocationDto addLocation( LocationDto locationDto ) {
        Location location = converter.toEntity( locationDto );
        location = this.persist( location );
        LocationDto result = converter.fromEntity( location );
        return result;
    }

}
