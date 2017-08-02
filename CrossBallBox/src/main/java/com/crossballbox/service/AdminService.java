package com.crossballbox.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import com.crossballbox.dao.FamilyDataDAO;
import com.crossballbox.dao.UserAdditionalInfoDAO;
import com.crossballbox.dao.UserDAO;
import com.crossballbox.dao.UserHealthyStateDAO;
import com.crossballbox.dao.UserInfoDAO;
import com.crossballbox.model.FamilyData;
import com.crossballbox.model.Programs;
import com.crossballbox.model.User;
import com.crossballbox.model.UserAdditionalInfo;
import com.crossballbox.model.UserHealthyState;
import com.crossballbox.model.UserInfo;
import com.crossballbox.model.UserProgress;
import com.crossballbox.util.ConfigurationUtils;

@Service
public class AdminService {

  private static final Logger logger = LoggerFactory.getLogger(AdminService.class);

  private final String USER_IMAGES_PREFIX_PATH = "../userImages/";

  @Autowired
  private ConfigurationUtils configurationUtils;

  @Autowired
  private ImageService imageService;

  @Autowired
  private UserDAO userDAO;

  @Autowired
  private UserInfoDAO userInfoDAO;

  @Autowired
  private FamilyDataDAO familyDataDAO;

  @Autowired
  private UserAdditionalInfoDAO userAdditionalInfoDAO;

  @Autowired
  private UserHealthyStateDAO userHealthyStateDAO;

  private File convertMultipartFileToFile(MultipartFile file) throws IOException {

    logger.info("convertMultipartFileToFile");

    File convFile = new File(file.getOriginalFilename());
    convFile.createNewFile();
    FileOutputStream fos = new FileOutputStream(convFile);
    fos.write(file.getBytes());
    fos.close();
    return convFile;
  }

  private void copyFile(File sourceFile, File destFile/* , MultipartFile multipartFile */) {

    logger.info("copyFile");
    // File destFile1 = new File(destFile.getPath());
    if (!destFile.exists()) {
      try {
        destFile.createNewFile();
      } catch (IOException e) {
        logger.error("error: " + e.getMessage());
      }
    } else {
      destFile.delete();
      try {
        destFile.createNewFile();

      } catch (IOException e) {
        logger.error("error: " + e.getMessage() + " stack trace: " + e.getStackTrace().toString());
      }
    }

    FileChannel source = null;
    FileChannel destination = null;

    try {
      try {
        if (!sourceFile.canRead()) {
          sourceFile.setReadable(true);
        }
        source = new FileInputStream(sourceFile).getChannel();
        destination = new FileOutputStream(destFile).getChannel();
        destination.transferFrom(source, 0, source.size());
      } catch (FileNotFoundException e) {
        logger.error("file not found: " + e.getMessage());
      } finally {
        if (source != null) {
          source.close();
        }
        if (destination != null) {
          destination.close();
        }
      }
    } catch (IOException e) {
      logger.error("error: " + e.getMessage());
    }
  }

  public User createNewUser(String firstName, String lastName, String eMail, String dateBirth, String phoneNumber, String trainingProgram,
      String username, String password) {

    logger.info("Create new user from admin service");

    User user = new User();
    user.setFirstName(firstName);
    user.setLastName(lastName);
    user.seteMail(eMail);
    user.setUsername(username);
    user.setPassword(password);
    java.util.Date d = new java.util.Date();
    user.setDateCreated(new Date(d.getTime()));
    user.setEnabled("true");
    UserInfo userInfo = new UserInfo();
    userInfo.setTraining(Programs.valueOf(trainingProgram));
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("[dd/MM/yyyy][d/MM/yyyy][dd/M/yyyy][d/M/yyyy]");
    LocalDate dateTime = LocalDate.parse(dateBirth, formatter);
    userInfo.setDateBirth(dateTime);
    // userInfo.setGender(gender);
    // userInfo.setImagePath(imagePath);
    userInfo.setPhone(phoneNumber);
    userInfo.setUser(user);
    userInfo.setId(user.getId());
    userInfoDAO.save(userInfo);
    user.setUserInfo(userInfo);

    return user;
  }

