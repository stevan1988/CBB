package com.crossballbox.service;

import java.io.File;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;

import com.crossballbox.dao.UserDAO;
import com.crossballbox.dao.UserInfoDAO;
import com.crossballbox.dao.UserProgressDAO;
import com.crossballbox.model.User;
import com.crossballbox.model.UserInfo;
import com.crossballbox.model.UserProgress;
import com.crossballbox.wrapper.UserProgressListWrapper;

@Service
public class UserInfoService {
  
  private static final Logger logger = LoggerFactory.getLogger(UserInfoService.class);
  
  String DEFAULT_USER_IMAGE = "../img/user_img.png";

  @Autowired
  private UserDAO userDAO;

  @Autowired
  private UserInfoDAO userInfoDAO;
  
  @Autowired
  private UserProgressDAO userProgressDAO;
  
  public Model updateUserInfo(Model model, String id){
    
    logger.info("id: " + id);
    int userId = Integer.valueOf(id);

    User user = userDAO.findById(userId);
    UserInfo userInfo = user.getUserInfo();// userInfoDAO.findById(userId);
    
    if (userInfo == null) {
      userInfo = new UserInfo(userId);
      userInfo.setId(userId);
      userInfo.setUser(user);
    } else {
      if (userInfo.getUser() == null) {
        userInfo.setUser(user);
      }
    }
    model.addAttribute("userInfo", userInfo);
    String image = FilenameUtils.getName(userInfo.getImagePath());
    if (StringUtils.isEmpty(image)) {
      logger.info("user does not have profil image - set default one");

      File userImage = new File("../userImages/" + id + ".jpg");
      if (userImage.exists() && !userImage.isDirectory()) {
        image = userImage.getPath();
      } else
        image = DEFAULT_USER_IMAGE;
      userInfo.setImagePath(image);
      userInfoDAO.save(userInfo);
    }
    logger.info("image: " + image);

    // userinfo radiobuttons
    model.addAttribute("familyDataInfo", userInfo.getFamilyData());
    model.addAttribute("userHealthyStateInfo", userInfo.getUserHealthyState());
    model.addAttribute("userAdditionalInfo", userInfo.getUserAdditionalInfo());

    // user progress list
    UserProgressListWrapper userProgressistWrapper = new UserProgressListWrapper();

    List<UserProgress> userProgressListByUser = userProgressDAO.findByUserInfo(userInfo);


    userProgressistWrapper.setUserProgressList(userProgressListByUser);
    model.addAttribute("userProgerssListInfo", userProgressistWrapper);

    model.addAttribute("userProfile", user);
    
    return model;
  }
}
