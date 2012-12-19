package com.geocent.teamdb.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the MISSION database table.
 * 
 */
@Entity
public class Mission extends com.geocent.util.jpa.Entity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	private String completed;

	private String description;

	@Temporal(TemporalType.DATE)
	@Column(name="MISSION_DATE")
	private Date missionDate;

	//bi-directional many-to-one association to Location
	@OneToMany(mappedBy="mission")
	private List<Location> locations;

	//bi-directional many-to-one association to Objective
	@ManyToOne
	private Objective objective;

	//bi-directional many-to-one association to MissionTeam
	@OneToMany(mappedBy="mission")
	private List<MissionTeam> missionTeams;

	public Mission() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCompleted() {
		return this.completed;
	}

	public void setCompleted(String completed) {
		this.completed = completed;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getMissionDate() {
		return this.missionDate;
	}

	public void setMissionDate(Date missionDate) {
		this.missionDate = missionDate;
	}

	public List<Location> getLocations() {
		return this.locations;
	}

	public void setLocations(List<Location> locations) {
		this.locations = locations;
	}

	public Objective getObjective() {
		return this.objective;
	}

	public void setObjective(Objective objective) {
		this.objective = objective;
	}

	public List<MissionTeam> getMissionTeams() {
		return this.missionTeams;
	}

	public void setMissionTeams(List<MissionTeam> missionTeams) {
		this.missionTeams = missionTeams;
	}

}