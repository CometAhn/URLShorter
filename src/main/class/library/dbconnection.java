/**
 * 데이터베이스 연결
 */

package library;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class dbconnection {

	public static Connection getConnection() throws SQLException, ClassNotFoundException {

		Connection conn = null;

		String url = "jdbc:mysql://localhost:3306/librarydb";
		String user = "root";
		String password = "0000";

		Class.forName("com.mysql.jdbc.Driver");
		// 뭐여 이게
		// Class.forName("com.mysql.cj.jdbc.Driver");

		conn = DriverManager.getConnection(url, user, password);

		return conn;
	}
}
