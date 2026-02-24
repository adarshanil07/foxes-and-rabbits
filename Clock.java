/**
 * Manages the time throughout the simulation. Incraments by 6-hour "steps", giving a time-aspect to the simulation.
 *
 * @author Adarsh & Jushan
 * @version 16.02.2026 19:05
 */

public class Clock
{
    // The current time in the simulator.
    private TimeOfDay currentTime;
    // The number of incraments passed (6-hour chunks)
    private int stepCount;
    
    
    /** 
     * Constructor for ENUM
     */
    public Clock() {
        currentTime = TimeOfDay.MIDNIGHT; // The simulator starts at MIDNIGHT
        stepCount = 0;                    
    }
    
    /**
     * Resets the clock back to inital stage
     */
    public void reset() {
        currentTime = TimeOfDay.MIDNIGHT; // The simulator goes back to MIDNIGHT
        stepCount = 0;
    }
    
    /**
     * @return - the current time in the simulator
     */
    public TimeOfDay getCurrentTime() {
        return currentTime;
    }
    /**
     * @return - the time in a specifc format -> 00:00, 06:00, 12:00, 18:00
     */
    public String getFormattedTime() {
        return currentTime.getFormattedTime();
        }
        
    /**
     * @return - the number of steps that have passed in this simulation
     */    
    public int getStepCount() {
        return stepCount;
    }
    /**
     * Incraments the counter and changes the time.
     */   
    public void tick() { 
        stepCount++;
        currentTime = currentTime.next(); // Moves to the adjacent time e.g. MIDNIGHT -> DAWN
        }
    }
    