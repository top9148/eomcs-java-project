package com.eomcs.lms.handler;

import java.util.Arrays;
import com.eomcs.lms.domain.Board;

public class BoardList {
  final int DEFAULT_CAPACITY = 10;
  Board[] boards;
  int size = 0;
  
  public BoardList() {
    boards  = new Board[DEFAULT_CAPACITY];
  }
  
  public BoardList(int initialCapacity) {
    if (initialCapacity > DEFAULT_CAPACITY) 
      boards = new Board[initialCapacity];
    else
      boards = new Board[DEFAULT_CAPACITY];
  }
  
  public Board[] toArray() {
    return Arrays.copyOf(boards, size); 
  }
  
  public void add(Board board) {
    if (size >= boards.length) {
      int oldCapacity = boards.length;
      int newCapacity = oldCapacity + (oldCapacity >> 1);
      boards = Arrays.copyOf(boards, newCapacity);
    }
    
    boards[size++] = board;
  }
}
