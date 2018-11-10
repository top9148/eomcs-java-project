# eomcs-java-project-4.5

JDBC API로 데이터베이스를 사용하기

- JDBC API를 활용하여 데이터를 입출력하는 방법
- JDBC 프로그래밍 코드를 DAO 객체에 정의하기
  
## 프로젝트 - 수업관리 시스템  

### 과제: 스레드풀을 적용하라.

- ServerApp.java
    - 클라이언트가 연결되면 요청을 처리할 `RequestHandler`를 만들어 스레드풀에 실행을 맡긴다.

#### 실행 결과

먼저 `ServerApp`을 실행한다.
```
서버 실행 중! 
데이터를 읽어옵니다.
```

`ClientApp`을 실행한다. 실행 결과는 이전 버전과 같다.


## 실습 소스

- src/main/java/com/eomcs/lms/ServerApp.java 변경
