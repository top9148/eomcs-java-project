package com.eomcs.lms.servlet;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.Statement;
import java.util.Map;

public class LessonUpdateServlet implements Servlet {

  Connection connection;

  public LessonUpdateServlet(Connection connection) {
    this.connection = connection;
  }
  
  @Override
  public void service(Map<String,String> paramMap, PrintWriter out) throws Exception {
    // 명령 예) /lesson/update?no=6&title=자바 프로젝트&contents=프로젝트 강의입니다.&startDate=2019-1-1&endDate=2019-2-7&totalHours=80&dayHours=9&memberNo=1
    int no = Integer.parseInt(paramMap.get("no"));

    Statement stmt = null;
    try { 
      stmt = connection.createStatement();
      int count = stmt.executeUpdate("UPDATE LESSON SET"
          + " TITLE='" + paramMap.get("title") + "',"
          + " CONT='" + paramMap.get("contents") + "',"
          + " SDT='" + Date.valueOf(paramMap.get("startDate")) + "',"
          + " EDT='" + Date.valueOf(paramMap.get("endDate")) + "',"
          + " TOT_HR=" + Integer.parseInt(paramMap.get("totalHours")) + ","
          + " DAY_HR=" + Integer.parseInt(paramMap.get("dayHours")) + "," 
          + " MNO=" + Integer.parseInt(paramMap.get("memberNo"))
          + " WHERE LNO=" + no);
      
      if (count > 0)
        out.println("수업을 변경했습니다.");
      else 
        out.println("해당 수업을 찾을 수 없습니다.");
      
    } catch (Exception e) {
      throw e;
    } finally {
      try {stmt.close();} catch (Exception ex) {}
    }
  }
}
