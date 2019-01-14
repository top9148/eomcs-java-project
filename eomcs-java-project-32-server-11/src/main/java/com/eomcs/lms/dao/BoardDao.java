package com.eomcs.lms.dao;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import com.eomcs.lms.domain.Board;

public class BoardDao {
  
  ArrayList<Board> boards = new ArrayList<>();
  
  String filepath;
  
  public BoardDao(String filepath) throws Exception {
    this.filepath = filepath;
    loadData();
  }
  
  @SuppressWarnings("unchecked")
  public void loadData() throws Exception {
    ObjectInputStream in = null;
    
    try {
      
      File file = new File(filepath);
      if (!file.exists()) {
        file.createNewFile();
        return;
      }
      
      if (file.length() == 0) {
        return;
      }
      
      in = new ObjectInputStream(new BufferedInputStream(new FileInputStream(file)));
      
      boards = (ArrayList<Board>) in.readObject();
      
    } catch (Exception e) {
      throw e;
      
    } finally {
      try {in.close();} catch (Exception e) {}
    }
  }
  
  public void saveData() throws Exception {
    try (ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(
        new FileOutputStream(filepath)))) {
      
      out.writeObject(boards);
      
    } catch (Exception e) {
      throw e;
    }
  }
  
  public void delete(ObjectInputStream in, ObjectOutputStream out) throws Exception {
    int no = in.readInt();
    int index = indexOf(no);
    
    if (index == -1) {
      out.writeUTF("fail");
    } else { 
      boards.remove(index);
      out.writeUTF("ok");
    }
    out.flush();
  }

  public void update(ObjectInputStream in, ObjectOutputStream out) throws Exception {
    Board board = (Board) in.readObject();
    
    int index = indexOf(board.getNo());
    
    if (index == -1) {
      out.writeUTF("fail");
    } else { 
      boards.set(index, board);
      out.writeUTF("ok");
    }
    out.flush();
  }

  public void list(ObjectInputStream in, ObjectOutputStream out) throws Exception {
    out.writeUTF("ok");
    Board[] arr = new Board[boards.size()];
    out.writeObject(boards.toArray(arr));
    out.flush();
  }

  public void detail(ObjectInputStream in, ObjectOutputStream out) throws Exception {
    int no = in.readInt();
    int index = indexOf(no);
    
    if (index == -1) {
      out.writeUTF("fail");
    } else { 
      out.writeUTF("ok");
      out.writeObject(boards.get(index));
    }
    out.flush();
  }

  public void add(ObjectInputStream in, ObjectOutputStream out) throws Exception {
    boards.add((Board)in.readObject());
    
    out.writeUTF("ok");
    out.flush();
  }
  
  private int indexOf(int no) {
    for (int i = 0; i < boards.size(); i++) {
      Board l = boards.get(i);
      if (l.getNo() == no)
        return i;
    }
    return -1;
  }  
}
