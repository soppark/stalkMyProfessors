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
        ArrayList<int[]> edges = new ArrayList<int[]>();

        for(String author1:profNames){
            co=map.get(author1).getCoauthors();
            //System.out.println("main author: "+author1);
            for (String author2 : co) {
                if (map.containsKey(author2)) {
                    int a1 = profNames.indexOf(author1);
                    int a2 = profNames.indexOf(author2);
                    if (a1 < a2) {
                        graph.addEdge(a1, a2);
                        edges.add(new int[] {a1, a2});
                    }
                }
            }
            //System.out.println("-----------------");
        }
        System.out.println(graph.toStringDetail(profNames));
        GraphVisualizer.showGraph(profNames, edges);
    }

    /**
    * Option 4:
    * Builds a smaller coauthor graph for one professor.
    *
    * The graph includes:
    * - the professor the user typed in
    * - that professor's coauthors
    * - edges between any people in this smaller group if they are coauthors
    *
    * @param scanner reads the professor name from the user
    * @param map the HashMap of all professors
    * @throws IOException if output has an issue
    */
    public static void getOneProfGraph(Scanner scanner, HashMap<String, Prof> map) throws IOException{
        System.out.print("Enter the professor name for the graph: ");
        String profName = scanner.nextLine();

        // Make sure the professor exists before building the graph.
        if (!map.containsKey(profName)){
            System.out.println("No prof with this name");
            return;
        }

        ArrayList<String> profNames = new ArrayList<>();
        profNames.add(profName);

        // Add the professor's coauthors to the smaller graph.
        for (String coauthor : map.get(profName).getCoauthors()){
            if (map.containsKey(coauthor) && !profNames.contains(coauthor)){
                profNames.add(coauthor);
            }
        }

       Graph graph = new Graph(profNames.size());
       ArrayList<int[]> edges = new ArrayList<int[]>();

        // Add edges between people in this smaller group.
        for (String author1 : profNames) {
            ArrayList<String> co = map.get(author1).getCoauthors();
            for (String author2 : co) {
                if (profNames.contains(author2)) {
                    int a1 = profNames.indexOf(author1);
                    int a2 = profNames.indexOf(author2);
                    if (a1 < a2) {
                        graph.addEdge(a1, a2);
                        edges.add(new int[] {a1, a2});
                    }
                }
            }
        }
        System.out.println(graph.toStringDetail(profNames));
        GraphVisualizer.showGraph(profNames, edges);
    }
}