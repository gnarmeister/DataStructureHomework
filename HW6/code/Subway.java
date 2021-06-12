import java.io.*;
import java.util.Stack;

public class Subway {
    static Graph<String, String> routeMap = new Graph<>();

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
                printShortestRoute(origin, destination);
            }
            catch (IOException e) {
                System.out.println(e.toString());
            }
        }
    }

    private static void readAndSaveData(String fileName) {
        // 파일을 읽어와 저장
        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String r;
            String[] splitInput;

            // 역 목록 그래프에 저장
            while (!(r = bufferedReader.readLine()).equals("")) {
                splitInput = r.split(" "); // [id, station name, line]
                routeMap.put(splitInput[0], splitInput[1]);
            }

            // 인접한 역 사이 weight 저장
            while ((r = bufferedReader.readLine()) != null) {
                splitInput = r.split(" ");
                routeMap.addEdge(splitInput[0], splitInput[1], Integer.parseInt(splitInput[2]));
            }
        } catch (IOException e) {
            System.out.println(e.toString());
        }
    }

    public static void printShortestRoute(String origin, String destination) {
        // 시작점과 도착점이 주어지면 가장 짧은 경로와 시간 출력
        GraphNode<String, String> lastAdded = routeMap.findShortestPath(origin, destination);
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
}
