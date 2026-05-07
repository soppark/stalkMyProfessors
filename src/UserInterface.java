import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class UserInterface {
    public static void getProf(Scanner scanner, HashMap<String,Prof> map) throws IOException{
        System.out.print("Enter the name of the Professor you'd like to learn more about: ");
        String profName=scanner.nextLine();
        if(map.containsKey(profName)){
            System.out.println(map.get(profName).toStringDetails());
        }else{
            System.out.println("No prof with this name.");
        }
    }

    public static void getDept(Scanner scanner, HashMap<String,Prof> map) throws IOException{
        System.out.print("Enter the department you'd like to learn more about: ");
        String deptName=scanner.nextLine();
        ArrayList<Prof> lst=DeptFinder.getDept(deptName, map);
        for(Prof p:lst){
            System.out.println(p.toStringDetails());
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
                System.out.print("Enter 1 to search Prof, 2 for Dept, 3 for graph");
                String c = scanner.nextLine();
                if(c.equals("1")) getProf(scanner, map);
                if(c.equals("2")) getDept(scanner, map);
                if(c.equals("3")) getGraph(scanner, map);
                
                //Name has to be exact match with data base, not ideal
            }

        }

    }
}
