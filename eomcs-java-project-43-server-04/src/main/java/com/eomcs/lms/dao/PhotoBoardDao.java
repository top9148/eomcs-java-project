package com.eomcs.lms.dao;

import java.util.List;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import com.eomcs.lms.domain.PhotoBoard;

public class PhotoBoardDao {

  SqlSessionFactory sqlSessionFactory;

  public PhotoBoardDao(SqlSessionFactory sqlSessionFactory) {
    this.sqlSessionFactory = sqlSessionFactory;
  }

  public int delete(int no) throws Exception {
    SqlSession session = sqlSessionFactory.openSession();
    try {
      int count = session.delete("PhotoBoardMapper.delete", no);
      session.commit(); // 기본이 수동 커밋 상태이기 때문에 작업이 끝난 후 commit 명령을 내려야 한다.
      return count;
    } finally {
      session.close();
    }
  }

  public int update(PhotoBoard photo) throws Exception {
    SqlSession session = sqlSessionFactory.openSession();
    try {
      int count = session.update("PhotoBoardMapper.update", photo);
      session.commit(); // 기본이 수동 커밋 상태이기 때문에 작업이 끝난 후 commit 명령을 내려야 한다.
      return count;
    } finally {
      session.close();
    }
  }

  public int add(PhotoBoard photo) throws Exception {
    SqlSession session = sqlSessionFactory.openSession();
    try {
      int count = session.insert("PhotoBoardMapper.add", photo);
      session.commit(); // 기본이 수동 커밋 상태이기 때문에 작업이 끝난 후 commit 명령을 내려야 한다.
      return count;
    } finally {
      session.close();
    }
  }

  public List<PhotoBoard> list() throws Exception {
    SqlSession session = sqlSessionFactory.openSession();
    try {
      return session.selectList("PhotoBoardMapper.list");
    } finally {
      session.close();
    }
  }

  public PhotoBoard detail(int no) throws Exception {
    SqlSession session = sqlSessionFactory.openSession();
    try {
      return session.selectOne("PhotoBoardMapper.detail", no);
    } finally {
      session.close();
    }
  }
}
