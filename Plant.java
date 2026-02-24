import java.util.Random;
import java.util.List;              // obtain all free adjacent locations and take one ygm cuZ
/**
 * Write a description of class Plant here.
 *
 * @author Jushan and Adarsh
 * @version v1
 */
public abstract class Plant extends Organism
{
    // plants age 
    private int age;            // fixed number of yrs before plant dies?
    // randomiser for deciding whether plant spreads
    private static final Random RANDOM = Randomizer.getRandom();

    // dk whether to put protected abstract methods here or below constructor?
    // max age for each plant
    protected abstract int getMaxAge();
    // probability of plant seeds spreading
    protected abstract double getChanceOfSpread();
    // creation of seedling
    protected abstract Plant newSeedling(Location location);
    // food value of plant
    protected abstract int foodValue();
    
    
    /**
     * Constructor for objects of class Plant
     */
    public Plant(Location location)
    {
        super(location);
        age = 0;
    }

    /**
     * incrementing the age of the plant
     */
    protected void incrementAge()
    {
        age++;
        
        // once they reach the max age, they will die.
        if (age >= getMaxAge()) {
            setDead();
        }
    }
    
    /**
     * Weather affects the max age of plants, this calculates maximum age of plant after
     * the effects of weather
     */
    protected int getWeatherAffectedMaxAge(WeatherType weather)
    {
        int maxAge = getMaxAge();
        
        // lives slightly longer if it rains        
        if (weather == WeatherType.RAIN) {
            maxAge += 2;
        }

        // lives slightly less if it is stormy
        else if (weather == WeatherType.STORM) {
            maxAge -= 2;
        }
        
        // prevents maxAge being 0 or lower since that is not possible logically
        if (maxAge < 1) {
            maxAge = 1;
        }
        
        return maxAge;
    }
    
    /**
     * both plants gonna act the same
     * 
     * gpt says if it was an abstract class in a class above this in the hierarchy thing
     * then we gotta do @override -> gotta check that out
     */
    public void act(Field currentField, Field nextFieldState, TimeOfDay currentTime)
    {
        // after each step, age of plant increments?
        incrementAge();
        
        // if max age is hit, then it is dead and cannot move
        // good programming practice to stop if dead instantly, to improve reliabilty
        
        // MIGHT NOT NEED THIS, DONT WANNA DELETE RN 
        if (!isAlive()) {
            return;           
        }
                
        WeatherType weather = currentField.getWeather();
        int maxAge = getWeatherAffectedMaxAge(weather);
        
        // if the age has reached max age then the plant will die 
        // this is since the max age could be exceeded due to stormy weather reducing it
        if (age >= maxAge) {
            setDead();
            return;
        }
        
        nextFieldState.place(this, getLocation());
        spread(currentField, nextFieldState);
    }
    
    
    /**
     * Plant probability of spreading to neighbouring fields and growing 
     */
    protected void spread(Field currentField, Field nextFieldState)
    {
        // default spread chance
        double spreadChance = getChanceOfSpread();
        // current weather type since weather type affects how plants spread
        WeatherType weather = currentField.getWeather();
        
        // if the weather type is rain, the plants have a higher chance of spreading
        if (weather == WeatherType.RAIN) {
            spreadChance *= 1.5;
        }
        
        // if weather type is stormy, then it will reduce the chance of spread for plants
        else if (weather == WeatherType.STORM) {
            spreadChance *= 0.5;
        }
        
        // check to prevent the probability exceeding 1, in the case of rain
        if (spreadChance > 1.0) {
            // ensures probability <= 1 since otherwise logic broken
            spreadChance = 1.0;        
        }
        
        if (RANDOM.nextDouble() <= spreadChance) {
            List<Location> freeFields = currentField.getFreeAdjacentLocations(getLocation());
            
            if (!freeFields.isEmpty()) {
                Location free = freeFields.get(0);
                Plant seedling = newSeedling(free);
                nextFieldState.place(seedling, free);
            }
    
        }
    }
}