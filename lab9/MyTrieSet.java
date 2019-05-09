import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;


public class MyTrieSet implements TrieSet61B {
    private Node root;

    private static class Node {
        private boolean isKey;
        private Map<Character, Node> map;

        private Node() {
            isKey = false;
            map = new HashMap<>();
        }
    }

    @Override
    public String longestPrefixOf(String key){
        throw new UnsupportedOperationException();
    }



    @Override
    public List<String> keysWithPrefix(String prefix) {
        if (prefix == null) {
            prefix = "";
        }
        Node curr = root;
        for (int x = 0, n = prefix.length();
             x < n; x++){
            char z = prefix.charAt(x);

            if (!curr.map.containsKey(z)) {

                return new ArrayList<>();
            }
            curr = curr.map.get(z);
        }
        return collect(prefix, new ArrayList<>(), curr);
    }

    @Override
    public void add(String key) {
        if (key == null || key.length() < 1) {
            return;
        }
        Node curr = root;
        for (int value = 0, n = key.length(); value < n; value++) {
            char z = key.charAt(value);

            if (!curr.map.containsKey(z)) {

                curr.map.put(z, new Node());
            }
            curr = curr.map.get(z);
        }
        curr.isKey = true;
    }

    @Override
    public boolean contains(String key) {
        if (key == null) {
            return false; }
        Node curr = root;
        for (int value = 0, n = key.length(); value < n; value++){


            char z = key.charAt(value);

            if (!curr.map.containsKey(z)) {
                return false;
            }
            curr = curr.map.get(z);
        }
        return curr.isKey;
    }
    public MyTrieSet() {
        clear();
    }

    @Override
    public void clear() {
        root = new Node();
    }


    private List<String> collect(String s, List<String> x, Node n) {
        if (n.isKey) {
            x.add(s);
        }
        for (Character z : n.map.keySet()) {
            collect(s + z, x, n.map.get(z));
        }
        return x;
    }
}
