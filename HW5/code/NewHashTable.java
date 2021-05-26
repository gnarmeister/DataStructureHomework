import java.util.*;

public class NewHashTable<K, V extends Comparable<V> & HashValue<K>> extends Hashtable<K, AVLTree<V>> {
    // 각 슬롯이 AVLTree 이고 V 는 comparable 하고 hashFunction 메소드를 가져야 한다
    public void add(V item, String index) {
        // 새로운 아이템을 추가
        K key = item.hashFunction();
        AVLTree<V> tree = super.get(key);

        if (tree == null) {
            super.put(key, new AVLTree<>(item, index));
        } else {
            tree.insert(item, index);
        }
    }

    public LinkedList<V> getItems(K key) {
        // key 에 해당하는 트리의 모든 노드 가져오기
        AVLTree<V> tree = super.get(key);

        if (tree == null) return null;
        else {
            return tree.getAllNode();
        }
    }

    public AVLNode<V> search(V target) {
        // 해당 target 을 가지는 AVLNode 검색
        AVLTree<V> tree = super.get(target.hashFunction());
        if (tree==null) return null;
        return tree.search(target);
    }
}
