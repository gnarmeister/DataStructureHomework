import java.util.*;

public class NewHashTable<K, V extends Comparable<V> & HashValue<K>> extends Hashtable<K, AVLTree<V>> {
    public void add(V item, String index) {
        K key = item.hashFunction();
        AVLTree<V> tree = super.get(key);

        if (tree == null) {
            super.put(key, new AVLTree<>(item, index));
        } else {
            tree.insert(item, index);
        }
    }

    public Queue<V> getItems(K key) {
        AVLTree<V> tree = super.get(key);

        if (tree == null) return null;
        else {
            return tree.getAllNode();
        }
    }
}
