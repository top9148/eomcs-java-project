package com.eomcs.lms;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Map;
import com.eomcs.context.ApplicationContextListener;

public class JdbcContextListener implements ApplicationContextListener {
  @Override
  public void contextInitialized(Map<String, Object> context) {
    try {
      // MariaDB에 연결하기
      Connection connection = DriverManager.getConnection(
          "jdbc:mariadb://localhost:3306/eomcs?user=eomcs&password=1111");
      context.put("connection", connection);
      System.out.println("MariaDB에 연결했습니다.");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  @Override
  public void contextDestroyed(Map<String, Object> context) {
    try {
      System.out.println("MariaDB의 연결을 끊습니다.");
      // MariaDB에 연결 끊기
      Connection connection = (Connection) context.get("connection");
      connection.close();
    } catch (Exception e) {
    }
  }
}
