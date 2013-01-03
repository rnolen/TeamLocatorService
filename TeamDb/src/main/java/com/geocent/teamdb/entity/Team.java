package com.geocent.teamdb.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the TEAM database table.
 * 
 */
@Entity
public class Team extends com.geocent.teamdb.entity.AbstractEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	private String name;

	//bi-directional many-to-one association to Member
	@OneToMany(mappedBy="team")
	private List<Member> members;

	//bi-directional many-to-one association to MissionTeam
	@OneToMany(mappedBy="team")
	private List<MissionTeam> missionTeams;

	public Team() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Member> getMembers() {
		return this.members;
	}

	public void setMembers(List<Member> members) {
		this.members = members;
	}

	public List<MissionTeam> getMissionTeams() {
		return this.missionTeams;
	}

	public void setMissionTeams(List<MissionTeam> missionTeams) {
		this.missionTeams = missionTeams;
	}

}