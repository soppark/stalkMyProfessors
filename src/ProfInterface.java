import java.util.*;

public interface ProfInterface extends Comparable<ProfInterface> {
    String getName();
    String getDepartment();
    String getID();
    List<String> getAcademicInterests();

    @Override
    int compareTo(ProfInterface other);
}