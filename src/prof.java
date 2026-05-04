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

    public Prof(String line) {
        String[] parts=line.split(",");
        this.name=parts[0];
        this.affiliation=parts[1];
        this.dept=parts[2];
        this.email=parts[3];
        this.website=parts[4];

        this.academicInterests=new ArrayList<>();
        if (!parts[5].isEmpty()) {
            String[] interests=parts[5].split(";");
            for (String s: interests) {
                academicInterests.add(s.trim());
            }
        }

        this.papers=new ArrayList<>();
    }


    public void addPaper(String line){
        String[] parts=line.split(",");
        String title=""; String year=""; String venue="";
        if(parts.length>6) title=parts[6];
        if(parts.length>7) title=parts[7];
        if(parts.length>8) title=parts[8];

        papers.add(new Paper(title, year, venue));
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
        return name + " (" + dept + ") - " + papers.size() + " papers";
    }
}