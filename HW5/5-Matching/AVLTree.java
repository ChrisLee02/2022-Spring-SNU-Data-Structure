import java.util.LinkedList;
import java.util.List;

public class AVLTree<T extends Comparable<T>> implements IndexInterface<AVLNode<T>, T>{
    public AVLNode<T> root;
    static final AVLNode<?> NIL = new AVLNode<>(null, null, null, 0);
    private final int LL = 1, LR = 2, RR = 3, RL = 4, NO_NEED = 0, ILLEGAL = -1;

    public AVLTree() {
        root = (AVLNode<T>) NIL;
    }
    // 검색
    @Override
    public AVLNode<T> search(T x) {
        return searchItem(root, x);
    }
    private AVLNode<T> searchItem(AVLNode<T> t, T x) {
        if(t==NIL) return (AVLNode<T>) NIL;
        else if(x.compareTo(t.item) == 0) return t;
        else if(x.compareTo(t.item) < 0) return searchItem(t.left, x);
        else return searchItem(t.right, x);
    }

    private AVLNode<T> afterCare(AVLNode<T> t){
        t.height = 1 + Math.max(t.right.height, t.left.height);
        int type = needBalance(t);
        if(type!=NO_NEED) {
            t = balanceAVL(t, type);
        }
        return t;
    }

    //삽입
    @Override
    public void insert(T x) {
        root = insertItem(root, x);
    }

    private AVLNode<T> insertItem(AVLNode<T> t, T x) {
        if(t == NIL) {
            t = new AVLNode<>(x);
        }
        else if(x.compareTo(t.item) == 0) {
            AVLNode<T> tmpNode = t;
            while(tmpNode.linkedNode != null ) {
                tmpNode = tmpNode.linkedNode;
            }
            tmpNode.linkedNode = new AVLNode<>(x); // 과제 5를 위한 수정
        }
        else if(x.compareTo(t.item)<0) {
            t.left = insertItem(t.left, x);
            t = afterCare(t);
        }
        else {
            t.right = insertItem(t.right, x);
            t = afterCare(t);
        }

        return t;

        //tree 를 재귀호출을 타고 내려간다. 끝자락에 다다르면 새 노드를 만들어 연결해준다.
        //끝자락에 다다르는 경우를 제외하고는, t.left  t.right 를 수정하는 로직은 큰 의미가 없음.
    }

    //삭제
    @Override
    public void delete(T x) {
        root = findAndDelete(root, x);
    }

    private AVLNode<T> findAndDelete(AVLNode<T> t, T x) {
        if(t == NIL) return (AVLNode<T>) NIL;
        else {
            if(x.compareTo(t.item) == 0) {
                t = deleteNode(t);
            }
            else if(x.compareTo(t.item) < 0) {
                t.left = findAndDelete(t.left, x);
                afterCare(t);
            }
            else {
                t.right = findAndDelete(t.right, x);
                afterCare(t);
            }
            return t;
        }
    }

    private AVLNode<T> deleteNode(AVLNode<T> t) {
        if(t.left == NIL && t.right == NIL) {
            return (AVLNode<T>) NIL;
        }
        else if(t.left == NIL) {
            return t.right;
        }
        else if(t.right == NIL) {
            return t.left;
        }
        else {// t를 실제로 지우는 것이 아니라, t의 값을 기존 t보다 더 큰쪽에서의 최솟값 노드로 바꾸고, 해당 최솟값 노드를 삭제함.
            returnPair rPair = deleteMinItem(t.right); //기존 t보다 더 큰쪽에서 최솟값을 찾는다. 걔가 새로운 t가 될거임.
            t.item = rPair.minItem;
            t.right = rPair.node;
            // 위의 두 경우는 t.right, t.left의 높이를 그대로 들고가서 조정 필요 없음
            // 이번 경우는 최솟값 노드를 최상단으로 가져왔기 때문에, 높이 조정을 체크해야됨.
            afterCare(t);
            return t;
        }
    }

