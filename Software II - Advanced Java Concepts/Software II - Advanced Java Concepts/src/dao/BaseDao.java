package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/**
 * The class Base dao
 */
public class BaseDao {
    private final String url = "jdbc:mysql://localhost:3306/client_schedule?connectionTimeZone=SERVER";
    protected Connection conn;


    /**
     *
     * It is a constructor.
     *
     */
    public BaseDao() {
        try {
            conn = DriverManager.getConnection(url,"sqlUser", "Passw0rd!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    @SuppressWarnings("deprecation")
    protected void finalize() {
        closeConnection();
    }


    /**
     *
     * Close connection
     *
     */
    public void closeConnection() {

        try {
            if(conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
