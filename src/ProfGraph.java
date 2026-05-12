import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import structures.*;

public class ProfGraph {
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
