package glit.skhu.DH_Seo.GUI;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Point2D;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import glit.skhu.DH_Seo.function.Alarm;
import glit.skhu.DH_Seo.main.Clock_Main;

/**
 * �ð� ���õ� ��ü���� GUI�� �����ϴ� Ŭ����
 * @author DongHyeong Seo
 *
 */
@SuppressWarnings("serial")
public class Clock_Paint extends JPanel
{
	/* �ð��� ������ ����ϱ� ���� ��� */
	private static final int SECONDS_IN_MINUTE 		= 60;
    private static final int SECONDS_IN_HOUR 		= 60 * SECONDS_IN_MINUTE;
    private static final int SECONDS_IN_12_HOURS 	= 12 * SECONDS_IN_HOUR;
    
    /* �������� ���� ������ ǥ���ϱ� ���� ���� �ð��� */
	private static final int AM_0_00 = 0;
	private static final int AM_3_00 = (3 * 60);
	private static final int AM_6_00 = (6 * 60);
	private static final int AM_9_00 = (9 * 60);
	private static final int AM_12_00 = (12 * 60);
	private static final int PM_3_00 = (15 * 60);
    private static final int PM_6_30 = (18 * 60) + 30;
    private static final int PM_9_00 = (21 * 60);
    private static final int PM_12_00 = (24 * 60);
    
    /* ������ �����ϱ� ���� �� ���� */
    private static final Color DARK_GRAY = new Color(32, 32, 32);
    private static final Color DARK_BLUE = new Color(0, 0, 128);
    private static final Color PURPLE = new Color(128, 0, 128);
    private static final Color CYAN = new Color(0, 255, 255);
    private static final Color YELLOW = new Color(225, 225, 0);
    private static final Color LIGHT_BLUE = new Color(128, 128, 255);
    private static final Color SKY_CYAN = new Color(48, 224, 224);
    
