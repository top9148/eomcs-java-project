package com.eomcs.lms.handler;

import java.util.Arrays;
import com.eomcs.lms.domain.Member;

public class MemberList {
  final int DEFAULT_CAPACITY = 10;
  Member[] members;
  int size = 0;
  
  public MemberList() {
    members  = new Member[DEFAULT_CAPACITY];
  }
  
  public MemberList(int initialCapacity) {
    if (initialCapacity > DEFAULT_CAPACITY) 
      members = new Member[initialCapacity];
    else
      members = new Member[DEFAULT_CAPACITY];
  }
  
  public Member[] toArray() {
    return Arrays.copyOf(members, size); 
  }
  
  public void add(Member member) {
    if (size >= members.length) {
      int oldCapacity = members.length;
      int newCapacity = oldCapacity + (oldCapacity >> 1);
      members = Arrays.copyOf(members, newCapacity);
    }
    
    members[size++] = member;
  }
}
