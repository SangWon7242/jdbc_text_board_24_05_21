package com.sbs.text.board;

import com.sbs.text.board.container.Container;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {
  public int articleLastId;
  public List<Article> articles;
  public Scanner sc;

  public App() {
    articleLastId = 0;
    articles = new ArrayList<>();
    sc = Container.scanner;
  }

  public void run() {
    while (true) {
      System.out.print("명령) ");
      String cmd = sc.nextLine();

      Rq rq = new Rq(cmd);
    
      // DB 연결시작
      String jdbcDriver = "com.mysql.cj.jdbc.Driver";

      // 데이터베이스 URL, 사용자 이름, 비밀번호를 설정합니다.
      String url = "jdbc:mysql://127.0.0.1:3306/text_board?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull"; // 데이터베이스 URL
      String user = "sbsst"; // 데이터베이스 사용자 이름
      String password = "sbs123414"; // 데이터베이스 비밀번호

      // Connection 객체 선언
      Connection conn = null;

      try {
        // 드라이버 로드 (필요에 따라 생략 가능, 최신 드라이버는 자동 로드)
        Class.forName(jdbcDriver);

        // 데이터베이스 연결
        conn = DriverManager.getConnection(url, user, password);

        doAction(conn, sc, rq);

      } catch (ClassNotFoundException e) {
        e.printStackTrace();
        System.out.println("드라이버 로딩 실패!!");
      } catch (SQLException e) {
        System.out.println("에러 : " + e);
        System.out.println("연결 실패!!");
      } finally {
        // 연결 닫기
        try {
          if (conn != null & !conn.isClosed()) {
            conn.close();
          }
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
      // DB 연결 끝

      //sc.close();
    }
  }

  private void doAction(Connection conn, Scanner sc, Rq rq) {
    if (rq.getUrlPath().equals("/usr/article/write")) {
      System.out.println("== 게시물 추가 ==");

      System.out.print("제목) ");
      String title = sc.nextLine();
      System.out.print("내용) ");
      String body = sc.nextLine();

      int id = ++articleLastId;

      PreparedStatement prst = null;

      // SQL 삽입 명령 준비
      String sql = "INSERT INTO article";
      sql += " SET regDate = NOW()";
      sql += ", updateDate = NOW()";
      sql += ", title = '%s'".formatted(title);
      sql += ", `body` = '%s'".formatted(body);

      try {
        prst = conn.prepareStatement(sql);
        // 명령 실행
        prst.executeUpdate();
      } catch (SQLException e) {
        System.out.println("에러 : " + e);
        System.out.println("연결 실패!!");
      } finally {
        try {
          if (prst != null && !prst.isClosed()) {
            prst.close();
          }
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }

      Article article = new Article(id, title, body);
      System.out.println("article : " + article);
      articles.add(article);

      System.out.printf("%d번 게시물이 등록되었습니다.\n", article.id);
    } else if (rq.getUrlPath().equals("/usr/article/list")) {
      PreparedStatement prst = null;
      ResultSet rs = null;

      // SQL 조회
      String sql = "SELECT *";
      sql += " FROM article";
      sql += " ORDER BY id DESC";

      try {
        prst = conn.prepareStatement(sql);
        // 명령 실행 및 결과 집합 가져오기
        rs = prst.executeQuery();

        while (rs.next()) {
          int id = rs.getInt("id");
          String regDate = rs.getString("regDate");
          String updateDate = rs.getString("updateDate");
          String title = rs.getString("title");
          String body = rs.getString("body");

          Article article = new Article(id, regDate, updateDate, title, body);
          articles.add(article);
        }

      } catch (SQLException e) {
        System.out.println("에러 : " + e);
        System.out.println("연결 실패!!");
      } finally {
        try {
          if (rs != null && !rs.isClosed()) {
            rs.close();
          }
        } catch (SQLException e) {
          e.printStackTrace();
        }
        try {
          if (prst != null && !prst.isClosed()) {
            prst.close();
          }
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }

      System.out.println("== 게시물 리스트 ==");

      if (articles.isEmpty()) {
        System.out.println("게시물이 존재하지 않습니다.");
        return;
      }

      for (Article article : articles) {
        System.out.printf("%d / %s\n", article.id, article.title);
      }

    } else if (rq.getUrlPath().equals("/usr/article/modify")) {
      System.out.println("== 게시물 수정 ==");

      int id = rq.getIntParam("id", 0);

      if (id == 0) {
        System.out.printf("id를 올바르게 입력해주세요.");
        return;
      }

      System.out.print("새 제목 : ");
      String title = sc.nextLine();

      System.out.print("새 내용 : ");
      String body = sc.nextLine();

      PreparedStatement prst = null;

      // SQL 업데이트
      String sql = "UPDATE article";
      sql += " SET title = '%s'".formatted(title);
      sql += ", `body` = '%s'".formatted(body);
      sql += " WHERE id = %d;".formatted(id);

      try {
        prst = conn.prepareStatement(sql);
        // 명령 실행 및 결과 집합 가져오기
        prst.executeUpdate();

      } catch (SQLException e) {
        System.out.println("에러 : " + e);
        System.out.println("연결 실패!!");
      } finally {
        try {
          if (prst != null && !prst.isClosed()) {
            prst.close();
          }
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
      System.out.printf("%d번 게시물이 수정되었습니다.\n", id);

    } else if (rq.getUrlPath().equals("exit")) {
      System.out.println("프로그램을 종료합니다.");
      return;
    } else {
      System.out.println("잘못 된 명령어입니다.");
    }
  }
}
