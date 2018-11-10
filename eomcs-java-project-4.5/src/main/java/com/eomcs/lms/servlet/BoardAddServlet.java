package com.eomcs.lms.servlet;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.List;
import java.util.Map;
import com.eomcs.lms.domain.Board;

public class BoardAddServlet implements Servlet {
  
  List<Board> list;
  
  public BoardAddServlet(List<Board> list) {
    this.list = list;
  }
  
  @Override
  public void service(Map<String,String> paramMap, PrintWriter out) throws Exception {
    Board board = new Board();
    board.setNo(Integer.parseInt(paramMap.get("no")));
    board.setContents(paramMap.get("contents"));
    board.setCreatedDate(new Date(System.currentTimeMillis())); 
    board.setViewCount(0);
    
    list.add(board);
    
    out.println("저장하였습니다.");
  }
}
