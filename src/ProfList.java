import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class ProfList{
    private final HashMap<String,Prof> map;

    public ProfList(String data) throws IOException{
        map=new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(data))) {
            String line=reader.readLine();
            
            int i=0;
            while(line!=null & i++<=3000){
                String key=line.split(",")[0];
                if (!map.containsKey(key)) {
                    Prof p=new Prof(line);
                    p.addPaper(line);
                    map.put(key, p);
                } else {
                    map.get(key).addPaper(line);
                }
                line=reader.readLine();
            }
        }
    }

    public HashMap<String,Prof> getHashMap(){
        return map;
    }

    @Override
    public String toString(){
        String out="";
        for(Prof p:map.values()){
            out+=p.toString()+"\n";
        }
        return out;
    }

    public static void main(String[] args) throws IOException{
        ProfList lst=new ProfList("data/all_output.csv");
        System.out.print(lst);
    }
}
