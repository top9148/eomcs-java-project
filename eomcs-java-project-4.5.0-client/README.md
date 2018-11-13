# eomcs-java-project-4.4-client

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
> CREATE DATABASE 데이터베이스명 CHARACTER SET utf8 COLLATE utf8_general_ci;
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
    - 예를 들면, `mvnrepository.com`에서 키워드 `mariadb jdbc`로 검색하면 **MariaDB Java Client** 라이브러리를 찾을 수 있다.
    - 최신 버전의 라이브러리 정보를 가져오면 된다.

build.gradle 파일
```
plugins {
    id 'java'
    id 'application'
    id 'eclipse'
}

tasks.withType(JavaCompile) {
    options.encoding = 'UTF-8'
    sourceCompatibility = '1.8'
    targetCompatibility = '1.8'
}

mainClassName = 'App'

dependencies {
    compile group: 'org.mariadb.jdbc', name: 'mariadb-java-client', version: '2.3.0'
    compile 'com.google.guava:guava:23.0'
    testCompile 'junit:junit:4.12'
}

repositories {
    jcenter()
}
```

`gradle`을 실행하여 이클립스 설정 파일을 갱신하기
```
$ gradle eclipse
```

이클립스 워크스페이스의 프로젝트를 갱신하기
> 이클립스에서도 프로젝트에 `Refresh`를 수행해야 라이브러리가 적용된다.

### 과제: 수업 데이터를 데이터베이스를 사용하여 관리하라.

- `com.eomcs.lms.proxy` ==> `com.eomcs.lms.dao` 패키지 이름 변경
    - `proxy` 패키지명 대신에 데이터를 처리하는 객체를 두는 패키지라는 의미로 `dao` 라는 이름으로 변경한다.
- `LessonDaoProxy.java` ==> `LessonDao.java` 이름 변경
    - JDBC API를 사용하여 DB 서버에 데이터를 보관하고 꺼낸다.
    - 클래스 이름에서 `Proxy`를 뺀다.
- `MemberDaoProxy.java` ==> `MemberDao.java` 이름 변경
    - JDBC API를 사용하여 DB 서버에 데이터를 보관하고 꺼낸다.
    - 클래스 이름에서 `Proxy`를 뺀다.
- `BoardDaoProxy.java` ==> `BoardDao.java` 이름 변경
    - JDBC API를 사용하여 DB 서버에 데이터를 보관하고 꺼낸다.
    - 클래스 이름에서 `Proxy`를 뺀다.
- DataLoaderListener.java
    - 애플리케이션이 시작할 때 MariaDB 데이터베이스 서버에 연결한다.
    - 애플리케이션이 종료할 때 MariaDB 데이터베이스 서버와 연결을 끊는다.
    - DAO 객체를 생성할 때 Connection 객체를 파라미터로 제공한다.
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

서버 애플리케이션의 `ServerApp`을 먼저 실행한다.
```
수업 데이터를 로딩 성공!
회원 데이터를 로딩 성공!
게시글 데이터를 로딩 성공!
서버 시작!
클라이언트와 연결되었음.
스레드 생성됨!
스레드 실행... - pool-1-thread-1
/lesson/list 명령을 처리중...
클라이언트와 연결을 끊었음.
수업 데이터를 저장했습니다.
회원 데이터를 저장했습니다.
게시글 데이터를 저장했습니다.
클라이언트와 연결되었음.
스레드 생성됨!
스레드 실행... - pool-1-thread-2
/lesson/list 명령을 처리중...
클라이언트와 연결을 끊었음.
수업 데이터를 저장했습니다.
회원 데이터를 저장했습니다.
게시글 데이터를 저장했습니다.
클라이언트와 연결되었음.
스레드 생성됨!
스레드 실행... - pool-1-thread-3
/lesson/list 명령을 처리중...
...
```

클라이언트 애플리케이션의 `App`을 실행한다. 실행은 이전 버전과 같다.
```
이전과 같다.
```


## 실습 소스

