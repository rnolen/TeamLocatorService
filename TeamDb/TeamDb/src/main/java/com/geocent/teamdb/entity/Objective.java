package com.geocent.teamdb.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the OBJECTIVE database table.
 * 
 */
@Entity
public class Objective extends com.geocent.teamdb.entity.AbstractEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	private String description;

	private double latitude;

	private double longitude;

	//bi-directional many-to-one association to Mission
//	@OneToMany(mappedBy="objective")
//	private List<Mission> missions;

	public Objective() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getLatitude() {
		return this.latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return this.longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

//	public List<Mission> getMissions() {
//		return this.missions;
//	}
//
//	public void setMissions(List<Mission> missions) {
//		this.missions = missions;
//	}

}