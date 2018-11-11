package com.eomcs.lms.servlet;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Map;

public class MemberListServlet implements Servlet {
  
  Connection connection;

  public MemberListServlet(Connection connection) {
    this.connection = connection;
  }
  
  @Override
  public void service(Map<String,String> paramMap, PrintWriter out) throws Exception {
    // 명령 예) /member/list
    Statement stmt = null;
    ResultSet rs = null;
    
    try {
      stmt = connection.createStatement();
      rs = stmt.executeQuery("SELECT MNO,NAME,EMAIL,TEL,CDT FROM MEMBER");
      while (rs.next()) {
        out.printf("%3d, %-4s, %-20s, %-15s, %s\n",  
            rs.getInt("MNO"), rs.getString("NAME"), 
            rs.getString("EMAIL"), rs.getString("TEL"), rs.getDate("CDT"));
      }
    } catch (Exception e) {
      throw e;
    } finally {
      try {rs.close();} catch (Exception ex) {}
      try {stmt.close();} catch (Exception ex) {}
    }
  }
}
