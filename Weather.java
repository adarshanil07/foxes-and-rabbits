import java.util.Random;
/**
 * Weather manages the current weather state in the simulation
 * The weather changes based on a probability at every 8 steps and is able to
 * influence organism behaviour such as plant growth and animal survival
 *
 * @author Jushan and Adarsh
 * @version v1
 */
public class Weather
{
    private static final Random RANDOM = Randomizer.getRandom();

    // The current weather type
    private WeatherType currentWeather;
    
    /**
     * Constructor for objects of class Weather with initial state of CLEAR
     */
    public Weather()
    {
        currentWeather = WeatherType.CLEAR;
    }

    /**
     * Update the weather when called (every 8 steps)
     * The weather is selected on a random basis using fixed probabilities where 
     * CLEAR is 50%, RAIN is 15%, STORM is 10% and CLOUDY is 25%
     */
    public void updateWeather()
    {
        int rand = RANDOM.nextInt(100);
        
        // 0 - 49: CLEAR (50%)
        // 50 - 64: RAIN (15%)
        // 65 - 74: STORM (10%)
        // 75 - 99: CLOUDY (25%)
        
        if (rand < 50) {
            currentWeather = WeatherType.CLEAR;
        }
        else if (rand < 64) {
            currentWeather = WeatherType.RAIN;
        }
        else if (rand < 74) {
            currentWeather = WeatherType.STORM;
        }
        else {
            currentWeather = WeatherType.CLOUDY;
        }
    }
    
    
    /**
     * Returns the current weather
     * 
     * @return The current WeatherType
     */
    public WeatherType getWeather()
    {
        return currentWeather;
    }
    
    /**
     * Resets the weather type to its default state which is Clear
     * Used when simulation is reset
     */
    public void reset()
    {
        currentWeather = WeatherType.CLEAR;
    }
}