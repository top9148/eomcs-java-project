# eomcs-java-project-4.2

동일한 자원으로 더 많은 클라이언트의 요청을 처리하는 방법

- `Stateless` 통신 방식으로 전환하기
- `Stateless` 통신 방식의 특징과 장단점 이해하기

  
## 프로젝트 - 수업관리 시스템  

### 과제: 통신 방식을 `Stateful`에서 `Stateless`로 바꿔라.

- ClientApp.java
    - 서버에 명령을 보낼 때 마다 네트워크에 연결한다.
    - 데이터를 보내고 받은 후에 연결을 끊는다.
- ServerApp.java
    - 클라이언트와 연결되면 명령을 처리한 후 연결을 끊는다.

#### 실행 결과

먼저 `ServerApp`을 실행한다.
```
서버 실행 중! 
데이터를 읽어옵니다.
```

`ClientApp`을 실행한다. 실행 결과는 이전 버전과 같다.


## 실습 소스

- src/main/java/com/eomcs/lms/ClientApp.java 변경
- src/main/java/com/eomcs/lms/ServerApp.java 변경
