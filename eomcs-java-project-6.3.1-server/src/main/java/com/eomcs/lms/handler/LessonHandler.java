package com.eomcs.lms.handler;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import com.eomcs.lms.dao.LessonDao;
import com.eomcs.lms.domain.Lesson;
import com.eomcs.stereotype.CommandMapping;

@Component
public class LessonHandler {

  LessonDao lessonDao;

  public LessonHandler(LessonDao lessonDao) {
    this.lessonDao = lessonDao;
  }

  @CommandMapping("/lesson/add")
  public void add(HttpServletRequest request, HttpServletResponse response) throws Exception {
    PrintWriter out = response.getWriter();
    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head><title>수업관리</title></head>");
    out.println("<body>");
    out.println("<h1>수업 추가</h1>");

    Lesson lesson = new Lesson();
    lesson.setTitle(request.getParameter("title"));
    lesson.setContents(request.getParameter("contents"));
    lesson.setStartDate(Date.valueOf(request.getParameter("startDate")));
    lesson.setEndDate(Date.valueOf(request.getParameter("endDate")));
    lesson.setTotalHours(Integer.parseInt(request.getParameter("totalHours")));
    lesson.setDayHours(Integer.parseInt(request.getParameter("dayHours")));
    lesson.setOwnerNo(Integer.parseInt(request.getParameter("ownerNo")));

    lessonDao.add(lesson);
    out.println("<p>수업을 저장했습니다.</p>");

    out.println("</body>");
    out.println("</html>");
  }

  @CommandMapping("/lesson/delete")
  public void delete(BufferedReader in, PrintWriter out) {
    try {
      out.print("번호?\n!{}!\n"); out.flush();
      int no = Integer.parseInt(in.readLine());

      if (lessonDao.delete(no) > 0) 
        out.println("수업을 삭제했습니다.");
      else 
        out.println("해당 수업을 찾을 수 없습니다.");

    } catch (Exception e) {
      out.printf("%s : %s\n", e.toString(), e.getMessage());
    }
  }

  @CommandMapping("/lesson/detail")
  public void detail(HttpServletRequest request, HttpServletResponse response) throws Exception {
    PrintWriter out = response.getWriter();
    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head><title>수업관리</title></head>");
    out.println("<body>");
    out.println("<h1>수업 상세 정보</h1>");

    int no = Integer.parseInt(request.getParameter("no"));

    Lesson lesson = lessonDao.detail(no);

    if (lesson == null) { 
      out.println("<p>해당 수업을 찾을 수 없습니다.</p>");

    } else {
      out.println("<table border='1'>");
      out.printf("<tr><th>수업명</th><td>%s</td></tr>\n", lesson.getTitle());
      out.printf("<tr><th>설명</th><td>%s</td></tr>\n", lesson.getContents());
      out.printf("<tr><th>기간</th><td>%s ~ %s</td></tr>\n", lesson.getStartDate(), lesson.getEndDate());
      out.printf("<tr><th>총수업시간</th><td>%d</td></tr>\n", lesson.getTotalHours());
      out.printf("<tr><th>일수업시간</th><td>%d</td></tr>\n", lesson.getDayHours());
      out.printf("<tr><th>강사</th><td>%d</td></tr>\n", lesson.getOwnerNo());
      out.println("</table>");
    }
    out.println("</body>");
    out.println("</html>");
  }

  @CommandMapping("/lesson/list")
  public void list(HttpServletRequest request, HttpServletResponse response) throws Exception {

    PrintWriter out = response.getWriter();
    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head><title>수업관리</title></head>");
    out.println("<body>");
    out.println("<h1>수업 목록</h1>");

    out.println("<a href='/lesson/form.html'>새 수업</a>");
    out.println("<table border='1'>");
    out.println("<tr> <th>번호</th> <th>수업</th> <th>수업 기간</th> <th>수업시간</th> </tr>");

    List<Lesson> lessons = lessonDao.list(null);
    for (Lesson lesson : lessons) {
      out.println("<tr>");
      out.printf("<td>%d</td>\n", lesson.getNo());
      out.printf("<td><a href='detail?no=%d'>%s</a></td>\n", lesson.getNo(), lesson.getTitle());
      out.printf("<td>%s ~ %s</td>\n", lesson.getStartDate(), lesson.getEndDate());
      out.printf("<td>%d</td>\n", lesson.getTotalHours());
      out.println("</tr>");
    }
    out.println("</table>");
    out.println("</body>");
    out.println("</html>");
  }

  @CommandMapping("/lesson/search")
  public void search(BufferedReader in, PrintWriter out) {
    try {
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

  @CommandMapping("/lesson/update")
  public void update(BufferedReader in, PrintWriter out) {

    try {
      out.print("번호?\n!{}!\n"); out.flush();
      int no = Integer.parseInt(in.readLine());

      Lesson lesson = lessonDao.detail(no);
      if (lesson == null) { 
        out.println("해당 게시글을 찾을 수 없습니다.");
        return;
      }

      out.printf("수업명(%s)?\n!{}!\n", lesson.getTitle()); out.flush();
      String input = in.readLine();
      if (input.length() > 0) 
        lesson.setTitle(input);

      out.printf("설명(%s)?\n!{}!\n", lesson.getContents()); out.flush();
      if ((input = in.readLine()).length() > 0)
        lesson.setContents(input);

      out.printf("시작일(%s)?\n!{}!\n", lesson.getStartDate()); out.flush();
      if ((input = in.readLine()).length() > 0)
        lesson.setStartDate(Date.valueOf(input));

      out.printf("종료일(%s)?\n!{}!\n", lesson.getEndDate()); out.flush();
      if ((input = in.readLine()).length() > 0)
        lesson.setEndDate(Date.valueOf(input));

      out.printf("총수업시간(%d)?\n!{}!\n", lesson.getTotalHours()); out.flush();
      if ((input = in.readLine()).length() > 0)
        lesson.setTotalHours(Integer.parseInt(input));

      out.printf("일수업시간(%d)?\n!{}!\n", lesson.getDayHours()); out.flush();
      if ((input = in.readLine()).length() > 0)
        lesson.setDayHours(Integer.parseInt(input));

      out.printf("강사(%d)?\n!{}!\n", lesson.getOwnerNo()); out.flush();
      if ((input = in.readLine()).length() > 0)
        lesson.setOwnerNo(Integer.parseInt(input));

      lessonDao.update(lesson);
      out.println("수업을 변경했습니다.");

    } catch (Exception e) {
      out.printf("%s : %s\n", e.toString(), e.getMessage());
    }
  }
}
