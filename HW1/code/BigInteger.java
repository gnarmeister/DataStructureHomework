import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
  
  
public class BigInteger
{
    public static final String QUIT_COMMAND = "quit";
    public static final String MSG_INVALID_INPUT = "입력이 잘못되었습니다.";
  
    // implement this
    public static final String VALUE_EXPRESSION = "[1-9][0-9]*|[0]";

    private final char[] value = new char[200];
    private int sign = 1;
    private int numberOfDigits = 0;
  
    public BigInteger(int input)
    {
        if (input < 0) {
            sign = -1;
        }
        putArrayIntoValue(Integer.toString(input*sign).toCharArray());
    }
  
    public BigInteger(String s)
    {
        char firstChar = s.charAt(0);
        if (firstChar == '+') {
            putArrayIntoValue(s.substring(1).toCharArray());
        } else if(firstChar == '-') {
            sign = -1;
            putArrayIntoValue(s.substring(1).toCharArray());
        } else {
            putArrayIntoValue(s.toCharArray());
        }
    }

    private void putArrayIntoValue(char[] array) {
        int length = array.length;
        numberOfDigits = length;
        for (int i=0; i<length; i++) {
            value[i] = array[length-i-1];
        }
    }

    public BigInteger reverseSign() {
        BigInteger result = new BigInteger(0);
        result.sign = sign*(-1);
        result.numberOfDigits = numberOfDigits;
        System.arraycopy(value, 0, result.value, 0, numberOfDigits);
        return result;
    }

    public boolean biggerValueThan(BigInteger big) {
        if (numberOfDigits > big.numberOfDigits) {
            return true;
        }
        if (big.numberOfDigits > numberOfDigits) {
            return false;
        }
        for (int i=numberOfDigits-1; i>=0; i--) {
            if (value[i] > big.value[i]) return true;
            if (value[i] < big.value[i]) return false;
        }
        return false;
    }
  
    public BigInteger add(BigInteger big)
    {
        if (sign != big.sign) {
            return subtract(big.reverseSign());
        }

        BigInteger result = new BigInteger(0);
        result.sign = sign;
        result.numberOfDigits = 0;

        int temp = 0;
        boolean indicator1 = true, indicator2 = true; // check value[i], big.value[i] is not null

        for (int i=0; i<200; i++) {
            if (value[i] == '\u0000') {
                indicator1 = false;
            } else {
                temp += (value[i]-'0');
            }
            if (big.value[i] == '\u0000') {
                indicator2 = false;
            } else {
                temp += (big.value[i]-'0');
            }
            if (!indicator1 && !indicator2) {
                if (temp != 0) {
                    result.value[i] = (char) (temp+'0');
                    result.numberOfDigits++;
                }
                break;
            }
            result.value[i] = (char) (temp%10+'0');
            result.numberOfDigits++;
            temp /= 10;
        }
        return result;
    }

    public BigInteger subtract(BigInteger big)
    {
        if (sign != big.sign) {
            return add(big.reverseSign());
        }

        if (!biggerValueThan(big)) {
            return big.subtract(this).reverseSign();
        }

        BigInteger result = new BigInteger(0);
        result.sign = sign;

        int temp = 0;
        int zeroStartIndex = 0;

        for (int i=0; i<numberOfDigits; i++) {
            temp += (value[i]-'0');
            if (big.value[i] != '\u0000') {
                temp -= (big.value[i]-'0');
            }
            if (temp<0) {
                temp+=10;
                result.value[i] = (char) (temp+'0');
                temp=-1;
            } else {
                result.value[i] = (char) (temp+'0');
                temp=0;
            }
            if (result.value[i] != '0') {
                zeroStartIndex = i+1;
            }
        }
        for (int i=zeroStartIndex; i<numberOfDigits; i++) {
            result.value[i] = '\u0000';
        }
        result.numberOfDigits = zeroStartIndex;
        return result;
    }

    public BigInteger multiply(BigInteger big)
    {
        return big;
    }
  
    @Override
    public String toString()
    {
        if (numberOfDigits==1 && value[0]=='0') return "0";
        char[] result;
        if (sign==-1) {
            result = new char[numberOfDigits + 1];
            result[0] = '-';
            for (int i=0; i<numberOfDigits; i++) {
                result[i+1] = value[numberOfDigits-i-1];
            }
        } else {
            result = new char[numberOfDigits];
            for (int i=0; i<numberOfDigits; i++) {
                result[i] = value[numberOfDigits-i-1];
            }
        }
        return new String(result);
    }

    static BigInteger evaluate(String input) throws IllegalArgumentException
    {
        String noSpace = input.replaceAll(" ", "");
        String[] operators = noSpace.split(VALUE_EXPRESSION);

        char[] secondOperators = operators[operators.length-1].toCharArray();
        char operator = secondOperators[0];

        Matcher valueMatcher = Pattern.compile(VALUE_EXPRESSION).matcher(noSpace);
        valueMatcher.find();
        String value1 = valueMatcher.group();
        valueMatcher.find();
        String value2 = valueMatcher.group();

        if (operators.length == 2) {
            value1 = operators[0] + value1;
        }

        if (secondOperators.length == 2) {
            value2 = secondOperators[1] + value2;
        }

        BigInteger big1 = new BigInteger(value1), big2 = new BigInteger(value2);

        switch (operator) {
            case '+':
                return big1.add(big2);

            case '-':
                return big1.subtract(big2);

            default:
                return big1.multiply(big2);
        }
    }
  
    public static void main(String[] args) throws Exception
    {

        System.out.println(evaluate("10000000000000000+200000000000000000"));
        System.out.println(evaluate("20000000000000000-100000000000000000"));
        System.out.println(evaluate("30000000000000000 - 200000000000000000"));
        System.out.println(evaluate("50000000 ++1000 "));
        System.out.println(evaluate("-1000000 + 0"));
//        try (InputStreamReader isr = new InputStreamReader(System.in))
//        {
//            try (BufferedReader reader = new BufferedReader(isr))
//            {
//                boolean done = false;
//                while (!done)
//                {
//                    String input = reader.readLine();
//
//                    try
//                    {
//                        done = processInput(input);
//                    }
//                    catch (IllegalArgumentException e)
//                    {
//                        System.err.println(MSG_INVALID_INPUT);
//                    }
//                }
//            }
//        }
    }
  
//    static boolean processInput(String input) throws IllegalArgumentException
//    {
//        boolean quit = isQuitCmd(input);
//
//        if (quit)
//        {
//            return true;
//        }
//        else
//        {
//            BigInteger result = evaluate(input);
//            System.out.println(result.toString());
//
//            return false;
//        }
//    }
  
    static boolean isQuitCmd(String input)
    {
        return input.equalsIgnoreCase(QUIT_COMMAND);
    }
}
