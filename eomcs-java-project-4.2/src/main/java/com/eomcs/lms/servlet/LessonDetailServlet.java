package com.eomcs.lms.servlet;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import com.eomcs.lms.domain.Lesson;

public class LessonDetailServlet implements Servlet {

  List<Lesson> list;

  public LessonDetailServlet(List<Lesson> list) {
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

    Lesson lesson = list.get(index);

    out.printf("수업명: %s\n", lesson.getTitle());
    out.printf("설명: %s\n", lesson.getContents());
    out.printf("기간: %s ~ %s\n", lesson.getStartDate(), lesson.getEndDate());
    out.printf("총수업시간: %d\n", lesson.getTotalHours());
    out.printf("일수업시간: %d\n", lesson.getDayHours());
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
