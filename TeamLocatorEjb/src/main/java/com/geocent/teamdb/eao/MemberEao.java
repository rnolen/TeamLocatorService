/**
 * 
 */
package com.geocent.teamdb.eao;

import java.util.List;

import com.geocent.teamdb.entity.Member;
import com.geocent.teamlocator.enums.TeamRole;

/**
 * @author rnolen
 *
 */
public interface MemberEao
{
    /**
     * Get Member by key
     * @param key
     * @return Member
     */
    public Member getMember( int key );
    
    /**
     * Get list of all members
     * @return List of Members
     */
    public List<Member> getMembers();
    
    /**
     * Get list of all members who are assigned the passed role.
     * @param role
     * @return List of Members
     */
    public List<Member> getMembers( TeamRole role );
    
    /**
     * Add passed member to the database
     * @param memberToAdd
     * @return updated Member 
     */
    public Member addMember( Member memberToAdd );
    
    /**
     * Update the member record passed
     * @param memberToUpdate
     * @return Updated Member
     */
    public Member updateMember( Member memberToUpdate );
}
