import java.util.LinkedList;

public class AVLNode<E, I> {
    public AVLNode<E, I> left, right;
    public int height;
    public E item;
    public LinkedList<I> indexList = new LinkedList<>(); // index 를 저장하기 위한 linked list

    // 생성자들
    public AVLNode() {
        item = null;
        height = 0;
        left = right = null;
    }

    public AVLNode(E newItem, AVLNode<E, I> leftChild, AVLNode<E, I> rightChild, int h, I index) {
        item = newItem;
        height = h;
        left = leftChild;
        right = rightChild;
        indexList.add(index);
    }
}
