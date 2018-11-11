# eomcs-java-project-4.5

JDBC API로 데이터베이스를 사용하기

- JDBC API를 활용하여 데이터를 입출력하는 방법
- JDBC 프로그래밍 코드를 캡슐화하여 DAO를 정의하기
  
## 프로젝트 - 수업관리 시스템  

### 과제: MariaDB에 애플리케이션에서 사용할 사용자와 데이터베이스를 추가한다.

MariaDB 에 연결하기 
> $ mysql -u 사용자아이디 -p
```
$ mysql -u root -p
Enter password: 암호입력
...

MariaDB [(none)]>
```

사용자 생성하기
> CREATE USER '사용자아이디'@'서버주소' IDENTIFIED BY '암호';

```
MariaDB [(none)]> CREATE USER 'eomcs'@'localhost' IDENTIFIED BY '1111';
```

데이터베이스 생성하기
> CREATE DATABASE 데이터베이스명 
> CHARACTER SET utf8 
> COLLATE utf8_general_ci;
```
MariaDB [(none)]> CREATE DATABASE eomcs
  CHARACTER SET utf8
  COLLATE utf8_general_ci;
```

사용자에게 DB 사용 권한을 부여하기
> GRANT ALL ON 데이터베이스명.* TO '사용자아이디'@'서버주소';
```
GRANT ALL ON eomcs.* TO 'eomcs'@'localhost';
```

MariaDB에 `eomcs` 사용자 아이디로 다시 접속하기
```
$ mysql -u eomcs -p
Enter password: 1111
...

MariaDB [(none)]>
```

`eomcs` 아이디로 사용할 수 있는 데이터베이스 목록 보기
```
MariaDB [(none)]> show databases;
+--------------------+
| Database           |
+--------------------+
| eomcs              |
| information_schema |
| test               |
+--------------------+
3 rows in set (0.000 sec)

MariaDB [(none)]> 
```



### 과제: 애플리케이션에서 사용할 테이블과 예제 데이터를 준비하라.

`eomcs` 아이디로 MariaDB에 접속한 후 기본으로 사용할 데이터베이스를 `eomcs`로 설정하기
```
MariaDB [(none)]> use eomcs;
...

Database changed
MariaDB [eomcs]> 
``` 

애플리케이션에서 사용할 테이블 생성하기. 
```
다음 파일의 내용을 복사하여 MariaDB 명령창에 붙여 넣고 실행한다.
eomcs-java-project    (Git 저장소)
    /eomcs-dbmodel    
        /model
            /ddl.sql  (테이블 정의 SQL 문이 들어 있는 파일)
```

생성된 테이블에 예제 데이터 입력하기. 
```
다음 파일의 내용을 복사하여 MariaDB 명령창에 붙여 넣고 실행한다.
eomcs-java-project    (Git 저장소)
    /eomcs-dbmodel    
        /model
            /data.sql  (INSERT SQL 문이 들어 있는 파일)
```

### 과제: 프로젝트에 `MariaDB` JDBC 드라이버를 추가하라.

- build.gradle
    - 의존 라이브러리에 MariaDB JDBC Driver 정보를 추가한다.

`gradle`을 실행하여 이클립스 설정 파일을 갱신하기
```
$ gradle eclipse
```

이클립스 워크스페이스의 프로젝트를 갱신하기
> 이클립스에서도 프로젝트에 `Refresh`를 수행해야 라이브러리가 적용된다.

### 과제: 수업 데이터를 데이터베이스를 사용하여 관리하라.

- JdbcContextListener.java
    - 애플리케이션이 시작될 때 DB 커넥션을 생성한다.
    - 애플리케이션이 종료될 때 DB 커넥션을 닫는다.
- App.java
    - 파일에서 데이터를 로딩하는 일을 했던 `DataLoaderListener`는 제거한다.
    - DB 연결을 준비하는 `JdbcContextListener`를 추가한다.
    - 서블릿 객체를 생성할 때 `List` 대신 `Connection` 객체를 넘겨준다.
