import java.util.HashSet;
import java.util.Set;
import java.util.Iterator;


public class MyHashMap<K, V> implements Map61B<K, V> {
    private double loadFactor = 0.75;
    private int size = 0;
    private int capacity = 16;
    private HashSet<K> keySet = new HashSet<K>();
    private Entry<K, V>[] table;
    private int threshhold;

    public MyHashMap() {
        threshhold = (int) (loadFactor * capacity);
        table = new Entry[capacity]; // Entry<K,V> Wrong???
    }
    public MyHashMap(int initialSize) {
        capacity = initialSize;
        threshhold = (int) (loadFactor * capacity);
        table = new Entry[capacity];
    }
    public MyHashMap(int initialSize, double loadFactor) {
        capacity = initialSize;
        this.loadFactor =  loadFactor;
        threshhold = (int) (loadFactor * capacity);
        table = (Entry<K, V>[]) new Object[capacity];
    }


    private static class Entry<K, V> {
        private K key;
        private V val;
        private Entry<K, V> next;
        private boolean deleted;

        Entry(K k, V v, Entry<K, V> n) {
            key = k;
            val = v;
            next = n;
            deleted = false;
        }

        Entry(K k, V v, Entry<K, V> n, boolean deleted) {
            key = k;
            val = v;
            next = n;
            this.deleted = deleted;
        }
    }

    @Override
    public void clear() {
        size = 0;
        threshhold = (int) (capacity * loadFactor);
        table = new Entry[capacity];
    }

    @Override
    public boolean containsKey(K key) {
        return get(key) != null;
    }

    @Override
    public V get(K key) {
        int hashcode = hash(key.hashCode());
        int index = hashcode % capacity;
        for (Entry<K, V> e = table[index]; e != null; e = e.next) {
            if (e.key.equals(key) && !e.deleted) {
                return e.val;
            }
        }
        return null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void put(K key, V value) {
        int hashcode = hash(key.hashCode());
        int index = hashcode % capacity;
        for (Entry<K, V> e = table[index]; e != null; e = e.next) {
            if (e.key.equals(key) && !e.deleted) {
                e.val = value;
                return;
            }
        }

        table[index] = new Entry<>(key, value, table[index]);

        size++;
        if (size > threshhold) {
            rehash();
        }
    }

    private void rehash() {
        int oldCapacity = capacity;
        capacity = capacity * 2 + 1;
        Entry[] oldTable = table;
        clear();
        for (int i = 0; i < oldCapacity; ++i) {
            for (Entry<K, V> e = oldTable[i]; e != null; e = e.next) {
                if (e.deleted) {
                    continue;
                }
                int hashcode = hash(e.key.hashCode());
                int index = hashcode % capacity;
                table[index] = new Entry<>(e.key, e.val, table[index]);
                size++;
            }
        }

    }

    @Override
    public V remove(K key) {
        int hashcode = hash(key.hashCode());
        int index = hashcode % capacity;
        Entry<K, V> f = table[index];
        if (f == null) {
            return null;
        }
        for (Entry<K, V> e = table[index]; e != null; e = e.next) {
            if (e.key.equals(key) && !e.deleted) {
                e.deleted = true;
                size--;
                return e.val;
            }
        }
        return null;
    }
    @Override
    public V remove(K key, V value) {
        int hashcode = hash(key.hashCode());
        int index = hashcode % capacity;
        Entry<K, V> f = table[index];
        if (f == null) {
            return null;
        }
        for (Entry<K, V> e = table[index]; e != null; e = e.next) {
            if (e.key.equals(key) && e.val == value && !e.deleted) {
                e.deleted = true;
                size--;
                return e.val;
            }
        }
        return null;
    }
    @Override
    public Set<K> keySet() {
        return keySet;
    }

    @Override
    public Iterator<K> iterator() {
        throw new UnsupportedOperationException();
    }


    private int hash(int h) {
        h ^= (h >>> 20) ^ (h >>> 12);
        return h ^ (h >>> 7) ^ (h >>> 4);
    }
}
