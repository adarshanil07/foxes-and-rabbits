/**
 * Descripton:
 *
 * @author Adarsh & Jushan
 * @version 16.02.2026 19:05
 */

import java.util.Iterator;

public class Clock
{
    private TimeOfDay currentTime;
    private int stepCount;
    
    public Clock() {
        currentTime = TimeOfDay.MIDNIGHT;
        stepCount = 0;
    }
    
    public void reset() {
        currentTime = TimeOfDay.MIDNIGHT;
        stepCount = 0;
    }
    
    public String getFormattedTime() {
        return currentTime.getFormattedTime();
        }
        
    public int getStepCount() {
        return stepCount;
    }
        
    public void tick() { 
        stepCount++;
        currentTime = currentTime.next();
        }
    }
    