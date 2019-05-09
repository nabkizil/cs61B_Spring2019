
import java.util.Iterator;
import java.util.Set;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {

    private class Node {
        private K key;
        private V value;

        /* Children of this Node. */
        private Node left;
        private Node right;

        private Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    @Override
    public boolean containsKey(K key){
        return get(key)!=null;
    }

    private Node root;  /* Root node of the tree. */
    private int size;   /* The number of key-value pairs in the tree */

    public BSTMap() {
        this.clear();
    }

    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    private V getHelper(K key, Node p) {

        if (key == null) {
            throw new IllegalArgumentException("null key...");
        }
        if (p == null) {
            return null;
        } else {

            int cmp = key.compareTo(p.key);
            if (cmp < 0) {
                return getHelper(key, p.left);
            }
            else if (cmp > 0) {
                return getHelper(key, p.right);
            }
            else {
                return p.value;
            }


        }

    }

    @Override
    public V get(K key) {

        return getHelper(key, root);
        // throw new UnsupportedOperationException();
    }


    private Node putHelper(K key, V value, Node p) {

        if (p == null) {
            size += 1;
            p = new Node(key, value);
            return p;
        } else {

            int cmp = key.compareTo(p.key);
            if (cmp < 0) {
                p.left  = putHelper(key, value, p.left);
            }
            else if (cmp > 0) {
                p.right = putHelper(key, value, p.right);
            }
            else {
                p.value = value;
            }
            return p;

        }


    }


    @Override
    public void put(K key, V value) {

        root = putHelper(key, value, root);

    }

    /* Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
        return size;
    }
    @Override
    public Set<K> keySet() {
        throw new UnsupportedOperationException();
    }


    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException();
    }


    @Override
    public V remove(K key, V value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<K> iterator() {
        throw new UnsupportedOperationException();
    }
}