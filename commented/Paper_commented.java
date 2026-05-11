/**
 * Paper class - represents one academic paper.
 * Each prof has a list of these.
 *
 * Just three fields for now: title, year, and venue (where it was published).
 * All final because once we read it in from the CSV it shouldn't change.
 */
public class Paper {
    // basic info about the paper
    private final String title;
    private final String year;
    private final String venue;

    /**
     * Constructor - just sets the three fields.
     * @param title  paper title
     * @param year   year it came out
     * @param venue  conference / journal it was published in
     */
    public Paper(String title, String year, String venue) {
        this.title = title;
        this.year = year;
        this.venue = venue;
    }

    /**
     * toString - prints the paper info on one line.
     * Used by Prof.toStringDetails() when listing all of a prof's papers.
     */
    @Override
    public String toString(){
        return "Title: "+title+", Year: "+year+", Venue: "+venue;
    }
}
