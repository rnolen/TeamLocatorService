/**
 * 
 */
package com.geocent.teamdb.eao;

import java.util.List;

import javax.ejb.Remote;

import com.geocent.teamlocator.dto.MemberDto;
import com.geocent.teamlocator.dto.TeamDto;
import com.geocent.teamlocator.enums.TeamRole;
import com.geocent.teamlocator.exception.EntityNotFoundException;

/**
 * @author rnolen
 *
 */
@Remote
public interface MemberEao extends Eao
{
    /**
     * Get Member by key
     * @param key
     * @return Member
     */
    public MemberDto getMember( int key );
    
    /**
     * Get list of all members
     * @return List of Members
     */
    public List<MemberDto> getMembers();
    
    /**
     * Get list of all members who are assigned the passed role.
     * @param role
     * @return List of Members
     */
    public List<MemberDto> getMembers( TeamRole role );
    
    /**
     * Get list of all members assigned to the passed team
     * @param team
     * @return List of MemberDto objects
     * @throws EntityNotFoundException if the Team can't be found
     */
    public List<MemberDto> getMembers( TeamDto team ) throws EntityNotFoundException;

    /**
     * Get members by name
     * @param lastName
     * @param middleName
     * @param firstName
     * @return List of MemberDto objects matching the input criteria
     */
    public List<MemberDto> getMembers( String lastName, String middleName, String firstName );
    
    /**
     * Add passed member to the database.
     * @param memberToAdd
     * @return updated Member
     * @throws EntityNotFoundException if the TeamDto held by the MemberDto is invalid. 
     */
    public MemberDto addMember( MemberDto memberToAdd ) throws EntityNotFoundException;
    
    /**
     * Update the member record passed
     * @param memberToUpdate
     * @return Updated Member
     */
    public MemberDto updateMember( MemberDto memberToUpdate );
}
