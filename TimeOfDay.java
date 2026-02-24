/**
 * Enumeration class TimesOfDay - splits the day into 4 parts - DAWN, DUSK, MIDDAY, MIDNIGHT affecting how animals act depending on their characteristics.
 *
 * @author Adarsh & Jushan
 * @version 18.02.2026 13:16
 */
public enum TimeOfDay {
    
    // TimeOfDay(hour, formattedTime)
    MIDNIGHT(0, "00:00"),
    DAWN(6, "06:00"),
    MIDDAY(12, "12:00"),
    DUSK(18, "18:00");
    
    private final int hour;
    private final String formattedTime;

    /**
     * Constructor for TimeOfDay
     */
    TimeOfDay(int hour, String formattedTime) {
        this.hour = hour;
        this.formattedTime = formattedTime;
    }

    /**
     * @return - the current hour e.g. 6 for dawn
     */
    public int getHour() {
        return hour;
    }

    /**
     * @return - string of formatted time to use in the GUI e.g. 00:00 for midnight
     */
    public String getFormattedTime() {
        return formattedTime;
    }
    
    /**
     * Cycles to next time in the day.
     * @return - the next time of day from the current time.
     */
    public TimeOfDay next() {
        TimeOfDay[] times = values(); // List with each TimeOfDay object
        int nextPos = ((this.ordinal() + 1) % times.length); // Chooses the next value in the list (uses MOD to cycle from 4th back to 1st i.e. DUSK to MIDNIGHT)
        return times[nextPos];
    }
}