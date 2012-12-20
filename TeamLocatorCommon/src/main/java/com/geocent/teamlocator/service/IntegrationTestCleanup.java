/**
 * 
 */
package com.geocent.teamlocator.service;

import javax.ejb.Remote;

import com.geocent.teamlocator.dto.LocationDto;
import com.geocent.teamlocator.dto.MemberDto;
import com.geocent.teamlocator.dto.MissionDto;
import com.geocent.teamlocator.dto.TeamDto;
import com.geocent.teamlocator.exception.EntityNotFoundException;

/**
 * @author rnolen
 *
 */
@Remote
public interface IntegrationTestCleanup
{
    public void deleteMission( MissionDto mission ) throws EntityNotFoundException;
    public void deleteTeamMember( MemberDto member ) throws EntityNotFoundException;
    public void deleteTeam( TeamDto team ) throws EntityNotFoundException;
    public void deleteLocation( LocationDto location )  throws EntityNotFoundException;
}
