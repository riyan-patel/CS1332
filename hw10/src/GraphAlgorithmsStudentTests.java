import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertEquals;

/**
 * Basic student tests to check GraphAlgorithmsNehal. These tests are in
 * no way comprehensive nor do they guarantee any kind of grade.
 *
 * @author Jacob Young, CS 1332 TAs
 * @version 1.0
 */
public class GraphAlgorithmsStudentTests {

    private Graph<Integer> directedGraph;
    private Graph<Character> directedGraph2;
    private Graph<Character> undirectedGraph;
    private Graph<Character> undirectedGraph2;
    public static final int TIMEOUT = 200;

    @Before
    public void init() {
        directedGraph = createDirectedGraph();
        directedGraph2 = createDirectedGraph2();
        undirectedGraph = createUndirectedGraph();
        undirectedGraph2 = createUndirectedGraph2();
    }

    /**
     * Creates a directed graph.
     * The graph is depicted in the pdf.
     *
     * @return the completed graph
     */
    private Graph<Integer> createDirectedGraph() {
        Set<Vertex<Integer>> vertices = new HashSet<Vertex<Integer>>();
        for (int i = 1; i <= 7; i++) {
            vertices.add(new Vertex<Integer>(i));
        }

        Set<Edge<Integer>> edges = new LinkedHashSet<Edge<Integer>>();
        edges.add(new Edge<>(new Vertex<>(1), new Vertex<>(2), 0));
        edges.add(new Edge<>(new Vertex<>(1), new Vertex<>(3), 0));
        edges.add(new Edge<>(new Vertex<>(1), new Vertex<>(4), 0));
        edges.add(new Edge<>(new Vertex<>(3), new Vertex<>(5), 0));
        edges.add(new Edge<>(new Vertex<>(4), new Vertex<>(6), 0));
        edges.add(new Edge<>(new Vertex<>(5), new Vertex<>(4), 0));
        edges.add(new Edge<>(new Vertex<>(5), new Vertex<>(7), 0));
        edges.add(new Edge<>(new Vertex<>(7), new Vertex<>(6), 0));

        return new Graph<Integer>(vertices, edges);
    }

    /**
     * Creates a directed graph.
     * The graph is based on the Small Graph from csvistool.com
     *
     * @return the completed graph
     */
    private Graph<Character> createDirectedGraph2() {
        Set<Vertex<Character>> vertices = new HashSet<Vertex<Character>>();
        for (int i = 65; i <= 72; i++) {
            if (i == 67) {
                continue;
            }
            vertices.add(new Vertex<Character>((char) i));
        }

        Set<Edge<Character>> edges = new LinkedHashSet<Edge<Character>>();
        edges.add(new Edge<>(new Vertex<>('A'), new Vertex<>('B'), 8));
        edges.add(new Edge<>(new Vertex<>('A'), new Vertex<>('E'), 6));

        edges.add(new Edge<>(new Vertex<>('B'), new Vertex<>('A'), 8));

        edges.add(new Edge<>(new Vertex<>('D'), new Vertex<>('F'), 3));

        edges.add(new Edge<>(new Vertex<>('E'), new Vertex<>('A'), 6));
        edges.add(new Edge<>(new Vertex<>('E'), new Vertex<>('H'), 4));

        edges.add(new Edge<>(new Vertex<>('F'), new Vertex<>('D'), 3));
        edges.add(new Edge<>(new Vertex<>('F'), new Vertex<>('G'), 7));
        edges.add(new Edge<>(new Vertex<>('F'), new Vertex<>('H'), 9));

        edges.add(new Edge<>(new Vertex<>('G'), new Vertex<>('F'), 7));
        edges.add(new Edge<>(new Vertex<>('G'), new Vertex<>('H'), 6));

        edges.add(new Edge<>(new Vertex<>('H'), new Vertex<>('E'), 4));
        edges.add(new Edge<>(new Vertex<>('H'), new Vertex<>('F'), 9));
        edges.add(new Edge<>(new Vertex<>('H'), new Vertex<>('G'), 6));

        return new Graph<Character>(vertices, edges);
    }

