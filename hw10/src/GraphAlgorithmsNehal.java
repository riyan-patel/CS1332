import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.PriorityQueue;

/**
 * Your implementation of various different graph algorithms.
 *
 * @author Nehal Singhal
 * @userid nsinghal38
 * @GTID 903998926
 * @version 1.0
 * 
 * By typing 'I agree' below, you are agreeing that this is your
 * own work and that you are responsible for all the contents of 
 * this file. If this is left blank, this homework will receive a zero.
 * 
 * Agree Here: I agree.
 */
public class GraphAlgorithmsNehal {

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
        //checks 
        if (start == null || graph == null) {
            throw new IllegalArgumentException("Arguments cannot be null.");
        }
        
        Map<Vertex<T>, List<VertexDistance<T>>> adjList = graph.getAdjList();
        
        if (!adjList.containsKey(start)) {
            throw new IllegalArgumentException("Start vertex doesn't exist in the graph.");
        }
        
        //initialisation
        List<Vertex<T>> out = new ArrayList<>();
        Set<Vertex<T>> visited = new HashSet<>();
        Queue<Vertex<T>> queue = new LinkedList<>();
        
        visited.add(start);
        queue.add(start);

        // loop to add
        while (!queue.isEmpty()) {
            Vertex<T> current = queue.remove();
            out.add(current);
            
            for (VertexDistance<T> vertexDistance : adjList.get(current)) {
                Vertex<T> neighbor = vertexDistance.getVertex();
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    queue.add(neighbor);
                }
            }
        }
        return out;
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
        //checks
        if (start == null || graph == null) {
            throw new IllegalArgumentException("Arguments cannot be null.");
        }
        
        Map<Vertex<T>, List<VertexDistance<T>>> adjList = graph.getAdjList();
        
        if (!adjList.containsKey(start)) {
            throw new IllegalArgumentException("Start vertex doesn't exist in the graph.");
        }
        
        List<Vertex<T>> out = new ArrayList<>();
        Set<Vertex<T>> visited = new HashSet<>();
        
        dfsHelper(start, graph, visited, out);
        
        return out;
    }
    
    /**
     * Helper method for DFS to handle the recursive implementation.
     *
     * @param <T> the generic
     * @param current the current vertex at
     * @param graph the graph being searched
     * @param visited set of vertices that have been gone to
     * @param out list of vertices in visited order
     */
    private static <T> void dfsHelper(Vertex<T> current, Graph<T> graph, 
                                     Set<Vertex<T>> visited, List<Vertex<T>> out) {
        visited.add(current);
        out.add(current);
        
        for (VertexDistance<T> vertexDistance : graph.getAdjList().get(current)) {
            Vertex<T> neighbor = vertexDistance.getVertex();
            if (!visited.contains(neighbor)) {
                dfsHelper(neighbor, graph, visited, out);
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
        if (start == null || graph == null) {
            throw new IllegalArgumentException("Arguments cannot be null.");
        }
        
        Map<Vertex<T>, List<VertexDistance<T>>> adjList = graph.getAdjList();
        
        if (!adjList.containsKey(start)) {
            throw new IllegalArgumentException("Start vertex doesn't exist in the graph.");
        }
        
        // Initialize distance map with infinity for all vertices
        Map<Vertex<T>, Integer> distanceMap = new HashMap<>();
        for (Vertex<T> vertex : graph.getVertices()) {
            distanceMap.put(vertex, Integer.MAX_VALUE);
        }
        distanceMap.put(start, 0);
        
        // Priority queue for vertices to visit, ordered by distance
        PriorityQueue<VertexDistance<T>> pq = new PriorityQueue<>();
        pq.add(new VertexDistance<>(start, 0));
        
        Set<Vertex<T>> visited = new HashSet<>();
        
        while (!pq.isEmpty() && visited.size() < graph.getVertices().size()) {
            VertexDistance<T> current = pq.remove();
            Vertex<T> currentVertex = current.getVertex();
            
            // Skip if already visited
            if (visited.contains(currentVertex)) {
                continue;
            }
            
            visited.add(currentVertex);
            
            // Process all neighbors
            for (VertexDistance<T> neighbor : adjList.get(currentVertex)) {
                Vertex<T> neighborVertex = neighbor.getVertex();
                
                if (!visited.contains(neighborVertex)) {
                    int currentDistance = distanceMap.get(currentVertex);
                    int edgeWeight = neighbor.getDistance();
                    
                    // Check for integer overflow
                    if (currentDistance != Integer.MAX_VALUE) {
                        int newDistance = currentDistance + edgeWeight;
                        
                        if (newDistance < distanceMap.get(neighborVertex)) {
                            distanceMap.put(neighborVertex, newDistance);
                            pq.add(new VertexDistance<>(neighborVertex, newDistance));
                        }
                    }
                }
            }
        }
        
        return distanceMap;
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
        //checks
        if (start == null || graph == null) {
            throw new IllegalArgumentException("Arguments cannot be null.");
        }
        
        Map<Vertex<T>, List<VertexDistance<T>>> adjList = graph.getAdjList();
        
        if (!adjList.containsKey(start)) {
            throw new IllegalArgumentException("Start vertex doesn't exist in the graph.");
        }
        
        Set<Edge<T>> mst = new HashSet<>();
        Set<Vertex<T>> visited = new HashSet<>();
        PriorityQueue<Edge<T>> pq = new PriorityQueue<>();
        
        visited.add(start);
        
        // Add all edges from start vertex to the priority queue
        for (VertexDistance<T> vertexDistance : adjList.get(start)) {
            Vertex<T> neighbor = vertexDistance.getVertex();
            int weight = vertexDistance.getDistance();
            pq.add(new Edge<>(start, neighbor, weight));
        }
        
        while (!pq.isEmpty() && visited.size() < graph.getVertices().size()) {
            Edge<T> edge = pq.remove();
            Vertex<T> u = edge.getU();
            Vertex<T> v = edge.getV();
            
            // If v is already visited, skip this edge
            if (visited.contains(v)) {
                continue;
            }
            
            // Add edge to MST
            mst.add(edge);
            // Add reverse edge to MST for testing purposes
            mst.add(new Edge<>(v, u, edge.getWeight()));
            
            visited.add(v);
            
            // Add all edges from v to unvisited vertices to the PQ
            for (VertexDistance<T> vertexDistance : adjList.get(v)) {
                Vertex<T> neighbor = vertexDistance.getVertex();
                if (!visited.contains(neighbor)) {
                    int weight = vertexDistance.getDistance();
                    pq.add(new Edge<>(v, neighbor, weight));
                }
            }
        }
        
        // If not all vertices are visited, graph is disconnected
        if (visited.size() < graph.getVertices().size()) {
            return null;
        }
        
        return mst;
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
            throw new IllegalArgumentException("Graph cannot be null.");
        }
        
        // Initialize disjoint set
        DisjointSet<T> disjointSet = new DisjointSet<>();
        
        // Initialize MST
        Set<Edge<T>> mst = new HashSet<>();
        
        // Get all edges and sort them by weight
        PriorityQueue<Edge<T>> pq = new PriorityQueue<>(graph.getEdges());
        
        // Initialize counters
        int edgesAdded = 0;
        int vertexCount = graph.getVertices().size();
        
        // Process edges in order of increasing weight
        while (!pq.isEmpty() && edgesAdded < vertexCount - 1) {
            Edge<T> edge = pq.remove();
            Vertex<T> u = edge.getU();
            Vertex<T> v = edge.getV();
            
            // Skip self loops
            if (u.equals(v)) {
                continue;
            }
            
            // Find the sets containing u and v
            T uRoot = disjointSet.find(u);
            T vRoot = disjointSet.find(v);
            
            // If u and v are in different sets, add edge to MST
            if (!uRoot.equals(vRoot)) {
                mst.add(edge);
                // Add reverse edge for testing purposes
                mst.add(new Edge<>(v, u, edge.getWeight()));
                
                // Union the sets
                disjointSet.union(uRoot, vRoot);
                edgesAdded++;
            }
        }
        
        // If we couldn't add enough edges, graph is disconnected
        if (edgesAdded < vertexCount - 1) {
            return null;
        }
        
        return mst;
    }
}