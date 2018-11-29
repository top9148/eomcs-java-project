# eomcs-java-project-6.2-server

Log4j를 사용하여 애플리케이션 로그 처리하기

- Log4j를 설정하고 이용하는 방법
- Log4j2를 설정하고 이용하는 방법

## 프로젝트 - 수업관리 시스템  

애플리케이션의 실행 상태를 확인하고 싶을 때 보통 `System.out.println()`을 사용하여 변수의 값이나 
메서드의 리턴 값, 객체의 필드 값을 출력한다. 
이 방식의 문제는 개발을 완료한 후 이런 코드를 찾아 제거하기가 매우 번거롭다는 것이다. 
또한 콘솔 출력이 아닌 파일이나 네트웍으로 출력하려면 별개의 코드를 작성해야 한다.
이런 문제점을 해결하기 위해 나온 것이 `Log4j`라는 라이브러리이다.
개발 중에는 로그를 자세하게 출력하고 개발이 완료된 후에는 중요 로그만 출력하도록 조정하는 기능을 제공한다.
로그의 출력 형식을 지정할 수 있다. 출력 대상도 콘솔, 파일, 네트워크, DB 등 다양하게 지정할 수 있다.

### ver 6.2.0 - `System.out.println()` 대신에 Log4j를 적용하여 로그를 출력하라.

#### 1단계) Log4j 라이브러리를 추가한다.

- 라이브러리 정보 알아내기
    - `mvnrepository.com`에서 `log4j`를 검색한다.
- build.gradle
    - `log4j` 라이브러리 정보를 추가한다.
    - `$ gradle eclipse`를 실행하여 이클립스 설정 파일을 갱신한다.
    - 이클립스 워크스페이스에 로딩되어 있는 클래스를 갱신한다.

#### 2단계) Log4j 설정 파일을 추가한다.

- src/main/resources 
    - 애플리케이션이 실행 중에 사용하는 `.properties`, `.xml`, `.txt` 와 같은 설정 파일을 두는 디렉토리이다.
    - 디렉토리를 추가한 후, `$ gradle eclipse` 를 다시 실행하여 이클립스 설정 파일을 갱신한다.
    - 그래야만 `resources` 폴더가 소스 폴더가 된다.
- log4j.properties
    - `Log4j` 의 출력 대상, 출력 형식 등을 정의한 설정 파일이다.
    - 자바 CLASSPATH의 루트 디렉토리에 파일을 둔다.

#### 3단계) 각 클래스의 로그 출력을 Log4j로 전환한다.

- App.java
    - 기존에는 `System.out.println()`을 사용하여 출력하였다.
    - Log4j로 전환한다.
- ContextLoaderListener.java
    - 기존에는 `System.out.println()`을 사용하여 출력하였다.
    - Log4j로 전환한다.


##### 실습 결과

