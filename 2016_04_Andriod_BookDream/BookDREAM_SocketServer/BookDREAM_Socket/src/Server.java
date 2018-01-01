import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.StringTokenizer;
/*
 	안드로이드 어플리케이션의 서비스와 연결하여 완료 현황 DB에
 	id와 이름이 매칭 되는 사람이면 DREAM을 하겠다는 사람의 메시지를 받는 역할을 하는 소켓 서버
 */
public class Server {
	HashMap<String, DataOutputStream> clients;
	private ServerSocket ServerSocket = null;
	private static InetAddress address=null;
	public static void main(String[] args) {
		new Server().start();
	}

	public Server() {
		clients = new HashMap<String, DataOutputStream>();
		Collections.synchronizedMap(clients);
	}

	private void start() {
		int port = 5001;
		Socket socket = null;

		try {
			ServerSocket = new ServerSocket(port);
			System.out.println("wait");
			while (true) {	
				System.out.println("test");
				socket = ServerSocket.accept();	// 무한 대기
				
				InetAddress ip = socket.getInetAddress();
				System.out.println(ip + "  connected");
				new MultiThread(socket).start();	// 쓰레드로 통신을 하기위해 socket을 인자로 넘겨준다.
			}
		} catch (IOException e) {
			System.out.println(e);
		}
	}
	class MultiThread extends Thread {
		Socket socket = null;
		DataInputStream input;
		DataOutputStream output;
			
		public MultiThread(Socket socket) {
			this.socket = socket;
			try {
				input = new DataInputStream(socket.getInputStream());
				output = new DataOutputStream(socket.getOutputStream());
			} catch (IOException e) {
			}
		}
		public void run() {

			HashMap<String, String> stringDataMap = null;
			try {
				/*
				 	안드로이드 프로그램으로 부터 id와 이름을 전송받는다.
				 	이를 이용해 DB 조회를 한다.
				 */
				ObjectInputStream ois;
				ois = new ObjectInputStream(socket.getInputStream());
				stringDataMap = (HashMap<String, String>)ois.readObject();
				
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			System.out.println("Test1");
			Connection conn = null;
			Statement stmt = null;
			ResultSet rs = null;
			String tableName = "complete_present_condition_table";
			boolean success=false;
			try {
				// 최근 현황 테이블 검색
				DriverManager.registerDriver(new com.mysql.jdbc.Driver());
				conn = DriverManager.getConnection(
						"jdbc:mysql://localhost/complete_present_condition_db", //JDBC URL
						"root",	// DBMS 사용자 아이디
						"1234");	// DBMS 사용자 암호
			while (true) {
				if(!socket.isConnected()){		// 연결끊어지면 종료
					System.out.println("종료되었습니다.");
					return;
					
				}
				System.out.println("안드로이드에서 데이터베이스 조회결과를 보냅니다.");
				
					stmt = conn.createStatement();
					rs= stmt.executeQuery("SHOW TABLES LIKE " + "'" + tableName+"'");
					if(rs.next() == false) {		// 테이블이 있는지 조회, 없다면 skip	
						/*Thread.sleep(2000);*/
						continue;
					}		
					/*
					 	완료 현황에 정보올라왔는지 조회한다.
					 */
					stmt = conn.createStatement();
					rs = stmt.executeQuery(
							"SELECT * FROM " + tableName+ " WHERE demand_id='" + stringDataMap.get("id") +"'" + 
							" AND demand_name='"+ stringDataMap.get("name") +"'");
					if(success==true && rs.next()==false) {
						success=false;
					}
					HashMap<String, String> dataMap = new HashMap<String, String>();
					while(rs.next()) {
						dataMap.put("title", rs.getString("title"));
						dataMap.put("supply_id", rs.getString("supply_id"));
						dataMap.put("supply_name", rs.getString("supply_name"));
						dataMap.put("date", rs.getString("date"));
						dataMap.put("time", rs.getString("time"));
						dataMap.put("place", rs.getString("place"));
						dataMap.put("content", rs.getString("content"));
						dataMap.put("phone", rs.getString("phone"));
						break;
					}
					// 데이터가 없다면 빈 데이터를 줍니다.
					ObjectOutputStream oos =new ObjectOutputStream(socket.getOutputStream());
					oos.writeObject(dataMap);
					oos.flush();
					if(success==false) {
						success=true;
						Thread.sleep(600000);// 처음엔 바로 보내고 이후 1시간 동안 잠든다.
					} else
						Thread.sleep(1800000);//이후부터는 3시간뒤마다 메시지 전송
					
					continue;
					
				}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {if (rs != null) rs.close();} catch(Exception e) {}
			try {if (stmt != null) stmt.close();} catch(Exception e) {}
			try {if (conn != null) conn.close();} catch(Exception e) {}
		}
		}
	}	
}
