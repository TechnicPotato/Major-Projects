package timetableapp;

// Import needed modules
import java.net.URL;
import java.util.Scanner;
import timetableapp.Event;

public class Converter {
    public static void main(String[] args){
        Converter test = new Converter();
        // <--- I/O --->
        String output = test.RetrieveTimetable("http://www.timetable.usyd.edu.au/personaltimetable/timetable/calendar/490481932/xp92hv2doHgqJh9LEudtX9sa8uNUcZBUuwtE72XYzp9/timetable.ics");
        String[] bar = output.split("\n");
        // String teststring = bar[30]+"\n"+bar[31]+"\n"+bar[32]+"\n"+bar[33]+"\n"+bar[34]+"\n"+bar[35]+"\n"+bar[36];
        // Event foo = test.GenerateEvent(teststring);
        // System.out.println(foo.name);
        // System.out.println(foo.start);
        // System.out.println(foo.end);
        // System.out.println(foo.uid);
        // System.out.println(foo.location);
    }
    // TODO: Add to seperate methods file
    public String RetrieveTimetable(String url) {
        // Attempts to go to a website and retrieve the data there. Returns as a string form, assumes UTF-8 formatting
        try {
            URL timetableurl = new URL(url);
            Scanner s = new Scanner(timetableurl.openStream(), "UTF-8");
            StringBuilder outputstr = new StringBuilder();
            while (s.hasNextLine()){
                outputstr.append(s.nextLine() + "\n");
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

    private Event GenerateEvent(String eventtext) {
        // Remove arbitrary start and end
        // TODO: Possibly deal with this before calling the function
        eventtext = eventtext.replace("BEGIN:VEVENT\n", "").replace("END:VEVENT\n", "");
        String[] fields = eventtext.split("\n");
        for(String i: fields){
            System.out.println(i);
        }
        Event newevent = new Event(fields[0], fields[1], fields[2], fields[3], fields[4]);
        return newevent;        
    }
}
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


