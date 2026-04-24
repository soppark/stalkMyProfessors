import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

public class main {
    public static void main(String[] args) {
        File dataDir = new File("data");
        ArrayList<Prof> professors = new ArrayList<>();

        if (!dataDir.isDirectory()) {
            System.err.println("'data' directory not found. Run api.py first.");
            return;
        }

        File[] files = dataDir.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.getName().endsWith(".txt")) {
                    professors.add(new Prof(file));
                }
            }
        }

        Collections.sort(professors);

        for (Prof p : professors) {
            System.out.println(p);
        }
    }
}