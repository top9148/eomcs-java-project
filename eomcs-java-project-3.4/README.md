# eomcs-java-project-3.4

`Command` 디자인 패턴의 응용

- 명령어 처리를 관리하기 쉽게 객체 단위로 쪼개는 설계 방법
- `Command' 디자인 패턴의 이해
  
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

#### 실행 결과

`App` 클래스의 실행 결과는 이전 버전과 같다.

## 실습 소스

- src/main/java/com/eomcs/util/List.java 삭제
- src/main/java/com/eomcs/util/ArrayList.java 삭제
- src/main/java/com/eomcs/util/LinkedList.java 삭제
- src/main/java/com/eomcs/util/Stack.java 삭제
- src/main/java/com/eomcs/util/Queue.java 삭제
- src/main/java/com/eomcs/lms/handler/LessonHandler.java 변경
- src/main/java/com/eomcs/lms/handler/MemberHandler.java 변경
- src/main/java/com/eomcs/lms/handler/BoardHandler.java 변경
- src/main/java/com/eomcs/lms/App.java 변경