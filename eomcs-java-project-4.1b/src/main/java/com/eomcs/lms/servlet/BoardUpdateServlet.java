package com.eomcs.lms.servlet;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import com.eomcs.lms.domain.Board;

public class BoardUpdateServlet implements Servlet {
  
  List<Board> list;
  
  public BoardUpdateServlet(List<Board> list) {
    this.list = list;
  }
  
  @Override
  public void service(Map<String,String> paramMap, PrintWriter out) throws Exception {
    int no = Integer.parseInt(paramMap.get("no"));

    int index = indexOfBoard(no);
    if (index == -1) {
      out.println("해당 게시글을 찾을 수 없습니다.");
      return;
    }
    
    Board board = list.get(index);
    
    try {
      // 기존 값 복제
      Board temp = board.clone();
      temp.setContents(paramMap.get("contents"));
      
      list.set(index, temp);
      out.println("게시글을 변경했습니다.");
      
    } catch (Exception e) {
      out.println("변경 중 오류 발생!");
    }
  }
  
  private int indexOfBoard(int no) {
    for (int i = 0; i < list.size(); i++) {
      Board b = list.get(i);
      if (b.getNo() == no)
        return i;
    }
    return -1;
  }
}
