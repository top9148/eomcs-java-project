package com.eomcs.lms;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;
import com.eomcs.context.ApplicationContextListener;
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
import com.eomcs.lms.handler.MemberUpdateCommand;
import com.eomcs.lms.proxy.BoardDaoProxy;
import com.eomcs.lms.proxy.LessonDaoProxy;
import com.eomcs.lms.proxy.MemberDaoProxy;

public class App {
  
  // Observer를 보관할 저장소
  ArrayList<ApplicationContextListener> applicationContextListeners = new ArrayList<>();

  // Observer와 서로 공유할 값을 저장하는 보관소
  Map<String,Object> context = new HashMap<>();
  
  Scanner keyboard = new Scanner(System.in);
  Stack<String> commandHistory = new Stack<>();
  Queue<String> commandHistory2 = new LinkedList<>();
  
  public void service() {
    // 서비스를 실행하기 전에 등록된 모든 Observer를 호출하여 시작을 알린다.
    for (ApplicationContextListener listener : applicationContextListeners) {
      listener.contextInitialized(context);
    }
    
    Map<String,Command> commandMap = new HashMap<>();

    // 핸들러가 사용할 DAO 프록시 객체는 context에서 꺼내 준다.
    LessonDaoProxy lessonDao = (LessonDaoProxy) context.get("lessonDao");
    
    commandMap.put("/lesson/add", new LessonAddCommand(keyboard, lessonDao));
    commandMap.put("/lesson/list", new LessonListCommand(keyboard, lessonDao));
    commandMap.put("/lesson/detail", new LessonDetailCommand(keyboard, lessonDao));
    commandMap.put("/lesson/update", new LessonUpdateCommand(keyboard, lessonDao));
    commandMap.put("/lesson/delete", new LessonDeleteCommand(keyboard, lessonDao));
    
    MemberDaoProxy memberDao = (MemberDaoProxy) context.get("memberDao");
    
    commandMap.put("/member/add", new MemberAddCommand(keyboard, memberDao));
    commandMap.put("/member/list", new MemberListCommand(keyboard, memberDao));
    commandMap.put("/member/detail", new MemberDetailCommand(keyboard, memberDao));
    commandMap.put("/member/update", new MemberUpdateCommand(keyboard, memberDao));
    commandMap.put("/member/delete", new MemberDeleteCommand(keyboard, memberDao));
    
    BoardDaoProxy boardDao = (BoardDaoProxy) context.get("boardDao");
    
    commandMap.put("/board/add", new BoardAddCommand(keyboard, boardDao));
    commandMap.put("/board/list", new BoardListCommand(keyboard, boardDao));
    commandMap.put("/board/detail", new BoardDetailCommand(keyboard, boardDao));
    commandMap.put("/board/update", new BoardUpdateCommand(keyboard, boardDao));
    commandMap.put("/board/delete", new BoardDeleteCommand(keyboard, boardDao));
    
    while (true) {
      String command = prompt();

      // 사용자가 입력한 명령을 스택에 보관한다.
      commandHistory.push(command);
      
      // 사용자가 입력한 명령을 큐에 보관한다.
      commandHistory2.offer(command);
      
      // 사용자가 입력한 명령으로 Command 객체를 찾는다.
      Command commandHandler = commandMap.get(command);
      
      if (commandHandler != null) {
        try {
          commandHandler.execute();
        } catch (Exception e) {
          System.out.println("명령어 실행 중 오류 발생 : " + e.toString());
        }
      } else if (command.equals("quit")) {
        System.out.println("안녕!");
        break;
        
      } else if (command.equals("history")) {
        printCommandHistory();
        
      } else if (command.equals("history2")) {
        printCommandHistory2();
        
      } else {
        System.out.println("실행할 수 없는 명령입니다.");
      }
      
      System.out.println(); 
    }

    keyboard.close();
    
    // 서비스를 종료하기 전에 등록된 모든 Observer를 호출하여 종료를 알린다.
    for (ApplicationContextListener listener : applicationContextListeners) {
      listener.contextDestroyed(context);
    }
  }
  
  @SuppressWarnings("unchecked")
  private void printCommandHistory() {
    Stack<String> temp = (Stack<String>) commandHistory.clone();
    
    while (temp.size() > 0) {
      System.out.println(temp.pop());
    }
  }
  
  @SuppressWarnings("unchecked")
  private void printCommandHistory2() {
    Queue<String> temp = (Queue<String>) ((LinkedList<String>) commandHistory2).clone();
    
    while (temp.size() > 0) {
      System.out.println(temp.poll());
    }
  }

  private String prompt() {
    System.out.print("명령> ");
    return keyboard.nextLine().toLowerCase();
  }
  
  // Observer를 등록하는 메서드
  private void addApplicationContextListener(ApplicationContextListener listener) {
    applicationContextListeners.add(listener);
  }
  
  public static void main(String[] args) {
    App app = new App();
    
    // App 객체에 Observer를 등록한다.
    app.addApplicationContextListener(new DataLoaderListener());
    
    // App 을 실행한다.
    app.service();
  }
}
