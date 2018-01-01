package CompletePrecentCondition;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.Date;
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
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/*
 	회원 정보를 받아서 이를 통해 공급게시판, 수요게시판에 작성한 건수,
 	다른 회원이 DREAM 신청한 건수 등의 정보를 조회해 안드로이드 어플리케이션으로 
 	정보를 묶어서 넘겨주는 서블릿
 */
@WebServlet("/loadAllPresentConditionInfo")
public class AllPrecentConditionLoadServlet extends HttpServlet {
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
		System.out.println("글 작성 건수, DREAM 신청 건수를 로드하는 서블릿 동작");
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String tableName = "demand_bulletinboard_table";
		ServletOutputStream servletOutputStream = response.getOutputStream();
		ObjectOutputStream oos =new ObjectOutputStream(servletOutputStream);
		HashMap<String, String> dataMap = new HashMap<String, String>();
		try {
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());
			conn = DriverManager.getConnection(
					"jdbc:mysql://localhost/demand_db", //JDBC URL
					"root",	// DBMS 사용자 아이디
					"1234");	// DBMS 사용자 암호
			
			// 조회를 위한 회원 정보를 안드로이드 어플리케이션으로 받는다. 
			ObjectInputStream ois = new ObjectInputStream(request.getInputStream());
			HashMap<String, String> userDataMap =(HashMap<String,String>)ois.readObject();
			stmt = conn.createStatement();
			rs= stmt.executeQuery("SHOW TABLES LIKE " + "'" + tableName+"'");
			// 회원이 수요 게시판에 글을 작성한 건수를 계산하는 과정
			int i=0;
			if(rs.next()!=false) {

				stmt = conn.createStatement();
				rs = stmt.executeQuery(
						"SELECT * FROM " + tableName+ " WHERE b_user='" + userDataMap.get("id")+" "
							 + userDataMap.get("name")+"'");
				
				while(rs.next()) {
					i++;
				}
			}
			dataMap.put("demand_cnt", i+"");
			conn.close();

			// 회원이 공급 게시판에 글을 작성한 건수를 계산하는 과정
			i=0;
			tableName = "supply_bulletinboard_table";
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());
			conn = DriverManager.getConnection(
						"jdbc:mysql://localhost/supply_db", //JDBC URL
						"root"
						+ ""
						+ "",	// DBMS 사용자 아이디
						"1234");	// DBMS 사용자 암호

			stmt = conn.createStatement();
			rs= stmt.executeQuery("SHOW TABLES LIKE " + "'" + tableName+"'");
			if(rs.next()!=false) {
				// 공급 게시판 검색
				stmt = conn.createStatement();
				rs = stmt.executeQuery(
						"SELECT * FROM " + tableName+ " WHERE id='" + userDataMap.get("id")+"'");
				
				while(rs.next()) {
					i++;
				}
			}
			dataMap.put("supply_cnt", i+"");
			// 공급현황에서 회원에게 DREAM 신청한 건수를  계산하는 과정
			i=0;
			tableName = "supply_present_condition_table";
			stmt = conn.createStatement();
			rs= stmt.executeQuery("SHOW TABLES LIKE " + "'" + tableName+"'");
			if(rs.next()!=false) {
				stmt = conn.createStatement();
				rs = stmt.executeQuery(
							"SELECT no, demand_id, demand_name FROM " + tableName+ " INNER JOIN supply_bulletinboard_table"
							+" ON " + tableName +".no =supply_bulletinboard_table.b_no"
							+ " WHERE supply_bulletinboard_table.id='" + userDataMap.get("id")+"'");
					
				while(rs.next()) {
						i++;
				}
			}
			dataMap.put("dream_cnt", i+"");
			response.setContentType("application/octet-stream");	
			oos.writeObject(dataMap);
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