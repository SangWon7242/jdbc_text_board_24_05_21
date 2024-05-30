package com.sbs.text.board.article;

import com.sbs.text.board.util.MysqlUtil;
import com.sbs.text.board.util.SecSql;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ArticleRepository {
  public int write(String title, String body, int memberId) {
    SecSql sql = new SecSql();
    sql.append("INSERT INTO article");
    sql.append("SET regDate = NOW()");
    sql.append(", updateDate = NOW()");
    sql.append(", memberId = ?", memberId);
    sql.append(", title = ?", title);
    sql.append(", `body` = ?", body);

    int id = MysqlUtil.insert(sql);

    return id;
  }

  public void update(int id, String title, String body) {
    SecSql sql = new SecSql();
    sql.append("UPDATE article");
    sql.append("SET title = ?", title);
    sql.append(", `body` = ?", body);
    sql.append("WHERE id = ?", id);

    MysqlUtil.update(sql);
  }

  public void delete(int id) {
    SecSql sql = new SecSql();
    sql.append("DELETE FROM article");
    sql.append("WHERE id = ?", id);

    MysqlUtil.delete(sql);
  }

  public List<Article> getArticles() {
    SecSql sql = new SecSql();
    sql.append("SELECT A.*, M.name AS extra__writerName");
    sql.append("FROM article AS A");
    sql.append("INNER JOIN `member` AS M");
    sql.append("ON A.memberId = M.id");
    sql.append("ORDER BY id DESC");

    List<Map<String, Object>> articlesListMap = MysqlUtil.selectRows(sql);

    List<Article> articles = new ArrayList<>();

    for (Map<String, Object> articleMap : articlesListMap) {
      articles.add(new Article(articleMap));
    }

    return articles;
  }

  public Article getArticleById(int id) {
    SecSql sql = new SecSql();
    sql.append("SELECT A.*, M.name AS extra__writerName");
    sql.append("FROM article AS A");
    sql.append("INNER JOIN `member` AS M");
    sql.append("ON A.memberId = M.id");
    sql.append("WHERE A.id = ?", id);

    Map<String, Object> articleMap = MysqlUtil.selectRow(sql);

    if(articleMap.isEmpty()) {
      return null;
    }

    return new Article(articleMap);
  }

  public void increaseHit(int id) {
    SecSql sql = new SecSql();

    sql.append("UPDATE article");
    sql.append("SET hit = hit + 1");
    sql.append("WHERE id = ?", id);

    MysqlUtil.update(sql);
  }
}
