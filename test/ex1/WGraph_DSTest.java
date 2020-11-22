package ex1;

import org.junit.jupiter.api.Test;
import java.util.Iterator;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class
WGraph_DSTest {
    @Test
    void nodeSize(){
        weighted_graph g = new WGraph_DS();
        g.addNode(0);
        g.addNode(1);
        g.addNode(2);
        g.addNode(3);
        g.addNode(4);
        int a = g.nodeSize();
        assertEquals(5,a,"This Graph should have 5 Nodes");
        g.removeNode(2);
        g.removeNode(0);
        g.removeNode(0);
        int b=g.nodeSize();
        assertEquals(3,b,"This Graph should have 3 Nodes");
    }
    @Test
    void edgeSizeNum(){
        weighted_graph g = new WGraph_DS();
        g.addNode(0);
        g.addNode(1);
        g.addNode(2);
        g.addNode(3);
        g.addNode(4);
        g.addNode(5);
        g.addNode(6);
        assertEquals(0,g.edgeSize(),"the graph shouldn't have edges yet but he has :"+ g.edgeSize());
        g.connect(0,1,1);
        g.connect(0,1,1);
        g.connect(0,2,2);
        g.connect(2,4,3);
        g.connect(3,5,1);
        g.connect(3,2,1);
        g.connect(1,6,2.2);
        int edn = g.edgeSize();
        assertEquals(6,edn,"This graph should have 6 edges but instead it has "+edn);
        g.removeNode(6);
        int edn1 =g.edgeSize();
        assertEquals(5,edn1,"This graph should have 5 edges but instead it has "+edn);
        double a=g.getEdge(0,1);
        double b=g.getEdge(2,4);
        double c=g.getEdge(0,5);
        assertEquals(a,1,"should be 1.0 but came out :"+a);
        assertEquals(b,3,"should be 1.0 but came out :"+b);
        assertEquals(c,-1,"should be -1.0 but came out :"+c);
    }
    @Test
    void getV(){
        weighted_graph g = new WGraph_DS();
        g.addNode(0);
        g.addNode(1);
        g.addNode(2);
        g.addNode(3);
        g.addNode(4);
        g.addNode(5);
        g.addNode(6);
        Iterator<node_info> b=g.getV().iterator();
        int i=0;
        while(b.hasNext()){
            node_info n= b.next();
            assertEquals(i,n.getKey(),"the node should be with key of :"+i+" but instead the key is :"+n.getKey());
            i++;
            assertNotNull(n);
        }
        g.removeNode(3);
        Iterator<node_info> c=g.getV().iterator();
        while(c.hasNext()){
            node_info n1=c.next();
            assertNotNull(n1,"shouldn't be null should be Node with the key of :"+n1.getKey());
        }
    }
@Test
    void hasEdge(){
    weighted_graph g = new WGraph_DS();
    g.addNode(0);
    g.addNode(1);
    g.addNode(2);
    g.addNode(3);
    g.addNode(4);
    g.addNode(5);
    g.addNode(6);
    g.connect(0,1,1);
    g.connect(0,2,2);
    g.connect(2,4,3);
    g.connect(3,5,1);
    g.connect(3,2,1);
    g.connect(1,6,2.2);
    assertTrue(g.hasEdge(0,1),"This Nodes should have an edge between them");
    assertTrue(g.hasEdge(3,2),"This Nodes should have an edge between them");
    assertFalse(g.hasEdge(0,6),"Those Nodes shouldn't have an edge between them");
    g.removeEdge(0,1);
    assertFalse(g.hasEdge(0,1),"Those Nodes shouldn't have an edge between them");
    g.removeNode(2);
    assertFalse(g.hasEdge(2,4),"cannot have edge with a deleted node");
    g.connect(0,0,2);
    assertFalse(g.hasEdge(0,0),"Node cant have an edge to itself");
}
@Test
    void connect() {
    weighted_graph g = new WGraph_DS();
    g.addNode(0);
    g.addNode(1);
    g.addNode(2);
    g.addNode(3);
    g.addNode(4);
    g.addNode(5);
    g.addNode(6);
    g.connect(0, 1, 1);
    g.connect(0, 2, 2);
    g.connect(2, 4, 3);
    g.connect(3, 5, 1);
    g.connect(3, 2, 1);
    g.connect(1, 6, 2.2);
    assertTrue(g.hasEdge(0, 1), "This nodes should have an edge between them");
    assertTrue(g.hasEdge(1, 0), "This nodes should have an edge between them");
    assertTrue(g.hasEdge(3, 5), "This nodes should have an edge between them");
    assertTrue(g.hasEdge(3, 2), "This nodes should have an edge between them");
    assertFalse(g.hasEdge(0, 6), "This nodes shouldn't have an edge between them");
}
    @Test
            void removeNode(){
        weighted_graph g = new WGraph_DS();
        g.addNode(0);
        g.addNode(1);
        g.addNode(2);
        g.addNode(3);
        g.addNode(4);
        g.addNode(5);
        g.addNode(6);
        g.connect(0, 1, 1);
        g.connect(0, 2, 2);
        g.connect(2, 4, 3);
        g.connect(3, 5, 1);
        g.connect(3, 2, 1);
        g.connect(1, 6, 2.2);
        assertNotNull(g.getNode(6),"problem at addNode");
        g.removeNode(6);
        assertNull(g.getNode(6),"This graph shouldn't contain this key because it got deleted");
        assertFalse(g.hasEdge(1,6),"cant have edges with a deleted node");
        assertNull(g.getNode(7),"cant delete a node thats not in the graph");

    }

    @Test
    void removeEdge(){
        weighted_graph g = new WGraph_DS();
        g.addNode(0);
        g.addNode(1);
        g.addNode(2);
        g.addNode(3);
        g.addNode(4);
        g.addNode(5);
        g.addNode(6);
        g.connect(0, 1, 1);
        g.connect(0, 2, 2);
        g.connect(2, 4, 3);
        g.connect(3, 5, 1);
        g.connect(3, 2, 1);
        g.connect(1, 6, 2.2);
        assertTrue(g.hasEdge(2,4),"should be an edge here");
        g.removeEdge(2,4);
        assertFalse(g.hasEdge(2,4),"this edge was falied to be removed");
        double a =g.getEdge(2,4);
        assertEquals(-1,a,"should be -1 because there isnt an edge between them");
    }

    private static Random _rnd = null;



}