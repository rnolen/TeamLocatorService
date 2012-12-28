/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.geocent.teamlocator.dto;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author rnolen
 */
@XmlRootElement
public class LocationDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    private Date dateStamp;

    private double lattitude;

    private double longitude;
    
    private int elevation;
    
    private boolean noRecentUpdate;

    private MemberDto member;
    
    private TeamDto team;

    private MissionDto mission;
    
    /**
     * range is a non-persisted value and carries the distance from some other location
     */
    private double range;

    public Integer getId() {
        return id;
    }

    public void setId( Integer id ) {
        this.id = id;
    }

    public Date getDateStamp() {
        return dateStamp;
    }

    public void setDateStamp( Date dateStamp ) {
        this.dateStamp = dateStamp;
    }

    public double getLattitude() {
        return lattitude;
    }

    public void setLattitude( double lattitude ) {
        this.lattitude = lattitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude( double longitude ) {
        this.longitude = longitude;
    }

    public int getElevation() {
        return elevation;
    }

    public void setElevation( int elevation ) {
        this.elevation = elevation;
    }

    public boolean isNoRecentUpdate() {
        return noRecentUpdate;
    }

    public void setNoRecentUpdate( boolean noRecentUpdate ) {
        this.noRecentUpdate = noRecentUpdate;
    }

    public MemberDto getMember() {
        return member;
    }

    public void setMember( MemberDto member ) {
        this.member = member;
    }

    public MissionDto getMission() {
        return mission;
    }

    public void setMission( MissionDto mission ) {
        this.mission = mission;
    }

    public TeamDto getTeam() {
        return team;
    }

    public void setTeam( TeamDto team ) {
        this.team = team;
    }

    public double getRange() {
        return range;
    }

    public void setRange( double range ) {
        this.range = range;
    }
    
}
