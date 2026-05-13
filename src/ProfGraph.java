import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import structures.*;

/**
 * ProfGraph builds the professor coauthor graph.
 *
 * Each professor is treated as a vertex. If two professors wrote a paper
 * together, we add an edge between them. This lets us represent the academic
 * network as a graph instead of just a list of names.
 */
public class ProfGraph {
    /**
     * Builds the coauthor graph from the professor map.
     *
     * The professor names are stored in an ArrayList so each professor has an
     * index. The graph stores edges using those indexes instead of names.
     *
     * @param scanner reads user input if we later want to ask for a professor name
     * @param map maps professor names to Prof objects
     * @param graph the graph we add coauthor edges to
     * @throws IOException if writing the dot file fails
     */
    public static void getGraph(Scanner scanner, HashMap<String,Prof> map, Graph graph) throws IOException{
        ArrayList<String> co;
        ArrayList<String> profNames=new ArrayList<>();
        profNames.addAll(map.keySet());

        for(String author1:profNames){
            co=map.get(author1).getCoauthors();
            //System.out.println("main author: "+author1);
            for(String author2:co){
                int a1=profNames.indexOf(author1);
                int a2=profNames.indexOf(author2);
                //System.out.println("coauthor: "+author2);
                graph.addEdge(a1,a2);
            }
            //System.out.println("-----------------");
        }
        System.out.println(graph.toStringDetail(profNames));

    }
}
