import java.nio.charset.StandardCharsets;

public class StringHashValue implements HashValue<Integer>, Comparable<StringHashValue> {
    // String 이 hashFunction 과 comparable 을 만족하도록 새로운 클래스 정의
    String value;

    StringHashValue(String input) {
        value = input;
    }

    @Override
    public Integer hashFunction() {
        byte[] ascii = value.getBytes(StandardCharsets.US_ASCII);
        int sum = 0;
        for (int number : ascii) {
            sum += number;
        }
        return sum%100;
    }

    @Override
    public int compareTo(StringHashValue o) {
        return value.compareTo(o.value);
    }

    public String toString() {
        return value;
    }
}
