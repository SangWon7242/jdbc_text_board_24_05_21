package com.sbs.text.board;

import com.sbs.text.board.container.Container;

import java.util.Scanner;

public class App {
  public void run() {
    Scanner sc = Container.scanner;

    while (true) {
      System.out.print("명령) ");
      String cmd = sc.nextLine();

      if(cmd.equals("/usr/article/list")) {
        System.out.println("== 게시물 리스트 ==");
      }
      else if(cmd.equals("exit")) {
        System.out.println("프로그램을 종료합니다.");
        break;
      }
    }

    sc.close();
  }
}
