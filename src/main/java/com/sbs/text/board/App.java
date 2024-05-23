package com.sbs.text.board;

import com.sbs.text.board.container.Container;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {
  public void run() {
    Scanner sc = Container.scanner;

    int articleLastId = 0;
    List<Article> articles = new ArrayList<>();

    while (true) {
      System.out.print("명령) ");
      String cmd = sc.nextLine();

      Rq rq = new Rq(cmd);

      if(rq.getUrlPath().equals("/usr/article/write")) {
        System.out.println("== 게시물 추가 ==");
        System.out.print("제목) ");
        String title = sc.nextLine();
        System.out.print("내용) ");
        String body = sc.nextLine();

        int id = ++articleLastId;

        // 데이터베이스 URL, 사용자 이름, 비밀번호를 설정합니다.
        String url = "jdbc:mysql://127.0.0.1:3306/text_board?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull"; // 데이터베이스 URL
        String user = "sbsst"; // 데이터베이스 사용자 이름
        String password = "sbs123414"; // 데이터베이스 비밀번호

        // Connection 객체 선언
        Connection conn = null;
        PreparedStatement prst = null;

        try {
          // 드라이버 로드 (필요에 따라 생략 가능, 최신 드라이버는 자동 로드)
          Class.forName("com.mysql.cj.jdbc.Driver");

          // 데이터베이스 연결
          conn = DriverManager.getConnection(url, user, password);

          // SQL 삽입 명령 준비
          String sql = "INSERT INTO article";
          sql += " SET regDate = NOW()";
          sql += ", updateDate = NOW()";
          sql += ", title = '%s'".formatted(title);
          sql += ", `body` = '%s'".formatted(body);

          System.out.println("sql : " + sql);
          prst = conn.prepareStatement(sql);

          // 명령 실행
          int affetedRows = prst.executeUpdate();
          System.out.println("affetedRows : " + affetedRows);

        } catch (ClassNotFoundException e) {
          e.printStackTrace();
          System.out.println("드라이버 로딩 실패!!");
        } catch (SQLException e) {
          System.out.println("에러 : " + e);
          System.out.println("연결 실패!!");
        } finally {
          // 리소스 해제
          if (prst != null) {
            try {
              prst.close();
            } catch (SQLException e) {
              e.printStackTrace();
            }
          }
          // 연결 닫기
          if (conn != null) {
            try {
              conn.close();
              System.out.println("Connection closed.");
            } catch (SQLException e) {
              e.printStackTrace();
            }
          }
        }

        Article article = new Article(id, title, body);
        System.out.println("article : " + article);
        articles.add(article);

        System.out.printf("%d번 게시물이 등록되었습니다.\n", article.id);
      }
      else if(rq.getUrlPath().equals("/usr/article/list")) {

        // 데이터베이스 URL, 사용자 이름, 비밀번호를 설정합니다.
        String url = "jdbc:mysql://127.0.0.1:3306/text_board?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull"; // 데이터베이스 URL
        String user = "sbsst"; // 데이터베이스 사용자 이름
        String password = "sbs123414"; // 데이터베이스 비밀번호

        // Connection 객체 선언
        Connection conn = null;
        PreparedStatement prst = null;
        ResultSet rs = null;

        try {
          // 드라이버 로드 (필요에 따라 생략 가능, 최신 드라이버는 자동 로드)
          Class.forName("com.mysql.cj.jdbc.Driver");

          // 데이터베이스 연결
          conn = DriverManager.getConnection(url, user, password);

          // SQL 조회
          String sql = "SELECT *";
          sql += " FROM article";
          sql += " ORDER BY id DESC";

          // System.out.println("sql : " + sql);
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

        } catch (ClassNotFoundException e) {
          e.printStackTrace();
          System.out.println("드라이버 로딩 실패!!");
        } catch (SQLException e) {
          System.out.println("에러 : " + e);
          System.out.println("연결 실패!!");
        } finally {
          // 리소스 해제
          if (rs != null) {
            try {
              rs.close();
            } catch (SQLException e) {
              e.printStackTrace();
            }
          }
          // 리소스 해제
          if (prst != null) {
            try {
              prst.close();
            } catch (SQLException e) {
              e.printStackTrace();
            }
          }
          // 연결 닫기
          if (conn != null) {
            try {
              conn.close();
              System.out.println("Connection closed.");
            } catch (SQLException e) {
              e.printStackTrace();
            }
          }
        }

        System.out.println("== 게시물 리스트 ==");

        if(articles.isEmpty()) {
          System.out.println("게시물이 존재하지 않습니다.");
          continue;
        }

        for(Article article : articles) {
          System.out.printf("%d / %s\n", article.id, article.title);
        }

      }
      else if(rq.getUrlPath().equals("/usr/article/modify")) {
        System.out.println("== 게시물 수정 ==");

        int id = rq.getIntParam("id", 0);

        if(id == 0) {
          System.out.printf("id를 올바르게 입력해주세요.");
          return;
        }

        System.out.print("새 제목 : ");
        String title = sc.nextLine();

        System.out.print("새 내용 : ");
        String body = sc.nextLine();

        // 데이터베이스 URL, 사용자 이름, 비밀번호를 설정합니다.
        String url = "jdbc:mysql://127.0.0.1:3306/text_board?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull"; // 데이터베이스 URL
        String user = "sbsst"; // 데이터베이스 사용자 이름
        String password = "sbs123414"; // 데이터베이스 비밀번호

        // Connection 객체 선언
        Connection conn = null;
        PreparedStatement prst = null;

        try {
          // 드라이버 로드 (필요에 따라 생략 가능, 최신 드라이버는 자동 로드)
          Class.forName("com.mysql.cj.jdbc.Driver");

          // 데이터베이스 연결
          conn = DriverManager.getConnection(url, user, password);

          // SQL 업데이트
          String sql = "UPDATE article";
          sql += " SET title = '%s'".formatted(title);
          sql += ", `body` = '%s'".formatted(body);
          sql += " WHERE id = %d;".formatted(id);

          System.out.println("sql : " + sql);
          prst = conn.prepareStatement(sql);

          // 명령 실행 및 결과 집합 가져오기
          prst.executeUpdate();

        } catch (ClassNotFoundException e) {
          e.printStackTrace();
          System.out.println("드라이버 로딩 실패!!");
        } catch (SQLException e) {
          System.out.println("에러 : " + e);
          System.out.println("연결 실패!!");
        } finally {
          // 리소스 해제
          if (prst != null) {
            try {
              prst.close();
            } catch (SQLException e) {
              e.printStackTrace();
            }
          }
          // 연결 닫기
          if (conn != null) {
            try {
              conn.close();
              System.out.println("Connection closed.");
            } catch (SQLException e) {
              e.printStackTrace();
            }
          }
        }

        System.out.printf("%d번 게시물이 수정되었습니다.\n", id);

      }
      else if(rq.getUrlPath().equals("exit")) {
        System.out.println("프로그램을 종료합니다.");
        break;
      }
      else if(rq.getUrlPath().equals("exit")) {
        System.out.println("프로그램을 종료합니다.");
        break;
      }
      else {
        System.out.println("잘못 된 명령어입니다.");
      }
    }

    sc.close();
  }
}
