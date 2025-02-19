import java.sql.*;

public class Database {
    private static final String url = "jdbc:mysql://localhost:3306/library";
    private static final String user = "root";

    private static final String password = System.getenv("password");

    public static Connection getConnection() throws SQLException {
        Connection conn = DriverManager.getConnection(url,user,password);
        return  conn;
    }

}
