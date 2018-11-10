package com.eomcs.lms.servlet;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import com.eomcs.lms.domain.Member;

public class MemberListServlet implements Servlet {
  
  List<Member> list;
  
  public MemberListServlet(List<Member> list) {
    this.list = list;
  }
  
  @Override
  public void service(Map<String,String> paramMap, PrintWriter out) throws Exception {
    Member[] members = list.toArray(new Member[] {});
    for (Member member : members) {
      out.printf("%3d, %-4s, %-20s, %-15s, %s\n", 
          member.getNo(), member.getName(), 
          member.getEmail(), member.getTel(), member.getRegisteredDate());
    }
  }
}
