import java.util.ArrayList;
import java.util.HashMap;

public class DeptFinder {
    public static ArrayList<Prof> getDept(String dept,HashMap<String,Prof> map){
        ArrayList<Prof> out=new ArrayList<Prof>();
        for(Prof p:map.values()){
            if(p.getDepartment().equals(dept)) out.add(p);
        }
        return out;
    }
}
