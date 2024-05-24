package com.sbs.text.board.article;

import com.sbs.text.board.Rq;
import com.sbs.text.board.container.Container;
import com.sbs.text.board.util.MysqlUtil;
import com.sbs.text.board.util.SecSql;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ArticleController {
  public void write() {
    System.out.println("== 게시물 추가 ==");

    System.out.print("제목) ");
    String title = Container.scanner.nextLine();
    System.out.print("내용) ");
    String body = Container.scanner.nextLine();

    SecSql sql = new SecSql();
    sql.append("INSERT INTO article");
    sql.append("SET regDate = NOW()");
    sql.append(", updateDate = NOW()");
    sql.append(", title = ?", title);
    sql.append(", `body` = ?", body);

    int id = MysqlUtil.insert(sql);

    Article article = new Article(id, title, body);

    System.out.printf("%d번 게시물이 등록되었습니다.\n", article.getId());
  }

  public void showList() {
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
  }

  public void showDetail(Rq rq) {
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

    if (!articleIsExists) {
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

  }

  public void modify(Rq rq) {
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

    if (!articleIsExists) {
      System.out.printf("%d번 게시물은 존재하지 않습니다.\n", id);
      return;
    }

    System.out.print("새 제목 : ");
    String title = Container.scanner.nextLine();

    System.out.print("새 내용 : ");
    String body = Container.scanner.nextLine();

    sql = new SecSql();
    sql.append("UPDATE article");
    sql.append("SET title = ?", title);
    sql.append(", `body` = ?", body);
    sql.append("WHERE id = ?", id);

    MysqlUtil.update(sql);

    System.out.printf("%d번 게시물이 수정되었습니다.\n", id);
  }

  public void delete(Rq rq) {
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

    if (!articleIsExists) {
      System.out.printf("%d번 게시물은 존재하지 않습니다.\n", id);
      return;
    }

    sql = new SecSql();
    sql.append("DELETE FROM article");
    sql.append("WHERE id = ?", id);

    MysqlUtil.delete(sql);

    System.out.printf("%d번 게시물이 삭제되었습니다.\n", id);
  }
}
