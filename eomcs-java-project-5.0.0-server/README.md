# eomcs-java-project-5.0-server

커넥션 재사용을 위해 커넥션풀 적용하기

- 커넥션풀의 동작 원리와 사용해야 하는 이유

## 프로젝트 - 수업관리 시스템  

이전 버전에서는 클라이언트 요청에 대해 SQL 작업을 실행할 때마다 DB 커넥션을 만들어 사용했다. 작업이 끝난 후에는 사용한 DB 커넥션을 닫은 후 버렸다. 이 방식은 다음의 문제를 발생시킨다. 

SQL을 실행할 때마다 DB 커넥션을 생성하는데, DBMS에 연결하는 과정 중에서 사용자 인증 과정을 수행하기 때문에 매번 일정 시간이 소요된다는 문제가 발생한다. 또한 사용한 DB 커넥션은 버리기 때문에 가비지가 많이 발생한다.

이를 해결하기 위해 `Object pool` 디자인 패턴을 적용한 스레드풀처럼 DB 커넥션에 대해서도 이 디자인 패턴을 적용한 DB 커넥션풀을 만들어 사용할 것이다.

### ver 4.9.0 - 같은 트랜잭션에 소속된 작업을 처리할 때는 같은 DB 커넥션을 사용하도록 하라.

- TxConnection.java
    - `java.sql.Connection` 구현체이다.
    - `Proxy` 디자인 패턴을 적용하여 기존의 `Connection` 객체를 대행한다.
    - 트랜잭션 관리자가 사용하는 DB 커넥션인 경우 `close()`를 호출했을 때 커넥션을 끊지 않는다.
    - 트랜잭션 관리자가 사용하지 않는 DB 커넥션인 경우에만 `close()`를 호출했을 때 커넥션을 끊는다.
- DataSource.java
    - 트랜잭션을 시작하면 DB 커넥션을 만들어 스레드 로컬 변수에 보관한다.
    - 트랜잭션을 끝내면 스레드 로컬 변수에 보관된 DB 커넥션을 제거한다.
- TransactionManager.java
    - `DataSource`를 통해 트랜잭션을 관리하는 기능을 수행한다.
    - `commit`, `rollback` 기능을 정의한다.
- DataLoaderListener.java
    - `TransactionManager` 객체를 준비한다.
- PhotoBoardAddCommand.java
    - `TransactionManager`를 생성자에서 받는다.
    - 트랜잭션을 적용한다.
- PhotoBoardUpdateCommand.java
    - `TransactionManager`를 생성자에서 받는다.
    - 트랜잭션을 적용한다.
- PhotoBoardDeleteCommand.java
    - `TransactionManager`를 생성자에서 받는다.
    - 트랜잭션을 적용한다.
- App.java
    - 트랜잭션을 사용하는 `Command` 객체에 `TransactionManager`를 주입한다.

#### 실행 결과

`eomcs-java-project-server` 프로젝트의 `App` 클래스를 실행한다.
```
DataLoaderListener.contextInitialized() 실행!
서버 실행!
클라이언트와 연결되었음.
스레드 생성됨!
스레드 실행... - pool-1-thread-1
명령어: /photoboard/list
[pool-1-thread-1] - DB 커넥션 생성
클라이언트와 연결 종료!
스레드 종료!
클라이언트와 연결되었음.
스레드 생성됨!
스레드 실행... - pool-1-thread-1
명령어: /photoboard/add
트랜잭션을 시작함.
[pool-1-thread-1] - DB 커넥션 생성
[pool-1-thread-1] - ThreadLocal <=== DB 커넥션 보관
[pool-1-thread-1] - ThreadLocal의 DB 커넥션 리턴
[pool-1-thread-1] - ThreadLocal의 DB 커넥션 리턴
[pool-1-thread-1] - ThreadLocal의 DB 커넥션 리턴
[pool-1-thread-1] - ThreadLocal의 DB 커넥션 리턴
[pool-1-thread-1] - ThreadLocal의 DB 커넥션 리턴
[pool-1-thread-1] - ThreadLocal의 DB 커넥션 리턴
[pool-1-thread-1] - ThreadLocal ===> DB 커넥션 제거
rollback()
클라이언트와 연결 종료!
스레드 종료!
클라이언트와 연결되었음.
스레드 생성됨!
스레드 실행... - pool-1-thread-1
명령어: /photoboard/list
[pool-1-thread-1] - DB 커넥션 생성
클라이언트와 연결 종료!
스레드 종료!
클라이언트와 연결되었음.
스레드 생성됨!
스레드 실행... - pool-1-thread-1
명령어: /photoboard/add
트랜잭션을 시작함.
[pool-1-thread-1] - DB 커넥션 생성
[pool-1-thread-1] - ThreadLocal <=== DB 커넥션 보관
[pool-1-thread-1] - ThreadLocal의 DB 커넥션 리턴
[pool-1-thread-1] - ThreadLocal의 DB 커넥션 리턴
[pool-1-thread-1] - ThreadLocal의 DB 커넥션 리턴
[pool-1-thread-1] - ThreadLocal의 DB 커넥션 리턴
[pool-1-thread-1] - ThreadLocal ===> DB 커넥션 제거
commit()
클라이언트와 연결 종료!
스레드 종료!
클라이언트와 연결되었음.
스레드 생성됨!
스레드 실행... - pool-1-thread-1
명령어: /photoboard/list
[pool-1-thread-1] - DB 커넥션 생성
클라이언트와 연결 종료!
스레드 종료!
클라이언트와 연결되었음.
스레드 생성됨!
스레드 실행... - pool-1-thread-1
명령어: /photoboard/detail
[pool-1-thread-1] - DB 커넥션 생성
[pool-1-thread-1] - DB 커넥션 생성
클라이언트와 연결 종료!
스레드 종료!

```

