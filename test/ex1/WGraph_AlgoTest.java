package ex1;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class WGraph_AlgoTest {
    public weighted_graph smallgraph() {
        weighted_graph g = new WGraph_DS();
        g.addNode(0);
        g.addNode(1);
        g.addNode(2);
        g.addNode(3);
        g.addNode(4);
        g.addNode(5);
        g.addNode(6);
        g.addNode(7);
        g.addNode(8);
        g.connect(0, 1, 1);
        g.connect(0, 2, 0.5);
        g.connect(1, 2, 4);
        g.connect(1, 4, 3);
        g.connect(2, 3, 1.5);
        g.connect(2, 8, 4);
        g.connect(3, 7, 10);
        g.connect(4, 5, 0);
        g.connect(5, 6, 1);
        g.connect(7, 8, 1);
        return g;
    }

    public weighted_graph notConnected() {
        weighted_graph g = new WGraph_DS();
        g.addNode(0);
        g.addNode(1);
        g.addNode(2);
        g.addNode(3);
        g.addNode(4);
        g.addNode(5);
        g.addNode(6);
        g.addNode(7);

        return g;
    }

    @Test
    void init() {
        weighted_graph g = smallgraph();
        weighted_graph_algorithms g0 = new WGraph_Algo();
        g0.init(g);
        assertEquals(g, g0.getGraph(), "Graphs should be equals");
        g0.getGraph().removeNode(0);
        g.removeNode(2);
        g0.getGraph().removeEdge(5, 6);
        assertEquals(g, g0.getGraph(), "should still be equals because its a shallow copy");
    }

    @Test
    void copy() {
        weighted_graph g = smallgraph();
        weighted_graph_algorithms g0 = new WGraph_Algo();
        weighted_graph_algorithms g1 = new WGraph_Algo();
        g0.init(g);
        g1.init(g0.copy());
        assertEquals(g0.getGraph(), g1.getGraph(), "copied graph should be equals");
        g1.getGraph().removeNode(6);
        g1.getGraph().connect(1, 2, 3);
        g1.getGraph().addNode(8);
        assertNotEquals(g1.getGraph(), g0.getGraph(), "shouldn't be equal because its a deep copy");
    }

    @Test
    void isConnected() {
        weighted_graph g = smallgraph();
        weighted_graph_algorithms g0 = new WGraph_Algo();
        g0.init(g);
        assertTrue(g0.isConnected(), "Graph should be connected");
        g0.getGraph().removeNode(2);
        assertFalse(g0.isConnected(), "Graph shouldn't be connected");
        g0.getGraph().connect(3, 1, 2);
        assertTrue(g0.isConnected(), "Graph should be connected");
        weighted_graph g1 = new WGraph_DS();
        weighted_graph_algorithms g2 = new WGraph_Algo();
        g2.init(g1);
        assertTrue(g2.isConnected(), "empty graph should be connected");
        g2.getGraph().addNode(0);
        assertTrue(g2.isConnected(), " graph with one node should be connected");
    }

    @Test
    void shortestPathDist() {
        weighted_graph g = smallgraph();
        weighted_graph_algorithms g0 = new WGraph_Algo();
        g0.init(g);
        assertEquals(5.5, g0.shortestPathDist(0, 7), "shortest path between this nodes should be 5.5");
        assertEquals(5, g0.shortestPathDist(0, 6), "shortest path between this nodes should be 5");
        assertEquals(5, g0.shortestPathDist(6, 0), "shortest path between this nodes should be 5");
        assertEquals(6, g0.shortestPathDist(3, 4), "shortest path between this nodes should be 6");
        assertEquals(5.5, g0.shortestPathDist(3, 8), "shortest path between this nodes should be 5.5");
        g0.getGraph().addNode(9);
        assertEquals(-1, g0.shortestPathDist(0, 9), "there isnt a path between this nodes need to be -1 ");
        assertEquals(0, g0.shortestPathDist(1, 1), "the shortest distance between a node to it self is 0");
        weighted_graph g1 = notConnected();
        weighted_graph_algorithms g2 = new WGraph_Algo();
        g2.init(g1);
        assertEquals(-1, g2.shortestPathDist(0, 4), "unconnected graph should be -1");
        assertEquals(-1, g2.shortestPathDist(0, 3), "unconnected graph should be -1");
        assertEquals(-1, g2.shortestPathDist(2, 6), "unconnected graph should be -1");
        assertEquals(-1, g2.shortestPathDist(1, 5), "unconnected graph should be -1");

    }

    @Test
    void shortestPath() {
        weighted_graph g = smallgraph();
        weighted_graph_algorithms g0 = new WGraph_Algo();
        g0.init(g);
        List<node_info> L = g0.shortestPath(0, 7);
        List<node_info> L1 = g0.shortestPath(0, 6);
        int[] checkL = {0, 2, 8, 7};
        int[] checkL1 = {0, 1, 4, 5, 6};
        int i = 0;
        for (node_info n : L) {
            assertEquals(checkL[i], n.getKey(), "List and array should contain the same keys");
            i++;
        }
        i = 0;
        for (node_info n : L1) {
            assertEquals(checkL1[i], n.getKey(), "List and array should contain the same keys");
            i++;
        }
        weighted_graph g1 = notConnected();
        weighted_graph_algorithms g2 = new WGraph_Algo();
        g2.init(g1);
        List<node_info> L2 = g2.shortestPath(0, 6);
        assertNull(L2, "should be empty because its a notConnected graph");
        List<node_info> L3 = g2.shortestPath(4, 4);
        i = 0;
        for (node_info n : L3) {
            assertEquals(4, n.getKey(), "List and array should contain the same keys");
            i++;
        }

    }

    @Test
    void SaveLoad() {
    weighted_graph g = smallgraph();
    weighted_graph_algorithms g0 = new WGraph_Algo();
g0.init(g);
String s = "Graph";
g0.save(s);
g0.load(s);
weighted_graph g1 = smallgraph();
assertEquals(g1,g0.getGraph(),"graphs should be equals");
g0.getGraph().removeEdge(0,1);
assertNotEquals(g1,g0.getGraph(),"Graphs shouldn't be equals");
    }

}