package com.eomcs.lms.handler;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import com.eomcs.lms.dao.LessonDao;
import com.eomcs.lms.domain.Lesson;

public class LessonSearchCommand implements Command {
  
  SqlSessionFactory sqlSessionFactory;

  public LessonSearchCommand(SqlSessionFactory sqlSessionFactory) {
    this.sqlSessionFactory = sqlSessionFactory;
  }

  @Override
  public void execute(BufferedReader in, PrintWriter out) {
    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      Map<String,Object> params = new HashMap<>();
      
      out.print("수업명?\n!{}!\n"); out.flush();
      String value = in.readLine();
      if (value.length() > 0) {
        params.put("title", value);
      }
      
      out.print("설명?\n!{}!\n"); out.flush();
      value = in.readLine();
      if (value.length() > 0) {
        params.put("contents", value);
      }
      
      out.print("시작일(<=)?\n!{}!\n"); out.flush();
      value = in.readLine();
      if (value.length() > 0) {
        params.put("startDate", Date.valueOf(value));
      }
      
      out.print("종료일(<=)?\n!{}!\n"); out.flush();
      value = in.readLine();
      if (value.length() > 0) {
        params.put("endDate", Date.valueOf(value));
      }
      
      LessonDao lessonDao = sqlSession.getMapper(LessonDao.class); 
      List<Lesson> lessons = lessonDao.list(params);
      if (lessons == null) { 
        out.println("서버에서 데이터를 가져오는데 오류 발생!");
        return;
      }
      
      for (Lesson lesson : lessons) {
        out.printf("%3d, %-15s, %10s ~ %10s, %4d\n", 
            lesson.getNo(), lesson.getTitle(), 
            lesson.getStartDate(), lesson.getEndDate(), lesson.getTotalHours());
      }
      
    } catch (Exception e) {
      out.printf("%s : %s\n", e.toString(), e.getMessage());
    }
  }
}
