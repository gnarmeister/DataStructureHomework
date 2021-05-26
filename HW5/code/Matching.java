import java.io.*;

public class Matching
{
	static NewHashTable<Integer, StringHashValue> hashTable;
	public static void main(String args[])
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		while (true)
		{
			try
			{
				String input = br.readLine();
				if (input.compareTo("QUIT") == 0)
					break;

				command(input);
			}
			catch (IOException e)
			{
				System.out.println("입력이 잘못되었습니다. 오류 : " + e.toString());
			}
		}
	}

	private static void command(String input) throws FileNotFoundException {
		String[] parsedInput = input.split(" ");
		switch (parsedInput[0]) {
			case "<":
				read(parsedInput[1]);
				break;
			case "@":
				print(parsedInput[1]);
				break;
			case "?":
				search(parsedInput[1]);
				break;
		}
	}

	private static void read(String location) throws FileNotFoundException {
		try {
			FileReader reader = new FileReader(location);
			BufferedReader bufferReader = new BufferedReader(reader);
			String readLine;
			int lineIndex = 1;
			while ((readLine = bufferReader.readLine()) != null ) {
				for (int i=0; i<readLine.length()-6; i++) {
					hashTable.add(new StringHashValue(readLine.substring(i, i+6-1)), "("+lineIndex+" "+(i+1)+")");
				}
			}
		} catch (IOException e) {
			System.out.println(e);
		}

	}

	private static void print(String index) {}

	private static void search(String pattern) {}
}