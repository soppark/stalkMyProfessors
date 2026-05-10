import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import structures.*;

public class UserInterface {
    public static void getMenu(Scanner scanner, HashMap<String,Prof> map) throws IOException{
        for(Prof p:map.values()){
            System.out.println(p.toString());
        }
        //Name has to be exact match with data base, not ideal
    }

    public static void getProf(Scanner scanner, HashMap<String,Prof> map) throws IOException{
        System.out.print("Enter the name of the Professor you'd like to learn more about: ");
        String profName=scanner.nextLine();
        if(map.containsKey(profName)){
            System.out.println(map.get(profName).toStringDetails());
        }else{
            System.out.println("No prof with this name.");
        }
        //Name has to be exact match with data base, not ideal
    }

    public static void getDept(Scanner scanner, HashMap<String,Prof> map) throws IOException{
        System.out.print("Enter the department you'd like to learn more about: ");
        String deptName=scanner.nextLine();
        ArrayList<Prof> lst=DeptFinder.getDept(deptName, map); //gives all prof in the dept
        BST<Integer,Prof> bst=new BST<Integer,Prof>();
        for(Prof p:lst){
            bst.put(p.getPaperNum(),p);
        }
        while(!bst.isEmpty()){
            System.out.println(bst.get(bst.max()).toString());
            bst.deleteMax();
        }
        //HERE need comparison method with BST?
    }

    public static void getGraph(Scanner scanner, HashMap<String,Prof> map) throws IOException{
        System.out.print("Enter the prof you'd like to see connections of: ");
        String profName=scanner.nextLine();
        
        //HERE need graph
    }

    public static void main(String[] args) throws IOException {
        try (Scanner scanner = new Scanner(System.in)) {
            String data="data/all_output.csv";
            HashMap<String,Prof> map=new ProfList(data).getHashMap();

            while (true) { 
                System.out.print("--------------------------\nEnter 1 to search Prof, 2 for Dept, 3 for graph, 0 for prof list: ");
                String c = scanner.nextLine();
                if(c.equals("0")) getMenu(scanner, map);
                else if(c.equals("1")) getProf(scanner, map);
                else if(c.equals("2")) getDept(scanner, map);
                else if(c.equals("3")) getGraph(scanner, map);
                else System.out.println("Wrong input");
                
            }

        }

    }
}
