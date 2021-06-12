import java.util.*;

public class Graph<K, V> {
    Hashtable<K, GraphNode<K, V>> nodes;
    Hashtable<V, ArrayList<K>> listOfValue; // 역 이름을 key, 고유 번호의 리스트를 value 로 하는 hash table
    private ArrayList<GraphNode<K, V>> S; // 다익스트라에서 사용하는 S
    private ArrayList<GraphNode<K, V>> adjacencyOfS; // S와 인접한 노드들의 리스트

    Graph() {
        nodes = new Hashtable<>();
        listOfValue = new Hashtable<>();
        S = new ArrayList<>();
        adjacencyOfS = new ArrayList<>();
    }

    public void addEdge(K origin, K destination, int weight) {
        // 두 노드를 꼭지점으로 하고 weight 를 가지는 edge 추가
        nodes.get(origin).addAdjacency(weight, destination);
    }

    public void put(K key, V value) {
        // 노드 추가
        nodes.put(key, new GraphNode<>(value));
        if (listOfValue.containsKey(value)) {
            // 이 value 가 이미 있었다면(환승역 존재) weight 5인 양방향 edge 추가
            for (K transferStation : listOfValue.get(value)) {
                addEdge(transferStation, key, 5);
                addEdge(key, transferStation, 5);
            }
            listOfValue.get(value).add(key);
        } else {
            listOfValue.put(value, new ArrayList<>(Collections.singletonList(key)));
        }
    }

    public GraphNode<K, V> findShortestPath(V origin, V destination) {
        // 각 노드들의 distance 를 origin 에서부터 최소 거리로 만들고 prev 갱신
        // 그리고 destination 에 해당하는 노드 리턴
        GraphNode<K, V> temp;
        for (K key : nodes.keySet()) {
            temp = nodes.get(key);
            temp.distance = GraphNode.INF;
            temp.prev = null;
        }
        S = new ArrayList<>();
        adjacencyOfS = new ArrayList<>();

        GraphNode<K, V> originNode;
        GraphNode<K, V> lastAdded = new GraphNode<>();
        for (K originKey : listOfValue.get(origin)) {
            // origin 이 환승역인 경우, 해당 역 이름을 value 로 가지는 모든 노드의 distance 를 0으로 만듦
            originNode = nodes.get(originKey);
            originNode.distance = 0;
            lastAdded = markNode(originNode);
        }

        while (!lastAdded.value.equals(destination)) {
            lastAdded = markNode(findMinimumDistance());
        }

        return lastAdded;
    }

    private GraphNode<K, V> markNode(GraphNode<K, V> target) {
        // 해당 target node 를 S에 포함시키고 이에 맞게 adjacencyOfS도 업데이트
        S.add(target);
        adjacencyOfS.remove(target);

        GraphNode<K, V> adjacentNode;
        for (ArrayListElement<K> adjacency : target.adjacencyList) {
            adjacentNode = nodes.get(adjacency.key);
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

    private GraphNode<K, V> findMinimumDistance() {
        // adjacencyOfS 중 distance 가 최소인 노드 골라 리턴
        int minimum = GraphNode.INF;
        GraphNode<K, V> minimumNode = adjacencyOfS.get(0);
        for (GraphNode<K, V> temp : adjacencyOfS) {
            if (minimum == GraphNode.INF || temp.distance < minimum) {
                minimum = temp.distance;
                minimumNode = temp;
            }
        }
        return minimumNode;
    }
}
