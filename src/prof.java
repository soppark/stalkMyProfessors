import java.util.*;

public class Prof implements ProfInterface{
    private String id;
    private final String name;
    private final String affiliation;
    private final String dept;
    private final String email;
    private final String website;
    private int paperNum;
    ArrayList<String> academicInterests;
    ArrayList<Paper> papers;
    ArrayList<String> coauthors;

    public Prof(String line) {
        String[] parts=line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
        // for(String s:parts){
        //     System.out.println(s);
        // }
        // System.out.println("------------");
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
        this.paperNum=0;
        this.coauthors=new ArrayList<>();
    }


    public void addPaper(String line){
        String[] parts=line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
        String title=""; String year=""; String venue="";
        if(parts.length>6) title=parts[6];
        if(parts.length>7) year=parts[7];
        if(parts.length>8) venue=parts[8];
        
        papers.add(new Paper(title, year, venue));
        paperNum++;

        int i=9;
        while(i<parts.length){
            if(!coauthors.contains(parts[i]) && !parts[i].equals("")) coauthors.add(parts[i]);
            i++;
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

    public int getPaperNum(){
        return paperNum;
    }
    
    public ArrayList<String> getCoauthors(){
        return coauthors;
    }

    public int compareTo(Prof other) {
        return this.paperNum-other.getPaperNum();
    }

    @Override
    public String toString() {
        return name + " (" + dept + ") - " + papers.size() + " papers";
    }

    public String toStringDetails(){
        String out="";
        out+="Name: "+name+"\n";
        out+="Affiliation: "+affiliation+"\n";
        out+="Department: "+dept+"\n";
        out+="Email: "+email+"\n";
        out+="Website: "+website+"\n";
        out+="Academic Interestst: \n";
        for(String interest:academicInterests){
            out+=interest+"\n";
        }
        out+="---------------\n";
        out+=paperNum+" Past Papers Found: \n";
        for(Paper p:papers){
            out+=p.toString()+"\n";
        }
        return out;
    }
}