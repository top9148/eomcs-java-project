package com.eomcs.lms;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.eomcs.context.ApplicationContextListener;
import com.eomcs.lms.domain.Board;
import com.eomcs.lms.domain.Lesson;
import com.eomcs.lms.domain.Member;
import com.eomcs.lms.servlet.BoardAddServlet;
import com.eomcs.lms.servlet.BoardDeleteServlet;
import com.eomcs.lms.servlet.BoardDetailServlet;
import com.eomcs.lms.servlet.BoardListServlet;
import com.eomcs.lms.servlet.BoardUpdateServlet;
import com.eomcs.lms.servlet.LessonAddServlet;
import com.eomcs.lms.servlet.LessonDeleteServlet;
import com.eomcs.lms.servlet.LessonDetailServlet;
import com.eomcs.lms.servlet.LessonListServlet;
import com.eomcs.lms.servlet.LessonUpdateServlet;
import com.eomcs.lms.servlet.MemberAddServlet;
import com.eomcs.lms.servlet.MemberDeleteServlet;
import com.eomcs.lms.servlet.MemberDetailServlet;
import com.eomcs.lms.servlet.MemberListServlet;
import com.eomcs.lms.servlet.MemberUpdateServlet;
import com.eomcs.lms.servlet.Servlet;

public class ServerApp {
  
  //Observer를 보관할 저장소
  ArrayList<ApplicationContextListener> applicationContextListeners = new ArrayList<>();

  // Observer와 서로 공유할 값을 저장하는 보관소
  Map<String,Object> context = new HashMap<>();
 
  // 명령를 처리할 서블릿을 저장하는 보관소
  Map<String,Servlet> servletMap = new HashMap<>();
  
  static final int DEFAULT_PORT = 8888;
  
  ServerSocket serverSocket;
  
  public ServerApp() throws Exception {
    this(DEFAULT_PORT);
  }
  
  public ServerApp(int port) throws Exception {
    serverSocket = new ServerSocket(port);
  }
  
  public void service() {
    // 서비스를 실행하기 전에 등록된 모든 Observer를 호출하여 시작을 알린다.
    for (ApplicationContextListener listener : applicationContextListeners) {
      listener.contextInitialized(context);
    }
    
    // 핸들러가 사용할 데이터는 context에서 꺼내 준다.
    @SuppressWarnings("unchecked")
    List<Lesson> lessonList = (List<Lesson>) context.get("lessonList");
    
    servletMap.put("/lesson/add", new LessonAddServlet(lessonList));
    servletMap.put("/lesson/list", new LessonListServlet(lessonList));
    servletMap.put("/lesson/detail", new LessonDetailServlet(lessonList));
    servletMap.put("/lesson/update", new LessonUpdateServlet(lessonList));
    servletMap.put("/lesson/delete", new LessonDeleteServlet(lessonList));
    
    @SuppressWarnings("unchecked")
    List<Member> memberList = (List<Member>) context.get("memberList");
    
    servletMap.put("/member/add", new MemberAddServlet(memberList));
    servletMap.put("/member/list", new MemberListServlet(memberList));
    servletMap.put("/member/detail", new MemberDetailServlet(memberList));
    servletMap.put("/member/update", new MemberUpdateServlet(memberList));
    servletMap.put("/member/delete", new MemberDeleteServlet(memberList));
    
    @SuppressWarnings("unchecked")
    List<Board> boardList = (List<Board>) context.get("boardList");
    
    servletMap.put("/board/add", new BoardAddServlet(boardList));
    servletMap.put("/board/list", new BoardListServlet(boardList));
    servletMap.put("/board/detail", new BoardDetailServlet(boardList));
    servletMap.put("/board/update", new BoardUpdateServlet(boardList));
    servletMap.put("/board/delete", new BoardDeleteServlet(boardList));
    
    server: 
    while (true) {
      try (Socket s = serverSocket.accept();
          PrintWriter out = new PrintWriter(new BufferedOutputStream(s.getOutputStream()));
          BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()))) {

        while (true) {
          // 클라이언트가 보낸 데이터를 읽는다.
          String command = in.readLine();

          if (command.toLowerCase().equals("quit")) {
            break;
          } 
          
          if (command.toLowerCase().equals("shutdown")) {
            // 서버를 완전히 종료한다.
            break server;
          }
          
          // 클라이언트가 보낸 명령에서 '?' 이후의 파라미터 값을 분리한다.
          // 예) /lesson/add?n=1&t=자바프로그래밍&c=설명&sd=2019-1-1&ed=2019-2-2&th=1000&dh=8
          String[] values = command.split("\\?");
          
          String servletPath = values[0];
          
          Map<String,String> paramMap = new HashMap<>();
          if (values.length == 2) {
            String[] params = values[1].split("&");
            for (String p : params) {
              String[] kv = p.split("=");
              if (kv.length > 1)
                paramMap.put(kv[0], kv[1]);
              else 
                paramMap.put(kv[0], "");
            }
          }
          
          // 명령어를 처리할 서블릿을 찾는다.
          Servlet servlet = servletMap.get(servletPath);
          
          if (servlet != null) {
            try {
              servlet.service(paramMap, out);
            } catch (Exception e) {
              out.println("서블릿 실행 중 오류 발생!");
              out.printf("%s: %s\n", e, e.getMessage());
            }
          } else {
            out.println("해당 명령을 처리할 수 없습니다.");
          }
          
          out.println();
          out.flush(); // 버퍼에 임시 보관된 출력물을 서버로 보낸다.
        }
      } catch (Exception e) {
        System.out.println("서버 통신 오류!");
        e.printStackTrace();
      }
    }
    
    // 서비스를 종료하기 전에 등록된 모든 Observer를 호출하여 종료를 알린다.
    for (ApplicationContextListener listener : applicationContextListeners) {
      listener.contextDestroyed(context);
    }
  }
  
  // Observer를 등록하는 메서드
  private void addApplicationContextListener(ApplicationContextListener listener) {
    applicationContextListeners.add(listener);
  }
  
  public static void main(String[] args) throws Exception {
    ServerApp serverApp = new ServerApp(8888);
    
    // ServerApp 객체에 Observer를 등록한다.
    serverApp.addApplicationContextListener(new DataLoaderListener());
    
    System.out.println("서버 실행 중!");
    
    // 서버를 실행한다.
    serverApp.service();
  }
}