    /**
     * Creates an undirected graph.
     * The graph is depicted in the pdf.
     *
     * @return the completed graph
     */
    private Graph<Character> createUndirectedGraph() {
        Set<Vertex<Character>> vertices = new HashSet<>();
        for (int i = 65; i <= 70; i++) {
            vertices.add(new Vertex<Character>((char) i));
        }

        Set<Edge<Character>> edges = new LinkedHashSet<>();
        edges.add(new Edge<>(new Vertex<>('A'), new Vertex<>('B'), 7));
        edges.add(new Edge<>(new Vertex<>('B'), new Vertex<>('A'), 7));
        edges.add(new Edge<>(new Vertex<>('A'), new Vertex<>('C'), 5));
        edges.add(new Edge<>(new Vertex<>('C'), new Vertex<>('A'), 5));
        edges.add(new Edge<>(new Vertex<>('C'), new Vertex<>('D'), 2));
        edges.add(new Edge<>(new Vertex<>('D'), new Vertex<>('C'), 2));
        edges.add(new Edge<>(new Vertex<>('A'), new Vertex<>('D'), 4));
        edges.add(new Edge<>(new Vertex<>('D'), new Vertex<>('A'), 4));
        edges.add(new Edge<>(new Vertex<>('D'), new Vertex<>('E'), 1));
        edges.add(new Edge<>(new Vertex<>('E'), new Vertex<>('D'), 1));
        edges.add(new Edge<>(new Vertex<>('B'), new Vertex<>('E'), 3));
        edges.add(new Edge<>(new Vertex<>('E'), new Vertex<>('B'), 3));
        edges.add(new Edge<>(new Vertex<>('B'), new Vertex<>('F'), 8));
        edges.add(new Edge<>(new Vertex<>('F'), new Vertex<>('B'), 8));
        edges.add(new Edge<>(new Vertex<>('E'), new Vertex<>('F'), 6));
        edges.add(new Edge<>(new Vertex<>('F'), new Vertex<>('E'), 6));

        return new Graph<Character>(vertices, edges);
    }

    /**
     * Creates an undirected graph.
     * The graph is based on the Small Graph from csvistool.com
     *
     * @return the completed graph
     */
    private Graph<Character> createUndirectedGraph2() {
        Set<Vertex<Character>> vertices = new HashSet<>();
        for (int i = 65; i <= 72; i++) {
            if (i == 67) {
                continue;
            }
            vertices.add(new Vertex<Character>((char) i));
        }

        Set<Edge<Character>> edges = new LinkedHashSet<>();
        edges.add(new Edge<>(new Vertex<>('A'), new Vertex<>('B'), 8));
        edges.add(new Edge<>(new Vertex<>('A'), new Vertex<>('E'), 6));

        edges.add(new Edge<>(new Vertex<>('B'), new Vertex<>('A'), 8));

        edges.add(new Edge<>(new Vertex<>('D'), new Vertex<>('F'), 3));

        edges.add(new Edge<>(new Vertex<>('E'), new Vertex<>('A'), 6));
        edges.add(new Edge<>(new Vertex<>('E'), new Vertex<>('H'), 4));

        edges.add(new Edge<>(new Vertex<>('F'), new Vertex<>('D'), 3));
        edges.add(new Edge<>(new Vertex<>('F'), new Vertex<>('G'), 7));
        edges.add(new Edge<>(new Vertex<>('F'), new Vertex<>('H'), 9));

        edges.add(new Edge<>(new Vertex<>('G'), new Vertex<>('F'), 7));
        edges.add(new Edge<>(new Vertex<>('G'), new Vertex<>('H'), 6));

        edges.add(new Edge<>(new Vertex<>('H'), new Vertex<>('E'), 4));
        edges.add(new Edge<>(new Vertex<>('H'), new Vertex<>('F'), 9));
        edges.add(new Edge<>(new Vertex<>('H'), new Vertex<>('G'), 6));

        return new Graph<Character>(vertices, edges);
    }

    @Test(timeout = TIMEOUT)
    public void testBFS() {
        List<Vertex<Integer>> bfsActual = GraphAlgorithmsNehal.bfs(new Vertex<Integer>(1), directedGraph);

        List<Vertex<Integer>> bfsExpected = new LinkedList<>();
        bfsExpected.add(new Vertex<Integer>(1));
        bfsExpected.add(new Vertex<Integer>(2));
        bfsExpected.add(new Vertex<Integer>(3));
        bfsExpected.add(new Vertex<Integer>(4));
        bfsExpected.add(new Vertex<Integer>(5));
        bfsExpected.add(new Vertex<Integer>(6));
        bfsExpected.add(new Vertex<Integer>(7));

        assertEquals(bfsExpected, bfsActual);
    }

