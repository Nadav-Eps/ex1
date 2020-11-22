#Weighted Graph
At this project i built a weighted graph by implantation three interfaces (one as ineer class) node_info, weighted_graph and weighted_graph_algorithms.  
A weighted graph is a collection of Points(Nodes) that could have an edge between them, every edge has a weight who represents a distance of the line between two nodes.  
I created the graph using an hashmap at the key section i added integers from the nodes keys and in the value the nodes themselfes, i created another hashmap to save every node neighbors and the weight of an edge between a node to his neighbors by creating an hashmap for every node wich saves his neighbors keys and weight of and edge.  
At class WGraph_DS i implemented weighted_graph interface and node_infor(as an inner class).
this class helps us build the graph and "play" with it by adding nodes, removing nodes, connect two nodes with an edge and remove edge.
it also help us to know node neighbors get a collection of the nodes at the graph.
At WGraph_Algo i implemented several functions such as :  
- isConnected : returns true only if we can get from every node to every other node by going on the edges , i used the nodes tags to check it.  
- copy : deep coping a graph by creating a new graph and add all the coppied graph nodes to the new one and the same with his neighbors.  
- shortestPathDist : returns the shortest path between to nodes by changing the tags every time we get from the src node to his neighbors until we get to the dest node.  
- shortestpath : returns a list of nodes wich we need to follow for the shortest path between to ndoes using a similar algorithm we used at shortestPathDist then going from the dest node back to src node by checking the tags and edges.  
- save : saves a graph as a file on the computer .  
- load : loads a graph that saved as a file on the pc adn can use it afterwords.
