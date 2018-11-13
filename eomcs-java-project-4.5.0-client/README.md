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

