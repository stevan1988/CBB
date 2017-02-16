package com.crossballbox.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * 
 * @author steva
 *
 *         <p>
 *         class for tracking progress of work outs
 *         </p>
 */
@Entity
@SuppressWarnings("serial")
public class UserProgress implements Serializable {

	@GeneratedValue
	@Id
	@Column(name = "id")
	private int id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "userProgress", nullable = false)
	private UserInfo userInfo;

	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	@Column(name = "hight")
	private double hight;

	@Column(name = "weigth")
	private double weigth;

	@Column(name = "BMI")
	private String BMI;

	@Column(name = "fatPercentage")
	private double fatPercentage;

	@Column(name = "viscelar")
	private double viscelar;

	@Column(name = "waist")
	private double waist;

	@Column(name = "thigh")
	private double thigh;

	public double getHight() {
		return hight;
	}

	public void setHight(double hight) {
		this.hight = hight;
	}

	public double getWeigth() {
		return weigth;
	}

	public void setWeigth(double weigth) {
		this.weigth = weigth;
	}

	public String getBMI() {
		return BMI;
	}

	public void setBMI(String bMI) {
		BMI = bMI;
	}

	public double getFatPercentage() {
		return fatPercentage;
	}

	public void setFatPercentage(double fatPercentage) {
		this.fatPercentage = fatPercentage;
	}

	public double getViscelar() {
		return viscelar;
	}

	public void setViscelar(double viscelar) {
		this.viscelar = viscelar;
	}

	public double getWaist() {
		return waist;
	}

	public void setWaist(double waist) {
		this.waist = waist;
	}

	public double getThigh() {
		return thigh;
	}

	public void setThigh(double thigh) {
		this.thigh = thigh;
	}

}
