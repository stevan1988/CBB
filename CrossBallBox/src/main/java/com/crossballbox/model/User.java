package com.crossballbox.model;

import java.io.Serializable;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@SuppressWarnings("serial")
@Cacheable(false)
public class User implements Serializable, Comparable<User> {

	@GeneratedValue
	@Id
	@Column(name = "id", nullable = false)
	private int id;

	@Column(name = "username", nullable = false, unique=true)
	private String username;
	@Column(name = "password", nullable = false)
	private String password;

	@Column(name = "first_name", nullable = false)
	private String firstName;
	@Column(name = "last_name", nullable = false)
	private String lastName;

	@Column(name = "e_mail", nullable = false)
	private String eMail;
	@Column(name = "enabled", nullable = false)
	private String enabled;

	@Column(name = "date_created")
	private Date dateCreated;
	@Column(name = "date_modified")
	private Date dateModified;

	@Column(name = "token")
	private String token;

	@Column(name = "role")
	private String role = "ROLE_USER";
	
	@Lob
	@Column(name = "user_info")
	UserInfo userInfo;
	
	@OneToOne(fetch = FetchType.EAGER)
	@PrimaryKeyJoinColumn
	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	@Column(name = "trainings", unique = true, nullable = false)
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "id")
	private Set<TrainingProgram> trainings = new HashSet<TrainingProgram>(0);

	//mozda na get anotacija?
	public Set<TrainingProgram> getTrainings() {
		return this.trainings;
	}

	public void setTrainings(Set<TrainingProgram> trainings) {
		this.trainings = trainings;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Boolean isAdmin() {
		return "ROLE_ADMIN".equals(role);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String geteMail() {
		return eMail;
	}

	public void seteMail(String eMail) {
		this.eMail = eMail;
	}

	public String getEnabled() {
		return enabled;
	}

	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Date getDateModified() {
		return dateModified;
	}

	public void setDateModified(Date dateModified) {
		this.dateModified = dateModified;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", eMail=" + eMail + ", enabled=" + enabled + ", dateCreated="
				+ dateCreated + ", dateModified=" + dateModified + ", role=" + role + "]";
	}

	@Override
	public int compareTo(User user) {
	        return this.getId() > user.getId() ? 1 : this.getId() < user.getId() ? -1 : 0;
	}

}
