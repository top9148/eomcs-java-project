package com.eomcs.lms.servlet;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import com.eomcs.lms.domain.Member;

public class MemberDeleteServlet implements Servlet {
  
  List<Member> list;
  
  public MemberDeleteServlet(List<Member> list) {
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
    
    list.remove(index);
    
    out.println("회원을 삭제했습니다.");
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
