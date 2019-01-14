# eomcs-java-project-3.2

사용 규칙을 정할 때는 인터페이스를 활용하자.

- 인터페이스를 이용하여 사용 규칙을 정의하는 방법
- 인터페이스의 용도와 이점을 이해하기

  
## 프로젝트 - 수업관리 시스템  

### 과제 1: ArrayList와 LinkedList 구분없이 사용할 수 있게 하라.

- List.java
    - 핸들러와 목록을 다루는 객체 사이의 호출 규칙을 정의한다.
    - 즉 핸들러가 호출할 메서드의 규칙을 인터페이스로 정의한다.
- ArrayList.java
    - `List` 인터페이스를 구현한다.
- LinkedList.java
    - `List` 인터페이스를 구현한다.
- LessonHandler.java
    - ArrayList 또는 LinkedList를 직접 지정하는 대신에 인터페이스를 필드로 선언한다.
    - 생성자에서 List 구현체를 공급받도록 변경하여 특정 클래스에 의존하는 코드를 제거한다.
- MemberHandler.java
    - ArrayList 또는 LinkedList를 직접 지정하는 대신에 인터페이스를 필드로 선언한다.
    - 생성자에서 List 구현체를 공급받도록 변경하여 특정 클래스에 의존하는 코드를 제거한다.
- BoardHandler.java
    - ArrayList 또는 LinkedList를 직접 지정하는 대신에 인터페이스를 필드로 선언한다.
    - 생성자에서 List 구현체를 공급받도록 변경하여 특정 클래스에 의존하는 코드를 제거한다.
- App.java
    - 핸들러를 생성할 때 List 구현체를 넘겨준다.

#### 실행 결과

`App` 클래스의 실행 결과는 이전 버전과 같다.

## 실습 소스

- src/main/java/com/eomcs/util/List.java 추가
- src/main/java/com/eomcs/util/ArrayList.java 변경
- src/main/java/com/eomcs/util/LinkedList.java 변경
- src/main/java/com/eomcs/lms/handler/LessonHandler.java 변경
- src/main/java/com/eomcs/lms/handler/MemberHandler.java 변경
- src/main/java/com/eomcs/lms/handler/BoardHandler.java 변경
- src/main/java/com/eomcs/lms/App.java 변경