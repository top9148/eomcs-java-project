package com.eomcs.lms;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import com.eomcs.lms.dao.BoardDao;
import com.eomcs.lms.dao.LessonDao;
import com.eomcs.lms.dao.MemberDao;

public class ServerApp {

  int port = 8888;
  
  LessonDao lessonDao;
  MemberDao memberDao;
  BoardDao boardDao;
  
  public ServerApp(int port) throws Exception {
    this.port = port;
    
    try {
      lessonDao = new LessonDao("lesson.data");
      System.out.println("수업 데이터를 로딩 성공!");
    } catch (Exception e) {
      System.out.println("수업 데이터 로딩 실패!");
    }
    
    try {
      memberDao = new MemberDao("member.data");
      System.out.println("회원 데이터를 로딩 성공!");
    } catch (Exception e) {
      System.out.println("회원 데이터 로딩 실패!");
    }
    
    try {
      boardDao = new BoardDao("board.data");
      System.out.println("게시글 데이터를 로딩 성공!");
    } catch (Exception e) {
      System.out.println("게시글 데이터 로딩 실패!");
    }
  }
  
  public void service() throws Exception {
    ServerSocket serverSocket = new ServerSocket(port);
    System.out.println("서버 시작!");

    while (true) {
      /*
      Socket socket = serverSocket.accept();
      RequestHandler requestHandler = new RequestHandler(socket);
      Thread t = new Thread(requestHandler);
      t.start();
      */
      new Thread(new RequestHandler(serverSocket.accept())).start();
    }
  }
  
  private void close() {
    try {
      lessonDao.saveData();
      System.out.println("수업 데이터를 저장했습니다.");
    } catch (Exception e) {
      System.out.println("수업 데이터 저장 실패입니다.");
    }
    try {
      memberDao.saveData();
      System.out.println("회원 데이터를 저장했습니다.");
    } catch (Exception e) {
      System.out.println("회원 데이터 저장 실패입니다.");
    }
    try {
      boardDao.saveData();
      System.out.println("게시글 데이터를 저장했습니다.");
    } catch (Exception e) {
      System.out.println("게시글 데이터 저장 실패입니다.");
    }
  }
  
  public static void main(String[] args) throws Exception {
    ServerApp serverApp = new ServerApp(8888);
    serverApp.service();
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
      try (
          Socket socket = this.socket;
          ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
          ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
          ) {

        String command = in.readUTF();
        
        System.out.println(command + " 명령을 처리중...");
        
        switch (command) {
          case "/member/add":
            memberDao.add(in, out);
            break;
          case "/member/detail":
            memberDao.detail(in, out);
            break;
          case "/member/list":
            memberDao.list(in, out);
            break;
          case "/member/update":
            memberDao.update(in, out);
            break;
          case "/member/delete":
            memberDao.delete(in, out);
            break;
          case "/lesson/add":
            lessonDao.add(in, out);
            break;
          case "/lesson/detail":
            lessonDao.detail(in, out);
            break;
          case "/lesson/list":
            lessonDao.list(in, out);
            break;
          case "/lesson/update":
            lessonDao.update(in, out);
            break;
          case "/lesson/delete":
            lessonDao.delete(in, out);
            break; 
          case "/board/add":
            boardDao.add(in, out);
            break;
          case "/board/detail":
            boardDao.detail(in, out);
            break;
          case "/board/list":
            boardDao.list(in, out);
            break;
          case "/board/update":
            boardDao.update(in, out);
            break;
          case "/board/delete":
            boardDao.delete(in, out);
            break;
          default:
            out.writeUTF("error");
            out.flush();
        }
            
      } catch (Exception e) {
        System.out.println("클라이언트 요청을 처리하는 중에 오류가 발생함!");
        
      } finally {
        System.out.println("클라이언트와 연결을 끊었음.");
        close();
        System.out.println("스레드 종료!");
      }
    }
  }
}
