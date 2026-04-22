import java.util.*;

public interface ProfInterest extends Comparable<ProfInterest> {

    String getName();
    String getDepartment();

    List<String> getAcademicInterests();
    List<String> getCollaborators();

    List<String> getPastTitles();

    @Override
    int compareTo(ProfInterest other);
}