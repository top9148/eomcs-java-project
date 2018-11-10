package com.eomcs.lms.servlet;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.List;
import java.util.Map;
import com.eomcs.lms.domain.Member;

public class MemberAddServlet implements Servlet {
  
  List<Member> list;
  
  public MemberAddServlet(List<Member> list) {
    this.list = list;
  }
  
  @Override
  public void service(Map<String,String> paramMap, PrintWriter out) throws Exception {
    Member member = new Member();
    member.setNo(Integer.parseInt(paramMap.get("no")));
    member.setName(paramMap.get("name"));
    member.setEmail(paramMap.get("email"));
    member.setPassword(paramMap.get("password"));
    member.setPhoto(paramMap.get("photo"));
    member.setTel(paramMap.get("tel"));
    member.setRegisteredDate(new Date(System.currentTimeMillis())); 
    
    list.add(member);
    
    out.println("저장하였습니다.");
  }
}
