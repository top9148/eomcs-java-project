# eomcs-java-project-2.9

ArrayList 대신 Linked List 자료구조 사용하기

- Linked List 자료구조를 구현하는 방법
- Linked List 의 구동원리 이해하기
- ArrayList와 LinkedList의 장단점 비교하기
- 중첩 클래스의 활용

  
## 프로젝트 - 수업관리 시스템  

### 과제 1: Linked List 자료구조를 구현하라.

- LinkedList.java (LinkedList.java.01)
    - Linked List 자료 구조를 구현한 클래스를 정의한다.

### 과제 2: LinkedList에 제네릭을 적용하라.

- LinkedList.java 
    - ArrayList처럼 특정 타입의 값을 다루도록 제네릭을 적용한다.

### 과제 3: 핸들러 클래스는 ArrayList 대신 LinkedList를 사용하라.

- LessonHandler.java
    - 수업 데이터를 저장하는 ArrayList를 LinkedList로 교체한다.
- MemberHandler.java
    - 회원 데이터를 저장하는 ArrayList를 LinkedList로 교체한다.
- BoardHandler.java
    - 게시글 데이터를 저장하는 ArrayList를 LinkedList로 교체한다.

#### 실행 결과

`App` 클래스의 실행 결과는 이전과 같다.


## 실습 소스

- src/main/java/com/eomcs/util/LinkedList.java 추가
- src/main/java/com/eomcs/lms/handler/LessonHandler.java 변경
- src/main/java/com/eomcs/lms/handler/MemberHandler.java 변경
- src/main/java/com/eomcs/lms/handler/BoardHandler.java 변경