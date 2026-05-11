import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import structures.*;


/**
     * UserInterface class - the main file that runs the program
     * It shows a menu and lets the user pick what they want to do:
     * - see a list of all professors
     * - look up one specif prof by name 
     * - see all profs in a department
     * - see a prof's connections, particularly coauthors
     * 
     */
public class UserInterface {
    /**
     * Option 0 in the menu
     * Prints out every profs in the map
     * 
     * @param scanner 
     * @param map the HashMap of all profs
     * @throws IOException 
     */
    public static void getMenu(Scanner scanner, HashMap<String,Prof> map) throws IOException{
        //Loop through everyone and print them 
        for(Prof p:map.values()){
            System.out.println(p.toString());
        }
        //Name has to be exact match with data base, not ideal
    }

    /**
     * Option 1:
     * Asks the user for a prof's name and prints out all details of them
     * 
     * @param scanner read what the user type in 
     * @param map the HashMap of all profs
     * @throws IOException if reading/printing breaks
     */
    public static void getProf(Scanner scanner, HashMap<String,Prof> map) throws IOException{
        System.out.print("Enter the name of the Professor you'd like to learn more about: ");
        String profName=scanner.nextLine();

        //check if the name is in the map
        if(map.containsKey(profName)){
            System.out.println(map.get(profName).toStringDetails());
        }else{
            System.out.println("No prof with this name.");
        }
        //Name has to be exact match with data base, not ideal
    }

    /**
     * 
     * Option 1:
     * Asks the user for a department and prints out all profs in it
     * sorted from most paper to least 
     * 
     * The way the sorting works: put each prof into a BST using their 
     * paper count as the key, then keep searching the max and deleting it
     * until the tree is empty, which gives the order from most to least.
     * @param scanner read the department name 
     * @param map the Hashmap of All profs
     * @throws IOException if input/output breaks
     */
    public static void getDept(Scanner scanner, HashMap<String,Prof> map) throws IOException{
        System.out.print("Enter the department you'd like to learn more about: ");
        String deptName=scanner.nextLine();

        //grab all profs in the department
        ArrayList<Prof> lst=DeptFinder.getDept(deptName, map); //gives all prof in the dept
        if(lst.isEmpty()) System.out.println("No Dept Find");

        //put profs in a BST by paper count to sort 
        BST<Integer,Prof> bst=new BST<Integer,Prof>();
        for(Prof p:lst){
            bst.put(p.getPaperNum(),p);
        }

        //keep printing the max and deleting it
        while(!bst.isEmpty()){
            System.out.println(bst.get(bst.max()).toString());
            bst.deleteMax();
        }
    }

    public static void getGraph(Scanner scanner, HashMap<String,Prof> map) throws IOException{
        System.out.print("Enter the prof you'd like to see connections of: ");
        String profName=scanner.nextLine();
        ArrayList<String> co;

        //check if the name is in the map
        if(map.containsKey(profName)){
            co=map.get(profName).getCoauthors();
            
        }else{
            System.out.println("No prof with this name.");
        }
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
