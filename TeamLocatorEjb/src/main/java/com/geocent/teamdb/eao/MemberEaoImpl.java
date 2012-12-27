package com.geocent.teamdb.eao;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Query;

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

    @SuppressWarnings( "unchecked" )
    @Override
    public List<MemberDto> getMembers( String lastName, String middleName, String firstName ) {
        List<MemberDto> memberDtos = new ArrayList<MemberDto>();
        String queryString = createQueryString( lastName, middleName, firstName );
        Query query = em.createQuery( queryString );
        setParameter( query, "lastName", lastName );
        setParameter( query, "middleName", middleName );
        setParameter( query, "firstName", firstName );
        System.out.println( "---->DEBUG: MemberEao.getMembers query string=" + queryString );
        List<Member> members = (List<Member>) query.getResultList();
        for( Member member : members ) {
            MemberDto dto = converter.fromEntity( member );
            TeamDto teamDto = converter.fromEntity( member.getTeam() );
            dto.setTeam( teamDto );
            memberDtos.add( dto );
        }
        return memberDtos;
    }

    private void setParameter( Query query, String fieldName, String value ) {
        System.out.println( "---->DEBUG: Setting parameter " + fieldName + " to value " + value );
        if( value != null && !value.isEmpty() ) {
            query.setParameter( fieldName, value.toUpperCase() );
        }
    }

    @Override
    public MemberDto saveMember( MemberDto memberToAdd ) throws EntityNotFoundException {
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

    private String createQueryString( String lastName, String middleName, String firstName ) {
        boolean lastNameOk = lastName != null && !lastName.isEmpty();
        boolean middleNameOk = middleName != null && !middleName.isEmpty();
        boolean firstNameOk = firstName != null && !firstName.isEmpty();
        StringBuilder sb = new StringBuilder();
        sb.append( "SELECT m from Member m " );
        if( lastNameOk || middleNameOk || firstNameOk ) {
            sb.append(  "WHERE"  );
            if( lastNameOk ) {
                sb.append( " upper(m.lastName) = :lastName" );
            }
            if( middleNameOk ) {
                if( lastNameOk ) {
                    sb.append( " AND"  );
                }
                sb.append( " upper(m.middleName) = :middleName" );
            }
            if( firstNameOk ) {
                if( lastNameOk || middleNameOk ) {
                    sb.append( " AND" );
                }
                sb.append( " upper(m.firstName) = :firstName" );
            }
        }
        return sb.toString();
        
    }
}
