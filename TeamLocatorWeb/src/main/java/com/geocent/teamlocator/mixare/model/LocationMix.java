package com.geocent.teamlocator.mixare.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class LocationMix implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    private int id;
    private double lat;
    private double lng;
    private int elevation;
    private String title;
    private double distance;
    
    public int getId() {
        return id;
    }
    public void setId( int id ) {
        this.id = id;
    }
    public double getLat() {
        return lat;
    }
    public void setLat( double lat ) {
        this.lat = lat;
    }
    public double getLng() {
        return lng;
    }
    public void setLng( double lng ) {
        this.lng = lng;
    }
    public int getElevation() {
        return elevation;
    }
    public void setElevation( int elevation ) {
        this.elevation = elevation;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle( String title ) {
        this.title = title;
    }
    public double getDistance() {
        return distance;
    }
    public void setDistance( double distance ) {
        this.distance = distance;
    }
    
    
}
