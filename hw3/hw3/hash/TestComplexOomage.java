package hw3.hash;

import java.util.HashSet;
import java.util.Set;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;
import edu.princeton.cs.algs4.StdRandom;


public class TestComplexOomage {

    public boolean haveNiceHashCodeSpread(Set<ComplexOomage> oomages) {
        int M = 10;
        int[] bucketSize = new int[M];
        boolean status = true;

        for (Oomage omage: oomages) {
            int buckNo = (omage.hashCode() & 0x7FFFFFFF) % M;
            bucketSize[buckNo] += 1;
        }
        for (int i = 0; i < M; i += 1) {
            if (bucketSize[i] < oomages.size() / 50.0) {
                status = false;
                break;
            }
        }
        return status;
    }

    @Test
    public void testHashCodeDeterministic() {
        ComplexOomage so = ComplexOomage.randomComplexOomage();
        int hashCode = so.hashCode();
        for (int i = 0; i < 100; i += 1) {
            assertEquals(hashCode, so.hashCode());
        }
    }

    /* This should pass if your OomageTestUtility.haveNiceHashCodeSpread
       is correct. This is true even though our given ComplexOomage class
       has a flawed hashCode. */
    @Test
    public void testRandomOomagesHashCodeSpread() {
        List<Oomage> oomages = new ArrayList<>();
        int N = 10000;

        for (int i = 0; i < N; i += 1) {
            oomages.add(ComplexOomage.randomComplexOomage());
        }

        assertTrue(OomageTestUtility.haveNiceHashCodeSpread(oomages, 10));
    }

    /* Create a list of Complex Oomages called deadlyList
     * that shows the flaw in the hashCode function.
     */

    @Test
    public void testWithDeadlyParams() {
        List<Oomage> deadlyList = new ArrayList<>();

        // Your code here.
        HashSet<ComplexOomage> oomages = new HashSet<ComplexOomage>();
        int N = 100;
        for (int i = 0; i < N; i += 1) {
            int number = StdRandom.uniform(100, 200);
            ArrayList<Integer> params = new ArrayList<Integer>(number);
            for (int j = 0; j < number; j += 1) {
                int powers = StdRandom.uniform(2, 8);
                params.add((int) Math.pow(2, powers));
            }
            oomages.add(new ComplexOomage(params));
        }
        assertTrue(haveNiceHashCodeSpread(oomages));
    }

    /** Calls tests for SimpleOomage. */
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestComplexOomage.class);
    }
}
