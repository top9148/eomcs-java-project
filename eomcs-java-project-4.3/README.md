# eomcs-java-project-4.3

여러 클라이언트의 요청을 동시에 처리하는 방법

- `Thread`를 활용하여 멀티 스레드 프로그래밍하는 방법
- 중첩 클래스의 활용

  
## 프로젝트 - 수업관리 시스템  

### 과제: 클라이언트 요청이 들어오면 별도의 스레드에서 처리하라.

- RequestHandler 중첩 클래스
    - ServerApp 클래스에서 클라이언트 요청을 처리하는 코드를 이 클래스로 옮긴다.
- ServerApp.java
    - 클라이언트가 연결되면 스레드에게 요청 처리를 맡긴다.

#### 실행 결과

먼저 `ServerApp`을 실행한다.
```
서버 실행 중! 
데이터를 읽어옵니다.
```

`ClientApp`을 실행한다. 실행 결과는 이전 버전과 같다.


## 실습 소스

- src/main/java/com/eomcs/lms/ServerApp.java 변경
