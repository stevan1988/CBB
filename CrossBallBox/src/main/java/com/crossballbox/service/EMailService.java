package com.crossballbox.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.crossballbox.model.User;

@Service
public class EMailService {

	@Autowired
	private JavaMailSender mailSender;
	
	@Value("${app.url}")
	private String appUrl;

	public void send(User user, String confirmationToken) {

		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setTo(user.geteMail());
		mailMessage.setSubject("Registration");
		String confirmationLink = appUrl + "/user/activate?activation=" + confirmationToken;
		String message ="Hello " + user.getUsername() + "\n Your registration is successfull!" + "\n\nPlease, click on link below to confirm your account.\n\n" + confirmationLink ; 
		mailMessage.setText(message);
		mailSender.send(mailMessage);
	}

	@Value("${app.email.from}")
    private String fromEmail;

    @Value("${app.email.support}")
    private String supportEmail;
    
    public void sendMail(String to, String subject, String text) {
        try {
            SimpleMailMessage email = new SimpleMailMessage();
            email.setTo(to);
            email.setSubject(subject);
            email.setFrom(fromEmail);
            email.setText(text);
            mailSender.send(email);
            System.out.println("SENT EMAIL: TO=" + to + "|SUBJECT:" + subject + "|TEXT:" + text);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public void sendResetPassword(String to, String token) {
        String url = appUrl + "/user/reset-password-change?token=" + token;
        String subject = "Reset Password";
        String text = "Please click the following link to reset your password: " + url;
        sendMail(to, subject, text);
    }
    
    public void sendNewRegistration(String to, String token) {
        String url = appUrl + "/user/activate?activation=" + token;
        String subject = "Please activate your account";
        String text = "Please click the following link to activate your account: " + url;
        sendMail(to, subject, text);
    }
    
    public void sendNewActivationRequest(String to, String token) {
        sendNewRegistration(to, token);
    }
    
    public void sendErrorEmail(Exception e, HttpServletRequest req, User user) {
        String subject = "Application Error: " + req.getRequestURL();
        String text = "An error occured in your application: " + e + "\r\nFor User:  " + user.geteMail();
        sendMail(supportEmail, subject, text);
    }
}
