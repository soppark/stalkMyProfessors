import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class RunThis {
    
    public static void main(String[] args) throws IOException {
        String data="data/all_output.csv";
        BufferedReader reader=new BufferedReader(new FileReader(data));
        String line=reader.readLine();

        HashMap<String,Prof> map=new HashMap<>();
        while(line!=null){
            String key=line.split(",")[0];
            if (!map.containsKey(key)) {
                Prof p=new Prof(line);
                p.addPaper(line);
                map.put(key, p);
            } else {
                map.get(key).addPaper(line);
            }
        }
        reader.close();
        System.out.println(map.values());

    
    }
}
