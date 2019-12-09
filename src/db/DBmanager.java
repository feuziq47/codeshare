package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBmanager {
	private final String JDBC_DRIVER="com.mysql.cj.jdbc.Driver";
	private final String DB_URL="jdbc:mysql://localhost:3306/netpro?serverTimezone=UTC";
	private final String USER_NAME="java";
	private final String PW="1234";
	
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	private static DBmanager dao;
	public static DBmanager getInstance() {
		if(dao==null) {
			dao = new DBmanager();
		}
		return dao;
	}
	
	public DBmanager() {
		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, USER_NAME, PW);
			System.out.println("MySQL connected!");
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	public boolean signUp(String id, String pw) {			//계정생성
		boolean result=false;
		try {
			String sql="INSERT INTO user Values(?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(2, id);
			pstmt.setString(3, pw);
			synchronized(this) {
				int k=pstmt.executeUpdate();
				if(k>0) {
					result=true;
				}
			}
			pstmt.close();
			System.out.println("회원가입완료");
			conn.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public String signIn(String id, String pw) {				//로그인
		
		try {
			String sql="SELECT * FROM user WHERE ID=?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1,id);
			synchronized(this) {
				rs=pstmt.executeQuery();
			}
			while(rs.next()==true) {
				if(rs.getString(2).equals(id) && rs.getString(3).equals(pw)) {
					return "success";
				}else {
					return "fail";
				}
			}
		}catch(Exception e) {e.printStackTrace();}
		
		return "fail";
	}
	
	public void close() {
		try {
				this.pstmt.close();

				this.conn.close();

			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
