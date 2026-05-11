package structures;

import java.util.List;
import java.util.Map;

/**
 * graph - generic graph using an adjacency list (map of node -> list of neighbors).
 *
 * Still a skeleton -> the methods are empty.
 * Plan: use this for the prof collaboration feature in UserInterface.getGraph()
 *       so we can find co-authors / connections between profs.
 *
 * TODO:
 *   - initialize adjMap (currently null)
 *   - actually implement addEdge so it inserts into the adjacency map
 *   - implement display so we can print connections
 *   - maybe add BFS/DFS methods for finding paths between profs
 *
 * @param <T> the type of node (probably will be Prof or String)
 */
public class graph<T>{
    // adjacency list: each node maps to a list of its neighbors
    // (needs to be initialized before we can put anything in it)
    public Map<T, List<T>> adjMap;

    /**
     * Add an edge between two nodes. Not implemented yet.
     */
    public void addEdge(){
        // TODO: take two args (from, to) and add each to the other's neighbor list
    }

    /**
     * Print the graph. Not implemented yet.
     */
    public void display(){
        // TODO: loop through adjMap and print each node and its neighbors
    }
}
