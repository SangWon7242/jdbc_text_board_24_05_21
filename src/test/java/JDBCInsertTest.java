import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JDBCInsertTest {
  public static void main(String[] args) {
    // 데이터베이스 URL, 사용자 이름, 비밀번호를 설정합니다.
    String url = "jdbc:mysql://127.0.0.1:3306/text_board?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull"; // 데이터베이스 URL
    String user = "sbsst"; // 데이터베이스 사용자 이름
    String password = "sbs123414"; // 데이터베이스 비밀번호

    // Connection 객체 선언
    Connection conn = null;
    PreparedStatement prst = null;

    try {
      // 드라이버 로드 (필요에 따라 생략 가능, 최신 드라이버는 자동 로드)
      Class.forName("com.mysql.cj.jdbc.Driver");

      // 데이터베이스 연결
      conn = DriverManager.getConnection(url, user, password);

      // SQL 삽입 명령 준비
      String sql = "INSERT INTO article";
      sql += " SET regDate = NOW()";
      sql += ", updateDate = NOW()";
      sql += ", title = CONCAT('제목', RAND())";
      sql += ", `body` = CONCAT('내용', RAND());";

      System.out.println("sql : " + sql);
      prst = conn.prepareStatement(sql);

      // 명령 실행
      int affetedRows = prst.executeUpdate();
      System.out.println("affetedRows : " + affetedRows);

    } catch (ClassNotFoundException e) {
      e.printStackTrace();
      System.out.println("JDBC Driver not found.");
    } catch (SQLException e) {
      e.printStackTrace();
      System.out.println("연결 실패!!");
    } finally {
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
