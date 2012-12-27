package com.geocent.teamdb.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the LOCATION database table.
 * 
 */
@Entity
public class Location extends com.geocent.util.jpa.Entity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATE_STAMP")
	private Date dateStamp;

	private int elevation;

	private double lattitude;

	private double longitude;

    //bi-directional many-to-one association to Member
    @ManyToOne
    private Member member;

    //bi-directional many-to-one association to Team
    @ManyToOne
    private Team team;

    //bi-directional many-to-one association to Mission
	@ManyToOne
	private Mission mission;

	public Location() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getDateStamp() {
		return this.dateStamp;
	}

	public void setDateStamp(Date dateStamp) {
		this.dateStamp = dateStamp;
	}

	public int getElevation() {
		return this.elevation;
	}

	public void setElevation(int elevation) {
		this.elevation = elevation;
	}

	public double getLattitude() {
		return this.lattitude;
	}

	public void setLattitude(double lattitude) {
		this.lattitude = lattitude;
	}

	public double getLongitude() {
		return this.longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public Member getMember() {
		return this.member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

    public Team getTeam() {
        return team;
    }

    public void setTeam( Team team ) {
        this.team = team;
    }

	public Mission getMission() {
		return this.mission;
	}

	public void setMission(Mission mission) {
		this.mission = mission;
	}

}