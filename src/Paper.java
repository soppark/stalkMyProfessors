/**
 * Paper class - represents one academic paper.
 * Each prof has a list of these.
 *
 */
public class Paper {
    private final String title;
    private final String year;
    private final String venue;

    /**
     * Constructor 
     * @param title paper title
     * @param year publish year
     * @param venue conference / journal it was published in
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