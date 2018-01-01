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
 * �˶� ��� �� �ð� ���� ���� ����� ���ִ� Ŭ�����̴�.
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
	public Alarm()		// �ð�׸��� �г� �ʱ�ȭ
	{    	
		setSize(600, 180);
		setVisible(true);

		setLayout(null);

		/* ó�� �������� ����� ���� HH, MM, SS, AM/PM, �ð�/�˸��� ����Ѵ�. */
		label = new JLabel();
		label.setOpaque(true);
		label.setFont(new Font("����", Font.BOLD, 18));
		label.setBackground(Color.BLACK);
		label.setBackground(Color.WHITE);
		label.setBounds(35, 10, 540, 40); // (��ǥ x, ��ǥ y, ���� w, ���� h)
		label.setText("HH          MM          SS          AM/PM          �ð�/�˶�");
		add(label);
		
		/* hour ���� ������� �Է��� �޾� �����Ѵ�. */
		hour = new JSpinner();
		SpinnerNumberModel numModel1 = new SpinnerNumberModel(1, 1, 12, 1); /* ������� �ʱⰪ, �ּҰ�, �ִ밪, �ö󰡴� ���� */
		hour.setModel(numModel1);
		hour.setBounds(25, 50, 50, 40);
		add(hour);

		/* minute ���� ������� �Է��� �޾� �����Ѵ�. */
		minute = new JSpinner();
		SpinnerNumberModel numModel2 = new SpinnerNumberModel(0, 1, 60, 1); /* ������� �ʱⰪ, �ּҰ�, �ִ밪, �ö󰡴� ���� */
		minute.setModel(numModel2);
		minute.setBounds(120, 50, 50, 40);
		add(minute);

		/* second ���� ������� �Է��� �޾� �����Ѵ�. */
		second = new JSpinner();
		SpinnerNumberModel numModel3 = new SpinnerNumberModel(0, 1, 60, 1); /* ������� �ʱⰪ, �ּҰ�, �ִ밪, �ö󰡴� ���� */
		second.setModel(numModel3);
		second.setBounds(215, 50, 50, 40);
		add(second);

		/* ����ڿ��� AM/PM�� �����ϰ� �Ѵ�. */
		JComboBox c1 = new JComboBox(time);
		c1.setBounds(318, 50, 75, 40);
		c1.addActionListener(new ActionListener() // �޺��ڽ� ���� �� �̺�Ʈ ��� ���� �׼Ǹ����� ����
		{  
			public void actionPerformed(ActionEvent e) // �׼� �̺�Ʈ �߻��� ó���ϵ��� �޼ҵ� �������̵�
			{ 
				JComboBox cb = (JComboBox)e.getSource(); // getSource�޼ҵ�� �̺�Ʈ�� �߻��� �ε����� Object������ ��ȯ�ϱ� ������ �޺��ڽ� Ÿ������ Cast
				int index = cb.getSelectedIndex(); // getSelectedIndex�޼ҵ�ν� �޺��ڽ��� ���õ� �������� �ε��� ��ȣ �˾Ƴ���
				if(index == 1) // ���� �ε����� 1�̶��
				{ 
					is_pm = true; // is_pm�� true�� �Ҵ����ְ�
				}
				else // �׷��� �ʴٸ�
				{
					is_pm = false; // is_pm�� false���� ��������
				}
			}   
				});
		c1.setBackground(Color.WHITE);
		add(c1);

		/* �ð� ���� ��ư */
		set_alarm = new JButton("�ð�");
		set_alarm.setBounds(420, 50, 70, 40);
		set_alarm.setBackground(Color.WHITE);
		set_alarm.addActionListener(this);
		add(set_alarm);

		/* �˶� ���� ��ư */
		set_timeSetting = new JButton("�˶�");
		set_timeSetting.setBounds(500, 50, 70, 40);
		set_timeSetting.setBackground(Color.WHITE);
		set_timeSetting.addActionListener(this);
		add(set_timeSetting);

		/* �˶� ������ �̿��ϴ� ���� �ð��� paint�ϴ� label */
		label2 = new JLabel();
		label2.setOpaque(true);
		label2.setFont(new Font("����", Font.BOLD, 18));
		label2.setBackground(Color.BLACK);
		label2.setBackground(Color.WHITE);
		setzoerTime();
		label2.setBounds(25, 110, 540, 40); // (��ǥ x, ��ǥ y, ���� w, ���� h)
		add(label2);
	}


	@Override
	public void actionPerformed(ActionEvent e) 
	{
		String click = e.getActionCommand();
		
		/* ����ڿ��� �Է¹��� ��(��/��/��)�� temp1~temp3 ������ �Է��� */
		temp1 = (Integer)hour.getValue();
		temp2 = (Integer)minute.getValue();
		temp3 = (Integer)second.getValue();
		
		/* ���� ���� ���Ķ�� �ð��� 12�ð��� ���Ѵ�. */
		if (is_pm == true)
		{
			temp1 += 12;
		}

		/* �ð� ��ư�� Ŭ���Ѵٸ�, */
		if (click.equals("�ð�")) 		
		{
			/* �Է¹��� ���� �ϴ� �� �ʷ� ��ȯ�Ѵ�. */
			timeset = (temp1 * 60 * 60) + (temp2 * 60) + temp3;	
			
			/* timesetflag�� �÷��ش�. */
			if (timesetflag == false)
			{
				timesetflag = true;
			}
		}
		/* �˶� ��ư�� Ŭ���Ѵٸ�, */
		else if (click.equals("�˶�")) 	
		{
			/* �Է¹��� ���� �ϴ� �� �ʷ� ��ȯ�Ѵ�. */
			timeset2 = (temp1 * 60 * 60) + (temp2 * 60) + temp3;	
			
			/* �˶��� ���������� ����, ���� �˶��� �������� �ƴ϶�� �˶��� �����ϴ� alarmflag�� �����Ѵ�. */
			if (alarmflag == false && Clock_Main.alarmOK == false)
			{
				alarmflag = true;
				System.out.println("Alarm Start");
			}
			/* �˶��� ������ ���������� �ٸ� �˶��� �������� �ʱ� ���� �����Ѵ�. */
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
		/* �˶��� �����ȴٸ� �˻縦 �Ѵ�. */
		if (alarmflag == true)
		{
			/* ���� �ð��� �˶� ���� �ð��� �������ٸ� */
			if (checkAlarm(temp1, temp2, temp3) == true)
			{
				/* ĸí�ڵ带 ���� ������Ʈ���� �����Ѵ�. */
				String tmp = ""; 

				int i = 0;
				while ( i < 6 ) 
				{
					tmp += Clock_Main.initRandomChar[(int)( Math.random() * Clock_Main.initRandomChar.length ) ];
					i++;
				}

				/* ����ڰ� ��Ȯ�� ĸí�ڵ� ���� �Է��ϱ� ������ �˶��� ������ �ʴ´�. */
				while (true)
				{
					String msg = JOptionPane.showInputDialog("ĸ���ڵ带 �Է��ϼ���\n" + tmp);

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
				/* �˶��� �����Ǿ�����, ���� ������ ���� ��� ���� �ð��� paint�Ѵ�. */
				setRemainTime();
			}
		}
		else
		{
			/* �˶��� �������� ������� ���� �ð��� ��� 0���� �����Ѵ�. */
			setzoerTime();
		}
	}
	
	/* �˶��� �������� ���� ��� 0�� ��� */
	public void setzoerTime()
	{
		label2.setOpaque(true);
		label2.setText("�˶� �����ð�        0        0        0");
	}
	
	/* �˶��� �����Ǿ����� ���� �˶� �ð��� ���� ���� ���, ���� �ð��� ����Ѵ�. */
	public void setRemainTime()
	{
		int hours = calAlarmTime / 3600;
		int minute = (calAlarmTime % 3600) / 60;
		int second = (calAlarmTime % 3600) % 60;
		
		label2.setOpaque(true);
		label2.setText("�˶� �����ð�        " + hours + "        " + minute + "        " + second);
	}

	/* ���� �ð��� ����ڰ� �Է��� �˶� �ð��� üũ�ϴ� �޼ҵ� */
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