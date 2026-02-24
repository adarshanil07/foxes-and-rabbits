import java.util.Random;
/**
 * Write a description of class Weather here.
 *
 * @author Jushan and Adarsh
 * @version v1
 */
public class Weather
{
    private static final Random RANDOM = Randomizer.getRandom();

    // current weather
    private WeatherType currentWeather;
    /**
     * Constructor for objects of class Weather
     */
    public Weather()
    {
        currentWeather = WeatherType.CLEAR;
        
    }

    /**
     * Updating weather with probabilities
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
     * GETTER METHOD WHICH RETURNS CURRENT WEATHER TYPE
     */
    public WeatherType getWeather()
    {
        return currentWeather;
    }
    
    /**
     * When simulation is reset, weather type should set back to clear
     */
    public void reset()
    {
        currentWeather = WeatherType.CLEAR;
    }
    
}