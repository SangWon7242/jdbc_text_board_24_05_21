package com.sbs.text.board.article;

import com.sbs.text.board.Rq;
import com.sbs.text.board.container.Container;
import com.sbs.text.board.member.Member;

import java.util.List;

public class ArticleController {
  private ArticleService articleService;

  public ArticleController() {
    articleService = Container.articleService;
  }

  public void write() {
    if(!Container.session.isLogined()) {
      System.out.println("로그인 후 이용해주세요.");
      return;
    }

    Member member = Container.session.loginedMember;

    System.out.println("== 게시물 추가 ==");

    System.out.print("제목 : ");
    String title = Container.scanner.nextLine();
    System.out.print("내용 : ");
    String body = Container.scanner.nextLine();

    int id = articleService.write(title, body, member.getId());

    System.out.printf("%d번 게시물이 등록되었습니다.\n", id);
  }

  public void showList(Rq rq) {
    int page = rq.getIntParam("page", 1);
    String searchKeyword = rq.getParam("searchKeyword", "");
    int pageItemCount = 10;

    System.out.println("== 게시물 리스트 ==");

    List<Article> articles = articleService.getArticles(page, pageItemCount, searchKeyword);

    if (articles.isEmpty()) {
      System.out.println("게시물이 존재하지 않습니다.");
      return;
    }

    System.out.println("번호 / 제목 / 작성자명");

    for (Article article : articles) {
      System.out.printf("%d / %s / %s\n", article.getId(), article.getTitle(), article.getExtra__writerName());
    }
  }

  public void showDetail(Rq rq) {
    int id = rq.getIntParam("id", 0);

    if (id == 0) {
      System.out.println("id를 올바르게 입력해주세요.");
      return;
    }

    articleService.increaseHit(id);
    Article article = articleService.getArticleById(id);

    if (article == null) {
      System.out.printf("%d번 게시물은 존재하지 않습니다.\n", id);
      return;
    }

    System.out.println("== 게시물 상세보기 ==");
    System.out.printf("번호 : %d\n", article.getId());
    System.out.printf("작성날짜 : %s\n", article.getRegDate());
    System.out.printf("수정날짜 : %s\n", article.getUpdateDate());
    System.out.printf("제목 : %s\n", article.getTitle());
    System.out.printf("내용 : %s\n", article.getBody());
    System.out.printf("작성자 : %s\n", article.getExtra__writerName());
    System.out.printf("조회수 : %s\n", article.getHit());
  }

  public void modify(Rq rq) {
    int id = rq.getIntParam("id", 0);

    if (id == 0) {
      System.out.println("id를 올바르게 입력해주세요.");
      return;
    }

    if(!Container.session.isLogined()) {
      System.out.println("로그인 후 이용해주세요.");
      return;
    }

    System.out.println("== 게시물 수정 ==");

    Article article = articleService.getArticleById(id);

    if (article == null) {
      System.out.printf("%d번 게시물은 존재하지 않습니다.\n", id);
      return;
    }

    if(article.getMemberId() != Container.session.loginedMember.getId()) {
      System.out.println("권한이 없습니다.");
      return;
    }

    System.out.print("새 제목 : ");
    String title = Container.scanner.nextLine();

    System.out.print("새 내용 : ");
    String body = Container.scanner.nextLine();

    articleService.update(id, title, body);

    System.out.printf("%d번 게시물이 수정되었습니다.\n", id);
  }

  public void delete(Rq rq) {
    int id = rq.getIntParam("id", 0);

    if (id == 0) {
      System.out.println("id를 올바르게 입력해주세요.");
      return;
    }

    if(!Container.session.isLogined()) {
      System.out.println("로그인 후 이용해주세요.");
      return;
    }

    Article article = articleService.getArticleById(id);

    if (article == null) {
      System.out.printf("%d번 게시물은 존재하지 않습니다.\n", id);
      return;
    }

    if(article.getMemberId() != Container.session.loginedMember.getId()) {
      System.out.println("권한이 없습니다.");
      return;
    }

    articleService.delete(id);

    System.out.printf("%d번 게시물이 삭제되었습니다.\n", id);
  }
}
