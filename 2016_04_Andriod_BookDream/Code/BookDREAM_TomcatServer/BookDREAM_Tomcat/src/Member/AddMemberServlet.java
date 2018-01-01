package Member;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/*
 	안드로이드 어플리케이션에서 회원가입한 데이터를 받아서
 	회원 DB에 데이터를 추가하는 서블릿(회원 정보 추가 역할)
 	
 */
@WebServlet("/addMemberInfo")
public class  AddMemberServlet  extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException
	{
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException
	{ 
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8"); 
		System.out.println("회원 정보 추가 서블릿 동작");	
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String tableName = "member_table";
		try {
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());
			conn = DriverManager.getConnection(
					"jdbc:mysql://localhost/member_db", //JDBC URL
					"root",	// DBMS 사용자 아이디
					"1234");	// DBMS 사용자 암호
			stmt = conn.createStatement();
			rs= stmt.executeQuery("SHOW TABLES LIKE " + "'" + tableName+"'");
			if(rs.next() == false) {		// 테이블이 있는지 조회, 없다면 새로 생성합니다.
				stmt.executeUpdate("CREATE TABLE " + tableName + 
						"(id VARCHAR(20)  NOT NULL, pw VARCHAR(30) NOT NULL, name VARCHAR(20) NOT NULL, email VARCHAR(40) NOT NULL);");
						System.out.println(tableName+" 테이블이 생성되었습니다. ");
						
			}
			// 안드로이드 어플리케이션에서 회원정보를 받는다.
			ObjectInputStream ois = new ObjectInputStream(request.getInputStream());
			HashMap<String, String> stringDataMap = (HashMap<String, String>)ois.readObject();

			String sql = "INSERT INTO " + tableName +"(id, pw, name, email) VALUES(?, ?, ?, ?)";	// 데이터 베이스에 추가
			PreparedStatement psmt = conn.prepareStatement(sql);
			psmt.setString(1, stringDataMap.get("id"));
			psmt.setString(2, stringDataMap.get("pw"));
			psmt.setString(3, stringDataMap.get("name"));
			psmt.setString(4, stringDataMap.get("email"));
			psmt.executeUpdate();	
		} catch (Exception e) {
				throw new ServletException(e);
			} finally {
				try {if (rs != null) rs.close();} catch(Exception e) {}
				try {if (stmt != null) stmt.close();} catch(Exception e) {}
				try {if (conn != null) conn.close();} catch(Exception e) {}
			}
	}
}