import java.io.*;
import java.util.*;

public class Subway {
    static Hashtable<String, GraphNode<String, String>> graph = new Hashtable<>();

    private static void command() {

    }

    private static void readAndSaveData(String fileName) {
        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String r;
            String[] splitInput;

            // 역 목록 그래프에 저장
            Hashtable<String, ArrayList<String>> listOfStations = new Hashtable<>(); // 역 이름을 key, 고유 번호의 리스트를 value 로 하는 hash table
            while (!(r = bufferedReader.readLine()).equals("")) {
                splitInput = r.split(" "); // [id, station name, line]
                graph.put(splitInput[0], new GraphNode<>(splitInput[1]));
                if (listOfStations.containsKey(splitInput[1])) {
                    // 이 역이 이미 있었다면(환승역 존재) weight 5 양방향 adjacency 추가
                    for (String transferStation : listOfStations.get(splitInput[0])) {
                        graph.get(transferStation).addAdjacency(5, splitInput[0]);
                        graph.get(splitInput[0]).addAdjacency(5, transferStation);
                    }
                } else {
                    listOfStations.put(splitInput[1], new ArrayList<String>(Collections.singletonList(splitInput[0])));
                }
            }

            // 인접한 역 사이 weight 저장
            while ((r = bufferedReader.readLine()) != null) {
                splitInput = r.split(" ");
                graph.get(splitInput[0]).addAdjacency(Integer.parseInt(splitInput[2]), splitInput[1]);
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public static void main(String[] args) {
        String fileName = args[0];
        readAndSaveData(fileName);

    }
}
