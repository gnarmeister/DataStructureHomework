import java.util.ArrayList;

public class GraphNode<K, V> {
    // Graph 의 노드
    public K key;
    public V value;
    public int distance = Graph.INF;
    public ArrayList<ArrayListElement<K>> adjacencyList = new ArrayList<>();

    GraphNode(K k, V v) {
        key = k;
        value = v;
    }

    public void addAdjacency(int weight, K key) {
        // 노드의 adjacency 추가
        adjacencyList.add(new ArrayListElement<>(weight, key));
    }
}