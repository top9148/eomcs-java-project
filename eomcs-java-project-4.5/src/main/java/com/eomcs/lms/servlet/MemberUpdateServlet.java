package com.eomcs.lms.servlet;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Map;

public class MemberUpdateServlet implements Servlet {
  
  Connection connection;

  public MemberUpdateServlet(Connection connection) {
    this.connection = connection;
  }
  
  @Override
  public void service(Map<String,String> paramMap, PrintWriter out) throws Exception {
    // 명령 예) /member/update?no=11&name=오호라2&email=ohora2@test.com&password=1111&photo=ohora2.jpeg&tel=1111-2222
    int no = Integer.parseInt(paramMap.get("no"));

    Statement stmt = null;
    try { 
      stmt = connection.createStatement();
      int count = stmt.executeUpdate("UPDATE MEMBER SET"
          + " NAME='" + paramMap.get("name") + "',"
          + " EMAIL='" + paramMap.get("email") + "',"
          + " PWD='" + paramMap.get("password") + "',"
          + " PHOTO='" + paramMap.get("photo") + "',"
          + " TEL='" + paramMap.get("tel") + "'"
          + " WHERE MNO=" + no);
      
      if (count > 0)
        out.println("회원을 변경했습니다.");
      else 
        out.println("해당 회원을 찾을 수 없습니다.");
      
    } catch (Exception e) {
      throw e;
    } finally {
      try {stmt.close();} catch (Exception ex) {}
    }
  }
}
