import java.util.Hashtable;

public class NewHashTable<K, V extends Comparable<V> & HashValue<K>> extends Hashtable<K, AVLTree<V>> {

}

interface HashValue<K> {
    public K hashFunction();
}