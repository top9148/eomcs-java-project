package com.eomcs.lms;

import java.util.Map;
import com.eomcs.context.ApplicationContextListener;
import com.eomcs.lms.dao.BoardDao;
import com.eomcs.lms.dao.LessonDao;
import com.eomcs.lms.dao.MemberDao;

public class DataLoaderListener implements ApplicationContextListener {

  @Override
  public void contextInitialized(Map<String,Object> context) {
    System.out.println("DataLoaderListener.contextInitialized() 실행!");

    try {
      
      String host = "localhost";
      int port = 8888;
      
      context.put("lessonDao", new LessonDao(host, port));
      context.put("memberDao", new MemberDao(host, port));
      context.put("boardDao", new BoardDao(host, port));
      
    } catch (Exception e) {
      System.out.println("서버 연결 오류!");
    }
  }

  @Override
  public void contextDestroyed(Map<String,Object> context) {
    System.out.println("DataLoaderListener.contextInitialized() 실행!");
  }
}
