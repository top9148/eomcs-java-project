package com.eomcs.lms.servlet;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Map;

public class LessonDeleteServlet implements Servlet {

  Connection connection;

  public LessonDeleteServlet(Connection connection) {
    this.connection = connection;
  }

  @Override
  public void service(Map<String,String> paramMap, PrintWriter out) throws Exception {
    // 명령 예) /lesson/delete?no=6
    int no = Integer.parseInt(paramMap.get("no"));

    Statement stmt = null;
    try { 
      stmt = connection.createStatement();
      int count = stmt.executeUpdate("DELETE FROM LESSON WHERE LNO=" + no);
      
      if (count > 0)
        out.println("수업을 삭제했습니다.");
      else 
        out.println("해당 수업을 찾을 수 없습니다.");
      
    } catch (Exception e) {
      throw e;
    } finally {
      try {stmt.close();} catch (Exception ex) {}
    }
  }
}
