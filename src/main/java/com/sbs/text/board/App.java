package com.sbs.text.board;

import com.sbs.text.board.article.Article;
import com.sbs.text.board.article.ArticleController;
import com.sbs.text.board.container.Container;
import com.sbs.text.board.member.MemberController;
import com.sbs.text.board.util.MysqlUtil;
import com.sbs.text.board.util.SecSql;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class App {

  private static boolean isDevMode() {
    // 이 부분을 false로 바꾸면 production 모드 이다.
    // true는 개발자 모드이다.(개발할 때 좋다.)
    return true;
  }

  public void run() {
    Scanner sc = Container.scanner;

    try {
      while (true) {
        System.out.print("명령) ");
        String cmd = sc.nextLine();

        Rq rq = new Rq(cmd);

        // DB 세팅
        MysqlUtil.setDBInfo("localhost", "sbsst", "sbs123414", "text_board");
        MysqlUtil.setDevMode(isDevMode());

        action(sc, rq);
      }
    } finally {
      sc.close();
    }
  }

  private void action(Scanner sc, Rq rq) {
    MemberController memberController = Container.memberController;
    ArticleController articleController = Container.articleController;


    if (rq.getUrlPath().equals("/usr/article/write")) {
      System.out.println("== 게시물 추가 ==");

      System.out.print("제목) ");
      String title = sc.nextLine();
      System.out.print("내용) ");
      String body = sc.nextLine();

      SecSql sql = new SecSql();
      sql.append("INSERT INTO article");
      sql.append("SET regDate = NOW()");
      sql.append(", updateDate = NOW()");
      sql.append(", title = ?", title);
      sql.append(", `body` = ?", body);

      int id = MysqlUtil.insert(sql);

      Article article = new Article(id, title, body);

      System.out.printf("%d번 게시물이 등록되었습니다.\n", article.getId());
    } else if (rq.getUrlPath().equals("/usr/article/list")) {
      List<Article> articles = new ArrayList<>();

      System.out.println("== 게시물 리스트 ==");

      SecSql sql = new SecSql();
      sql.append("SELECT *");
      sql.append("FROM article");
      sql.append("ORDER BY id DESC");

      List<Map<String, Object>> articlesListMap = MysqlUtil.selectRows(sql);

      for (Map<String, Object> articleMap : articlesListMap) {
        articles.add(new Article(articleMap));
      }

      if (articles.isEmpty()) {
        System.out.println("게시물이 존재하지 않습니다.");
        return;
      }

      for (Article article : articles) {
        System.out.printf("%d / %s\n", article.getId(), article.getTitle());
      }

    } else if (rq.getUrlPath().equals("/usr/article/detail")) {
      int id = rq.getIntParam("id", 0);

      if (id == 0) {
        System.out.println("id를 올바르게 입력해주세요.");
        return;
      }

      SecSql sql = new SecSql();
      sql.append("SELECT COUNT(*) AS cnt");
      sql.append("FROM article");
      sql.append("WHERE id = ?", id);

      boolean articleIsExists = MysqlUtil.selectRowBooleanValue(sql);

      if(!articleIsExists) {
        System.out.printf("%d번 게시물은 존재하지 않습니다.\n", id);
        return;
      }

      sql = new SecSql();
      sql.append("SELECT *");
      sql.append("FROM article");
      sql.append("WHERE id = ?", id);

      Map<String, Object> articleMap = MysqlUtil.selectRow(sql);

      Article article = new Article(articleMap);

      System.out.println("== 게시물 상세보기 ==");
      System.out.printf("번호 : %d\n", article.getId());
      System.out.printf("작성날짜 : %s\n", article.getRegDate());
      System.out.printf("수정날짜 : %s\n", article.getUpdateDate());
      System.out.printf("제목 : %s\n", article.getTitle());
      System.out.printf("내용 : %s\n", article.getBody());


    } else if (rq.getUrlPath().equals("/usr/article/modify")) {
      System.out.println("== 게시물 수정 ==");

      int id = rq.getIntParam("id", 0);

      if (id == 0) {
        System.out.println("id를 올바르게 입력해주세요.");
        return;
      }

      SecSql sql = new SecSql();
      sql.append("SELECT COUNT(*) > 0");
      sql.append("FROM article");
      sql.append("WHERE id = ?", id);

      boolean articleIsExists = MysqlUtil.selectRowBooleanValue(sql);

      if(!articleIsExists) {
        System.out.printf("%d번 게시물은 존재하지 않습니다.\n", id);
        return;
      }

      System.out.print("새 제목 : ");
      String title = sc.nextLine();

      System.out.print("새 내용 : ");
      String body = sc.nextLine();

      sql = new SecSql();
      sql.append("UPDATE article");
      sql.append("SET title = ?", title);
      sql.append(", `body` = ?", body);
      sql.append("WHERE id = ?", id);

      MysqlUtil.update(sql);

      System.out.printf("%d번 게시물이 수정되었습니다.\n", id);

    } else if (rq.getUrlPath().equals("/usr/article/delete")) {
      int id = rq.getIntParam("id", 0);

      if (id == 0) {
        System.out.println("id를 올바르게 입력해주세요.");
        return;
      }

      SecSql sql = new SecSql();
      sql.append("SELECT COUNT(*) AS cnt");
      sql.append("FROM article");
      sql.append("WHERE id = ?", id);

      boolean articleIsExists = MysqlUtil.selectRowBooleanValue(sql);

      if(!articleIsExists) {
        System.out.printf("%d번 게시물은 존재하지 않습니다.\n", id);
        return;
      }

      sql = new SecSql();
      sql.append("DELETE FROM article");
      sql.append("WHERE id = ?", id);

      MysqlUtil.delete(sql);

      System.out.printf("%d번 게시물이 삭제되었습니다.\n", id);
    } else if (rq.getUrlPath().equals("/usr/member/join")) {
      memberController.join();
    } else if (rq.getUrlPath().equals("exit")) {
      System.out.println("프로그램을 종료합니다.");
      System.exit(0);
    } else {
      System.out.println("잘못 된 명령어입니다.");
    }
  }
}
