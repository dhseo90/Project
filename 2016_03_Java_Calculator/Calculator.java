package Calculator_0329;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Stack;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")                      					/* Warning ���� */
public class Calculator extends JFrame
{
	JPanel buttonPanel, resultPanel;               					/* ��ư�ǳڰ� ����ǳڷ� ���� */
	GridLayout grid;												/* GridLayout���� ��ư�ǳ� ���� */

	JButton[] jbButton = null;										/* ��ư�迭 �ʱ�ȭ */
	JLabel historyLabel, inputLabel;								/* ���â ���̺�(History & Input & Spare) */
	String[] keypadArr = { "C", "��", "��", "��",
						   "7", "8", "9", "��",
						   "4", "5", "6", "��",
						   "1", "2", "3", "()",
						   "0", ".", "��", "="};						/* �⺻ ��ư ���� �迭 */

	Color resultbgcolor = new Color(233, 234, 236);     			/* ��ü��� ���â */
	Color buttonbgcolor = new Color(37, 37, 37);      				/* ��ü��� ��ưâ */
	Color buttoncolor1 = new Color(75, 110, 155);      				/* ��ư��� �Ķ���*/
	Color buttoncolor2 = new Color(82, 82, 82);         			/* ��ư��� ������ */
	Color buttoncolor3 = new Color(200, 115, 35);     				/* ��ư��� ��Ȳ�� */
	Color buttoncolor4 = new Color(235, 235, 235);    				/* ��ư��� ��� */

	String input = "";												/* ����� �Է°� ���� String */
	String history = "";											/* ���� History ���� String */
	String beforeCheck = "";										/* ���� �Է°� ���� String */
	String temp = new String();										/* �ӽ� String(�������� ���) */
	
	boolean minusFlag = false;
	boolean resultFlag = false;
	boolean bracketFlag = false;
	int bracketCount = 0;
	
	ArrayList<String> postfixList = new ArrayList<String>();		/* ����ǥ������� ��ȯ �� �ش� ���� ���� �� ArrayList */
	Stack<Character> checkStack = new Stack<Character>();			/* ��ȣ ��� �� ������ �ùٸ��� �˻��ϴ� Stack */
	Stack<String> calculatorStack = new Stack<String>();			/* ����ǥ������� ��ȯ�� �� �����ڸ� �����ϴ� Stack */

	public Calculator()
	{
		setTitle("Calculator[made by 200934013]");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);				/* x��ư�� ������ ���α׷� ���� */

		getContentPane().addKeyListener(new KeyBoardListener());	/* Ű���� ������ */
		getContentPane().requestFocus();							/* Component���� ��Ŀ���� �־� Ű �Է��� ���� �� �ְ� �� */
		getContentPane().setFocusable(true);						/* Component�� ��Ŀ���� ���� �� �ֵ��� ���� */

		initButtonSetting();										/* ��ư �ǳ� ���� */
		initButtonPanel();											/* ��ư �ǳ� �ʱ�ȭ */
		initResultWindow();											/* ���â �ǳ� �ʱ�ȭ */

		getContentPane().add("North",  resultPanel);				/* ���â �ǳ� North�� ��ġ */
		getContentPane().add("Center", buttonPanel);				/* ��ư �ǳ� Center�� ��ġ */

