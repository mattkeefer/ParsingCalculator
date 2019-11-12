package main;

public class Parser {

	private int num1;
	private int num2;
	private String input;
	private char[] equation;
	private String condensed;
	private char operation;
	
	public Parser() {
		num1 = 0;
		num2 = 0;
		input = "";
		condensed = "";
	}
	
	private void setInput(String str) throws formatException {
		input = str;
		condenseString();
		//initial error checking
		if(condensed.equals("=")) {
			throw new formatException("There is no operation being performed");
		}
		if(isValidOperator(equation[equation.length-1])) {
			throw new formatException("There is no second number following the operator");
		}
		if(condensed.indexOf('=')==-1) {
			throw new formatException(input);
		}
		if(condensed.indexOf('=')!=0) {
			throw new formatException("Equation must start with =");
		}
		int holder = condensed.indexOf('=');
		if(condensed.indexOf('=', holder+1)!=-1) {
			throw new formatException("There are too many =");
		}
	}
	
	private void condenseString() { //deletes white space within string --> stores in "condensed"
		equation = input.replaceAll(" ", "").toCharArray();
		condensed = new String(equation);
	}
	
	private boolean isValidOperator(char ch) {
		if(ch=='+' || ch=='-' || ch=='*' || ch=='/') {
			return true;
		}
		return false;
	}
	
	private void findTerms() throws formatException {
		String numString1 = "";
		String numString2 = "";
		boolean one = true;
		for(int i=1; i<equation.length; i++) {
			if(Character.isDigit(equation[i])) {
				if(one) {
					numString1 += equation[i];
				}
				else {
					numString2 += equation[i];
				}
			}
			else if(isValidOperator(equation[i])) {
				if(one && i!=1) {
					operation = equation[i];
					one = false;
				}
				else if(i==1) {
					numString1 += equation[i];
				}
				else {
					numString2 += equation[i];
				}
			}
			else {
				throw new formatException(String.format("%c is not a valid input", equation[i]));
			}
		}
		try {
			if(numString2.charAt(0)=='+') {
				throw new formatException("Invalid operation, do not use + to indicate positive");
			}
			num1 = Integer.parseInt(numString1); //converts strings to integer values
			num2 = Integer.parseInt(numString2);
		}
		catch(NumberFormatException e) {
			if(!(isValidOperator(operation))) {
				throw new formatException("There is no operation being performed");
			}
			int holder = condensed.indexOf(operation);
			if(condensed.indexOf(operation, holder)!=-1) {
				throw new formatException("Too many operators");
			}
			throw new formatException("Invalid equation, please try again");
		}
	}
	
	public String doTheMath(String str) throws formatException {
		setInput(str);
		findTerms();
		if(operation=='+') {
			return String.format("%d", num1+num2); //addition
		}
		else if(operation=='-') {
			return String.format("%d", num1-num2); //subtraction
		}
		else if(operation=='/') {
			double div = (double)num1/(double)num2;
			if(div==(int)div) {
				return String.format("%d", (int)div); //division - standard
			}
			else {
				return String.format("%.2f", div); //division - rounds to 2 decimal places
			}
		}
		else if(operation=='*') {
			return String.format("%d", num1*num2); //multiplication
		}
		throw new formatException("There is no operation being performed");
	}
}