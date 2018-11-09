package com.eomcs.lms.servlet;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import com.eomcs.lms.domain.Member;

public class MemberUpdateServlet implements Servlet {
  
  List<Member> list;
  
  public MemberUpdateServlet(List<Member> list) {
    this.list = list;
  }
  
  @Override
  public void service(Map<String,String> paramMap, PrintWriter out) throws Exception {
    int no = Integer.parseInt(paramMap.get("no"));

    int index = indexOfMember(no);
    if (index == -1) {
      out.println("해당 회원을 찾을 수 없습니다.");
      return;
    }
    
    Member member = list.get(index);
    
    try {
      // 기존 값 복제
      Member temp = member.clone();
      
      String input = paramMap.get("name");
      if (input.length() > 0) 
        temp.setName(input);
      
      if ((input = paramMap.get("email")) != null)
        temp.setEmail(input);
      
      if ((input = paramMap.get("password")) != null)
        temp.setPassword(input);
      
      if ((input = paramMap.get("photo")) != null)
        temp.setPhoto(input);
      
      if ((input = paramMap.get("tel")) != null)
        temp.setTel(input);
      
      list.set(index, temp);
      
      out.println("회원을 변경했습니다.");
      
    } catch (Exception e) {
      out.println("변경 중 오류 발생!");
    }
  }
  
  private int indexOfMember(int no) {
    for (int i = 0; i < list.size(); i++) {
      Member m = list.get(i);
      if (m.getNo() == no)
        return i;
    }
    return -1;
  }
}
