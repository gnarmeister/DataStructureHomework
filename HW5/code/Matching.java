import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;

public class Matching
{
	static NewHashTable<Integer, StringHashValue, String> hashTable = new NewHashTable<>();
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
		char indicator = input.charAt(0);
		switch (indicator) {
			case '<':
				read(input.substring(2));
				break;
			case '@':
				print(input.substring(2));
				break;
			case '?':
				search(input.substring(2));
				break;
		}
	}

	private static void read(String location) {
		// 파일 읽어오기
		try {
			hashTable = new NewHashTable<>();
			FileReader reader = new FileReader(location);
			BufferedReader bufferReader = new BufferedReader(reader);
			String readLine;
			int lineIndex = 1;
			while ((readLine = bufferReader.readLine()) != null ) {
				for (int i=0; i<=readLine.length()-6; i++) {
					hashTable.add(new StringHashValue(readLine.substring(i, i+6)), lineIndex+", "+(i+1));
				}
				lineIndex++;
			}
		} catch (IOException e) {
			System.out.println(e);
		}
	}

	private static void print(String index) {
		// 주어진 key 에 해당하는 AVLTree 의 모든 노드를 출력
		LinkedList<StringHashValue> itemList = hashTable.getItems(Integer.parseInt(index));
		if (itemList == null) {
			System.out.println("EMPTY");
			return;
		}
		String result = "";
		for (StringHashValue item : itemList) {
			result = result + item + " ";
		}
		result = result.substring(0, result.length()-1);
		System.out.println(result);
	}

	private static void search(String pattern) {
		// 패턴을 검색하여 일치하는 부분의 좌표 출력
		StringHashValue patternPart = new StringHashValue(pattern.substring(0, 6));
		AVLNode<StringHashValue, String> possibleLocation = hashTable.search(patternPart);
		if (possibleLocation == null) {
			// pattern 의 첫 6글자 없으면 (0, 0) 출력 후 종료
			System.out.println("(0, 0)");
			return;
		}
		ArrayList<String> possibleLocationList = new ArrayList<>(possibleLocation.indexList);
		AVLNode<StringHashValue, String> nextLocation;
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
				for (String index : new ArrayList<>(possibleLocationList)) {
					temp = index.split(", ")[0] + ", " + (Integer.parseInt(index.split(", ")[1]) + i);
					if (!nextLocation.indexList.contains(temp)) {
						possibleLocationList.remove(index);
					}
				}
			}
		}
		if (possibleLocationList.size() == 0) {
			// 남은 좌표 없으면 (0, 0) 출력 후 종료
			System.out.println("(0, 0)");
			return;
		}

		String result = "("+possibleLocationList.get(0)+")";
		for (int i=1; i<possibleLocationList.size(); i++) {
			result = result + " (" + possibleLocationList.get(i) + ")";
		}
		System.out.println(result);
	}
}