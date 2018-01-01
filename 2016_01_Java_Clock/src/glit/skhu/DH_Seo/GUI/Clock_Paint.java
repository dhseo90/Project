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
 * 시계 관련된 전체적인 GUI를 구성하는 클래스
 * @author DongHyeong Seo
 *
 */
@SuppressWarnings("serial")
public class Clock_Paint extends JPanel
{
	/* 시계의 각도를 계산하기 위한 상수 */
	private static final int SECONDS_IN_MINUTE 		= 60;
    private static final int SECONDS_IN_HOUR 		= 60 * SECONDS_IN_MINUTE;
    private static final int SECONDS_IN_12_HOURS 	= 12 * SECONDS_IN_HOUR;
    
    /* 순차적인 배경색 변경을 표현하기 위한 구분 시간들 */
	private static final int AM_0_00 = 0;
	private static final int AM_3_00 = (3 * 60);
	private static final int AM_6_00 = (6 * 60);
	private static final int AM_9_00 = (9 * 60);
	private static final int AM_12_00 = (12 * 60);
	private static final int PM_3_00 = (15 * 60);
    private static final int PM_6_30 = (18 * 60) + 30;
    private static final int PM_9_00 = (21 * 60);
    private static final int PM_12_00 = (24 * 60);
    
    /* 배경색을 구현하기 위한 색 설정 */
    private static final Color DARK_GRAY = new Color(32, 32, 32);
    private static final Color DARK_BLUE = new Color(0, 0, 128);
    private static final Color PURPLE = new Color(128, 0, 128);
    private static final Color CYAN = new Color(0, 255, 255);
    private static final Color YELLOW = new Color(225, 225, 0);
    private static final Color LIGHT_BLUE = new Color(128, 128, 255);
    private static final Color SKY_CYAN = new Color(48, 224, 224);
    
    /* 5분단위 숫자를 설정하기 위한 배열 */
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
    
    public Clock_Paint()		// 시계그리는 패널 초기화
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
		
		/* 시계의 현재 시간을 받아온다 */
		int now = Clock_Main.nowtime;
		
		/* 받아온 시계를 시/분/초로 구분한다 */
		hours = now / 3600;
		minute = (now % 3600) / 60;
		second = (now % 3600) % 60;
		
		g2 = (Graphics2D)g;
		/* 그래픽 구현 시 끊어짐 현상을 방지하기 위함 */
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		/* 배경을 설정하는 메소드 */
		paintBackground(hours, minute);
		
		/* 배경색 설정 기준은 초가 아닌 시/분으로만 구성된다. 그 기준점이 되는 시간 temp */
		int temp = (hours * 60) + minute;
		
		ImageIcon img;
		/* 오전 6시 ~ 오후 6시 29분까지 낮 그림 출력 */
		if (temp >= AM_6_00 && temp < PM_6_30)
		{
			img = new ImageIcon(cl.getResource("img/DayTime.jpg"));
		}
		else
		{/* 오후 6시 30분 ~ 새벽 5시 59분까지 밤 그림 출력 */
			img = new ImageIcon(cl.getResource("img/NightTime.jpg"));
		}
		/* 이미지를 출력한다. */
		g2.drawImage(img.getImage(), 0, 0, null);
		
		/* 시계를 paint하는 메소드 */
		paintClockArea();
		
