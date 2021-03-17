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

    private char[] value = new char[200];
  
    public BigInteger(int input)
    {
        this.putArrayIntoValue(Integer.toString(input).toCharArray());
    }
  
    public BigInteger(String s)
    {
        this.putArrayIntoValue(s.toCharArray());
    }

    private void putArrayIntoValue(char[] array) {
        int length = array.length;
        for (int i=0; i<length; i++){
            this.value[i] = array[length-i-1];
        }
    }
  
//    public BigInteger add(BigInteger big)
//    {
//        BigInteger result = big;
//    }
//
//    public BigInteger subtract(BigInteger big)
//    {
//    }
//
//    public BigInteger multiply(BigInteger big)
//    {
//    }
  
//    @Override
//    public String toString()
//    {
//    }
//
//    static BigInteger evaluate(String input) throws IllegalArgumentException
//    {
//
//    }
  
    public static void main(String[] args) throws Exception
    {
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
