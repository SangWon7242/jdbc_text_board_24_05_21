package com.sbs.text.board.session;

import com.sbs.text.board.member.Member;

public class Session {
  public Member loginedMember;

  public void login(Member member) {
    loginedMember = member;
  }

  public boolean isLogined() {
    return loginedMember != null;
  }

  public boolean isLogout() {
    return !isLogined();
  }
}