`eomcs-java-project-server` 프로젝트의 `App` 클래스를 실행한다.
```
DEBUG [main] - DataLoaderListener.contextInitialized() 실행!
DEBUG [main] - 커맨드 핸들러의 매핑 정보 준비하기
DEBUG [main] - org.springframework.context.annotation.ConfigurationClassPostProcessor 클래스의 커맨드 매핑:
DEBUG [main] - org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor 클래스의 커맨드 매핑:
DEBUG [main] - org.springframework.context.event.EventListenerMethodProcessor 클래스의 커맨드 매핑:
DEBUG [main] - org.springframework.context.event.DefaultEventListenerFactory 클래스의 커맨드 매핑:
DEBUG [main] - com.eomcs.lms.AppConfig 클래스의 커맨드 매핑:
DEBUG [main] - com.eomcs.lms.handler.AuthHandler 클래스의 커맨드 매핑:
DEBUG [main] - ==> /auth/login : login()
DEBUG [main] - com.eomcs.lms.handler.BoardHandler 클래스의 커맨드 매핑:
DEBUG [main] - ==> /board/add : add()
DEBUG [main] - ==> /board/update : update()
DEBUG [main] - ==> /board/list : list()
DEBUG [main] - ==> /board/delete : delete()
DEBUG [main] - ==> /board/detail : detail()
DEBUG [main] - com.eomcs.lms.handler.LessonHandler 클래스의 커맨드 매핑:
DEBUG [main] - ==> /lesson/add : add()
DEBUG [main] - ==> /lesson/update : update()
DEBUG [main] - ==> /lesson/list : list()
DEBUG [main] - ==> /lesson/delete : delete()
DEBUG [main] - ==> /lesson/search : search()
DEBUG [main] - ==> /lesson/detail : detail()
DEBUG [main] - com.eomcs.lms.handler.MemberHandler 클래스의 커맨드 매핑:
DEBUG [main] - ==> /member/add : add()
DEBUG [main] - ==> /member/update : update()
DEBUG [main] - ==> /member/list : list()
DEBUG [main] - ==> /member/delete : delete()
DEBUG [main] - ==> /member/search : search()
DEBUG [main] - ==> /member/detail : detail()
DEBUG [main] - com.eomcs.lms.handler.PhotoBoardHandler$$EnhancerBySpringCGLIB$$608ff98c 클래스의 커맨드 매핑:
DEBUG [main] - Spring AOP가 자동 생성한 프록시 객체이다.
DEBUG [main] - ==> /photoboard/add : add()
DEBUG [main] - ==> /photoboard/update : update()
DEBUG [main] - ==> /photoboard/list : list()
DEBUG [main] - ==> /photoboard/delete : delete()
DEBUG [main] - ==> /photoboard/detail : detail()
DEBUG [main] - ==> /photoboard/detail2 : detail2()
DEBUG [main] - org.springframework.transaction.annotation.ProxyTransactionManagementConfiguration$$EnhancerBySpringCGLIB$$17fd01ce 클래스의 커맨드 매핑:
DEBUG [main] - org.springframework.transaction.interceptor.BeanFactoryTransactionAttributeSourceAdvisor 클래스의 커맨드 매핑:
DEBUG [main] - org.springframework.transaction.annotation.AnnotationTransactionAttributeSource 클래스의 커맨드 매핑:
DEBUG [main] - org.springframework.transaction.interceptor.TransactionInterceptor 클래스의 커맨드 매핑:
DEBUG [main] - org.springframework.transaction.event.TransactionalEventListenerFactory 클래스의 커맨드 매핑:
DEBUG [main] - org.apache.commons.dbcp2.BasicDataSource 클래스의 커맨드 매핑:
DEBUG [main] - org.apache.ibatis.session.defaults.DefaultSqlSessionFactory 클래스의 커맨드 매핑:
DEBUG [main] - org.springframework.jdbc.datasource.DataSourceTransactionManager 클래스의 커맨드 매핑:
DEBUG [main] - org.springframework.aop.framework.autoproxy.InfrastructureAdvisorAutoProxyCreator 클래스의 커맨드 매핑:
DEBUG [main] - com.sun.proxy.$Proxy27 클래스의 커맨드 매핑:
DEBUG [main] - com.sun.proxy.$Proxy28 클래스의 커맨드 매핑:
DEBUG [main] - com.sun.proxy.$Proxy26 클래스의 커맨드 매핑:
DEBUG [main] - com.sun.proxy.$Proxy30 클래스의 커맨드 매핑:
DEBUG [main] - com.sun.proxy.$Proxy31 클래스의 커맨드 매핑:
 INFO [main] - 서버 실행!
 INFO [main] - 클라이언트와 연결되었음.
DEBUG [main] - 스레드 생성됨!
DEBUG [pool-1-thread-1] - 스레드 실행...
DEBUG [pool-1-thread-1] - 명령어: /lesson/list
 INFO [pool-1-thread-1] - 클라이언트와 연결 종료!
DEBUG [pool-1-thread-1] - 스레드 종료!
```

서버 시작 후 `eomcs-java-project-client`프로젝트의 `ClientApp`을 실행한다.
```
이전과 같다.
```

## 실습 소스

- build.gradle 변경
- src/main/resources 디렉토리 생성
- src/main/resources/log4j.properties 생성
- com/eomcs/lms/App.java 변경
- com/eomcs/lms/ContextLoaderListener.java 변경