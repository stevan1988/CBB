package com.crossballbox.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.Test;

public class AdminControllerTest {

  @Test
  public void dateParseTest(){
    String dateBirth = "4/5/1988";
     DateTimeFormatter formatter = DateTimeFormatter.ofPattern("[dd/MM/yyyy][d/MM/yyyy][dd/M/yyyy][d/M/yyyy]");
    LocalDate dateTime = LocalDate.parse(dateBirth, formatter);
    
    System.out.println("date: " + dateTime);
    

//    String dateBirth = "4/5/1988";
//    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy, dd/M/yyyy, d/MM/yyyy, d/M/yyyy");
//    LocalDate dateTime = LocalDate.parse(dateBirth, formatter);
//    System.out.println(dateTime);
  }
}
