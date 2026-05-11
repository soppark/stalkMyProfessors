import java.util.ArrayList;
import java.util.HashMap;

/**
 * DeptFinder - small helper class for searching by department.
 */
public class DeptFinder {
    /**
     * Goes through every prof in the map and returns a list of the ones
     * whose department contains the search string.
     * 
     * @param dept the department name to search for
     * @param map  the full name -> Prof map
     * @return.    list of profs in that department
     */
    public static ArrayList<Prof> getDept(String dept,HashMap<String,Prof> map){
        ArrayList<Prof> out=new ArrayList<Prof>();
        for(Prof p:map.values()){
            if(p.getDepartment().contains(dept)) out.add(p);
        }
        return out;
    }
}
