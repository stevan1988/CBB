package com.crossballbox.controller;

import java.io.File;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.crossballbox.dao.UserDAO;
import com.crossballbox.dao.UserInfoDAO;
import com.crossballbox.dao.UserProgressDAO;
import com.crossballbox.model.FamilyData;
import com.crossballbox.model.Programs;
import com.crossballbox.model.Roles;
import com.crossballbox.model.User;
import com.crossballbox.model.UserAdditionalInfo;
import com.crossballbox.model.UserHealthyState;
import com.crossballbox.model.UserInfo;
import com.crossballbox.model.UserProgress;
import com.crossballbox.service.AdminService;
import com.crossballbox.service.UserInfoService;
import com.crossballbox.wrapper.UserProgressListWrapper;

@Controller
@RequestMapping("/admin")
public class AdminController {

  private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

  @Autowired
  private AdminService adminService;

  @Autowired
  private UserInfoService userInfoService;

  @Autowired
  private UserDAO userDAO;

  @Autowired
  private UserInfoDAO userInfoDAO;

  @Autowired
  private UserProgressDAO userProgressDAO;

  @RequestMapping("")
  public String admin(Model model) {

    logger.info("admin-index");

    String test = "test123";
    model.addAttribute("submissions", test);
    return "admin/index";
  }

  @RequestMapping(value = "/changeUserRole", method = RequestMethod.GET)
  public String changeUserRole(@RequestParam(value = "id", required = true) int id) {

    logger.info("Setting user role for id=" + id);
    User user = userDAO.findById(id);
    if (Roles.USER.toString().equals(user.getRole())) {
      logger.info("to ROLE_ADMIN");
      user.setRole(Roles.ADMIN.toString());
    } else {
      logger.info("to ROLE_USER");
      user.setRole(Roles.USER.toString());
    }

    userDAO.save(user);

    return "#";
  }

  @RequestMapping(value = "/user/delete", method = RequestMethod.GET)
  public String deleteUser(Model model, @RequestParam(value = "id", required = true) String id) {
    logger.info("delete user with id: " + id);
    int userId = Integer.valueOf(id);
    
    User user = userDAO.findById(userId);
    try{
    userInfoDAO.delete(user.getUserInfo());
    userInfoDAO.flush();
    }catch(Exception e){
      logger.error("Error: delete user info for user id: " + id);
    }
    userDAO.delete(user);
    userDAO.flush();

    String query = "";
    String program = null;
    return search(model, query, program);//"redirect:/admin/search?search=";
  }

  @RequestMapping(value = "/createNewUser", method = RequestMethod.POST)
  public String newUser(Model model, @RequestParam(value = "firstName", required = true) String firstName,
      @RequestParam(value = "lastName", required = true) String lastName, @RequestParam(value = "eMail", required = true) String eMail,
      @RequestParam(value = "dateBirth", required = false) String dateBirth,
      @RequestParam(value = "phoneNumber", required = true) String phoneNumber,
      @RequestParam(value = "trainingProgram", required = true) String trainingProgram,
      @RequestParam(value = "username", required = false) String username,
      @RequestParam(value = "password", required = false) String password) {

    logger.info("create new user with: firstname: " + firstName + ", lastname: " + lastName + ", email:" + eMail + ", dateBirth: "
        + dateBirth + ", phoneNumber: " + phoneNumber + ", trainingProgram:" + trainingProgram + " ,username: " + username);

    if (StringUtils.isEmpty(username)) {
      username = adminService.createUsername(firstName, lastName);
    }
    if (adminService.isValidUsername(username)) {// delete, unnecessary check

      password = "pass";
      User user = adminService.createNewUser(firstName, lastName, eMail, dateBirth, phoneNumber, trainingProgram, username, password);

      userDAO.save(user);

      logger.info("new user is created" + user.toString());
      return updateUserInfo(model, String.valueOf(user.getId()));
    } else {
      return "admin/createNewUser";
    }

  }

