package zqc.reading.poeaa.ch10datasourcepatterns;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import zqc.reading.poeaa.ApplicationException;

public class DB {

    private static final String driver   = "com.mysql.jdbc.Driver";
    private static final String url      = "jdbc:mysql://localhost:3306/test";
    private static final String user     = "qczhang";
    private static final String password = "";

    static {
        try {
            Class.forName(driver);
        }
        catch (ClassNotFoundException ex) {
            // e.printStackTrace();
            throw new ApplicationException(ex);
        }
    }

    private static Connection createConnection() throws SQLException {

        return DriverManager.getConnection(url, user, password);
    }

    public static PreparedStatement prepare(String sql) throws SQLException {

        return createConnection().prepareStatement(sql);
    }
    
    public static PreparedStatement prepare(String sql, String... columnNames) throws SQLException {
        return createConnection().prepareStatement(sql, columnNames);
    }

    public static void cleanUp(PreparedStatement statement) {

        try {
            Connection conn = statement.getConnection();
            statement.close();
            conn.close();
        }
        catch (SQLException ex) {
            throw new ApplicationException(ex);
        }
    }

    public static void cleanUp(PreparedStatement statement, ResultSet rs) {
        try{
        rs.close();
        statement.close();
        statement.getConnection().close();
        }catch(SQLException ex){
            throw new ApplicationException(ex);
        }
    }

}
