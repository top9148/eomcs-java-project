# eomcs-java-project-2.7

제네릭을 사용하는 방법과 이점

- Object 타입의 한계를 극복하는 제네릭 활용법

## 프로젝트 - 수업관리 시스템  

### 과제 1: ArrayList 클래스에 제네릭을 적용하라.

- ArrayList.java
    - 다양한 객체에 대해 목록을 다룰 수 있도록 제네릭 문법을 적용한다.
    - 목록을 처리를 필요로 하는 여러 프로젝트에서 두루 사용할 수 있도록 util 패키지를 만들어 이동시킨다.
- LessonHandler.java
    - 제네릭을 적용한 `ArrayList` 클래스를 사용하여 데이터를 처리한다.
- MemberHandler.java
    - 제네릭을 적용한 `ArrayList` 클래스를 사용하여 데이터를 처리한다.
- BoardHandler.java
    - 제네릭을 적용한 `ArrayList` 클래스를 사용하여 데이터를 처리한다.

#### 실행 결과

`App` 클래스의 실행 결과는 이전 버전과 같다.

## 실습 소스

- src/main/java/com/eomcs/util/ArrayList.java 이동,변경
- src/main/java/com/eomcs/lms/handler/LessonHandler.java 변경
- src/main/java/com/eomcs/lms/handler/MemberHandler.java 변경
- src/main/java/com/eomcs/lms/handler/BoardHandler.java 변경