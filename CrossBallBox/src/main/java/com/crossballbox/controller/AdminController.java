package com.crossballbox.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.crossballbox.dao.FamilyDataDAO;
import com.crossballbox.dao.TrainingProgramDAO;
import com.crossballbox.dao.UserAdditionalInfoDAO;
import com.crossballbox.dao.UserDAO;
import com.crossballbox.dao.UserHealthyStateDAO;
import com.crossballbox.dao.UserInfoDAO;
import com.crossballbox.model.FamilyData;
import com.crossballbox.model.Programs;
import com.crossballbox.model.TrainingProgram;
import com.crossballbox.model.User;
import com.crossballbox.model.UserAdditionalInfo;
import com.crossballbox.model.UserHealthyState;
import com.crossballbox.model.UserInfo;
import com.crossballbox.model.UserProgress;

@Controller
@RequestMapping("/admin")
public class AdminController {

	private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

	@Autowired
	private UserDAO userDAO;

	@Autowired
	private UserInfoDAO userInfoDAO;

	@Autowired
	private FamilyDataDAO familyDataDAO;

	@Autowired
	private UserHealthyStateDAO userHealthyStateDAO;

	@Autowired
	private UserAdditionalInfoDAO userAdditionalInfoDAO;

	@Autowired
	private TrainingProgramDAO trainingProgramDAO;

	@RequestMapping("")
	public String admin(Model model) {

		logger.info("admin-index");

		String test = "test123";
		model.addAttribute("submissions", test);
		return "admin/index";
	}

	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public String search(Model model, @RequestParam(value = "search", required = false) String query,
			@RequestParam(value = "program", required = false) String program) {
		logger.info("Search query: " + query);
		logger.info("Searching program: " + program);
		// zameniti factory design patternom
		List<User> users = new ArrayList<User>();
		if (program == null) {
			if (!(query.isEmpty() || "".equals(query.trim()) || "*".equals(query.trim()))) {
				User user = userDAO.getUserByUsername(query);
				if (user != null)
					users.add(user);
				List<User> tempUsers = userDAO.getUsersByFirstName(query);
				if (tempUsers.size() > 0)
					users.addAll(tempUsers);
				tempUsers = userDAO.getUsersByLastName(query);
				if (tempUsers.size() > 0)
					users.addAll(tempUsers);
				tempUsers = userDAO.findUsersByFirstNameContaining(query);
				if (tempUsers.size() > 0)
					users.addAll(tempUsers);
				tempUsers = userDAO.findUsersByLastNameContaining(query);
				if (tempUsers.size() > 0)
					users.addAll(tempUsers);

				// Creating list with the distinct users
				users = users.stream().distinct().collect(Collectors.toList());
				// sort users by id
				Collections.sort(users, Comparator.comparing(User::getId).thenComparing(User::getId));

			} else {
				users = userDAO.findAll();
			}
		} else {
			// zameniti sa factory patternom
			Programs prog = Programs.valueOf(program);
//			Set<TrainingProgram> programs = new HashSet<TrainingProgram>();
			TrainingProgram trainingProgram = trainingProgramDAO.getTrainingProgramByName(program);

				users = null;//userDAO.getUsersByTrainings(trainingProgram);
		}

		model.addAttribute("users", users);
		model.addAttribute("query", query);
		return "admin/search_results1";
	}
	
	@RequestMapping(value = "/user/delete", method = RequestMethod.GET)
	public String deleteUser(Model model, @RequestParam(value = "id", required = true) String id) {
		logger.info("delete user with id: " + id);
		int userId = Integer.valueOf(id);
		userDAO.delete(userId);

		return "redirect:/admin/search?search=";
//		return search(model, search, program);
	}
	
	@RequestMapping(value = "/user", method = RequestMethod.GET)
	public String updateUserInfo(Model model, @RequestParam(value = "id", required = true) String id) {
		logger.info("id: " + id);
		int userId = Integer.valueOf(id);

		UserInfo userInfo = userInfoDAO.findById(userId);
		User user = userDAO.findById(userId);
		if (userInfo == null) {
			userInfo = new UserInfo(userId);
			userInfo.setId(userId);
			List<UserProgress> userProgressList = new ArrayList<UserProgress>();
			userInfo.setUserProgressList(userProgressList);
			userInfo.setUserProgressList(new ArrayList<UserProgress>());
			userInfo.setUser(user);
		} else {
				if(userInfo.getUser() == null){
					userInfo.setUser(user);
				}
			if (userInfo.getUserProgressList().equals(null)) {
				List<UserProgress> userProgressList = new ArrayList<UserProgress>();
				userInfo.setUserProgressList(userProgressList);
			}
		}
		model.addAttribute("userInfo", userInfo);
		String image = FilenameUtils.getName(userInfo.getImagePath());
		logger.info("image: " + image);
		model.addAttribute("image", image);

		if (!model.containsAttribute("imagePath")) {
			logger.info("nema");
			model.addAttribute("imagePath", "../../imagesTest/" + image);
		}
		model.addAttribute("imagePath1", "/imagesTest/" + image);

		// userinfo radiobuttons
		model.addAttribute("familyDataInfo", userInfo.getFamilyData());
		model.addAttribute("userHealthyStateInfo", userInfo.getUserHealthyState());
		model.addAttribute("userAdditionalInfo", userInfo.getUserAdditionalInfo());
		model.addAttribute("userProgerssListInfo", userInfo.getUserProgressList());
		
		model.addAttribute("userProfile", user);

		return "admin/user_details";
	}

