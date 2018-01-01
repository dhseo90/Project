package glit.skhu.DH_Seo.function;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import glit.skhu.DH_Seo.main.Clock_Main;

/**
 * 알람 기능 및 시간 설정 관련 기능이 모여있는 클래스이다.
 * @author DongHyeong Seo
 */
@SuppressWarnings({ "serial" })
public class Alarm extends JPanel implements ActionListener
{
	JLabel label;
	public JLabel label2;
	JSpinner hour, minute, second;
	public static int reshour, resminute, ressecond;
	String[] time = {"AM", "PM"};
	public boolean is_pm;
	JButton set_alarm, set_timeSetting;
	public boolean alarmflag = false;
	public boolean timesetflag = false;
	
	public int temp1 = 8, temp2 = 0, temp3 = 0;
	public int timeset = 0, timeset2 = 0;
	public int calAlarmTime = 0;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Alarm()		// 시계그리는 패널 초기화
	{    	
		setSize(600, 180);
		setVisible(true);

		setLayout(null);

		/* 처음 기준점을 만들기 위해 HH, MM, SS, AM/PM, 시간/알림을 출력한다. */
		label = new JLabel();
		label.setOpaque(true);
		label.setFont(new Font("돋움", Font.BOLD, 18));
		label.setBackground(Color.BLACK);
		label.setBackground(Color.WHITE);
		label.setBounds(35, 10, 540, 40); // (좌표 x, 좌표 y, 가로 w, 세로 h)
		label.setText("HH          MM          SS          AM/PM          시간/알람");
		add(label);
		
		/* hour 관련 사용자의 입력을 받아 저장한다. */
		hour = new JSpinner();
		SpinnerNumberModel numModel1 = new SpinnerNumberModel(1, 1, 12, 1); /* 순서대로 초기값, 최소값, 최대값, 올라가는 단위 */
		hour.setModel(numModel1);
		hour.setBounds(25, 50, 50, 40);
		add(hour);

		/* minute 관련 사용자의 입력을 받아 저장한다. */
		minute = new JSpinner();
		SpinnerNumberModel numModel2 = new SpinnerNumberModel(0, 1, 60, 1); /* 순서대로 초기값, 최소값, 최대값, 올라가는 단위 */
		minute.setModel(numModel2);
		minute.setBounds(120, 50, 50, 40);
		add(minute);

		/* second 관련 사용자의 입력을 받아 저장한다. */
		second = new JSpinner();
		SpinnerNumberModel numModel3 = new SpinnerNumberModel(0, 1, 60, 1); /* 순서대로 초기값, 최소값, 최대값, 올라가는 단위 */
		second.setModel(numModel3);
		second.setBounds(215, 50, 50, 40);
		add(second);

		/* 사용자에게 AM/PM을 선택하게 한다. */
		JComboBox c1 = new JComboBox(time);
		c1.setBounds(318, 50, 75, 40);
		c1.addActionListener(new ActionListener() // 콤보박스 선택 시 이벤트 제어를 위한 액션리스너 설정
		{  
			public void actionPerformed(ActionEvent e) // 액션 이벤트 발생을 처리하도록 메소드 오버라이드
			{ 
				JComboBox cb = (JComboBox)e.getSource(); // getSource메소드로 이벤트가 발생한 인덱스를 Object형으로 반환하기 때문에 콤보박스 타입으로 Cast
				int index = cb.getSelectedIndex(); // getSelectedIndex메소드로써 콤보박스의 선택된 아이템의 인덱스 번호 알아내기
				if(index == 1) // 만약 인덱스가 1이라면
				{ 
					is_pm = true; // is_pm을 true로 할당해주고
				}
				else // 그렇지 않다면
				{
					is_pm = false; // is_pm을 false으로 지정해줌
				}
			}   
				});
		c1.setBackground(Color.WHITE);
		add(c1);

		/* 시간 설정 버튼 */
		set_alarm = new JButton("시간");
		set_alarm.setBounds(420, 50, 70, 40);
		set_alarm.setBackground(Color.WHITE);
		set_alarm.addActionListener(this);
		add(set_alarm);

		/* 알람 설정 버튼 */
		set_timeSetting = new JButton("알람");
		set_timeSetting.setBounds(500, 50, 70, 40);
		set_timeSetting.setBackground(Color.WHITE);
		set_timeSetting.addActionListener(this);
		add(set_timeSetting);

		/* 알람 설정에 이용하는 남은 시간을 paint하는 label */
		label2 = new JLabel();
		label2.setOpaque(true);
		label2.setFont(new Font("돋움", Font.BOLD, 18));
		label2.setBackground(Color.BLACK);
		label2.setBackground(Color.WHITE);
		setzoerTime();
		label2.setBounds(25, 110, 540, 40); // (좌표 x, 좌표 y, 가로 w, 세로 h)
		add(label2);
	}


