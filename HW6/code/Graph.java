import java.util.ArrayList;

public class Graph<K extends HashableKey, V> {
    // 역 정보를 저정할 graph
    // 각 노드를 hash table 에 저장하고, adjacency 는 각 노드에 array list 로 저장
    public static int INF = -1;
    public int size;
    private int maxSize; // hash table 의 최대 크기
    private int secondSize; // double hashing 을 위한 변수
    public ArrayList<GraphNode<K, V>> nodes;

    Graph(GraphNode<K, V>[] inputNodes) {
        findProperSize(inputNodes.length);
        size = inputNodes.length;
        for (GraphNode<K, V> node : inputNodes) {
            add(node);
        }
    }

    private void add(GraphNode<K, V> node) {
        // Graph 에 노드 추가
        int key = node.key.hashFunction(maxSize);
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
        // key 에 해당하는 노드 찾기
        int firstKey = key.hashFunction(maxSize);
        int secondKey = key.hashFunction(secondSize);
        int count = 0;
        while (true) {
            GraphNode<K, V> temp = nodes.get(firstKey);
            count++;
            if (temp != null && temp.key == key) {
                return temp;
            }
            if (count >= maxSize) {
                // error handling required: TargetNotExists
            }
            firstKey += secondKey;
        }
    }

    private void findProperSize(int length) {
        maxSize = length*2;
        for (int i = maxSize; i>1; i--) {
            if (isPrime(i)) {
                secondSize = i;
                return;
            }
        }
    }

    private static boolean isPrime(int n) {
        for (int i=2; i<=Math.sqrt(n); i++) {
            if (n%i==0) return false;
        }
        return true;
    }
}
