package com.geocent.teamlocator.mixare.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class LocationsMix implements Serializable
{
    private static final long serialVersionUID = 1L;

    private String status;
    private int num_results;
    private LocationMix[] results;
    
    public LocationsMix() {
        results = new LocationMix[0];
    }
    
    public String getStatus() {
        return status;
    }
    public void setStatus( String status ) {
        this.status = status;
    }
    public int getNum_results() {
        return num_results;
    }
    public void setNum_results( int num_results ) {
        this.num_results = num_results;
    }
    public LocationMix[] getResults() {
        return results;
    }

    public void setResults( LocationMix[] locations ) {
        this.results = locations;
    }
    
}