  @RequestMapping(value = "/saveUserProgress", method = RequestMethod.POST)
  public String processQuery(Model model, @RequestParam(value = "id", required = true) String id,
      @ModelAttribute("userProgerssListInfo") UserProgressListWrapper userProgressListWrapper) {
    logger.info("saveUserProgeress");

    List<UserProgress> userProgressList = userProgressListWrapper.getUserProgressList();

    List<UserProgress> filteredList =
        userProgressList.stream().filter(userProgress -> userProgress.getDate() != null).collect(Collectors.toList());
    userProgressList = filteredList;

    int userId = Integer.valueOf(id);
    User user = userDAO.findById(userId);
    UserInfo userInfo = user.getUserInfo();// userInfoDAO.findById(userId);
    if (userInfo == null) {
      userInfo = new UserInfo();
      userInfo.setId(userId);
      userInfo.setUser(userDAO.getOne(userId));
    }

    userInfoDAO.save(userInfo);
    for (UserProgress userProgress : userProgressList) {
      // TODO: fix - for some reason first data in list is one with empty values
      if (userProgress == null || userProgress.getDate() == null) {
      } else {
        userProgress.setUserInfo(userInfo);
        userProgressDAO.save(userProgress);
      }
    }

    return "redirect:/admin/user?id=" + id + "#";
  }



  @RequestMapping(value = "/renewMemberFees", method = RequestMethod.POST)
  public String renewMemberFees() {
    return null;
  }

  @RequestMapping(value = "/notifications", method = RequestMethod.GET)
  public String notifications(Model model) {
    model = adminService.populateNotification(model);
    return "admin/notifications";
  }

  @RequestMapping(value = "/settings", method = RequestMethod.GET)
  public String settings() {
    return "admin/settings";
  }

  @RequestMapping(value = "/saveUserInfo", method = RequestMethod.POST)
  public String saveUserInfo(Model model, @RequestParam(value = "id", required = true) String id,
      @RequestParam(value = "family_diabetis", required = true) String familyDiabetis,
      @RequestParam(value = "family_obesity", required = true) String familyObesity,
      @RequestParam(value = "family_cardio", required = true) String familyCardio,
      @RequestParam(value = "user_cardio", required = true) String userCardio,
      @RequestParam(value = "user_metabolic", required = true) String userMetabolic,
      @RequestParam(value = "user_other", required = true) String userOther,
      @RequestParam(value = "user_medicament", required = true) String userMedicament,
      @RequestParam(value = "user_injuries", required = true) String userInjuries,
      @RequestParam(value = "eating_per_day", required = true) String eatingPerDay,
      @RequestParam(value = "wather_per_day", required = true) String watherPerDay,
      @RequestParam(value = "suplements", required = true) String suplements,
      @RequestParam(value = "suplements_type", required = false) String suplementsType,
      @RequestParam(value = "training_activity", required = true) String trainingActivity,
      @RequestParam(value = "training_activity_type", required = false) String trainingActivityType) {

    logger.info("update user info for userId: " + id);
    logger.info("1: " + familyDiabetis + ", 2:" + familyObesity + ", 3:" + familyCardio + ", 4:" + userCardio + ", 5:" + userMetabolic
        + ", 6:" + userOther + ", 7:" + userMedicament + ", 8:" + userInjuries + ", 9:" + eatingPerDay + ", 10:" + watherPerDay + ", 11:"
        + suplements + ", 12:" + trainingActivity);

    int userId = Integer.valueOf(id);

    FamilyData familyData = adminService.saveFamilyData(userId, familyDiabetis, familyObesity, familyCardio);

    UserHealthyState userHealthyState =
        adminService.saveHealthyState(userId, userCardio, userMetabolic, userOther, userMedicament, userInjuries);

    UserAdditionalInfo userAdditionalInfo = adminService.saveUserAditionalInfo(userId, watherPerDay, eatingPerDay, suplements,
        trainingActivity, suplementsType, trainingActivityType);

    User user = userDAO.findById(userId);

    UserInfo userInfo = adminService.saveUserInfo(familyData, userHealthyState, userAdditionalInfo, null, user);

    adminService.saveUser(userId, userInfo);

    return "redirect:/admin/user?id=" + id + "#";
  }