  public String createUsername(String firstname, String lastname) {
    String username = firstname.substring(0, 1) + lastname;
    int i = 1;
    while (!isValidUsername(username)) {
      if (i <= firstname.length()) {
        username = firstname.substring(0, ++i) + lastname;
      } else {
        username += i++;
      }
    }

    return username.toLowerCase();
  }

  public boolean isValidUsername(String username) {
    boolean ret = true;
    if (userDAO.getUserByUsernameIgnoreCase(username) != null) {
      ret = false;
    }
    return ret;
  }

  public File uploadUserImage(MultipartFile uploadfile, int userId) {

    logger.info("Uploadfile: " + uploadfile.getName() + "for user with id: " + userId);

    // TODO: get extension
    String fileExtension = uploadfile.getOriginalFilename().split("\\.")[1];
    File dstFile = new File(configurationUtils.getFileDownloadPath() + File.separator + userId + "." + fileExtension);
    // File file = new File(ClassLoader.getResource(".").getFile() + File.separator + userId + "." +
    // fileExtension);
    try {
      System.out.println("file extension: " + fileExtension);
      if (("jpg").equals(fileExtension) || ("png").equals(fileExtension) || ("gif").equals(fileExtension)) {

        File jpgFile;
        if ("jpg".equals(fileExtension)) {
          jpgFile = convertMultipartFileToFile(uploadfile);
        } else {
          jpgFile = /* imageService. */convertMultipartFileToFile(uploadfile);
        }
        copyFile(jpgFile, dstFile/* , uploadfile */);
        User user = userDAO.findById(userId);
        UserInfo userInfo = user.getUserInfo();// UserInfo userInfo = userInfoDAO.findById(userId);
        if (userInfo == null) {
          userInfo = new UserInfo(userId);
          userInfo.setUser(userDAO.findById(userId));
          userDAO.findById(userId).setUserInfo(userInfo);
          List<UserProgress> list = new ArrayList<UserProgress>();
        } else {
        }
        String dstRelativePath = null;
        if (dstFile.getCanonicalPath().contains(configurationUtils.getFileDownloadPath().toString())) {
          dstRelativePath = dstFile.getCanonicalPath()
              .substring(dstFile.getCanonicalPath().lastIndexOf(configurationUtils.getFileDownloadPath().toString()) + 1);
        }
        // logger.info("dstRelativePath: " + dstFile.getCanonicalPath());
        // logger.info("dstRelativePath1: " + dstFile.getName());
        userInfo.setImagePath(USER_IMAGES_PREFIX_PATH + dstFile.getName());
        userInfoDAO.save(userInfo);
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);

        // delete from folder
      } else {
        logger.error("Bad file format for user profile image!");
      }

    } catch (Exception e) {
      logger.error("Failed to upload image!");
      System.out.println(e.getMessage());
    }

