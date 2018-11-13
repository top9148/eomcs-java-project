package com.eomcs.lms.servlet;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import com.eomcs.lms.domain.Lesson;

public class LessonListServlet implements Servlet {
  
  List<Lesson> list;

  public LessonListServlet(List<Lesson> list) {
    this.list = list;
  }
  
  @Override
  public void service(Map<String,String> paramMap, PrintWriter out) throws Exception {
    Lesson[] lessons = list.toArray(new Lesson[] {});
    for (Lesson lesson : lessons) {
      out.printf("%3d, %-15s, %10s ~ %10s, %4d\n", 
          lesson.getNo(), lesson.getTitle(), 
          lesson.getStartDate(), lesson.getEndDate(), lesson.getTotalHours());
    }
  }
}
