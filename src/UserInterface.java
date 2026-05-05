import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

public class UserInterface {
    public static void main(String[] args) throws IOException {
        Scanner scanner=new Scanner(System.in);
        String data="data/all_output.csv";

        System.out.print("Enter the name of the Professor you'd like to learn more about: ");
        String profName=scanner.nextLine();
        
        HashMap<String,Prof> map=new ProfList(data).getHashMap();
        if(map.containsKey(profName)){
            System.out.println(map.get(profName).toStringDetails());
        }else{
            System.out.println("No prof with this name.");
        }
    }
}
