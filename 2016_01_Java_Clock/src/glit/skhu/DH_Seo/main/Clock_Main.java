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
 * ��ü���� ������ �����ϴ� Ŭ����.
 * �� Ŭ�������� ������� ���������� �ð谡 ������ �ȴ�.
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

	/* ĸ���ڵ带 �ޱ� ���� ���� */
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

		/* �޴��� ���� */
		MenuBar = new JMenuBar();
		setJMenuBar(MenuBar);
		CreateMenu();

		/* �ð� ��ü�� paint */
		pClock= new Clock_Paint();	// �гλ����ڿ� �ʱ�ȭ�κ��� �־ ������ �ϸ� ������ �����˴ϴ�.
		add(pClock);

		/* time�� ���� ���� �ǳ� ��ġ ���� */
		timeView = new JPanel();
		lb = new JLabel("");

		/* �˶�/�ð����� �κ� ���� ��ġ ���� */
		alarm = new Alarm();
		alarm.setLocation(0, 500);
		alarm.setSize(600, 180);
		alarm.setBackground(Color.WHITE);
		add(alarm);

		setResizable(false);
		setSize(600, 725);
		setVisible(true);	

		/* ���� ���� �ð��� �� �ѹ� ����� �Ѵ�. ���Ŀ� �ð��� run���� ���� �����ϱ� ������ �ѹ��� ������ �ȴ�. */
		Calendar c = Calendar.getInstance();		// ���� ��¥�� �ޱ� ���� Calendar ��ü�� �޾ƿɴϴ�.
		nowtime = (c.get(Calendar.HOUR) * 60 * 60) + (c.get(Calendar.MINUTE ) * 60) + c.get(Calendar.SECOND);
		
		new Thread(this).start();       		// ����������Լ��� ���� ��ü�� �ְ� �ٷ� ���� 
	}

	public static void main(String args[])			// ���� �Լ� 
	{ 
		new Clock_Main();	// ���⼭ �ð� ���α׷� ����	       
	}

	@Override
	public void run() 
	{
		while(true)
		{            
			/* �˶� ������ ��� üũ�Ѵ�. */
			if (alarm.timesetflag == true)
			{
				nowtime = alarm.timeset;
				alarm.timesetflag = false;
			}
			else
			{
				/* Ư���� ������ ���� �� 1�ʸ� ��� ���Ѵ� */
				nowtime += settime;
			}

			/* �˶� ������ ����ؼ� üũ�Ѵ�. */
			alarm.checkAlarm();
			
			/* �˶� �ð��� ���� �ð����� �������� ������ �����Ѵ�. */
			if (alarm.calAlarmTime <= 0)
			{
				alarm.alarmflag = false;
			}
			
			pClock.repaint();			// �ð踦 �׸��� ���̱� ������ ��� �ٽ� �׷��־�� �մϴ�.

			try
			{
				Thread.sleep(1000);	// ������� 1�ʸ��� ���̵�ϴ�. �� 1�ʸ��� ���� ������ �����մϴ�.
			}
			catch(Exception ex)	// �������� ���� ���� ��� ���
			{
				ex.printStackTrace();
			}
		}
	}

	private void CreateMenu()
	{		
		Help = new JMenu("Help(H)");	/* Help �޴� ���� */
		Help.setMnemonic('h');			/* Help �޴��� ����Ű ����(ALT + H) */

		/* ����ڰ� �ð��� �������� �� �ѱ� �ð����� ���ư��� ���� �޴� */
		KoreaTime = new JMenuItem("KoreaTime(K)", new ImageIcon(cl.getResource("img/TimeSetting.jpg")));
		KoreaTime.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_K, ActionEvent.ALT_MASK));
		KoreaTime.addActionListener(this);	
		Help.add(KoreaTime);
		
		/* Help�� MadeBy �޴� ����(�̹���, ����Ű(CTRL + W), ActionListener �߰�, Help �޴��ٿ� �߰�) */
		MadeBy = new JMenuItem("Who(W)", new ImageIcon(cl.getResource("img/Who.jpg")));
		MadeBy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, ActionEvent.ALT_MASK));
		MadeBy.addActionListener(this);	
		Help.add(MadeBy);

		MenuBar.add(Help);
	}

	public void actionPerformed(ActionEvent e) 
	{
		/* �߻��� �̺�Ʈ �޴� ��ȯ */
		String col = (String)e.getActionCommand(); 					

		/* ������ ���θ���⸦ ������ ���, */
		if (col.equals("Who(W)"))
		{
			JOptionPane.showMessageDialog(this, "����ȸ���б�\n�й�: 200934013\n�̸�: ������\n",
					"����ȸ���б�", JOptionPane.INFORMATION_MESSAGE);
		}
		else if (col.equals("KoreaTime(K)"))
		{
			Calendar c = Calendar.getInstance();		// ���� ��¥�� �ޱ� ���� Calendar ��ü�� �޾ƿɴϴ�.
			nowtime = (c.get(Calendar.HOUR) * 60 * 60) + (c.get(Calendar.MINUTE) * 60) + c.get(Calendar.SECOND);
			
		}
	}
}