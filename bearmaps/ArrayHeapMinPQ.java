package bearmaps;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.ArrayList;

public class ArrayHeapMinPQ<T extends Comparable<T>> implements ExtrinsicMinPQ<T>  {

    private ArrayList<PriorityNode> values;
    HashMap<T, Integer> hm;

    public ArrayHeapMinPQ() {
        values = new ArrayList<>();
        hm = new HashMap<>();
    }
    /* Returns the number of items in the PQ. */
    @Override
    public int size() {
        return values.size();
    }
    private int parent(int k) {
        return (k - 1) / 2;
    }
    /* Returns true if the PQ contains the given item. */
    @Override
    public boolean contains(T item) {
        return hm.containsKey(item);
    }
    /*Adds an item of type T with the given priority.
    If the item already exists, throw an IllegalArgumentException. */
    @Override
    public void add(T item, double priority) {
        if (!hm.containsKey(item)) {
            values.add(new PriorityNode(item, priority));
            hm.put(item, values.size() - 1);
            swim(values.size() - 1);
        } else {
            throw new IllegalArgumentException("Already has item");
        }
    }
    private void swim(int k) {
        int p = parent(k);
        PriorityNode node = values.get(k);
        PriorityNode parent = values.get(p);
        if (node.getPriority() < parent.getPriority()) {
            swap(k, p);
            k = p;
            swim(k);
        }
    }

    private void swap(int n1, int n2) {
        PriorityNode temp = values.get(n1);
        values.set(n1, values.get(n2));
        values.set(n2, temp);
        hm.put(values.get(n2).item, n2);
        hm.put(values.get(n1).item, n1);
    }

    @Override
    public T getSmallest() {
        if (values == null) {
            throw new NoSuchElementException("No such item");
        }
        return values.get(0).getItem();
    }

    @Override
    public T removeSmallest() {
        if (values == null) {
            throw new NoSuchElementException("Item non-existent");
        }
        T current = getSmallest();
        swap(0, values.size() - 1);
        hm.remove(current);
        values.remove(values.size() - 1);
        sink(0);
        return current;
    }

    private void sink(int k) {
        int l = (2 * k) + 1;
        int d = l + 1;
        int min = l;
        if (l < values.size()) {
            if (d < values.size() && (values.get(d).getPriority() < values.get(l).getPriority())) {
                min++;
            }
            if (values.get(k).getPriority() > values.get(min).getPriority()) {
                swap(k, min);
                k = min;
                sink(k);
            }
        }
    }

    /* Sets the priority of the given item to the given value.
    If the item does not exist, throw a NoSuchElementException. */
    @Override
    public void changePriority(T item, double priority) {
        if (!(hm.containsKey(item))) {
            throw new NoSuchElementException("Item doesn't exist");
        } else {
            double p = values.get(indOf(item)).getPriority();
            if (p != priority) {
                values.get(indOf(item)).setPriority(priority);
                if (priority > p) {
                    sink(indOf(item));
                } else {
                    swim(indOf(item));
                }
            }
        }
    }

    private int indOf(T element) {
        return hm.get(element);
    }

    private class PriorityNode implements Comparable<PriorityNode> {
        private T item;
        private double priority;

        PriorityNode(T i, double p) {
            this.item = i;
            this.priority = p;
        }

        public T getItem() {
            return item;
        }
        @Override
        public int hashCode() {
            return item.hashCode();
        }
        public void setPriority(double priority) {
            this.priority = priority;
        }
        public double getPriority() {
            return priority;
        }
        @Override
        public int compareTo(PriorityNode other) {
            if (other == null) {
                return -1;
            }
            return Double.compare(this.getPriority(), other.getPriority());
        }

        @Override
        public boolean equals(Object o) {
            if (o == null || o.getClass() != this.getClass()) {
                return false;
            } else {
                return ((PriorityNode) o).getItem().equals(getItem());
            }
        }

    }

}
