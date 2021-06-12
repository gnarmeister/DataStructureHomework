import java.util.ArrayList;

public class GraphNode<K, V> {
    public K key;
    public V value;
    public int distance = Graph.INF;
    public ArrayList<ArrayListElement<K>> adjacencyList = new ArrayList<>();

    GraphNode(K k, V v) {
        key = k;
        value = v;
    }

    public void addAdjacency(int weight, K key) {
        adjacencyList.add(new ArrayListElement<>(weight, key));
    }
}