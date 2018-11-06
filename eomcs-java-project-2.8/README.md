# eomcs-java-project-2.8

CRUD 기능 완성

- 수업/회원/게시판에 대해 CRUD 완성하기
  
## 프로젝트 - 수업관리 시스템  

### 과제 1: ArrayList 클래스에 항목 값을 조회하고 변경하고 삭제하는 기능을 추가하라.

- ArrayList.java
    - 목록에서 특정 항목의 값을 꺼내주는 get()을 정의한다.
    - 목록의 항목 값을 바꾸는 set()을 정의한다.
    - 목록에서 특정 항목의 값을 삭제하는 remove()를 정의한다.
  
### 과제 2: 수업 상세조회, 변경, 삭제 기능을 추가하라.

- LessonHandler.java
    - 

#### 실행 결과

```
명령> /lesson/list
1, 자바 프로젝트 실습     , 2019-01-02 ~ 2019-05-28, 1000
2, 자바 프로그래밍 기초    , 2019-02-01 ~ 2019-02-28,  160
3, 자바 프로그래밍 고급    , 2019-03-02 ~ 2019-03-30,  160
4, 서블릿/JSP 프로그래밍   , 2019-04-02 ~ 2019-05-30,  150

명령> /lesson/view
번호? 2
수업명: 자바 프로젝트 실습
수업내용: 자바 프로젝트를 통한 자바 언어 활용법 익히기
기간: 2019-01-02 ~ 2019-05-28
총수업시간: 1000
일수업시간: 8

명령> /lesson/update
번호? 2

```

## 실습 소스

- src/main/java/com/eomcs/util/ArrayList.java 이동,변경
- src/main/java/com/eomcs/lms/handler/LessonHandler.java 변경
- src/main/java/com/eomcs/lms/handler/MemberHandler.java 변경
- src/main/java/com/eomcs/lms/handler/BoardHandler.java 변경