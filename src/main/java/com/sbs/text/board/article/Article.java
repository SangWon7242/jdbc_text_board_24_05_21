package com.sbs.text.board.article;

import lombok.*;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Article {
  private int id;
  private String regDate;
  private String updateDate;
  private String title;
  private String body;

  public Article(int id, String title, String body) {
    this.id = id;
    this.title = title;
    this.body = body;
  }

  public Article(Map<String, Object> articleMap) {
    this.id = (int) articleMap.get("id");
    this.regDate = (String) articleMap.get("regDate");
    this.updateDate = (String) articleMap.get("updateDate");
    this.title = (String) articleMap.get("title");
    this.body = (String) articleMap.get("body");
  }
}
