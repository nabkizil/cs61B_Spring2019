public class UnionFind {

    private int[] id;
    private int[] size;
    private int counter;

    /* Creates a UnionFind data structure holding n vertices. Initially, all
       vertices are in disjoint sets. */
    public UnionFind(int n) {
        // TODO
        id = new int[n];
        size = new int[n];
        for(int i = 0; i < n; i++){
            id[i] = i;
            size[i] = 1;
        }
    }

    /* Throws an exception if v1 is not a valid index. */
    private void validate(int vertex) {
        int n = id.length;
        if (vertex < 0 || vertex >= n) {
            throw new IndexOutOfBoundsException("index " + vertex + " is not between 0 and " + (n-1));
        }
    }

    /* Returns the size of the set v1 belongs to. */
    public int sizeOf(int v1) {
        return counter;
    }

    /* Returns the parent of v1. If v1 is the root of a tree, returns the
       negative size of the tree for which v1 is the root. */
    public int parent(int v1) {
        // TODO
        return -1;
    }

    /* Returns true if nodes v1 and v2 are connected. */
    public boolean connected(int v1, int v2) {
        return find(v1) == find(v2);
    }

    /* Connects two elements v1 and v2 together. v1 and v2 can be any valid 
       elements, and a union-by-size heuristic is used. If the sizes of the sets
       are equal, tie break by connecting v1's root to v2's root. Unioning a 
       vertex with itself or vertices that are already connected should not 
       change the sets but may alter the internal structure of the data. */
    public void union(int v1, int v2) {
        int x = find(v1);
        int y = find(v2);
        if(x == y){
            return;
        }
        if(size[x] < size[y]){
            id[x] = y;
            size[y] += size[x];
        }
        else{
            id[y] = x;
            size[x] += size[y];
        }
        counter--;
    }

    /* Returns the root of the set V belongs to. Path-compression is employed
       allowing for fast search-time. */
    public int find(int vertex) {
        validate(vertex);
        if(id[vertex] < 0){
            return vertex;
        } else{
            id[vertex] = find(id[vertex]);
        }
        return id[vertex];
    }

}
