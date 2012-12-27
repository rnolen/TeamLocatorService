package com.geocent.teamdb.eao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.geocent.teamdb.entity.Member;
import com.geocent.teamdb.entity.Team;
import com.geocent.teamlocator.dto.MemberDto;
import com.geocent.teamlocator.dto.TeamDto;
import com.geocent.teamlocator.enums.TeamRole;
import com.geocent.teamlocator.exception.EntityNotFoundException;

/**
 * Session Bean implementation class MemberEaoImpl
 */
@Stateless
@LocalBean
public class MemberEaoImpl extends AbstractEao implements MemberEao {

    /**
     * Default constructor. 
     */
    public MemberEaoImpl() {
        // TODO Auto-generated constructor stub
    }

    @Override
    public MemberDto getMember( int key ) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<MemberDto> getMembers() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<MemberDto> getMembers( TeamRole role ) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<MemberDto> getMembers( TeamDto team )
            throws EntityNotFoundException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<MemberDto> getMembers( String lastName, String middleName,
            String firstName ) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public MemberDto addMember( MemberDto memberToAdd ) throws EntityNotFoundException {
        MemberDto result = null;
        Member member = converter.toEntity( memberToAdd );
        Team team = converter.toEntity( memberToAdd.getTeam() );
        // Verify that the team Id at least looks valid
        if( !team.hasId() ) {
            throw new EntityNotFoundException( team.getClass(), team.getId() );
        }
        member.setTeam( team );
        persist( member );
        TeamDto teamDto = converter.fromEntity( member.getTeam() );
        result = converter.fromEntity( member );
        result.setTeam( teamDto );
        return result;
    }

    @Override
    public MemberDto updateMember( MemberDto memberToUpdate ) {
        // TODO Auto-generated method stub
        return null;
    }

}
