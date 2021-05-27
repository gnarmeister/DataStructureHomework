import java.util.LinkedList;

public class AVLTree<E extends Comparable<E>, I> {
    // 강의자료의 AVLTree 자바 구현을 참고하여 작성하였습니다.
    private AVLNode<E, I> root;
    private final AVLNode<E, I> NIL = new AVLNode<>();

    public AVLTree(E item, I index) {
        root = new AVLNode<>(item, NIL, NIL, 1, index);
    }

    private AVLNode<E, I> searchItem(AVLNode<E, I> node, E target) {
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

    public AVLNode<E, I> search(E target) {
        // 검색
        return searchItem(root, target);
    }

    private final int LL=1, LR=2, RR=3, RL=4, NO_NEED=0, ILLEGAL=-1;
    private int needBalance(AVLNode<E, I> node) {
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

    private AVLNode<E, I> leftRotate(AVLNode<E, I> node) {
        AVLNode<E, I> right = node.right;
        AVLNode<E, I> rightLeft = right.left;
        right.left = node;
        node.right = rightLeft;
        node.height = 1 + Math.max(node.left.height, node.right.height);
        right.height = 1 + Math.max(right.left.height, right.right.height);
        return right;
    }

    private AVLNode<E, I> rightRotate(AVLNode<E, I> node) {
        AVLNode<E, I> left = node.left;
        AVLNode<E, I> leftRight = left.right;
        left.right = node;
        node.left = leftRight;
        node.height = 1 + Math.max(node.left.height, node.right.height);
        left.height = 1 + Math.max(left.left.height, left.right.height);
        return left;
    }

    private AVLNode<E, I> balance(AVLNode<E, I> node, int type) {
        // 균형 조정
        AVLNode<E, I> returnNode = node;
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

    private AVLNode<E, I> insertItem(AVLNode<E, I> node, E newItem, I index) {
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

    public void insert(E newItem, I index) {
        // item 삽입
        root = insertItem(root, newItem, index);
    }

    private void getAllNodeRecursive(AVLNode<E, I> node, LinkedList<E> list) {
        if (node == NIL) {
            return;
        }
        list.add(node.item);
        getAllNodeRecursive(node.left, list);
        getAllNodeRecursive(node.right, list);
    }

    public LinkedList<E> getAllNode() {
        // 모든 노드를 preorder traversal 하게 list 에 넣음
        LinkedList<E> list = new LinkedList<>();
        getAllNodeRecursive(root, list);
        return list;
    }
}
