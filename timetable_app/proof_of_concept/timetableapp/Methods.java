package timetableapp;

// Import needed modules
import java.net.URL;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;
import timetableapp.Event;

public class Methods {
    public static void main(String[] args){
        Methods test = new Methods();
        String output;
        // <--- I/O --->
        // Perform basic checking
        try {
            output = test.RetrieveTimetable("http://www.timetable.usyd.edu.au/personaltimetable/timetable/calendar/490481932/xp92hv2doHgqJh9LEudtX9sa8uNUcZBUuwtE72XYzp9/timetable.ics");
            ArrayList<Event> Eventlist = test.GenerateTimeline(output);
            // for (Event i: Eventlist) {
            //     System.out.println(i.name);
            // }
            System.out.println(test.ObtainCurrentEvent(Eventlist));
            Event nextEvent = test.ObtainClosestEvent(Eventlist);
            System.out.println(nextEvent.getName());
            System.out.println(test.ObtainNextEvent(Eventlist, nextEvent).getName());
            System.out.println(test.ObtainPastEvent(Eventlist, nextEvent));
        } 
        catch (Exception e) {
            e.printStackTrace();
            return;
        }
        // String[] bar = output.split("\n");
        // String teststring = bar[30]+"\n"+bar[31]+"\n"+bar[32]+"\n"+bar[33]+"\n"+bar[34]+"\n"+bar[35]+"\n"+bar[36];
        // try {
        //     Event foo = test.GenerateEvent(teststring);
        //     System.out.println(foo.name);
        //     System.out.println(foo.start);
        //     System.out.println(foo.end);
        //     System.out.println(foo.uid);
        //     System.out.println(foo.location);
        // }
        // catch(Exception e){
        //     e.printStackTrace();
        // }
    }

