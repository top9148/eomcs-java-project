# eomcs-java-project-3.4

자바 컬렉션 API 사용하기

- 자바에서 제공하는 자료구조 구현체를 활용하는 방법

  
## 프로젝트 - 수업관리 시스템  

### 과제 1: ArrayList, LinkedList, Stack, Queue를 자바 컬렉션 API로 교체하라.

- LessonHandler.java
    - `List` 를 `java.util.List` 인터페이스로 교체한다.
- MemberHandler.java
    - `List` 를 `java.util.List` 인터페이스로 교체한다.
- BoardHandler.java
    - `List` 를 `java.util.List` 인터페이스로 교체한다.
- App.java
    - 핸들러를 생성할 때 자바 컬렉션 API에서 제공하는 `java.util.List`의 구현체를 넘겨준다.
    - 명령 내역을 저장할 때도 자바 컬렉션 API의 Stack과 Queue 구현체를 사용한다. 단 이 클래스들의 Iterator 객체는 입력 순서대로 값을 꺼내주기 때문에 우리가 사용하기에 적합하지 않다.
    - printCommandHistory()와 printCommandHistory2()를 변경한다. 

#### 실행 결과

`App` 클래스의 실행 결과는 이전 버전과 같다.

## 실습 소스

- src/main/java/com/eomcs/util/List.java 삭제
- src/main/java/com/eomcs/util/ArrayList.java 삭제
- src/main/java/com/eomcs/util/LinkedList.java 삭제
- src/main/java/com/eomcs/util/Iterator.java 삭제
- src/main/java/com/eomcs/util/Stack.java 삭제
- src/main/java/com/eomcs/util/Queue.java 삭제
- src/main/java/com/eomcs/lms/handler/LessonHandler.java 변경
- src/main/java/com/eomcs/lms/handler/MemberHandler.java 변경
- src/main/java/com/eomcs/lms/handler/BoardHandler.java 변경
- src/main/java/com/eomcs/lms/App.java 변경