import java.util.ArrayList;

public class GraphNode<K, V> {
    // Graph 의 노드
    public static int INF = -1;
    public V value;
    public int distance = INF;
    public GraphNode<K, V> prev = null;
    public ArrayList<ArrayListElement<K>> adjacencyList = new ArrayList<>();

    GraphNode() {
        value = null;
    }

    GraphNode(V v) {
        value = v;
    }

    public void addAdjacency(int weight, K key) {
        // 노드의 adjacency 추가
        adjacencyList.add(new ArrayListElement<>(weight, key));
    }
}