package glit.DongHyeong_Seo.PrintProject_Final;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
//import java.awt.event.MouseEvent;
import java.util.Vector;

/**
 *  ��, ��, ����, �簢��, ���찳�� �� ��ü�� ���� ������ ������ �ִ� Ŭ����
 */
public class Information 
{
	private Vector<Point> point = new Vector<Point>();
	private Color color;
	public String pointStr, rgbStr; 
//	private int mousePointX, mousePointY, redColor, greenColor, blueColor;
	
	/* Information ������ */
	public Information() {}								
	
	/* ���� ����Ʈ�� �����Ѵ� */
	public void addPoint(Point p) 						
	{
		point.add(p);
	}
	
	/* ���� �÷��� �����Ѵ� */
	public void setColor(Color c) 						
	{
		color = c;
	}
	
	/* ���� �÷��� ���´� */
	public Color getColor() 							
	{
		return color;				
	}
	
	/* ���� ����Ʈ�� ���´� */
	public Vector<Point> getPoint()						
	{
		return point;
	}

	/* ���� p1, p2 ������ ������ ��ȯ�Ѵ� */
	public Rectangle getRect(Point p1, Point p2) 		
	{
		Rectangle rect = null;

		int minX = Math.min(p1.x, p2.x);
		int maxX = Math.max(p1.x, p2.x);
		int minY = Math.min(p1.y, p2.y);
		int maxY = Math.max(p1.y, p2.y);

		rect = new Rectangle(minX, minY, maxX - minX, maxY - minY);

		/* ���ڷ� �޾ƿ� ������ ����� ��ȯ�Ѵ�. */
		return rect;									
	}
	
//	public void mouseMoved(MouseEvent e) 
//	{
//		mousePointX = e.getX();
//		mousePointY = e.getY();
//		setPointStr();
//		setRgbStr();
//	}
//	
//	public String setPointStr()
//	{
//		pointStr = "x : " + mousePointX + ", y : " + mousePointY;
//		return pointStr;
//	}
//
//	public String setRgbStr()
//	{
//		rgbStr = "r : " + redColor + " g : " + greenColor + " b : " + blueColor;
//		return rgbStr;
//	}
}
