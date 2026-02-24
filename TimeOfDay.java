/**
 * Enumeration class TimesOfDay 
 *
 * @author Adarsh & Jushan
 * @version 18.02.2026 13:16
 */
public enum TimeOfDay {

    MIDNIGHT(0, "00:00"),
    DAWN(6, "06:00"),
    MIDDAY(12, "12:00"),
    DUSK(18, "18:00");

    private final int hour;
    private final String formattedTime;

    TimeOfDay(int hour, String formattedTime) {
        this.hour = hour;
        this.formattedTime = formattedTime;
    }

    public int getHour() {
        return hour;
    }

    public String getFormattedTime() {
        return formattedTime;
    }
    
    public TimeOfDay next() {
        TimeOfDay[] times = values();
        int nextPos = ((this.ordinal() + 1) % times.length);
        return times[nextPos];
    }
}