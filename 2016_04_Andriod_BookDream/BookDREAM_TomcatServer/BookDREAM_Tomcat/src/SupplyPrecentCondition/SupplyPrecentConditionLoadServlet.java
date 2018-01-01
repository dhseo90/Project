package SupplyPrecentCondition;

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
 	안드로이드 어플리케이션에서 로그인 한 사람의 정보를 이용해
 	공급 현황(선배가 공급하겠다고 올린 게시글에 요청한 사람이 있으면 이를 공급현황 DB를 생성해 추가한다.(임시 공급 이라 생각하면 된다.)) 정보를
 	찾아서 로드해주는 서블릿
 */
@WebServlet("/loadSupplyPresentConditionInfo")
public class SupplyPrecentConditionLoadServlet extends HttpServlet {
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
		System.out.println("공급현황 로드 서블릿 동작");
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String tableName = "supply_present_condition_table";
		ServletOutputStream servletOutputStream = response.getOutputStream();
		ObjectOutputStream oos =new ObjectOutputStream(servletOutputStream);
		HashMap<String, String> dataMap = new HashMap<String, String>();
		try {
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());
			conn = DriverManager.getConnection(
					"jdbc:mysql://localhost/supply_db", //JDBC URL
					"root",	// DBMS 사용자 아이디
					"1234");	// DBMS 사용자 암호
			ObjectInputStream ois = new ObjectInputStream(request.getInputStream());
			ArrayList<String> idList =(ArrayList<String>)ois.readObject();
			stmt = conn.createStatement();
			rs= stmt.executeQuery("SHOW TABLES LIKE " + "'" + tableName+"'");
			if(rs.next() == false) {		// 테이블이 있는지 조회, 없다면 빈 데이터 보내고 종료-로드하는 것이기때문에 따로 테이블 생성안해도 됨
				oos.writeObject(dataMap);
				oos.flush();
				oos.close();
				return ;
			}
			/*
			 	안드로이드 어플리케이션에서 회원정보를 받아 
			 	그 정보와 일치하는 공급현황을 다시 안드로이드 어플리케이션으로 전송해준다.
			 	외래키로 공급게시판과 연결해놨기 때문에 ID 정보만 있으면 자세한 정보를 불러올 수 있다.
			 */
			stmt = conn.createStatement();
			rs = stmt.executeQuery(
					"SELECT no, demand_id, demand_name, supply_bulletinboard_table.b_title FROM " + tableName+ " INNER JOIN supply_bulletinboard_table"
					+" ON " + tableName +".no =supply_bulletinboard_table.b_no"
					+ " WHERE supply_bulletinboard_table.id=" + idList.get(0));
			response.setContentType("text/html; charset=UTF-8");
			int i=0;
			while(rs.next()) {
				dataMap.put("no "+i, rs.getInt("no")+"");
				dataMap.put("title"+i, rs.getString("supply_bulletinboard_table.b_title"));
				dataMap.put("id"+i, rs.getString("demand_id"));
				dataMap.put("name"+i, rs.getString("demand_name"));
				i++;
			}

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