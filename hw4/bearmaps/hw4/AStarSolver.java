package bearmaps.hw4;

import bearmaps.proj2ab.DoubleMapPQ;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import edu.princeton.cs.algs4.Stopwatch;

public class AStarSolver<Vertex> implements ShortestPathsSolver<Vertex> {

    HashMap<Vertex, Double> distTo = new HashMap<>();
    HashMap<Vertex, Vertex> edgeTo = new HashMap<>();
    int numst = 0;
    private DoubleMapPQ<Vertex> fringe = new DoubleMapPQ<>();
    private Vertex init;
    private Vertex finish;

    double time;
    private AStarGraph<Vertex> input;
    private List<Vertex> solution;
    double ttt;


    public AStarSolver(AStarGraph<Vertex> input, Vertex start, Vertex end, double timeout) {
        this.input = input;
        this.init = start;
        this.finish = end;
        this.time = timeout;
        Stopwatch tt = new Stopwatch();
        aStar();
        ttt = tt.elapsedTime();

    }

    private void aStar() {
        fringe.add(init, 0);
        distTo.put(init, 0.0);
        edgeTo.put(init, null);
        Stopwatch sw = new Stopwatch();
        while (fringe.size() != 0 && sw.elapsedTime() < time) {
            if (!fringe.getSmallest().equals(finish)) {
                Vertex p = fringe.removeSmallest();
                numst += 1;
                for (WeightedEdge<Vertex> e : input.neighbors(p)) {
                    relax(e);
                }
            } else {
                break;
            }
        }
    }

    private double h(Vertex v, Vertex goal) {
        return this.input.estimatedDistanceToGoal(v, goal);
    }

    private void relax(WeightedEdge<Vertex> e) {
        Vertex P = e.from();
        Vertex q = e.to();
        double w = e.weight();
        if (!distTo.containsKey(q)) {
            distTo.put(q, Double.POSITIVE_INFINITY);
        }
        if ((distTo.get(P) + w) < (distTo.get(q))) {
            distTo.replace((q), distTo.get(P) + w);
            edgeTo.put(q, P);
            if (fringe.contains(q)) {
                fringe.changePriority(q, distTo.get(q) + h(q, finish));
            } else if (!(fringe.contains(q))) {
                fringe.add(q, distTo.get(q) + h(q, finish));
            }
        }
    }

    public SolverOutcome outcome() {
        if (ttt < time) {
            return SolverOutcome.SOLVED;
        } else if (fringe.size() == 0) {
            return SolverOutcome.UNSOLVABLE;
        } else {
            return SolverOutcome.TIMEOUT;
        }
    }

    public List<Vertex> solution() {
        List<Vertex> v = new ArrayList<>();
        Vertex pointer = finish;

        while (!pointer.equals(init)) {
            v.add(pointer);
            pointer = edgeTo.get(pointer);
        }
        v.add(pointer);
        Collections.reverse(v);
        return v;
    }

    public double solutionWeight() {
        return distTo.get(finish);
    }

    public int numStatesExplored() {
        return numst;
    }

    public double explorationTime() {
        return ttt;
    }



  /*  public static void main(String[] args) {
        Stopwatch sw = new Stopwatch();
        AStarSolver(AStarGraph < Vertex > input, Vertex start, Vertex end, double timeout)

        AStarSolver = new AStarSolver<>();
    add(new WeightedEdge<>(v, v + 1, 1));
**/

}
