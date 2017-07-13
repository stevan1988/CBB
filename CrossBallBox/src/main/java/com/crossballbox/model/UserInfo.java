package com.crossballbox.model;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.transaction.Transactional;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Transactional
@Cacheable(false)
public class UserInfo implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 5089797229581520127L;

  @Id
  @GeneratedValue
  @Column(name = "id", nullable = false)
  private int id;

  @Column(name = "imagePath")
  private String imagePath;

  @Lob
  @Column(name = "user_id", nullable = false)
  User user;

  @Column(name = "notes")
  private String notes;

  @Column(name = "training")
  private Programs training;

  @Column(name = "date_birth")
  @DateTimeFormat(pattern = "dd/MM/yyyy")
  private LocalDate dateBirth;

  @Column(name = "phone")
  private String phone;

  @Column(name = "gender")
  private Gender gender;

  @Column(name = "profession")
  private String profession;

  @Lob
  @OneToOne(mappedBy = "userInfo", fetch = FetchType.EAGER)
  private FamilyData familyData;

  @Lob
  @OneToOne(fetch = FetchType.EAGER, mappedBy = "userInfo")
  private UserHealthyState userHealthyState;

  @Lob
  @OneToOne(fetch = FetchType.EAGER, mappedBy = "userInfo")
  private UserAdditionalInfo userAdditionalInfo;

  @Column(name = "member_fees")
  @DateTimeFormat(pattern = "dd/MM/yyyy")
  private LocalDate memberFees;

  public UserInfo() {}

  // @Column(name = "userProgress", length = Integer.MAX_VALUE - 1)
  //// @OneToMany(fetch = FetchType.EAGER, mappedBy = "id") -- for now without second table -
  // user_progress
  //// private List<UserProgress> userProgressList;
  // private byte[] userProgressList;

  public UserInfo(int id) {
    this.id = id;
  }

  public LocalDate getDateBirth() {
    return dateBirth;
  }

  public FamilyData getFamilyData() {
    return familyData;
  }

  public Gender getGender() {
    return gender;
  }

  public int getId() {
    return id;
  }

  public String getImagePath() {
    return imagePath;
  }

  public LocalDate getMemberFees() {
    return memberFees;
  }

  public String getNotes() {
    return notes;
  }

  public String getPhone() {
    return phone;
  }

  // @Lob
  // @Column(name = "members", length = Integer.MAX_VALUE - 1)
  // private byte[] getFamilyMembersAsByteArray() { // not exposed
  // return userProgressList;
  // }
  //
  // private void setFamilyMembersAsByteArray(byte[] familyMembersAsByteArray) { // not exposed
  // this.userProgressList = familyMembersAsByteArray;
  // }


  // @SuppressWarnings("unchecked")
  // @Transient
  // public List<UserProgress> getUserProgressList() {
  // return (List<UserProgress>) SerializationUtils.deserialize(userProgressList);
  // }
  //
  // public void saveUserProgressList(List<UserProgress> userProgressList) {
  // this.userProgressList = SerializationUtils.serialize((Serializable) userProgressList);
  // }

  // public void setUserProgressList(byte[] userProgressList) {
  // this.userProgressList = userProgressList;
  // }

  public String getProfession() {
    return profession;
  }

  public Programs getTraining() {
    return training;
  }

  @OneToOne(fetch = FetchType.EAGER, mappedBy = "user", cascade = CascadeType.ALL)
  public User getUser() {
    return user;
  }

  public UserAdditionalInfo getUserAdditionalInfo() {
    return userAdditionalInfo;
  }

  public UserHealthyState getUserHealthyState() {
    return userHealthyState;
  }

  public void setDateBirth(LocalDate dateBirth) {
    this.dateBirth = dateBirth;
  }

  public void setFamilyData(FamilyData familyData) {
    this.familyData = familyData;
  }

  public void setGender(Gender gender) {
    this.gender = gender;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setImagePath(String imagePath) {
    this.imagePath = imagePath;
  }

  public void setMemberFees(LocalDate memberFees) {
    this.memberFees = memberFees;
  }

  public void setNotes(String notes) {
    this.notes = notes;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public void setProfession(String profession) {
    this.profession = profession;
  }

  public void setTraining(Programs training) {
    this.training = training;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public void setUserAdditionalInfo(UserAdditionalInfo userAdditionalInfo) {
    this.userAdditionalInfo = userAdditionalInfo;
  }

  public void setUserHealthyState(UserHealthyState userHealthyState) {
    this.userHealthyState = userHealthyState;
  }
}
