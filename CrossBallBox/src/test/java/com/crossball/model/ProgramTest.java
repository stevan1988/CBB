package com.crossball.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.crossballbox.model.Programs;

public class ProgramTest {

  @Test
  public void getPropperNameFromEnum(){
    
    String program = "CROSSFIT";
    Programs prog = Programs.valueOf(program);
    
    assertEquals("CROSSFIT", prog.toString());
  }
}
