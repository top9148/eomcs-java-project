package com.eomcs.lms;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.Stack;

public class ClientApp {
  
  static final int DEFAULT_PORT = 8888;
  
  Scanner keyboard = new Scanner(System.in);
  Stack<String> commandHistory = new Stack<>();
  
  String host;
  int port;
  
  public ClientApp(String host) throws Exception {
    this(host, DEFAULT_PORT);
  }
  
  public ClientApp(String host, int port) throws Exception {
    this.host = host;
    this.port = port;
  }
  
  public void service() {
    
    while (true) {
      String command = prompt();
      
      // 입력한 명령을 보관한다.
      commandHistory.push(command);

      if (command.equals("history")) {
        printCommandHistory();
        System.out.println();
        continue;
      }
      
      try (Socket s = new Socket(host, port);
          PrintWriter out = new PrintWriter(new BufferedOutputStream(s.getOutputStream()));
          BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()))) {
        
        if (command.toLowerCase().equals("quit") ||
            command.toLowerCase().equals("shutdown")) {
          out.println(command.toLowerCase());
          out.flush(); // 버퍼에 임시 보관된 출력물을 서버로 보낸다.
          System.out.println("안녕!");
          break;
        } 
        
        // 서버에 명령을 보낸다.
        out.println(command);
        out.flush(); // 버퍼에 임시 보관된 출력물을 서버로 보낸다.
        
        // 서버가 응답한 내용을 읽는다.
        String line = null;
        while (!(line = in.readLine()).isEmpty()) {
          System.out.println(line);
        }
        
        System.out.println(); 
        
      } catch (Exception e) {
        System.out.println("통신 중 오류 발생!");
        break;
      }
    }
  }
  
  @SuppressWarnings("unchecked")
  private void printCommandHistory() {
    Stack<String> temp = (Stack<String>) commandHistory.clone();
    
    while (temp.size() > 0) {
      System.out.println(temp.pop());
    }
  }
  
  private String prompt() {
    System.out.print("명령> ");
    return keyboard.nextLine().toLowerCase();
  }
  
  public static void main(String[] args) throws Exception {
    ClientApp clientApp = new ClientApp("localhost", 8888);
    clientApp.service();
  }
}