	@Override
	public void actionPerformed(ActionEvent e) 
	{
		String click = e.getActionCommand();
		
		/* 사용자에게 입력받은 값(시/분/초)을 temp1~temp3 변수에 입력함 */
		temp1 = (Integer)hour.getValue();
		temp2 = (Integer)minute.getValue();
		temp3 = (Integer)second.getValue();
		
		/* 만약 현재 오후라면 시간에 12시간을 더한다. */
		if (is_pm == true)
		{
			temp1 += 12;
		}

		/* 시간 버튼을 클릭한다면, */
		if (click.equals("시간")) 		
		{
			/* 입력받은 값을 일단 다 초로 변환한다. */
			timeset = (temp1 * 60 * 60) + (temp2 * 60) + temp3;	
			
			/* timesetflag를 올려준다. */
			if (timesetflag == false)
			{
				timesetflag = true;
			}
		}
		/* 알람 버튼을 클릭한다면, */
		else if (click.equals("알람")) 	
		{
			/* 입력받은 값을 일단 다 초로 변환한다. */
			timeset2 = (temp1 * 60 * 60) + (temp2 * 60) + temp3;	
			
			/* 알람이 설정된적이 없고, 현재 알람이 진행중이 아니라면 알람을 시작하는 alarmflag를 설정한다. */
			if (alarmflag == false && Clock_Main.alarmOK == false)
			{
				alarmflag = true;
				System.out.println("Alarm Start");
			}
			/* 알람이 완전히 끝날때까지 다른 알람을 설정받지 않기 위해 설정한다. */
			else if (alarmflag == true && Clock_Main.alarmOK == true)
			{
				alarmflag = false;
				Clock_Main.alarmOK = false;
				System.out.println("Alarm Finsih");
			}
		}
	}
	
	public void checkAlarm()
	{
		/* 알람이 설정된다면 검사를 한다. */
		if (alarmflag == true)
		{
			/* 현재 시간과 알람 설정 시간이 같아진다면 */
			if (checkAlarm(temp1, temp2, temp3) == true)
			{
				/* 캡챠코드를 위해 랜덤스트링을 설정한다. */
				String tmp = ""; 

				int i = 0;
				while ( i < 6 ) 
				{
					tmp += Clock_Main.initRandomChar[(int)( Math.random() * Clock_Main.initRandomChar.length ) ];
					i++;
				}

				/* 사용자가 정확한 캡챠코드 값을 입력하기 전까지 알람이 꺼지지 않는다. */
				while (true)
				{
					String msg = JOptionPane.showInputDialog("캡차코드를 입력하세요\n" + tmp);

					if (tmp.equals(msg))
					{
						Clock_Main.alarmOK = true;
						setzoerTime();
						break;
					}
				}
			}
			else
			{
				/* 알람이 설정되었지만, 아직 끝나지 않은 경우 남은 시간을 paint한다. */
				setRemainTime();
			}
		}
		else
		{
			/* 알람이 설정되지 않은경우 남은 시간은 모두 0으로 셋팅한다. */
			setzoerTime();
		}
	}
	
	/* 알람이 설정되지 않은 경우 0만 출력 */
	public void setzoerTime()
	{
		label2.setOpaque(true);
		label2.setText("알람 남은시간        0        0        0");
	}
	
	/* 알람이 설정되었지만 아직 알람 시간이 되지 않은 경우, 남은 시간을 출력한다. */
	public void setRemainTime()
	{
		int hours = calAlarmTime / 3600;
		int minute = (calAlarmTime % 3600) / 60;
		int second = (calAlarmTime % 3600) % 60;
		
		label2.setOpaque(true);
		label2.setText("알람 남은시간        " + hours + "        " + minute + "        " + second);
	}

	/* 현재 시간과 사용자가 입력한 알람 시간을 체크하는 메소드 */
	public boolean checkAlarm(int h, int m, int s)
	{
		int atime = (h * 60 * 60) + (m * 60) + s;
		int ntime = Clock_Main.nowtime;
				
		calAlarmTime = atime - ntime - 1;
		
		if (calAlarmTime == 0)
		{
			return true;
		}
		else
		{	
			return false;
		}
	}
}