- LessonListServlet.java
    - `App` 객체로부터 받은 `Connection` 객체를 사용하여 DB에서 데이터를 꺼내온다.
- LessonDetailServlet.java
    - `App` 객체로부터 받은 `Connection` 객체를 사용하여 DB에서 해당 번호의 데이터를 꺼내온다.
- LessonAddServlet.java
    - `App` 객체로부터 받은 `Connection` 객체를 사용하여 DB에 데이터를 저장한다.
- LessonUpdateServlet.java
    - `App` 객체로부터 받은 `Connection` 객체를 사용하여 해당 번호의 DB 데이터를 변경한다.
- LessonDeleteServlet.java
    - `App` 객체로부터 받은 `Connection` 객체를 사용하여 해당 번호의 DB 데이터를 삭제한다.
     
#### 실행 결과

먼저 `ServerApp`을 실행한다.
```
서버 실행 중!
MariaDB에 연결했습니다.
```

`ClientApp`을 실행한다. 실행 결과는 이전 버전과 같다.

```
명령> /lesson/list
  1, 자바 기초 프로그래밍    , 2019-01-02 ~ 2019-01-15,   80
  2, 자바 고급 프로그래밍    , 2019-01-14 ~ 2019-01-25,   80
  3, 자바 웹 프로그래밍     , 2019-01-21 ~ 2019-02-01,   80
  4, IoT 프로그래밍      , 2019-02-04 ~ 2019-03-04,   80
  5, C# 프로그래밍       , 2019-02-04 ~ 2019-02-15,   40
  6, 자바 프로젝트        , 2019-01-01 ~ 2019-02-07,   80

명령> /lesson/add?title=과정명입니다.&contents=과정설명입니다.&startDate=2019-1-1&endDate=2019-1-30&totalHours=80&dayHours=8&memberNo=1
수업을 저장했습니다.

명령> /lesson/list
  1, 자바 기초 프로그래밍    , 2019-01-02 ~ 2019-01-15,   80
  2, 자바 고급 프로그래밍    , 2019-01-14 ~ 2019-01-25,   80
  3, 자바 웹 프로그래밍     , 2019-01-21 ~ 2019-02-01,   80
  4, IoT 프로그래밍      , 2019-02-04 ~ 2019-03-04,   80
  5, C# 프로그래밍       , 2019-02-04 ~ 2019-02-15,   40
  6, 자바 프로젝트        , 2019-01-01 ~ 2019-02-07,   80
  7, 과정명입니다.        , 2019-01-01 ~ 2019-01-30,   80

명령> /lesson/detail?no=7
수업명: 과정명입니다.
설명: 과정설명입니다.
기간: 2019-01-01 ~ 2019-01-30
총수업시간: 80
일수업시간: 8

명령> /lesson/update?no=7&title=자바 웹 프로젝트&contents=웹 프로젝트 강의입니다.&startDate=2019-1-1&endDate=2019-2-15&totalHours=120&dayHours=8&memberNo=1
수업을 변경했습니다.

명령> /lesson/detail?no=7
수업명: 자바 웹 프로젝트
설명: 웹 프로젝트 강의입니다.
기간: 2019-01-01 ~ 2019-02-15
총수업시간: 120
일수업시간: 8

명령> /lesson/delete?no=7
수업을 삭제했습니다.

명령> /lesson/list
  1, 자바 기초 프로그래밍    , 2019-01-02 ~ 2019-01-15,   80
  2, 자바 고급 프로그래밍    , 2019-01-14 ~ 2019-01-25,   80
  3, 자바 웹 프로그래밍     , 2019-01-21 ~ 2019-02-01,   80
  4, IoT 프로그래밍      , 2019-02-04 ~ 2019-03-04,   80
  5, C# 프로그래밍       , 2019-02-04 ~ 2019-02-15,   40
  6, 자바 프로젝트        , 2019-01-01 ~ 2019-02-07,   80

명령> 
```

## 실습 소스

- src/main/java/com/eomcs/lms/DataLoaderListener.java 삭제
- 
- src/main/java/com/eomcs/lms/ServerApp.java 변경
