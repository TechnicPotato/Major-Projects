// Import needed modules
import java.net.URL;
import java.util.Scanner;

public class converter {
    public static void main(String[] args){
        converter test = new converter();
        // <--- I/O --->
        String output = test.RetrieveTimetable("http://www.timetable.usyd.edu.au/personaltimetable/timetable/calendar/490481932/xp92hv2doHgqJh9LEudtX9sa8uNUcZBUuwtE72XYzp9/timetable.ics");
        System.out.println(output);
    }
    public String RetrieveTimetable(String url) {
        try {
            URL timetableurl = new URL(url);
            Scanner s = new Scanner(timetableurl.openStream(), "UTF-8");
            StringBuilder outputstr = new StringBuilder();
            while (s.hasNext()){
                outputstr.append(s.next() + "\n");
            }
            // Prevent a memory leak
            s.close();
            return outputstr.toString();
        }
        catch (Exception e) {
            // Calling a failure
            e.printStackTrace();
            return null;
        }
    }
}
    // Go to website and retrieve data
    // <--- Event Parsing -->
    // From retrieved data, sort into events
    // Capability to retrieve events when needed
    // <--- Text storage --->
    // Store data as a file
    // <--- Possibly store a current event --->
    // Sort events such that they are checked against system time
    // Ability to skip to the next event
    // Display time til next event
    // Possibly display next event
    // <--- UI Design --->
    // Basic Swipe controls
    // Design an icon


