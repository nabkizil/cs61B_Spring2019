public class ArrayDeque<T> {

    /**initializing/declaring and instantiating variables*/
    private int size = 0;
    private int nextFirst;
    private int nextLast;
    private T[] items;

    /** creates the deque*/
    public ArrayDeque() {
        nextFirst = 0;
        nextLast = 0;
        items = (T[]) new Object[8];
    }

    /**constructor for the deque
    public ArrayDeque(T d) {
        items = (T[]) new Object[8];
        items[0] = d;
        size = 8;
        nextFirst = 7;
        nextLast = 1;
    }
    */

    /**creates the deep copy of the list*/
    public ArrayDeque(ArrayDeque other) { //Creates a deep copy of other
        T[] copy = (T[]) new Object[other.items.length];
        System.arraycopy(other.items, nextFirst, copy, 0, other.items.length);
        this.nextFirst = other.nextFirst;
        this.size = other.size;
        this.nextLast = other.nextLast;
        this.items = copy;
    }


    /**adds an object to the beginning of the list*/
    public void addFirst(T i) {
        if (size + 1 > items.length) {
            resize(size * 2);
        }
        if (size + 1 == 1) {
            nextFirst = nextFirst;
        } else if (nextFirst == 0) {
            nextFirst = items.length - 1;
        } else {
            nextFirst = nextFirst - 1;
        }
        size += 1;
        items[nextFirst] = i;
    }

    /**augments the size of the list */
    private void resize(int capacity) {
        T[] newItems = (T[]) new Object[capacity];
        if (nextFirst < nextLast) {
            System.arraycopy(items, nextFirst, newItems, 0, size);
        } else {
            System.arraycopy(items, nextFirst, newItems, 0, items.length - nextFirst);
            System.arraycopy(items, 0, newItems, items.length - nextFirst, nextLast + 1);
        }
        items = newItems;
        nextFirst = 0;
        nextLast = size - 1;
    }

    /**adds an item to the end of the list*/
    public void addLast(T i) {
        if (size + 1 > items.length) {
            resize(size * 2);
        }
        if (size + 1 == 1) {
            nextLast = nextLast;
        } else if (nextLast == items.length - 1) {
            nextLast = 0;
        } else {
            nextLast += 1;
        }
        size += 1;
        items[nextLast] = i;
    }

    /**checks to see if the size of the list is 0 */
    public boolean isEmpty() {
        if (size == 0) {
            return true;
        }
        return false;
    }

    /**checks the size of the list */
    public int size() {
        if (size <= -1) {
            size = 0;
        }
        return size;
    }

    /**iterates through the list and prints out the values */
    public void printDeque() {
        for (int i = 0; i < size - 1; i++) {
            System.out.print(items[(nextFirst + i) % items.length]);
            System.out.print(" ");
        }
    }

    /**removes first item in the list*/
    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        T item = items[nextFirst];
        items[nextFirst] = null;
        if (size == 1) {
            nextFirst = nextFirst;
        } else if (nextFirst == items.length - 1) {
            nextFirst = 0;
        } else {
            nextFirst += 1;
        }
        size = size - 1;
        if ((size <= items.length / 3) && (size > 3)) {
            resize(items.length / 3);
        }
        return item;
    }

    /**removes the last item in the list */
    public T removeLast() {
        if (size == 0) {
            return null;
        }
        T item = items[nextLast];
        items[nextLast] = null;
        if (size == 1) {
            nextLast = nextLast;
        } else if (nextLast == 0) {
            nextLast = items.length - 1;
        } else {
            nextLast -= 1;
        }
        size -= 1;
        if ((size <= items.length / 3) && (size > 3)) {
            resize(items.length / 3);
        }
        return item;
    }

    /**checks to see if an item exists at a specified index*/
    private boolean isExist(int index) {
        if (index > nextFirst || index < nextLast) {
            return true;
        }
        return false;
    }

    /**gets an item at a specified index using iteration */
    public T get(int index) {
        if (isExist(index)) {
            return items[index];
        }
        return null;
    }
}

