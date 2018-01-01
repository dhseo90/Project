package Member;


import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/*
 	데이터 베이스에 저장된 모든 회원 정보를 
 	안드로이드 어플리케이션으로 보내는 서블릿
 	안드로이드 어플리케이션에서 로그인시 ID와 비밀번호가 맞는지 확인하고
 	그외에 정보도 같이 저장하기 위한 용도
 */
@WebServlet("/requestLogin")
public class LoginServlet  extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException
	{
		doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException
	{ 
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		System.out.println("로그인 서블릿 동작");
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
			// 데이터 베이스 조회..
			stmt = conn.createStatement();
			rs = stmt.executeQuery(
					"SELECT * FROM " + tableName);
			
			ArrayList<String> idList = new ArrayList<String>();
			ArrayList<String> pwList = new ArrayList<String>();
			ArrayList<String> nameList = new ArrayList<String>();
			ArrayList<String> emailList = new ArrayList<String>();
			
			while(rs.next()) {
				idList.add(rs.getString("id"));
				pwList.add(rs.getString("pw"));
				nameList.add(rs.getString("name"));
				emailList.add(rs.getString("email"));
			}
			response.setContentType("application/octet-stream");	
			ServletOutputStream servletOutputStream = response.getOutputStream();
			ObjectOutputStream oos =new ObjectOutputStream(servletOutputStream);
			oos.writeObject(idList);
			oos.flush();
			oos.writeObject(pwList);
			oos.flush();
			oos.writeObject(nameList);
			oos.flush();
			oos.writeObject(emailList);
			oos.flush();
			oos.close();
			servletOutputStream.flush();
			} catch (Exception e) {
				throw new ServletException(e);
			} finally {
				try {if (rs != null) rs.close();} catch(Exception e) {}
				try {if (stmt != null) stmt.close();} catch(Exception e) {}
				try {if (conn != null) conn.close();} catch(Exception e) {}
			}
		}
}