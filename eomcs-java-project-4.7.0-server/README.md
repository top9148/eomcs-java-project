# eomcs-java-project-4.7-server

트랜잭션이 필요한 이유!

- 여러 개의 DB 변경 작업을 한 작업 단위로 묶는 방법
- `commit`과 `rollback`의 의미

## 프로젝트 - 수업관리 시스템  

### ver 4.7.0 - `수업 사진 게시판`을 만들라.

- PhotoBoard.java
    - 사진 게시물의 데이터 타입을 정의한다.
- PhotoBoardDao.java
    - 사진 게시물의 CRUD 관련 메서드를 정의한다.
- PhotoListCommand.java
    - 사진 게시물의 목록을 출력한다.
- DataLoaderListener.java
    - `PhotoBoardDao` 객체를 생성하여 맵 객체에 보관한다.
- App.java
    - 사진 게시물 관련 `Command` 객체를 생성하여 커맨드 맵에 보관한다.




##### 실행 결과

`eomcs-java-project-server` 프로젝트의 `App` 클래스를 실행한다.
```
DataLoaderListener.contextInitialized() 실행!
MariaDB에 연결했습니다.
서버 실행!
.
.
.
클라이언트와 연결됨.
클라이언트와 요청 처리 중...
클라이언트와 요청 처리 중...
클라이언트와 요청 처리 중...
클라이언트와 요청 처리 중...
클라이언트와 연결 종료!
```

`eomcs-java-project-client`프로젝트의 `ClientApp`을 실행한다.
```
명령> /lesson/list
  1, 자바 기초 프로그래밍    , 2019-01-02 ~ 2019-01-15,   80
  2, 자바 고급 프로그래밍    , 2019-01-14 ~ 2019-01-25,   80
  3, 자바 웹 프로그래밍     , 2019-01-21 ~ 2019-02-01,   80
  4, IoT 프로그래밍      , 2019-02-04 ~ 2019-03-04,   80
  5, C# 프로그래밍       , 2019-02-04 ~ 2019-02-15,   40

명령> /lesson/add
수업명?
테스트
설명?
테스트 과정입니다
시작일?
2019-1-1
종료일?
2019-2-2
총수업시간?
40
일수업시간?
2
강사?
2
수업을 저장했습니다.

명령> /lesson/list
  1, 자바 기초 프로그래밍    , 2019-01-02 ~ 2019-01-15,   80
  2, 자바 고급 프로그래밍    , 2019-01-14 ~ 2019-01-25,   80
  3, 자바 웹 프로그래밍     , 2019-01-21 ~ 2019-02-01,   80
  4, IoT 프로그래밍      , 2019-02-04 ~ 2019-03-04,   80
  5, C# 프로그래밍       , 2019-02-04 ~ 2019-02-15,   40
  6, 테스트            , 2019-01-01 ~ 2019-02-02,   40

명령> quit

```


## 실습 소스

- src/main/java/com/eomcs/lms/handler/Command.java 변경
- src/main/java/com/eomcs/lms/handler/LessonListCommand.java 변경
- src/main/java/com/eomcs/lms/handler/LessonAddCommand.java 변경
- src/main/java/com/eomcs/lms/handler/LessonUpdateCommand.java 변경
- src/main/java/com/eomcs/lms/handler/LessonDetailCommand.java 변경
- src/main/java/com/eomcs/lms/handler/LessonDeleteCommand.java 변경
- src/main/java/com/eomcs/lms/handler/MemberListCommand.java 변경
- src/main/java/com/eomcs/lms/handler/MemberAddCommand.java 변경
- src/main/java/com/eomcs/lms/handler/MemberUpdateCommand.java 변경
- src/main/java/com/eomcs/lms/handler/MemberDetailCommand.java 변경
- src/main/java/com/eomcs/lms/handler/MemberDeleteCommand.java 변경
- src/main/java/com/eomcs/lms/handler/BoardListCommand.java 변경
- src/main/java/com/eomcs/lms/handler/BoardAddCommand.java 변경
- src/main/java/com/eomcs/lms/handler/BoardUpdateCommand.java 변경
- src/main/java/com/eomcs/lms/handler/BoardDetailCommand.java 변경
- src/main/java/com/eomcs/lms/handler/BoardDeleteCommand.java 변경
- src/main/java/com/eomcs/lms/dao/MemberDao.java 변경
- src/main/java/com/eomcs/lms/handler/MemberSearchCommand.java 추가
- src/main/java/com/eomcs/lms/App.java 변경
