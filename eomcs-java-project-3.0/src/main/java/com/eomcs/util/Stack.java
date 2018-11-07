package com.eomcs.util;

import java.util.Vector;

public class Stack<E> extends Vector<E> implements Cloneable {
  private static final long serialVersionUID = 1L;

  @SuppressWarnings("unchecked")
  @Override
  public synchronized Stack<E> clone() {
    return (Stack<E>) super.clone();
  }
  
  public void push(E value) {
    if (size() == 10)
      remove(0);
    add(value);
  }
  
  public E pop() {
    return remove(size() - 1);
  }
  
  /*
  public static void main(String[] args) throws Exception {
    Stack<String> stack = new Stack<>();
    stack.push("aaa");
    stack.push("bbb");
    stack.push("ccc");
    stack.push("ddd");
    stack.push("eee");
    stack.push("fff");
    
    Stack<String> temp1 = stack.clone();
    while (temp1.size() > 0) {
      System.out.println(temp1.pop());
    }
    System.out.println("----------------------");
    
    Stack<String> temp2 = stack.clone();
    while (temp2.size() > 0) {
      System.out.println(temp2.pop());
    }
  }*/
}
