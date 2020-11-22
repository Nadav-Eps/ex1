package ex1;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Objects;

/**
 * Creating a graph using two HashMaps
 * The first one if for saving all the graph nodes with their keys.
 * the second one contains all the graph keys and in the value contains
 * another HashMap with each node Neighbors and Weight of an edge.
 */
public class WGraph_DS implements weighted_graph,java.io.Serializable{
private int Edges;
private int MC;
private int Size;
    private HashMap<Integer,node_info> GraphNodes;
    private HashMap<Integer,HashMap<Integer,Double>> Neighbors;

    /**
     * Empty constructor to initialize a new graph.
     */
    public WGraph_DS(){
        this.Edges=0;
        this.MC=0;
        this.Size=0;
        GraphNodes = new HashMap<Integer, node_info>();
        Neighbors = new HashMap<Integer,HashMap<Integer,Double>>();
    }

    /**
     * Function to compare between to Graphs by checking if all
     * the Nodes that in Graph 1 are at graph 2 then if each node have the same Neighbors
     * with the same distance adn then if there is exactly same number of nodes and edges
     * and because we checked if one contains all the nodes of the other one and if the
     * size of the nodes and edges are the same it will return true.
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WGraph_DS wGraph_ds = (WGraph_DS) o;
        Iterator<node_info> itr = GraphNodes.values().iterator();
        Iterator<node_info> itr1 = ((WGraph_DS) o).GraphNodes.values().iterator();
        while (itr.hasNext()){
            node_info a = itr.next();
            node_info b= itr1.next();
            if(a.getKey()!=b.getKey()){ return false;}
            Iterator<node_info> itr2=getV(a.getKey()).iterator();
            while (itr2.hasNext()){
                if(!getV(b.getKey()).contains(getNode(itr2.next().getKey()))) {return false;}
            }
        }
        return Edges == wGraph_ds.Edges &&
                Size == wGraph_ds.Size ;
    }


    /**
     * return the wanted Node by his key and the hash map of the graph.
     * @param key - the node_id
     * @return
     */
    @Override
    public node_info getNode(int key) {
        if(GraphNodes.containsKey(key))
        return GraphNodes.get(key);
        return null;
    }

    /**
     * This function wants to check if theres an edge between two nodes
     * by checking each og the nodes hashmaps of neighbors and see if it contains
     * the other node.
     * @param node1
     * @param node2
     * @return
     */
    @Override
    public boolean hasEdge(int node1, int node2) {
    if((Neighbors.get(node1).containsKey(node2))&&(Neighbors.get(node2).containsKey(node1))) {
        return true;
    }
        return false;
    }

    /**
     * return the weight of an edge between to nodes
     * by taking it from the hashmap we saved it in.
     * @param node1
     * @param node2
     * @return
     */
    @Override
    public double getEdge(int node1, int node2) {
        if (hasEdge(node1 , node2)){
            return Neighbors.get(node1).get(node2);
        }
        return -1;
    }

    /**
     * Adds a node to the graph only if it has a key thats diffrent from others keys we got before.
     * then adds him to the hashmap of the graph and create a new hash map for his Neighbors.
     * @param key
     */
    @Override
    public void addNode(int key) {
if (!GraphNodes.containsKey(key)&&key>=0) {
    node_info n = new NodeData(key);
    Neighbors.put(key,new HashMap<Integer, Double>());
    GraphNodes.put(key, n);
    Size++;
    MC++;
}
    }

    /**
     *This function connects two nodes at the graph by adding
     * each node key with weight to the other HashMap of Neighbors.
     *only if the Nodes are at the graph.
     * @param node1
     * @param node2
     * @param w
     */
    @Override
     public void connect(int node1, int node2, double w) {
if(((GraphNodes.containsKey(node1))&&(GraphNodes.containsKey(node2)))&&(node1!=node2)&&(w>=0)){
    if(!hasEdge(node1,node2)) {
        HashMap<Integer, Double> tmp = Neighbors.get(node2);
        HashMap<Integer, Double> tmp1 = Neighbors.get(node1);
        tmp.put(node1, w);
        tmp1.put(node2, w);
        Neighbors.put(node2, tmp);
        Neighbors.put(node1, tmp1);
        Edges++;
        MC++;
    }
    else {
        if(w!=getEdge(node1,node2))
            MC++;
        Neighbors.get(node2).put(node1,w);
        Neighbors.get(node1).put(node2,w);
    }
}
    }

    /**
     * return a collection og the Graph nodes
     * @return
     */
    @Override
    public Collection<node_info> getV() {
        return GraphNodes.values();
    }

    /**
     * return a wanted Node Neighbors as a collection og Nodes
     * by
     * @param node_id
     * @return
     */
    @Override
    public Collection<node_info> getV(int node_id) {
        Iterator<Integer> itr = Neighbors.get(node_id).keySet().iterator();
        HashMap<Integer,node_info> tmp = new HashMap<Integer, node_info>();
       while (itr.hasNext()) {
           int x =itr.next();
       tmp.put(x,getNode(x));
       }

        return tmp.values();
    }

    /**
     *Removes a node from the graph by deleting him from the Graphs HashMap
     * and then going to all his Neighbors and deleting him from his Neighbors
     * HashMap of Neighbors .
     * @param key
     * @return
     */
    @Override
    public node_info removeNode(int key) {
        if(GraphNodes.containsKey(key)){
            Iterator<Integer> itr = Neighbors.get(key).keySet().iterator();
            while (itr.hasNext()){
                Neighbors.get(itr.next()).remove(key);
                Edges--;
                MC++;
            }
            Neighbors.get(key).clear();
            GraphNodes.remove(key);
            MC++;
            Size--;
        }
        return GraphNodes.get(key);
    }

    /**
     * Remove an edge between two wanted nodes by deleting each other
     * from the others HashMap of Neighbors.
     * @param node1
     * @param node2
     */
    @Override
    public void removeEdge(int node1, int node2) {
if(hasEdge(node1,node2)){
    Neighbors.get(node1).remove(node2);
    Neighbors.get(node2).remove(node1);
    Edges--;
    MC++;
}

    }

    @Override
    public int nodeSize() {
        return Size;
    }

    @Override
    public int edgeSize() {
        return Edges;
    }

    @Override
    public int getMC() {
        return MC;
    }
    public class NodeData implements node_info,java.io.Serializable{
        private int key;
        private double tag;
        private String Info;


        public NodeData (int key){
            this.tag=-1;
            this.key=key;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            NodeData nodeData = (NodeData) o;
            return key == nodeData.key;
        }


        @Override
        public int getKey() {
            return key;
        }

        @Override
        public String getInfo() {
            return Info;
        }

        @Override
        public void setInfo(String s) {
        this.Info=s;
        }

        @Override
        public double getTag() {

            return tag;
        }

        @Override
        public void setTag(double t) {
        this.tag=t;
        }
    }
}