    /* 5�д��� ���ڸ� �����ϱ� ���� �迭 */
    private static final String[] ROMAN 
    	= {"", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
    
    private final int width;
    private final int height;
    private final int radius;
    private Graphics2D g2;
    private final int cx;
    private final int cy;
    public double hAngle;
    public double mAngle;
    public double sAngle;
    public int hours, minute, second;
    
    Alarm alarm;
    
    private ClassLoader cl = this.getClass().getClassLoader();
    
    public Clock_Paint()		// �ð�׸��� �г� �ʱ�ȭ
	{    	
    	width = 600;
        height = 500;
        radius = Math.min(width / 2, height / 2);
        cx = width / 2;
        cy = height / 2;
        
		setSize(width, height);
		setLocation(0, 0);
		setVisible(true);
	}
    
    public void paintComponent(Graphics g)
 	{
		super.paintComponent(g);
		
		alarm = new Alarm();
		
		/* �ð��� ���� �ð��� �޾ƿ´� */
		int now = Clock_Main.nowtime;
		
		/* �޾ƿ� �ð踦 ��/��/�ʷ� �����Ѵ� */
		hours = now / 3600;
		minute = (now % 3600) / 60;
		second = (now % 3600) % 60;
		
		g2 = (Graphics2D)g;
		/* �׷��� ���� �� ������ ������ �����ϱ� ���� */
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		/* ����� �����ϴ� �޼ҵ� */
		paintBackground(hours, minute);
		
		/* ���� ���� ������ �ʰ� �ƴ� ��/�����θ� �����ȴ�. �� �������� �Ǵ� �ð� temp */
		int temp = (hours * 60) + minute;
		
		ImageIcon img;
		/* ���� 6�� ~ ���� 6�� 29�б��� �� �׸� ��� */
		if (temp >= AM_6_00 && temp < PM_6_30)
		{
			img = new ImageIcon(cl.getResource("img/DayTime.jpg"));
		}
		else
		{/* ���� 6�� 30�� ~ ���� 5�� 59�б��� �� �׸� ��� */
			img = new ImageIcon(cl.getResource("img/NightTime.jpg"));
		}
		/* �̹����� ����Ѵ�. */
		g2.drawImage(img.getImage(), 0, 0, null);
		
		/* �ð踦 paint�ϴ� �޼ҵ� */
		paintClockArea();
		
		/* ����, ��ħ, ��ħ �� ���� */
        g2.setPaint(Color.WHITE);
        /* ��/��/��ħ�� paint */
		paintPointers(hours, minute, second);
		/* 5�д��� �������� ���� paint */
		paintNumbers();
		/* 1�д��� �������� �� paint */
		paintDots();
 	}
    
    /* ��ü �����Ӱ� �ð� �������� ������ �ϱ� ���� �޼ҵ� */
    private void paintClockArea() 
    {
        /* �ð� �������� �����ϴ� �� ���� */
        g2.fillOval(width / 2 - radius, height / 2 - radius, radius * 2, radius * 2);
    }
    
    /* ��ħ, ��ħ�� ������ ����ϴ� �޼ҵ� */
    private double pointerRevolutionsToRadians(double angle) 
    {
        return Math.toRadians((450 + angle * -360) % 360.0);
    }

    /* ��ħ, ��ħ�� paint�ϴ� �޼ҵ� */
    private void paintPointers(int hours, int minute, int second) 
    {
    	/* ���� �ð��� �ʷ� ��ȯ */
    	int seconds = (hours * 60 * 60) + (minute * 60) + second;
    	
    	/* ��/��/��ħ�� ����ϱ� ���� ���� ��� */
        hAngle = pointerRevolutionsToRadians(seconds % SECONDS_IN_12_HOURS / (double) SECONDS_IN_12_HOURS);
        mAngle = pointerRevolutionsToRadians(seconds % SECONDS_IN_HOUR / (double) SECONDS_IN_HOUR);
        sAngle = pointerRevolutionsToRadians(seconds % SECONDS_IN_MINUTE / (double) SECONDS_IN_MINUTE);

        /* ��ħ paint */
        g2.setStroke(new BasicStroke(7.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2.drawLine(cx, cy, (int) (cx + Math.cos(hAngle) * radius * 0.55), 
        					(int) (cy - Math.sin(hAngle) * radius * 0.55));
        /* ��ħ paint */
        g2.setStroke(new BasicStroke(4.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2.drawLine(cx, cy, (int) (cx + Math.cos(mAngle) * radius * 0.75), 
        					(int) (cy - Math.sin(mAngle) * radius * 0.75));
        /* ��ħ paint */
        g2.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2.drawLine(cx, cy, (int) (cx + Math.cos(sAngle) * radius * 0.80),
        					(int) (cy - Math.sin(sAngle) * radius * 0.80));
    }

    /* 5�д����� ���ڸ� paint�ϴ� �޼ҵ� */
    private void paintNumbers() 
    {
    	g2.setFont(new Font("�������", Font.BOLD, 50));					/* �������ü, ���� ����, ũ�� 18 */
    	
        for (int i = 1; i <= 12; i++) 
        {
        	double x = 13.0, y = 20.0;
            double angle = pointerRevolutionsToRadians(i / 12.0);
            
            /* 5�д��� ���� paint */
            if (i < 12)
            {
            	g2.drawString(ROMAN[i], (int)(cx + Math.cos(angle) * radius * 0.88 - x), 
            				  			(int)(cy - Math.sin(angle) * radius * 0.88 + y));
            }
            else
            {
            	/* 12�� ��� �̹��ϰ� �ڸ��� ���ڿ������� ��찡 �����Ƿ� �ణ ��ġ �̵� */
            	g2.drawString(ROMAN[i], (int)(cx + Math.cos(angle) * radius * 0.88 - x - 10),
            							(int) (cy - Math.sin(angle) * radius * 0.88 + y));
            }
        }
    }
    
    /* �� ���� �� paint�ϴ� �޼ҵ� */
    private void paintDots()
    {
    	for (int i = 0; i < 60; i++) 
        {
            double angle = pointerRevolutionsToRadians(i / 60.0);
            
            g2.fillRect((int) (cx + Math.cos(angle) * radius * 0.98), 
            			(int) (cy - Math.sin(angle) * radius * 0.98), 3, 3);
        }
    }
    
    /* ĵ���� ��ü ����ȭ�� ���� �ð��뺰�� �׶��̼����� ����(2���� �Ǿ�����) */
    private void paintBackground(int hours, int minute) 
    {
    	Point2D p1 = new Point2D.Double(width / 2, 0);
        Point2D p2 = new Point2D.Double(width / 2, height);
    	
    	g2.setPaint(new GradientPaint(p1, upperBackgroundColor(hours, minute), p2, lowerBackgroundColor(hours, minute)));
        g2.fillRect(0, 0, width, height);
    }
    
    /* 2���ҵ� �κ� �� ���κ��� ���� ���� �ð��� ���� ���� ��µȴ�. */
    private Color upperBackgroundColor(int hours, int minute) 
    {
    	int minutes = (hours * 60) + minute;
    	
        if (minutes < 0) throw new IllegalArgumentException();
        if (minutes <= AM_3_00) return DARK_GRAY;
        if (minutes <= AM_6_00) return mixColors(DARK_GRAY, DARK_BLUE, AM_3_00, AM_6_00, minutes);
        if (minutes <= AM_9_00) return mixColors(LIGHT_BLUE, CYAN, AM_6_00, AM_9_00, minutes);
        if (minutes <= AM_12_00) return SKY_CYAN;
        if (minutes <= PM_3_00) return SKY_CYAN;
        if (minutes <= PM_6_30) return mixColors(CYAN, LIGHT_BLUE, PM_3_00, PM_6_30, minutes);
        if (minutes <= PM_9_00) return mixColors(DARK_BLUE, DARK_GRAY, PM_6_30, PM_9_00, minutes);
        if (minutes <= PM_12_00) return DARK_GRAY;
        if (minutes > PM_12_00) return DARK_GRAY;
        throw new IllegalArgumentException();
    }

    /* 2���ҵ� �κ� �� �Ʒ��κ��� ���� ���� �ð��� ���� ���� ��µȴ�. */
    private Color lowerBackgroundColor(int hours, int minute) 
    {
    	int minutes = (hours * 60) + minute;
    	
    	if (minutes < 0) throw new IllegalArgumentException();
        if (minutes <= AM_3_00) return mixColors(DARK_GRAY, DARK_BLUE, AM_0_00, AM_3_00, minutes);
        if (minutes <= AM_6_00) return mixColors(DARK_BLUE, PURPLE, AM_3_00, AM_6_00, minutes);
        if (minutes <= AM_9_00) return mixColors(PURPLE, YELLOW, AM_6_00, AM_9_00, minutes);
        if (minutes <= AM_12_00) return mixColors(YELLOW, CYAN, AM_9_00, AM_12_00, minutes);
        if (minutes <= PM_3_00) return mixColors(CYAN, YELLOW, AM_12_00, PM_3_00, minutes);
        if (minutes <= PM_6_30) return mixColors(YELLOW, PURPLE, PM_3_00, PM_6_30, minutes);
        if (minutes <= PM_9_00) return mixColors(PURPLE, DARK_BLUE, PM_6_30, PM_9_00, minutes);
        if (minutes <= PM_12_00) return mixColors(DARK_BLUE, DARK_GRAY, PM_9_00, PM_12_00, minutes);
        if (minutes > PM_12_00) return mixColors(DARK_BLUE, DARK_GRAY, PM_9_00, PM_12_00, minutes);
        throw new IllegalArgumentException();
    }
    
    /* color�� �����ϱ� ���� �޼ҵ� */
    private int mixColorComponent(int startComponent, int endComponent, double position) 
    {
        int difference = endComponent - startComponent;
        return startComponent + (int) (difference * position);
    }

    private Color mixColors(Color startColor, Color endColor, int startTime, int endTime, int currentTime) 
    {
        double normalized = (currentTime - startTime) / (double) (endTime - startTime);
        return new Color(
                mixColorComponent(startColor.getRed(), endColor.getRed(), normalized),
                mixColorComponent(startColor.getGreen(), endColor.getGreen(), normalized),
                mixColorComponent(startColor.getBlue(), endColor.getBlue(), normalized));
    }
}