    private returnPair deleteMinItem(AVLNode<T> t) {
        if(t.left == NIL) { //최솟값에 도달하면 값을 여기서 땡겨옴
            return new returnPair(t.item, t.right);
        }
        else {
            returnPair rPair = deleteMinItem(t.left); //여기선 재귀호출로 최솟값까지 도달하는 파트임.
            //즉, deleteNode에서 right는 변하지 않는다.
            t.left = rPair.node;//화살표 재정리하는 부분임. 여기서 최솟값 노드의 right가 어떻게 생겼냐에 따라 균형 여부가 갈리기 때문에 체크해야됨.
            afterCare(t);
            rPair.node = t; // 백트래킹하는 부분임. 거쳐온 노드들을 다시 rPair.node에 담음. 삭제할 노드의 오른쪽 노드부터 그 윗노드들을 쭉 거침
            //마지막에는 최초로 호출된 t의 right노드가 node변수에 들어간다.
            return rPair;
        }
    }

    private AVLNode<T> balanceAVL(AVLNode<T> t, int type) {
        AVLNode<T> returnNode = (AVLNode<T>) NIL;
        switch (type) {
            case LL:
                returnNode = rightRotate(t);
                break;
            case LR:
                t.left = leftRotate(t.left);
                returnNode = rightRotate(t);
                break;
            case RR:
                returnNode = leftRotate(t);
                break;
            case RL:
                t.right = rightRotate(t.right);
                returnNode = leftRotate(t);
                break;
            default:
                System.out.println("Impossible case");
                break;
        }
        return returnNode;
    }

    private AVLNode<T> leftRotate(AVLNode<T> t) {
        AVLNode<T> rightNode = t.right;
        if(rightNode == NIL) {
            System.out.println("Invalid Right rotate");
            return (AVLNode<T>) NIL;
        }
        t.right = rightNode.left;
        rightNode.left = t;
        t.height = 1 + Math.max(t.right.height, t.left.height);
        rightNode.height = 1 + Math.max(rightNode.right.height, rightNode.left.height);
        return rightNode;
    }

    private AVLNode<T> rightRotate(AVLNode<T> t) {
        AVLNode<T> leftNode = t.left;
        if(leftNode == NIL) {
            System.out.println("Invalid Right rotate");
            return (AVLNode<T>) NIL;
        }
        t.left = leftNode.right;
        leftNode.right = t;
        t.height = 1 + Math.max(t.right.height, t.left.height);
        leftNode.height = 1 + Math.max(leftNode.right.height, leftNode.left.height);
        return leftNode;
    }

    private int needBalance(AVLNode<T> t) {
        int type = ILLEGAL;
        if(t.left.height + 2 <= t.right.height) {
            if(t.right.left.height <= t.right.right.height) {
                type=RR;
            }
            else {
                type=RL;
            }
        } else if(t.right.height + 2 <= t.left.height) {
            if(t.left.right.height <= t.left.left.height) {
                type=LL;
            }
            else {
                type=LR;
            }
        } else {
            type = NO_NEED;
        }
        return type;
    }

    @Override
    public boolean isEmpty() {
        return root == NIL;
    }

    @Override
    public void clear() {
        root = (AVLNode<T>) NIL;
    }

    public List<T> getPreOrderNode() {
        List<T> result = new LinkedList<>();
        preOrderNode(root, result);
        return result;
    }

    private void preOrderNode(AVLNode<T> t, List<T> result) {
        if(t==NIL) return;
        else {
            result.add(t.item);
            preOrderNode(t.left, result);
            preOrderNode(t.right ,result);
        }
    }

    public void preOrderTraverse() {
        preOrder(root, 0);
    }

    private void preOrder(AVLNode<T> t, int i) {
        if(t==NIL) return;
        else {
            AVLNode<T> tmpNode = t;
            while(tmpNode!=null) {
                System.out.println(tmpNode + " " + i);
                tmpNode = tmpNode.linkedNode;
            }
            preOrder(t.left,i+1);
            preOrder(t.right ,i +1);
        }
    }

    private class returnPair {
        T minItem;
        AVLNode<T> node;
        private returnPair(T x, AVLNode<T> t) {
            minItem = x;
            node = t;
        }
    }
}
