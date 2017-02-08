package com.crossballbox.controller;

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

import com.crossballbox.dao.UserDAO;
import com.crossballbox.dao.UserInfoDAO;
import com.crossballbox.model.User;
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

	@RequestMapping("")
	public String admin(Model model) {

		logger.info("admin-index");

		String test = "test123";
		model.addAttribute("submissions", test);
		return "admin/index";
	}

	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public String search(Model model, @RequestParam(value = "search", required = false) String query) {
		logger.info("Search query: " + query);
		List<User> users = new ArrayList<User>();
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

		model.addAttribute("users", users);
		model.addAttribute("query", query);
		return "admin/search_results";
	}

	@RequestMapping(value = "/user", method = RequestMethod.GET)
	public String updateUserInfo(Model model, @RequestParam(value = "id", required = true) String id) {
		logger.info("id: " + id);
		int userId = Integer.valueOf(id);

		UserInfo userInfo = userInfoDAO.findById(userId);
		if (userInfo == null) {
			userInfo = new UserInfo();
			userInfo.setId(userId);
			List<UserProgress> userProgressList = new ArrayList<UserProgress>();
			userInfo.setUserProgressList(userProgressList);
			userInfo.setUserProgressList(new ArrayList<UserProgress>());
			User user = userDAO.findById(userId);
			userInfo.setUser(user);
		} else {
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
		return "admin/user_details";
	}
	
	@RequestMapping(value = "/createNewUser", method = RequestMethod.GET)
	public String updateUserInfo() {
		//pop up window sa imenom rezimenom mailom sifrom -- kao signup
		return null;
	}

}
