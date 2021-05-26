import java.nio.charset.StandardCharsets;

public class StringHashValue implements HashValue<Integer>, Comparable<StringHashValue> {
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
}
