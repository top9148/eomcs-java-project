package com.eomcs.util;

import java.util.Arrays;

public class ArrayList<T> implements List<T>{
  private static final int DEFAULT_CAPACITY = 10;
  private Object[] elementData;
  private int size = 0;
  
  public ArrayList() {
    elementData  = new Object[DEFAULT_CAPACITY];
  }
  
  public ArrayList(int initialCapacity) {
    if (initialCapacity > DEFAULT_CAPACITY) 
      elementData = new Object[initialCapacity];
    else
      elementData = new Object[DEFAULT_CAPACITY];
  }
  
  @Override
  @SuppressWarnings("unchecked")
  public T[] toArray(T[] a) {
    if (a.length < size) {
      return (T[]) Arrays.copyOf(elementData, size, a.getClass());
    }
    System.arraycopy(elementData, 0, a, 0, size);
    if (a.length > size)
      a[size] = null;
    return a;
  }
  
  @Override
  public void add(T obj) {
    if (size >= elementData.length) {
      int oldCapacity = elementData.length;
      int newCapacity = oldCapacity + (oldCapacity >> 1);
      elementData = Arrays.copyOf(elementData, newCapacity);
    }
    
    elementData[size++] = obj;
  }
  
  @Override
  @SuppressWarnings("unchecked")
  public T get(int index) {
    if (index < 0 || index >= size) 
      return null;
    
    return (T) elementData[index];
  }

  @Override
  public T set(int index, T obj) {
    if (index < 0 || index >= size)
      return null;
    
    @SuppressWarnings("unchecked")
    T old = (T)elementData[index];
    elementData[index] = obj;
    return old;
  }
  
  @Override
  public T remove(int index) {
    if (index < 0 || index >= size)
      return null;
    
    @SuppressWarnings("unchecked")
    T old = (T)elementData[index];
    
    int newSize = size - 1;
    System.arraycopy(elementData, index + 1, elementData, index, newSize - index);
    elementData[size = newSize] = null;
    return old;
  }
  
  @Override
  public int size() {
    return size;
  }
  
  /*
  public static void main(String[] args) {
    ArrayList<String> list = new ArrayList<>();
    
    list.add("aaa");
    list.add("bbb");
    list.add("ccc");
    list.add("ddd");
    list.add("eee");
    list.add("fff");
    list.add("ggg");
    
    System.out.println(list.size());
    
    System.out.println(list.remove(3));
    
    System.out.println(list.size());
    
    for (int i = 0; i < list.size(); i++) {
      System.out.println(list.get(i));
    }
  }
  */
}
