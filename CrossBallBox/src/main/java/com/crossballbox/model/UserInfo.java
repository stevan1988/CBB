package com.crossballbox.model;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.transaction.Transactional;

@Entity
@Transactional
@SuppressWarnings("serial")
public class UserInfo implements Serializable {

	@Id
//	@GeneratedValue
	@Column(name = "id", nullable = false)
	private int id;

	@Column(name = "imagePath")
	private String imagePath;

	@Lob
	@Column(name = "user_id", nullable = false)
	User user;

	@Column(name = "notes")
	private String notes;

	@Column(name = "date_birth")
	private Date dateBirth;

	@Column(name = "phone")
	private String phone;

	@Lob
	@Column(name = "changes")
	private List<UserProgress> changes;

	@Column(name = "gender")
	private Gender gender;

	@Column(name = "profession")
	private String profession;

	@Lob
	@OneToOne(mappedBy = "userInfo")
	private FamilyData familyData;

	@Lob
	@OneToOne(mappedBy = "userInfo")
	private UserHealthyState userHealthyState;

	@Lob
	@OneToOne(mappedBy = "userInfo")
	private UserAdditionalInfo userAdditionalInfo;

	@Column(name = "userProgress", unique = true, nullable = false)
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "id")
	private List<UserProgress> userProgressList;

	public FamilyData getFamilyData() {
		return familyData;
	}

	public void setFamilyData(FamilyData familyData) {
		this.familyData = familyData;
	}

	public UserHealthyState getUserHealthyState() {
		return userHealthyState;
	}

	public void setUserHealthyState(UserHealthyState userHealthyState) {
		this.userHealthyState = userHealthyState;
	}

	public UserAdditionalInfo getUserAdditionalInfo() {
		return userAdditionalInfo;
	}

	public void setUserAdditionalInfo(UserAdditionalInfo userAdditionalInfo) {
		this.userAdditionalInfo = userAdditionalInfo;
	}

	public List<UserProgress> getUserProgressList() {
		return userProgressList;
	}

	public void setUserProgressList(List<UserProgress> userProgressList) {
		this.userProgressList = userProgressList;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public List<UserProgress> getChanges() {
		return changes;
	}

	public void setChanges(List<UserProgress> changes) {
		this.changes = changes;
	}

	public Date getDateBirth() {
		return dateBirth;
	}

	public void setDateBirth(Date dateBirth) {
		this.dateBirth = dateBirth;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public String getProfession() {
		return profession;
	}

	public void setProfession(String profession) {
		this.profession = profession;
	}

	public int getId() {
		return id;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	@OneToOne(fetch = FetchType.EAGER, mappedBy = "user", cascade = CascadeType.ALL)
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public UserInfo(int id){
		this.id = id;
	}
	
	public UserInfo(){
	}
}
