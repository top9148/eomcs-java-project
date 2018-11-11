package com.eomcs.lms.servlet;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Map;

public class LessonDetailServlet implements Servlet {

  Connection connection;

  public LessonDetailServlet(Connection connection) {
    this.connection = connection;
  }

  @Override
  public void service(Map<String,String> paramMap, PrintWriter out) throws Exception {
    // 명령 예) /lesson/detail?no=3
    int no = Integer.parseInt(paramMap.get("no"));

    Statement stmt = null;
    ResultSet rs = null; 
    
    try {
      stmt = connection.createStatement();
      rs = stmt.executeQuery("SELECT LNO,TITLE,CONT,SDT,EDT,TOT_HR,DAY_HR FROM LESSON"
          + " WHERE LNO=" + no);
      if (rs.next()) {
        out.printf("수업명: %s\n", rs.getString("TITLE"));
        out.printf("설명: %s\n", rs.getString("CONT"));
        out.printf("기간: %s ~ %s\n", rs.getDate("SDT"), rs.getDate("EDT"));
        out.printf("총수업시간: %d\n", rs.getInt("TOT_HR"));
        out.printf("일수업시간: %d\n", rs.getInt("DAY_HR"));
      } else {
        out.println("해당 수업을 찾을 수 없습니다.");
      }
    } catch (Exception e) {
      throw e;
    } finally {
      try {rs.close();} catch (Exception ex) {}
      try {stmt.close();} catch (Exception ex) {}
    }
  }
}
