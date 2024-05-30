package com.sbs.text.board.member;

import com.sbs.text.board.container.Container;

public class MemberController {

  private MemberService memberService;

  public MemberController() {
    memberService = Container.memberService;
  }

  public void join() {
    String loginId;
    String loginPw;
    String loginPwConfirm;
    String name;

    System.out.println("== 회원 가입 ==");

    // 로그인 아이디 입력
    while (true) {
      System.out.print("로그인 아이디 : ");
      loginId = Container.scanner.nextLine();

      if(loginId.trim().isEmpty()) {
        System.out.println("로그인 아이디를 입력해주세요.");
        continue;
      }

      boolean isLoginIdDup = memberService.isLoginIdDup(loginId);

      if(isLoginIdDup) {
        System.out.printf("\"%s\"(은)는 이미 사용중인 로그인 아이디입니다.\n", loginId);
        continue;
      }

      break;
    }

    // 로그인 비밀번호 입력
    while (true) {
      System.out.print("로그인 비밀번호 : ");
      loginPw = Container.scanner.nextLine();

      if(loginPw.trim().isEmpty()) {
        System.out.println("로그인 비밀번호를 입력해주세요.");
        continue;
      }

      boolean loginPwConfirmIsSame = true;

      while (true) {
        System.out.print("로그인 비밀번호 확인 : ");
        loginPwConfirm = Container.scanner.nextLine();

        if(!loginPw.equals(loginPwConfirm)) {
          System.out.println("로그인 비밀번호 확인이 일치하지 않습니다.");

          loginPwConfirmIsSame = false;
          continue;
        }

        break;
      }
      if(loginPwConfirmIsSame) {
        break;
      }
    }

    // 로그인 아이디 입력
    while (true) {
      System.out.print("이름 : ");
      name = Container.scanner.nextLine();

      if(name.trim().isEmpty()) {
        System.out.println("이름을 입력해주세요.");
        continue;
      }

      break;
    }

    memberService.join(loginId, loginPw, name);

    System.out.printf("\"%s\"님 회원 가입 되었습니다.\n", name);
  }

  public void login() {
    String loginId;
    String loginPw;
    Member member;

    if(Container.session.loginedMember != null) {
      System.out.println("로그아웃 후 이용해주세요.");
      return;
    }

    System.out.println("== 로그인 ==");

    // 로그인 아이디 입력
    while (true) {
      System.out.print("로그인 아이디 : ");
      loginId = Container.scanner.nextLine();

      if(loginId.trim().isEmpty()) {
        System.out.println("로그인 아이디를 입력해주세요.");
        continue;
      }

      member = memberService.getMemberByLoginId(loginId);

      if(member == null) {
        System.out.printf("\"%s\"(은)는 존재하지 않는 로그인 아이디입니다.\n", loginId);
        continue;
      }

      break;
    }

    int loginPwTryMaxCount = 3;
    int loginPwTryCount = 0;

    // 로그인 비밀번호 입력
    while (true) {
      if(loginPwTryMaxCount == loginPwTryCount) {
        System.out.println("비밀번호를 확인 후 다시 로그인 해주세요.");
        return;
      }

      System.out.print("로그인 비밀번호 : ");
      loginPw = Container.scanner.nextLine();

      if(loginPw.trim().isEmpty()) {
        System.out.println("로그인 비밀번호를 입력해주세요.");
        continue;
      }

      if(!member.getLoginPw().equals(loginPw)) {
        System.out.println("로그인 비밀번호가 일치하지 않습니다.");
        loginPwTryCount++;

        System.out.printf("비밀번호 틀린 횟수(%d / %d)\n", loginPwTryCount, loginPwTryMaxCount);
        continue;
      }

      break;
    }

    System.out.printf("\"%s\"님 로그인 되었습니다.\n", member.getLoginId());
    Container.session.login(member);
  }

  public void logout() {
    if(Container.session.isLogout()) {
      System.out.println("로그인 후 이용해주세요.");
      return;
    }

    Container.session.loginedMember = null;
    System.out.println("로그아웃 되었습니다.");
  }

  public void whoami() {
    String loginId;

    if(Container.session.loginedMember == null) {
      System.out.println("로그인 후 이용해주세요.");
    } else {
      loginId = Container.session.loginedMember.getLoginId();
      System.out.printf("현재 로그인 한 회원은 \"%s\" 입니다.\n", loginId);
    }
  }

  public void changePw() {
    String loginId;
    String email;
    String newLoginPw;
    String newLoginPwConfirm;
    Member member;

    if (Container.session.isLogined()) {
      System.out.println("현재 로그인 상태입니다.");
      return;
    }

    System.out.println("== 비밀번호 변경 ==");

    // 가입 아이디 입력
    while (true) {
      System.out.print("가입 아이디 : ");
      loginId = Container.scanner.nextLine();

      if(loginId.trim().isEmpty()) {
        System.out.println("아이디를 입력해주세요.");
        continue;
      }

      member = memberService.getMemberByLoginId(loginId);

      if(member == null) {
        System.out.println("입력하신 아이디는 존재하지 않는 아이디입니다.");
        continue;
      }

      // 가입 이메일 입력
      while (true) {
        System.out.print("가입 이메일 : ");
        email = Container.scanner.nextLine();

        if(email.trim().isEmpty()) {
          System.out.println("이메일을 입력해주세요.");
          continue;
        }

        if(!member.getEmail().equals(email)) {
          System.out.println("이메일이 일치하지 않습니다.");
          continue;
        }

        break;
      }

      break;
    }

    // 새 비밀번호 입력
    while (true) {
      System.out.print("새 비밀번호 : ");
      newLoginPw = Container.scanner.nextLine();

      if(newLoginPw.trim().isEmpty()) {
        System.out.println("새 비밀번호를 입력해주세요.");
        continue;
      }

      while (true) {
        System.out.print("새 비밀번호 확인 : ");
        newLoginPwConfirm = Container.scanner.nextLine();

        if(!newLoginPwConfirm.equals(newLoginPw)) {
          System.out.println("비밀번호가 일치하지 않습니다.");
          continue;
        }

        break;
      }

      break;
    }

    memberService.changeLoginPw(loginId, email, newLoginPw);

    System.out.println("비밀번호가 변경되었습니다.");
  }
}
