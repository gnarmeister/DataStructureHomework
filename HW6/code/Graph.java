public class Graph<K extends HashableKey, V> {
    public static int INF = -1;
    public int size;
    public GraphNode<K, V>[] nodes;

    Graph(GraphNode<K, V>[] inputNodes) {
        size = findProperSize(inputNodes.length);
        for (GraphNode<K, V> node : inputNodes) {
            add(node);
        }
    }

    public void add(GraphNode<K, V> node) {

    }

    public GraphNode<K, V> get(K key) {
        return nodes[0];
    }

    private int findProperSize(int length) {
//        int result = length * 2;
//        while (true) {
//            for (int i=2; i<=Math.sqrt(result); i++) {
//                if (result % i == 0) {
//                    break;
//                }
//            }
//            result += 1;
//        }
        return 1;
    }
}
