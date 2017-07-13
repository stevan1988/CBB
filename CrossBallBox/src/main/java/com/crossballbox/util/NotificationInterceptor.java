package com.crossballbox.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.HandlerMethod;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.crossballbox.controller.AdminController;
import com.crossballbox.service.AdminService;

public class NotificationInterceptor extends HandlerInterceptorAdapter{

  private static final Logger logger = LoggerFactory.getLogger(NotificationInterceptor.class);
  
  @Autowired
  private AdminService adminService;
  
//  @Override
//  public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception exception)
//  throws Exception {
//  // TODO Auto-generated method stub
//
//  }

  @Override
  public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
  throws Exception {
  // TODO Auto-generated method stub
//    Model model = adminService.populateNotification((Model) modelAndView.getModelMap().get("model"));
//    modelAndView.getModelMap().put("model", model);
//    
//    modelAndView.addAttribute(model.get"notifications", notificationsLinks); // number of unread notifications
//
//    modelAndView.addAttribute("notificationNumber", notificationsLinks.size()); // number of unread
//                                                                         // notifications
//    modelAndView.addAttribute("notificationUserID", notificationUserID);
//    modelAndView = adminService.populateNotification(modelAndView);

  }

//  @Override
//  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//
//
//      HandlerMethod handlerMethod = (HandlerMethod) handler;
//
//      String emailAddress = request.getParameter("emailaddress");
//      String password = request.getParameter("password");
//
//      if(StringUtils.isEmpty(emailAddress) || StringUtils.containsWhitespace(emailAddress) ||
//      StringUtils.isEmpty(password) || StringUtils.containsWhitespace(password)) {
//          throw new Exception("Invalid User Id or Password. Please try again.");
//      }
//
//      return true;
//  }
  
}
