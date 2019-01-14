package com.eomcs.lms.dao;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import com.eomcs.lms.domain.Lesson;

public class LessonDao {
  
  ArrayList<Lesson> lessons = new ArrayList<>();
  
  String filepath;
  
  public LessonDao(String filepath) throws Exception {
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
      
      lessons = (ArrayList<Lesson>) in.readObject();
      
    } catch (Exception e) {
      throw e;
      
    } finally {
      try {in.close();} catch (Exception e) {}
    }
  }
  
  public void saveData() throws Exception {
    try (ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(
        new FileOutputStream(filepath)))) {
      
      out.writeObject(lessons);
      
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
      lessons.remove(index);
      out.writeUTF("ok");
    }
    out.flush();
  }

  public void update(ObjectInputStream in, ObjectOutputStream out) throws Exception {
    Lesson lesson = (Lesson) in.readObject();
    
    int index = indexOf(lesson.getNo());
    
    if (index == -1) {
      out.writeUTF("fail");
    } else { 
      lessons.set(index, lesson);
      out.writeUTF("ok");
    }
    out.flush();
  }

  public void list(ObjectInputStream in, ObjectOutputStream out) throws Exception {
    out.writeUTF("ok");
    Lesson[] arr = new Lesson[lessons.size()];
    out.writeObject(lessons.toArray(arr));
    out.flush();
  }

  public void detail(ObjectInputStream in, ObjectOutputStream out) throws Exception {
    int no = in.readInt();
    int index = indexOf(no);
    
    if (index == -1) {
      out.writeUTF("fail");
    } else { 
      out.writeUTF("ok");
      out.writeObject(lessons.get(index));
    }
    out.flush();
  }

  public void add(ObjectInputStream in, ObjectOutputStream out) throws Exception {
    lessons.add((Lesson)in.readObject());
    
    out.writeUTF("ok");
    out.flush();
  }
  
  private int indexOf(int no) {
    for (int i = 0; i < lessons.size(); i++) {
      Lesson l = lessons.get(i);
      if (l.getNo() == no)
        return i;
    }
    return -1;
  } 
}
