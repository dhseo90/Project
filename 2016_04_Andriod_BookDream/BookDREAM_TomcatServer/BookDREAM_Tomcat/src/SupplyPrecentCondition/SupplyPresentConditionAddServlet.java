package SupplyPrecentCondition;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Calendar;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/*
 	안드로이드 어플리케이션에서 공급게시판 글에 DREAM 요청을 하면 이를 이용해
 	공급현황 DB를 생성하는 서블릿 
 	좀더 효율적인 데이터베이스 사용을 위해 공급 DB의 b_no를 이용한 외래키로 행을 생성
 	DREAM 신청은 오직 한명만 할 수 있게 설정(나중에 공급하는 사람이 이걸보고 선택을 해야하는데 많은 사람을 보고 생각해서 선택하기보다는 
 	선착순으로 하는 것이 더 공평할 것이라 생각해서 한명만 가능하게 했다.)
 */
@WebServlet("/addSupplyPresentConditionInfo")
public class SupplyPresentConditionAddServlet extends HttpServlet {
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
		System.out.println("공급 현황 추가 서블릿 동작 ");	
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String tableName = "supply_present_condition_table";
		HashMap<String, String> dataMap=new HashMap<String, String>();
		try {
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());
			conn = DriverManager.getConnection(
					"jdbc:mysql://localhost/supply_db", //JDBC URL
					"root",	// DBMS 사용자 아이디
					"1234");	// DBMS 사용자 암호
			stmt = conn.createStatement();
			rs= stmt.executeQuery("SHOW TABLES LIKE " + "'" + tableName+"'");
			if(rs.next() == false) {		// 테이블이 있는지 조회, 없다면 새로 생성합니다.
				stmt.executeUpdate("CREATE TABLE " + tableName + 
						"(no INT NOT NULL, demand_id VARCHAR(20) NOT NULL, demand_name VARCHAR(20) NOT NULL,"
						+ " FOREIGN KEY(no) REFERENCES supply_bulletinboard_table(b_no))ENGINE=INNODB;");
						System.out.println(tableName+" 테이블이 생성되었습니다. ");
						
			}
			ObjectInputStream ois = new ObjectInputStream(request.getInputStream());
			HashMap<String, String> stringDataMap =(HashMap<String,String>)ois.readObject();
			ois.close();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(
					"SELECT * FROM " + tableName + " WHERE demand_id='" + stringDataMap.get("demand_id") + "'"
					+" AND no="+Integer.parseInt(stringDataMap.get("no")));
			if(rs.next()==false){		// 요청한 사람이 없으면 DREAM 신청 완료 -> 공급 현황 DB에 데이터 추가
				System.out.println("DREAM 신청 완료"+stringDataMap.get("no"));
				dataMap.put("state", "success");
				
				String sql = "INSERT INTO " + tableName +"(no, demand_id, demand_name) VALUES(?, ?, ?)";
				PreparedStatement psmt = conn.prepareStatement(sql);
				psmt.setInt(1, Integer.parseInt(stringDataMap.get("no")));
				psmt.setString(2, stringDataMap.get("demand_id"));
				psmt.setString(3, stringDataMap.get("demand_name"));
				psmt.executeUpdate();	
			}
			else	// 요청한 사람이 없으면 DREAM 신청 실패
				System.out.println("DREAM 신청 실패");
			
			ServletOutputStream servletOutputStream = response.getOutputStream();
			ObjectOutputStream oos =new ObjectOutputStream(servletOutputStream);
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