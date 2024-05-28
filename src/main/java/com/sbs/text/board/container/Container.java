package com.sbs.text.board.container;

import com.sbs.text.board.article.ArticleController;
import com.sbs.text.board.article.ArticleRepository;
import com.sbs.text.board.article.ArticleService;
import com.sbs.text.board.member.MemberController;
import com.sbs.text.board.member.MemberRepository;
import com.sbs.text.board.member.MemberService;
import com.sbs.text.board.session.Session;

import java.util.Scanner;

public class Container {
  public static Scanner scanner;
  public static Session session;

  public static MemberRepository memberRepository;
  public static ArticleRepository articleRepository;

  public static MemberService memberService;
  public static ArticleService articleService;

  public static MemberController memberController;
  public static ArticleController articleController;

  static {
    scanner = new Scanner(System.in);
    session = new Session();

    memberRepository = new MemberRepository();
    articleRepository = new ArticleRepository();

    memberService = new MemberService();
    articleService = new ArticleService();

    memberController = new MemberController();
    articleController = new ArticleController();
  }
}
