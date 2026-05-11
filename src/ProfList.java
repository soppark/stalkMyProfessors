import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;


/**
 * ProList = reads the CSV file and builds a HashMap of all the professors
 * 
 * Each row in the CSV is one paper. If a prof has multiple papers, 
 * they show up on multiple rows and group these rows by prof name.
 * 
 * After construction, call gatHashMap() to get the name -> Prof map
 */
public class ProfList{
    // map fro prof name 
    private final HashMap<String,Prof> map;

    /**
     * Constructor - oepns he CSV fiel and reads it line by line 
     * 
     * @param data path to the CSV file
     * @throws IOException if the file can't be opened/read
     */
    public ProfList(String data) throws IOException{
        map=new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(data))) {
            String line=reader.readLine();
            
            int i=0;
            while(line!=null & i++<=3000){
                // first colum if the pro'f name - use it as the key
                String key=line.split(",")[0];
                if (!map.containsKey(key)) {
                    // first time seeing the prof: make a new Prof and add the paper
                    Prof p=new Prof(line);
                    p.addPaper(line);
                    map.put(key, p);
                } else {
                    // if already have, add this row as another paper
                    map.get(key).addPaper(line);
                }
                line=reader.readLine();
            }
        }
    }

    /**
     * 
     * @return the HashMap of all professors by their name
     */
    public HashMap<String,Prof> getHashMap(){
        return map;
    }

    /**
     * builds a big string of every prof's toString()
     */
    @Override
    public String toString(){
        String out="";
        for(Prof p:map.values()){
            out+=p.toString()+"\n";
        }
        return out;
    }

    /**
     * Test main - prints out every professors
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException{
        ProfList lst=new ProfList("data/all_output.csv");
        System.out.print(lst);
    }
}
