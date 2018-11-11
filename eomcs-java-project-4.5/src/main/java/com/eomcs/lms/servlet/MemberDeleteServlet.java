package com.eomcs.lms.servlet;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Map;

public class MemberDeleteServlet implements Servlet {
  
  Connection connection;

  public MemberDeleteServlet(Connection connection) {
    this.connection = connection;
  }
  
  @Override
  public void service(Map<String,String> paramMap, PrintWriter out) throws Exception {
    // 명령 예) /member/delete?no=11
    int no = Integer.parseInt(paramMap.get("no"));

    Statement stmt = null;
    try { 
      stmt = connection.createStatement();
      int count = stmt.executeUpdate("DELETE FROM MEMBER WHERE MNO=" + no);
      
      if (count > 0)
        out.println("회원을 삭제했습니다.");
      else 
        out.println("해당 회원을 찾을 수 없습니다.");
      
    } catch (Exception e) {
      throw e;
    } finally {
      try {stmt.close();} catch (Exception ex) {}
    }
  }
}
