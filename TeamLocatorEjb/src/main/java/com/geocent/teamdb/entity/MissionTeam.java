package com.geocent.teamdb.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the MISSION_TEAMS database table.
 * 
 */
@Entity
@Table(name="MISSION_TEAMS")
public class MissionTeam extends com.geocent.util.jpa.Entity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	//bi-directional many-to-one association to Mission
	@ManyToOne
	private Mission mission;

	//bi-directional many-to-one association to Team
	@ManyToOne
	private Team team;

	public MissionTeam() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Mission getMission() {
		return this.mission;
	}

	public void setMission(Mission mission) {
		this.mission = mission;
	}

	public Team getTeam() {
		return this.team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

}