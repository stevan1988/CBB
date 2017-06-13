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
public class UserHealthyState implements Serializable {

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

	@Column(name = "cardioIllness")
	private boolean cardioIllness;

	@Column(name = "metabolicIllness")
	private boolean metabolicIllness;

	@Column(name = "otherHealthyIssues")
	private boolean otherHealthyIssues;

	@Column(name = "medicamentsConsumer")
	private boolean medicamentsConsumer;

	@Column(name = "injury")
	private boolean injury;

	public boolean isCardioIllness() {
		return cardioIllness;
	}

	public void setCardioIllness(boolean cardioIllness) {
		this.cardioIllness = cardioIllness;
	}

	public boolean isMetabolicIllness() {
		return metabolicIllness;
	}

	public void setMetabolicIllness(boolean metabolicIllness) {
		this.metabolicIllness = metabolicIllness;
	}

	public boolean isOtherHealthyIssues() {
		return otherHealthyIssues;
	}

	public void setOtherHealthyIssues(boolean otherHealthyIssues) {
		this.otherHealthyIssues = otherHealthyIssues;
	}

	public boolean isMedicamentsConsumer() {
		return medicamentsConsumer;
	}

	public void setMedicamentsConsumer(boolean medicamentsConsumer) {
		this.medicamentsConsumer = medicamentsConsumer;
	}

	public boolean isInjury() {
		return injury;
	}

	public void setInjury(boolean injury) {
		this.injury = injury;
	}

	public UserHealthyState() {

	}
	
	public UserHealthyState(int id) {
	  this.id = id;
    }

	public UserHealthyState(int id, boolean cardioIllness, boolean metabolicIllness, boolean otherHealthyIssues,
			boolean medicamentsConsumer, boolean injury) {
		this.id = id;
		this.cardioIllness = cardioIllness;
		this.metabolicIllness = metabolicIllness;
		this.otherHealthyIssues = otherHealthyIssues;
		this.medicamentsConsumer = medicamentsConsumer;
		this.injury = injury;
	}

}
