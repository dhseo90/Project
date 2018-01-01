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
@WebServlet("/removeCompletePresentConditionInfo")
public class RemoveCompletePresentConditionServlet extends HttpServlet {
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
		System.out.println("삭제 완료 현황 서블릿접속은 되요");	
		RequestDispatcher rd = null;
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String tableName = "complete_present_condition_table";
		try {
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());
			conn = DriverManager.getConnection(
					"jdbc:mysql://localhost/complete_present_condition_db", //JDBC URL
					"root"
					+ ""
					+ "",	// DBMS 사용자 아이디
					"1234");	// DBMS 사용자 암호
			ObjectInputStream ois = new ObjectInputStream(request.getInputStream());
			ArrayList<String> list =(ArrayList<String>)ois.readObject();

			stmt = conn.createStatement();
			stmt.executeUpdate("DELETE FROM " + tableName+ " WHERE no=" + Integer.parseInt(list.get(0)));

		
		} catch (Exception e) {
				throw new ServletException(e);
			} finally {
				try {if (rs != null) rs.close();} catch(Exception e) {}
				try {if (stmt != null) stmt.close();} catch(Exception e) {}
				try {if (conn != null) conn.close();} catch(Exception e) {}
			}
		
	}
}