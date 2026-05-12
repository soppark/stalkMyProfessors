import java.util.*;

/**
 * ProfInterface - the interace that Prof has to implement
 * Extends Comparable so we cna sort profs against each other
 */
public interface ProfInterface{
    /**
     * @return the prof's full name
     */
    String getName();

    /**
     * @return the prof's department
     */
    String getDepartment();
    
    /**
     * @return the prof's ID from the dataset
     */
    String getID();

    /**
     * @return list of the prof's research interests
     */
    List<String> getAcademicInterests();
}