import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
  
  
public class BigInteger
{
    public static final String QUIT_COMMAND = "quit";
    public static final String MSG_INVALID_INPUT = "입력이 잘못되었습니다.";
  
    // implement this
    public static final Pattern EXPRESSION_PATTERN = Pattern.compile("");

    private final char[] value = new char[200];
    private int sign = 1;
  
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
        for (int i=0; i<length; i++) {
            value[i] = array[length-i-1];
        }
        for (int i=length; i<200; i++) {
            value[i] = '0';
        }
    }

    public void reverseSign() {
        sign *= -1;
    }
  
    public BigInteger add(BigInteger big)
    {
        if (sign != big.sign) {
            big.reverseSign();
            return subtract(big);
        }
        BigInteger result = new BigInteger(0);
        result.sign = sign;
        int temp = 0;
        for (int i=0; i<200; i++) {
            temp += (value[i]-'0') + (big.value[i]-'0');
            result.value[i] = (char) (temp%10);
            temp /= 10;
        }
        return big;
    }

    public BigInteger subtract(BigInteger big)
    {
        if (sign != big.sign) {
            big.reverseSign();
            return add(big);
        }
        return big;
    }

    public BigInteger multiply(BigInteger big)
    {
        return big;
    }
  
//    @Override
//    public String toString()
//    {
//    }

//    static BigInteger evaluate(String input) throws IllegalArgumentException
//    {
//        input.split("+|*|-");
//    }
  
    public static void main(String[] args) throws Exception
    {
        BigInteger a = new BigInteger("10000000000000000");
        BigInteger b = new BigInteger("200000000000000000");
        System.out.println(a.add(b));
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
