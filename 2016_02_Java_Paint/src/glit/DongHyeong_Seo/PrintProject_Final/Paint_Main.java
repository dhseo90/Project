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
 *  ��ü �׸����� �������� ��Ÿ���� Ŭ�����̴�.
 *  �� Ŭ���������� ĵ������ �ܿ� ���� �⺻���� ������ �Ѵ�.
 *  �߰��� �޴��� ���� �� �׿� �´� ����Ű ������ �Ѵ�.
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
	
	/* ��ü ������ ũ�� ������ ���� ��� */
	public final static int FRAMEHEIGHT = 800;
	public final static int FRAMEWIDTH = 750;				
	/* ĵ���� ���� ������ ���� ��� */
	public final static int CANVASHEIGHT = 650;							
	/* �� ���� ������ ���� ��� */
	public final static int TABHEIGHT = 150;							
	/* �� ����(Window7 �׸��� �� ����) ������ ���� ����� */
	public final static Color TABCOLOR = new Color(225, 235, 245);		
	/* ĵ���� ����(���) ������ ���� ����� */
	public final static Color CANVASCOLOR = new Color(255, 255, 255);	
	
	/* Home Tab�� Panel�� �ʺ� �����ϱ� ���� ����*/
	public int panelwidth = 7;
	/* contentPanel ���� */
	public Container contentPane;
	/* CanvasPanel�� �޼ҵ带 ����ϱ� ���� ���� */
	private CanvasPanel canvasPanel;
	
	/* jar file�� ���鶧 �̹����� �ε��ϱ� ���� */
	private ClassLoader cl = this.getClass().getClassLoader();
 	
	/* ���� ������ �÷��� ������(black)���� ����  */
 	public static Color color = Color.black;
 	/* �⺻������ �����̸��� ������ String(Untitled.jpg) */
	public static String filename;
	/* �⺻���� ������ ������ �̸��� ������ String(Unknown) */
	public static String makername;
	
	/**
	 *  �⺻���� UI�� ���� init �޼ҵ�
	 */
	private void initUI()
	{
		/* (0, 0)���� (FRAMEWIDTH, TABHEIGHT)���� tabPanel ���� */
		tabPanel.setBounds(0, 0, FRAMEWIDTH, TABHEIGHT);			
		/* �׵θ� ���� */
		tabPanel.setBorder(BorderFactory.createRaisedBevelBorder());
		/* TaPanel�� Background color ���� */
		tabPanel.setBackground(TABCOLOR);									
		/* tabPanel ���Խ� �ո�� ���콺�� Ŀ������ */
		tabPanel.setCursor(new Cursor(Cursor.HAND_CURSOR)); 				
		
		/* (0, TABHEIGHT)���� (FRAMEWIDTH, CANVASHEIGHT)���� canvasPanel ���� */
		canvasPanel.setBounds(0, TABHEIGHT, FRAMEWIDTH, CANVASHEIGHT);
		/* canvasPanel�� Background color ���� */
		canvasPanel.setBackground(CANVASCOLOR);								
		/* canvasPanel ���Խ� ũ�ν� ���콺�� Ŀ������ */
		canvasPanel.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR)); 		
		
		/* tabPanel, canvasPanel, statusBar�� ����Ʈ�ҿ� ��ġ */
		contentPane.add(tabPanel);
		contentPane.add(canvasPanel);
	}
	
	/**
	 *  �޴��ٸ� �����ϴ� �޼ҵ�
	 */
	private void createMenu()
	{
		menuBar= new JMenuBar();
		
		/* �޴��ٸ� File, Edit, Shape, Help 4������ ���� */
		menuBar.add(File);											
		menuBar.add(Edit);
		menuBar.add(Shape);
		menuBar.add(Help);
		
		/* �޴����� �� �޴� ���� �� ����Ű ������ ���� �޼ҵ� */
		setJMenuBar(menuBar);										
	}
	
	/**
	 *  �޴����� �� �޴� ���� �� ����Ű ������ ���� �޼ҵ�
	 *  �ؽ�Ʈ, �����̵�, �۾�ũ��, �۾�Ÿ���� �޴����� �������� �ʰ� ���ٿ��� ���� �����ϵ��� ��.
	 */
	private void settingMenu()
	{
		File = new JMenu("File(F)");	/* File �޴� ���� */
		File.setMnemonic('f');			/* File �޴��� ����Ű ����(ALT + F) */
		
		/* File�� ���θ���� �޴� ����(�̹���, ����Ű(CTRL + N), ActionListener �߰�, File �޴��ٿ� �߰�) */
		MenuNew = new JMenuItem("���� �����(N)", new ImageIcon(cl.getResource("img/New.jpg")));
		MenuNew.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
		MenuNew.addActionListener(this);
		File.add(MenuNew);
																	
		/* File�� ���� �޴� ����(�̹���, ����Ű(CTRL + O), ActionListener �߰�, File �޴��ٿ� �߰�) */
		MenuOpen = new JMenuItem("����(O)", new ImageIcon(cl.getResource("img/Open.jpg")));
		MenuOpen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
		MenuOpen.addActionListener(this);
		File.add(MenuOpen);
		
		/* File�� ���� �޴� ����(�̹���, ����Ű(CTRL + S), ActionListener �߰�, File �޴��ٿ� �߰�) */
		MenuSaveAs = new JMenuItem("����(S)", new ImageIcon(cl.getResource("img/SaveAs.jpg")));
		MenuSaveAs.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
		MenuSaveAs.addActionListener(this);
		File.add(MenuSaveAs);
		
		/* File�� ���� �޴� ����(�̹���, ����Ű(ALT + F4), ActionListener �߰�, File �޴��ٿ� �߰�) */
		MenuExit = new JMenuItem("����", new ImageIcon(cl.getResource("img/Exit.jpg")));
		MenuExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, ActionEvent.ALT_MASK));
		MenuExit.addActionListener(this);
		File.add(MenuExit);
		
		Edit = new JMenu("Edit(E)");		/* Edit �޴� ���� */
		Edit.setMnemonic('e');				/* File �޴��� ����Ű ����(ALT + E) */

		/* Edit�� �����ϱ� �޴� ����(�̹���, ����Ű(CTRL + T), ActionListener �߰�, Edit �޴��ٿ� �߰�) */
		MenuSelect = new JMenuItem("�����ϱ�(T)", new ImageIcon(cl.getResource("img/Select.jpg")));
		MenuSelect.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, ActionEvent.CTRL_MASK));
		MenuSelect.addActionListener(this);		
		Edit.add(MenuSelect);

		/* Edit�� �߶󳻱� �޴� ����(�̹���, ����Ű(CTRL + X), ActionListener �߰�, Edit �޴��ٿ� �߰�) */
		MenuCut = new JMenuItem("�߶󳻱�(X)", new ImageIcon(cl.getResource("img/Cut.jpg")));
		MenuCut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));
		MenuCut.addActionListener(this);		
		Edit.add(MenuCut);

		/* Edit�� �����ϱ� �޴� ����(�̹���, ����Ű(CTRL + C), ActionListener �߰�, Edit �޴��ٿ� �߰�) */
		MenuCopy = new JMenuItem("�����ϱ�(C)", new ImageIcon(cl.getResource("img/Copy.jpg")));
		MenuCopy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));
		MenuCopy.addActionListener(this);		
		Edit.add(MenuCopy);

		/* Edit�� �ٿ��ֱ� �޴� ����(�̹���, ����Ű(CTRL + V), ActionListener �߰�, Edit �޴��ٿ� �߰�) */
		MenuPaste = new JMenuItem("�ٿ��ֱ�(V)", new ImageIcon(cl.getResource("img/Paste.jpg")));
		MenuPaste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK));
		MenuPaste.addActionListener(this);		
		Edit.add(MenuPaste);
		
		Shape = new JMenu("Shape(S)");		/* Shape �޴� ���� */	
		Shape.setMnemonic('s');				/* Shape �޴��� ����Ű ����(ALT + S) */

		/* Shape�� ����(�����) �޴� ����(�̹���, ����Ű(ALT + P), ActionListener �߰�, Shape �޴��ٿ� �߰�) */
		MenuPen = new JMenuItem("����(P)", new ImageIcon(cl.getResource("img/Pen.jpg")));
		MenuPen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.ALT_MASK));
		MenuPen.addActionListener(this);		
		Shape.add(MenuPen);

		/* Shape�� �귯��(�����) �޴� ����(�̹���, ����Ű(ALT + B), ActionListener �߰�, Shape �޴��ٿ� �߰�) */
		MenuBrush = new JMenuItem("�귯��(B)", new ImageIcon(cl.getResource("img/Brush.jpg")));
		MenuBrush.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, ActionEvent.ALT_MASK));
		MenuBrush.addActionListener(this);		
		Shape.add(MenuBrush);
		
		/* Shape�� ���찳 �޴� ����(�̹���, ����Ű(ALT + E), ActionListener �߰�, Shape �޴��ٿ� �߰�) */
		MenuEraser = new JMenuItem("���찳(E)", new ImageIcon(cl.getResource("img/Eraser.jpg")));
		MenuEraser.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.ALT_MASK));
		MenuEraser.addActionListener(this);		
		Shape.add(MenuEraser);

		/* Shape�� ���׸��� �޴� ����(�̹���, ����Ű(ALT + L), ActionListener �߰�, Shape �޴��ٿ� �߰�) */
		MenuLine = new JMenuItem("���׸���(L)", new ImageIcon(cl.getResource("img/Line.jpg")));
		MenuLine.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, ActionEvent.ALT_MASK));
		MenuLine.addActionListener(this);		
		Shape.add(MenuLine);
		
		/* Shape�� ���׸��� �޴� ����(�̹���, ����Ű(ALT + C), ActionListener �߰�, Shape �޴��ٿ� �߰�) */
		MenuOval = new JMenuItem("���׸���(C)", new ImageIcon(cl.getResource("img/Oval.jpg")));
		MenuOval.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.ALT_MASK));
		MenuOval.addActionListener(this);	
		Shape.add(MenuOval);

		/* Shape�� �ձٻ簢�� �޴� ����(�̹���, ����Ű(ALT + O), ActionListener �߰�, Shape �޴��ٿ� �߰�) */
		MenuRoundRect = new JMenuItem("�ձٻ簢��(O)", new ImageIcon(cl.getResource("img/RoundRect.jpg")));
		MenuRoundRect.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.ALT_MASK));
		MenuRoundRect.addActionListener(this);	
		Shape.add(MenuRoundRect);
		
		/* Shape�� �ﰢ�� �޴� ����(�̹���, ����Ű(ALT + I), ActionListener �߰�, Shape �޴��ٿ� �߰�) */
		MenuTriangle = new JMenuItem("�ﰢ��(I)", new ImageIcon(cl.getResource("img/Triangle.jpg")));
		MenuTriangle.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, ActionEvent.ALT_MASK));
		MenuTriangle.addActionListener(this);	
		Shape.add(MenuTriangle);
		
		/* Shape�� �簢�� �޴� ����(�̹���, ����Ű(ALT + R), ActionListener �߰�, Shape �޴��ٿ� �߰�) */
		MenuRectangle = new JMenuItem("�簢��(R)", new ImageIcon(cl.getResource("img/Rect.jpg")));
		MenuRectangle.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.ALT_MASK));
		MenuRectangle.addActionListener(this);	
		Shape.add(MenuRectangle);
		
		/* Shape�� ������ �޴� ����(�̹���, ����Ű(ALT + A), ActionListener �߰�, Shape �޴��ٿ� �߰�) */
		MenuPentagon = new JMenuItem("������(A)", new ImageIcon(cl.getResource("img/Pentagon.jpg")));
		MenuPentagon.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.ALT_MASK));
		MenuPentagon.addActionListener(this);	
		Shape.add(MenuPentagon);
		
		/* Shape�� ������ �޴� ����(�̹���, ����Ű(ALT + H), ActionListener �߰�, Shape �޴��ٿ� �߰�) */
		MenuHexagon = new JMenuItem("������(H)", new ImageIcon(cl.getResource("img/Hexagon.jpg")));
		MenuHexagon.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, ActionEvent.ALT_MASK));
		MenuHexagon.addActionListener(this);	
		Shape.add(MenuHexagon);
		
		/* Shape�� ĥ���� �޴� ����(�̹���, ����Ű(ALT + U), ActionListener �߰�, Shape �޴��ٿ� �߰�) */
		MenuHeptagon = new JMenuItem("ĥ����(U)", new ImageIcon(cl.getResource("img/Heptagon.jpg")));
		MenuHeptagon.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U, ActionEvent.ALT_MASK));
		MenuHeptagon.addActionListener(this);	
		Shape.add(MenuHeptagon);
		
		/* Shape�� �Ȱ��� �޴� ����(�̹���, ����Ű(ALT + G), ActionListener �߰�, Shape �޴��ٿ� �߰�) */
		MenuOctagon = new JMenuItem("�Ȱ���(O)", new ImageIcon(cl.getResource("img/Octagon.jpg")));
		MenuOctagon.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, ActionEvent.ALT_MASK));
		MenuOctagon.addActionListener(this);	
		Shape.add(MenuOctagon);		
		
		Help = new JMenu("Help(H)");			/* Help �޴� ���� */	
		Help.setMnemonic('h');					/* Help �޴��� ����Ű ����(ALT + H) */

		/* Help�� �ؽ�Ʈ �޴� ����(�̹���, ActionListener �߰�, Help �޴��ٿ� �߰�), ����Ű ���� ���� */
		MenuMadeBy = new JMenuItem("������", new ImageIcon(cl.getResource("img/Who.jpg")));
		MenuMadeBy.addActionListener(this);	
		Help.add(MenuMadeBy);
	}

	/**
	 *  ���� �����ϴ� �޼ҵ�
	 *  Home Tab�� File Tab�� ���� �ʱ�ȭ �� �����Ѵ�.
	 */
	private void settingTab()
	{
		/* HomeTab�� FileTab�� �ʱ�ȭ�ϴ� �޼ҵ� */
		initHomeTab();
		initFileTab();
	}
	
	/**
	 *  Home Tab�� �����ϴ� �޼ҵ�
	 */
	private void initHomeTab()
	{
		homePanel.setBackground(TABCOLOR);
		homePanel.setLayout(null);
		
		clipboardPanel = new JPanel();
		clipboardPanel.setBounds(panelwidth, 0, 135, (TABHEIGHT - 35));					
		clipboardPanel.setBorder(new TitledBorder("ClipBoard")); 				/* �׵θ� ���� */
		clipboardPanel.setBackground(TABCOLOR);									/* TaPanel�� Background color ���� */
		clipboardPanel.setCursor(new Cursor(Cursor.HAND_CURSOR)); 				/* tabPanel ���Խ� �ո�� ���콺�� Ŀ������ */
		makeclipboard();
		homePanel.add(clipboardPanel);
		
		panelwidth += 147;
		
		toolPanel = new JPanel();
		toolPanel.setBounds(panelwidth, 0, 50, (TABHEIGHT - 35));					
		toolPanel.setBorder(new TitledBorder("Tools")); 					/* �׵θ� ���� */
		toolPanel.setBackground(TABCOLOR);									/* TaPanel�� Background color ���� */
		toolPanel.setCursor(new Cursor(Cursor.HAND_CURSOR)); 				/* tabPanel ���Խ� �ո�� ���콺�� Ŀ������ */
		maketool();
		homePanel.add(toolPanel);
		
		panelwidth += 57;
		
		shapePanel = new JPanel();
		shapePanel.setBounds(panelwidth, 0, 105, (TABHEIGHT - 35));					
		shapePanel.setBorder(new TitledBorder("Shape")); 		/* �׵θ� ���� */
		shapePanel.setBackground(TABCOLOR);									/* TaPanel�� Background color ���� */
		shapePanel.setCursor(new Cursor(Cursor.HAND_CURSOR)); 				/* tabPanel ���Խ� �ո�� ���콺�� Ŀ������ */
		makeshape();
		homePanel.add(shapePanel);
		
		panelwidth += 117;
		
		typePanel = new JPanel();
		typePanel.setBounds(panelwidth, 0, 95, (TABHEIGHT - 35));					
		typePanel.setBorder(new TitledBorder("Setting")); 		/* �׵θ� ���� */
		typePanel.setBackground(TABCOLOR);									/* TaPanel�� Background color ���� */
		typePanel.setCursor(new Cursor(Cursor.HAND_CURSOR)); 				/* tabPanel ���Խ� �ո�� ���콺�� Ŀ������ */
		maketype();
		homePanel.add(typePanel);
		
		panelwidth += 107;
		
		colorPanel = new JPanel();
		colorPanel.setBounds(panelwidth, 0, 295, (TABHEIGHT - 35));					
		colorPanel.setBorder(new TitledBorder("Color")); 		/* �׵θ� ���� */
		colorPanel.setBackground(TABCOLOR);									/* TaPanel�� Background color ���� */
		colorPanel.setCursor(new Cursor(Cursor.HAND_CURSOR)); 				/* tabPanel ���Խ� �ո�� ���콺�� Ŀ������ */
		makecolor();
		homePanel.add(colorPanel);
	}
	
	/**
	 *  Home Tab�ȿ� �ִ� Color ������ ��ġ, ũ�� ���� ���ϴ� �޼ҵ�
	 */
	private void makecolor()
	{
		/* ��ġ������ ����, ��ư ���� ��ҵ��� ��ġ�� �������� ��� �ֱ� ����*/
		colorPanel.setLayout(null);
		
		/* colorPanel�� (10, 20)�� 25x25 ũ���� Black ��ư ���� */
		Black = new JButton(new ImageIcon(cl.getResource("color/Black.jpg")));
		Black.setToolTipText("������");
		Black.setBounds(10, 20, 25, 25);
		Black.setBackground(TABCOLOR);
		Black.addMouseListener(this);
		colorPanel.add(Black);
		
		/* colorPanel�� (10, 50)�� 25x25 ũ���� Gray ��ư ���� */
		Gray = new JButton(new ImageIcon(cl.getResource("color/Gray.jpg")));
		Gray.setToolTipText("ȸ��");
		Gray.setBounds(10, 50, 25, 25);
		Gray.setBackground(TABCOLOR);
		Gray.addMouseListener(this);
		colorPanel.add(Gray);
		
		/* colorPanel�� (10, 80)�� 25x25 ũ���� White ��ư ���� */
		White = new JButton(new ImageIcon(cl.getResource("color/White.jpg")));
		White.setToolTipText("�Ͼ��");
		White.setBounds(10, 80, 25, 25);
		White.setBackground(TABCOLOR);
		White.addMouseListener(this);
		colorPanel.add(White);
		
		/* colorPanel�� (40, 20)�� 25x25 ũ���� Brown ��ư ���� */
		Brown = new JButton(new ImageIcon(cl.getResource("color/Brown.jpg")));
		Brown.setToolTipText("����");
		Brown.setBounds(40, 20, 25, 25);
		Brown.setBackground(TABCOLOR);
		Brown.addMouseListener(this);
		colorPanel.add(Brown);
		
		/* colorPanel�� (40, 50)�� 25x25 ũ���� Orange ��ư ���� */
		Orange = new JButton(new ImageIcon(cl.getResource("color/Orange.jpg")));
		Orange.setToolTipText("��������");
		Orange.setBounds(40, 50, 25, 25);
		Orange.setBackground(TABCOLOR);
		Orange.addMouseListener(this);
		colorPanel.add(Orange);
		
		/* colorPanel�� (40, 80)�� 25x25 ũ���� Gray2 ��ư ���� */
		Gray2 = new JButton(new ImageIcon(cl.getResource("color/Gray2.jpg")));
		Gray2.setToolTipText("����ȸ��");
		Gray2.setBounds(40, 80, 25, 25);
		Gray2.setBackground(TABCOLOR);
		Gray2.addMouseListener(this);
		colorPanel.add(Gray2);
		
		/* colorPanel�� (70, 20)�� 25x25 ũ���� Red2 ��ư ���� */
		Red2 = new JButton(new ImageIcon(cl.getResource("color/Red2.jpg")));
		Red2.setToolTipText("���ѻ�����");
		Red2.setBounds(70, 20, 25, 25);
		Red2.setBackground(TABCOLOR);
		Red2.addMouseListener(this);
		colorPanel.add(Red2);
		
		/* colorPanel�� (70, 50)�� 25x25 ũ���� Red ��ư ���� */
		Red = new JButton(new ImageIcon(cl.getResource("color/Red.jpg")));
		Red.setToolTipText("������");
		Red.setBounds(70, 50, 25, 25);
		Red.setBackground(TABCOLOR);
		Red.addMouseListener(this);
		colorPanel.add(Red);
		
		/* colorPanel�� (70, 80)�� 25x25 ũ���� Pink ��ư ���� */
		Pink = new JButton(new ImageIcon(cl.getResource("color/Pink.jpg")));
		Pink.setToolTipText("��ũ��");
		Pink.setBounds(70, 80, 25, 25);
		Pink.setBackground(TABCOLOR);
		Pink.addMouseListener(this);
		colorPanel.add(Pink);
		
		/* colorPanel�� (100, 20)�� 25x25 ũ���� Pink ��ư ���� */
		Green = new JButton(new ImageIcon(cl.getResource("color/Green.jpg")));
		Green.setToolTipText("�ʷϻ�");
		Green.setBounds(100, 20, 25, 25);
		Green.setBackground(TABCOLOR);
		Green.addMouseListener(this);
		colorPanel.add(Green);

		/* colorPanel�� (100, 50)�� 25x25 ũ���� Lime ��ư ���� */
		Lime = new JButton(new ImageIcon(cl.getResource("color/Lime.jpg")));
		Lime.setToolTipText("���ӻ�");
		Lime.setBounds(100, 50, 25, 25);
		Lime.setBackground(TABCOLOR);
		Lime.addMouseListener(this);
		colorPanel.add(Lime);
		
		/* colorPanel�� (100, 80)�� 25x25 ũ���� Yellow2 ��ư ���� */
		Yellow2 = new JButton(new ImageIcon(cl.getResource("color/Yellow2.jpg")));
		Yellow2.setToolTipText("���ѳ����");
		Yellow2.setBounds(100, 80, 25, 25);
		Yellow2.setBackground(TABCOLOR);
		Yellow2.addMouseListener(this);
		colorPanel.add(Yellow2);
		
		/* colorPanel�� (130, 20)�� 25x25 ũ���� Blue ��ư ���� */
		Blue = new JButton(new ImageIcon(cl.getResource("color/Blue.jpg")));
		Blue.setToolTipText("�Ķ���");
		Blue.setBounds(130, 20, 25, 25);
		Blue.setBackground(TABCOLOR);
		Blue.addMouseListener(this);
		colorPanel.add(Blue);
		
		/* colorPanel�� (130, 50)�� 25x25 ũ���� Blue ��ư ���� */
		Sky = new JButton(new ImageIcon(cl.getResource("color/Sky.jpg")));
		Sky.setToolTipText("�ϴû�");
		Sky.setBounds(130, 50, 25, 25);
		Sky.setBackground(TABCOLOR);
		Sky.addMouseListener(this);
		colorPanel.add(Sky);
		
		/* colorPanel�� (130, 80)�� 25x25 ũ���� Yellow ��ư ���� */
		Yellow = new JButton(new ImageIcon(cl.getResource("color/Yellow.jpg")));
		Yellow.setToolTipText("�����");
		Yellow.setBounds(130, 80, 25, 25);
		Yellow.setBackground(TABCOLOR);
		Yellow.addMouseListener(this);
		colorPanel.add(Yellow);
		
		/* colorPanel�� (160, 20)�� 25x25 ũ���� Violet ��ư ���� */
		Violet = new JButton(new ImageIcon(cl.getResource("color/Violet.jpg")));
		Violet.setToolTipText("�����");
		Violet.setBounds(160, 20, 25, 25);
		Violet.setBackground(TABCOLOR);
		Violet.addMouseListener(this);
		colorPanel.add(Violet);

		/* colorPanel�� (160, 50)�� 25x25 ũ���� Wine ��ư ���� */
		Wine = new JButton(new ImageIcon(cl.getResource("color/Wine.jpg")));
		Wine.setToolTipText("���λ�");
		Wine.setBounds(160, 50, 25, 25);
		Wine.setBackground(TABCOLOR);
		Wine.addMouseListener(this);
		colorPanel.add(Wine);
		
		/* colorPanel�� (160, 80)�� 25x25 ũ���� Gold ��ư ���� */
		Gold = new JButton(new ImageIcon(cl.getResource("color/Gold.jpg")));
		Gold.setToolTipText("�ݻ�");
		Gold.setBounds(160, 80, 25, 25);
		Gold.setBackground(TABCOLOR);
		Gold.addMouseListener(this);
		colorPanel.add(Gold);
		
		/* colorPanel�� (200, 20)�� 83x35 ũ���� selectColor ��ư ���� */
		selectColor = new JPanel();
		selectColor.setBorder(BorderFactory.createLoweredBevelBorder());	// �׵θ� ����
		selectColor.setBounds(200, 20, 83, 35);
		selectColor.setBackground(Color.black);
		selectColor.setToolTipText("���õ� ����");
		colorPanel.add(selectColor);
		
		/* colorPanel�� (200, 60)�� 83x35 ũ���� SetColor ��ư ���� */
		SetColor = new JButton(new ImageIcon(cl.getResource("color/Color.jpg")));
		SetColor.setToolTipText("������");
		SetColor.setBounds(200, 60, 83, 45);
		SetColor.setBackground(TABCOLOR);
		SetColor.addMouseListener(this);
		colorPanel.add(SetColor);
	}
	
	/**
	 *  Home Tab�ȿ� �ִ� Setting ������ ��ġ, ũ�� ���� ���ϴ� �޼ҵ�
	 */
	@SuppressWarnings("static-access")
	private void maketype()
	{
		/* ��ġ������ ����, ��ư ���� ��ҵ��� ��ġ�� �������� ��� �ֱ� ����*/
		typePanel.setLayout(null);
		
		/* typePanel�� (10, 20)�� 75x25 ũ���� fill üũ��ư ����
		 * ����(�� ����)�� �׸� �� ä���� �׸��� ���θ� ���� */
		fill = new JCheckBox("ä���");
		fill.setBorderPainted(true);
		fill.setBounds(10, 20, 75, 25);
		fill.addItemListener(this);
		typePanel.add(fill);
		
		/* typePanel�� (10, 50)�� 75x25 ũ���� dottedLine üũ��ư ����
		 * ����(�� ����)�� �׸� �� �������� �׸��� ���θ� ���� */
		dottedLine = new JCheckBox("����");
		dottedLine.setBorderPainted(true);
		dottedLine.setBounds(10, 50, 75, 25);
		dottedLine.addItemListener(this);
		typePanel.add(dottedLine);
		
		/* typePanel�� (10, 80)�� 75x25 ũ���� slider �����̴� ����
		 * ����(�� ����)�� �׸� �� ���� ũ��(1 ~ 9)�� ����
		 * �����ڴ� 2���� ����, �����̴��� 1������ ������ �� �ִ�. */
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
	 *  Home Tab�ȿ� �ִ� Setting ������ ��ġ, ũ�� ���� ���ϴ� �޼ҵ�
	 */
	private void makeshape()
	{
		/* ��ġ������ ����, ��ư ���� ��ҵ��� ��ġ�� �������� ��� �ֱ� ����*/
		shapePanel.setLayout(null);
		
		/* shapePanel�� (10, 20)�� 25x25 ũ���� Line ��ư ���� */
		Line = new JButton(new ImageIcon(cl.getResource("img/Line.jpg")));
		Line.setToolTipText("����");
		Line.setBounds(10, 20, 25, 25);
		Line.setBackground(TABCOLOR);
		Line.addMouseListener(this);
		shapePanel.add(Line);
		
		/* shapePanel�� (40, 20)�� 25x25 ũ���� Oval ��ư ���� */
		Oval = new JButton(new ImageIcon(cl.getResource("img/Oval.jpg")));
		Oval.setToolTipText("��");
		Oval.setBounds(40, 20, 25, 25);
		Oval.setBackground(TABCOLOR);
		Oval.addMouseListener(this);
		shapePanel.add(Oval);
		
		/* shapePanel�� (70, 20)�� 25x25 ũ���� RoundRect ��ư ���� */
		RoundRect = new JButton(new ImageIcon(cl.getResource("img/RoundRect.jpg")));
		RoundRect.setToolTipText("�ձٻ簢��");
		RoundRect.setBounds(70, 20, 25, 25);
		RoundRect.setBackground(TABCOLOR);
		RoundRect.addMouseListener(this);
		shapePanel.add(RoundRect);
		
		/* shapePanel�� (10, 50)�� 25x25 ũ���� Triangle ��ư ���� */
		Triangle = new JButton(new ImageIcon(cl.getResource("img/Triangle.jpg")));
		Triangle.setToolTipText("�ﰢ��");
		Triangle.setBounds(10, 50, 25, 25);
		Triangle.setBackground(TABCOLOR);
		Triangle.addMouseListener(this);
		shapePanel.add(Triangle);
		
		/* shapePanel�� (40, 50)�� 25x25 ũ���� Rectangle ��ư ���� */
		Rectangle = new JButton(new ImageIcon(cl.getResource("img/Rect.jpg")));
		Rectangle.setToolTipText("�簢��");
		Rectangle.setBounds(40, 50, 25, 25);
		Rectangle.setBackground(TABCOLOR);
		Rectangle.addMouseListener(this);
		shapePanel.add(Rectangle);
		
		/* shapePanel�� (70, 50)�� 25x25 ũ���� Pentagon ��ư ���� */
		Pentagon = new JButton(new ImageIcon(cl.getResource("img/Pentagon.jpg")));
		Pentagon.setToolTipText("������");
		Pentagon.setBounds(70, 50, 25, 25);
		Pentagon.setBackground(TABCOLOR);
		Pentagon.addMouseListener(this);
		shapePanel.add(Pentagon);
		
		/* shapePanel�� (10, 80)�� 25x25 ũ���� Hexagon ��ư ���� */
		Hexagon = new JButton(new ImageIcon(cl.getResource("img/Hexagon.jpg")));
		Hexagon.setToolTipText("������");
		Hexagon.setBounds(10, 80, 25, 25);
		Hexagon.setBackground(TABCOLOR);
		Hexagon.addMouseListener(this);
		shapePanel.add(Hexagon);
		
		/* shapePanel�� (40, 80)�� 25x25 ũ���� Heptagon ��ư ���� */
		Heptagon = new JButton(new ImageIcon(cl.getResource("img/Heptagon.jpg")));
		Heptagon.setToolTipText("ĥ����");
		Heptagon.setBounds(40, 80, 25, 25);
		Heptagon.setBackground(TABCOLOR);
		Heptagon.addMouseListener(this);
		shapePanel.add(Heptagon);

		/* shapePanel�� (70, 80)�� 25x25 ũ���� Octagon ��ư ���� */
		Octagon = new JButton(new ImageIcon(cl.getResource("img/Octagon.jpg")));
		Octagon.setToolTipText("�Ȱ���");
		Octagon.setBounds(70, 80, 25, 25);
		Octagon.setBackground(TABCOLOR);
		Octagon.addMouseListener(this);
		shapePanel.add(Octagon);
	}
	
	/**
	 *  Home Tab�ȿ� �ִ� Tools ������ ��ġ, ũ�� ���� ���ϴ� �޼ҵ�
	 */
	private void maketool()
	{
		/* ��ġ������ ����, ��ư ���� ��ҵ��� ��ġ�� �������� ��� �ֱ� ����*/
		toolPanel.setLayout(null);
		
		/* toolPanel�� (13, 20)�� 25x25 ũ���� Pen ��ư ���� */
		Pen = new JButton(new ImageIcon(cl.getResource("img/Pen.jpg")));
		Pen.setToolTipText("����");
		Pen.setBounds(13, 20, 25, 25);
		Pen.setBackground(TABCOLOR);
		Pen.addMouseListener(this);
		toolPanel.add(Pen);
		
		/* toolPanel�� (13, 50)�� 25x25 ũ���� Eraser ��ư ���� */
		Eraser = new JButton(new ImageIcon(cl.getResource("img/Eraser.jpg")));
		Eraser.setToolTipText("���찳");
		Eraser.setBounds(13, 50, 25, 25);
		Eraser.setBackground(TABCOLOR);
		Eraser.addMouseListener(this);
		toolPanel.add(Eraser);
		
		/* toolPanel�� (13, 80)�� 25x25 ũ���� Brush ��ư ���� */
		Brush = new JButton(new ImageIcon(cl.getResource("img/Brush.jpg")));
		Brush.setToolTipText("�귯��");
		Brush.setBounds(13, 80, 25, 25);
		Brush.setBackground(TABCOLOR);
		Brush.addMouseListener(this);
		toolPanel.add(Brush);
	}
	
	/**
	 *  Home Tab�ȿ� �ִ� Clipboard ������ ��ġ, ũ�� ���� ���ϴ� �޼ҵ�
	 */	
	private void makeclipboard()
	{
		/* ��ġ������ ����, ��ư ���� ��ҵ��� ��ġ�� �������� ��� �ֱ� ����*/
		clipboardPanel.setLayout(null);
		
		/* clipboardPanel�� (10, 20)�� 85x85 ũ���� Select ��ư ���� */
		Select = new JButton(new ImageIcon(cl.getResource("img/Select2.jpg")));
		Select.setToolTipText("����");
		Select.setBounds(10, 20, 85, 85);
		Select.setBackground(TABCOLOR);
		Select.addMouseListener(this);
		clipboardPanel.add(Select);
		
		/* clipboardPanel�� (100, 20)�� 25x25 ũ���� Copy ��ư ���� */
		Copy = new JButton(new ImageIcon(cl.getResource("img/Copy.jpg")));
		Copy.setToolTipText("����");
		Copy.setBounds(100, 20, 25, 25);
		Copy.setBackground(TABCOLOR);
		Copy.addMouseListener(this);
		clipboardPanel.add(Copy);
		
		/* clipboardPanel�� (100, 50)�� 25x25 ũ���� Cut ��ư ���� */
		Cut = new JButton(new ImageIcon(cl.getResource("img/Cut.jpg")));
		Cut.setToolTipText("�߶󳻱�");
		Cut.setBounds(100, 50, 25, 25);
		Cut.setBackground(TABCOLOR);
		Cut.addMouseListener(this);
		clipboardPanel.add(Cut);
		
		/* clipboardPanel�� (100, 80)�� 25x25 ũ���� Paste ��ư ���� */
		Paste = new JButton(new ImageIcon(cl.getResource("img/Paste.jpg")));
		Paste.setToolTipText("�ٿ��ֱ�");
		Paste.setBounds(100, 80, 25, 25);
		Paste.setBackground(TABCOLOR);
		Paste.addMouseListener(this);
		clipboardPanel.add(Paste);
	}
	
	/**
	 *  File Tab�ȿ� �ִ� File ������ ��ġ, ũ�� ���� ���ϴ� �޼ҵ�
	 */	
	private void makefile()
	{
		/* ��ġ������ ����, ��ư ���� ��ҵ��� ��ġ�� �������� ��� �ֱ� ����*/
		filePanel.setLayout(null);
		
		/* filePanel�� (10, 10)�� 85x85 ũ���� New ��ư ���� */
		New = new JButton(new ImageIcon(cl.getResource("img/New2.jpg")));
		New.setToolTipText("���θ����");
		New.setBounds(10, 20, 85, 85);
		New.setBackground(Color.WHITE);
		New.addMouseListener(this);
		filePanel.add(New);
		
		/* filePanel�� (100, 20)�� 85x85 ũ���� Open ��ư ���� */
		Open = new JButton(new ImageIcon(cl.getResource("img/Open2.jpg")));
		Open.setToolTipText("����");
		Open.setBounds(100, 20, 85, 85);
		Open.setBackground(Color.WHITE);
		Open.addMouseListener(this);
		filePanel.add(Open);
		
		/* filePanel�� (190, 20)�� 85x85 ũ���� SaveAs ��ư ���� */
		SaveAs = new JButton(new ImageIcon(cl.getResource("img/SaveAs2.jpg")));
		SaveAs.setToolTipText("����");
		SaveAs.setBounds(190, 20, 85, 85);
		SaveAs.setBackground(Color.WHITE);
		SaveAs.addMouseListener(this);
		filePanel.add(SaveAs);
	}
	
	/**
	 *  File Tab�� �����ϴ� �޼ҵ�
	 */
	private void initFileTab()
	{
		/* FilePanel�� ���� ���� */
		FilePanel.setBackground(TABCOLOR);
		/* ��ġ������ ����, ��ư ���� ��ҵ��� ��ġ�� �������� ��� �ֱ� ���� */
		FilePanel.setLayout(null);
		
		filePanel = new JPanel();
		/* filePanel�� ��ġ�� (0,0)���� 285x(TABHEIGHT - 35) ũ��� ���� */
		filePanel.setBounds(0, 0, 285, (TABHEIGHT - 35));	
		/* Border�� Title�� File�� ���� */
		filePanel.setBorder(new TitledBorder("File")); 		
		/* TaPanel�� Background color ���� */
		filePanel.setBackground(TABCOLOR);									
		/* tabPanel ���Խ� �ո�� ���콺�� Ŀ������ */
		filePanel.setCursor(new Cursor(Cursor.HAND_CURSOR)); 
		/* file�� ���� */
		makefile();
		
		/* filePanel�� filePanel�� �ٿ��� */
		FilePanel.add(filePanel);
	}
	
	/**
	 *  Tab�� �����ϰ�, Home, File �� 2���� Tab�� ���δ�.
	 */
	private void createTab()
	{
		/* tabPanel�� ���� */
		tabPanel = new JTabbedPane(); 
		
		/* tabPanel�� �ٿ��� homePanel, FilePanel ���� */
		homePanel = new JPanel();
		FilePanel = new JPanel();
		
		/* tabPanel�� homePanel, FilePanel �ٿ��� */
		tabPanel.addTab("Home", homePanel);
		tabPanel.addTab("File", FilePanel);
	}

	/**
	 *  CheckBox�� üũ ���θ� Ȯ���� ä���� ������ �����ϴ� ������
	 */
	@SuppressWarnings("static-access")
	public void itemStateChanged(ItemEvent e) 
	{
		/* �߻��� �̺�Ʈ �޴� ��ȯ */
		JCheckBox input = (JCheckBox)e.getItem();
		
		/* fill üũ�ڽ��� ��ȭ�� �ִٸ�, ��ȭ�� canvasPanel�� ���� fillMode�� ���� */
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
		/* dottedLine üũ�ڽ��� ��ȭ�� �ִٸ�, ��ȭ�� canvasPanel�� ���� isdot�� ���� */
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
	 *  �����̴� ���� ��ȭ�� �ִٸ� ��ȭ�� �а� �� ���� �β��� �����ϴ� ������
	 */
	@SuppressWarnings("static-access")
	public void stateChanged(ChangeEvent e) 
	{
		/* �����̴� ���� ��ȭ�� ������ ��ȭ�� canvasPanel�� ���� thicknessValue�� ���� */
		canvasPanel.thicknessValue = slider.getValue();
	}
	
	/**
	 *  ������ ���� ��ư�� ���õ� ���, �׿� �ش��ϴ� �ൿ�� �����ϱ� ���� �޼ҵ�
	 */
	@SuppressWarnings("static-access")
	public void mousePressed(MouseEvent e)
	{
		/* �߻��� �̺�Ʈ �޴� ��ȯ */
		JButton input = (JButton)e.getSource();
	
		/* ������ ���θ���⸦ ������ ���, */
		if (input.equals(New))
		{
			/* �̸� �ʱ�ȭ */
			filename="Untitled.jpg"; 								
			makername = "Unknown";
			
			/* ���� �۾� ������ ���� ���θ� �������� ���� ���� ���� ���� */
			JLabel msg = new JLabel();
			msg.setText("���� ������ �����ϰڽ��ϱ�?");
			
			if (JOptionPane.showConfirmDialog(null, msg, "Ȯ��",
				JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) != JOptionPane.YES_OPTION) 
			{	/* ������ ������ ������ �ʴ� ��� ������Ÿ��Ʋ ���� */
				setTitle(filename + " - " + makername);
				canvasPanel.creation();
			}
			else 													
			{	/* ������ ������ ���ϴ� ��� �׸� ������ �����ϰ� ���θ���� */
				canvasPanel.reNameSave(); 
				canvasPanel.creation();
			}
		}
		/* ������ ���⸦ ������ ���, */
		else if(input.equals(Open))
		{
			/* �̸� �ʱ�ȭ */
			filename="Untitled.jpg"; 								
			makername = "Unknown";
			
			/* ���� �۾� ������ ���� ���θ� �������� ���� ���� ���� */
			JLabel msg = new JLabel();       
			msg.setText("���� ������ �����ϰڽ��ϱ�?");					
			
			if (JOptionPane.showConfirmDialog(null, msg, "Ȯ��",	
				JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION) 
			{	/* ������ ������ ���ϴ� ��� �׸� ������ ������Ÿ��Ʋ �����Ѵ� */
				canvasPanel.reNameSave(); 	
				filename = canvasPanel.open();
				setTitle(filename + " - " + makername);
			}
			else
			{	/* �ƴѰ�� �������� �ʰ� ���� */
				filename = canvasPanel.open();
				setTitle(filename + " - " + makername);
			}
		}
		/* ������ ������ ������ ���, */
		else if(input.equals(SaveAs))			
		{	/* ���� �� ������Ÿ��Ʋ ���� */
			canvasPanel.reNameSave();
			setTitle(filename + " - " + makername);
		}
		/* ������ ������ ������ ���, */
		else if(input.equals(Select))
		{
			/* canvasPanel�� select ������ 1(���ø��)�� ����. setDrawMode�� ������ ���� ���� ������ ��� ���� */
			canvasPanel.select=1;
		}
		/* ������ ���縦 ������ ���, */
		else if(input.equals(Copy))
		{	/* canvasPanel�� copy()�޼ҵ忡 ���� */
			canvasPanel.copy();
		}
		/* ������ �߶󳻱⸦ ������ ���, */
		else if(input.equals(Cut))
		{	/* canvasPanel�� cut()�޼ҵ忡 ���� */
			canvasPanel.cut();
		}
		/* ������ �ٿ��ֱ⸦ ������ ���, */
		else if(input.equals(Paste))
		{	/* canvasPanel�� Paste()�޼ҵ忡 ���� */
			canvasPanel.paste();
		}
		/* ������ ����(�����)�� ������ ���, CanvasPanel�� PEN */
		else if(input.equals(Pen))
		{
			/* �߰��� ó�� ���α׷� ���� �� �����Ǵ� �⺻���� �׸��� ����� */
			canvasPanel.select=0;
			canvasPanel.setDrawMode(CanvasPanel.PEN);
		}
		/* ������ ���찳�� ������ ���, CanvasPanel�� ERASER ����*/
		else if(input.equals(Eraser))
		{
			canvasPanel.select=0;
			canvasPanel.setDrawMode(CanvasPanel.ERASER);
		}
		/* ������ �귯�ø� ������ ���, CanvasPanel�� BRUSH ����*/
		else if(input.equals(Brush))
		{
			canvasPanel.select=0;
			canvasPanel.setDrawMode(CanvasPanel.BRUSH);
		}
		/* ������ ���׸���(����)�� ������ ���, CanvasPanel�� LINE ����*/
		else if(input.equals(Line))
		{
			canvasPanel.select=0;
			canvasPanel.setDrawMode(CanvasPanel.LINE);
		}
		/* ������ ���׸��⸦ ������ ���, CanvasPanel�� OVAL ����*/
		else if(input.equals(Oval))
		{
			canvasPanel.select=0;
			canvasPanel.setDrawMode(CanvasPanel.OVAL);
		}
		/* ������ ���׸��⸦ ������ ���, CanvasPanel�� ROUNDRECT ����*/
		else if(input.equals(RoundRect))
		{
			canvasPanel.select=0;
			canvasPanel.setDrawMode(CanvasPanel.ROUNDRECT);
		}
		/* ������ �ﰢ���� ������ ���, CanvasPanel�� TRIANGLE ����*/
		else if(input.equals(Triangle))	
		{
			canvasPanel.select=0;
			canvasPanel.setDrawMode(CanvasPanel.TRIANGLE);
		}
		/* ������ �簢���� ������ ���, CanvasPanel�� RECT ����*/
		else if(input.equals(Rectangle))
		{
			canvasPanel.select=0;
			canvasPanel.setDrawMode(CanvasPanel.RECT);
		}
		/* ������ �������� ������ ���, CanvasPanel�� PENTAGON ����*/
		else if(input.equals(Pentagon))
		{
			canvasPanel.select=0;
			canvasPanel.setDrawMode(CanvasPanel.PENTAGON);
		}
		/* ������ �������� ������ ���, CanvasPanel�� HEXAGON ����*/
		else if(input.equals(Hexagon))
		{
			canvasPanel.select=0;
			canvasPanel.setDrawMode(CanvasPanel.HEXAGON);
		}
		/* ������ ĥ������ ������ ���, CanvasPanel�� HEXAGON ����*/
		else if(input.equals(Heptagon))
		{
			canvasPanel.select=0;
			canvasPanel.setDrawMode(CanvasPanel.HEPTAGON);
		}
		/* ������ �Ȱ����� ������ ���, CanvasPanel�� HEXAGON ����*/
		else if(input.equals(Octagon))
		{
			canvasPanel.select=0;
			canvasPanel.setDrawMode(CanvasPanel.OCTAGON);
		}
		/* ������ ������ ��ư�� Ŭ���� ���, �ش�� ���� */
		else if (input.equals(Black)) 
		{
			selectColor.setBackground(new Color(0, 0, 0));
			color = new Color(0, 0, 0);
		}
		/* ������ ��� ��ư�� Ŭ���� ���, �ش�� ���� */
		else if (input.equals(White)) 
		{
			selectColor.setBackground(new Color(255,255,255));
			color = new Color(255,255,255);
		}
		/* ������ �Ķ��� ��ư�� Ŭ���� ���, �ش�� ���� */
		else if (input.equals(Blue)) 
		{
			selectColor.setBackground(new Color(0,165,232));
			color = new Color(0,165,232);
		}
		/* ������ ���� ��ư�� Ŭ���� ���, �ش�� ���� */
		else if (input.equals(Brown)) 
		{
			selectColor.setBackground(new Color(185,122,87));
			color = new Color(185,122,87);
		}
		/* ������ �ϴû� ��ư�� Ŭ���� ���, �ش�� ���� */
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
		/* ������ �ʷϻ� ��ư�� Ŭ���� ���, �ش�� ���� */
		else if (input.equals(Lime)) 
		{
			selectColor.setBackground(new Color(181,229,29));
			color = new Color(181,229,29);
		}
		/* ������ �������� ��ư�� Ŭ���� ���, �ش�� ���� */
		else if (input.equals(Orange)) 
		{
			selectColor.setBackground(new Color(253,128,36));
			color = new Color(253,128,36);
		}
		/* ������ ��ũ�� ��ư�� Ŭ���� ���, �ش�� ���� */
		else if (input.equals(Pink)) 
		{
			selectColor.setBackground(new Color(253,175,200));
			color = new Color(253,175,200);
		}
		/* ������ ������ ��ư�� Ŭ���� ���, �ش�� ���� */
		else if (input.equals(Red)) 
		{
			selectColor.setBackground(new Color(237,27,36));
			color=new Color(237,27,36);
		}
		/* ������ ���ѻ����� ��ư�� Ŭ���� ���, �ش�� ���� */
		else if (input.equals(Red2)) 
		{
			selectColor.setBackground(new Color(136,0,22));
			color = new Color(136,0,22);
		}	
		/* ������ ����� ��ư�� Ŭ���� ���, �ش�� ���� */
		else if (input.equals(Yellow)) 
		{
			selectColor.setBackground(new Color(253,242,0));
			color=new Color(253,242,0);
		}
		/* ������ ���ѳ���� ��ư�� Ŭ���� ���, �ش�� ���� */
		else if (input.equals(Yellow2)) 
		{
			selectColor.setBackground(new Color(238,228,175));
			color = new Color(238,228,175);
		}
		/* ������ �ݻ� ��ư�� Ŭ���� ���, �ش�� ���� */
		else if (input.equals(Gold)) 
		{
			selectColor.setBackground(new Color(255,200,10));
			color = new Color(255,200,10);
		}
		/* ������ ����� ��ư�� Ŭ���� ���, �ش�� ���� */
		else if (input.equals(Violet)) 
		{
			selectColor.setBackground(new Color(199,191,232));
			color=new Color(199,191,232);
		}
		/* ������ ���λ� ��ư�� Ŭ���� ���, �ش�� ���� */
		else if (input.equals(Wine)) 
		{
			selectColor.setBackground(new Color(163,72,167));
			color = new Color(163,72,167);
		}
		/* ������ ȸ�� ��ư�� Ŭ���� ���, �ش�� ���� */
		else if (input.equals(Gray)) 
		{
			selectColor.setBackground(new Color(127,127,127));
			color = new Color(127,127,127);
		}
		/* ������ ����ȸ�� ��ư�� Ŭ���� ���, �ش�� ���� */
		else if (input.equals(Gray2)) 
		{
			selectColor.setBackground(new Color(195,195,195));
			color = new Color(195,195,195);
		}
		/* ������ ���� �� ������ Ŭ���� ���, ���� �� ����â�� ���� ������ ������ ������ ���� */
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
	/* ������� �ʴ� ������ */
	public void mouseClicked(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}

	/**
	 *  ������ ���� �޴��ٰ� ���õ� ���, �׿� �ش��ϴ� �ൿ�� �����ϱ� ���� �޼ҵ�
	 */
	@SuppressWarnings("static-access")
	public void actionPerformed(ActionEvent e)
	{
		/* �߻��� �̺�Ʈ �޴� ��ȯ */
		String col = (String)e.getActionCommand(); 					
	
		/* ������ ���θ���⸦ ������ ���, */
		if (col.equals("���� �����(N)")) 
		{
			/* �̸� �ʱ�ȭ */
			filename="Untitled.jpg"; 								
			makername = "Unknown";
			
			/* ���� �۾� ������ ���� ���θ� �������� ���� ���� ���� ���� */
			JLabel msg = new JLabel();
			msg.setText("���� ������ �����ϰڽ��ϱ�?");					
			
			if (JOptionPane.showConfirmDialog(null, msg, "Ȯ��",
				JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) != JOptionPane.YES_OPTION) 
			{	/* ������ ������ ������ �ʴ� ���, ������Ÿ��Ʋ ���� */
				setTitle(filename + " - " + makername);	
				canvasPanel.creation();
			}
			else
			{	/* ������ ������ ���ϴ� ��� �����ϰ� ���θ��� */
				canvasPanel.reNameSave(); 
				canvasPanel.creation();
			}
		}
		/* ������ ���⸦ ������ ���, */
		else if(col.equals("����(O)"))
		{
			/* �̸� �ʱ�ȭ */
			filename="Untitled.jpg"; 								
			makername = "Unknown";
			
			/* ���� �۾� ������ ���� ���θ� �������� ���� ���� ���� */
			JLabel msg = new JLabel();       
			msg.setText("���� ������ �����ϰڽ��ϱ�?");					
			
			if (JOptionPane.showConfirmDialog(null, msg, "Ȯ��",	
				JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION) 
			{	/* ������ ������ ���ϴ� ���, ���� �� ������Ÿ��Ʋ ���� */
				canvasPanel.reNameSave();
				filename = canvasPanel.open();
				setTitle(filename + " - " + makername);
			}
			else
			{	/* ������ �ʴ� ��� ������Ÿ��Ʋ ���� */
				filename = canvasPanel.open();
				setTitle(filename + " - " + makername);
			}
		}
		/* ������ ������ ������ ���, ���� �� ������Ÿ��Ʋ ���� */
		else if(col.equals("����(S)"))
		{
			canvasPanel.reNameSave();
			setTitle(filename + " - " + makername);
		}
		/* ������ ���Ḧ ������ ���, */
		else if(col.equals("����"))	
		{
			/* �ٽ��ѹ� ����ڿ��� ���� Ȯ�� */
			JLabel msg = new JLabel();
			msg.setText("���� �Ͻðڽ��ϱ�?");
	
			if (JOptionPane.showConfirmDialog(null, msg, "Ȯ��",
				JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) != JOptionPane.YES_OPTION)
			{	/* �ƴϿ並 ������ ���, �������� �ʰ� ���ư� */
				return;												
			}
			System.exit(0);	/* ���� */
		}
		/* ������ �����ϱ⸦ ������ ���, */
		else if(col.equals("�����ϱ�(T)"))
		{
			/* select 1�̸� ���ø��. setDrawMode�� ������ �������� ������ ��� ���� */
			canvasPanel.select=1;
		}
		/* ������ ���縦 ������ ���, ���� ������ �̹����� ���� */
		else if(col.equals("�����ϱ�(C)"))
		{
			canvasPanel.copy();
		}
		/* ������ �߶󳻱⸦ ������ ���, ���� ������ �̹����� �߶󳻱� */
		else if(col.equals("�߶󳻱�(X)"))
		{
			canvasPanel.cut();
		}
		/* ������ �ٿ��ֱ⸦ ������ ���, ���� ������ �̹����� �ٿ��ֱ� */
		else if(col.equals("�ٿ��ֱ�(V)"))
		{
			canvasPanel.paste();
		}
		/* ������ ����(�����) �׸��� ���ý�, CanvasPanel�� PEN ����. ó�� ���� �� �⺻������ */
		else if(col.equals("����(P)"))
		{
			canvasPanel.select=0;
			canvasPanel.setDrawMode(CanvasPanel.PEN);
		}
		/* ������ ���찳 ���ý�, CanvasPanel�� ERASER ���� */
		else if(col.equals("���찳(E)"))
		{
			canvasPanel.select=0;
			canvasPanel.setDrawMode(CanvasPanel.ERASER);
		}
		/* ������ �귯�� ���ý�, CanvasPanel�� BRUSH ���� */
		else if(col.equals("�귯��(B)"))
		{
			canvasPanel.select=0;
			canvasPanel.setDrawMode(CanvasPanel.BRUSH);
		}
		/* ������ ���׸���(����) ���ý�, CanvasPanel�� LINE ���� */
		else if(col.equals("���׸���(L)"))
		{
			canvasPanel.select=0;
			canvasPanel.setDrawMode(CanvasPanel.LINE);
		}
		/* ������ ���׸��� ���ý�, CanvasPanel�� OVAL ���� */
		else if(col.equals("���׸���(C)"))
		{
			canvasPanel.select=0;
			canvasPanel.setDrawMode(CanvasPanel.OVAL);
		}
		/* ������ �ձٻ簢�� ���ý�, CanvasPanel�� ROUNDRECT ���� */
		else if(col.equals("�ձٻ簢��(O)"))
		{
			canvasPanel.select=0;
			canvasPanel.setDrawMode(CanvasPanel.ROUNDRECT);
		}
		/* ������ �ﰢ�� ���ý�, CanvasPanel�� TRIANGLE ���� */
		else if(col.equals("�ﰢ��(I)"))
		{
			canvasPanel.select=0;
			canvasPanel.setDrawMode(CanvasPanel.TRIANGLE);
		}
		/* ������ �簢�� ���ý�, CanvasPanel�� RECT ���� */
		else if(col.equals("�簢��(R)"))
		{
			canvasPanel.select=0;
			canvasPanel.setDrawMode(CanvasPanel.RECT);
		}
		/* ������ ������ ���ý�, CanvasPanel�� PENTAGON ���� */
		else if(col.equals("������(A)"))	
		{
			canvasPanel.select=0;
			canvasPanel.setDrawMode(CanvasPanel.PENTAGON);
		}
		/* ������ ������ ���ý�, CanvasPanel�� HEXAGON ���� */
		else if(col.equals("������(H)"))
		{
			canvasPanel.select=0;
			canvasPanel.setDrawMode(CanvasPanel.HEXAGON);
		}
		/* ������ ĥ���� ���ý�, CanvasPanel�� HEPTAGON ���� */
		else if(col.equals("ĥ����(U)"))
		{
			canvasPanel.select=0;
			canvasPanel.setDrawMode(CanvasPanel.HEPTAGON);
		}
		/* ������ �Ȱ��� ���ý�, CanvasPanel�� OCTAGON ���� */
		else if(col.equals("�Ȱ���(O)"))
		{
			canvasPanel.select=0;
			canvasPanel.setDrawMode(CanvasPanel.OCTAGON);
		}
		/* ������ ������ ���ý�, ������ �⺻������ ������ */
		else if(col.equals("������"))									/* ������ ���� ��� */
		{
			JOptionPane.showMessageDialog(this, "����ȸ���б�\n�й�: 200934013\n�̸�: ������\n",
										  "����ȸ���б�", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	public Paint_Main() 
	{
		canvasPanel = new CanvasPanel(filename);
		
		/* ����Ʈ�� �޾ƿ��� */
		contentPane = getContentPane();
		/* ���� ���� */
		contentPane.setBackground(CANVASCOLOR);
		/* ��ġ������ ����, ��ư ���� ��ҵ��� ��ġ�� �������� ��� �ֱ� ���� */
		contentPane.setLayout(null);
		
		settingMenu();					/* �������� �޴��� ���� */
		createMenu();					/* �������� �޴��� ���� */
		createTab();					/* �������� ���� ���� */
		settingTab();					/* �������� ���� ���� */
		initUI();						/* �⺻ UI ���� */
		
		filename="Untitled.jpg"; 
		
		makername = "Unknown";
		setTitle(filename+" - "+makername); 						/* �ʱ������̸� ���� */

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(FRAMEWIDTH, FRAMEHEIGHT); 							/* ������ ���� */
		setLocation(200, 60); 										/* �⺻ ��ġ���� */
		setResizable(false);										/* ������� �Ұ� ���� */
		setVisible(true);
	}
	
	public static void main(String args[]) 
	{
		new Paint_Main();
	}
}