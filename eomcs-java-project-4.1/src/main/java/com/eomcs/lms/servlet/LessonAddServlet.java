package com.eomcs.lms.servlet;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.List;
import java.util.Map;
import com.eomcs.lms.domain.Lesson;

public class LessonAddServlet implements Servlet {

  List<Lesson> list;

  public LessonAddServlet(List<Lesson> list) {
    this.list = list;
  }

  @Override
  public void service(Map<String,String> paramMap, PrintWriter out) throws Exception {
    Lesson lesson = new Lesson();
    lesson.setNo(Integer.parseInt(paramMap.get("n")));
    lesson.setTitle(paramMap.get("t"));
    lesson.setContents(paramMap.get("c"));
    lesson.setStartDate(Date.valueOf(paramMap.get("sd")));
    lesson.setEndDate(Date.valueOf(paramMap.get("ed")));
    lesson.setTotalHours(Integer.parseInt(paramMap.get("th")));
    lesson.setDayHours(Integer.parseInt(paramMap.get("dh")));

    list.add(lesson);

    out.println("저장하였습니다.");
  }
  
}
