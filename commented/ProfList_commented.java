import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

/**
 * ProfList - reads the CSV file and builds a HashMap of all the professors.
 *
 * Each row in the CSV is one paper. If a prof has multiple papers, they show
 * up on multiple rows so we have to group rows by prof name.
 *
 * After construction, you can call getHashMap() to get the name -> Prof map.
 */
public class ProfList{
    // map from prof name -> Prof object (with all their papers attached)
    private final HashMap<String,Prof> map;

    /**
     * Constructor - opens the CSV file and reads it line by line.
     *
     * @param data  path to the CSV file (like "data/all_output.csv")
     * @throws IOException if the file can't be opened/read
     */
    public ProfList(String data) throws IOException{
        map=new HashMap<>();

        // try-with-resources -> the reader gets closed automatically
        try (BufferedReader reader = new BufferedReader(new FileReader(data))) {
            String line=reader.readLine();

            // cap at 3000 rows so it doesn't take forever for now
            // (& should probably be && here -> bitwise vs logical AND, oops)
            int i=0;
            while(line!=null & i++<=3000){
                // first column is the prof's name -> use it as the key
                String key=line.split(",")[0];
                if (!map.containsKey(key)) {
                    // first time seeing this prof: make a new Prof and add the paper
                    Prof p=new Prof(line);
                    p.addPaper(line);
                    map.put(key, p);
                } else {
                    // already have them, just add this row as another paper
                    map.get(key).addPaper(line);
                }
                line=reader.readLine();
            }
        }
    }

    /**
     * @return the HashMap of all professors keyed by name
     */
    public HashMap<String,Prof> getHashMap(){
        return map;
    }

    /**
     * Builds a big string of every prof's toString(), one per line.
     * Mostly used for debugging.
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
     * Quick test main - just prints out everyone.
     * Run this to make sure the CSV is being read correctly.
     */
    public static void main(String[] args) throws IOException{
        ProfList lst=new ProfList("data/all_output.csv");
        System.out.print(lst);
    }
}
