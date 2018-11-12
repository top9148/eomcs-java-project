package com.eomcs.lms.servlet;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.List;
import java.util.Map;
import com.eomcs.lms.domain.Lesson;

public class LessonUpdateServlet implements Servlet {

  List<Lesson> list;

  public LessonUpdateServlet(List<Lesson> list) {
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
    
    try {
      // 일단 기존 값을 복제한다.
      Lesson temp = lesson.clone();
      
      String input = paramMap.get("t");
      
      if (input.length() > 0) 
        temp.setTitle(input);
      
      if ((input = paramMap.get("c")) != null)
        temp.setContents(input);
      
      if ((input = paramMap.get("sd")) != null)
        temp.setStartDate(Date.valueOf(input));
      
      if ((input = paramMap.get("ed")) != null)
        temp.setEndDate(Date.valueOf(input));
      
      if ((input = paramMap.get("th")) != null)
        temp.setTotalHours(Integer.parseInt(input));
      
      if ((input = paramMap.get("dh")) != null)
        temp.setDayHours(Integer.parseInt(input));
      
      list.set(index, temp);
      
      out.println("수업을 변경했습니다.");
      
    } catch (Exception e) {
      out.println("변경 중 오류 발생!" + e.toString());
    }
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
