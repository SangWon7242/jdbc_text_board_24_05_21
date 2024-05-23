import com.sbs.text.board.Article;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JDBCSelectTest {
  public static void main(String[] args) {
    // 데이터베이스 URL, 사용자 이름, 비밀번호를 설정합니다.
    String url = "jdbc:mysql://127.0.0.1:3306/text_board?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull"; // 데이터베이스 URL
    String user = "sbsst"; // 데이터베이스 사용자 이름
    String password = "sbs123414"; // 데이터베이스 비밀번호

    // Connection 객체 선언
    Connection conn = null;
    PreparedStatement prst = null;
    ResultSet rs = null;

    List<Article> articles = new ArrayList<>();

    try {
      // 드라이버 로드 (필요에 따라 생략 가능, 최신 드라이버는 자동 로드)
      Class.forName("com.mysql.cj.jdbc.Driver");

      // 데이터베이스 연결
      conn = DriverManager.getConnection(url, user, password);

      // SQL 조회
      String sql = "SELECT *";
      sql += " FROM article";
      sql += " ORDER BY id DESC";

      System.out.println("sql : " + sql);
      prst = conn.prepareStatement(sql);

      // 명령 실행 및 결과 집합 가져오기
      rs = prst.executeQuery();

      while (rs.next()) {
        int id = rs.getInt("id");
        String regDate = rs.getString("regDate");
        String updateDate = rs.getString("updateDate");
        String title = rs.getString("title");
        String body = rs.getString("body");

        Article article = new Article(id, regDate, updateDate, title, body);
        articles.add(article);
      }

      System.out.println("결과 : " + articles);

    } catch (ClassNotFoundException e) {
      e.printStackTrace();
      System.out.println("드라이버 로딩 실패!!");
    } catch (SQLException e) {
      System.out.println("에러 : " + e);
      System.out.println("연결 실패!!");
    } finally {
      // 리소스 해제
      if (rs != null) {
        try {
          rs.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
      // 리소스 해제
      if (prst != null) {
        try {
          prst.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
      // 연결 닫기
      if (conn != null) {
        try {
          conn.close();
          System.out.println("Connection closed.");
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
    }
  }
}
