package com.crossballbox.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.crossballbox.dao.UserDAO;
import com.crossballbox.model.User;
import com.crossballbox.security.Navigation;
import com.crossballbox.service.AdminService;
import com.crossballbox.service.EMailService;
import com.crossballbox.service.UserInfoService;
import com.crossballbox.service.UserService;

@Controller
@Navigation(Section.HOME)
public class HomeController {

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	@Autowired
	private UserDAO userDAO;

	@Autowired
	private EMailService eMailService;

	@Autowired
	private UserService userService;

	@Autowired
	protected AuthenticationManager authenticationManager;
	
	@Autowired
	private UserInfoService userInfoService;

	@RequestMapping(value = {"/table" })
	public String table(Model model) {
		logger.info("Oppening table");

		model.addAttribute("", "");
		return "table";
	}
	
	@RequestMapping(value = { "", "/", "/index" })
	public String home(Model model) {
		logger.info("Oppening index page");
		model = adminService.populateNotification(model);
		model.addAttribute("", "");
		return "index";
	}
	
	 @Autowired
	  private AdminService adminService;
	
	@RequestMapping(value = "/createAdminUser")
	  public String createAdminUser(Model model) {
	    logger.info("create admin user!");

	    String ADMIN = "admin";
	    User user = adminService.createNewUser(ADMIN, ADMIN, "test@gmail.com", "1/1/2000", "123456", "NONE", ADMIN, ADMIN);
	    user.setRole("ROLE_ADMIN");
	    userDAO.save(user);

	    logger.info("new user is created" + user.toString());
	    
	    userInfoService.updateUserInfo(model, String.valueOf(user.getId()));

	    return "index";
	  }

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String displayLogin(Model model) {
		logger.info("Oppening login page");

		model.addAttribute("", "");
		return "login";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String executeLogin(@RequestParam(value = "username", required = true) String username,
			@RequestParam(value = "password", required = true) String password) {

		return "redirect:/index";
	}

	@RequestMapping(value = "/signUp", method = RequestMethod.GET)
	public String displaySignUp(Model model) {
		logger.info("Oppening signUp page");

		model.addAttribute("", "");
		return "signup";
	}

	@RequestMapping(value = "/signUp", method = RequestMethod.POST)
	public String executeSignUp(Model model, @RequestParam(value = "firstName", required = true) String firstName,
			@RequestParam(value = "lastName", required = true) String lastName,
			@RequestParam(value = "eMail", required = true) String eMail,
			@RequestParam(value = "username", required = true) String username,
			@RequestParam(value = "password", required = true) String password) {
		logger.info("Oppening signUp page");

		model.addAttribute("", "");

		User user = new User();
		// Date date = new Date();
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.seteMail(eMail);
		user.setEnabled("false");
		user.setUsername(username);
		user.setPassword(password);

		// check if already exxist that user - mail, username

		// create token and add user in database
		String confirmationLink = userService.createActivationToken(user, true);

		// slanje aktivaciong mail-a
		eMailService.send(user, confirmationLink);

		return "email_verification";
	}

	@RequestMapping("/user/activate")
	public String userActivation(@RequestParam(value = "activation", required = true) String token,
			HttpServletRequest request) {
		User user = userDAO.findUserByToken(token);
		if (user != null) {
			user.setEnabled("true");
			userDAO.save(user);
		}

		// auto login user after registration
		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(user.getUsername(),
				user.getPassword());
		request.getSession();
		authToken.setDetails(new WebAuthenticationDetails(request));
		Authentication authenticatedUser = authenticationManager.authenticate(authToken);
		SecurityContextHolder.getContext().setAuthentication(authenticatedUser);

		return "index";
	}
	
	@RequestMapping("/news")
    public String news(Model model) {
        logger.info("Oppening news page");

        model.addAttribute("", "");
        return "news";
    }
	
	@RequestMapping("/about")
    public String about(Model model) {
        logger.info("Oppening about page");

        model.addAttribute("", "");
        return "about";
    }
	
	@RequestMapping("/contact")
    public String contact(Model model) {
        logger.info("Oppening contact page");

        model.addAttribute("", "");
        return "contact";
    }

	@RequestMapping("/taebo")
	public String taebo(Model model) {
		logger.info("Oppening taebo page");

		model.addAttribute("", "");
		return "taebo";
	}
	
	@RequestMapping("/aerotonus")
    public String aerotonus(Model model) {
        logger.info("Oppening aerotonus page");

        model.addAttribute("", "");
        return "aerotonus";
    }
	
	@RequestMapping("/total50")
    public String total50(Model model) {
        logger.info("Oppening total50 page");

        model.addAttribute("", "");
        return "total50";
    }
	
	@RequestMapping("/crossfit")
    public String crossfit(Model model) {
        logger.info("Oppening crossfit page");

        model.addAttribute("", "");
        return "crossfit";
    }

	@RequestMapping("/submit")
	public String submit(Model model) {
		logger.info("Oppening signUp page");

		model.addAttribute("", "");
		return "submit";
	}
	
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public String search(Model model, @RequestParam(value = "search", required = false) String query) {
		logger.info("Search query: " + query);
		List<User> users = new ArrayList<User>();
		if (!(query.isEmpty() || "".equals(query.trim()) || "*".equals(query.trim()))) {
			User user = userDAO.getUserByUsername(query);
			if(user != null)
				users.add(user);
			List<User> tempUsers = userDAO.getUsersByFirstName(query);
			if(tempUsers.size()>0)
				users.addAll(tempUsers);
			tempUsers = userDAO.getUsersByLastName(query);
			if(tempUsers.size()>0)
				users.addAll(tempUsers);
			tempUsers = userDAO.findUsersByFirstNameContaining(query);
			if(tempUsers.size()>0)
				users.addAll(tempUsers);
			tempUsers = userDAO.findUsersByLastNameContaining(query);
			if(tempUsers.size()>0)
				users.addAll(tempUsers);
			
			// Creating list with the distinct users
			users = users.stream().distinct().collect(Collectors.toList());
			//sort users by id
			Collections.sort(users, Comparator.comparing(User::getId).thenComparing(User::getId));

		} else {
			users = userDAO.findAll();
		}

		model.addAttribute("users", users);
		return "admin/search_results";
	}
}
