package com.sbs.text.board.article;

import com.sbs.text.board.container.Container;

import java.util.List;

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

  public List<Article> getArticles() {
    return articleRepository.getArticles();
  }

  public Article getArticleById(int id) {
    return articleRepository.getArticleById(id);
  }

  public void increaseHit(int id) {
    articleRepository.increaseHit(id);
  }
}
