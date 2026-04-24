import java.io.*;
import java.util.*;

public class Prof implements ProfInterest {
    private String id;
    private String name;
    private String affiliation;
    private String dept;
    private String email;
    private String website;
    ArrayList<String> academicInterests;
    ArrayList<Paper> papers;

    public Prof(File data) {
        academicInterests = new ArrayList<>();
        papers = new ArrayList<>();
        id = "";
        name = "";
        affiliation = "";
        dept = "";
        email = "";
        website = "";

        try (BufferedReader br = new BufferedReader(new FileReader(data))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("ID=")) {
                    id = line.substring(3);
                } else if (line.startsWith("NAME=")) {
                    name = line.substring(5);
                } else if (line.startsWith("AFFILIATION=")) {
                    affiliation = line.substring(12);
                } else if (line.startsWith("DEPARTMENTS=")) {
                    dept = line.substring(12);
                } else if (line.startsWith("EMAIL=")) {
                    email = line.substring(6);
                } else if (line.startsWith("WEBSITE=")) {
                    website = line.substring(8);
                } else if (line.startsWith("INTERESTS=")) {
                    String raw = line.substring(10);
                    if (!raw.isEmpty()) {
                        String[] parts = raw.split(";");
                        for (String part : parts) {
                            String trimmed = part.trim();
                            if (!trimmed.isEmpty()) {
                                academicInterests.add(trimmed);
                            }
                        }
                    }
                } else if (line.startsWith("PAPER=")) {
                    String raw = line.substring(6);
                    String[] parts = raw.split("\\|", 3);
                    String title = parts.length > 0 ? parts[0] : "";
                    int year = 0;
                    if (parts.length > 1) {
                        try {
                            year = Integer.parseInt(parts[1].trim());
                        } catch (NumberFormatException e) {
                            year = 0;
                        }
                    }
                    String source = parts.length > 2 ? parts[2] : "";
                    papers.add(new Paper(title, year, source));
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + data.getName() + " — " + e.getMessage());
        }
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDepartment() {
        return dept;
    }

    @Override
    public String getID() {
        return id;
    }

    public String getAffiliation() {
        return affiliation;
    }

    public String getEmail() {
        return email;
    }

    public String getWebsite() {
        return website;
    }

    @Override
    public List<String> getAcademicInterests() {
        return academicInterests;
    }

    public List<Paper> getPapers() {
        return papers;
    }

    @Override
    public int compareTo(ProfInterest other) {
        return this.name.compareToIgnoreCase(other.getName());
    }

    @Override
    public String toString() {
        return name + " (" + dept + ") — " + papers.size() + " papers";
    }
}