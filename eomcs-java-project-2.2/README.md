# eomcs-java-project-2.2

인스턴스 변수와 인스턴스 메서드가 필요한 이유

- 인스턴스 변수와 메서드를 사용하는 방법
- 인스턴스 변수를 사용할 때의 이점
- 인스턴스 메서드를 사용하는 이유 
- 개별적으로 보관해야 하는 데이터에 대해 인스턴스 변수를 사용하기

## 프로젝트 - 수업관리 시스템  

### 과제 1: 새 게시판을 추가하라.

- BoardHandler.java
    - 게시판 마다 게시글을 개별적으로 다루도록 변수를 인스턴스 멤버로 전환한다.
    - 인스턴스 변수를 다루기 위해 클래스 메서드를 인스턴스 메서드로 전환한다.
- App.java (App.java.01)
    - `BoardHandler` 클래스의 변화에 맞추어 코드를 변경한다.
- BoardHandler2.java
    - 더 이상 필요 없다. 삭제한다.

#### 실행 결과

`App` 클래스의 실행 결과는 이전 버전과 같다.

### 과제 2: `LessonHandler`와 `MemberHandler`도 인스턴스 변수와 인스턴스 메서드로 전환하라.

- LessonHandler.java
    - 변수와 메서드를 인스턴스 멤버로 전환한다.
- MemberHandler.java    
    - 변수와 메서드를 인스턴스 멤버로 전환한다.
- App.java
    - `LessonHandler`와 `MemberHandler` 클래스의 변화에 맞추어 코드를 변경한다.

#### 실행 결과

`App` 클래스의 실행 결과는 이전 버전과 같다.


## 실습 소스

- src/main/java/com/eomcs/lms/App.java 변경
- src/main/java/com/eomcs/lms/handler/BoardHandler.java 변경
- src/main/java/com/eomcs/lms/handler/LessonHandler.java 변경
- src/main/java/com/eomcs/lms/handler/MemberHandler.java 변경
- src/main/java/com/eomcs/lms/handler/BoardHandler2.java 삭제