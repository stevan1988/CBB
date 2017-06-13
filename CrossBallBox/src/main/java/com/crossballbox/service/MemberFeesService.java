package com.crossballbox.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.MonthDay;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class MemberFeesService {

  /**
   * calculating if member fees is paid for more than one month
   * 
   * @param date - format of date is yyyy-MM-dd, i.e. "1986-04-08"
   * @return
   */
  public boolean calculate(String date) {

    boolean ret = false;
    LocalDate today = LocalDate.now();
    
    DateTimeFormatter formatter =
        DateTimeFormatter.ofPattern("" + "[yyyy-MM-dd]" + "[yyyy-M-dd]" + "[yyyy-M-d]" + "[yyyy-MM-d]");
  
//    System.out.println(LocalDateTime.parse("2016/03/23 22:00:00.256145", formatter));

//    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd || yyyy-M-dd");
    LocalDate dateTime = LocalDate.parse(date, formatter);



    LocalDate previousMonth = today.minus(1, ChronoUnit.MONTHS);
    System.out.println("Today is : " + today);
    System.out.println("Date after 1 week : " + previousMonth);

    if (dateTime.isBefore(previousMonth)) {
      ret = true;
    }

    return ret;
  }
  
//  public static void main(String[] args){
//    System.out.println(calculate("2017-5-20"));
//  }

  public void isBirthay(String date) {
    LocalDateTime today = LocalDateTime.now();
    LocalDate dateOfBirth = LocalDate.of(2010, 01, 14);
    MonthDay birthday = MonthDay.of(dateOfBirth.getMonth(), dateOfBirth.getDayOfMonth());
    MonthDay currentMonthDay = MonthDay.from(today);
    if (currentMonthDay.equals(birthday)) {
      System.out.println("Many Many happy returns of the day !!");
    } else {
      System.out.println("Sorry, today is not your birthday");
    }

    // Read more:
    // http://javarevisited.blogspot.com/2015/03/20-examples-of-date-and-time-api-from-Java8.html#ixzz4i1vvBiBb
  }

}
