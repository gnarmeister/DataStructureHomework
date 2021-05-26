import java.io.*;
import java.util.ArrayList;
import java.util.Queue;

public class Matching
{
	static NewHashTable<Integer, StringHashValue> hashTable = new NewHashTable<>();
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

	private static void read(String location) {
		try {
			hashTable = new NewHashTable<>();
			FileReader reader = new FileReader(location);
			BufferedReader bufferReader = new BufferedReader(reader);
			String readLine;
			int lineIndex = 1;
			while ((readLine = bufferReader.readLine()) != null ) {
				for (int i=0; i<readLine.length()-6; i++) {
					hashTable.add(new StringHashValue(readLine.substring(i, i+6)), "("+lineIndex+", "+(i+1)+")");
				}
			}
		} catch (IOException e) {
			System.out.println(e);
		}
	}

	private static void print(String index) {
		Queue<StringHashValue> itemList = hashTable.getItems(Integer.parseInt(index));
		for (int i=0; i<itemList.size()-1; i++) {
			System.out.print(itemList.poll().value);
			System.out.print(" ");
		}
		System.out.println(itemList.poll().value);
	}

	private static void search(String pattern) {
		StringHashValue patternPart = new StringHashValue(pattern.substring(0, 6));
		AVLNode<StringHashValue> possibleLocation = hashTable.search(patternPart);
		if (possibleLocation == null) {
			// pattern 의 첫 6글자 없으면 (0, 0) 출력 후 종료
			System.out.println("(0, 0)");
			return;
		}
		ArrayList<String> possibleLocationList = new ArrayList<>(possibleLocation.indexList);
		AVLNode<StringHashValue> nextLocation;
		String temp;
		for (int i=1; i<=pattern.length()-6; i++) {
			// pattern 의 substring 들로 재검색
			patternPart.value = pattern.substring(i, i+6);
			nextLocation = hashTable.search(patternPart);
			if (nextLocation == null) {
				// 재검색 결과 없으면 (0, 0) 출력 후 종료
				System.out.println("(0, 0)");
				return;
			} else {
				for (String index : possibleLocationList) {
					temp = index.substring(0, 4) + (Integer.parseInt(index.substring(4, 5)) + i) + ")";
					if (!nextLocation.indexList.contains(temp)) {
						possibleLocationList.remove(index);
					}
				}
			}
		}
		String result = possibleLocationList.get(0);
		for (int i=1; i<possibleLocationList.size(); i++) {
			result = result + " " + possibleLocationList.get(i);
		}
		System.out.println(result);
	}
}