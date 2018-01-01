package glit.skhu.DH_Seo.main;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Calendar;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import glit.skhu.DH_Seo.GUI.Clock_Paint;
import glit.skhu.DH_Seo.function.Alarm;

/**
 * 전체적인 구조를 구성하는 클래스.
 * 이 클래스에서 스레드로 실질적으로 시계가 구현이 된다.
 * 
 **/
@SuppressWarnings("serial")
public class Clock_Main  extends JFrame implements Runnable, ActionListener
{
	private JMenuBar MenuBar;
	private JMenu Help;
	private JMenuItem MadeBy, KoreaTime;
	JPanel timeView;
	JLabel lb;
	Clock_Paint pClock;
	Alarm alarm;
	private ClassLoader cl = this.getClass().getClassLoader();
	public static int nowtime = 0;
	public static boolean alarmOK = false;
	private int settime = 1;

	/* 캡차코드를 달기 위해 설정 */
	public static char [] initRandomChar = {'A', 'B', 'C', 'D', 'F', 'G', 'H', 'I', 'J', 
			'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 
			'N', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', 
			'1', '2', '3', '4', '5', '6', '7', '8', '9',
			'!', '@', '#', '$', '%', '^', '&', '*', '(',
			')', '+', '-', '*', '/', '?'};

	public Clock_Main() 
	{
		setTitle("DongHyeong's Alarm Clock");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		setLayout(null);

		/* 메뉴바 설정 */
		MenuBar = new JMenuBar();
		setJMenuBar(MenuBar);
		CreateMenu();

		/* 시계 본체를 paint */
		pClock= new Clock_Paint();	// 패널생성자에 초기화부분을 넣어서 생성만 하면 값들이 설정됩니다.
		add(pClock);

		/* time을 보기 위한 판넬 위치 조정 */
		timeView = new JPanel();
		lb = new JLabel("");

		/* 알람/시간설정 부분 관련 위치 조정 */
		alarm = new Alarm();
		alarm.setLocation(0, 500);
		alarm.setSize(600, 180);
		alarm.setBackground(Color.WHITE);
		add(alarm);

		setResizable(false);
		setSize(600, 725);
		setVisible(true);	

		/* 현재 기준 시간을 단 한번 출력을 한다. 이후에 시간은 run에서 직접 설정하기 때문에 한번만 받으면 된다. */
		Calendar c = Calendar.getInstance();		// 현재 날짜를 받기 위해 Calendar 객체를 받아옵니다.
		nowtime = (c.get(Calendar.HOUR) * 60 * 60) + (c.get(Calendar.MINUTE ) * 60) + c.get(Calendar.SECOND);
		
		new Thread(this).start();       		// 쓰레드생성함수에 현재 객체를 넣고 바로 실행 
	}

	public static void main(String args[])			// 메인 함수 
	{ 
		new Clock_Main();	// 여기서 시계 프로그램 실행	       
	}

	@Override
	public void run() 
	{
		while(true)
		{            
			/* 알람 설정을 계속 체크한다. */
			if (alarm.timesetflag == true)
			{
				nowtime = alarm.timeset;
				alarm.timesetflag = false;
			}
			else
			{
				/* 특별한 설정이 없을 시 1초를 계속 더한다 */
				nowtime += settime;
			}

			/* 알람 설정을 계속해서 체크한다. */
			alarm.checkAlarm();
			
			/* 알람 시간이 현재 시간보다 이전으로 설정시 무시한다. */
			if (alarm.calAlarmTime <= 0)
			{
				alarm.alarmflag = false;
			}
			
			pClock.repaint();			// 시계를 그리는 것이기 때문에 계속 다시 그려주어야 합니다.

			try
			{
				Thread.sleep(1000);	// 쓰레드는 1초마다 잠이듭니다. 즉 1초마다 위의 문장을 실행합니다.
			}
			catch(Exception ex)	// 에러나면 에러 관련 기록 출력
			{
				ex.printStackTrace();
			}
		}
	}

	private void CreateMenu()
	{		
		Help = new JMenu("Help(H)");	/* Help 메뉴 설정 */
		Help.setMnemonic('h');			/* Help 메뉴의 단축키 설정(ALT + H) */

		/* 사용자가 시간을 변경했을 시 한국 시간으로 돌아가기 위한 메뉴 */
		KoreaTime = new JMenuItem("KoreaTime(K)", new ImageIcon(cl.getResource("img/TimeSetting.jpg")));
		KoreaTime.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_K, ActionEvent.ALT_MASK));
		KoreaTime.addActionListener(this);	
		Help.add(KoreaTime);
		
		/* Help의 MadeBy 메뉴 설정(이미지, 단축키(CTRL + W), ActionListener 추가, Help 메뉴바에 추가) */
		MadeBy = new JMenuItem("Who(W)", new ImageIcon(cl.getResource("img/Who.jpg")));
		MadeBy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, ActionEvent.ALT_MASK));
		MadeBy.addActionListener(this);	
		Help.add(MadeBy);

		MenuBar.add(Help);
	}

	public void actionPerformed(ActionEvent e) 
	{
		/* 발생한 이벤트 메뉴 반환 */
		String col = (String)e.getActionCommand(); 					

		/* 유저가 새로만들기를 선택한 경우, */
		if (col.equals("Who(W)"))
		{
			JOptionPane.showMessageDialog(this, "성공회대학교\n학번: 200934013\n이름: 서동형\n",
					"성공회대학교", JOptionPane.INFORMATION_MESSAGE);
		}
		else if (col.equals("KoreaTime(K)"))
		{
			Calendar c = Calendar.getInstance();		// 현재 날짜를 받기 위해 Calendar 객체를 받아옵니다.
			nowtime = (c.get(Calendar.HOUR) * 60 * 60) + (c.get(Calendar.MINUTE) * 60) + c.get(Calendar.SECOND);
			
		}
	}
}