`eomcs-java-project-client`프로젝트의 `ClientApp`을 실행한다.

먼저 입력 오류가 나는 상황을 테스트한다.

```
명령> /photoboard/list
  1, 수업 오리엔테이션           , 2018-11-14, 0, 1
  2, 1차 과제 발표            , 2018-11-14, 0, 1
  3, null                , 2018-11-14, 0, 2
  4, 과제 발표회              , 2018-11-14, 0, 3
  6, 발표2                 , 2018-11-14, 0, 1
  8, test1               , 2018-11-15, 0, 2
 11, okokok              , 2018-11-15, 0, 1
 12, test..100           , 2018-11-15, 0, 1

명령> /photoboard/add
제목?
transaction...test
수업?
1
최소 한 개의 사진 파일을 등록해야 합니다.
파일명 입력 없이 그냥 엔터를 치면 파일 추가를 마칩니다.
사진 파일?
a1.png
사진 파일?
a2.png
사진 파일?
a3.png
사진 파일?
012345678900123456789001234567890012345678900123456789001234567890012345678900123456789001234567890012345678900123456789001234567890012345678900123456789001234567890012345678900123456789001234567890012345678900123456789001234567890012345678900123456789001234567890012345678900123456789001234567890012345678900123456789001234567890
사진 파일?

java.sql.SQLDataException: (conn=57) Data too long for column 'PATH' at row 1 : (conn=57) Data too long for column 'PATH' at row 1

명령> /photoboard/list
  1, 수업 오리엔테이션           , 2018-11-14, 0, 1
  2, 1차 과제 발표            , 2018-11-14, 0, 1
  3, null                , 2018-11-14, 0, 2
  4, 과제 발표회              , 2018-11-14, 0, 3
  6, 발표2                 , 2018-11-14, 0, 1
  8, test1               , 2018-11-15, 0, 2
 11, okokok              , 2018-11-15, 0, 1
 12, test..100           , 2018-11-15, 0, 1
```

위 실행 결과를 보면 사진 파일을 입력할 때 사진 파일명이 컬럼의 길이 보다 길어 예외가 발생했다. 트랜잭션 관리자에 의해 기존의 입력까지도 모두 취소되었음을 확인할 수 있다.

다음과 같이 정상적으로 실행한다면 `PHOTO`테이블과 `PHO_FILE` 테이블에 제대로 값이 들어간다.

```
명령> /photoboard/add
제목?
transaction... ok!!!
수업?
1
최소 한 개의 사진 파일을 등록해야 합니다.
파일명 입력 없이 그냥 엔터를 치면 파일 추가를 마칩니다.
사진 파일?
b1.png
사진 파일?
b2.png
사진 파일?

사진을 저장했습니다.

명령> /photoboard/list
  1, 수업 오리엔테이션           , 2018-11-14, 0, 1
  2, 1차 과제 발표            , 2018-11-14, 0, 1
  3, null                , 2018-11-14, 0, 2
  4, 과제 발표회              , 2018-11-14, 0, 3
  6, 발표2                 , 2018-11-14, 0, 1
  8, test1               , 2018-11-15, 0, 2
 11, okokok              , 2018-11-15, 0, 1
 12, test..100           , 2018-11-15, 0, 1
 20, transaction... ok!!!, 2018-11-16, 0, 1

명령> /photoboard/detail
번호?
20
제목: transaction... ok!!!
작성일: 2018-11-16
조회수: 0
수업: 1
사진 파일:
> b1.png
> b2.png

명령> 
```


## 실습 소스

- src/main/java/com/eomcs/sql/TxConnection.java 추가
- src/main/java/com/eomcs/sql/TransactionManager.java 추가
- src/main/java/com/eomcs/sql/DataSource.java 변경
- src/main/java/com/eomcs/lms/DataLoaderListener.java 변경
- src/main/java/com/eomcs/lms/handler/PhotoBoardAddCommand.java 변경
- src/main/java/com/eomcs/lms/handler/PhotoBoardUpdateCommand.java 변경
- src/main/java/com/eomcs/lms/handler/PhotoBoardDeleteCommand.java 변경
- src/main/java/com/eomcs/lms/App.java 변경
