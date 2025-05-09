import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.Queue;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Map;
import java.util.HashMap;

/**
 * Your implementation of various different graph algorithms.
 *
 * @author Riyan Patel
 * @userid rpatel816
 * @GTID 903978548
 * @version 1.0
 * 
 * By typing 'I agree' below, you are agreeing that this is your
 * own work and that you are responsible for all the contents of 
 * this file. If this is left blank, this homework will receive a zero.
 * 
 * Agree Here: I agree
 */
public class GraphAlgorithms {

    /**
     * Performs a breadth first search (bfs) on the input graph, starting at
     * the parameterized starting vertex.
     *
     * When exploring a vertex, explore in the order of neighbors returned by
     * the adjacency list. Failure to do so may cause you to lose points.
     *
     * You may import/use java.util.Set, java.util.List, java.util.Queue, and
     * any classes that implement the aforementioned interfaces, as long as they
     * are efficient.
     *
     * The only instance of java.util.Map that you may use is the
     * adjacency list from graph. DO NOT create new instances of Map
     * for BFS (storing the adjacency list in a variable is fine).
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the bfs on
     * @param graph the graph to search through
     * @return list of vertices in visited order
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph
     */
    public static <T> List<Vertex<T>> bfs(Vertex<T> start, Graph<T> graph) {
        if (start == null || graph == null
            || !graph.getAdjList().containsKey(start)) {
            throw new IllegalArgumentException("start or graph can't be null");
        }
        Queue<Vertex<T>> queue = new LinkedList<>();
        Set<Vertex<T>> visited = new HashSet<>();
        List<Vertex<T>> result = new ArrayList<>();

        queue.add(start);
        visited.add(start);

        while (!queue.isEmpty()) {
            Vertex<T> temp = queue.remove();
            result.add(temp);
            for (VertexDistance<T> distance: graph.getAdjList().get(temp)) {
                if (!visited.contains(distance.getVertex())) {
                    queue.add(distance.getVertex());
                    visited.add(distance.getVertex());
                }
            }
        }
        return result;
    }

    /**
     * Performs a depth first search (dfs) on the input graph, starting at
     * the parameterized starting vertex.
     *
     * When exploring a vertex, explore in the order of neighbors returned by
     * the adjacency list. Failure to do so may cause you to lose points.
     *
     * *NOTE* You MUST implement this method recursively, or else you will lose
     * all points for this method.
     *
     * You may import/use java.util.Set, java.util.List, and
     * any classes that implement the aforementioned interfaces, as long as they
     * are efficient.
     *
     * The only instance of java.util.Map that you may use is the
     * adjacency list from graph. DO NOT create new instances of Map
     * for DFS (storing the adjacency list in a variable is fine).
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the dfs on
     * @param graph the graph to search through
     * @return list of vertices in visited order
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph
     */
    public static <T> List<Vertex<T>> dfs(Vertex<T> start, Graph<T> graph) {
        if (start == null || graph == null
            || !graph.getAdjList().containsKey(start)) {
            throw new IllegalArgumentException("start or graph can't be null");
        }
        Set<Vertex<T>> visited = new HashSet<>();
        List<Vertex<T>> result = new ArrayList<>();

        dfsHelper(start, graph, visited, result);
        return result;
    }

    /**
     * Recursive helper for depth‑first search, marking and collecting vertices.
     *
     * @param <T> the type of data stored in each vertex
     * @param vertex the current vertex to visit
     * @param graph the graph being traversed
     * @param set the set of already visited vertices
     * @param result the list accumulating the visit order
     */
    private static <T> void dfsHelper(Vertex<T> vertex, Graph<T> graph,
                                Set<Vertex<T>> set, List<Vertex<T>> result) {
        result.add(vertex);
        set.add(vertex);

        for (VertexDistance<T> distance : graph.getAdjList().get(vertex)) {
            if (!set.contains(distance.getVertex())) {
                dfsHelper(distance.getVertex(), graph, set, result);
            }
        }
    }

    /**
     * Finds the single-source shortest distance between the start vertex and
     * all vertices given a weighted graph (you may assume non-negative edge
     * weights).
     *
     * Return a map of the shortest distances such that the key of each entry
     * is a node in the graph and the value for the key is the shortest distance
     * to that node from start, or Integer.MAX_VALUE (representing
     * infinity) if no path exists.
     *
     * You may import/use java.util.PriorityQueue,
     * java.util.Map, and java.util.Set and any class that
     * implements the aforementioned interfaces, as long as your use of it
     * is efficient as possible.
     *
     * You should implement the version of Dijkstra's where you use two
     * termination conditions in conjunction.
     *
     * 1) Check if all of the vertices have been visited.
     * 2) Check if the PQ is empty.
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the Dijkstra's on (source)
     * @param graph the graph we are applying Dijkstra's to
     * @return a map of the shortest distances from start to every
     * other node in the graph
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph.
     */
    public static <T> Map<Vertex<T>, Integer> dijkstras(Vertex<T> start,
                                                        Graph<T> graph) {
        if (start == null || graph == null
                || !graph.getAdjList().containsKey(start)) {
            throw new IllegalArgumentException("start or graph can't be null");
        }

        Queue<VertexDistance<T>> queue = new PriorityQueue<>();
        Map<Vertex<T>, Integer> result = new HashMap<>();

        for (Vertex<T> vertex : graph.getAdjList().keySet()) {
            if (vertex.equals(start)) {
                result.put(vertex, 0);
            } else {
                result.put(vertex, Integer.MAX_VALUE);
            }
        }
        queue.add(new VertexDistance<>(start, 0));
        while (!queue.isEmpty()) {
            VertexDistance<T> temp = queue.remove();
            for (VertexDistance<T> dist : graph.getAdjList().get(temp.getVertex())) {
                int distance = temp.getDistance() + dist.getDistance();
                if (result.get(dist.getVertex()) > distance) {
                    result.put(dist.getVertex(), distance);
                    queue.add(new VertexDistance<>(dist.getVertex(), distance));
                }
            }
        }
        return result;
    }

