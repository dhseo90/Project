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

@SuppressWarnings("serial")                      					/* Warning 무시 */
public class Calculator extends JFrame
{
	JPanel buttonPanel, resultPanel;               					/* 버튼판넬과 결과판넬로 구성 */
	GridLayout grid;												/* GridLayout으로 버튼판넬 구성 */

	JButton[] jbButton = null;										/* 버튼배열 초기화 */
	JLabel historyLabel, inputLabel;								/* 결과창 레이블(History & Input & Spare) */
	String[] keypadArr = { "C", "÷", "×", "◀",
						   "7", "8", "9", "－",
						   "4", "5", "6", "＋",
						   "1", "2", "3", "()",
						   "0", ".", "±", "="};						/* 기본 버튼 구성 배열 */

	Color resultbgcolor = new Color(233, 234, 236);     			/* 전체배경 결과창 */
	Color buttonbgcolor = new Color(37, 37, 37);      				/* 전체배경 버튼창 */
	Color buttoncolor1 = new Color(75, 110, 155);      				/* 버튼배경 파랑색*/
	Color buttoncolor2 = new Color(82, 82, 82);         			/* 버튼배경 검정색 */
	Color buttoncolor3 = new Color(200, 115, 35);     				/* 버튼배경 주황색 */
	Color buttoncolor4 = new Color(235, 235, 235);    				/* 버튼배경 흰색 */

	String input = "";												/* 사용자 입력값 저장 String */
	String history = "";											/* 연산 History 저장 String */
	String beforeCheck = "";										/* 직전 입력값 저장 String */
	String temp = new String();										/* 임시 String(여러곳에 사용) */
	
	boolean minusFlag = false;
	boolean resultFlag = false;
	boolean bracketFlag = false;
	int bracketCount = 0;
	
	ArrayList<String> postfixList = new ArrayList<String>();		/* 후위표기법으로 변환 후 해당 식을 저장 할 ArrayList */
	Stack<Character> checkStack = new Stack<Character>();			/* 괄호 계산 시 계산식이 올바른지 검산하는 Stack */
	Stack<String> calculatorStack = new Stack<String>();			/* 후위표기법으로 변환할 때 연산자를 저장하는 Stack */

	public Calculator()
	{
		setTitle("Calculator[made by 200934013]");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);				/* x버튼을 누르면 프로그램 종료 */

		getContentPane().addKeyListener(new KeyBoardListener());	/* 키보드 리스너 */
		getContentPane().requestFocus();							/* Component에게 포커스를 주어 키 입력을 받을 수 있게 함 */
		getContentPane().setFocusable(true);						/* Component가 포커스를 받을 수 있도록 설정 */

		initButtonSetting();										/* 버튼 판넬 셋팅 */
		initButtonPanel();											/* 버튼 판넬 초기화 */
		initResultWindow();											/* 결과창 판넬 초기화 */

		getContentPane().add("North",  resultPanel);				/* 결과창 판넬 North로 배치 */
		getContentPane().add("Center", buttonPanel);				/* 버튼 판넬 Center로 배치 */

