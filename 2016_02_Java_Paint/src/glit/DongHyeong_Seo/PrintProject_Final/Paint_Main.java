package glit.DongHyeong_Seo.PrintProject_Final;

import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTabbedPane;
import javax.swing.KeyStroke;
import javax.swing.border.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *  전체 그림판의 프레임을 나타내는 클래스이다.
 *  이 클래스에서는 캔버스와 텝에 대한 기본적인 설정을 한다.
 *  추가로 메뉴바 구성 및 그에 맞는 단축키 설정을 한다.
 */
@SuppressWarnings("serial")
public class Paint_Main extends JFrame implements ItemListener, ChangeListener, ActionListener, MouseListener
{
	private JMenu File, Edit, Shape, Help;
	private JMenuItem MenuNew, MenuCut, MenuPaste, MenuCopy, MenuSelect, MenuPen, MenuEraser,
					  MenuBrush, MenuLine, MenuRectangle, MenuOval, MenuRoundRect, MenuMadeBy, MenuExit,
					  MenuOpen, MenuSaveAs, MenuPentagon, MenuHexagon, MenuTriangle, MenuHeptagon, MenuOctagon;
	public JButton New, Open, SaveAs, Cut, Paste, Copy, Select, Text, Pen, Eraser, Heptagon, Octagon,
    			   Brush, Line, Oval, RoundRect, Triangle, Rectangle, Pentagon, Hexagon, Polyline;
	public JButton Black, White, Blue, Brown, Gold, Gray, Gray2, Green, Lime, Orange, Pink, Red, Red2,
				   Sky, Violet, Wine, Yellow, Yellow2, SetColor;
	
	private JMenuBar menuBar;
	private JTabbedPane tabPanel;
	private JPanel homePanel, FilePanel, filePanel, clipboardPanel, toolPanel, shapePanel, typePanel, colorPanel, selectColor;
	private JCheckBox fill, dottedLine;
	private JSlider slider;
	
	/* 전체 프레임 크기 설정을 위한 상수 */
	public final static int FRAMEHEIGHT = 800;
	public final static int FRAMEWIDTH = 750;				
	/* 캔버스 높이 설정을 위한 상수 */
	public final static int CANVASHEIGHT = 650;							
	/* 텝 높이 설정을 위한 상수 */
	public final static int TABHEIGHT = 150;							
	/* 텝 배경색(Window7 그림판 텝 배경색) 설정을 위한 색상수 */
	public final static Color TABCOLOR = new Color(225, 235, 245);		
	/* 캔버스 배경색(흰색) 설정을 위한 색상수 */
	public final static Color CANVASCOLOR = new Color(255, 255, 255);	
	
	/* Home Tab의 Panel의 너비를 조절하기 위한 변수*/
	public int panelwidth = 7;
	/* contentPanel 선언 */
	public Container contentPane;
	/* CanvasPanel의 메소드를 사용하기 위해 선언 */
	private CanvasPanel canvasPanel;
	
	/* jar file을 만들때 이미지를 로드하기 위함 */
	private ClassLoader cl = this.getClass().getClassLoader();
 	
	/* 현재 설정된 컬러를 검정색(black)으로 지정  */
 	public static Color color = Color.black;
 	/* 기본설정된 파일이름을 저장한 String(Untitled.jpg) */
	public static String filename;
	/* 기본으로 설정된 만든이 이름을 저장한 String(Unknown) */
	public static String makername;
	
