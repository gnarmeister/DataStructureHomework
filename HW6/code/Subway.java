import java.io.*;
import java.util.*;

public class Subway {
    static Hashtable<String, GraphNode<String, String>> graph = new Hashtable<>();
    static Hashtable<String, ArrayList<String>> listOfStations = new Hashtable<>(); // 역 이름을 key, 고유 번호의 리스트를 value 로 하는 hash table
    static ArrayList<GraphNode<String, String>> S = new ArrayList<>();
    static ArrayList<GraphNode<String, String>> adjacencyOfS = new ArrayList<>();

    private static void readAndSaveData(String fileName) {
        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String r;
            String[] splitInput;

            // 역 목록 그래프에 저장
            while (!(r = bufferedReader.readLine()).equals("")) {
                splitInput = r.split(" "); // [id, station name, line]
                graph.put(splitInput[0], new GraphNode<>(splitInput[1]));
                if (listOfStations.containsKey(splitInput[1])) {
                    // 이 역이 이미 있었다면(환승역 존재) weight 5 양방향 adjacency 추가
                    for (String transferStation : listOfStations.get(splitInput[1])) {
                        graph.get(transferStation).addAdjacency(5, splitInput[0]);
                        graph.get(splitInput[0]).addAdjacency(5, transferStation);
                    }
                    listOfStations.get(splitInput[1]).add(splitInput[0]);
                } else {
                    listOfStations.put(splitInput[1], new ArrayList<>(Collections.singletonList(splitInput[0])));
                }
            }

            // 인접한 역 사이 weight 저장
            while ((r = bufferedReader.readLine()) != null) {
                splitInput = r.split(" ");
                graph.get(splitInput[0]).addAdjacency(Integer.parseInt(splitInput[2]), splitInput[1]);
            }
        } catch (IOException e) {
            System.out.println(e.toString());
        }
    }

    private static void findShortestPath(String origin, String destination) {
        // graph distance, prev, S, adjacencyOfS 초기화
        GraphNode<String, String> temp;
        for (String key : graph.keySet()) {
            temp = graph.get(key);
            temp.distance = GraphNode.INF;
            temp.prev = null;
        }
        S = new ArrayList<>();
        adjacencyOfS = new ArrayList<>(); // S와 인접한 노드를 저장

        GraphNode<String, String> originNode;
        GraphNode<String, String> lastAdded = new GraphNode<>("");
        for (String originKey : listOfStations.get(origin)) {
            // 환승역인 경우, 해당 역 이름의 모든 노드 distance 0 으로 만듦
            originNode = graph.get(originKey);
            originNode.distance = 0;
            lastAdded = markNode(originNode);
        }

        while (!lastAdded.value.equals(destination)) {
            lastAdded = markNode(findMinimumDistance());
        }
        int time = lastAdded.distance;

        Stack<String> route = new Stack<>();
        while (!lastAdded.value.equals(origin)) {
            if (!route.isEmpty() && route.peek().equals(lastAdded.value)) {
                route.pop();
                route.push("[" + lastAdded.value + "]");
            } else {
                route.push(lastAdded.value);
            }
            lastAdded = lastAdded.prev;
        }
        System.out.print(origin);
        while (!route.isEmpty()) {
            System.out.print(" "+route.pop());
        }
        System.out.println();
        System.out.println(time-lastAdded.distance);
    }

    private static GraphNode<String, String> markNode(GraphNode<String, String> target) {
        // 해당 target node 를 S에 포함하고 이에 맞게 adjacencyOfS도 업데이트
        S.add(target);
        adjacencyOfS.remove(target);

        GraphNode<String, String> adjacentNode;
        for (ArrayListElement<String> adjacency : target.adjacencyList) {
            adjacentNode = graph.get(adjacency.key);
            if (adjacentNode.distance == GraphNode.INF || adjacentNode.distance > target.distance + adjacency.weight) {
                adjacentNode.distance = target.distance + adjacency.weight;
                adjacentNode.prev = target;
            }
            if (!S.contains(adjacentNode) && !adjacencyOfS.contains(adjacentNode)) {
                adjacencyOfS.add(adjacentNode);
            }
        }
        return target;
    }

    private static GraphNode<String, String> findMinimumDistance() {
        // adjacencyOfS 중 distance 가 최소인 노드 골라 리턴
        int minimum = GraphNode.INF;
        GraphNode<String, String> minimumNode = adjacencyOfS.get(0);
        for (GraphNode<String, String> temp : adjacencyOfS) {
            if (minimum == GraphNode.INF || temp.distance < minimum) {
                minimum = temp.distance;
                minimumNode = temp;
            }
        }
        return minimumNode;
    }

    public static void main(String[] args) {
        String fileName = args[0];
        readAndSaveData(fileName);

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            try {
                String input = br.readLine();
                if (input.compareTo("QUIT") == 0)
                    break;
                String origin = input.split(" ")[0];
                String destination = input.split(" ")[1];
                findShortestPath(origin, destination);
            }
            catch (IOException e) {
                System.out.println(e.toString());
            }
        }
    }
}
