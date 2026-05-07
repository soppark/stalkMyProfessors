import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

public class UserInterface {
    public static void getProf(Scanner scanner,String data) throws IOException{
        System.out.print("Enter the name of the Professor you'd like to learn more about: ");
        String profName=scanner.nextLine();
        
        HashMap<String,Prof> map=new ProfList(data).getHashMap();
        if(map.containsKey(profName)){
            System.out.println(map.get(profName).toStringDetails());
        }else{
            System.out.println("No prof with this name.");
        }
    }

    public static void getDept(Scanner scanner,String data) throws IOException{
        System.out.print("Enter the department you'd like to learn more about: ");
        String deptName=scanner.nextLine();
        
        HashMap<String,Prof> map=new ProfList(data).getHashMap();
        //HERE
    }

    public static void main(String[] args) throws IOException {
        try (Scanner scanner = new Scanner(System.in)) {
            String data="data/all_output.csv";

            while (true) { 
                System.out.print("Enter 1 to search Prof, 2 for Dept, 3 for graph");
                String c = scanner.nextLine();
                if(c.equals("1")) getProf(scanner,data);
                if(c.equals("2")) getDept(scanner);
                
                //Name has to be exact match with data base, not ideal
            }
        }
    }
}
