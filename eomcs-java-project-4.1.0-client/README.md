# eomcs-java-project-4.1-client

네트워크 API를 활용하여 애플리케이션 사이에 데이터 주고 받기

- 데이터 관리를 별도의 프로그램으로 분리하기
- 네트워크 API 사용법
- 클라이언트/서버 애플리케이션 아키텍처의 이해
- `Stateful` 통신 방식의 특징과 장단점 이해하기
- 상속의 일반화(generalization) 기법과 추상 클래스 활용법

## 프로젝트 - 수업관리 시스템  

### 과제: 사용자 UI 부분을 클라이언트로 만들라.

`App.java`에서 데이터 관리 부분을 제외한 사용자 UI 부분을 추출한다.

- `eomcs-java-project-client` 프로젝트로 분리한다.

#### ver 4.1.0

데이터 관리 부분을 제외한 나머지를 클라이언트로 만들라.

- App.java
    - `App.java` 파일을 복사해서 `ClientApp.java`를 만든다.
    - 파일에서 데이터를 로딩하는 코드를 제거한다.
    - `Observer` 등록 및 실행 코드를 제거한다.
    - 사용자로부터 명령을 입력 받아서 서버로 전송한다.
    - 서버로부터 받은 데이터를 화면에 출력한다.
    - 입력 명령의 기록은 Stack에만 보관한다.

#### 실행 결과

먼저 `ServerApp`을 실행한다.
```
서버 실행 중!
데이터를 읽어옵니다.
```

`ClientApp`을 실행한다. 수업에 대해 CRUD 테스트를 한다.
```
명령> /lesson/list
  1, 1              , 2019-01-01 ~ 2019-02-02,    1
  2, 2              , 2019-02-02 ~ 2019-03-03,    2
  3, 3              , 2019-03-03 ~ 2019-04-04,  100

명령> /lesson/add?n=4&t=자바프로그래밍&c=자바 기본 문법 설명&sd=2019-1-1&ed=2019-3-3&th=100&dh=8
저장하였습니다.

명령> /lesson/list
  1, 1              , 2019-01-01 ~ 2019-02-02,    1
  2, 2              , 2019-02-02 ~ 2019-03-03,    2
  3, 3              , 2019-03-03 ~ 2019-04-04,  100
  4, 자바프로그래밍        , 2019-01-01 ~ 2019-03-03,  100

명령> /lesson/detail?n=4
수업명: 자바프로그래밍
설명: 자바 기본 문법 설명
기간: 2019-01-01 ~ 2019-03-03
총수업시간: 100
일수업시간: 8

명령> /lesson/update?n=4&t=자바프로그래밍x&c=자바 기본 문법 설명x&sd=2019-1-2&ed=2019-3-4&th=101&dh=9
수업을 변경했습니다.

명령> /lesson/detail?n=4
수업명: 자바프로그래밍x
설명: 자바 기본 문법 설명x
기간: 2019-01-02 ~ 2019-03-04
총수업시간: 101
일수업시간: 9

명령> /lesson/delete?n=4
수업을 삭제했습니다.

명령> /lesson/list
  1, 1              , 2019-01-01 ~ 2019-02-02,    1
  2, 2              , 2019-02-02 ~ 2019-03-03,    2
  3, 3              , 2019-03-03 ~ 2019-04-04,  100

명령> 
```


## 실습 소스

- src/main/java/com/eomcs/lms/servlet 패키지 생성
- src/main/java/com/eomcs/lms/servlet/Servlet.java 추가
- src/main/java/com/eomcs/lms/servlet/LessonAddServlet.java 추가
- src/main/java/com/eomcs/lms/servlet/LessonDeleteServlet.java 추가
- src/main/java/com/eomcs/lms/servlet/LessonDetailServlet.java 추가
- src/main/java/com/eomcs/lms/servlet/LessonListServlet.java 추가
- src/main/java/com/eomcs/lms/servlet/LessonUpdateServlet.java 추가
- src/main/java/com/eomcs/lms/servlet/MemberAddServlet.java 추가
- src/main/java/com/eomcs/lms/servlet/MemberDeleteServlet.java 추가
- src/main/java/com/eomcs/lms/servlet/MemberDetailServlet.java 추가
- src/main/java/com/eomcs/lms/servlet/MemberListServlet.java 추가
- src/main/java/com/eomcs/lms/servlet/MemberUpdateServlet.java 추가
- src/main/java/com/eomcs/lms/servlet/BoardAddServlet.java 추가
- src/main/java/com/eomcs/lms/servlet/BoardDeleteServlet.java 추가
- src/main/java/com/eomcs/lms/servlet/BoardDetailServlet.java 추가
- src/main/java/com/eomcs/lms/servlet/BoardListServlet.java 추가
- src/main/java/com/eomcs/lms/servlet/BoardUpdateServlet.java 추가
- src/main/java/com/eomcs/lms/ClientApp.java 추가
- src/main/java/com/eomcs/lms/ServerApp.java 추가
- src/main/java/com/eomcs/lms/handler 패키지 및 하위 클래스 모두 삭제
- src/main/java/com/eomcs/lms/App.java 삭제
