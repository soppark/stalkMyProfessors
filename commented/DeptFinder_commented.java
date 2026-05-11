import java.util.ArrayList;
import java.util.HashMap;

/**
 * DeptFinder - small helper class for searching by department.
 *
 * Right now it just has one static method: getDept.
 * Could maybe add more search helpers in here later.
 */
public class DeptFinder {

    /**
     * Goes through every prof in the map and returns a list of the ones
     * whose department contains the search string.
     *
     * I'm using .contains() instead of .equals() so partial matches work
     * (e.g. searching "Computer" would catch "Computer Science").
     *
     * @param dept  the department name (or part of it) to search for
     * @param map   the full name -> Prof map
     * @return      list of profs in that department (empty if none found)
     */
    public static ArrayList<Prof> getDept(String dept, HashMap<String,Prof> map){
        ArrayList<Prof> out=new ArrayList<Prof>();
        for(Prof p:map.values()){
            // partial match -> case sensitive though, watch out
            if(p.getDepartment().contains(dept)) out.add(p);
        }
        return out;
    }
}