    @Test(timeout = TIMEOUT)
    public void testBFS2() {
        List<Vertex<Character>> bfsActual = GraphAlgorithmsNehal.bfs(new Vertex<Character>('A'), directedGraph2);

        List<Vertex<Character>> bfsExpected = new LinkedList<>();
        bfsExpected.add(new Vertex<Character>('A'));
        bfsExpected.add(new Vertex<Character>('B'));
        bfsExpected.add(new Vertex<Character>('E'));
        bfsExpected.add(new Vertex<Character>('H'));
        bfsExpected.add(new Vertex<Character>('F'));
        bfsExpected.add(new Vertex<Character>('G'));
        bfsExpected.add(new Vertex<Character>('D'));

        assertEquals(bfsExpected, bfsActual);
    }

    @Test(timeout = TIMEOUT)
    public void testDFS() {
        List<Vertex<Integer>> dfsActual = GraphAlgorithmsNehal.dfs(new Vertex<Integer>(5), directedGraph);

        List<Vertex<Integer>> dfsExpected = new LinkedList<>();
        dfsExpected.add(new Vertex<Integer>(5));
        dfsExpected.add(new Vertex<Integer>(4));
        dfsExpected.add(new Vertex<Integer>(6));
        dfsExpected.add(new Vertex<Integer>(7));

        assertEquals(dfsExpected, dfsActual);
    }

    @Test(timeout = TIMEOUT)
    public void testDFS2() {
        List<Vertex<Character>> dfsActual = GraphAlgorithmsNehal.dfs(new Vertex<Character>('A'), directedGraph2);

        List<Vertex<Character>> dfsExpected = new LinkedList<>();
        dfsExpected.add(new Vertex<Character>('A'));
        dfsExpected.add(new Vertex<Character>('B'));
        dfsExpected.add(new Vertex<Character>('E'));
        dfsExpected.add(new Vertex<Character>('H'));
        dfsExpected.add(new Vertex<Character>('F'));
        dfsExpected.add(new Vertex<Character>('D'));
        dfsExpected.add(new Vertex<Character>('G'));

        assertEquals(dfsExpected, dfsActual);
    }

    @Test(timeout = TIMEOUT)
    public void testDijkstras() {
        Map<Vertex<Character>, Integer> dijkActual = GraphAlgorithmsNehal.dijkstras(new Vertex<Character>('D'),
                undirectedGraph);
        Map<Vertex<Character>, Integer> dijkExpected = new HashMap<>();
        dijkExpected.put(new Vertex<>('A'), 4);
        dijkExpected.put(new Vertex<>('B'), 4);
        dijkExpected.put(new Vertex<>('C'), 2);
        dijkExpected.put(new Vertex<>('D'), 0);
        dijkExpected.put(new Vertex<>('E'), 1);
        dijkExpected.put(new Vertex<>('F'), 7);

        assertEquals(dijkExpected, dijkActual);
    }

    @Test(timeout = TIMEOUT)
    public void testDijkstras2() {
        Map<Vertex<Character>, Integer> dijkActual = GraphAlgorithmsNehal.dijkstras(new Vertex<Character>('A'),
                undirectedGraph2);
        Map<Vertex<Character>, Integer> dijkExpected = new HashMap<>();
        dijkExpected.put(new Vertex<>('A'), 0);
        dijkExpected.put(new Vertex<>('B'), 8);
        // dijkExpected.put(new Vertex<>('C'), Integer.MAX_VALUE);
        dijkExpected.put(new Vertex<>('D'), 22);
        dijkExpected.put(new Vertex<>('E'), 6);
        dijkExpected.put(new Vertex<>('F'), 19);
        dijkExpected.put(new Vertex<>('G'), 16);
        dijkExpected.put(new Vertex<>('H'), 10);

        assertEquals(dijkExpected, dijkActual);
    }

    @Test(timeout = TIMEOUT)
    public void testPrims() {
        Set<Edge<Character>> mstActual = GraphAlgorithmsNehal.prims(new Vertex<>('A'), undirectedGraph);
        Set<Edge<Character>> edges = new HashSet<>();
        edges.add(new Edge<>(new Vertex<>('C'), new Vertex<>('D'), 2));
        edges.add(new Edge<>(new Vertex<>('D'), new Vertex<>('C'), 2));
        edges.add(new Edge<>(new Vertex<>('A'), new Vertex<>('D'), 4));
        edges.add(new Edge<>(new Vertex<>('D'), new Vertex<>('A'), 4));
        edges.add(new Edge<>(new Vertex<>('D'), new Vertex<>('E'), 1));
        edges.add(new Edge<>(new Vertex<>('E'), new Vertex<>('D'), 1));
        edges.add(new Edge<>(new Vertex<>('B'), new Vertex<>('E'), 3));
        edges.add(new Edge<>(new Vertex<>('E'), new Vertex<>('B'), 3));
        edges.add(new Edge<>(new Vertex<>('E'), new Vertex<>('F'), 6));
        edges.add(new Edge<>(new Vertex<>('F'), new Vertex<>('E'), 6));

        assertEquals(edges, mstActual);
    }

