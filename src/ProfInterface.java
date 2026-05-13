import java.util.*;

/**
 * ProfInterface - the interace that Prof has to implement
 * Extends Comparable so we cna sort profs against each other
 */
public interface ProfInterface{
    String getName();

    String getDepartment();
    
    String getID();

    List<String> getAcademicInterests();
}