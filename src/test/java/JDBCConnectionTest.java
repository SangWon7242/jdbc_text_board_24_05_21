import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCConnectionTest {
  public static void main(String[] args) {
    // 데이터베이스 URL, 사용자 이름, 비밀번호를 설정합니다.
    String url = "jdbc:mysql://127.0.0.1:3306/text_board?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull"; // 데이터베이스 URL
    String user = "sbsst"; // 데이터베이스 사용자 이름
    String password = "sbs123414"; // 데이터베이스 비밀번호

    // Connection 객체 선언
    Connection conn = null;

    try {
      // 드라이버 로드 (필요에 따라 생략 가능, 최신 드라이버는 자동 로드)
      Class.forName("com.mysql.cj.jdbc.Driver");

      // 데이터베이스 연결
      conn = DriverManager.getConnection(url, user, password);

      if (conn != null) {
        System.out.println("연결 성공!!");
      } else {
        System.out.println("연결 실패!!");
      }
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