  @RequestMapping(value = "/search", method = RequestMethod.GET)
  public String search(Model model, @RequestParam(value = "search", required = false) String query,
      @RequestParam(value = "program", required = false) String program) {

    model = adminService.populateNotification(model);
    logger.info("Search query: " + query);
    logger.info("Searching program: " + program);
    // zameniti factory design patternom
    List<User> users = new ArrayList<User>();
    if (program == null) {
      if (!(query.isEmpty() || "".equals(query.trim()) || "*".equals(query.trim()))) {
        User user = userDAO.getUserByUsernameIgnoreCase(query); // i da nije admin user!!!
        if (user != null)
          users.add(user);
        List<User> tempUsers = userDAO.findByFirstNameIgnoreCase(query);
        if (tempUsers.size() > 0)
          users.addAll(tempUsers);
        tempUsers = userDAO.findByLastNameIgnoreCase(query);
        if (tempUsers.size() > 0)
          users.addAll(tempUsers);
        tempUsers = userDAO.findByFirstNameContainingIgnoreCase(query);
        if (tempUsers.size() > 0)
          users.addAll(tempUsers);
        tempUsers = userDAO.findByLastNameContainingIgnoreCase(query);
        if (tempUsers.size() > 0)
          users.addAll(tempUsers);

        tempUsers = userDAO.findUsersByRole("ROLE_ADMIN");

        users.removeAll(tempUsers);
        // Creating list with the distinct users
        users = users.stream().distinct().collect(Collectors.toList());
        // sort users by id
        Collections.sort(users, Comparator.comparing(User::getId).thenComparing(User::getId));

      } else {
        users = userDAO.findAll();

        List<User> tempUsers = userDAO.findUsersByRole("ROLE_ADMIN");
        users.removeAll(tempUsers);
      }
    } else {
      // zameniti sa factory patternom
      Programs prog = Programs.valueOf(program);

      List<UserInfo> usersInfoList = userInfoDAO.findUsersByTraining(prog);

      Iterator<UserInfo> iterator = usersInfoList.iterator();
      while (iterator.hasNext()) {
        users.add(iterator.next().getUser());
      }

    }

    model.addAttribute("users", users);
    model.addAttribute("query", query);
    return "admin/search_results1";
  }

  @RequestMapping(value = "/setMemberFees", method = RequestMethod.POST)
  public String setMemberFees(@RequestParam(value = "id", required = true) String id, @RequestParam(value = "date") Date date) {
    logger.info("setMemberFees for user with id=" + id);
    int userId = Integer.parseInt(id);
    User user = userDAO.findById(userId);
    UserInfo userInfo = user.getUserInfo();// UserInfo userInfo = userInfoDAO.findById(userId);
    if (userInfo == null) {
      userInfo = new UserInfo(userId);

      user.setUserInfo(userInfo);
      userDAO.save(user);
      userInfo.setUser(user);
    }

    userInfo.setMemberFees(date.toLocalDate());

    // userInfoDAO.save(userInfo);
    userInfoDAO.saveAndFlush(userInfo);

    String ret = "redirect:/admin/user?id=" + id + "#";
    return ret;
  }

  @RequestMapping(value = "/user", method = RequestMethod.GET)
  public String updateUserInfo(Model model, @RequestParam(value = "id", required = true) String id) {
    model = adminService.populateNotification(model);
    model = userInfoService.updateUserInfo(model, id);

    return "admin/user_details";
  }

  @RequestMapping(value = "/upload_image", method = RequestMethod.POST)
  public String uploadUserImage(Model model, @RequestParam(value = "uploadfile", required = true) MultipartFile uploadfile,
      @RequestParam(value = "userId", required = true) int userId) throws JSONException {

    File dstFile = adminService.uploadUserImage(uploadfile, userId);

    model.addAttribute("imagePath", "../" + dstFile.getPath());
    model.addAttribute("reload", "true");
    updateUserInfo(model, Integer.toString(userId));

    String ret = "redirect:/admin/user?id=" + userId + "#";
    return ret;
  }


}