		setSize(350, 450);											/* 프레임 크기를 350 x 450으로 설정 */
		setLocation(400, 200);										/* 500 x 200의 위치에서 띄워짐 */
		setResizable(false);										/* 화면 크기 변경 불가 */
		setVisible(true);											/* 프레임을 화면에 출력 */
	}
	
	private class ButtonListener implements ActionListener			/* 마우스 입력값 확인 */
	{
		public void actionPerformed(ActionEvent e) 
		{
			String click = e.getActionCommand();
			System.out.println("Mouse Check : " + click);			/* 마우스 입력 체크 */

			if (click.equals("×"))									/* 버튼 × 입력시 연산자 * 으로 인식(디자인) */
			{
				inputMethod("*");
			}
			else if (click.equals("÷"))								/* 버튼 ÷ 입력시 연산자 / 으로 인식(디자인) */
			{
				inputMethod("/");
			}
			else if (click.equals("－"))								/* 버튼 ÷ 입력시 연산자 / 으로 인식(디자인) */
			{
				inputMethod("-");
			}
			else if (click.equals("＋"))								/* 버튼 ÷ 입력시 연산자 / 으로 인식(디자인) */
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
	
	private class KeyBoardListener extends KeyAdapter				/* 키보드 입력값 확인 */
	{
		public void keyReleased(KeyEvent e) 
		{
			String enter = ((Character) e.getKeyChar()).toString();
			System.out.println("KeyBoard Check : " + enter);		/* 키보드 입력 체크 */

			if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE)			/* 키보드로 백스페이스 입력시 ← 으로 인식 */
			{
				inputMethod("◀");
			} 
			else if (e.getKeyCode() == KeyEvent.VK_ESCAPE)			/* ESC 입력시 C 으로 인식 */
			{
				inputMethod("C");
			}
			else if (e.getKeyCode() == KeyEvent.VK_ENTER)			/* 키보드로 엔터 입력시 = 으로 인식 */
			{
				inputMethod("=");
			}
			else if (e.getKeyCode() == KeyEvent.VK_SHIFT)			/* Shift값 무시(오류 발생하는 경우가 있음) */
			{
				;
			}
			else
			{
				inputMethod(enter);
			}
		}
	}
	
	private void inputMethod(String check) 							/* 사용자 입력값을 처리 */
	{
		/* inputMethod에 아래줄이 없으면 키보드는 최초의 1번만 입력받을 수 있고, 마우스 입력 이후에 키보드로 입력 시도시 아무것도 입력이 안됨 */
		getContentPane().requestFocus();						/* Component에게 포커스를 주어 키 입력을 받을 수 있게 함 */

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
				if (resultFlag == true)								/* 결과값 이후 피연산자 입력시 이전 결과값 지워줌 */
				{
					history = "";
					historyLabel.setText("");
					resultFlag = false;
				}
				
				input += check;
				if (input.length() > 19)   							/* 사용자가 많은 입력을 해 더이상 출력이 불가능한 경우 출력 가능한 최대의 양을 출력 */
				{
					inputLabel.setText(".." + input.substring((input.length() - 17), input.length()));
				}
				else
				{
					inputLabel.setText(input);
				}
				break;
	
			case "0" :
				if (resultFlag == true)								/* 결과값 이후 피연산자 입력시 이전 결과값 지워줌 */
				{
					history = "";
					resultFlag = false;
				}
				
				if (input.equals(""))								/* 첫 입력값이 0인경우 입력값 무시 */
				{   
					input = "";
				}
				else
				{
					input += check;
	
					if (input.length() > 19)						/* 사용자가 많은 입력을 해 더이상 출력이 불가능한 경우 출력 가능한 최대의 양을 출력 */
					{
						inputLabel.setText(".." + input.substring((input.length() - 17), input.length()));
					}
					else
					{
						inputLabel.setText(input);
					}
				}
				break;
	
			case "◀" :
				if (input.length() == 1)							/* 한글자만 입력되어 있던 경우, 모두 지우고 0 출력 */
				{
					input = "";
					inputLabel.setText("0");
				}
				else if (input.length() == 2 && input.charAt(0) == '－')
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
	
			case "C" :												/* 모든 내용 삭제 */
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
				if (resultFlag == true)								/* 결과값 이후 피연산자 입력시 이전 결과값 지워줌 */
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
					if (input.indexOf(".") == -1)					/* 현재 입력중인 숫자값에 소수점이 없다면, */
					{
						input += check;
	
						if (input.length() > 18)					/* 사용자가 많은 입력을 해 더이상 출력이 불가능한 경우 출력 가능한 최대의 양을 출력 */
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
	
			case "±" :			
				if (resultFlag == true)								/* 결과값 이후 부호값 입력시 결과값의 부호를 바꿔줌 */
				{
					input = history;
					history = "";
					resultFlag = false;
				}
				
				if (input != "")
				{
					if (minusFlag == false)							/* 빼기 연산자(-)와 구분하기 위해 특수문자 마이너스(－) 사용 */
					{
						input = "－" + input;
						minusFlag = true;
					}
					else
					{
						input = input.substring(1);
						minusFlag = false;
					}
	
					if (input.length() > 18)						/* 사용자가 많은 입력을 해 더이상 출력이 불가능한 경우 출력 가능한 최대의 양을 출력 */
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
				if (input == "" && history == "")					/* 맨 처음 연산자만 입력 시 0과 연산자가 히스토리에 입력됨 */
				{
					history = "0" + check;
					historyLabel.setText(history);
					break;
				}
	
				char ch = beforeCheck.charAt(0);					/* 연산자가 중복 입력되었을 때, 나중에 입력한 연산자로 대체 */

				if (isOperator(ch)) 
				{
					history = history.substring(0, history.length() - 1);
					history += check;
					historyLabel.setText(history);
				}
				else												/* 일반적인 경우 히스토리에 연산자를 넣어줌 */
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
	
				if (history.length() > 18)							/* 사용자가 많은 입력을 해 더이상 출력이 불가능한 경우 출력 가능한 최대의 양을 출력 */
				{
					historyLabel.setText(".." + history.substring((history.length() - 17), history.length()));
				}
				else
				{
					historyLabel.setText(history);
				}
	
				input = "";											/* 연산자 입력 시 input값을 history에 저장 후 비워줌 */
				minusFlag = false;
				inputLabel.setText("");
	
				break;
	
			case "=" :
				history += input;									/* 입력된 input값을 history에 넣어줌 */
	
				if (bracketsCheck(history) == false)				/* 괄호 검사, 괄호 오류 발생시 이번 수식 초기화 */
				{
					input = "";
					history = "";
					historyLabel.setText(history);
					inputLabel.setText("괄호 오류 발생");
					break;
				}
	
				postfix(history);									/* 후위연산식으로 바꿔주는 메소드 */
	
				if (formulaCheck() == false)						/* 연산자와 피연산자의 관계가 정확한지 계산해주는 메소드. */
				{
					input = "";
					history = "";
					historyLabel.setText(history);
					inputLabel.setText("수식 오류 발생");
					break;
				}
				
				String result = calculate();						/* 후위연산으로 바뀐 식을 계산하는 메소드 */
				double realResultValue = Double.parseDouble(result);
					
				temp = history;
				input = "";
				history = "";
	
				if ((realResultValue % 1) != 0)						/* 계산된 값이 실수라면 실수로 저장 */
				{
					DecimalFormat dFormat = new DecimalFormat("####.###");	/* Double형의 format 지정 소수점 3자리까지 저장하는 format */
					
					if (realResultValue < 0)						/* 결과값이 음수인 경우 처리 */
					{
						realResultValue *= (-1);
						history = "－" + dFormat.format(realResultValue);
					}
					else
					{
						history = "" + dFormat.format(realResultValue);
					}
					
					historyLabel.setText(temp);
					inputLabel.setText("= " + history);
				}
				else												/* 계산된 값이 정수라면 정수로 저장 */
				{
					if ((int)realResultValue < 0)					/* 결과값이 음수인 경우 처리 */
					{
						realResultValue *= (-1);
						history = "－" + (int)realResultValue;
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
	
			case "(" :												/* 열린 괄호가 눌린다면 history에 저장 */
				history += "(";
				historyLabel.setText(history);
				bracketCount++;
				break;
	
			case ")" :												/* 닫힌 괄호가 눌리면 그 전에 input된 값과 함께 history에 저장 */
				history += (input + ")");
				historyLabel.setText(history);
				input = "";
				inputLabel.setText("");
				bracketCount--;
				break;
		}

		if (check.equals("±") && input.isEmpty())					/* 이 경우에 연산에 오류가 생겨서 Validate */
		{
			;
		}
		else
		{
			beforeCheck = check;
		}
	}
	
	public void validateBrackets()									/* 마우스 입력 괄호가 "("인지 ")"인지 인식하는 메소드 */
	{
		if (resultFlag == true)												/* 결과값이 나온 이후 ()가 입력된다면 *( 입력 */
		{
			resultFlag = false;
			history += "*";
			inputMethod("(");
		}
		else if (history.isEmpty() || isOperator(beforeCheck.charAt(0)))	/* 수식이 비어있고, 직전에 연산자가 눌려진 경우 ( 입력 */
		{
			inputMethod("(");
			bracketFlag = true;
		}
		else if (bracketCount > 0 && isOperation(beforeCheck.charAt(0)))	/* 수식에 (가 눌렸었고, 직전에 피연산자가 눌려진 경우 ( 입력 */
		{
			inputMethod(")");
			bracketFlag = false;
		}
		else if (beforeCheck == ")")
		{
			if (bracketCount > 0)											/* 직전에 )가 눌렸는데, 괄호짝이 안맞는다면 ) 입력 */
			{
				inputMethod(")");
				bracketFlag = false;
			}
			else															/* 직전에 )가 눌렸고, 앞 괄호가 제대로 닫힌경우 *( 입력 */
			{
				history += "*";
				inputMethod("(");
				bracketFlag = true;
			}
		}
		else if (beforeCheck == "(")										/* 직전에 (가 눌리고 위에 경우에 속하지 않는다면 ( 입력 */
		{
			inputMethod("(");
			bracketFlag = true;
		}
	}
	
	public boolean isOperator(char op)								/* 입력값이 연산자라면 true, 아니면 false 반환 */
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
	
	public boolean isOperation(char op)								/* 입력값이 피연산자라면 true, 아니면 false 반환 */
	{
		if (op >= '0' && op <= '9')
		{
			return true;
		}
		else if (op == '－')
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public int checkPrecedence(char op)								/* 연산자 우선순위 설정(Ver_Character) */
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
	
	public int checkPrecedence(String str)							/* 연산자 우선순위 설정(Ver_String) */
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
	
	public void postfix(String exp)									/* 중위표기법을 후위표기법으로 바꿔주는 메소드 */
	{
		calculatorStack.clear();
		postfixList.clear();

		for (int i = 0; i < exp.length(); i++)
		{
			char ch = exp.charAt(i);

			if (ch == '(')											/* 열림 괄호가 입력시 Stack에 입력 */
			{
				temp = "";
				temp += ch;
				calculatorStack.push(temp);
			}
			else if (ch == ')')										/* 닫힘 괄호가 입력시 열림 괄호가 나올떄까지 pop */
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
			else if (isOperator(ch))								/* 연산자 입력시 우선순위 체크 후 Stack에 입력된 연산자가 방금 입력된 연산자보다 우선순위가 높다면 List에 저장 */
			{
				while ((!calculatorStack.empty()) && (checkPrecedence((String)calculatorStack.peek()) >= checkPrecedence(ch)))
				{
					postfixList.add(calculatorStack.pop());
				}
				temp = "";
				temp += ch;
				calculatorStack.push(temp);
			}
			else if (isOperation(ch) || ch == '.' || ch == '－')		/* 피연산자 입력시 피연산자 값을 List에 저장 */
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
				} while (isOperation(ch) || ch == '.' || ch == '－');
				postfixList.add(temp);
				i--;
			}
		}

		while (!calculatorStack.empty())							/* Stack에 저장된 연산자들을 모두 List에 저장 */
		{
			temp = "";
			temp = (String)calculatorStack.pop();
			postfixList.add(temp);
		}
	}
	
	public String calculate()										/* 후위표기법으로 변환된 식을 계산해 결과값을 내는 메소드 */
	{
		calculatorStack.clear();
		double ddst, dsrc;
		
		while (!postfixList.isEmpty())
		{
			temp = "";
			temp = (String)postfixList.get(0);
			postfixList.remove(0);									/* List에 저장된 값을 하나씩 뺌(빼낸 값은 삭제) */

			if (isOperation(temp.charAt(0)))						/* 피연산자면 push */
			{
				calculatorStack.push(temp);
			}
			else
			{
				String dst = (String)calculatorStack.pop();
				String src = (String)calculatorStack.pop();
	
				if (dst.charAt(0) == '－')							/* 음수부호 －가 있는 피연산자인 경우, － 삭제 후 -1을 곱한다. */
				{
					dst = dst.substring(1);
					ddst = Double.parseDouble(dst) * (-1);
				}
				else 
				{
					ddst = Double.parseDouble(dst);
				}
					
				if (src.charAt(0) == '－')
				{
					src = src.substring(1);
					dsrc = Double.parseDouble(src) * (-1);
				}
				else
				{
					dsrc = Double.parseDouble(src);
				}
				
				if (temp.charAt(0) == '*')							/* 연산자라면 피연산자를 pop해서 연산 후 결과값 다시 Stack에 저장 */
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
	
	public boolean bracketsCheck(String exp)                        /* 수식 체크 : 연산식 안에 단순히 괄호의 짝 갯수만 맞나 확인해봄 */
	{
		checkStack.clear();

		for(int i = 0; i < exp.length(); i++)
		{
			char ch = exp.charAt(i);

			if (ch == '(')
			{
				checkStack.push(ch);								/* 괄호가 있다면, 제대로 닫혔나 확인하기 위해 push */
			}
			else if (ch == ')')
			{
				if (checkStack.isEmpty())							/* 닫힘괄호만 쓴 경우 false */
				{
					return false;
				}

				char checkInTheStack = (Character)checkStack.pop().charValue();

				if ((ch == ')') && checkInTheStack != '(')			/* 괄호의 짝이 안맞으면 false */
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
			return false;											/* Stack안에 다른 내용이 있다면 검사 오류 false */
		}
	}
	
	public boolean formulaCheck()									/* 수식 체크 : 피연산자와 연산자의 갯수를 확인해 연산식의 유효성 검사 */
	{
		int numberOfOperation = 0;

		for (int i = 0; i < postfixList.size(); i++)
		{
			temp = "";
			temp = (String) postfixList.get(i);
			char ch = temp.charAt(0);

			while (ch == '(' || ch == ')')							/* 괄호를 제외한 연산자와 피연산자의 갯수를 계산함 */
			{
				i++;
				temp = (String) postfixList.get(i);
				ch = temp.charAt(0);
			}
			
			if (isOperator(ch))										/* 연산자가 나타나면 피연산자의 갯수를 하나 줄임 */
			{
				numberOfOperation--;
			}
			else													/* 피연산자가 나타나면 피연산자의 갯수를 하나 늘림 */
			{
				numberOfOperation++;
				ch = temp.charAt(0);
			}
		}

		if (numberOfOperation == 1)									/* 피연산자의 갯수가 연산자보다 1개 많아야 정삭 수식 */
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	private void initButtonSetting() 								/* 버튼 셋팅(디자인, 여백, 글꼴), 마우스리스너 등록 */
	{
		buttonPanel = new JPanel();														/* Button Panel, GridLayout적용 */

		buttonPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));				/* 바깥 여백(위, 왼쪽, 아래, 오른쪽) 지정 */
		buttonPanel.setBackground(buttonbgcolor);										/* buttonPanel라는 패널에 배경색 적용 */

		jbButton = new JButton[keypadArr.length];										/* jbButton 버튼 배열 초기화 */

		for (int i = 0; i < keypadArr.length; i++)										/* jbButton에 표기할 값들 적용 */
		{
			jbButton[i] = new JButton(keypadArr[i]);
			jbButton[i].setBorderPainted(false);										/* 버튼 테두리 삭제 */
			jbButton[i].setFont(new Font("맑은고딕", Font.BOLD, 40));						/* 맑은고딕체, 글자 굵게, 글자크기 30 */

			if (i == 0 || i == 3 || i == 15)
			{
				jbButton[i].setFont(new Font("맑은고딕", Font.BOLD, 35));					/* 맑은고딕체, 글자 굵게, 글자크기 25 */
			}

			if (i == 0)																	/* C, CE 버튼 디자인(배경색, 글씨색) 설정 */
			{
				jbButton[i].setForeground(Color.white);
				jbButton[i].setBackground(buttoncolor1);
			}
			else if ((i >= 1 && i <= 3) || ((i % 4 == 3) && i != 19))					/* 연산자 버튼 디자인(배경색, 글씨색) 설정 */
			{
				jbButton[i].setForeground(Color.white);
				jbButton[i].setBackground(buttoncolor2);
			}
			else if (i == 19)															/* = 버튼 디자인(배경색, 글씨색) 설정 */
			{
				jbButton[i].setForeground(Color.white);
				jbButton[i].setBackground(buttoncolor3);
			}
			else																		/* 피연산자 버튼 디자인(배경색, 글씨색) 설정 */
			{
				jbButton[i].setForeground(Color.black);
				jbButton[i].setBackground(buttoncolor4);
			}
			
			jbButton[i].addActionListener(new ButtonListener());						/* 마우스 리스너 등록 */
		}
	}
	
	private void initButtonPanel() 									/* GridLayout인 KeyPad를 버튼 판넬에 등록 */
	{
		grid = new GridLayout(5, 4, 5, 5);							/* GridLayout의 행, 렬, 수평여백, 수직여백 지정 */
		buttonPanel.setLayout(grid);
	      
		for (int i = 0; i < keypadArr.length; i++)
		{
			buttonPanel.add(jbButton[i]);
		}
	}
	
	private void initResultWindow() 								/* GridLayout인 History와 Input을 결과 판넬에 등록 */
	{
		resultPanel = new JPanel(new GridLayout(2, 1));							/* Result Panel, GridLayout적용 */
		resultPanel.setBackground(buttonbgcolor);								/* resPanel라는 패널에 배경색 적용 */
		resultPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 10, 5));	/* 바깥 여백(위, 왼쪽, 아래, 오른쪽) 지정 */

		historyLabel = new JLabel("", JLabel.RIGHT);							/* 값 없는 레이블, 우측정렬 */
		historyLabel.setFont(new Font("맑은고딕", Font.PLAIN, 30));				/* 맑은고딕체, Plain양식, 크기 18 */
		historyLabel.setBackground(resultbgcolor);
		historyLabel.setOpaque(true);

		inputLabel = new JLabel("0", JLabel.RIGHT);								/* 기본값(0)인 레이블, 우측 정렬 */
		inputLabel.setFont(new Font("맑은고딕", Font.PLAIN, 30));					/* 맑은고딕체, 글자 굵게, 크기 18 */
		inputLabel.setBackground(resultbgcolor);
		inputLabel.setOpaque(true);

		resultPanel.add(historyLabel);
		resultPanel.add(inputLabel);
	}  
	
	public static void main(String[] args) 
	{
		new Calculator();														/* Calculator 객체 생성 */
	}
}