package main.java;

import java.util.*;

/**
 * @author github.com/butburg (EW) on Nov 2021
 * <p>
 * The exercise is to implement the FordFulkerson-algorithm, using the shortest aug-
 * mentation path in each step (Edmonds and Karp’s version). Given a graph with
 * capacities your program shall output the value of an optimal flow, the flow over each
 * edge, and a cut (the one given by the algorithm) proving that the flow is optimal.
 * The graph is a directed graph, i.e. the capacity from vertex u to vertex v can be
 * different from the capacity from v back to u. All capacities are integer and non-
 * negative.
 */
public class FFA {
    /**
     * The first vertex of the graph, the source is always defined as 0 here
     */
    private final int SOURCE = 0;
    /**
     * the dimension of the graph or the number of vertices a graph has in total
     */
    private final int DIMENSION;
    /**
     * the last vertex of the graph is the sind, its m-1, Dimension-1
     */
    private final int SINK;

    //
    /**
     * The BFS will store the path with help of this Map.
     * The first int defines the vertex that got explored by the
     * vertex represented by the second int:
     * Int1 got explored by Int2
     */
    Map<Integer, Integer> parent = new HashMap<>();

    /**
     * Not needed but nice to have:
     * Store which paths where used/found with BFS.
     */
    List<List<Integer>> augmentedPaths = new ArrayList<>();

    /**
     * The original matrix that came as input.
     */
    private int[][] originalMatrix;
    /**
     * The matrix used for making augmented paths and stores the changing flows.
     */
    private int[][] updatedMatrix;
    /**
     * The final Matrix with the maxFLow in the graph shown.
     */
    private int[][] flowMatrix;
    /**
     * The calculated final MaxFlow the graph can provide.
     */
    private int maxFlow;
    /**
     * TreeSet is storing all the vertices at the side of the source in asc order.
     */
    private Set<Integer> cut = new TreeSet<>();
    /**
     * How many times we find a augmented Path/calculate more possibilities.
     */
    private int steps;

    public FFA(int[][] matrix) {
        this.originalMatrix = matrix;
        this.DIMENSION = matrix.length;
        this.SINK = DIMENSION - 1;
        this.flowMatrix = new int[DIMENSION][DIMENSION];

        //copy to matrix for changes
        updatedMatrix = new int[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                updatedMatrix[i][j] = matrix[i][j];
            }
        }
    }

    /**
     * what do I need:
     * take the edges step by step, as soon as we touch the end node, take these path first
     * do a augmented path, find the bottleneck value
     * start again, leave out the used nodes of course
     * <p>
     * when all edges from source are having zero capacity, sum up all the flows, this will be the maximum flow
     */
    public int calculate() {

        //find path using bfs
        while (BFS(false)) {
            //store path that was used
            List<Integer> augmentedPath = new ArrayList<>();

            //highest flow of founded path (equals min capacity of all edges from path)
            int flow = 2147483647; // highest int java can hold -> MAX_VALUE

            int v = SINK; //sink

            //go the path backwards from sink, till we are at the beginning/source
            while (v != SOURCE) {
                //bfs defined a parent, us this one as u
                int u = parent.get(v);
                //
                augmentedPath.add(v);
                // find the smallest flow
                if (flow > updatedMatrix[u][v]) {
                    flow = updatedMatrix[u][v];
                }
                //procced with next parent
                v = u;
            }

            //store path, use reversed order
            augmentedPath.add(SOURCE);//optional
            Collections.reverse(augmentedPath);
            augmentedPaths.add(augmentedPath);

            //add min capacity to sum of max flow
            maxFlow += flow;

            //decrease residual capacity
            //min capcity from u to v in augmented path
            //increase residual capcity by min capcity from v to u
            v = SINK;
            while (v != SOURCE) {
                int u = parent.get(v);
                updatedMatrix[u][v] -= flow;
                updatedMatrix[v][u] += flow;
                flowMatrix[u][v] += flow;
                v = u;
            }
            steps++;
        }

        //add cut nodes, all that are on side S and have some capacity left, include Source as node
        cut.add(0);
        BFS(true);
        return maxFlow;
    }

    /**
     * Using the BFS will find the shortest path from source to sink. Will return false if there is no path left (because maybe all capacities are saturated).
     *
     * @param findCut If false, it will behave normal to find BFS and set parents
     *                so that method calculate can make an augmented path.
     *                Ff true, it will use BFS to find all vertices that
     *                belong to the cut/side of the source. This needs a
     *                completed calculation of BFS and FFA before!
     * @return true if it founds a path from source to the sink (with capacities).
     */
    public boolean BFS(boolean findCut) {
        Set<Integer> visited = new HashSet<>();
        Queue<Integer> queue = new LinkedList<>();
        queue.add(0); //source
        visited.add(0);
        //find path from source to sink
        while (!queue.isEmpty()) {
            int u = queue.poll();
            for (int v = 0; v < DIMENSION; v++) {
                if (!visited.contains(v)) {
                    if (findCut) {
                        if (originalMatrix[u][v] - flowMatrix[u][v] > 0) {
                            //v got explored by u
                            cut.add(v);
                            visited.add(v);
                            queue.add(v);
                        }
                    } else {
                        if (updatedMatrix[u][v] > 0) {
                            //v got explored by u
                            parent.put(v, u);
                            visited.add(v);
                            queue.add(v);
                            //if sink is found then path is found
                            if (v == SINK) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    public int getMaxFlow() {
        return maxFlow;
    }

    public Set<Integer> getCut() {
        return cut;
    }

    public int getSteps() {
        return steps;
    }

    public String printMatrix() {
        StringBuilder matrixPrint = new StringBuilder();
        for (int[] row : flowMatrix) {
            for (int digit : row) {
                matrixPrint.append(digit + " ");
            }
            matrixPrint.append("\n");
        }
        return matrixPrint.toString();

    }

    public String printCut() {
        StringBuilder matrixPrint = new StringBuilder();
        for (int digit : cut) {
            matrixPrint.append(digit + " ");
        }
        return matrixPrint.toString();
    }

    /**
     * Print all paths, found with BFS
     */
    public String printPaths() {
        StringBuilder paths = new StringBuilder();
        for (List l : augmentedPaths) {
            for (Object i : l) {
                paths.append(i + " ");
            }
            paths.append("\n");
        }
        return paths.toString();
    }
}
