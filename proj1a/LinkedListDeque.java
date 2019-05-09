
public class LinkedListDeque<T> {
    /**created the double linked list */
    private class Node {
        private T item;
        private Node prev;
        private Node next;
        Node(T i, Node pre, Node nex) {
            item = i;
            prev = pre;
            next = nex;
        }
    }

    /**creating the sentinel node and initializing*/
    private Node sentinel = new Node(null, null, null);
    private int size;

    /** create the empty deque*/
    public LinkedListDeque() {
        sentinel = new Node(null, null, null);
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
        size = 0;
    }

    /**creates the deep copy of the other */
    public LinkedListDeque(LinkedListDeque other) {
        sentinel = new Node(null, null, null);
        this.sentinel.prev = this.sentinel;
        this.sentinel.next = this.sentinel;
        size = 0;
        for (int i = 0; i < other.size(); i += 1) {
            addLast((T) other.get(i));
        }
    }

    /**adds an item to the beginning of the list */
    public void addFirst(T item) {
        Node first = new Node(item, sentinel, sentinel.next);
        sentinel.next.prev = first;
        if (size == 0) {
            sentinel.next = new Node(item, sentinel, sentinel.next);
            sentinel.prev = sentinel.next;
        } else {
            sentinel.next = new Node(item, sentinel, sentinel.next);
        }
        size = size + 1;
    }

    /**adds an item to the end of the list */
    public void addLast(T item) {
        Node last = new Node(item, sentinel.prev, sentinel);
        sentinel.prev.next = last;
        if (size == 0) {
            sentinel.prev = last;
            sentinel.next = sentinel.prev;
        } else {
            sentinel.prev = last;
        }
        size = size + 1;
    }

    /**checks to see if the list is empty */
    public boolean isEmpty() {
        if (size == 0) {
            return true;
        }
        return false;
    }

    /**displays the items in the deque separated by a space*/
    public void printDeque() {
        Node nod = sentinel.next;
        while (nod != sentinel) {
            System.out.print(nod.item + " ");
            nod = nod.next;
        }
    }

    /**removes the firt item after the sentinel node */
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }
        T first = sentinel.next.item;
        sentinel.next = sentinel.next.next;
        size = size - 1;
        return first;
    }

    /**removes the last item after the sentinel node */
    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        T last = sentinel.prev.item;
        sentinel.prev = sentinel.prev.prev;
        size -= 1;
        return last;
    }

    /**gets an item at a specified index */
    public T get(int index) {
        if (index > size - 1) {
            return null;
        }
        int i = 0;
        Node pre = sentinel.next;
        while (i < index) {
            pre = pre.next;
            i++;
        }
        return pre.item;
    }

    /**helper method for get method using recursion*/
    private T getHelper(Node link, int index) {
        if (index == 0) {
            return link.item;
        } else if (index >= size) {
            return null;
        } else {
            return getHelper(link.next, index - 1);
        }
    }

    /**get method but uses recursion instead of iteration*/
    public T getRecursive(int index) {
        return getHelper(sentinel.next, index);
    }

    /**returns the size of the list*/
    public int size() {
        return size;
    }
}
