import java.util.LinkedList;

public class AVLNode<E> {
    public AVLNode<E> left, right;
    public int height;
    public E item;
    public LinkedList<String> indexList = new LinkedList<>();

    public AVLNode(E newItem, String index) {
        item = newItem;
        height = 1;
        left = right = null;
        indexList.add(index);
    }

    public AVLNode(E newItem, AVLNode<E> leftChild, AVLNode<E> rightChild, int h, String index) {
        item = newItem;
        height = h;
        left = leftChild;
        right = rightChild;
        indexList.add(index);
    }
}
