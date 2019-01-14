package com.eomcs.lms;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import com.eomcs.context.ApplicationContextListener;
import com.eomcs.lms.dao.BoardDao;
import com.eomcs.lms.dao.LessonDao;
import com.eomcs.lms.dao.MemberDao;
import com.eomcs.lms.handler.BoardAddCommand;
import com.eomcs.lms.handler.BoardDeleteCommand;
import com.eomcs.lms.handler.BoardDetailCommand;
import com.eomcs.lms.handler.BoardListCommand;
import com.eomcs.lms.handler.BoardUpdateCommand;
import com.eomcs.lms.handler.Command;
import com.eomcs.lms.handler.LessonAddCommand;
import com.eomcs.lms.handler.LessonDeleteCommand;
import com.eomcs.lms.handler.LessonDetailCommand;
import com.eomcs.lms.handler.LessonListCommand;
import com.eomcs.lms.handler.LessonUpdateCommand;
import com.eomcs.lms.handler.MemberAddCommand;
import com.eomcs.lms.handler.MemberDeleteCommand;
import com.eomcs.lms.handler.MemberDetailCommand;
import com.eomcs.lms.handler.MemberListCommand;
import com.eomcs.lms.handler.MemberSearchCommand;
import com.eomcs.lms.handler.MemberUpdateCommand;

public class App {

  // Observer를 보관할 저장소
  ArrayList<ApplicationContextListener> applicationContextListeners = new ArrayList<>();

  // Observer와 서로 공유할 값을 저장하는 보관소
  Map<String,Object> context = new HashMap<>();

  // 서버 포트 번호
  int port;
  ServerSocket serverSocket;

  // 클라이언트 명령을 처리할 객체 맵
  Map<String,Command> commandMap = new HashMap<>();

  
  public App(int port) {
    this.port = port;
  }

  public void service() throws Exception {
    // 서비스를 실행하기 전에 등록된 모든 Observer를 호출하여 시작을 알린다.
    for (ApplicationContextListener listener : applicationContextListeners) {
      listener.contextInitialized(context);
    }

    // 핸들러가 사용할 DAO 프록시 객체는 context에서 꺼내 준다.
    LessonDao lessonDao = (LessonDao) context.get("lessonDao");

    commandMap.put("/lesson/add", new LessonAddCommand(lessonDao));
    commandMap.put("/lesson/list", new LessonListCommand(lessonDao));
    commandMap.put("/lesson/detail", new LessonDetailCommand(lessonDao));
    commandMap.put("/lesson/update", new LessonUpdateCommand(lessonDao));
    commandMap.put("/lesson/delete", new LessonDeleteCommand(lessonDao));

    MemberDao memberDao = (MemberDao) context.get("memberDao");

    commandMap.put("/member/add", new MemberAddCommand(memberDao));
    commandMap.put("/member/list", new MemberListCommand(memberDao));
    commandMap.put("/member/detail", new MemberDetailCommand(memberDao));
    commandMap.put("/member/update", new MemberUpdateCommand(memberDao));
    commandMap.put("/member/delete", new MemberDeleteCommand(memberDao));
    commandMap.put("/member/search", new MemberSearchCommand(memberDao));

    BoardDao boardDao = (BoardDao) context.get("boardDao");

    commandMap.put("/board/add", new BoardAddCommand(boardDao));
    commandMap.put("/board/list", new BoardListCommand(boardDao));
    commandMap.put("/board/detail", new BoardDetailCommand(boardDao));
    commandMap.put("/board/update", new BoardUpdateCommand(boardDao));
    commandMap.put("/board/delete", new BoardDeleteCommand(boardDao));

    // 클라이언트와 연결할 준비하기
    serverSocket = new ServerSocket(port);
    System.out.println("서버 실행!");

    while (true) {
      try {
        new Thread(new RequestHandler(serverSocket.accept())).start();
      } catch (Exception e) {
        // 클라이언트와 연결하다거 오류가 발생한 경우 무시한다.
      }
    }
    
  }
  
  private void shutdown() {
    try {
      // 서비스를 종료하기 전에 등록된 모든 Observer를 호출하여 종료를 알린다.
      for (ApplicationContextListener listener : applicationContextListeners) {
        listener.contextDestroyed(context);
      }
  
      serverSocket.close();
      System.out.println("서버 종료!");
    } catch (Exception e) {
      // 종료 시에 발생하는 예외는 무시한다.
    } finally {
      System.exit(0);
    }
  }

  // Observer를 등록하는 메서드
  private void addApplicationContextListener(ApplicationContextListener listener) {
    applicationContextListeners.add(listener);
  }

  public static void main(String[] args) throws Exception {
    App app = new App(8888);

    // App 객체에 Observer를 등록한다.
    app.addApplicationContextListener(new DataLoaderListener());

    // App 을 실행한다.
    app.service();
  }

  class RequestHandler implements Runnable {

    Socket socket;

    public RequestHandler(Socket socket) {
      System.out.println("클라이언트와 연결되었음.");
      System.out.println("스레드 생성됨!");
      this.socket = socket;
    }

    @Override
    public void run() {
      System.out.println("스레드 실행...");
      try (Socket socket = this.socket;
          BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
          PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()))) {

        String command = in.readLine();
        
        // 사용자가 입력한 명령으로 Command 객체를 찾는다.
        Command commandHandler = commandMap.get(command);

        if (commandHandler != null) {
          try {
            commandHandler.execute(in, out);
          } catch (Exception e) {
            out.println("명령어 실행 중 오류 발생 : " + e.toString());
          }
        } else if (command.equals("shutdown")) {
          shutdown();
          return;

        } else {
          out.println("실행할 수 없는 명령입니다.");
        }

        // 서버의 응답이 끝났음을 알린다. 
        out.println("\n!end!");
        out.flush();

      } catch (Exception e) {
        System.out.println("클라이언트와 통신중 오류 발생: " + e.getMessage());
      } finally {
        System.out.println("클라이언트와 연결 종료!");
        System.out.println("스레드 종료!");
      }
    }
  }
}
