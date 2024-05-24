package com.sbs.text.board.member;

import com.sbs.text.board.container.Container;
import com.sbs.text.board.util.MysqlUtil;
import com.sbs.text.board.util.SecSql;

public class MemberController {
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

      SecSql sql = new SecSql();
      sql.append("SELECT COUNT(*) > 0");
      sql.append("FROM `member`");
      sql.append("WHERE loginId = ?", loginId);

      boolean isLoginIdDup = MysqlUtil.selectRowBooleanValue(sql);

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

    SecSql sql = new SecSql();
    sql.append("INSERT INTO `member`");
    sql.append("SET regDate = NOW()");
    sql.append(", updateDate = NOW()");
    sql.append(", loginId = ?", loginId);
    sql.append(", loginPw = ?", loginPw);
    sql.append(", name = ?", name);

    MysqlUtil.insert(sql);

    System.out.printf("\"%s\"님 회원 가입 되었습니다.\n", name);
  }
}
