public class Paper {
    private String title;
    private String year;
    private String venue;

    public Paper(String title, String year, String venue) {
        this.title = title;
        this.year = year;
        this.venue = venue;
    }

    @Override
    public String toString(){
        return "Title: "+title+", Year: "+year+", Venue: "+venue;
    }
}