/**
 * 
 */
package com.geocent.teamdb.util;

import com.geocent.teamdb.entity.Location;
import com.geocent.teamdb.entity.Member;
import com.geocent.teamdb.entity.Mission;
import com.geocent.teamdb.entity.Objective;
import com.geocent.teamdb.entity.Team;
import com.geocent.teamlocator.dto.LocationDto;
import com.geocent.teamlocator.dto.MemberDto;
import com.geocent.teamlocator.dto.MissionDto;
import com.geocent.teamlocator.dto.ObjectiveDto;
import com.geocent.teamlocator.dto.TeamDto;
import com.geocent.teamlocator.enums.MemberRank;
import com.geocent.teamlocator.enums.TeamRole;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 * @author rnolen
 *
 */
@Stateless
@LocalBean
public class Converter
{
    public ObjectiveDto fromEntity( Objective objective ) {
        ObjectiveDto objectiveDto = null;
        if( objective != null ) {
            objectiveDto = new ObjectiveDto();
            objectiveDto.setId( objective.getId() );
            objectiveDto.setDescription( objective.getDescription() );
            objectiveDto.setLatitude( objective.getLatitude() );
            objectiveDto.setLongitude( objective.getLongitude() );
        }
        
        return objectiveDto;
    }
    
    public MissionDto fromEntity( Mission mission ) {
        if( mission == null ) {
            return null;
        }
        MissionDto missionDto = new MissionDto();
        missionDto.setId( mission.getId() );
        boolean completed = false;
        if( mission.getCompleted() != null ) {
            completed = (mission.getCompleted().toUpperCase() == "Y" ? true : false );
        }
        missionDto.setCompleted( completed );
        missionDto.setObjective( fromEntity( mission.getObjective() ) );
        missionDto.setDescription( mission.getDescription() );
        missionDto.setMissionDate( mission.getMissionDate() );
        
        return missionDto;
    }

    
    public TeamDto fromEntity( Team team ) {
        if( team == null ) {
            return null;
        }
        TeamDto teamDto = new TeamDto();
        teamDto.setId( team.getId() );
        teamDto.setName( team.getName() );
        return teamDto;
    }
    
    public MemberDto fromEntity( Member member ) {
        if( member == null ) {
            return null;
        }
        MemberDto memberDto = new MemberDto();
        memberDto.setId( member.getId() );
        memberDto.setFirstName( member.getFirstName() );
        memberDto.setMiddleName( member.getMiddleName() );
        memberDto.setLastName( member.getLastName() );
        memberDto.setRank( MemberRank.valueOf(member.getRank()) );
        memberDto.setRole( TeamRole.valueOf( member.getRole()) );
        memberDto.setTeam( fromEntity(member.getTeam()) );
        
        return memberDto;
    }

    public LocationDto fromEntity( Location location ) {
        if( location == null ) {
            return null;
        }
        LocationDto dto = new LocationDto();
        dto.setId(  location.getId() );
        dto.setDateStamp( location.getDateStamp() );
        dto.setElevation( location.getElevation() );
        dto.setLattitude( location.getLattitude() );
        dto.setLongitude( location.getLongitude() );
        dto.setMember( fromEntity( location.getMember()) );
        dto.setTeam( fromEntity(location.getTeam()) );
        dto.setMission( fromEntity(location.getMission()) );
        return dto;
    }
    
    public Objective toEntity( ObjectiveDto objectiveDto ) {
        if( objectiveDto == null ) {
            return null;
        }
        Objective objective = new Objective();
        objective.setId( objectiveDto.getId() );
        objective.setDescription( objectiveDto.getDescription() );
        objective.setLatitude( objectiveDto.getLatitude() );
        objective.setLongitude( objectiveDto.getLongitude() );
        
        return objective;
        
    }

    public Mission toEntity( MissionDto missionDto ) {
        if( missionDto == null ) {
            return null;
        }
        Mission mission = new Mission();
        mission.setId( missionDto.getId() );
        mission.setDescription( missionDto.getDescription() );
        mission.setCompleted( (missionDto.isCompleted() ? "Y" : "N") );
        mission.setObjective( toEntity( missionDto.getObjective() ) );
        mission.setMissionDate( missionDto.getMissionDate() );
        
        return mission;
    }

    public Team toEntity( TeamDto teamDto ) {
        if( teamDto == null ) {
            return null;
        }
        Team team = new Team();
        team.setId( teamDto.getId() );
        team.setName( teamDto.getName() );
        return team;
    }

    public Member toEntity( MemberDto memberDto ) {
        if( memberDto == null ) {
            return null;
        }
        Member member = new Member();
        member.setId( memberDto.getId() );
        member.setFirstName( memberDto.getFirstName() );
        member.setMiddleName( memberDto.getMiddleName() );
        member.setLastName( memberDto.getLastName() );
        member.setRank( memberDto.getRank().toString() );
        member.setRole( memberDto.getRole().toString() );
        member.setTeam( toEntity(memberDto.getTeam()) );
        
        return member;
    }
    
    public Location toEntity( LocationDto locationDto ) {
        if( locationDto == null ) {
            return null;
        }
        Location location = new Location();
        location.setId(  locationDto.getId() );
        location.setDateStamp( locationDto.getDateStamp() );
        location.setElevation( locationDto.getElevation() );
        location.setLattitude( locationDto.getLattitude() );
        location.setLongitude( locationDto.getLongitude() );
        location.setMember( toEntity( locationDto.getMember()) );
        location.setTeam( toEntity(locationDto.getTeam()) );
        location.setMission( toEntity(locationDto.getMission()) );
        return location;
    }
}
