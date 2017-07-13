package com.crossballbox.service;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.crossballbox.dao.UserDAO;
import com.crossballbox.model.User;

/**
 * class implements UserDetailService so User could login
 */
@Service
public class UserService implements UserDetailsService {

	private static final Logger logger = LoggerFactory.getLogger(UserService.class);

	public final String CURRENT_USER_KEY = "CURRENT_USER";

	@Autowired
	private UserDAO userDAO;

	@Value("${app.user.verification}")
	private Boolean requireActivation;

	@Value("${app.secret}")
	private String applicationSecret;

	public String createActivationToken(User user, Boolean save) {
		Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		String activationToken = encoder.encodePassword(user.getUsername(), applicationSecret);
		if (save) {
			user.setToken(activationToken);
			userDAO.save(user);
		}
		return activationToken;
	}

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) {
		Set<GrantedAuthority> grantedAuthorities = new HashSet<GrantedAuthority>();
		org.springframework.security.core.userdetails.User retUser = new org.springframework.security.core.userdetails.User(
				"bad", "user", grantedAuthorities);
		try {
			User user = userDAO.getUserByUsername(username);
			if ("true".equals(user.getEnabled())) {
				String role = user.getRole();
				grantedAuthorities.add(new SimpleGrantedAuthority(role));
				logger.info(user.toString());

				retUser = new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
						grantedAuthorities);
			}else{
				logger.info("User is not activated, but you try to login");
			}
		} catch (Exception e) {
			logger.error("Error finding username/password for login!");
		}
		return retUser;
		
	}

	public void addTrainngToUser(){
		
	}
	
	
	
}
