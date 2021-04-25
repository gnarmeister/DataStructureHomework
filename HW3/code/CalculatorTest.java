import java.io.*;
import java.util.Stack;
import java.util.StringJoiner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CalculatorTest
{
	public static void main(String args[])
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		while (true)
		{
			try
			{
				String input = br.readLine();
				if (input.compareTo("q") == 0)
					break;

				command(input);
			}
			catch (Exception e)
			{
				System.out.println("ERROR");
			}
		}
	}

	private static void command(String input) throws Exception {
		String postfix = inputToPostfix(input);
		long result = calculate(postfix);
		System.out.println(postfix);
		System.out.println(result);
	}

	private static int getPriority(char operator) {
		int priority;
		switch (operator) {
			case '^':
				priority = 1;
				break;
			case '~':
				priority = 2;
				break;
			case '*':
			case '/':
			case '%':
				priority = 3;
				break;
			case '+':
			case '-':
				priority = 4;
				break;
			default:
				priority = 5;
				break;
		}
		return priority;
	}

	private static long doOperation(String operator, long B, long A) {
		long result;
		switch (operator) {
			case "+":
				result = A + B;
				break;
			case "-":
				result = A - B;
				break;
			case "*":
				result = A * B;
				break;
			case "/":
				result = A / B;
				break;
			case "%":
				result = A % B;
				break;
			case "^":
				result = (long) Math.pow(A, B);
				break;
			default:
				result = 0;
				break;
		}
		return result;
	}

	private static String inputToPostfix(String input) throws Exception {
		// convert input to postfix expression
		Pattern regExp = Pattern.compile("([-+*^%()/])|([0-9]+)");
		Matcher matcher = regExp.matcher(input);

		Stack<Character> operators = new Stack<>();
		StringJoiner result = new StringJoiner(" ");
		String temp;
		boolean shouldBeNumber = true; // 다음 패턴이 연산자일지 숫자일지 판단

		while (matcher.find()) {
			temp = matcher.group(0);
			switch (temp) {
				case "(":
					if (shouldBeNumber) {
						operators.push('(');
					} else {
						throw new Exception();
					}
					break;

				case ")":
					if (shouldBeNumber) {
						throw new Exception();
					} else {
						Character currentOperator = operators.pop();
						while (currentOperator != '(') {
							result.add(currentOperator.toString());
							currentOperator = operators.pop();
						}
					}
					break;

				case "^":
					if (shouldBeNumber) {
						throw new Exception();
					} else {
						operators.push('^');
						shouldBeNumber = true;
					}
					break;

				case "-":
					if (shouldBeNumber) {
						operators.push('~');
						break;
					}

				case "+":
				case "/":
				case "*":
				case "%":
					if (shouldBeNumber) {
						throw new Exception();
					} else {
						while (!operators.isEmpty() && getPriority(operators.peek())<=getPriority(temp.charAt(0))) {
							result.add(operators.pop().toString());
						}
						operators.push(temp.charAt(0));
						shouldBeNumber = true;
					}
					break;

				default:
					if (!shouldBeNumber) {
						throw new Exception();
					}
					result.add(temp);
					shouldBeNumber = false;
			}
		}

		while (!operators.isEmpty()) {
			result.add(operators.pop().toString());
		}

		return result.toString();
	}

	private static long calculate(String postfix) throws Exception {
		// calculate postfix expression
		String[] postfixToArray = postfix.split(" ");
		Stack<Long> numbers = new Stack<>();

		for (String terms: postfixToArray) {
			switch (terms) {
				case "^":
					if (numbers.peek() < 0) throw new Exception();
				case "+":
				case "-":
				case "*":
				case "/":
				case "%":
					numbers.push(doOperation(terms, numbers.pop(), numbers.pop()));
					break;
				case "~":
					numbers.push(numbers.pop() * (-1));
					break;
				default:
					numbers.push(Long.parseLong(terms));
					break;
			}
		}

		return numbers.pop();
	}
}
