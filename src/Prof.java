import java.util.*;

/**
 * Prof represents one professor in our dataset.
 *
 * Each Prof object stores the professor's basic information, research
 * interests, papers, and coauthors. Since the CSV has one row per paper,
 * the same professor can appear multiple times. ProfList handles grouping
 * those rows together, and this class stores the combined information.
 */
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

    /**
     * Creates Prof object
     *
     * This constructor only reads the professor's basic information and
     * academic interests. Papers are added separately by addPaper(), because
     * a professor can have many paper rows in the CSV.
     *
     * @param line one row from the professor/paper CSV file
     */
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


    /**
     * Adds paper from CSV file to this professor.
     *
     * Since each CSV row represents one paper, ProfList calls this method
     * every time it sees another row for the same professor.
     *
     * @param line one row from the professor/paper CSV file
     */
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

     /**
     * Compares professors by number of papers.
     */
    public int compareTo(Prof other) {
        return this.paperNum-other.getPaperNum();
    }

    @Override
    public String toString() {
        return name + " (" + dept + ") - " + papers.size() + " papers";
    }

    /**
     * Prints out basic info, interests, and all known papers.
     */
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

    // Testing
    // public static void main(String[] args) {
    //     // Creates new Prof object and logs the paper included in the line
    //     Prof newProf = new Prof("Jingyi Li,\"Assistant Professor of Computer Science, Pomona College\",Computer Science,@pomona.edu,http://jingyi.me/,human-computer interaction,Trusting virtual agents: The effect of personality,2019,\"ACM Transactions on Interactive Intelligent Systems (TiiS) 9 (2-3), 1-36, 2019\"");

    //     // Adds new paper to Prof object's list of papers
    //     newProf.addPaper("Jingyi Li,\"Assistant Professor of Computer Science, Pomona College\",Computer Science,@pomona.edu,http://jingyi.me/,human-computer interaction,Makers' marks: Physical markup for designing and fabricating functional objects,2015,\"Proceedings of the 28th Annual ACM Symposium on User Interface Software …, 2015\"");

    //     // Returns "Jingyi Li"
    //     System.out.println(newProf.toStringDetails());

    //     // Returns "Computer Science"
    //     System.out.println(newProf.getPaperNum());

    //     // returns
    //     System.out.println(newProf.getCoauthors());
    // }
}
