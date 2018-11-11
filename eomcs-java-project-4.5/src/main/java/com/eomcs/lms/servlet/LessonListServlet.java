package com.eomcs.lms.servlet;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Map;

public class LessonListServlet implements Servlet {
  
  Connection connection;

  public LessonListServlet(Connection connection) {
    this.connection = connection;
  }
  
  @Override
  public void service(Map<String,String> paramMap, PrintWriter out) throws Exception {
    // 명령 예) /lesson/list
    Statement stmt = null;
    ResultSet rs = null;
    
    try {
      stmt = connection.createStatement();
      rs = stmt.executeQuery("SELECT LNO,TITLE,SDT,EDT,TOT_HR FROM LESSON");
      while (rs.next()) {
        out.printf("%3d, %-15s, %10s ~ %10s, %4d\n", 
            rs.getInt("LNO"), rs.getString("TITLE"), 
            rs.getDate("SDT"), rs.getDate("EDT"), rs.getInt("TOT_HR"));
      }
    } catch (Exception e) {
      throw e;
    } finally {
      try {rs.close();} catch (Exception ex) {}
      try {stmt.close();} catch (Exception ex) {}
    }
  }
}