		setSize(350, 450);											/* ������ ũ�⸦ 350 x 450���� ���� */
		setLocation(400, 200);										/* 500 x 200�� ��ġ���� ����� */
		setResizable(false);										/* ȭ�� ũ�� ���� �Ұ� */
		setVisible(true);											/* �������� ȭ�鿡 ��� */
	}
	
	private class ButtonListener implements ActionListener			/* ���콺 �Է°� Ȯ�� */
	{
		public void actionPerformed(ActionEvent e) 
		{
			String click = e.getActionCommand();
			System.out.println("Mouse Check : " + click);			/* ���콺 �Է� üũ */

			if (click.equals("��"))									/* ��ư �� �Է½� ������ * ���� �ν�(������) */
			{
				inputMethod("*");
			}
			else if (click.equals("��"))								/* ��ư �� �Է½� ������ / ���� �ν�(������) */
			{
				inputMethod("/");
			}
			else if (click.equals("��"))								/* ��ư �� �Է½� ������ / ���� �ν�(������) */
			{
				inputMethod("-");
			}
			else if (click.equals("��"))								/* ��ư �� �Է½� ������ / ���� �ν�(������) */
			{
				inputMethod("+");
			}
			else if (click.equals("()"))
			{
				validateBrackets();
			}
			else
			{
				inputMethod(click);
			}
		}
	}
	
	private class KeyBoardListener extends KeyAdapter				/* Ű���� �Է°� Ȯ�� */
	{
		public void keyReleased(KeyEvent e) 
		{
			String enter = ((Character) e.getKeyChar()).toString();
			System.out.println("KeyBoard Check : " + enter);		/* Ű���� �Է� üũ */

			if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE)			/* Ű����� �齺���̽� �Է½� �� ���� �ν� */
			{
				inputMethod("��");
			} 
			else if (e.getKeyCode() == KeyEvent.VK_ESCAPE)			/* ESC �Է½� C ���� �ν� */
			{
				inputMethod("C");
			}
			else if (e.getKeyCode() == KeyEvent.VK_ENTER)			/* Ű����� ���� �Է½� = ���� �ν� */
			{
				inputMethod("=");
			}
			else if (e.getKeyCode() == KeyEvent.VK_SHIFT)			/* Shift�� ����(���� �߻��ϴ� ��찡 ����) */
			{
				;
			}
			else
			{
				inputMethod(enter);
			}
		}
	}
	
	private void inputMethod(String check) 							/* ����� �Է°��� ó�� */
	{
		/* inputMethod�� �Ʒ����� ������ Ű����� ������ 1���� �Է¹��� �� �ְ�, ���콺 �Է� ���Ŀ� Ű����� �Է� �õ��� �ƹ��͵� �Է��� �ȵ� */
		getContentPane().requestFocus();						/* Component���� ��Ŀ���� �־� Ű �Է��� ���� �� �ְ� �� */

		System.out.println("inputMethod : " + check);

		switch (check)
		{
			case "1" :
			case "2" :
			case "3" :
			case "4" :
			case "5" :
			case "6" :
			case "7" :
			case "8" :
			case "9" :
				if (resultFlag == true)								/* ����� ���� �ǿ����� �Է½� ���� ����� ������ */
				{
					history = "";
					historyLabel.setText("");
					resultFlag = false;
				}
				
				input += check;
				if (input.length() > 19)   							/* ����ڰ� ���� �Է��� �� ���̻� ����� �Ұ����� ��� ��� ������ �ִ��� ���� ��� */
				{
					inputLabel.setText(".." + input.substring((input.length() - 17), input.length()));
				}
				else
				{
					inputLabel.setText(input);
				}
				break;
	
			case "0" :
				if (resultFlag == true)								/* ����� ���� �ǿ����� �Է½� ���� ����� ������ */
				{
					history = "";
					resultFlag = false;
				}
				
				if (input.equals(""))								/* ù �Է°��� 0�ΰ�� �Է°� ���� */
				{   
					input = "";
				}
				else
				{
					input += check;
	
					if (input.length() > 19)						/* ����ڰ� ���� �Է��� �� ���̻� ����� �Ұ����� ��� ��� ������ �ִ��� ���� ��� */
					{
						inputLabel.setText(".." + input.substring((input.length() - 17), input.length()));
					}
					else
					{
						inputLabel.setText(input);
					}
				}
				break;
	
			case "��" :
				if (input.length() == 1)							/* �ѱ��ڸ� �ԷµǾ� �ִ� ���, ��� ����� 0 ��� */
				{
					input = "";
					inputLabel.setText("0");
				}
				else if (input.length() == 2 && input.charAt(0) == '��')
				{
					input = "";
					inputLabel.setText("0");
				}
				else if(input.length() != 0) 
				{
					input = input.substring(0, input.length() - 1);
					inputLabel.setText(input);
				}
				break;
	
			case "C" :												/* ��� ���� ���� */
				input = "";
				history = "";
				minusFlag = false;
				resultFlag = false;
				bracketFlag = false;
				bracketCount = 0;
				historyLabel.setText(history);
				inputLabel.setText("0");
				break;
				
			case "." :
				if (resultFlag == true)								/* ����� ���� �ǿ����� �Է½� ���� ����� ������ */
				{
					history = "";
					resultFlag = false;
				}
				
				if ("".equals(input)) 
				{
					input = "0" + check;
					inputLabel.setText(input);
				} 
				else 
				{
					if (input.indexOf(".") == -1)					/* ���� �Է����� ���ڰ��� �Ҽ����� ���ٸ�, */
					{
						input += check;
	
						if (input.length() > 18)					/* ����ڰ� ���� �Է��� �� ���̻� ����� �Ұ����� ��� ��� ������ �ִ��� ���� ��� */
						{
							inputLabel.setText(".." + input.substring((input.length() - 17), input.length()));
						}
						else
						{
							inputLabel.setText(input);
						}
					}
				}
				break;
	
			case "��" :			
				if (resultFlag == true)								/* ����� ���� ��ȣ�� �Է½� ������� ��ȣ�� �ٲ��� */
				{
					input = history;
					history = "";
					resultFlag = false;
				}
				
				if (input != "")
				{
					if (minusFlag == false)							/* ���� ������(-)�� �����ϱ� ���� Ư������ ���̳ʽ�(��) ��� */
					{
						input = "��" + input;
						minusFlag = true;
					}
					else
					{
						input = input.substring(1);
						minusFlag = false;
					}
	
					if (input.length() > 18)						/* ����ڰ� ���� �Է��� �� ���̻� ����� �Ұ����� ��� ��� ������ �ִ��� ���� ��� */
					{
						inputLabel.setText(".." + input.substring((input.length() - 17), input.length()));
					}
					else
					{
						inputLabel.setText(input);
					}
					historyLabel.setText(history);
				}
				break;
	
			case "/" :   
			case "*" :
			case "-" :
			case "+" :
				if (input == "" && history == "")					/* �� ó�� �����ڸ� �Է� �� 0�� �����ڰ� �����丮�� �Էµ� */
				{
					history = "0" + check;
					historyLabel.setText(history);
					break;
				}
	
				char ch = beforeCheck.charAt(0);					/* �����ڰ� �ߺ� �ԷµǾ��� ��, ���߿� �Է��� �����ڷ� ��ü */

				if (isOperator(ch)) 
				{
					history = history.substring(0, history.length() - 1);
					history += check;
					historyLabel.setText(history);
				}
				else												/* �Ϲ����� ��� �����丮�� �����ڸ� �־��� */
				{
					if (resultFlag == true)
					{
						history += check;
						resultFlag = false;
					}
					else
					{
						history += (input + check);
					}
				}
	
				if (history.length() > 18)							/* ����ڰ� ���� �Է��� �� ���̻� ����� �Ұ����� ��� ��� ������ �ִ��� ���� ��� */
				{
					historyLabel.setText(".." + history.substring((history.length() - 17), history.length()));
				}
				else
				{
					historyLabel.setText(history);
				}
	
				input = "";											/* ������ �Է� �� input���� history�� ���� �� ����� */
				minusFlag = false;
				inputLabel.setText("");
	
				break;
	
			case "=" :
				history += input;									/* �Էµ� input���� history�� �־��� */
	
				if (bracketsCheck(history) == false)				/* ��ȣ �˻�, ��ȣ ���� �߻��� �̹� ���� �ʱ�ȭ */
				{
					input = "";
					history = "";
					historyLabel.setText(history);
					inputLabel.setText("��ȣ ���� �߻�");
					break;
				}
	
				postfix(history);									/* ������������� �ٲ��ִ� �޼ҵ� */
	
				if (formulaCheck() == false)						/* �����ڿ� �ǿ������� ���谡 ��Ȯ���� ������ִ� �޼ҵ�. */
				{
					input = "";
					history = "";
					historyLabel.setText(history);
					inputLabel.setText("���� ���� �߻�");
					break;
				}
				
				String result = calculate();						/* ������������ �ٲ� ���� ����ϴ� �޼ҵ� */
				double realResultValue = Double.parseDouble(result);
					
				temp = history;
				input = "";
				history = "";
	
				if ((realResultValue % 1) != 0)						/* ���� ���� �Ǽ���� �Ǽ��� ���� */
				{
					DecimalFormat dFormat = new DecimalFormat("####.###");	/* Double���� format ���� �Ҽ��� 3�ڸ����� �����ϴ� format */
					
					if (realResultValue < 0)						/* ������� ������ ��� ó�� */
					{
						realResultValue *= (-1);
						history = "��" + dFormat.format(realResultValue);
					}
					else
					{
						history = "" + dFormat.format(realResultValue);
					}
					
					historyLabel.setText(temp);
					inputLabel.setText("= " + history);
				}
				else												/* ���� ���� ������� ������ ���� */
				{
					if ((int)realResultValue < 0)					/* ������� ������ ��� ó�� */
					{
						realResultValue *= (-1);
						history = "��" + (int)realResultValue;
					}
					else
					{
						history = "" + (int)realResultValue;
					}
					historyLabel.setText(temp);
					inputLabel.setText("= " + history);
				}
				minusFlag = false;
				resultFlag = true;
				bracketFlag = false;
				bracketCount = 0;
				break;
	
			case "(" :												/* ���� ��ȣ�� �����ٸ� history�� ���� */
				history += "(";
				historyLabel.setText(history);
				bracketCount++;
				break;
	
			case ")" :												/* ���� ��ȣ�� ������ �� ���� input�� ���� �Բ� history�� ���� */
				history += (input + ")");
				historyLabel.setText(history);
				input = "";
				inputLabel.setText("");
				bracketCount--;
				break;
		}

		if (check.equals("��") && input.isEmpty())					/* �� ��쿡 ���꿡 ������ ���ܼ� Validate */
		{
			;
		}
		else
		{
			beforeCheck = check;
		}
	}
	
	public void validateBrackets()									/* ���콺 �Է� ��ȣ�� "("���� ")"���� �ν��ϴ� �޼ҵ� */
	{
		if (resultFlag == true)												/* ������� ���� ���� ()�� �Էµȴٸ� *( �Է� */
		{
			resultFlag = false;
			history += "*";
			inputMethod("(");
		}
		else if (history.isEmpty() || isOperator(beforeCheck.charAt(0)))	/* ������ ����ְ�, ������ �����ڰ� ������ ��� ( �Է� */
		{
			inputMethod("(");
			bracketFlag = true;
		}
		else if (bracketCount > 0 && isOperation(beforeCheck.charAt(0)))	/* ���Ŀ� (�� ���Ⱦ���, ������ �ǿ����ڰ� ������ ��� ( �Է� */
		{
			inputMethod(")");
			bracketFlag = false;
		}
		else if (beforeCheck == ")")
		{
			if (bracketCount > 0)											/* ������ )�� ���ȴµ�, ��ȣ¦�� �ȸ´´ٸ� ) �Է� */
			{
				inputMethod(")");
				bracketFlag = false;
			}
			else															/* ������ )�� ���Ȱ�, �� ��ȣ�� ����� ������� *( �Է� */
			{
				history += "*";
				inputMethod("(");
				bracketFlag = true;
			}
		}
		else if (beforeCheck == "(")										/* ������ (�� ������ ���� ��쿡 ������ �ʴ´ٸ� ( �Է� */
		{
			inputMethod("(");
			bracketFlag = true;
		}
	}
	
	public boolean isOperator(char op)								/* �Է°��� �����ڶ�� true, �ƴϸ� false ��ȯ */
	{
		if (op == '+' || op == '-' || op == '*' || op == '/')
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public boolean isOperation(char op)								/* �Է°��� �ǿ����ڶ�� true, �ƴϸ� false ��ȯ */
	{
		if (op >= '0' && op <= '9')
		{
			return true;
		}
		else if (op == '��')
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public int checkPrecedence(char op)								/* ������ �켱���� ����(Ver_Character) */
	{
		if (op == '(')
		{
			return 1;
		}
		else if (op == '+' || op == '-')
		{
			return 3;
		}
		else if (op == '*' || op == '/')
		{
			return 5;
		}
		else
		{
			return 7;
		}
	}
	
	public int checkPrecedence(String str)							/* ������ �켱���� ����(Ver_String) */
	{
		if (str.charAt(0) == '(')
		{
			return 1;
		}
		else if (str.charAt(0) == '+' || str.charAt(0) == '-')
		{
			return 3;
		}
		else if (str.charAt(0) == '*' || str.charAt(0) == '/')
		{
			return 5;
		}
		else
		{
			return 7;
		}
	}
	
	public void postfix(String exp)									/* ����ǥ����� ����ǥ������� �ٲ��ִ� �޼ҵ� */
	{
		calculatorStack.clear();
		postfixList.clear();

		for (int i = 0; i < exp.length(); i++)
		{
			char ch = exp.charAt(i);

			if (ch == '(')											/* ���� ��ȣ�� �Է½� Stack�� �Է� */
			{
				temp = "";
				temp += ch;
				calculatorStack.push(temp);
			}
			else if (ch == ')')										/* ���� ��ȣ�� �Է½� ���� ��ȣ�� ���Ë����� pop */
			{
				while (true)
				{
					String dst = (String)calculatorStack.pop();
					if (dst.charAt(0) != '(')
					{
						postfixList.add(dst);
					}
					else
					{
						break;
					}
				}
			}
			else if (isOperator(ch))								/* ������ �Է½� �켱���� üũ �� Stack�� �Էµ� �����ڰ� ��� �Էµ� �����ں��� �켱������ ���ٸ� List�� ���� */
			{
				while ((!calculatorStack.empty()) && (checkPrecedence((String)calculatorStack.peek()) >= checkPrecedence(ch)))
				{
					postfixList.add(calculatorStack.pop());
				}
				temp = "";
				temp += ch;
				calculatorStack.push(temp);
			}
			else if (isOperation(ch) || ch == '.' || ch == '��')		/* �ǿ����� �Է½� �ǿ����� ���� List�� ���� */
			{
				temp = "";
				
				do
				{
					temp += ch;
					i++;
					if (i < exp.length())
					{
						ch = exp.charAt(i);
					}
					else
					{
						break;
					}
				} while (isOperation(ch) || ch == '.' || ch == '��');
				postfixList.add(temp);
				i--;
			}
		}

		while (!calculatorStack.empty())							/* Stack�� ����� �����ڵ��� ��� List�� ���� */
		{
			temp = "";
			temp = (String)calculatorStack.pop();
			postfixList.add(temp);
		}
	}
	
	public String calculate()										/* ����ǥ������� ��ȯ�� ���� ����� ������� ���� �޼ҵ� */
	{
		calculatorStack.clear();
		double ddst, dsrc;
		
		while (!postfixList.isEmpty())
		{
			temp = "";
			temp = (String)postfixList.get(0);
			postfixList.remove(0);									/* List�� ����� ���� �ϳ��� ��(���� ���� ����) */

			if (isOperation(temp.charAt(0)))						/* �ǿ����ڸ� push */
			{
				calculatorStack.push(temp);
			}
			else
			{
				String dst = (String)calculatorStack.pop();
				String src = (String)calculatorStack.pop();
	
				if (dst.charAt(0) == '��')							/* ������ȣ ���� �ִ� �ǿ������� ���, �� ���� �� -1�� ���Ѵ�. */
				{
					dst = dst.substring(1);
					ddst = Double.parseDouble(dst) * (-1);
				}
				else 
				{
					ddst = Double.parseDouble(dst);
				}
					
				if (src.charAt(0) == '��')
				{
					src = src.substring(1);
					dsrc = Double.parseDouble(src) * (-1);
				}
				else
				{
					dsrc = Double.parseDouble(src);
				}
				
				if (temp.charAt(0) == '*')							/* �����ڶ�� �ǿ����ڸ� pop�ؼ� ���� �� ����� �ٽ� Stack�� ���� */
				{
					String buf = String.valueOf(ddst * dsrc);
					calculatorStack.push(buf);
				}
				else if (temp.charAt(0) == '+')
				{
					String buf = String.valueOf(ddst + dsrc);
					calculatorStack.push(buf);
				}
				else if (temp.charAt(0) == '/')
				{
					String buf = String.valueOf(dsrc / ddst);
					calculatorStack.push(buf);
				}
				else if (temp.charAt(0) == '-')
				{
					String buf = String.valueOf(dsrc - ddst);
					calculatorStack.push(buf);
				}
				else if (temp.charAt(0) == '%')
				{
					String buf = String.valueOf(dsrc % ddst);
					calculatorStack.push(buf);
				}
			}
		}

		return (String) calculatorStack.pop();
	}
	
	public boolean bracketsCheck(String exp)                        /* ���� üũ : ����� �ȿ� �ܼ��� ��ȣ�� ¦ ������ �³� Ȯ���غ� */
	{
		checkStack.clear();

		for(int i = 0; i < exp.length(); i++)
		{
			char ch = exp.charAt(i);

			if (ch == '(')
			{
				checkStack.push(ch);								/* ��ȣ�� �ִٸ�, ����� ������ Ȯ���ϱ� ���� push */
			}
			else if (ch == ')')
			{
				if (checkStack.isEmpty())							/* ������ȣ�� �� ��� false */
				{
					return false;
				}

				char checkInTheStack = (Character)checkStack.pop().charValue();

				if ((ch == ')') && checkInTheStack != '(')			/* ��ȣ�� ¦�� �ȸ����� false */
				{
					return false;
				}
			}
		}

		if (checkStack.isEmpty())
		{
			return true;
		}
		else
		{
			return false;											/* Stack�ȿ� �ٸ� ������ �ִٸ� �˻� ���� false */
		}
	}
	
	public boolean formulaCheck()									/* ���� üũ : �ǿ����ڿ� �������� ������ Ȯ���� ������� ��ȿ�� �˻� */
	{
		int numberOfOperation = 0;

		for (int i = 0; i < postfixList.size(); i++)
		{
			temp = "";
			temp = (String) postfixList.get(i);
			char ch = temp.charAt(0);

			while (ch == '(' || ch == ')')							/* ��ȣ�� ������ �����ڿ� �ǿ������� ������ ����� */
			{
				i++;
				temp = (String) postfixList.get(i);
				ch = temp.charAt(0);
			}
			
			if (isOperator(ch))										/* �����ڰ� ��Ÿ���� �ǿ������� ������ �ϳ� ���� */
			{
				numberOfOperation--;
			}
			else													/* �ǿ����ڰ� ��Ÿ���� �ǿ������� ������ �ϳ� �ø� */
			{
				numberOfOperation++;
				ch = temp.charAt(0);
			}
		}

		if (numberOfOperation == 1)									/* �ǿ������� ������ �����ں��� 1�� ���ƾ� ���� ���� */
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	private void initButtonSetting() 								/* ��ư ����(������, ����, �۲�), ���콺������ ��� */
	{
		buttonPanel = new JPanel();														/* Button Panel, GridLayout���� */

		buttonPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));				/* �ٱ� ����(��, ����, �Ʒ�, ������) ���� */
		buttonPanel.setBackground(buttonbgcolor);										/* buttonPanel��� �гο� ���� ���� */

		jbButton = new JButton[keypadArr.length];										/* jbButton ��ư �迭 �ʱ�ȭ */

		for (int i = 0; i < keypadArr.length; i++)										/* jbButton�� ǥ���� ���� ���� */
		{
			jbButton[i] = new JButton(keypadArr[i]);
			jbButton[i].setBorderPainted(false);										/* ��ư �׵θ� ���� */
			jbButton[i].setFont(new Font("�������", Font.BOLD, 40));						/* �������ü, ���� ����, ����ũ�� 30 */

			if (i == 0 || i == 3 || i == 15)
			{
				jbButton[i].setFont(new Font("�������", Font.BOLD, 35));					/* �������ü, ���� ����, ����ũ�� 25 */
			}

			if (i == 0)																	/* C, CE ��ư ������(����, �۾���) ���� */
			{
				jbButton[i].setForeground(Color.white);
				jbButton[i].setBackground(buttoncolor1);
			}
			else if ((i >= 1 && i <= 3) || ((i % 4 == 3) && i != 19))					/* ������ ��ư ������(����, �۾���) ���� */
			{
				jbButton[i].setForeground(Color.white);
				jbButton[i].setBackground(buttoncolor2);
			}
			else if (i == 19)															/* = ��ư ������(����, �۾���) ���� */
			{
				jbButton[i].setForeground(Color.white);
				jbButton[i].setBackground(buttoncolor3);
			}
			else																		/* �ǿ����� ��ư ������(����, �۾���) ���� */
			{
				jbButton[i].setForeground(Color.black);
				jbButton[i].setBackground(buttoncolor4);
			}
			
			jbButton[i].addActionListener(new ButtonListener());						/* ���콺 ������ ��� */
		}
	}
	
	private void initButtonPanel() 									/* GridLayout�� KeyPad�� ��ư �ǳڿ� ��� */
	{
		grid = new GridLayout(5, 4, 5, 5);							/* GridLayout�� ��, ��, ���򿩹�, �������� ���� */
		buttonPanel.setLayout(grid);
	      
		for (int i = 0; i < keypadArr.length; i++)
		{
			buttonPanel.add(jbButton[i]);
		}
	}
	
	private void initResultWindow() 								/* GridLayout�� History�� Input�� ��� �ǳڿ� ��� */
	{
		resultPanel = new JPanel(new GridLayout(2, 1));							/* Result Panel, GridLayout���� */
		resultPanel.setBackground(buttonbgcolor);								/* resPanel��� �гο� ���� ���� */
		resultPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 10, 5));	/* �ٱ� ����(��, ����, �Ʒ�, ������) ���� */

		historyLabel = new JLabel("", JLabel.RIGHT);							/* �� ���� ���̺�, �������� */
		historyLabel.setFont(new Font("�������", Font.PLAIN, 30));				/* �������ü, Plain���, ũ�� 18 */
		historyLabel.setBackground(resultbgcolor);
		historyLabel.setOpaque(true);

		inputLabel = new JLabel("0", JLabel.RIGHT);								/* �⺻��(0)�� ���̺�, ���� ���� */
		inputLabel.setFont(new Font("�������", Font.PLAIN, 30));					/* �������ü, ���� ����, ũ�� 18 */
		inputLabel.setBackground(resultbgcolor);
		inputLabel.setOpaque(true);

		resultPanel.add(historyLabel);
		resultPanel.add(inputLabel);
	}  
	
	public static void main(String[] args) 
	{
		new Calculator();														/* Calculator ��ü ���� */
	}
}