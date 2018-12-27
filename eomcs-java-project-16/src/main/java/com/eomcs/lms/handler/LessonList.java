package com.eomcs.lms.handler;

import java.util.Arrays;
import com.eomcs.lms.domain.Lesson;

public class LessonList {
  final int DEFAULT_CAPACITY = 10;
  Lesson[] lessons;
  int size = 0;
  
  public LessonList() {
    lessons  = new Lesson[DEFAULT_CAPACITY];
  }
  
  public LessonList(int initialCapacity) {
    if (initialCapacity > DEFAULT_CAPACITY) 
      lessons = new Lesson[initialCapacity];
    else
      lessons = new Lesson[DEFAULT_CAPACITY];
  }
  
  public Lesson[] toArray() {
    return Arrays.copyOf(lessons, size); 
    /*
    Lesson[] list = new Lesson[size];
    
    for (int i = 0; i < size; i++) {
      list[i] = lessons[i];
    }
    return list;
    */
    
  }
  
  public void add(Lesson lesson) {
    if (size >= lessons.length) {
      int oldCapacity = lessons.length;
      int newCapacity = oldCapacity + (oldCapacity >> 1);
      lessons = Arrays.copyOf(lessons, newCapacity);
    }
    
    lessons[size++] = lesson;
  }
}
