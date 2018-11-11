package com.eomcs.lms.servlet;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Map;

public class MemberAddServlet implements Servlet {
  
  Connection connection;

  public MemberAddServlet(Connection connection) {
    this.connection = connection;
  }
  
  @Override
  public void service(Map<String,String> paramMap, PrintWriter out) throws Exception {
    // 명령 예) /member/add?name=오호라&email=ohora@test.com&password=1111&photo=ohora.jpeg&tel=1111-2222
    Statement stmt = null;
    try { 
      stmt = connection.createStatement();
      stmt.executeUpdate("INSERT INTO MEMBER(NAME,EMAIL,PWD,PHOTO,TEL)"
          + " VALUES('" + paramMap.get("name")
          + "','" + paramMap.get("email")
          + "','" + paramMap.get("password")
          + "','" + paramMap.get("photo")
          + "','" + paramMap.get("tel") + "')");
      
      out.println("회원을 저장했습니다.");
      
    } catch (Exception e) {
      throw e;
    } finally {
      try {stmt.close();} catch (Exception ex) {}
    }
  }
}