	@RequestMapping(value = "/createNewUser", method = RequestMethod.POST)
	public String newUser(Model model, @RequestParam(value = "firstName", required = true) String firstName, 
			@RequestParam(value = "lastName", required = true) String lastName
			, @RequestParam(value = "eMail", required = true) String eMail
			, @RequestParam(value = "dateBirth", required = true) String dateBirth
			, @RequestParam(value = "phoneNumber", required = true) String phoneNumber
			, @RequestParam(value = "trainingProgram", required = true) String trainingProgram
			, @RequestParam(value = "username", required = false) String username
			, @RequestParam(value = "password", required = false) String password) {

		password = "pass";
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
//		userInfo.setDateBirth(dateBirth);
//		userInfo.setGender(gender);
//		userInfo.setImagePath(imagePath);
		userInfo.setPhone(phoneNumber);
		user.setUserInfo(userInfo);
		
		userDAO.save(user);
		logger.info("new user is created" + user.toString());
		
		
		
		return updateUserInfo(model, String.valueOf(user.getId()));
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
		logger.info("1: " + familyDiabetis + ", 2:" + familyObesity + ", 3:" + familyCardio + ", 4:" + userCardio
				+ ", 5:" + userMetabolic + ", 6:" + userOther + ", 7:" + userMedicament + ", 8:" + userInjuries + ", 9:"
				+ eatingPerDay + ", 10:" + watherPerDay + ", 11:" + suplements + ", 12:" + trainingActivity);

		int userId = Integer.valueOf(id);

		boolean familyDiabetisBool = (familyDiabetis.equalsIgnoreCase("no") ? false : true);
		boolean familyObesityBool = (familyObesity.equalsIgnoreCase("no") ? false : true);
		boolean familyCardioBool = (familyCardio.equalsIgnoreCase("no") ? false : true);
		FamilyData familyData = new FamilyData(userId, familyDiabetisBool, familyObesityBool, familyCardioBool);
		familyDataDAO.save(familyData);

		boolean userCardioBool = (userCardio.equalsIgnoreCase("no") ? false : true);
		boolean userMetabolicBool = (userMetabolic.equalsIgnoreCase("no") ? false : true);
		boolean userOtherBool = (userOther.equalsIgnoreCase("no") ? false : true);
		boolean userMedicamentBool = (userMedicament.equalsIgnoreCase("no") ? false : true);
		boolean userInjuriesBool = (userInjuries.equalsIgnoreCase("no") ? false : true);
		UserHealthyState userHealthyState = new UserHealthyState(userId, userCardioBool, userMetabolicBool,
				userOtherBool, userMedicamentBool, userInjuriesBool);
		userHealthyStateDAO.save(userHealthyState);

		double watherLiterPerDay = Double.valueOf(watherPerDay);
		int timeOfMealPerDay = Integer.valueOf(eatingPerDay);
		logger.info("w: " + watherLiterPerDay + " o: " + timeOfMealPerDay);
		boolean suplementsOrProtein = (suplements.equalsIgnoreCase("no") ? false : true);
		boolean physicalActivity = (trainingActivity.equalsIgnoreCase("no") ? false : true);
		UserAdditionalInfo userAdditionalInfo = new UserAdditionalInfo(userId, watherLiterPerDay, timeOfMealPerDay,
				suplementsOrProtein, suplementsType, physicalActivity, trainingActivityType);
		userAdditionalInfoDAO.save(userAdditionalInfo);

		UserInfo userInfo = userInfoDAO.findOne(userId);
		if(userInfo == null){
			userInfo = new UserInfo(userId);
			userInfo.setUser(userDAO.getOne(userId));
		}
		userInfo.setUserAdditionalInfo(userAdditionalInfo);
//		userInfo.setImagePath(imagePath);
		userInfoDAO.save(userInfo);
		
		User user = userDAO.getOne(userId);
		user.setUserInfo(userInfo);
		userDAO.save(user);
//		updateUserInfo(model, id);
		return "redirect:/admin/user?id=" + id + "#";
	}
}
