import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class event {
    String name;
    ZonedDateTime start;
    ZonedDateTime end;
    String uid;
    String location;
    public event(String name, String dtstart, String dtend, String uid, String location) {
        this.name = name;
        this.start = convertdt(dtstart);
        this.end = convertdt(dtend);
        this.uid = uid;
        this.location = location;
    }

    /**
     * Returns a ZonedDateTime object from an inputted date/time string from an ICAL file.
     * Assumes string is in basic ical format:
     * <p>
     * <b> DTSTART/END;TZID=[];VALUE=DATE-TIME:[]</b>
     * <p>
     * Function does so by converting and splitting the string to a managable format
     * ,then calling LocalDateTime parse method.
     * 
     * @param dtstring - A date/time string in standard ICAL format to be formatted as a time object
     * @return  A formatted date/time string as a ZonedDateTime
     */
    private static ZonedDateTime convertdt(String dtstring) {
        String[] dtevent = dtstring.split(";");
        // Perform conversion of local time to something easier formatted.
        String localtime = dtevent[2].replace("VALUE=DATE-TIME:", "").replace("T", " ");
        String location = dtevent[1].replace("TZID=", "");
        // Create a local date time
        LocalDateTime ldt = LocalDateTime.parse(localtime, DateTimeFormatter.ofPattern("yyyyMMdd HHmmss"));
        // Adjust date time to appropriate time zone
        ZonedDateTime currentdt = ldt.atZone(ZoneId.of(location));
        return currentdt;
    }
}