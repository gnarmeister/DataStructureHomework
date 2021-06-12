import java.util.ArrayList;

public class Graph<K extends HashableKey, V> {
    public static int INF = -1;
    public int size;
    public int secondSize;
    public ArrayList<GraphNode<K, V>> nodes;

    Graph(GraphNode<K, V>[] inputNodes) {
        findProperSize(inputNodes.length);
        for (GraphNode<K, V> node : inputNodes) {
            add(node);
        }
    }

    public void add(GraphNode<K, V> node) {
        int key = node.key.hashFunction(size);
        int secondKey = node.key.hashFunction(secondSize);
        while (true) {
            if (nodes.get(key) == null) {
                nodes.set(key, node);
                return;
            }
            key += secondKey;
        }
    }

    public GraphNode<K, V> get(K key) {
        int firstKey = key.hashFunction(size);
        int secondKey = key.hashFunction(secondSize);
        int count = 0;
        while (true) {
            GraphNode<K, V> temp = nodes.get(firstKey);
            count++;
            if (temp != null && temp.key == key) {
                return temp;
            }
            if (count >= size) {
                // error handling required: TargetNotExists
            }
            firstKey += secondKey;
        }
    }

    private void findProperSize(int length) {
        size = length*2;
        for (int i=size; i>1; i--) {
            if (isPrime(i)) {
                secondSize = i;
                return;
            }
        }
    }

    private boolean isPrime(int n) {
        for (int i=2; i<=Math.sqrt(n); i++) {
            if (n%i==0) return false;
        }
        return true;
    }
}
