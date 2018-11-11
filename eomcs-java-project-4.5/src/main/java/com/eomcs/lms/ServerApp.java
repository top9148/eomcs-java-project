package com.eomcs.lms;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import com.eomcs.context.ApplicationContextListener;
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
  
  // 스레드 풀
  ExecutorService executorService = Executors.newCachedThreadPool();
  
  static final int DEFAULT_PORT = 8888;
  
  ServerSocket serverSocket;
  
  public ServerApp() throws Exception {
    this(DEFAULT_PORT);
  }
  
  public ServerApp(int port) throws Exception {
    serverSocket = new ServerSocket(port);
  }
  
  public void service() throws Exception {
    
    // 서비스를 실행하기 전에 등록된 모든 Observer를 호출하여 시작을 알린다.
    for (ApplicationContextListener listener : applicationContextListeners) {
      listener.contextInitialized(context);
    }
    
    // JdbcContextListener 에서 준비한 Connection 객체를 꺼낸다.
    Connection connection = (Connection) context.get("connection");
    
    servletMap.put("/lesson/add", new LessonAddServlet(connection));
    servletMap.put("/lesson/list", new LessonListServlet(connection));
    servletMap.put("/lesson/detail", new LessonDetailServlet(connection));
    servletMap.put("/lesson/update", new LessonUpdateServlet(connection));
    servletMap.put("/lesson/delete", new LessonDeleteServlet(connection));
    
    servletMap.put("/member/add", new MemberAddServlet(connection));
    servletMap.put("/member/list", new MemberListServlet(connection));
    servletMap.put("/member/detail", new MemberDetailServlet(connection));
    servletMap.put("/member/update", new MemberUpdateServlet(connection));
    servletMap.put("/member/delete", new MemberDeleteServlet(connection));
    /*
    @SuppressWarnings("unchecked")
    List<Board> boardList = (List<Board>) context.get("boardList");
    
    servletMap.put("/board/add", new BoardAddServlet(boardList));
    servletMap.put("/board/list", new BoardListServlet(boardList));
    servletMap.put("/board/detail", new BoardDetailServlet(boardList));
    servletMap.put("/board/update", new BoardUpdateServlet(boardList));
    servletMap.put("/board/delete", new BoardDeleteServlet(boardList));
    */
    while (true) {
      try {
        executorService.submit(new RequestHandler(serverSocket.accept()));
        
      } catch (Exception e) {
        System.out.println("클라이언트와의 연결 실패!");
      }
    }
  }
  
  private void shutdown() {
    // 서비스를 종료하기 전에 등록된 모든 Observer를 호출하여 종료를 알린다.
    for (ApplicationContextListener listener : applicationContextListeners) {
      listener.contextDestroyed(context);
    }
    executorService.shutdown();
    
    System.exit(0);
  }

  // Observer를 등록하는 메서드
  private void addApplicationContextListener(ApplicationContextListener listener) {
    applicationContextListeners.add(listener);
  }
  
  public static void main(String[] args) throws Exception {
    ServerApp serverApp = new ServerApp(8888);
    
    serverApp.addApplicationContextListener(new JdbcContextListener());
    
    System.out.println("서버 실행 중!");
    // 서버를 실행한다.
    serverApp.service();
  }
  
  class RequestHandler implements Runnable {
    
    Socket socket;
    
    public RequestHandler(Socket socket) {
      this.socket = socket;
    }
    
    @Override
    public void run() {
      try (Socket s = socket;
          PrintWriter out = new PrintWriter(new BufferedOutputStream(s.getOutputStream()));
          BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()))) {
        
        // 현재 이 요청을 처리하는 스레드 이름을 함께 출력!
        System.out.printf("클라이언트와 연결됨! - %s\n", Thread.currentThread().getName());
        
        // 클라이언트가 보낸 데이터를 읽는다.
        String command = in.readLine();
        System.out.printf("클라이언트의 %s 요청 처리 중...\n", command);
        
        if (command.toLowerCase().equals("quit")) {
          return;
        } 
        
        if (command.toLowerCase().equals("shutdown")) {
          // 중첩 클래스의 장점은 바깥 클래스의 인스턴스 멤버에 쉽게 접근할 수 있다는 것이다.
          shutdown();
          return;
        }
        
        // 클라이언트가 보낸 명령에서 '?' 이후의 파라미터 값을 분리한다.
        // 예) /lesson/add?title=테스트입니다.&contents=내용입니다.&startDate=2019-1-1&endDate=2019-2-2&totalHours=80&dayHours=8
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
            e.printStackTrace();
          }
        } else {
          out.println("해당 명령을 처리할 수 없습니다.");
        }
        
        out.println();
        out.flush(); // 버퍼에 임시 보관된 출력물을 서버로 보낸다.
        
      } catch (Exception e) {
        System.out.println("서버 통신 오류!");
        e.printStackTrace();
      } finally {
        System.out.println("------------------------------- 클라이언트와 연결 종료!");
      }
    }
  }
}
