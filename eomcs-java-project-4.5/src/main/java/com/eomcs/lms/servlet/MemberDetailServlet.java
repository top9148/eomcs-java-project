package com.eomcs.lms.servlet;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Map;

public class MemberDetailServlet implements Servlet {
  
  Connection connection;

  public MemberDetailServlet(Connection connection) {
    this.connection = connection;
  }

  @Override
  public void service(Map<String,String> paramMap, PrintWriter out) throws Exception {
    // 명령 예) /member/detail?no=3
    int no = Integer.parseInt(paramMap.get("no"));

    Statement stmt = null;
    ResultSet rs = null; 
    
    try {
      stmt = connection.createStatement();
      rs = stmt.executeQuery("SELECT MNO,NAME,EMAIL,PWD,PHOTO,TEL,CDT FROM MEMBER"
          + " WHERE MNO=" + no);
      if (rs.next()) {
        out.printf("이름: %s\n", rs.getString("NAME"));
        out.printf("이메일: %s\n", rs.getString("EMAIL"));
        out.printf("암호: %s\n", rs.getString("PWD"));
        out.printf("사진: %s\n", rs.getString("PHOTO"));
        out.printf("전화: %s\n", rs.getString("TEL"));
        out.printf("가입일: %s\n", rs.getDate("CDT"));
      } else {
        out.println("해당 회원을 찾을 수 없습니다.");
      }
    } catch (Exception e) {
      throw e;
    } finally {
      try {rs.close();} catch (Exception ex) {}
      try {stmt.close();} catch (Exception ex) {}
    }
  }
}
