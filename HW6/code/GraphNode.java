import java.util.ArrayList;

public class GraphNode<K, V> {
    public V value;
    public int distance = Graph.INF;
    public ArrayList<ArrayListElement<K>> adjacencyList = new ArrayList<>();

    GraphNode(V v) {
        value = v;
    }

    public void addAdjacency(int weight, K key) {
        adjacencyList.add(new ArrayListElement<>(weight, key));
    }
}