package ex1;

//import java.util.Iterator;
import java.io.*;
import java.util.*;

public class WGraph_Algo implements weighted_graph_algorithms, java.io.Serializable {
    private weighted_graph gr;
    private node_info n;

    public WGraph_Algo() {
        gr = new WGraph_DS();
    }
    /**
     * Init the graph on which this set of algorithms operates on.
     * @param g
     */
    @Override
    public void init(weighted_graph g) {
        this.gr = g;
    }

    /**
     * Return the underlying graph of which this class works.
     * @return
     */
    @Override
    public weighted_graph getGraph() {
        return gr;
    }

    /**
     * Copies the graph by creating a new empty graph
     * then adds to his Node HashMap all the nodes that are at the original graph.
     * then going to each node HashMap of neighbors and connects them by connect function (with their weight of edge).
     * and by that it deep coping a graph.
     * @return
     */
    @Override
    public weighted_graph copy() {
        weighted_graph g = new WGraph_DS();
        Iterator<node_info> itr = gr.getV().iterator();
        while (itr.hasNext()) {
            n = itr.next();
            g.addNode(n.getKey());
            Iterator<node_info> itr1 = gr.getV(n.getKey()).iterator();
            while (itr1.hasNext()) {
                int x = itr1.next().getKey();
                g.connect(n.getKey(), x, gr.getEdge(n.getKey(), x));
            }
        }
        return g;
    }

    /**
     * Return true if the graph is connected adn false if he isnt.
     * starting from the the "first" node at the HashMap changing his tag to 0.
     * then going to his neighbors and changing their tags as well and so on untill all the nodes
     * that connected to the node we started in are with tag!=-1.
     * then going on the graph HashMap and checking if theres a node with tag -1
     * if there is such a node return false otherwise true.
     * @return
     */
    @Override
    public boolean isConnected() {
        boolean flag = true;
        Iterator<node_info> itr = gr.getV().iterator();
        if (itr.hasNext()) {
            n = itr.next();
            Queue<node_info> Q = new LinkedList<>();
            Q.add(n);
            n.setTag(n.getTag() + 1);
            while (!Q.isEmpty()) {
                Iterator<node_info> itr1 = gr.getV(Q.poll().getKey()).iterator();
                while (itr1.hasNext()) {
                    n = itr1.next();
                    if (n.getTag() < 0) {
                        n.setTag(n.getTag() + 1);
                        Q.add(n);
                    }
                }
            }
        }

        Iterator<node_info> itr2 = gr.getV().iterator();
        while (itr2.hasNext()) {
            n = itr2.next();
            if (n.getTag() < 0) {
                flag = false;
            }
            n.setTag(-1);
        }
        return flag;
    }

    /**
     * Return the shortest path length between to wanted nodes.
     * changing the src tag to 0 then going to all his neighbor and change their tags to his tag + the weight of the edge
     * then to their neighbors but only changes their tags if the tag is -1 or the tag+the edge is smaller then the node tag.
     * by that all the nodes tags  are exactly the shortest path betweeen the src to each node.
     * if there isnt a path between src to a node it will not change his tag so it will be still -1.
     * then returns the dest node tag.
     * @param src - start node
     * @param dest - end (target) node
     * @return
     */
    @Override
    public double shortestPathDist(int src, int dest) {
        if (gr.getNode(src) != null && gr.getNode(dest) != null) {
            n = gr.getNode(src);
            Queue<node_info> Q = new LinkedList<>();
            Q.add(n);
            n.setTag(n.getTag() + 1);
            while (!Q.isEmpty()) {
                node_info c = Q.poll();
                Iterator<node_info> itr = gr.getV(c.getKey()).iterator();
                while (itr.hasNext()) {
                    n = itr.next();
                    double d = gr.getEdge(n.getKey(), c.getKey());
                    if (n.getTag() == -1) {
                        n.setTag(c.getTag() + d);
                        Q.add(n);
                    } else if (n.getTag() > c.getTag() + d) {
                        n.setTag(c.getTag() + d);
                        Q.add(n);
                    }
                }
            }
            double ans = gr.getNode(dest).getTag();
            Iterator<node_info> itr2 = gr.getV().iterator();
            while (itr2.hasNext()) {
                n = itr2.next();
                n.setTag(-1);
            }
            return ans;
        }
        return -1;
    }

    /**
     * Return a list of nodes that we need to go throw for the shortest path between two wanted nodes.
     * using the same method i explained at shortestPathDist to change nodes tags to the weight of the shortest path from src node.
     * now going from dest node and looking in his neighbors if there is a node that his tag + the weight of
     * the edge between them equals (in epsilon environment) to the node we are on now tag add him to the list and
     * go to his neighbors and so on until we'll get to the src node and return the list.
     * using stack first then from the stack to a list so we'll get it in the right order.
     * @param src - start node
     * @param dest - end (target) node
     * @return
     */
    @Override
    public List<node_info> shortestPath(int src, int dest) {
        if (gr.getNode(src) != null && gr.getNode(dest) != null) {
            n = gr.getNode(src);
            Queue<node_info> Q = new LinkedList<>();
            Q.add(n);
            n.setTag(n.getTag() + 1);
            while (!Q.isEmpty()) {
                node_info c = Q.poll();
                Iterator<node_info> itr = gr.getV(c.getKey()).iterator();
                while (itr.hasNext()) {
                    n = itr.next();
                    double d = gr.getEdge(n.getKey(), c.getKey());
                    if (n.getTag() == -1) {
                        n.setTag(c.getTag() + d);
                        Q.add(n);
                    } else if (n.getTag() > c.getTag() + d) {
                        n.setTag(c.getTag() + d);
                        Q.add(n);
                    }
                }
            }
            if (gr.getNode(dest).getTag() == -1) {
                return null;
            }
            double EPS = 0.000001;
            List<node_info> L = new ArrayList<>();
            Stack<node_info> ST = new Stack<>();
            double i = gr.getNode(dest).getTag();
            node_info d = gr.getNode(dest);
            ST.push(d);
//            while (i >= 0) {
            while(d.getKey()!=src){
//                if (d.getKey() == src) {
//                    break;
//                }
                n = d;
                Iterator<node_info> itr2 = gr.getV(n.getKey()).iterator();
                while (itr2.hasNext()) {
                    d = itr2.next();
                    double o = n.getTag() - gr.getEdge(d.getKey(), n.getKey());
                    if ((Math.abs(d.getTag() - o) < EPS)) {
                        ST.push(d);
                        i = i - gr.getEdge(d.getKey(), n.getKey());
                        break;
                    }
                }
            }
            while (!ST.isEmpty()) {
                L.add(ST.pop());
            }
            Iterator<node_info> itr2 = gr.getV().iterator();
            while (itr2.hasNext()) {
                n = itr2.next();
                n.setTag(-1);
            }
            return L;
        }
        return null;
    }

    /**
     * Saves a Weighted Graph as a file by pushing the object that the
     * grapg algo is pointing right now as an object og graph to a file.
     * @param file - the file name (may include a relative path).
     * @return
     */
    @Override
    public boolean save(String file) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            ObjectOutputStream objectOutputStream
                    = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(this.gr);
            fileOutputStream.close();
            objectOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * loads a graph that been saved as a file by using the file name
     * and casting the object into a weighted graph.
     * @param file - file name
     * @return
     */
    @Override
    public boolean load(String file) {
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            ObjectInputStream objectInputStream
                    = new ObjectInputStream(fileInputStream);
            weighted_graph g = (weighted_graph) objectInputStream.readObject();
            fileInputStream.close();
            objectInputStream.close();
            this.gr = g;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }
}