    // TODO: Add to seperate methods file
    /**
     * Retrieves an ical formatted timetable from a url and returns the string formatted variant.
     * 
     * @param url - A url (in non-secured http form) in which an ical formatted file is to be retrieved.
     * @return A formatted string of the contents of the ical file.
     * @throws Exception if the file is not valid.
     * @see Converter#CheckCalendarValidity(String)
     */
    public String RetrieveTimetable(String url) throws Exception{
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
            String output = outputstr.toString();
            // Validation checking
            if (CheckCalendarValidity(output)) {
                return output;
            }
            else {
                throw new Exception("Calendar not in specified ical format!");
            }
        }
        catch (Exception e) {
            // Calling a failure
            e.printStackTrace();
            return null;
        }
    }

    //TODO: Utilise the inbuilt refresh in an ical format and use that to refresh the calender.

    /**
     * Returns a boolean to whether the ical calender retrieved is complete.
     * Performs a check if the calender starts with and ends with VCALENDAR
     * 
     * @param timetablestring - An ical file that has been converted to a string format.
     * @return A boolean if the timetablestring matches the preferred format.
     * @see Converter#RetrieveTimetable(String)
     */
    private static boolean CheckCalendarValidity(String timetablestring) {
        if (timetablestring.startsWith("BEGIN:VCALENDAR") && timetablestring.endsWith("END:VCALENDAR\n")) {
            return true;
        }
        return false;
    }
    
    /**
     * From an ical formatted string, generate an ArrayList of Events for general use.
     * @param timetablestring - String of an ICAL formatted file
     * @return Timeline of the Events from the ical string, formatted in an ArrayList
     * @see Converter#RetrieveTimetable(String)
     * @see Converter#GenerateEvent(String)
     * @see Event
     */
    private ArrayList<Event> GenerateTimeline(String timetablestring) {
        Scanner s = new Scanner(timetablestring);
        // Generate strings to avoid regenerating them later.
        String eventgenerate = "";
        String currentline = new String();
        boolean generateevent = false;
        ArrayList<Event> timeline = new ArrayList<Event>();
        while (s.hasNextLine()) {
            currentline = s.nextLine();
            // Check for a new event
            if (currentline.equals("BEGIN:VEVENT")) {
                generateevent = true;
            }
            // If an event has been found, copy into a buffer for event generation
            if (generateevent) {
                eventgenerate = eventgenerate.concat(currentline + "\n");
            }
            // After, perform a check if END has been found, and if so, generate an event
            if (currentline.equals("END:VEVENT")) {
                generateevent = false;
                // Use generateevent to create Event from String
                try {
                    timeline.add(GenerateEvent(eventgenerate));
                }
                catch (Exception e) {
                    s.close();
                    e.printStackTrace();
                    return null;
                }
                // Clean the string for a new event
                eventgenerate = "";
            }
        }
        // Prevent resource leak
        s.close();
        // Sort the timeline via the time
        Collections.sort(timeline, new CompareTime());
        return timeline;
    }

    /**
     * From an ICAL formatted string, generate an Event object for use in a timeline.
     * 
     * @param eventtext - ICAL formatted string to generate an event with. Must start with <i>BEGIN:VEVENT</i> and end with <i>END:VEVENT</i>
     * @return Object of Event class
     * @throws Exception - If Event lacks the necessary fields to be generated. 
     */
    private Event GenerateEvent(String eventtext) throws Exception{
        // Remove arbitrary start and end
        // TODO: Possibly deal with this before calling the function
        eventtext = eventtext.replace("BEGIN:VEVENT\n", "").replace("END:VEVENT\n", "");
        
        // Handle misordered fields
        String[] fields = eventtext.split("\n");
        String name = null;
        String location = null;
        String dtstart = null;
        String dtend = null;
        String uid = null;
        // Search inside the fields and look for each field
        for (String i: fields) {
            if (i.startsWith("SUMMARY:")) {
                name = i.replace("SUMMARY:", "");
            }
            else if (i.startsWith("LOCATION:")) {
                location = i.replace("LOCATION:", "");
            }
            else if (i.startsWith("DTSTART")) {
                dtstart = i;
            }
            else if (i.startsWith("DTEND")) {
                dtend = i;
            }
            else if (i.startsWith("UID:")) {
                uid = i.replace("UID:", "");
            }
        }

        // Error handling in case one of the following if not created:
        if ((name == null) || (dtstart == null) || (dtend == null) || (uid == null) || (location == null)) {
            throw new Exception("Failed to find valid fields for generating an event");
        }
        Event newevent = new Event(name, dtstart, dtend, uid, location);
        return newevent;        
    }

    /**
     * Performs a check based on current system time if an event is taking place. Returns appropriate Event object.
     * @param Eventlist - Sorted (time-based) list of Events to be parsed
     * @return - Event object if currently during the time of an Event in the list, otherwise null.
     */
    private Event ObtainCurrentEvent(ArrayList<Event> Eventlist){
        Event output = null;
        ZonedDateTime CurrentTime = ZonedDateTime.now();
        // Look through the list of events and see if an event is currently on
        // Since sorted, for performance optimisation don't look at events past 1 in the future.
        for (Event e: Eventlist) {
            if (CurrentTime.isBefore(e.getStart())) {
                break;
            }
            else if (CurrentTime.isBefore(e.getEnd()) && CurrentTime.isAfter(e.getStart())) {
                output = e;
            }
        }
        return output;
    }

    /**
     * Obtains the next event object past the current one.
     * @param Eventlist - Sorted (time-based list of Events to be parsed)
     * @param currentEvent - The event the system is currently looking at,
     * @return - Event object 1 past the currentEvent, otherwise null if none exists
     */
    private Event ObtainNextEvent(ArrayList<Event> Eventlist, Event currentEvent) {
        // Look for the next indexed number
        try {
            return Eventlist.get((Eventlist.indexOf(currentEvent) + 1));
        }
        catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    /**
     * Obtains the next event object 1 previous to the current one.
     * @param Eventlist - Sorted (time-based list of Events to be parsed)
     * @param currentEvent - The event the system is currently looking at,
     * @return - Event object 1 previous to the currentEvent, otherwise null if none exists
     */
    private Event ObtainPastEvent(ArrayList<Event> Eventlist, Event currentEvent) {
        // Look for the previous indexed number
        try {
            return Eventlist.get((Eventlist.indexOf(currentEvent) - 1));
        }
        catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    /**
     * Performs a search based on current time on what the next event will be.
     * @param Eventlist - Sorted (time-based) list of Events to be parsed
     * @return - Event object of the next event based on current time.
     */
    private Event ObtainClosestEvent(ArrayList<Event> Eventlist) {
        ZonedDateTime CurrentTime = ZonedDateTime.now();
        // Performs a quick search for the next event.
        for (Event e: Eventlist) {
            if (CurrentTime.isBefore(e.getStart())) {
                return e;
            }
        }
        return null;
    }
}

class CompareTime implements Comparator<Event> {
    public int compare(Event a, Event b) {
        return a.getStart().compareTo(b.getEnd());
    }
    
}
 
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


