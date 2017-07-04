package com.crossballbox.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.crossballbox.controller.AdminController;

@Component
public class ScheduledTaskService {

  private static final Logger logger = LoggerFactory.getLogger(ScheduledTaskService.class);

  // second, minute, hour, day of month, month, day(s) of week
  // * "0 0 * * * *" = the top of every hour of every day.
  // * "*/10 * * * * *" = every ten seconds.
  // * "0 0 8-10 * * *" = 8, 9 and 10 o'clock of every day.
  // * "0 0/30 8-10 * * *" = 8:00, 8:30, 9:00, 9:30 and 10 o'clock every day.
  // * "0 0 9-17 * * MON-FRI" = on the hour nine-to-five weekdays
  // * "0 0 0 25 12 ?" = every Christmas Day at midnight
//  @Scheduled(cron = "0 1 1 * * ?", zone = "CST") // in midnight every 1st in month
  @Scheduled(cron = "0 0 * * * ?", zone = "CST") //every day at midnight
//  @Scheduled(cron = "*/10 * * * * *", zone = "CST") -- every 10sec
  public String getAllMemberFees() {
    logger.info(
        "Calculate member fees for all users and notify admin user with info of all users that are not payed member fees for current month!");
    return null;
    
    //send e mail
  }
}
