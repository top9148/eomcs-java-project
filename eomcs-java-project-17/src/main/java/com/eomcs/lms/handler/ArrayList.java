package com.eomcs.lms.handler;

import java.util.Arrays;

public class ArrayList {
  final int DEFAULT_CAPACITY = 10;
  Object[] elementData;
  int size = 0;
  
  public ArrayList() {
    elementData  = new Object[DEFAULT_CAPACITY];
  }
  
  public ArrayList(int initialCapacity) {
    if (initialCapacity > DEFAULT_CAPACITY) 
      elementData = new Object[initialCapacity];
    else
      elementData = new Object[DEFAULT_CAPACITY];
  }
  
  public Object[] toArray() {
    return Arrays.copyOf(elementData, size); 
  }
  
  public void add(Object obj) {
    if (size >= elementData.length) {
      int oldCapacity = elementData.length;
      int newCapacity = oldCapacity + (oldCapacity >> 1);
      elementData = Arrays.copyOf(elementData, newCapacity);
    }
    
    elementData[size++] = obj;
  }
}