    return dstFile;
  }

  public UserInfo saveUserInfo(FamilyData familyData, UserHealthyState userHealthyState, UserAdditionalInfo userAdditionalInfo,
      String imagePath, User user) {

    int userId = user.getId();
    UserInfo userInfo = user.getUserInfo();// UserInfo userInfo = userInfoDAO.findById(userId);
    if (userInfo == null) {
      userInfo = new UserInfo(userId);
    }
    userInfo.setFamilyData(familyData);
    userInfo.setUserHealthyState(userHealthyState);
    userInfo.setUserAdditionalInfo(userAdditionalInfo);
    userInfo.setUser(user);
//    userInfo.setImagePath(imagePath);

    userInfoDAO.save(userInfo);

    return userInfo;
  }

  public Model populateNotification(Model model) {

    logger.info("populate Notification");

    // to avoid hibernate/jpa caching
    entityManager.clear();

    LocalDate date = LocalDate.of(1900, 8, 11);
    LocalDate now = LocalDate.now().minusMonths(1).plusDays(3);// moze citanje iz baze, za setting

    List<UserInfo> userInfoList = userInfoDAO.findByMemberFeesBetween(date, now);

    Map<Integer, String> notificationsLinks = new HashMap<Integer, String>();
    for (UserInfo userInfo : userInfoList) {
      if (userInfo.getMemberFees().isBefore(LocalDate.now().minusMonths(1).plusDays(3))) {
        notificationsLinks.put(userInfo.getUser().getId(), userInfo.getUser().getFirstName() + " " + userInfo.getUser().getLastName());
      }
    }
    // notificationsLinks treba da bude <= 15 - reseno na beckendu, lakse mi je tako

    model.addAttribute("notificationNumber", notificationsLinks.size()); // number of unread

    model.addAttribute("notifications", notificationsLinks); // number of unread notifications
                                                             // notifications
    return model;
  }

  @Autowired
  private EntityManager entityManager;

  public FamilyData saveFamilyData(int userId, String familyDiabetis, String familyObesity, String familyCardio) {

    boolean familyDiabetisBool = (familyDiabetis.equalsIgnoreCase("no") ? false : true);
    boolean familyObesityBool = (familyObesity.equalsIgnoreCase("no") ? false : true);
    boolean familyCardioBool = (familyCardio.equalsIgnoreCase("no") ? false : true);

    FamilyData familyData = familyDataDAO.findById(userId);
    if (familyData == null) {
      familyData = new FamilyData(userId, familyDiabetisBool, familyObesityBool, familyCardioBool);
    } else {
      familyData.setCardioIllnes(familyCardioBool);
      familyData.setObesity(familyObesityBool);
      familyData.setDiabetes(familyDiabetisBool);
    }

    familyDataDAO.saveAndFlush(familyData);

    return familyData;
  }

  public UserHealthyState saveHealthyState(int userId, String userCardio, String userMetabolic, String userOther, String userMedicament,
      String userInjuries) {

    boolean userCardioBool = (userCardio.equalsIgnoreCase("no") ? false : true);
    boolean userMetabolicBool = (userMetabolic.equalsIgnoreCase("no") ? false : true);
    boolean userOtherBool = (userOther.equalsIgnoreCase("no") ? false : true);
    boolean userMedicamentBool = (userMedicament.equalsIgnoreCase("no") ? false : true);
    boolean userInjuriesBool = (userInjuries.equalsIgnoreCase("no") ? false : true);


    UserHealthyState userHealthyState = userHealthyStateDAO.findById(userId);
    if (userHealthyState == null) {
      userHealthyState =
          new UserHealthyState(userId, userCardioBool, userMetabolicBool, userOtherBool, userMedicamentBool, userInjuriesBool);
    } else {
      userHealthyState.setCardioIllness(userCardioBool);
      userHealthyState.setMetabolicIllness(userMetabolicBool);
      userHealthyState.setOtherHealthyIssues(userOtherBool);
      userHealthyState.setInjury(userInjuriesBool);
      userHealthyState.setMedicamentsConsumer(userMedicamentBool);
    }

    userHealthyStateDAO.save(userHealthyState);

    return userHealthyState;
  }

  public void saveUser(int userId, UserInfo userInfo) {
    User user = userDAO.getOne(userId);
    user.setUserInfo(userInfo);
    userDAO.save(user);
  }

  public UserAdditionalInfo saveUserAditionalInfo(int userId, String watherPerDay, String eatingPerDay, String suplements,
      String trainingActivity, String suplementsType, String trainingActivityType) {

    double watherLiterPerDay = Double.valueOf(watherPerDay);
    int timeOfMealPerDay = Integer.valueOf(eatingPerDay);
    logger.info("w: " + watherLiterPerDay + " o: " + timeOfMealPerDay);

    boolean suplementsOrProtein = (suplements.equalsIgnoreCase("no") ? false : true);
    boolean physicalActivity = (trainingActivity.equalsIgnoreCase("no") ? false : true);


    UserAdditionalInfo userAdditionalInfo = userAdditionalInfoDAO.findById(userId);
    if (userAdditionalInfo == null) {
      userAdditionalInfo = new UserAdditionalInfo(userId, watherLiterPerDay, timeOfMealPerDay, suplementsOrProtein, suplementsType,
          physicalActivity, trainingActivityType);
    } else {
      userAdditionalInfo.setWatherLiterPerDay(watherLiterPerDay);
      userAdditionalInfo.setTimeOfMealPerDay(timeOfMealPerDay);
      userAdditionalInfo.setSuplementsOrProtein(suplementsOrProtein);
      userAdditionalInfo.setSuplementsOrProteinDescription(suplementsType);
      userAdditionalInfo.setPhysicalActivityDescription(trainingActivityType);
      userAdditionalInfo.setPhysicalActivity(physicalActivity);
    }
    userAdditionalInfoDAO.save(userAdditionalInfo);

    return userAdditionalInfo;
  }

}
