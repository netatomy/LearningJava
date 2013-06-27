package zqc.wechat.server.data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import zqc.wetalk.LogonInfo;
import zqc.wetalk.UserInfo;
import zqc.wetalk.exceptions.ApplicationException;

public class UserFinder {

	private static final String selectUserClause = "SELECT id, username, nickname FROM UserTable ";
	private static final String whereIdClause = " WHERE id = ? ";
	private static final String whereUserNameClause = " WHERE userName = ? ";
	private static final String whereUserNamePasswordClass = " WHERE userName = ? AND password = ?";
	private static final Registry registry = Registry.getInstance();
	
	public UserInfo find(int id) {
		
		UserInfo result = Registry.getInstance().getUser(id);
		
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
				result = load(rs);

			return result;
			
		} catch (SQLException ex) {
			throw new ApplicationException(ex);
		} finally {
			db.cleanUp(findStmt, rs);
		}

	}

	private UserInfo load(ResultSet rs) throws SQLException {

			UserInfo result = null;
			int id = rs.getInt("ID");
			result = Registry.getInstance().getUser(id);
			if (result != null)
				return result;

			String userName = rs.getString("UserName");
			String nickName = rs.getString("NickName");

			result = new UserInfo(id, userName, nickName);
			registry.addUser(result);
			return result;
	}

	public UserInfo findByUserName(String userName) {
		UserInfo result = null;
		
		DataAccess db = new DataAccess();
		PreparedStatement findStmt = null;
		ResultSet rs = null;

		try {
			findStmt = db.prepare(selectUserClause + whereUserNameClause);
			findStmt.setString(1, userName);
			rs = findStmt.executeQuery();
			if (rs.next())
				result = load(rs);
			
			return result;
		} catch (SQLException ex) {
			throw new ApplicationException(ex);
		} finally {
			db.cleanUp(findStmt, rs);
		}
	}

	public boolean checkLogon(LogonInfo logonInfo) {
		DataAccess db = new DataAccess();
		PreparedStatement findStmt = null;
		ResultSet rs = null;
		try {
			
			findStmt = db.prepare(selectUserClause + whereUserNamePasswordClass);
			findStmt.setString(1, logonInfo.getUserName());
			findStmt.setString(2, logonInfo.getPassword());
			rs = findStmt.executeQuery();
			UserInfo user = null;
			if (rs.next())
				user = load(rs);
			if (user == null)
				return false;
			return true;
		} catch (SQLException ex) {
			throw new ApplicationException(ex);
		} finally {
			db.cleanUp(findStmt, rs);
		}
	}

}