		/* 숫자, 시침, 분침 색 설정 */
        g2.setPaint(Color.WHITE);
        /* 시/분/초침을 paint */
		paintPointers(hours, minute, second);
		/* 5분단위 구분자인 숫자 paint */
		paintNumbers();
		/* 1분단위 구분자인 점 paint */
		paintDots();
 	}
    
    /* 전체 프레임과 시계 프레임의 구분을 하기 위한 메소드 */
    private void paintClockArea() 
    {
        /* 시계 프레임을 구분하는 원 설정 */
        g2.fillOval(width / 2 - radius, height / 2 - radius, radius * 2, radius * 2);
    }
    
    /* 시침, 분침의 각도를 계산하는 메소드 */
    private double pointerRevolutionsToRadians(double angle) 
    {
        return Math.toRadians((450 + angle * -360) % 360.0);
    }

    /* 시침, 분침을 paint하는 메소드 */
    private void paintPointers(int hours, int minute, int second) 
    {
    	/* 현재 시간을 초로 변환 */
    	int seconds = (hours * 60 * 60) + (minute * 60) + second;
    	
    	/* 시/분/초침을 출력하기 위해 각도 계산 */
        hAngle = pointerRevolutionsToRadians(seconds % SECONDS_IN_12_HOURS / (double) SECONDS_IN_12_HOURS);
        mAngle = pointerRevolutionsToRadians(seconds % SECONDS_IN_HOUR / (double) SECONDS_IN_HOUR);
        sAngle = pointerRevolutionsToRadians(seconds % SECONDS_IN_MINUTE / (double) SECONDS_IN_MINUTE);

        /* 시침 paint */
        g2.setStroke(new BasicStroke(7.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2.drawLine(cx, cy, (int) (cx + Math.cos(hAngle) * radius * 0.55), 
        					(int) (cy - Math.sin(hAngle) * radius * 0.55));
        /* 분침 paint */
        g2.setStroke(new BasicStroke(4.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2.drawLine(cx, cy, (int) (cx + Math.cos(mAngle) * radius * 0.75), 
        					(int) (cy - Math.sin(mAngle) * radius * 0.75));
        /* 초침 paint */
        g2.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2.drawLine(cx, cy, (int) (cx + Math.cos(sAngle) * radius * 0.80),
        					(int) (cy - Math.sin(sAngle) * radius * 0.80));
    }

    /* 5분단위의 숫자를 paint하는 메소드 */
    private void paintNumbers() 
    {
    	g2.setFont(new Font("맑은고딕", Font.BOLD, 50));					/* 맑은고딕체, 글자 굵게, 크기 18 */
    	
        for (int i = 1; i <= 12; i++) 
        {
        	double x = 13.0, y = 20.0;
            double angle = pointerRevolutionsToRadians(i / 12.0);
            
            /* 5분단위 숫자 paint */
            if (i < 12)
            {
            	g2.drawString(ROMAN[i], (int)(cx + Math.cos(angle) * radius * 0.88 - x), 
            				  			(int)(cy - Math.sin(angle) * radius * 0.88 + y));
            }
            else
            {
            	/* 12의 경우 미묘하게 자리의 부자연스러운 경우가 많으므로 약간 위치 이동 */
            	g2.drawString(ROMAN[i], (int)(cx + Math.cos(angle) * radius * 0.88 - x - 10),
            							(int) (cy - Math.sin(angle) * radius * 0.88 + y));
            }
        }
    }
    
    /* 분 단위 점 paint하는 메소드 */
    private void paintDots()
    {
    	for (int i = 0; i < 60; i++) 
        {
            double angle = pointerRevolutionsToRadians(i / 60.0);
            
            g2.fillRect((int) (cx + Math.cos(angle) * radius * 0.98), 
            			(int) (cy - Math.sin(angle) * radius * 0.98), 3, 3);
        }
    }
    
    /* 캔버스 전체 바탕화면 색을 시간대별로 그라데이션으로 설정(2분할 되어있음) */
    private void paintBackground(int hours, int minute) 
    {
    	Point2D p1 = new Point2D.Double(width / 2, 0);
        Point2D p2 = new Point2D.Double(width / 2, height);
    	
    	g2.setPaint(new GradientPaint(p1, upperBackgroundColor(hours, minute), p2, lowerBackgroundColor(hours, minute)));
        g2.fillRect(0, 0, width, height);
    }
    
    /* 2분할된 부분 중 윗부분이 각각 기준 시간에 따라 색이 출력된다. */
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

    /* 2분할된 부분 중 아래부분이 각각 기준 시간에 따라 색이 출력된다. */
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
    
    /* color를 설정하기 위한 메소드 */
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