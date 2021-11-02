package main.java;

/**
 * @author github.com/butburg (EW) on Nov 2021
 * <p>
 * The exercise is to implement the FordFulkerson-algorithm, using the shortest aug-
 * mentation path in each step (Edmonds and Karp’s version). Given a graph with
 * capacities your program shall output the value of an optimal flow, the flow over each
 * edge, and a cut (the one given by the algorithm) proving that the flow is optimal.
 * The graph is a directed graph, i.e. the capacity from vertex u to vertex v can be
 * different from the capacity from v back to u. All capacities are integer and non-
 * negative. (We use the term vertex in this exercise; it is sometimes also called a node.)
 */
public class FFA {
    private int[][] matrix;
    private int maxFlow;
    private int[] cut;
    private int steps;

    public FFA(int[][] matrix) {
        this.matrix = matrix;
    }

    /**
     * read matrix like:
     *
     */
    public void calculate() {

    }


    public int getMaxFlow() {
        return maxFlow;
    }

    public int[] getCut() {
        return cut;
    }

    public int getSteps() {
        return steps;
    }

    public void setMaxFlow(int maxFlow) {
        this.maxFlow = maxFlow;
    }

    public void setCut(int[] cut) {
        this.cut = cut;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }

    public String printMatrix() {
        StringBuilder matrixPrint = new StringBuilder();
        for (int[] row : matrix) {
            for (int digit : row) {
                matrixPrint.append(digit + " ");
            }
            matrixPrint.append("\n");
        }
        return matrixPrint.toString();

    }
}
