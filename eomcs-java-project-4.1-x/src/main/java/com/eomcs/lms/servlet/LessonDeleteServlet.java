package com.eomcs.lms.servlet;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import com.eomcs.lms.domain.Lesson;

public class LessonDeleteServlet implements Servlet {

  List<Lesson> list;

  public LessonDeleteServlet(List<Lesson> list) {
    this.list = list;
  }

  @Override
  public void service(Map<String,String> paramMap, PrintWriter out) throws Exception {
    int no = Integer.parseInt(paramMap.get("n"));

    int index = indexOfLesson(no);
    if (index == -1) {
      out.println("해당 수업을 찾을 수 없습니다.");
      return;
    }
    
    list.remove(index);
    
    out.println("수업을 삭제했습니다.");
  }
  
  private int indexOfLesson(int no) {
    for (int i = 0; i < list.size(); i++) {
      Lesson l = list.get(i);
      if (l.getNo() == no)
        return i;
    }
    return -1;
  }
}
