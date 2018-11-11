package com.eomcs.lms.servlet;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.Statement;
import java.util.Map;

public class LessonAddServlet implements Servlet {

  Connection connection;

  public LessonAddServlet(Connection connection) {
    this.connection = connection;
  }

  @Override
  public void service(Map<String,String> paramMap, PrintWriter out) throws Exception {
    // 명령 예) /lesson/add?title=테스트입니다.&contents=내용입니다.&startDate=2019-1-1&endDate=2019-2-2&totalHours=80&dayHours=8&memberNo=1
    Statement stmt = null;
    try { 
      stmt = connection.createStatement();
      stmt.executeUpdate("INSERT INTO LESSON(TITLE,CONT,SDT,EDT,TOT_HR,DAY_HR,MNO)"
          + " VALUES('" + paramMap.get("title")
          + "','" + paramMap.get("contents")
          + "','" + Date.valueOf(paramMap.get("startDate"))
          + "','" + Date.valueOf(paramMap.get("endDate"))
          + "'," + Integer.parseInt(paramMap.get("totalHours"))
          + "," + Integer.parseInt(paramMap.get("dayHours")) 
          + "," + Integer.parseInt(paramMap.get("memberNo")) + ")");
      
      out.println("수업을 저장했습니다.");
      
    } catch (Exception e) {
      throw e;
    } finally {
      try {stmt.close();} catch (Exception ex) {}
    }
  }
  
}
