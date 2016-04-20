package net.ddns.hnmirror.dao;

import net.ddns.hnmirror.dao.impl.StoryDAOImpl;

public class Factory {
      
      private static StoryDAO storyDAO = null;
      private static Factory instance = null;

      public static synchronized Factory getInstance(){
            if (instance == null){
              instance = new Factory();
            }
            return instance;
      }

      public StoryDAO getStoryDAO(){
            if (storyDAO == null){
              storyDAO = new StoryDAOImpl();
            }
            return storyDAO;
      }   
}