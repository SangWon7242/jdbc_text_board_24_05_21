package com.sbs.text.board.article;

import com.sbs.text.board.container.Container;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArticleService {
  private ArticleRepository articleRepository;

  public ArticleService() {
    articleRepository = Container.articleRepository;
  }
  public int write(String title, String body, int memberId) {
    return articleRepository.write(title, body, memberId);
  }

  public void update(int id, String title, String body) {
    articleRepository.update(id, title, body);
  }

  public void delete(int id) {
    articleRepository.delete(id);
  }

  public List<Article> getArticles(int page, int pageItemCount, String searchKeyword) {
    int limitFrom = (page - 1) * pageItemCount;
    int limitTake = pageItemCount;

    Map<String, Object> args = new HashMap<>();
    args.put("searchKeyword", searchKeyword);
    args.put("limitFrom", limitFrom);
    args.put("limitTake", limitTake);


    return articleRepository.getArticles(args);
  }

  public Article getArticleById(int id) {
    return articleRepository.getArticleById(id);
  }

  public void increaseHit(int id) {
    articleRepository.increaseHit(id);
  }
}
