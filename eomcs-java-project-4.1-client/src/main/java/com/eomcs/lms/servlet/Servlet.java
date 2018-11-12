package com.eomcs.lms.servlet;

import java.io.PrintWriter;
import java.util.Map;

public interface Servlet {
  // 파라미터 out은 클라이언트로 콘텐트를 출력할 때 사용할 출력 스트림이다.
  void service(Map<String,String> paramMap, PrintWriter out) throws Exception;
}
