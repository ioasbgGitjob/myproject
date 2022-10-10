package 链表反转;

/**
 * @author szy
 * @version 1.0
 * @description 使用递归实现链表反转
 * @date 2021-11-22 18:15:29
 */

public class ReverseNode {

    public static void main(String[] args) {
        Node next4 = new Node(4, null);
        Node next3 = new Node(3, next4);
        Node next2 = new Node(2, next3);
        Node next1 = new Node(1, next2);

        //打印出来单向链表数据 1，2，3，4
        Node next = next1;
        while (next != null) {
            System.out.println(next.index);
            next = next.next;
        }

        //反转单向链表
        Node r = reverse(next1);

        //打印出来单向链表数据 4321
        Node nextf = r;
        while (nextf != null) {
            System.out.println(nextf.index);
            nextf = nextf.next;
        }
    }

    public static Node reverse(Node head) {
        if (head == null || head.getNext() == null) {
            return head;
        }
        Node reHead = reverse(head.getNext());
        head.getNext().setNext(head);
        head.setNext(null);
        return reHead;
    }

    static class Node {
        int index;

        Node next;

        public Node(int index, Node next) {
            this.index = index;
            this.next = next;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public Node getNext() {
            return next;
        }

        public void setNext(Node next) {
            this.next = next;
        }
    }
}
