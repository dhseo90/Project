package CompletePrecentCondition;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import javax.imageio.ImageIO;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/*
 	공급자가 DREAM을 하겠다고 메시지들을 작성했을 때 완료 현황 DB에
 	정보가 추가되고 소켓서버는 이를 확인하여 책을 요청한 사람에게 
 	이 정보를 지속적으로 전달해주는 서블릿
 */
@WebServlet("/completePresentConditionInfo")
public class CompletePresentConditionServlet extends HttpServlet {
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
		System.out.println("완료 현황 등록 서블릿 동작");	
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		HashMap<String, String> stringDataMap =null;
		HashMap<String, String> dataMap =new HashMap<String, String>();
		String tableName = "complete_present_condition_table";
		try {
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());
			conn = DriverManager.getConnection(
					"jdbc:mysql://localhost/complete_present_condition_db", //JDBC URL
					"root"
					+ ""
					+ "",	// DBMS 사용자 아이디
					"1234");	// DBMS 사용자 암호
			stmt = conn.createStatement();
			rs= stmt.executeQuery("SHOW TABLES LIKE " + "'" + tableName+"'");
			/*
			 	요청자에게 전달되는 메시지는 구체적으로 정의되어 완료 현황 DB에 저장된다.
			 	저장되는 정보는 공급자 id, 공급자 이름, 요청자 id, 요청자 이름, 날짜, 시간, 장소, 내용, 폰번호를 포함한다.
			 */
			if(rs.next() == false) {		// 테이블이 있는지 조회, 없다면 새로 생성합니다.
				stmt.executeUpdate("CREATE TABLE " + tableName + 
						"(title VARCHAR(20) NOT NULL, supply_id VARCHAR(20) NOT NULL, supply_name VARCHAR(20) NOT NULL, "
						+ "demand_id VARCHAR(20) NOT NULL, demand_name VARCHAR(20) NOT NULL, "
						+ "date VARCHAR(20) NOT NULL, time VARCHAR(20) NOT NULL, place VARCHAR(20) NOT NULL, "
						+ "content VARCHAR(40) NOT NULL, phone VARCHAR(20) NOT NULL);");
						System.out.println(tableName+" 테이블이 생성되었습니다. ");
						
			}
			
			ObjectInputStream ois = new ObjectInputStream(request.getInputStream());
			stringDataMap = (HashMap<String, String>)ois.readObject();
			ois.close();
			
			/*
			 	완료 현황이 만들어지는 것은 매칭의 완료를 뜻하기때문에 공급 DB, 공급현황 DB에서 해당 내용의
			 	정보들이 삭제된다.그렇기 때문에 안드로이드 어플리케이션으로 해당하는 글의 번호도 받아옵니다.
			 */
			int no=Integer.parseInt(stringDataMap.get("no"));
			stmt = conn.createStatement();
			rs = stmt.executeQuery(
					"SELECT * FROM " + tableName+ " WHERE supply_id='" + stringDataMap.get("supply_id")+"'"
					+" AND demand_id='"+stringDataMap.get("demand_id")+"' AND title='"+stringDataMap.get("title")+"'" );
			if(rs.next()==false) {
				dataMap.put("state", "success");		// DREAM 성공, 실패했으면 빈 dataMap이 안드로이드 어플리케이션으로 전달된다.
				String sql = "INSERT INTO " + tableName +"(title, supply_id, supply_name, demand_id, demand_name, date, time, place, content, phone) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
				PreparedStatement psmt = conn.prepareStatement(sql);
				psmt.setString(1, stringDataMap.get("title"));
				psmt.setString(2, stringDataMap.get("supply_id"));
				psmt.setString(3, stringDataMap.get("supply_name"));
				psmt.setString(4, stringDataMap.get("demand_id"));
				psmt.setString(5, stringDataMap.get("demand_name"));
				psmt.setString(6, stringDataMap.get("date"));
				psmt.setString(7, stringDataMap.get("time"));
				psmt.setString(8, stringDataMap.get("where"));
				psmt.setString(9, stringDataMap.get("content"));
				psmt.setString(10, stringDataMap.get("phone"));
				psmt.executeUpdate();	
				// DREAM을 한 방식을 구분한다.(공급게시판에서 매칭했냐, 수요게시판에서 매칭했냐를 구분)
				if(stringDataMap.get("type").equals("supply")) {		// 공급에서 매칭했으면 공급게시판에서 해당게시글 삭제
					conn.close();
					DriverManager.registerDriver(new com.mysql.jdbc.Driver());
					conn = DriverManager.getConnection(
								"jdbc:mysql://localhost/supply_db", //JDBC URL
								"root"
								+ ""
								+ "",	// DBMS 사용자 아이디
								"1234");	// DBMS 사용자 암호
					tableName = "supply_present_condition_table";
					stmt = conn.createStatement();
					stmt.executeUpdate(
						"DELETE FROM " + tableName+ " WHERE no=" + no);

					tableName = "supply_bulletinboard_table";
					stmt = conn.createStatement();
					stmt.executeUpdate(
							"DELETE FROM " + tableName+ " WHERE b_no=" + no);
					stmt.executeUpdate(
							"UPDATE " + tableName+ " SET b_no=b_no-1"
							+" WHERE b_no > "+no);
				}
				else if(stringDataMap.get("type").equals("demand"))		// 수요에서 매칭했으면 공급게시판에서 해당게시글 삭제
				{
					conn.close();
					DriverManager.registerDriver(new com.mysql.jdbc.Driver());
					conn = DriverManager.getConnection(
								"jdbc:mysql://localhost/demand_db", //JDBC URL
								"root"
								+ ""
								+ "",	// DBMS 사용자 아이디
								"1234");	// DBMS 사용자 암호c
					

					tableName = "demand_bulletinboard_table";
					stmt = conn.createStatement();
					stmt.executeUpdate(
							"DELETE FROM " + tableName+ " WHERE b_no=" + no);
					stmt.executeUpdate(
							"UPDATE " + tableName+ " SET b_no=b_no-1"
							+" WHERE b_no > "+no);
				}
				else
					return ;
			} 
		
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