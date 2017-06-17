package com.crossballbox.model;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.transaction.Transactional;

@Entity
@Transactional
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

  @Column(name = "date_birth")
  private Date dateBirth;

  @Column(name = "phone")
  private String phone;

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

  // @Column(name = "userProgress", length = Integer.MAX_VALUE - 1)
  //// @OneToMany(fetch = FetchType.EAGER, mappedBy = "id") -- for now without second table -
  // user_progress
  //// private List<UserProgress> userProgressList;
  // private byte[] userProgressList;

  @Column(name = "memeber_fees")
  private Date memberFees;

  public Date getMemberFees() {
    return memberFees;
  }

  public void setMemberFees(Date memberFees) {
    this.memberFees = memberFees;
  }

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

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
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

  public UserInfo(int id) {
    this.id = id;
  }

  public UserInfo() {}
}
