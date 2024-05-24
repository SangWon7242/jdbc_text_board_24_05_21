package com.sbs.text.board.member;

import java.util.Map;

public class Member {
  private int id;
  private String regDate;
  private String updateDate;
  private String loginId;
  private String loginPw;
  private String name;


  public Member(Map<String, Object> memberMap) {
    this.id = (int) memberMap.get("id");
    this.regDate = (String) memberMap.get("regDate");
    this.updateDate = (String) memberMap.get("updateDate");
    this.loginId = (String) memberMap.get("loginId");
    this.loginPw = (String) memberMap.get("loginPw");
    this.name = (String) memberMap.get("name");
  }

  @Override
  public String toString() {
    return "Member{" +
        "id=" + id +
        ", regDate='" + regDate + '\'' +
        ", updateDate='" + updateDate + '\'' +
        ", loginId='" + loginId + '\'' +
        ", loginPw='" + loginPw + '\'' +
        ", name='" + name + '\'' +
        '}';
  }
}