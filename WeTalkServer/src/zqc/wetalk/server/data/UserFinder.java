package zqc.wetalk.server.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import zqc.wetalk.exceptions.ApplicationException;

public class UserFinder {

	private static final String selectUserClause = "SELECT id, username, password, nickname FROM UserTable ";
	private static final String whereIdClause = " WHERE id = ? ";
	private static final String whereUserNameClause = " WHERE userName = ? ";

	public UserGateway find(int id) {
		
		UserGateway result = Registry.getInstance().getUser(id);
		
		if (result != null)
			return result;
		
		PreparedStatement findStmt = null;
		ResultSet rs = null;
		DataAccess db = new DataAccess();
		try {
			findStmt = db.prepare(selectUserClause + whereIdClause);
			findStmt.setInt(1, id);
			rs = findStmt.executeQuery();
			if (rs.next())
				result = UserGateway.load(rs);

			return result;
			
		} catch (SQLException ex) {
			throw new ApplicationException(ex);
		} finally {
			db.cleanUp(findStmt, rs);
		}

	}

	public UserGateway findByUserName(String userName) {
		UserGateway result = null;
		
		DataAccess db = new DataAccess();
		PreparedStatement findStmt = null;
		ResultSet rs = null;

		try {
			findStmt = db.prepare(selectUserClause + whereUserNameClause);
			findStmt.setString(1, userName);
			rs = findStmt.executeQuery();
			if (rs.next())
				result = UserGateway.load(rs);
			
			return result;
		} catch (SQLException ex) {
			throw new ApplicationException(ex);
		} finally {
			db.cleanUp(findStmt, rs);
		}
	}

}
