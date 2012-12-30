package zqc.wetalk.server.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import zqc.wetalk.LogonInfo;
import zqc.wetalk.RegisterInfo;
import zqc.wetalk.UserInfo;
import zqc.wetalk.exceptions.ApplicationException;
import zqc.wetalk.server.net.Client;

/**
 * 代表用户对象，可以向数据库执行抛入和更新等操作。
 * 
 * @author User
 * 
 */
public class UserGateway {

	private Integer id;
	private String userName;
	private String password;
	private String nickName;
	private Client client;

	public UserGateway() {
	}

	public UserGateway(String userName, String password, String nickName) {
		this(-1, userName, password, nickName);
	}

	public UserGateway(Integer id, String userName, String password, String nickName) {
		this.id = id;
		this.userName = userName;
		this.password = password;
		this.nickName = nickName;
	}

	public UserGateway(RegisterInfo userInfo) {
		this(-1, userInfo.getUserName(), userInfo.getPassword(), userInfo
				.getNickName());
	}

	public Integer getID() {
		return id;
	}

	public void setID(Integer id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	private static final String insertUserStatement = "INSERT INTO UserTable(UserName, Password, NickName) VALUES(?, ?, ?)";

	public int insert() {

		DataAccess db = new DataAccess();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = db.prepareReturnKeys(insertUserStatement);
			stmt.setString(1, userName);
			stmt.setString(2, password);
			stmt.setString(3, nickName);
			stmt.executeUpdate();
			rs = stmt.getGeneratedKeys();
			rs.next();
			setID(rs.getInt(1));
			Registry.getInstance().addUser(this);
			return getID();
		} catch (SQLException ex) {
			throw new ApplicationException(ex);
		} finally {
			db.cleanUp(stmt);
		}

	}

	public static UserGateway load(ResultSet rs) throws SQLException {

		UserGateway result = null;
		int id = rs.getInt(1);
		result = Registry.getInstance().getUser(id);
		if (result != null)
			return result;

		String userName = rs.getString(2);
		String password = rs.getString(3);
		String nickName = rs.getString(4);

		result = new UserGateway(id, userName, password, nickName);
		Registry.getInstance().addUser(result);
		return result;
	}

	public UserInfo toUserInfo() {
		return new UserInfo(id, userName, nickName);
	}
	
	public Client getClient(){
		return client;
	}

	public void setClient(Client client) {
		this.client = client; 
		
	}
}
