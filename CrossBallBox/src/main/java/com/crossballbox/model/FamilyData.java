package com.crossballbox.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
@SuppressWarnings("serial")
public class FamilyData implements Serializable {

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
	
	@OneToOne
	@JoinColumn(name = "id")
	private UserInfo userInfo;

	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}
	
	@Column(name = "diabetes")
	private boolean diabetes;

	@Column(name = "obesity")
	private boolean obesity;

	@Column(name = "cardioIllnes")
	private boolean cardioIllnes;

	public boolean isDiabetes() {
		return diabetes;
	}

	public void setDiabetes(boolean diabetes) {
		this.diabetes = diabetes;
	}

	public boolean isObesity() {
		return obesity;
	}

	public void setObesity(boolean obesity) {
		this.obesity = obesity;
	}

	public boolean isCardioIllnes() {
		return cardioIllnes;
	}

	public void setCardioIllnes(boolean cardioIllnes) {
		this.cardioIllnes = cardioIllnes;
	}
	
	public FamilyData(){};
	
	public FamilyData(int id, boolean diabetes, boolean obesity, boolean cardioIllnes){
		this.id = id;
		this.diabetes = diabetes;
		this.obesity = obesity;
		this.cardioIllnes = cardioIllnes;
	};

}
