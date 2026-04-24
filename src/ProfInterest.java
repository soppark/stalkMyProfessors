import java.util.*;

public interface ProfInterest extends Comparable<ProfInterest> {
    String getName();
    String getDepartment();
    String getID();
    List<String> getAcademicInterests();

    @Override
    int compareTo(ProfInterest other);
}