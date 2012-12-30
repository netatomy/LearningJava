package zqc.wetalk.server.data;

import java.io.FileInputStream;
import java.io.IOError;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import zqc.wetalk.LogonInfo;
import zqc.wetalk.RegisterInfo;
import zqc.wetalk.exceptions.ApplicationException;

public class DataAccess {

	private static String driver = "org.apache.derby.jdbc.ClientDriver";// "org.apache.derby.jdbc.EmbeddedDriver";
	private static String url = "jdbc:derby://localhost/wetalk"; // E:\\zqc\\DB\\wetalkdb
	private static String user = "zqc";
	private static String password = "zqc";
	private static DataAccess instance;

	private boolean dbInfoLoaded = false;
	
	static{
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException ex) {
			throw new ApplicationException(ex);
		}
	}

	DataAccess() {
		
	}

	private Connection getConnection() throws SQLException {

		if (!dbInfoLoaded)
			loadDbInfo();
		return DriverManager.getConnection(url, user, password);
	}

	private void loadDbInfo() {
		Properties dbProps = new Properties();
		try {
			FileInputStream dbInfoFile = new FileInputStream("database.ini");
			try {
				dbProps.load(dbInfoFile);
				driver = dbProps.getProperty("jdbc.driver");
				url = dbProps.getProperty("jdbc.url");
				user = dbProps.getProperty("jdbc.user");
				password = dbProps.getProperty("jdbc.password");
			} finally {
				dbInfoFile.close();
			}
		} catch (IOException ex) {
			throw new ApplicationException(ex);
		}

	}

	public PreparedStatement prepare(String sql) throws SQLException {
		return getConnection().prepareStatement(sql);
	}

	public PreparedStatement prepareReturnKeys(String sql) throws SQLException {
		return getConnection().prepareStatement(sql,
				Statement.RETURN_GENERATED_KEYS);
	}

	public void cleanUp(PreparedStatement stmt) {
		try {
			Connection conn = stmt.getConnection();
			stmt.close();
			conn.close();
		} catch (SQLException ex) {
			throw new ApplicationException(ex);
		}
	}

	public void cleanUp(PreparedStatement stmt, ResultSet rs) {
		try {
			Connection conn = stmt.getConnection();
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException ex) {
			throw new ApplicationException(ex);
		}
	}
}
