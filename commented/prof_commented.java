import java.util.*;

/**
 * Prof class - one professor and all their info.
 *
 * Each Prof is built from a row of the CSV file and then we keep adding
 * Paper objects to them as we find more rows with the same name.
 * Implements ProfInterface so we can compare profs (used in sorting).
 */
public class Prof implements ProfInterface{
    // basic prof info pulled from the CSV
    private String id;
    private final String name;
    private final String affiliation;
    private final String dept;
    private final String email;
    private final String website;

    // how many papers we've added so far
    private int paperNum;

    // list of research interests (split on semicolons in the CSV)
    ArrayList<String> academicInterests;

    // list of all the papers we've collected for this prof
    ArrayList<Paper> papers;

    /**
     * Constructor - takes one CSV line and pulls out the prof's basic info.
     *
     * I'm using a fancy regex to split on commas, but NOT commas inside
     * quotes. That's because some fields (like academic interests) might
     * have commas inside them and we don't want to split on those.
     *
     * @param line  one row from the CSV file
     */
    public Prof(String line) {
        // regex split: comma, but only if there's an even number of quotes ahead
        // -> avoids splitting inside quoted fields
        String[] parts=line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);

        // debug prints from when I was figuring out the parsing - commented out
        // for(String s:parts){
        //     System.out.println(s);
        // }
        // System.out.println("------------");

        // columns 0-4 are the basic info
        this.name=parts[0];
        this.affiliation=parts[1];
        this.dept=parts[2];
        this.email=parts[3];
        this.website=parts[4];

        // column 5 is interests, separated by semicolons
        this.academicInterests=new ArrayList<>();
        if (!parts[5].isEmpty()) {
            String[] interests=parts[5].split(";");
            for (String s: interests) {
                academicInterests.add(s.trim());
            }
        }

        // papers list starts empty - we add to it later with addPaper()
        this.papers=new ArrayList<>();
        this.paperNum=0;
    }

    /**
     * Adds one more paper to this prof from a CSV line.
     * Called from ProfList every time we find another row for this prof.
     *
     * @param line  the CSV row containing the paper info
     */
    public void addPaper(String line){
        // same split as in the constructor
        String[] parts=line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);

        // grab paper info, but check first so we don't get an
        // ArrayIndexOutOfBoundsException if the row is short
        String title=""; String year=""; String venue="";
        if(parts.length>6) title=parts[6];
        if(parts.length>7) year=parts[7];
        if(parts.length>8) venue=parts[8];

        papers.add(new Paper(title, year, venue));
        paperNum++;
    }

    // ---------- getters ----------

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
        // note: id is never actually set anywhere, so this returns null
        // -> TODO either set it in the constructor or remove this
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

    /**
     * Compares two profs by how many papers they have.
     * Lets us sort profs by productivity (more papers = "bigger").
     */
    public int compareTo(Prof other) {
        return this.paperNum-other.getPaperNum();
    }

    /**
     * Short version - just name, dept, and paper count.
     * Used in the menu list and dept search.
     */
    @Override
    public String toString() {
        return name + " (" + dept + ") - " + papers.size() + " papers";
    }

    /**
     * Long version - prints everything, including every paper.
     * Used when the user picks a specific prof to look at.
     */
    public String toStringDetails(){
        String out="";
        out+="Name: "+name+"\n";
        out+="Affiliation: "+affiliation+"\n";
        out+="Department: "+dept+"\n";
        out+="Email: "+email+"\n";
        out+="Website: "+website+"\n";
        out+="Academic Interestst: \n"; // typo: should be "Interests"
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
