import java.util.*;

/**
 * ProfInterface - the interface that Prof has to implement.
 *
 * Basically a contract that says "anything that calls itself a Prof
 * needs to have these methods". Extends Comparable so we can sort
 * profs against each other (Prof.compareTo uses paper count).
 */
public interface ProfInterface extends Comparable<ProfInterface> {
    /** @return the prof's full name */
    String getName();

    /** @return the prof's department */
    String getDepartment();

    /** @return the prof's ID from the dataset */
    String getID();

    /** @return list of the prof's research interests */
    List<String> getAcademicInterests();
}
