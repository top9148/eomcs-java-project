package com.eomcs.lms.servlet;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import com.eomcs.lms.domain.Board;

public class BoardListServlet implements Servlet {
  
  List<Board> list;
  
  public BoardListServlet(List<Board> list) {
    this.list = list;
  }
  
  @Override
  public void service(Map<String,String> paramMap, PrintWriter out) throws Exception {
    Board[] boards = list.toArray(new Board[] {});
    for (Board board : boards) {
      out.printf("%3d, %-20s, %s, %d\n", 
          board.getNo(), board.getContents(), 
          board.getCreatedDate(), board.getViewCount());
    }
  }

}