    /**
     * Runs Prim's algorithm on the given graph and returns the Minimum
     * Spanning Tree (MST) in the form of a set of Edges. If the graph is
     * disconnected and therefore no valid MST exists, return null.
     *
     * You may assume that the passed in graph is undirected. In this framework,
     * this means that if (u, v, 3) is in the graph, then the opposite edge
     * (v, u, 3) will also be in the graph, though as a separate Edge object.
     *
     * The returned set of edges should form an undirected graph. This means
     * that every time you add an edge to your return set, you should add the
     * reverse edge to the set as well. This is for testing purposes. This
     * reverse edge does not need to be the one from the graph itself; you can
     * just make a new edge object representing the reverse edge.
     *
     * You may assume that there will only be one valid MST that can be formed.
     *
     * You should NOT allow self-loops or parallel edges in the MST.
     *
     * You may import/use PriorityQueue, java.util.Set, and any class that 
     * implements the aforementioned interface.
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * The only instance of java.util.Map that you may use is the
     * adjacency list from graph. DO NOT create new instances of Map
     * for this method (storing the adjacency list in a variable is fine).
     *
     * @param <T> the generic typing of the data
     * @param start the vertex to begin Prims on
     * @param graph the graph we are applying Prims to
     * @return the MST of the graph or null if there is no valid MST
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph.
     */
    public static <T> Set<Edge<T>> prims(Vertex<T> start, Graph<T> graph) {
        if (start == null || graph == null) {
            throw new IllegalArgumentException("Input or graph is null");
        }
        if (!graph.getVertices().contains(start)) {
            throw new IllegalArgumentException("Start vertex does not exist in graph");
        }

        Set<Vertex<T>> visited = new HashSet<>();
        Set<Edge<T>> eSet = new HashSet<>();
        Queue<Edge<T>> queue = new PriorityQueue<>();
        visited.add(start);

        for (VertexDistance<T> dist : graph.getAdjList().get(start)) {
            queue.add(new Edge<>(start, dist.getVertex(), dist.getDistance()));
        }
        while (!queue.isEmpty() && visited.size() < graph.getVertices().size()) {
            Edge<T> currE = queue.remove();
            Vertex<T> currV = currE.getV();

            if (!visited.contains(currV)) {
                visited.add(currV);
                eSet.add(currE);
                eSet.add(new Edge<>(currV, currE.getU(), currE.getWeight()));

                for (VertexDistance<T> dist : graph.getAdjList().get(currV)) {
                    Vertex<T> neighborVertex = dist.getVertex();
                    if (!visited.contains(neighborVertex)) {
                        queue.add(new Edge<>(currV, neighborVertex, dist.getDistance()));
                    }
                }
            }
        }
        if (visited.size() < graph.getVertices().size()) {
            return null;
        }
        return eSet;
    }

    /**
     * Runs Kruskal's algorithm on the given graph and returns the Minimal
     * Spanning Tree (MST) in the form of a set of Edges. If the graph is
     * disconnected and therefore no valid MST exists, return null.
     *
     * You may assume that the passed in graph is undirected. In this framework,
     * this means that if (u, v, 3) is in the graph, then the opposite edge
     * (v, u, 3) will also be in the graph, though as a separate Edge object.
     *
     * The returned set of edges should form an undirected graph. This means
     * that every time you add an edge to your return set, you should add the
     * reverse edge to the set as well. This is for testing purposes. This
     * reverse edge does not need to be the one from the graph itself; you can
     * just make a new edge object representing the reverse edge.
     *
     * You may assume that there will only be one valid MST that can be formed.
     *
     * Kruskal's will also require you to use a Disjoint Set which has been
     * provided for you. A Disjoint Set will keep track of which vertices are
     * connected given the edges in your current MST, allowing you to easily
     * figure out whether adding an edge will create a cycle. Refer
     * to the DisjointSet and DisjointSetNode classes that
     * have been provided to you for more information.
     *
     * An MST should NOT have self-loops or parallel edges.
     *
     * By using the Disjoint Set provided, you can avoid adding self-loops and
     * parallel edges into the MST.
     *
     * You may import/use java.util.PriorityQueue,
     * java.util.Set, and any class that implements the aforementioned
     * interfaces.
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param graph the graph we are applying Kruskals to
     * @return the MST of the graph or null if there is no valid MST
     * @throws IllegalArgumentException if any input is null
     */
    public static <T> Set<Edge<T>> kruskals(Graph<T> graph) {
        if (graph == null) {
            throw new IllegalArgumentException("graph can't be null");
        }
        DisjointSet<T> disjointSet = new DisjointSet<>();
        Set<Edge<T>> eSet = new HashSet<>();
        Queue<Edge<T>> queue = new PriorityQueue<>();
        queue.addAll(graph.getEdges());

        while (!queue.isEmpty() && eSet.size() < 2 * (graph.getVertices().size() - 1)) {
            Edge<T> currE = queue.remove();
            Vertex<T> uVal = currE.getU();
            Vertex<T> vVal = currE.getV();

            T u = disjointSet.find(uVal);
            T v = disjointSet.find(vVal);
            if (!u.equals(v)) {
                eSet.add(currE);
                eSet.add(new Edge<>(vVal, uVal, currE.getWeight()));
                disjointSet.union(u, v);
            }
        }

        if (eSet.size() != 2 * (graph.getVertices().size() - 1)) {
            return null;
        }
        return eSet;
    }
}