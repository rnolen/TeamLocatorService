/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.geocent.teamlocator.dto;

import java.io.Serializable;
import java.util.List;


/**
 *
 * @author rnolen
 */
public class ObjectiveDto implements Serializable {
    
    private static final long serialVersionUID = 1L;

    private Integer id;

    private String description;

    private double latitude;

    private double longitude;

    private List<MissionDto> missions;

    public Integer getId() {
        return id;
    }

    public void setId( Integer id ) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription( String description ) {
        this.description = description;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude( double latitude ) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude( double longitude ) {
        this.longitude = longitude;
    }

    public List<MissionDto> getMissions() {
        return missions;
    }

    public void setMissions( List<MissionDto> missions ) {
        this.missions = missions;
    }
    
    
}