	/**
	 *  기본적인 UI를 위한 init 메소드
	 */
	private void initUI()
	{
		/* (0, 0)에서 (FRAMEWIDTH, TABHEIGHT)까지 tabPanel 설정 */
		tabPanel.setBounds(0, 0, FRAMEWIDTH, TABHEIGHT);			
		/* 테두리 설정 */
		tabPanel.setBorder(BorderFactory.createRaisedBevelBorder());
		/* TaPanel의 Background color 설정 */
		tabPanel.setBackground(TABCOLOR);									
		/* tabPanel 진입시 손모양 마우스로 커서변경 */
		tabPanel.setCursor(new Cursor(Cursor.HAND_CURSOR)); 				
		
		/* (0, TABHEIGHT)에서 (FRAMEWIDTH, CANVASHEIGHT)까지 canvasPanel 설정 */
		canvasPanel.setBounds(0, TABHEIGHT, FRAMEWIDTH, CANVASHEIGHT);
		/* canvasPanel의 Background color 설정 */
		canvasPanel.setBackground(CANVASCOLOR);								
		/* canvasPanel 진입시 크로스 마우스로 커서변경 */
		canvasPanel.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR)); 		
		
		/* tabPanel, canvasPanel, statusBar을 컨텐트팬에 배치 */
		contentPane.add(tabPanel);
		contentPane.add(canvasPanel);
	}
	
	/**
	 *  메뉴바를 생성하는 메소드
	 */
	private void createMenu()
	{
		menuBar= new JMenuBar();
		
		/* 메뉴바를 File, Edit, Shape, Help 4가지로 구성 */
		menuBar.add(File);											
		menuBar.add(Edit);
		menuBar.add(Shape);
		menuBar.add(Help);
		
		/* 메뉴바의 상세 메뉴 설정 및 단축키 설정을 위한 메소드 */
		setJMenuBar(menuBar);										
	}
	
	/**
	 *  메뉴바의 상세 메뉴 설정 및 단축키 설정을 위한 메소드
	 *  텍스트, 스포이드, 글씨크기, 글씨타입은 메뉴에는 구현하지 않고 툴바에서 직접 설정하도록 함.
	 */
	private void settingMenu()
	{
		File = new JMenu("File(F)");	/* File 메뉴 설정 */
		File.setMnemonic('f');			/* File 메뉴의 단축키 설정(ALT + F) */
		
		/* File의 새로만들기 메뉴 설정(이미지, 단축키(CTRL + N), ActionListener 추가, File 메뉴바에 추가) */
		MenuNew = new JMenuItem("새로 만들기(N)", new ImageIcon(cl.getResource("img/New.jpg")));
		MenuNew.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
		MenuNew.addActionListener(this);
		File.add(MenuNew);
																	
		/* File의 열기 메뉴 설정(이미지, 단축키(CTRL + O), ActionListener 추가, File 메뉴바에 추가) */
		MenuOpen = new JMenuItem("열기(O)", new ImageIcon(cl.getResource("img/Open.jpg")));
		MenuOpen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
		MenuOpen.addActionListener(this);
		File.add(MenuOpen);
		
		/* File의 저장 메뉴 설정(이미지, 단축키(CTRL + S), ActionListener 추가, File 메뉴바에 추가) */
		MenuSaveAs = new JMenuItem("저장(S)", new ImageIcon(cl.getResource("img/SaveAs.jpg")));
		MenuSaveAs.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
		MenuSaveAs.addActionListener(this);
		File.add(MenuSaveAs);
		
		/* File의 종료 메뉴 설정(이미지, 단축키(ALT + F4), ActionListener 추가, File 메뉴바에 추가) */
		MenuExit = new JMenuItem("종료", new ImageIcon(cl.getResource("img/Exit.jpg")));
		MenuExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, ActionEvent.ALT_MASK));
		MenuExit.addActionListener(this);
		File.add(MenuExit);
		
		Edit = new JMenu("Edit(E)");		/* Edit 메뉴 설정 */
		Edit.setMnemonic('e');				/* File 메뉴의 단축키 설정(ALT + E) */

		/* Edit의 선택하기 메뉴 설정(이미지, 단축키(CTRL + T), ActionListener 추가, Edit 메뉴바에 추가) */
		MenuSelect = new JMenuItem("선택하기(T)", new ImageIcon(cl.getResource("img/Select.jpg")));
		MenuSelect.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, ActionEvent.CTRL_MASK));
		MenuSelect.addActionListener(this);		
		Edit.add(MenuSelect);

		/* Edit의 잘라내기 메뉴 설정(이미지, 단축키(CTRL + X), ActionListener 추가, Edit 메뉴바에 추가) */
		MenuCut = new JMenuItem("잘라내기(X)", new ImageIcon(cl.getResource("img/Cut.jpg")));
		MenuCut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));
		MenuCut.addActionListener(this);		
		Edit.add(MenuCut);

		/* Edit의 복사하기 메뉴 설정(이미지, 단축키(CTRL + C), ActionListener 추가, Edit 메뉴바에 추가) */
		MenuCopy = new JMenuItem("복사하기(C)", new ImageIcon(cl.getResource("img/Copy.jpg")));
		MenuCopy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));
		MenuCopy.addActionListener(this);		
		Edit.add(MenuCopy);

		/* Edit의 붙여넣기 메뉴 설정(이미지, 단축키(CTRL + V), ActionListener 추가, Edit 메뉴바에 추가) */
		MenuPaste = new JMenuItem("붙여넣기(V)", new ImageIcon(cl.getResource("img/Paste.jpg")));
		MenuPaste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK));
		MenuPaste.addActionListener(this);		
		Edit.add(MenuPaste);
		
		Shape = new JMenu("Shape(S)");		/* Shape 메뉴 설정 */	
		Shape.setMnemonic('s');				/* Shape 메뉴의 단축키 설정(ALT + S) */

		/* Shape의 연필(자유곡선) 메뉴 설정(이미지, 단축키(ALT + P), ActionListener 추가, Shape 메뉴바에 추가) */
		MenuPen = new JMenuItem("연필(P)", new ImageIcon(cl.getResource("img/Pen.jpg")));
		MenuPen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.ALT_MASK));
		MenuPen.addActionListener(this);		
		Shape.add(MenuPen);

		/* Shape의 브러시(자유곡선) 메뉴 설정(이미지, 단축키(ALT + B), ActionListener 추가, Shape 메뉴바에 추가) */
		MenuBrush = new JMenuItem("브러시(B)", new ImageIcon(cl.getResource("img/Brush.jpg")));
		MenuBrush.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, ActionEvent.ALT_MASK));
		MenuBrush.addActionListener(this);		
		Shape.add(MenuBrush);
		
		/* Shape의 지우개 메뉴 설정(이미지, 단축키(ALT + E), ActionListener 추가, Shape 메뉴바에 추가) */
		MenuEraser = new JMenuItem("지우개(E)", new ImageIcon(cl.getResource("img/Eraser.jpg")));
		MenuEraser.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.ALT_MASK));
		MenuEraser.addActionListener(this);		
		Shape.add(MenuEraser);

		/* Shape의 선그리기 메뉴 설정(이미지, 단축키(ALT + L), ActionListener 추가, Shape 메뉴바에 추가) */
		MenuLine = new JMenuItem("선그리기(L)", new ImageIcon(cl.getResource("img/Line.jpg")));
		MenuLine.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, ActionEvent.ALT_MASK));
		MenuLine.addActionListener(this);		
		Shape.add(MenuLine);
		
		/* Shape의 원그리기 메뉴 설정(이미지, 단축키(ALT + C), ActionListener 추가, Shape 메뉴바에 추가) */
		MenuOval = new JMenuItem("원그리기(C)", new ImageIcon(cl.getResource("img/Oval.jpg")));
		MenuOval.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.ALT_MASK));
		MenuOval.addActionListener(this);	
		Shape.add(MenuOval);

		/* Shape의 둥근사각형 메뉴 설정(이미지, 단축키(ALT + O), ActionListener 추가, Shape 메뉴바에 추가) */
		MenuRoundRect = new JMenuItem("둥근사각형(O)", new ImageIcon(cl.getResource("img/RoundRect.jpg")));
		MenuRoundRect.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.ALT_MASK));
		MenuRoundRect.addActionListener(this);	
		Shape.add(MenuRoundRect);
		
		/* Shape의 삼각형 메뉴 설정(이미지, 단축키(ALT + I), ActionListener 추가, Shape 메뉴바에 추가) */
		MenuTriangle = new JMenuItem("삼각형(I)", new ImageIcon(cl.getResource("img/Triangle.jpg")));
		MenuTriangle.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, ActionEvent.ALT_MASK));
		MenuTriangle.addActionListener(this);	
		Shape.add(MenuTriangle);
		
		/* Shape의 사각형 메뉴 설정(이미지, 단축키(ALT + R), ActionListener 추가, Shape 메뉴바에 추가) */
		MenuRectangle = new JMenuItem("사각형(R)", new ImageIcon(cl.getResource("img/Rect.jpg")));
		MenuRectangle.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.ALT_MASK));
		MenuRectangle.addActionListener(this);	
		Shape.add(MenuRectangle);
		
		/* Shape의 오각형 메뉴 설정(이미지, 단축키(ALT + A), ActionListener 추가, Shape 메뉴바에 추가) */
		MenuPentagon = new JMenuItem("오각형(A)", new ImageIcon(cl.getResource("img/Pentagon.jpg")));
		MenuPentagon.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.ALT_MASK));
		MenuPentagon.addActionListener(this);	
		Shape.add(MenuPentagon);
		
		/* Shape의 육각형 메뉴 설정(이미지, 단축키(ALT + H), ActionListener 추가, Shape 메뉴바에 추가) */
		MenuHexagon = new JMenuItem("육각형(H)", new ImageIcon(cl.getResource("img/Hexagon.jpg")));
		MenuHexagon.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, ActionEvent.ALT_MASK));
		MenuHexagon.addActionListener(this);	
		Shape.add(MenuHexagon);
		
		/* Shape의 칠각형 메뉴 설정(이미지, 단축키(ALT + U), ActionListener 추가, Shape 메뉴바에 추가) */
		MenuHeptagon = new JMenuItem("칠각형(U)", new ImageIcon(cl.getResource("img/Heptagon.jpg")));
		MenuHeptagon.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U, ActionEvent.ALT_MASK));
		MenuHeptagon.addActionListener(this);	
		Shape.add(MenuHeptagon);
		
		/* Shape의 팔각형 메뉴 설정(이미지, 단축키(ALT + G), ActionListener 추가, Shape 메뉴바에 추가) */
		MenuOctagon = new JMenuItem("팔각형(O)", new ImageIcon(cl.getResource("img/Octagon.jpg")));
		MenuOctagon.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, ActionEvent.ALT_MASK));
		MenuOctagon.addActionListener(this);	
		Shape.add(MenuOctagon);		
		
		Help = new JMenu("Help(H)");			/* Help 메뉴 설정 */	
		Help.setMnemonic('h');					/* Help 메뉴의 단축키 설정(ALT + H) */

		/* Help의 텍스트 메뉴 설정(이미지, ActionListener 추가, Help 메뉴바에 추가), 단축키 구성 안함 */
		MenuMadeBy = new JMenuItem("만든이", new ImageIcon(cl.getResource("img/Who.jpg")));
		MenuMadeBy.addActionListener(this);	
		Help.add(MenuMadeBy);
	}

	/**
	 *  텝을 생성하는 메소드
	 *  Home Tab과 File Tab을 각각 초기화 및 설정한다.
	 */
	private void settingTab()
	{
		/* HomeTab과 FileTab을 초기화하는 메소드 */
		initHomeTab();
		initFileTab();
	}
	
	/**
	 *  Home Tab을 구성하는 메소드
	 */
	private void initHomeTab()
	{
		homePanel.setBackground(TABCOLOR);
		homePanel.setLayout(null);
		
		clipboardPanel = new JPanel();
		clipboardPanel.setBounds(panelwidth, 0, 135, (TABHEIGHT - 35));					
		clipboardPanel.setBorder(new TitledBorder("ClipBoard")); 				/* 테두리 설정 */
		clipboardPanel.setBackground(TABCOLOR);									/* TaPanel의 Background color 설정 */
		clipboardPanel.setCursor(new Cursor(Cursor.HAND_CURSOR)); 				/* tabPanel 진입시 손모양 마우스로 커서변경 */
		makeclipboard();
		homePanel.add(clipboardPanel);
		
		panelwidth += 147;
		
		toolPanel = new JPanel();
		toolPanel.setBounds(panelwidth, 0, 50, (TABHEIGHT - 35));					
		toolPanel.setBorder(new TitledBorder("Tools")); 					/* 테두리 설정 */
		toolPanel.setBackground(TABCOLOR);									/* TaPanel의 Background color 설정 */
		toolPanel.setCursor(new Cursor(Cursor.HAND_CURSOR)); 				/* tabPanel 진입시 손모양 마우스로 커서변경 */
		maketool();
		homePanel.add(toolPanel);
		
		panelwidth += 57;
		
		shapePanel = new JPanel();
		shapePanel.setBounds(panelwidth, 0, 105, (TABHEIGHT - 35));					
		shapePanel.setBorder(new TitledBorder("Shape")); 		/* 테두리 설정 */
		shapePanel.setBackground(TABCOLOR);									/* TaPanel의 Background color 설정 */
		shapePanel.setCursor(new Cursor(Cursor.HAND_CURSOR)); 				/* tabPanel 진입시 손모양 마우스로 커서변경 */
		makeshape();
		homePanel.add(shapePanel);
		
		panelwidth += 117;
		
		typePanel = new JPanel();
		typePanel.setBounds(panelwidth, 0, 95, (TABHEIGHT - 35));					
		typePanel.setBorder(new TitledBorder("Setting")); 		/* 테두리 설정 */
		typePanel.setBackground(TABCOLOR);									/* TaPanel의 Background color 설정 */
		typePanel.setCursor(new Cursor(Cursor.HAND_CURSOR)); 				/* tabPanel 진입시 손모양 마우스로 커서변경 */
		maketype();
		homePanel.add(typePanel);
		
		panelwidth += 107;
		
		colorPanel = new JPanel();
		colorPanel.setBounds(panelwidth, 0, 295, (TABHEIGHT - 35));					
		colorPanel.setBorder(new TitledBorder("Color")); 		/* 테두리 설정 */
		colorPanel.setBackground(TABCOLOR);									/* TaPanel의 Background color 설정 */
		colorPanel.setCursor(new Cursor(Cursor.HAND_CURSOR)); 				/* tabPanel 진입시 손모양 마우스로 커서변경 */
		makecolor();
		homePanel.add(colorPanel);
	}
	
	/**
	 *  Home Tab안에 있는 Color 섹션의 위치, 크기 등을 정하는 메소드
	 */
	private void makecolor()
	{
		/* 배치관리자 제거, 버튼 등의 요소들의 위치를 수동으로 잡아 주기 위함*/
		colorPanel.setLayout(null);
		
		/* colorPanel에 (10, 20)에 25x25 크기의 Black 버튼 생성 */
		Black = new JButton(new ImageIcon(cl.getResource("color/Black.jpg")));
		Black.setToolTipText("검정색");
		Black.setBounds(10, 20, 25, 25);
		Black.setBackground(TABCOLOR);
		Black.addMouseListener(this);
		colorPanel.add(Black);
		
		/* colorPanel에 (10, 50)에 25x25 크기의 Gray 버튼 생성 */
		Gray = new JButton(new ImageIcon(cl.getResource("color/Gray.jpg")));
		Gray.setToolTipText("회색");
		Gray.setBounds(10, 50, 25, 25);
		Gray.setBackground(TABCOLOR);
		Gray.addMouseListener(this);
		colorPanel.add(Gray);
		
		/* colorPanel에 (10, 80)에 25x25 크기의 White 버튼 생성 */
		White = new JButton(new ImageIcon(cl.getResource("color/White.jpg")));
		White.setToolTipText("하얀색");
		White.setBounds(10, 80, 25, 25);
		White.setBackground(TABCOLOR);
		White.addMouseListener(this);
		colorPanel.add(White);
		
		/* colorPanel에 (40, 20)에 25x25 크기의 Brown 버튼 생성 */
		Brown = new JButton(new ImageIcon(cl.getResource("color/Brown.jpg")));
		Brown.setToolTipText("갈색");
		Brown.setBounds(40, 20, 25, 25);
		Brown.setBackground(TABCOLOR);
		Brown.addMouseListener(this);
		colorPanel.add(Brown);
		
		/* colorPanel에 (40, 50)에 25x25 크기의 Orange 버튼 생성 */
		Orange = new JButton(new ImageIcon(cl.getResource("color/Orange.jpg")));
		Orange.setToolTipText("오랜지색");
		Orange.setBounds(40, 50, 25, 25);
		Orange.setBackground(TABCOLOR);
		Orange.addMouseListener(this);
		colorPanel.add(Orange);
		
		/* colorPanel에 (40, 80)에 25x25 크기의 Gray2 버튼 생성 */
		Gray2 = new JButton(new ImageIcon(cl.getResource("color/Gray2.jpg")));
		Gray2.setToolTipText("연한회색");
		Gray2.setBounds(40, 80, 25, 25);
		Gray2.setBackground(TABCOLOR);
		Gray2.addMouseListener(this);
		colorPanel.add(Gray2);
		
		/* colorPanel에 (70, 20)에 25x25 크기의 Red2 버튼 생성 */
		Red2 = new JButton(new ImageIcon(cl.getResource("color/Red2.jpg")));
		Red2.setToolTipText("진한빨강색");
		Red2.setBounds(70, 20, 25, 25);
		Red2.setBackground(TABCOLOR);
		Red2.addMouseListener(this);
		colorPanel.add(Red2);
		
		/* colorPanel에 (70, 50)에 25x25 크기의 Red 버튼 생성 */
		Red = new JButton(new ImageIcon(cl.getResource("color/Red.jpg")));
		Red.setToolTipText("빨강색");
		Red.setBounds(70, 50, 25, 25);
		Red.setBackground(TABCOLOR);
		Red.addMouseListener(this);
		colorPanel.add(Red);
		
		/* colorPanel에 (70, 80)에 25x25 크기의 Pink 버튼 생성 */
		Pink = new JButton(new ImageIcon(cl.getResource("color/Pink.jpg")));
		Pink.setToolTipText("핑크색");
		Pink.setBounds(70, 80, 25, 25);
		Pink.setBackground(TABCOLOR);
		Pink.addMouseListener(this);
		colorPanel.add(Pink);
		
		/* colorPanel에 (100, 20)에 25x25 크기의 Pink 버튼 생성 */
		Green = new JButton(new ImageIcon(cl.getResource("color/Green.jpg")));
		Green.setToolTipText("초록색");
		Green.setBounds(100, 20, 25, 25);
		Green.setBackground(TABCOLOR);
		Green.addMouseListener(this);
		colorPanel.add(Green);

		/* colorPanel에 (100, 50)에 25x25 크기의 Lime 버튼 생성 */
		Lime = new JButton(new ImageIcon(cl.getResource("color/Lime.jpg")));
		Lime.setToolTipText("라임색");
		Lime.setBounds(100, 50, 25, 25);
		Lime.setBackground(TABCOLOR);
		Lime.addMouseListener(this);
		colorPanel.add(Lime);
		
		/* colorPanel에 (100, 80)에 25x25 크기의 Yellow2 버튼 생성 */
		Yellow2 = new JButton(new ImageIcon(cl.getResource("color/Yellow2.jpg")));
		Yellow2.setToolTipText("연한노란색");
		Yellow2.setBounds(100, 80, 25, 25);
		Yellow2.setBackground(TABCOLOR);
		Yellow2.addMouseListener(this);
		colorPanel.add(Yellow2);
		
		/* colorPanel에 (130, 20)에 25x25 크기의 Blue 버튼 생성 */
		Blue = new JButton(new ImageIcon(cl.getResource("color/Blue.jpg")));
		Blue.setToolTipText("파란색");
		Blue.setBounds(130, 20, 25, 25);
		Blue.setBackground(TABCOLOR);
		Blue.addMouseListener(this);
		colorPanel.add(Blue);
		
		/* colorPanel에 (130, 50)에 25x25 크기의 Blue 버튼 생성 */
		Sky = new JButton(new ImageIcon(cl.getResource("color/Sky.jpg")));
		Sky.setToolTipText("하늘색");
		Sky.setBounds(130, 50, 25, 25);
		Sky.setBackground(TABCOLOR);
		Sky.addMouseListener(this);
		colorPanel.add(Sky);
		
		/* colorPanel에 (130, 80)에 25x25 크기의 Yellow 버튼 생성 */
		Yellow = new JButton(new ImageIcon(cl.getResource("color/Yellow.jpg")));
		Yellow.setToolTipText("노란색");
		Yellow.setBounds(130, 80, 25, 25);
		Yellow.setBackground(TABCOLOR);
		Yellow.addMouseListener(this);
		colorPanel.add(Yellow);
		
		/* colorPanel에 (160, 20)에 25x25 크기의 Violet 버튼 생성 */
		Violet = new JButton(new ImageIcon(cl.getResource("color/Violet.jpg")));
		Violet.setToolTipText("보라색");
		Violet.setBounds(160, 20, 25, 25);
		Violet.setBackground(TABCOLOR);
		Violet.addMouseListener(this);
		colorPanel.add(Violet);

		/* colorPanel에 (160, 50)에 25x25 크기의 Wine 버튼 생성 */
		Wine = new JButton(new ImageIcon(cl.getResource("color/Wine.jpg")));
		Wine.setToolTipText("와인색");
		Wine.setBounds(160, 50, 25, 25);
		Wine.setBackground(TABCOLOR);
		Wine.addMouseListener(this);
		colorPanel.add(Wine);
		
		/* colorPanel에 (160, 80)에 25x25 크기의 Gold 버튼 생성 */
		Gold = new JButton(new ImageIcon(cl.getResource("color/Gold.jpg")));
		Gold.setToolTipText("금색");
		Gold.setBounds(160, 80, 25, 25);
		Gold.setBackground(TABCOLOR);
		Gold.addMouseListener(this);
		colorPanel.add(Gold);
		
		/* colorPanel에 (200, 20)에 83x35 크기의 selectColor 버튼 생성 */
		selectColor = new JPanel();
		selectColor.setBorder(BorderFactory.createLoweredBevelBorder());	// 테두리 설정
		selectColor.setBounds(200, 20, 83, 35);
		selectColor.setBackground(Color.black);
		selectColor.setToolTipText("선택된 색상");
		colorPanel.add(selectColor);
		
		/* colorPanel에 (200, 60)에 83x35 크기의 SetColor 버튼 생성 */
		SetColor = new JButton(new ImageIcon(cl.getResource("color/Color.jpg")));
		SetColor.setToolTipText("색선택");
		SetColor.setBounds(200, 60, 83, 45);
		SetColor.setBackground(TABCOLOR);
		SetColor.addMouseListener(this);
		colorPanel.add(SetColor);
	}
	
	/**
	 *  Home Tab안에 있는 Setting 섹션의 위치, 크기 등을 정하는 메소드
	 */
	@SuppressWarnings("static-access")
	private void maketype()
	{
		/* 배치관리자 제거, 버튼 등의 요소들의 위치를 수동으로 잡아 주기 위함*/
		typePanel.setLayout(null);
		
		/* typePanel에 (10, 20)에 75x25 크기의 fill 체크버튼 생성
		 * 도형(선 제외)을 그릴 때 채워서 그릴지 여부를 정함 */
		fill = new JCheckBox("채우기");
		fill.setBorderPainted(true);
		fill.setBounds(10, 20, 75, 25);
		fill.addItemListener(this);
		typePanel.add(fill);
		
		/* typePanel에 (10, 50)에 75x25 크기의 dottedLine 체크버튼 생성
		 * 도형(선 포함)을 그릴 때 점선으로 그릴지 여부를 정함 */
		dottedLine = new JCheckBox("점선");
		dottedLine.setBorderPainted(true);
		dottedLine.setBounds(10, 50, 75, 25);
		dottedLine.addItemListener(this);
		typePanel.add(dottedLine);
		
		/* typePanel에 (10, 80)에 75x25 크기의 slider 슬라이더 생성
		 * 도형(선 포함)을 그릴 때 선의 크기(1 ~ 9)를 정함
		 * 눈금자는 2마다 적고, 슬라이더는 1단위로 움직일 수 있다. */
		slider = new JSlider(JSlider.HORIZONTAL, 1, 9, 1);
		slider.setBounds(10, 80, 75, 25);
		slider.setPaintLabels(true);
		slider.setMajorTickSpacing(2);
		slider.setMinorTickSpacing(1);
		slider.addChangeListener(this);
		canvasPanel.thicknessValue = 1;
		typePanel.add(slider);
	}
	
	/**
	 *  Home Tab안에 있는 Setting 섹션의 위치, 크기 등을 정하는 메소드
	 */
	private void makeshape()
	{
		/* 배치관리자 제거, 버튼 등의 요소들의 위치를 수동으로 잡아 주기 위함*/
		shapePanel.setLayout(null);
		
		/* shapePanel에 (10, 20)에 25x25 크기의 Line 버튼 생성 */
		Line = new JButton(new ImageIcon(cl.getResource("img/Line.jpg")));
		Line.setToolTipText("직선");
		Line.setBounds(10, 20, 25, 25);
		Line.setBackground(TABCOLOR);
		Line.addMouseListener(this);
		shapePanel.add(Line);
		
		/* shapePanel에 (40, 20)에 25x25 크기의 Oval 버튼 생성 */
		Oval = new JButton(new ImageIcon(cl.getResource("img/Oval.jpg")));
		Oval.setToolTipText("원");
		Oval.setBounds(40, 20, 25, 25);
		Oval.setBackground(TABCOLOR);
		Oval.addMouseListener(this);
		shapePanel.add(Oval);
		
		/* shapePanel에 (70, 20)에 25x25 크기의 RoundRect 버튼 생성 */
		RoundRect = new JButton(new ImageIcon(cl.getResource("img/RoundRect.jpg")));
		RoundRect.setToolTipText("둥근사각형");
		RoundRect.setBounds(70, 20, 25, 25);
		RoundRect.setBackground(TABCOLOR);
		RoundRect.addMouseListener(this);
		shapePanel.add(RoundRect);
		
		/* shapePanel에 (10, 50)에 25x25 크기의 Triangle 버튼 생성 */
		Triangle = new JButton(new ImageIcon(cl.getResource("img/Triangle.jpg")));
		Triangle.setToolTipText("삼각형");
		Triangle.setBounds(10, 50, 25, 25);
		Triangle.setBackground(TABCOLOR);
		Triangle.addMouseListener(this);
		shapePanel.add(Triangle);
		
		/* shapePanel에 (40, 50)에 25x25 크기의 Rectangle 버튼 생성 */
		Rectangle = new JButton(new ImageIcon(cl.getResource("img/Rect.jpg")));
		Rectangle.setToolTipText("사각형");
		Rectangle.setBounds(40, 50, 25, 25);
		Rectangle.setBackground(TABCOLOR);
		Rectangle.addMouseListener(this);
		shapePanel.add(Rectangle);
		
		/* shapePanel에 (70, 50)에 25x25 크기의 Pentagon 버튼 생성 */
		Pentagon = new JButton(new ImageIcon(cl.getResource("img/Pentagon.jpg")));
		Pentagon.setToolTipText("오각형");
		Pentagon.setBounds(70, 50, 25, 25);
		Pentagon.setBackground(TABCOLOR);
		Pentagon.addMouseListener(this);
		shapePanel.add(Pentagon);
		
		/* shapePanel에 (10, 80)에 25x25 크기의 Hexagon 버튼 생성 */
		Hexagon = new JButton(new ImageIcon(cl.getResource("img/Hexagon.jpg")));
		Hexagon.setToolTipText("육각형");
		Hexagon.setBounds(10, 80, 25, 25);
		Hexagon.setBackground(TABCOLOR);
		Hexagon.addMouseListener(this);
		shapePanel.add(Hexagon);
		
		/* shapePanel에 (40, 80)에 25x25 크기의 Heptagon 버튼 생성 */
		Heptagon = new JButton(new ImageIcon(cl.getResource("img/Heptagon.jpg")));
		Heptagon.setToolTipText("칠각형");
		Heptagon.setBounds(40, 80, 25, 25);
		Heptagon.setBackground(TABCOLOR);
		Heptagon.addMouseListener(this);
		shapePanel.add(Heptagon);

		/* shapePanel에 (70, 80)에 25x25 크기의 Octagon 버튼 생성 */
		Octagon = new JButton(new ImageIcon(cl.getResource("img/Octagon.jpg")));
		Octagon.setToolTipText("팔각형");
		Octagon.setBounds(70, 80, 25, 25);
		Octagon.setBackground(TABCOLOR);
		Octagon.addMouseListener(this);
		shapePanel.add(Octagon);
	}
	
	/**
	 *  Home Tab안에 있는 Tools 섹션의 위치, 크기 등을 정하는 메소드
	 */
	private void maketool()
	{
		/* 배치관리자 제거, 버튼 등의 요소들의 위치를 수동으로 잡아 주기 위함*/
		toolPanel.setLayout(null);
		
		/* toolPanel에 (13, 20)에 25x25 크기의 Pen 버튼 생성 */
		Pen = new JButton(new ImageIcon(cl.getResource("img/Pen.jpg")));
		Pen.setToolTipText("연필");
		Pen.setBounds(13, 20, 25, 25);
		Pen.setBackground(TABCOLOR);
		Pen.addMouseListener(this);
		toolPanel.add(Pen);
		
		/* toolPanel에 (13, 50)에 25x25 크기의 Eraser 버튼 생성 */
		Eraser = new JButton(new ImageIcon(cl.getResource("img/Eraser.jpg")));
		Eraser.setToolTipText("지우개");
		Eraser.setBounds(13, 50, 25, 25);
		Eraser.setBackground(TABCOLOR);
		Eraser.addMouseListener(this);
		toolPanel.add(Eraser);
		
		/* toolPanel에 (13, 80)에 25x25 크기의 Brush 버튼 생성 */
		Brush = new JButton(new ImageIcon(cl.getResource("img/Brush.jpg")));
		Brush.setToolTipText("브러쉬");
		Brush.setBounds(13, 80, 25, 25);
		Brush.setBackground(TABCOLOR);
		Brush.addMouseListener(this);
		toolPanel.add(Brush);
	}
	
	/**
	 *  Home Tab안에 있는 Clipboard 섹션의 위치, 크기 등을 정하는 메소드
	 */	
	private void makeclipboard()
	{
		/* 배치관리자 제거, 버튼 등의 요소들의 위치를 수동으로 잡아 주기 위함*/
		clipboardPanel.setLayout(null);
		
		/* clipboardPanel에 (10, 20)에 85x85 크기의 Select 버튼 생성 */
		Select = new JButton(new ImageIcon(cl.getResource("img/Select2.jpg")));
		Select.setToolTipText("선택");
		Select.setBounds(10, 20, 85, 85);
		Select.setBackground(TABCOLOR);
		Select.addMouseListener(this);
		clipboardPanel.add(Select);
		
		/* clipboardPanel에 (100, 20)에 25x25 크기의 Copy 버튼 생성 */
		Copy = new JButton(new ImageIcon(cl.getResource("img/Copy.jpg")));
		Copy.setToolTipText("복사");
		Copy.setBounds(100, 20, 25, 25);
		Copy.setBackground(TABCOLOR);
		Copy.addMouseListener(this);
		clipboardPanel.add(Copy);
		
		/* clipboardPanel에 (100, 50)에 25x25 크기의 Cut 버튼 생성 */
		Cut = new JButton(new ImageIcon(cl.getResource("img/Cut.jpg")));
		Cut.setToolTipText("잘라내기");
		Cut.setBounds(100, 50, 25, 25);
		Cut.setBackground(TABCOLOR);
		Cut.addMouseListener(this);
		clipboardPanel.add(Cut);
		
		/* clipboardPanel에 (100, 80)에 25x25 크기의 Paste 버튼 생성 */
		Paste = new JButton(new ImageIcon(cl.getResource("img/Paste.jpg")));
		Paste.setToolTipText("붙여넣기");
		Paste.setBounds(100, 80, 25, 25);
		Paste.setBackground(TABCOLOR);
		Paste.addMouseListener(this);
		clipboardPanel.add(Paste);
	}
	
	/**
	 *  File Tab안에 있는 File 섹션의 위치, 크기 등을 정하는 메소드
	 */	
	private void makefile()
	{
		/* 배치관리자 제거, 버튼 등의 요소들의 위치를 수동으로 잡아 주기 위함*/
		filePanel.setLayout(null);
		
		/* filePanel에 (10, 10)에 85x85 크기의 New 버튼 생성 */
		New = new JButton(new ImageIcon(cl.getResource("img/New2.jpg")));
		New.setToolTipText("새로만들기");
		New.setBounds(10, 20, 85, 85);
		New.setBackground(Color.WHITE);
		New.addMouseListener(this);
		filePanel.add(New);
		
		/* filePanel에 (100, 20)에 85x85 크기의 Open 버튼 생성 */
		Open = new JButton(new ImageIcon(cl.getResource("img/Open2.jpg")));
		Open.setToolTipText("열기");
		Open.setBounds(100, 20, 85, 85);
		Open.setBackground(Color.WHITE);
		Open.addMouseListener(this);
		filePanel.add(Open);
		
		/* filePanel에 (190, 20)에 85x85 크기의 SaveAs 버튼 생성 */
		SaveAs = new JButton(new ImageIcon(cl.getResource("img/SaveAs2.jpg")));
		SaveAs.setToolTipText("저장");
		SaveAs.setBounds(190, 20, 85, 85);
		SaveAs.setBackground(Color.WHITE);
		SaveAs.addMouseListener(this);
		filePanel.add(SaveAs);
	}
	
	/**
	 *  File Tab을 구성하는 메소드
	 */
	private void initFileTab()
	{
		/* FilePanel의 배경색 설정 */
		FilePanel.setBackground(TABCOLOR);
		/* 배치관리자 제거, 버튼 등의 요소들의 위치를 수동으로 잡아 주기 위함 */
		FilePanel.setLayout(null);
		
		filePanel = new JPanel();
		/* filePanel의 위치를 (0,0)부터 285x(TABHEIGHT - 35) 크기로 설정 */
		filePanel.setBounds(0, 0, 285, (TABHEIGHT - 35));	
		/* Border의 Title을 File로 설정 */
		filePanel.setBorder(new TitledBorder("File")); 		
		/* TaPanel의 Background color 설정 */
		filePanel.setBackground(TABCOLOR);									
		/* tabPanel 진입시 손모양 마우스로 커서변경 */
		filePanel.setCursor(new Cursor(Cursor.HAND_CURSOR)); 
		/* file을 설정 */
		makefile();
		
		/* filePanel에 filePanel을 붙여줌 */
		FilePanel.add(filePanel);
	}
	
	/**
	 *  Tab을 생성하고, Home, File 총 2개의 Tab을 붙인다.
	 */
	private void createTab()
	{
		/* tabPanel을 생성 */
		tabPanel = new JTabbedPane(); 
		
		/* tabPanel에 붙여줄 homePanel, FilePanel 생성 */
		homePanel = new JPanel();
		FilePanel = new JPanel();
		
		/* tabPanel에 homePanel, FilePanel 붙여줌 */
		tabPanel.addTab("Home", homePanel);
		tabPanel.addTab("File", FilePanel);
	}

	/**
	 *  CheckBox에 체크 여부를 확인해 채우기와 점선을 설정하는 리스너
	 */
	@SuppressWarnings("static-access")
	public void itemStateChanged(ItemEvent e) 
	{
		/* 발생한 이벤트 메뉴 반환 */
		JCheckBox input = (JCheckBox)e.getItem();
		
		/* fill 체크박스에 변화가 있다면, 변화를 canvasPanel의 변수 fillMode에 저장 */
		if (input == fill)
		{
			if (canvasPanel.fillMode == 0)
			{
				canvasPanel.fillMode = 1;
			}
			else if (canvasPanel.fillMode == 1)
			{
				canvasPanel.fillMode = 0;
			}
		}
		/* dottedLine 체크박스에 변화가 있다면, 변화를 canvasPanel의 변수 isdot에 저장 */
		else if(input == dottedLine)
		{
			if (canvasPanel.isdot == 0)
			{
				canvasPanel.isdot = 1;
			}
			else if (canvasPanel.isdot == 1)
			{
				canvasPanel.isdot = 0;
			}
		}
	}
	
	/**
	 *  슬라이더 값에 변화가 있다면 변화를 읽고 그 값을 두께에 적용하는 리스너
	 */
	@SuppressWarnings("static-access")
	public void stateChanged(ChangeEvent e) 
	{
		/* 슬라이더 값의 변화를 인지해 변화를 canvasPanel의 변수 thicknessValue에 저장 */
		canvasPanel.thicknessValue = slider.getValue();
	}
	
	/**
	 *  유저에 의해 버튼이 선택된 경우, 그에 해당하는 행동을 설정하기 위한 메소드
	 */
	@SuppressWarnings("static-access")
	public void mousePressed(MouseEvent e)
	{
		/* 발생한 이벤트 메뉴 반환 */
		JButton input = (JButton)e.getSource();
	
		/* 유저가 새로만들기를 선택한 경우, */
		if (input.equals(New))
		{
			/* 이름 초기화 */
			filename="Untitled.jpg"; 								
			makername = "Unknown";
			
			/* 현재 작업 내용의 저장 여부를 유저에게 묻고 새로 시작 수행 */
			JLabel msg = new JLabel();
			msg.setText("현재 내용을 저장하겠습니까?");
			
			if (JOptionPane.showConfirmDialog(null, msg, "확인",
				JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) != JOptionPane.YES_OPTION) 
			{	/* 유저가 저장을 원하지 않는 경우 프레임타이틀 변경 */
				setTitle(filename + " - " + makername);
				canvasPanel.creation();
			}
			else 													
			{	/* 유저가 저장을 원하는 경우 그림 파일을 저장하고 새로만든다 */
				canvasPanel.reNameSave(); 
				canvasPanel.creation();
			}
		}
		/* 유저가 열기를 선택한 경우, */
		else if(input.equals(Open))
		{
			/* 이름 초기화 */
			filename="Untitled.jpg"; 								
			makername = "Unknown";
			
			/* 현재 작업 내용의 저장 여부를 유저에게 묻고 열기 수행 */
			JLabel msg = new JLabel();       
			msg.setText("현재 내용을 저장하겠습니까?");					
			
			if (JOptionPane.showConfirmDialog(null, msg, "확인",	
				JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION) 
			{	/* 유저가 저장을 원하는 경우 그림 파일을 프레임타이틀 변경한다 */
				canvasPanel.reNameSave(); 	
				filename = canvasPanel.open();
				setTitle(filename + " - " + makername);
			}
			else
			{	/* 아닌경우 저장하지 않고 열기 */
				filename = canvasPanel.open();
				setTitle(filename + " - " + makername);
			}
		}
		/* 유저가 저장을 선택한 경우, */
		else if(input.equals(SaveAs))			
		{	/* 저장 후 프레임타이틀 변경 */
			canvasPanel.reNameSave();
			setTitle(filename + " - " + makername);
		}
		/* 유저가 선택을 선택한 경우, */
		else if(input.equals(Select))
		{
			/* canvasPanel의 select 변수를 1(선택모드)로 설정. setDrawMode에 설정된 값에 따라 설정후 기능 수행 */
			canvasPanel.select=1;
		}
		/* 유저가 복사를 선택한 경우, */
		else if(input.equals(Copy))
		{	/* canvasPanel의 copy()메소드에 접근 */
			canvasPanel.copy();
		}
		/* 유저가 잘라내기를 선택한 경우, */
		else if(input.equals(Cut))
		{	/* canvasPanel의 cut()메소드에 접근 */
			canvasPanel.cut();
		}
		/* 유저가 붙여넣기를 선택한 경우, */
		else if(input.equals(Paste))
		{	/* canvasPanel의 Paste()메소드에 접근 */
			canvasPanel.paste();
		}
		/* 유저가 연필(자유곡선)를 선택한 경우, CanvasPanel의 PEN */
		else if(input.equals(Pen))
		{
			/* 추가로 처음 프로그램 실행 시 설정되는 기본설정 그리기 모드임 */
			canvasPanel.select=0;
			canvasPanel.setDrawMode(CanvasPanel.PEN);
		}
		/* 유저가 지우개를 선택한 경우, CanvasPanel의 ERASER 선택*/
		else if(input.equals(Eraser))
		{
			canvasPanel.select=0;
			canvasPanel.setDrawMode(CanvasPanel.ERASER);
		}
		/* 유저가 브러시를 선택한 경우, CanvasPanel의 BRUSH 선택*/
		else if(input.equals(Brush))
		{
			canvasPanel.select=0;
			canvasPanel.setDrawMode(CanvasPanel.BRUSH);
		}
		/* 유저가 선그리기(직선)를 선택한 경우, CanvasPanel의 LINE 선택*/
		else if(input.equals(Line))
		{
			canvasPanel.select=0;
			canvasPanel.setDrawMode(CanvasPanel.LINE);
		}
		/* 유저가 원그리기를 선택한 경우, CanvasPanel의 OVAL 선택*/
		else if(input.equals(Oval))
		{
			canvasPanel.select=0;
			canvasPanel.setDrawMode(CanvasPanel.OVAL);
		}
		/* 유저가 원그리기를 선택한 경우, CanvasPanel의 ROUNDRECT 선택*/
		else if(input.equals(RoundRect))
		{
			canvasPanel.select=0;
			canvasPanel.setDrawMode(CanvasPanel.ROUNDRECT);
		}
		/* 유저가 삼각형을 선택한 경우, CanvasPanel의 TRIANGLE 선택*/
		else if(input.equals(Triangle))	
		{
			canvasPanel.select=0;
			canvasPanel.setDrawMode(CanvasPanel.TRIANGLE);
		}
		/* 유저가 사각형을 선택한 경우, CanvasPanel의 RECT 선택*/
		else if(input.equals(Rectangle))
		{
			canvasPanel.select=0;
			canvasPanel.setDrawMode(CanvasPanel.RECT);
		}
		/* 유저가 오각형을 선택한 경우, CanvasPanel의 PENTAGON 선택*/
		else if(input.equals(Pentagon))
		{
			canvasPanel.select=0;
			canvasPanel.setDrawMode(CanvasPanel.PENTAGON);
		}
		/* 유저가 육각형을 선택한 경우, CanvasPanel의 HEXAGON 선택*/
		else if(input.equals(Hexagon))
		{
			canvasPanel.select=0;
			canvasPanel.setDrawMode(CanvasPanel.HEXAGON);
		}
		/* 유저가 칠각형을 선택한 경우, CanvasPanel의 HEXAGON 선택*/
		else if(input.equals(Heptagon))
		{
			canvasPanel.select=0;
			canvasPanel.setDrawMode(CanvasPanel.HEPTAGON);
		}
		/* 유저가 팔각형을 선택한 경우, CanvasPanel의 HEXAGON 선택*/
		else if(input.equals(Octagon))
		{
			canvasPanel.select=0;
			canvasPanel.setDrawMode(CanvasPanel.OCTAGON);
		}
		/* 유저가 검은색 버튼을 클릭한 경우, 해당색 선택 */
		else if (input.equals(Black)) 
		{
			selectColor.setBackground(new Color(0, 0, 0));
			color = new Color(0, 0, 0);
		}
		/* 유저가 흰색 버튼을 클릭한 경우, 해당색 선택 */
		else if (input.equals(White)) 
		{
			selectColor.setBackground(new Color(255,255,255));
			color = new Color(255,255,255);
		}
		/* 유저가 파란색 버튼을 클릭한 경우, 해당색 선택 */
		else if (input.equals(Blue)) 
		{
			selectColor.setBackground(new Color(0,165,232));
			color = new Color(0,165,232);
		}
		/* 유저가 갈색 버튼을 클릭한 경우, 해당색 선택 */
		else if (input.equals(Brown)) 
		{
			selectColor.setBackground(new Color(185,122,87));
			color = new Color(185,122,87);
		}
		/* 유저가 하늘색 버튼을 클릭한 경우, 해당색 선택 */
		else if (input.equals(Sky)) 
		{
			selectColor.setBackground(new Color(154,217,235));
			color = new Color(154,217,235);
		}
		else if (input.equals(Green)) 
		{
			selectColor.setBackground(new Color(35,177,77));
			color = new Color(35,177,77);
		}
		/* 유저가 초록색 버튼을 클릭한 경우, 해당색 선택 */
		else if (input.equals(Lime)) 
		{
			selectColor.setBackground(new Color(181,229,29));
			color = new Color(181,229,29);
		}
		/* 유저가 오랜지색 버튼을 클릭한 경우, 해당색 선택 */
		else if (input.equals(Orange)) 
		{
			selectColor.setBackground(new Color(253,128,36));
			color = new Color(253,128,36);
		}
		/* 유저가 핑크색 버튼을 클릭한 경우, 해당색 선택 */
		else if (input.equals(Pink)) 
		{
			selectColor.setBackground(new Color(253,175,200));
			color = new Color(253,175,200);
		}
		/* 유저가 빨간색 버튼을 클릭한 경우, 해당색 선택 */
		else if (input.equals(Red)) 
		{
			selectColor.setBackground(new Color(237,27,36));
			color=new Color(237,27,36);
		}
		/* 유저가 진한빨간색 버튼을 클릭한 경우, 해당색 선택 */
		else if (input.equals(Red2)) 
		{
			selectColor.setBackground(new Color(136,0,22));
			color = new Color(136,0,22);
		}	
		/* 유저가 노란색 버튼을 클릭한 경우, 해당색 선택 */
		else if (input.equals(Yellow)) 
		{
			selectColor.setBackground(new Color(253,242,0));
			color=new Color(253,242,0);
		}
		/* 유저가 연한노란색 버튼을 클릭한 경우, 해당색 선택 */
		else if (input.equals(Yellow2)) 
		{
			selectColor.setBackground(new Color(238,228,175));
			color = new Color(238,228,175);
		}
		/* 유저가 금색 버튼을 클릭한 경우, 해당색 선택 */
		else if (input.equals(Gold)) 
		{
			selectColor.setBackground(new Color(255,200,10));
			color = new Color(255,200,10);
		}
		/* 유저가 보라색 버튼을 클릭한 경우, 해당색 선택 */
		else if (input.equals(Violet)) 
		{
			selectColor.setBackground(new Color(199,191,232));
			color=new Color(199,191,232);
		}
		/* 유저가 와인색 버튼을 클릭한 경우, 해당색 선택 */
		else if (input.equals(Wine)) 
		{
			selectColor.setBackground(new Color(163,72,167));
			color = new Color(163,72,167);
		}
		/* 유저가 회색 버튼을 클릭한 경우, 해당색 선택 */
		else if (input.equals(Gray)) 
		{
			selectColor.setBackground(new Color(127,127,127));
			color = new Color(127,127,127);
		}
		/* 유저가 연한회색 버튼을 클릭한 경우, 해당색 선택 */
		else if (input.equals(Gray2)) 
		{
			selectColor.setBackground(new Color(195,195,195));
			color = new Color(195,195,195);
		}
		/* 유저가 세부 색 설정을 클릭한 경우, 세부 색 설정창을 띄우고 유저가 선택한 색으로 설정 */
		else if (input.equals(SetColor))
		{
			JColorChooser chooser = new JColorChooser();
		
			Color selectedColor = chooser.showDialog(null, "Color", selectColor.getBackground());
			
			if(selectedColor != null)
			{
				selectColor.setBackground(selectedColor);
				color=selectedColor;
			}
		}
	}
	/* 사용하지 않는 리스너 */
	public void mouseClicked(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}

	/**
	 *  유저에 의해 메뉴바가 선택된 경우, 그에 해당하는 행동을 설정하기 위한 메소드
	 */
	@SuppressWarnings("static-access")
	public void actionPerformed(ActionEvent e)
	{
		/* 발생한 이벤트 메뉴 반환 */
		String col = (String)e.getActionCommand(); 					
	
		/* 유저가 새로만들기를 선택한 경우, */
		if (col.equals("새로 만들기(N)")) 
		{
			/* 이름 초기화 */
			filename="Untitled.jpg"; 								
			makername = "Unknown";
			
			/* 현재 작업 내용의 저장 여부를 유저에게 묻고 새로 시작 수행 */
			JLabel msg = new JLabel();
			msg.setText("현재 내용을 저장하겠습니까?");					
			
			if (JOptionPane.showConfirmDialog(null, msg, "확인",
				JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) != JOptionPane.YES_OPTION) 
			{	/* 유저가 저장을 원하지 않는 경우, 프레임타이틀 변경 */
				setTitle(filename + " - " + makername);	
				canvasPanel.creation();
			}
			else
			{	/* 유저가 저장을 원하는 경우 저장하고 새로만듬 */
				canvasPanel.reNameSave(); 
				canvasPanel.creation();
			}
		}
		/* 유저가 열기를 선택한 경우, */
		else if(col.equals("열기(O)"))
		{
			/* 이름 초기화 */
			filename="Untitled.jpg"; 								
			makername = "Unknown";
			
			/* 현재 작업 내용의 저장 여부를 유저에게 묻고 열기 수행 */
			JLabel msg = new JLabel();       
			msg.setText("현재 내용을 저장하겠습니까?");					
			
			if (JOptionPane.showConfirmDialog(null, msg, "확인",	
				JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION) 
			{	/* 유저가 저장을 원하는 경우, 저장 후 프레임타이틀 변경 */
				canvasPanel.reNameSave();
				filename = canvasPanel.open();
				setTitle(filename + " - " + makername);
			}
			else
			{	/* 원하지 않는 경우 프레임타이틀 변경 */
				filename = canvasPanel.open();
				setTitle(filename + " - " + makername);
			}
		}
		/* 유저가 저장을 선택한 경우, 저장 후 프레임타이틀 변경 */
		else if(col.equals("저장(S)"))
		{
			canvasPanel.reNameSave();
			setTitle(filename + " - " + makername);
		}
		/* 유저가 종료를 선택한 경우, */
		else if(col.equals("종료"))	
		{
			/* 다시한번 사용자에게 종료 확인 */
			JLabel msg = new JLabel();
			msg.setText("종료 하시겠습니까?");
	
			if (JOptionPane.showConfirmDialog(null, msg, "확인",
				JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) != JOptionPane.YES_OPTION)
			{	/* 아니요를 선택한 경우, 종료하지 않고 돌아감 */
				return;												
			}
			System.exit(0);	/* 종료 */
		}
		/* 유저가 선택하기를 선택한 경우, */
		else if(col.equals("선택하기(T)"))
		{
			/* select 1이면 선택모드. setDrawMode에 설정된 값에따라 설정후 기능 수행 */
			canvasPanel.select=1;
		}
		/* 유저가 복사를 선택한 경우, 선택 영역의 이미지를 복사 */
		else if(col.equals("복사하기(C)"))
		{
			canvasPanel.copy();
		}
		/* 유저가 잘라내기를 선택한 경우, 선택 영역의 이미지를 잘라내기 */
		else if(col.equals("잘라내기(X)"))
		{
			canvasPanel.cut();
		}
		/* 유저가 붙여넣기를 선택한 경우, 선택 영역의 이미지를 붙여넣기 */
		else if(col.equals("붙여넣기(V)"))
		{
			canvasPanel.paste();
		}
		/* 유저가 연필(자유곡선) 그리기 선택시, CanvasPanel의 PEN 선택. 처음 실행 시 기본설정임 */
		else if(col.equals("연필(P)"))
		{
			canvasPanel.select=0;
			canvasPanel.setDrawMode(CanvasPanel.PEN);
		}
		/* 유저가 지우개 선택시, CanvasPanel의 ERASER 선택 */
		else if(col.equals("지우개(E)"))
		{
			canvasPanel.select=0;
			canvasPanel.setDrawMode(CanvasPanel.ERASER);
		}
		/* 유저가 브러시 선택시, CanvasPanel의 BRUSH 선택 */
		else if(col.equals("브러시(B)"))
		{
			canvasPanel.select=0;
			canvasPanel.setDrawMode(CanvasPanel.BRUSH);
		}
		/* 유저가 선그리기(직선) 선택시, CanvasPanel의 LINE 선택 */
		else if(col.equals("선그리기(L)"))
		{
			canvasPanel.select=0;
			canvasPanel.setDrawMode(CanvasPanel.LINE);
		}
		/* 유저가 원그리기 선택시, CanvasPanel의 OVAL 선택 */
		else if(col.equals("원그리기(C)"))
		{
			canvasPanel.select=0;
			canvasPanel.setDrawMode(CanvasPanel.OVAL);
		}
		/* 유저가 둥근사각형 선택시, CanvasPanel의 ROUNDRECT 선택 */
		else if(col.equals("둥근사각형(O)"))
		{
			canvasPanel.select=0;
			canvasPanel.setDrawMode(CanvasPanel.ROUNDRECT);
		}
		/* 유저가 삼각형 선택시, CanvasPanel의 TRIANGLE 선택 */
		else if(col.equals("삼각형(I)"))
		{
			canvasPanel.select=0;
			canvasPanel.setDrawMode(CanvasPanel.TRIANGLE);
		}
		/* 유저가 사각형 선택시, CanvasPanel의 RECT 선택 */
		else if(col.equals("사각형(R)"))
		{
			canvasPanel.select=0;
			canvasPanel.setDrawMode(CanvasPanel.RECT);
		}
		/* 유저가 오각형 선택시, CanvasPanel의 PENTAGON 선택 */
		else if(col.equals("오각형(A)"))	
		{
			canvasPanel.select=0;
			canvasPanel.setDrawMode(CanvasPanel.PENTAGON);
		}
		/* 유저가 육각형 선택시, CanvasPanel의 HEXAGON 선택 */
		else if(col.equals("육각형(H)"))
		{
			canvasPanel.select=0;
			canvasPanel.setDrawMode(CanvasPanel.HEXAGON);
		}
		/* 유저가 칠각형 선택시, CanvasPanel의 HEPTAGON 선택 */
		else if(col.equals("칠각형(U)"))
		{
			canvasPanel.select=0;
			canvasPanel.setDrawMode(CanvasPanel.HEPTAGON);
		}
		/* 유저가 팔각형 선택시, CanvasPanel의 OCTAGON 선택 */
		else if(col.equals("팔각형(O)"))
		{
			canvasPanel.select=0;
			canvasPanel.setDrawMode(CanvasPanel.OCTAGON);
		}
		/* 유저가 만든이 선택시, 제작자 기본정보를 보여줌 */
		else if(col.equals("만든이"))									/* 만든이 정보 출력 */
		{
			JOptionPane.showMessageDialog(this, "성공회대학교\n학번: 200934013\n이름: 서동형\n",
										  "성공회대학교", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	public Paint_Main() 
	{
		canvasPanel = new CanvasPanel(filename);
		
		/* 컨텐트팬 받아오기 */
		contentPane = getContentPane();
		/* 배경색 설정 */
		contentPane.setBackground(CANVASCOLOR);
		/* 배치관리자 제거, 버튼 등의 요소들의 위치를 수동으로 잡아 주기 위함 */
		contentPane.setLayout(null);
		
		settingMenu();					/* 프레임의 메뉴를 설정 */
		createMenu();					/* 프레임의 메뉴를 생성 */
		createTab();					/* 프레임의 텝을 설정 */
		settingTab();					/* 프레임의 텝을 생성 */
		initUI();						/* 기본 UI 설정 */
		
		filename="Untitled.jpg"; 
		
		makername = "Unknown";
		setTitle(filename+" - "+makername); 						/* 초기파일이름 설정 */

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(FRAMEWIDTH, FRAMEHEIGHT); 							/* 사이즈 설정 */
		setLocation(200, 60); 										/* 기본 위치설정 */
		setResizable(false);										/* 사이즈변경 불가 설정 */
		setVisible(true);
	}
	
	public static void main(String args[]) 
	{
		new Paint_Main();
	}
}