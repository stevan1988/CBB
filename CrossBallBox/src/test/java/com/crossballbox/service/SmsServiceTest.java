package com.crossballbox.service;

import java.util.HashMap;

import com.twilio.Twilio;
import com.twilio.http.TwilioRestClient;
import com.twilio.rest.api.v2010.Account;
import com.twilio.rest.api.v2010.account.Message;
//import com.twilio.sdk.TwilioRestException;
//import com.twilio.sdk.resource.factory.SmsFactory;
import com.twilio.type.PhoneNumber;

/**
 * 
 * @author steva
 *
 * <p>Class for testing https://www.twilio.com API for sending SMS</p>
 */
public class SmsServiceTest {
  
  private static String TWILIO_ACCOUNT_SID = "ACb5abbef16fcf0d3a3930a85c87a4194e";
  private static String TWILIO_AUTH_TOKEN = "c6ab6799b20f734f06abd963bfb5e586";
  
//  public static void main1(String[] args) throws TwilioRestException{
//    TwilioRestClient client = new TwilioRestClient(TWILIO_ACCOUNT_SID, TWILIO_AUTH_TOKEN);
//    
//    Account account = client.getAccount();
//    
//    SmsFactory factory = account.getSmsFactory();
//    
//    HashMap<String, String> message = new HashMap<String, String>();
//    message.put("To", "+381 64 2401534");
//    message.put("From", "+381 64 2401534");
//    message.put("Body", "Testing sms from twilio!");
//    
//    factory.create(message);
//  }
//  


  // Find your Account Sid and Token at twilio.com/user/account
  public static final String ACCOUNT_SID = "ACb5abbef16fcf0d3a3930a85c87a4194e";
  public static final String AUTH_TOKEN = "c6ab6799b20f734f06abd963bfb5e586";

  public static void main(String[] args) {
    Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

    Message message = Message.creator(new PhoneNumber("+381 64 2401534"),
        new PhoneNumber("+14695572852"), 
        "This is the ship that made the Kessel Run in fourteen parsecs?").create();

    System.out.println(message.getSid());
}}
