package com.crossballbox.wrapper;

import java.util.List;

import com.crossballbox.model.UserProgress;

/**
 * 
 * @author steva
 *
 *         <p>
 *         This class is used for wrapping information on user_detalis.html page to load and save
 *         UserProgress list of information for every user.
 *         </p>
 */
public class UserProgressListWrapper {

  private List<UserProgress> userProgressList;

  public List<UserProgress> getUserProgressList() {
    return userProgressList;
  }

  public void setUserProgressList(List<UserProgress> userProgressList) {
    this.userProgressList = userProgressList;
  }

}
