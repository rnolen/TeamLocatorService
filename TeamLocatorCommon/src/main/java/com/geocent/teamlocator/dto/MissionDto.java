/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.geocent.teamlocator.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author rnolen
 */
@XmlRootElement
public class MissionDto implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;

	private boolean completed;
	
	private String description;

    private Date missionDate;

    private ObjectiveDto objective;

    private List<TeamDto> missionTeams;
    
    private List<LocationDto> locations;
    

	public Integer getId() {
        return id;
    }

    public void setId( Integer id ) {
        this.id = id;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted( boolean completed ) {
        this.completed = completed;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription( String description ) {
        this.description = description;
    }

    public Date getMissionDate() {
        return missionDate;
    }

    public void setMissionDate( Date missionDate ) {
        this.missionDate = missionDate;
    }

    public ObjectiveDto getObjective() {
        return objective;
    }

    public void setObjective( ObjectiveDto objective ) {
        this.objective = objective;
    }

    public List<TeamDto> getMissionTeams() {
        return missionTeams;
    }

    public void setMissionTeams( List<TeamDto> missionTeams ) {
        this.missionTeams = missionTeams;
    }

    public List<LocationDto> getLocations() {
        return locations;
    }

    public void setLocations( List<LocationDto> locations ) {
        this.locations = locations;
    }

}
