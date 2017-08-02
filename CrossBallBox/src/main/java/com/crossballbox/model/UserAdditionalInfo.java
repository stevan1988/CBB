package com.crossballbox.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
@SuppressWarnings("serial")
public class UserAdditionalInfo implements Serializable {

//	@GeneratedValue(strategy=GenerationType.AUTO)
	@Id
	@Column(name = "id")
	private int id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@OneToOne
	@JoinColumn(name = "id")
	private UserInfo userInfo;

	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	@Column(name = "watherLiterPerDay")
	private double watherLiterPerDay;

	@Column(name = "timeOfMealPerDay")
	private int timeOfMealPerDay;

	@Column(name = "suplementsOrProtein")
	private boolean suplementsOrProtein;

	@Column(name = "suplementsOrProteinDescription")
	private String suplementsOrProteinDescription;

	@Column(name = "physicalActivity")
	private boolean physicalActivity;

	@Column(name = "physicalActivityDescription")
	private String physicalActivityDescription;

	public String getSuplementsOrProteinDescription() {
		return suplementsOrProteinDescription;
	}

	public void setSuplementsOrProteinDescription(String suplementsOrProteinDescription) {
		this.suplementsOrProteinDescription = suplementsOrProteinDescription;
	}

	public double getWatherLiterPerDay() {
		return watherLiterPerDay;
	}

	public void setWatherLiterPerDay(double watherLiterPerDay) {
		this.watherLiterPerDay = watherLiterPerDay;
	}

	public int getTimeOfMealPerDay() {
		return timeOfMealPerDay;
	}

	public void setTimeOfMealPerDay(int timeOfMealPerDay) {
		this.timeOfMealPerDay = timeOfMealPerDay;
	}

	public boolean isSuplementsOrProtein() {
		return suplementsOrProtein;
	}

	public void setSuplementsOrProtein(boolean suplementsOrProtein) {
		this.suplementsOrProtein = suplementsOrProtein;
	}

	public boolean isPhysicalActivity() {
		return physicalActivity;
	}

	public void setPhysicalActivity(boolean physicalActivity) {
		this.physicalActivity = physicalActivity;
	}

	public String getPhysicalActivityDescription() {
		return physicalActivityDescription;
	}

	public void setPhysicalActivityDescription(String physicalActivityDescription) {
		this.physicalActivityDescription = physicalActivityDescription;
	}

	public UserAdditionalInfo() {
	}

	public UserAdditionalInfo(int id, double watherLiterPerDay, int timeOfMealPerDay, boolean suplementsOrProtein,
			String suplementsOrProteinDescription, boolean physicalActivity, String physicalActivityDescription) {
		this.id = id;
		this.watherLiterPerDay = watherLiterPerDay;
		this.timeOfMealPerDay = timeOfMealPerDay;
		this.suplementsOrProtein = suplementsOrProtein;
		this.suplementsOrProteinDescription = suplementsOrProteinDescription;
		this.physicalActivity = physicalActivity;
		this.physicalActivityDescription = physicalActivityDescription;
	}

}
