package glit.DongHyeong_Seo.PrintProject_Final;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
//import java.awt.event.MouseEvent;
import java.util.Vector;

/**
 *  선, 원, 연필, 사각형, 지우개등 각 객체에 대한 정보를 가지고 있는 클래스
 */
public class Information 
{
	private Vector<Point> point = new Vector<Point>();
	private Color color;
	public String pointStr, rgbStr; 
//	private int mousePointX, mousePointY, redColor, greenColor, blueColor;
	
	/* Information 생성자 */
	public Information() {}								
	
	/* 현재 포인트를 저장한다 */
	public void addPoint(Point p) 						
	{
		point.add(p);
	}
	
	/* 현재 컬러를 설정한다 */
	public void setColor(Color c) 						
	{
		color = c;
	}
	
	/* 현재 컬러를 얻어온다 */
	public Color getColor() 							
	{
		return color;				
	}
	
	/* 현재 포인트를 얻어온다 */
	public Vector<Point> getPoint()						
	{
		return point;
	}

	/* 현재 p1, p2 사이의 영역을 반환한다 */
	public Rectangle getRect(Point p1, Point p2) 		
	{
		Rectangle rect = null;

		int minX = Math.min(p1.x, p2.x);
		int maxX = Math.max(p1.x, p2.x);
		int minY = Math.min(p1.y, p2.y);
		int maxY = Math.max(p1.y, p2.y);

		rect = new Rectangle(minX, minY, maxX - minX, maxY - minY);

		/* 인자로 받아온 영역을 계산해 반환한다. */
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
