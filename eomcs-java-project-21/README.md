# eomcs-java-project-3.0

Stack 자료구조 구현과 활용

- Stack 자료구조를 구현하는 방법
- Stack 의 구동원리 이해하기
- Stack 의 활용 사례
- 상속의 활용

  
## 프로젝트 - 수업관리 시스템  

### 과제 1: Stack 자료구조를 구현하라.

- Stack.java 
    - 이전에 만든 `LinkedList` 를 상속 받아 Stack 자료 구조를 구현한다.
    - 제네릭을 적용한다.
    - 객체 복제가 가능하도록 Cloneable 인터페이스를 구현한다.

### 과제 2: 사용자가 입력한 명령어를 Stack에 보관하라.

- App.java
    - 사용자가 입력한 명령어를 Stack에 보관한다.
    - 최대 10개만 보관한다.

### 과제 3: 사용자가 입력한 명령을 최신순으로 출력하는 `history` 명령을 추가하라.

- App.java
    - 명령어 내역을 출력하는 printCommandHistory()를 정의한다.
    - `history` 명령을 처리하는 분기문을 추가한다.

#### 실행 결과

```
명령> /lesson/list
...

명령> /lesson/add
...

명령> /member/list
...

명령> /board/detail
...

명령> history
/board/detail
/member/list
/lesson/add
/lesson/list

```


## 실습 소스

- src/main/java/com/eomcs/util/Stack.java 추가
- src/main/java/com/eomcs/lms/App.java 변경