    @Test(timeout = TIMEOUT)
    public void testPrims2() {
        Set<Edge<Character>> mstActual = GraphAlgorithmsNehal.prims(new Vertex<>('A'), undirectedGraph2);
        Set<Edge<Character>> edges = new HashSet<>();
        edges.add(new Edge<>(new Vertex<>('A'), new Vertex<>('E'), 6));
        edges.add(new Edge<>(new Vertex<>('E'), new Vertex<>('A'), 6));
        edges.add(new Edge<>(new Vertex<>('E'), new Vertex<>('H'), 4));
        edges.add(new Edge<>(new Vertex<>('H'), new Vertex<>('E'), 4));
        edges.add(new Edge<>(new Vertex<>('H'), new Vertex<>('G'), 6));
        edges.add(new Edge<>(new Vertex<>('G'), new Vertex<>('H'), 6));
        edges.add(new Edge<>(new Vertex<>('G'), new Vertex<>('F'), 7));
        edges.add(new Edge<>(new Vertex<>('F'), new Vertex<>('G'), 7));
        edges.add(new Edge<>(new Vertex<>('F'), new Vertex<>('D'), 3));
        edges.add(new Edge<>(new Vertex<>('D'), new Vertex<>('F'), 3));
        edges.add(new Edge<>(new Vertex<>('A'), new Vertex<>('B'), 8));
        edges.add(new Edge<>(new Vertex<>('B'), new Vertex<>('A'), 8));

        assertEquals(edges, mstActual);
    }

    @Test(timeout = TIMEOUT)
    public void testKruskals() {
        Set<Edge<Character>> mstActual = GraphAlgorithmsNehal.kruskals(undirectedGraph);

        Set<Edge<Character>> mstExpected = new HashSet<>();
        mstExpected.add(new Edge<>(new Vertex<>('C'), new Vertex<>('D'), 2));
        mstExpected.add(new Edge<>(new Vertex<>('D'), new Vertex<>('C'), 2));
        mstExpected.add(new Edge<>(new Vertex<>('A'), new Vertex<>('D'), 4));
        mstExpected.add(new Edge<>(new Vertex<>('D'), new Vertex<>('A'), 4));
        mstExpected.add(new Edge<>(new Vertex<>('D'), new Vertex<>('E'), 1));
        mstExpected.add(new Edge<>(new Vertex<>('E'), new Vertex<>('D'), 1));
        mstExpected.add(new Edge<>(new Vertex<>('B'), new Vertex<>('E'), 3));
        mstExpected.add(new Edge<>(new Vertex<>('E'), new Vertex<>('B'), 3));
        mstExpected.add(new Edge<>(new Vertex<>('E'), new Vertex<>('F'), 6));
        mstExpected.add(new Edge<>(new Vertex<>('F'), new Vertex<>('E'), 6));

        assertEquals(mstExpected, mstActual);
    }

    @Test(timeout = TIMEOUT)
    public void testKruskals2() {
        Set<Edge<Character>> mstActual = GraphAlgorithmsNehal.kruskals(undirectedGraph2);

        Set<Edge<Character>> mstExpected = new HashSet<>();
        mstExpected.add(new Edge<>(new Vertex<>('D'), new Vertex<>('F'), 3));
        mstExpected.add(new Edge<>(new Vertex<>('F'), new Vertex<>('D'), 3));
        mstExpected.add(new Edge<>(new Vertex<>('E'), new Vertex<>('H'), 4));
        mstExpected.add(new Edge<>(new Vertex<>('H'), new Vertex<>('E'), 4));
        mstExpected.add(new Edge<>(new Vertex<>('A'), new Vertex<>('E'), 6));
        mstExpected.add(new Edge<>(new Vertex<>('E'), new Vertex<>('A'), 6));
        mstExpected.add(new Edge<>(new Vertex<>('G'), new Vertex<>('H'), 6));
        mstExpected.add(new Edge<>(new Vertex<>('H'), new Vertex<>('G'), 6));
        mstExpected.add(new Edge<>(new Vertex<>('F'), new Vertex<>('G'), 7));
        mstExpected.add(new Edge<>(new Vertex<>('G'), new Vertex<>('F'), 7));
        mstExpected.add(new Edge<>(new Vertex<>('A'), new Vertex<>('B'), 8));
        mstExpected.add(new Edge<>(new Vertex<>('B'), new Vertex<>('A'), 8));

        assertEquals(mstExpected, mstActual);
    }
}