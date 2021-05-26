import java.util.LinkedList;
import java.util.Queue;

public class AVLTree<E extends Comparable<E>> {
    // 강의자료의 AVLTree 자바 구현을 참고하여 작성하였습니다.
    private AVLNode<E> root;
    private final AVLNode<E> NIL = new AVLNode<>();

    public AVLTree(E item, String index) {
        root = new AVLNode<>(item, NIL, NIL, 1, index);
    }

    private AVLNode<E> searchItem(AVLNode<E> node, E target) {
        // 검색을 재귀적으로 구현하기 위한 private method
        if (node == NIL) return null;
        if (node.item.compareTo(target) == 0) {
            return node;
        }
        if (node.item.compareTo(target) < 0) {
            return searchItem(node.right, target);
        } else {
            return searchItem(node.left, target);
        }
    }

    public AVLNode<E> search(E target) {
        // 검색
        return searchItem(root, target);
    }

    private final int LL=1, LR=2, RR=3, RL=4, NO_NEED=0, ILLEGAL=-1;
    private int needBalance(AVLNode<E> node) {
        // 해당 노드를 기준으로 어떤 balancing 이 필요한지 판단
        int type = ILLEGAL;
        if (node.left.height+2 <= node.right.height) {
            if (node.right.left.height <= node.right.right.height) {
                type = RR;
            } else {
                type = RL;
            }
        } else if (node.left.height >= node.right.height+2) {
            if (node.left.left.height >= node.left.right.height) {
                type = LL;
            } else {
                type = LR;
            }
        } else {
            type = NO_NEED;
        }
        return type;
    }

    private AVLNode<E> leftRotate(AVLNode<E> node) {
        AVLNode<E> right = node.right;
        AVLNode<E> rightLeft = right.left;
        right.left = node;
        node.right = rightLeft;
        node.height = 1 + Math.max(node.left.height, node.right.height);
        right.height = 1 + Math.max(right.left.height, right.right.height);
        return right;
    }

    private AVLNode<E> rightRotate(AVLNode<E> node) {
        AVLNode<E> left = node.left;
        AVLNode<E> leftRight = left.right;
        left.right = node;
        node.left = leftRight;
        node.height = 1 + Math.max(node.left.height, node.right.height);
        left.height = 1 + Math.max(left.left.height, left.right.height);
        return left;
    }

    private AVLNode<E> balance(AVLNode<E> node, int type) {
        AVLNode<E> returnNode = node;
        switch (type) {
            case LL:
                returnNode = rightRotate(node);
                break;
            case LR:
                node.left = leftRotate(node.left);
                returnNode = rightRotate(node);
                break;
            case RR:
                returnNode = leftRotate(node);
                break;
            case RL:
                node.right = rightRotate(node.right);
                returnNode = leftRotate(node);
                break;
            default:
                break;
        }
        return returnNode;
    }

    private AVLNode<E> insertItem(AVLNode<E> node, E newItem, String index) {
        // item 삽입을 재귀적으로 수행하기 위한 private method
        int type;
        if (node == NIL) {
            node = new AVLNode<>(newItem, NIL, NIL, 1, index);
        } else if (node.item.compareTo(newItem) < 0) {
            node.right = insertItem(node.right, newItem, index);
            node.height = 1 + Math.max(node.left.height, node.right.height);
            type = needBalance(node);
            node = balance(node, type);
        } else if (node.item.compareTo(newItem) > 0) {
            node.left = insertItem(node.left, newItem, index);
            node.height = 1 + Math.max(node.left.height, node.right.height);
            type = needBalance(node);
            node = balance(node, type);
        } else {
            // 이미 존재하는 경우
            node.indexList.add(index);
        }
        return node;
    }

    public void insert(E newItem, String index) {
        // item 삽입
        root = insertItem(root, newItem, index);
    }

    private Queue<E> getAllNodeRecursive(AVLNode<E> node, Queue<E> queue) {
        if (node == NIL) {
            return queue;
        }
        queue.offer(node.item);
        getAllNodeRecursive(node.left, queue);
        getAllNodeRecursive(node.right, queue);
        return queue;
    }

    public Queue<E> getAllNode() {
        Queue<E> queue = new LinkedList<>();
        return getAllNodeRecursive(root, queue);
    }
}
