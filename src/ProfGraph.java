import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import structures.Graph;

public class ProfGraph {
    public static void getGraph(Scanner scanner, HashMap<String,Prof> map, Graph graph) throws IOException{
        ArrayList<String> co;
        ArrayList<String> profNames=(ArrayList<String>) map.keySet();

        for(String author1:profNames){
            co=map.get(author1).getCoauthors();
            for(String author2:co){
                int a1=profNames.indexOf(author1);
                int a2=profNames.indexOf(author2);
                graph.addEdge(a1,a2);
            }
        }
    }
}
