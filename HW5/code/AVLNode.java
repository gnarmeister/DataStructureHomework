import java.util.LinkedList;

public class AVLNode<E> {
    public AVLNode<E> left, right;
    public int height;
    public E item;
    public LinkedList<String> indexList = new LinkedList<>(); // index 를 저장하기 위한 linked list

    // 생성자들
    public AVLNode() {
        item = null;
        height = 0;
        left = right = null;
    }

    public AVLNode(E newItem, AVLNode<E> leftChild, AVLNode<E> rightChild, int h, String index) {
        item = newItem;
        height = h;
        left = leftChild;
        right = rightChild;
        indexList.add(index);
    }
}
