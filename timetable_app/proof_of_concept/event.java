public class event {
    String name;
    String dtstart;
    String dtend;
    String uid;
    String location;
    public event(String name, String dtstart, String dtend, String uid, String location) {
        this.name = name;
        this.dtstart = dtstart;
        this.dtend = dtend;
        this.uid = uid;
        this.location = location;
    }

    private void convertdt(String dtstring) {
        // Generates a time event from a DTString from an ical file
        String[] dtevent = dtstring.split(";");
        if(dtevent[1].contains("Sydney")){
            // Add some value to match the correct date and time.
        }

    }
}