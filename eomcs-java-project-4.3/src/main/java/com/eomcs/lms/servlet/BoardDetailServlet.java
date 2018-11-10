package com.eomcs.lms.servlet;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import com.eomcs.lms.domain.Board;

public class BoardDetailServlet implements Servlet {
  
  List<Board> list;
  
  public BoardDetailServlet(List<Board> list) {
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

    out.printf("내용: %s\n", board.getContents());
    out.printf("작성일: %s\n", board.getCreatedDate());
    out.printf("조회수: %d\n", board.getViewCount());
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
