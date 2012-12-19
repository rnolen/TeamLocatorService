/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.geocent.teamlocator.dto;

import java.io.Serializable;
import java.util.List;

import com.geocent.teamlocator.enums.MemberRank;
import com.geocent.teamlocator.enums.TeamRole;


/**
 *
 * @author rnolen
 */
public class MemberDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    private String firstName;

    private String lastName;

    private String middleName;

    private MemberRank rank;

    private TeamRole role;

    private List<LocationDto> locations;

    private TeamDto team;

    public Integer getId() {
        return id;
    }

    public void setId( Integer id ) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName( String firstName ) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName( String lastName ) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName( String middleName ) {
        this.middleName = middleName;
    }

    public MemberRank getRank() {
        return rank;
    }

    public void setRank( MemberRank rank ) {
        this.rank = rank;
    }

    public TeamRole getRole() {
        return role;
    }

    public void setRole( TeamRole role ) {
        this.role = role;
    }

    public List<LocationDto> getLocations() {
        return locations;
    }

    public void setLocations( List<LocationDto> locations ) {
        this.locations = locations;
    }

    public TeamDto getTeam() {
        return team;
    }

    public void setTeam( TeamDto team ) {
        this.team = team;
    }

    
}
