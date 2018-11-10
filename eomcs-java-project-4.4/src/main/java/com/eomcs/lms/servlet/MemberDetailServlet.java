package com.eomcs.lms.servlet;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import com.eomcs.lms.domain.Member;

public class MemberDetailServlet implements Servlet {
  
  List<Member> list;
  
  public MemberDetailServlet(List<Member> list) {
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

    out.printf("이름: %s\n", member.getName());
    out.printf("이메일: %s\n", member.getEmail());
    out.printf("암호: %s\n", member.getPassword());
    out.printf("사진: %s\n", member.getPhoto());
    out.printf("전화: %s\n", member.getTel());
    out.printf("가입일: %s\n", member.getRegisteredDate());
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
