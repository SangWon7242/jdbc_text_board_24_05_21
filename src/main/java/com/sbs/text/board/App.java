package com.sbs.text.board;

import com.sbs.text.board.article.ArticleController;
import com.sbs.text.board.container.Container;
import com.sbs.text.board.member.MemberController;
import com.sbs.text.board.util.MysqlUtil;

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

        action(rq);
      }
    } finally {
      sc.close();
    }
  }

  private void action(Rq rq) {
    MemberController memberController = Container.memberController;
    ArticleController articleController = Container.articleController;

    if (rq.getUrlPath().equals("/usr/article/write")) {
      articleController.write();
    } else if (rq.getUrlPath().equals("/usr/article/list")) {
      articleController.showList();
    } else if (rq.getUrlPath().equals("/usr/article/detail")) {
      articleController.showDetail(rq);
    } else if (rq.getUrlPath().equals("/usr/article/modify")) {
      articleController.modify(rq);
    } else if (rq.getUrlPath().equals("/usr/article/delete")) {
      articleController.delete(rq);
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
