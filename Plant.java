import java.util.Random;
import java.util.List;             
/**
 * Superclass for all plants in the simulation.
 * 
 * A plant never moves. It is able to age per simulation step, will die once it reaches its
 * maximum age. The weather can affect its maximum age, weather conditions can increase lifespan 
 * while also decrease lifespan of the plant. It is able to spread by creating a new seedling in 
 * neighbouring location based on a probability.
 *
 * @author Jushan and Adarsh
 * @version v1
 */
public abstract class Plant extends Organism
{
    // plants current age in steps 
    private int age;            
    private static final Random rand = Randomizer.getRandom();    
    
    /**
     * Constructor for objects of class Plant
     * Constructs a plant at the specific location with initial age of 0
     * 
     * @param location The location where this plant is initially.
     */
    public Plant(Location location)
    {
        super(location);
        age = 0;
    }

    /**
     * Returns the maximum age (in steps) of the plant before it dies
     * (this is the default max age, not weather affected max age)
     * 
     * @return default maximum age of the plant
     */
    protected abstract int getMaxAge();
    
    /**
     * Returns the default probability for the plant to spread in a given step
     * (this is the default probability, not weather affected probability)
     * 
     * @return default spreading probability (range 0.0 - 1.0)
     */
    protected abstract double getChanceOfSpread();

    /**
     * Create a new seedling of this plant type at a specific location (field)
     * 
     * @param location The location (field) where seedling is created
     * @return A new Plant instance of plant type
     */
    protected abstract Plant newSeedling(Location location);
    
    /**
     * Returns the food value of this plant if eaten
     * 
     * @return The number of food units/points provided when this plant is eaten.
     */
    protected abstract int foodValue();    
    
    /**
     * Increase the plant's age by one step (in simulation)
     * If the age reaches or surpasses default maximum age then the plant is marked as dead.
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
     * Calculates maximum age of plant after applying effects of weather
     * - Rain increases maximum age slightly
     * - Storm decreases maximum age slightly
     * Returned value can not be less than 1
     * 
     * @param weather The current weather type which is "affecting" the plant
     * @return The maximum age of plant after effects of weather
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
        
        // prevents maxAge from being 0 or lower since that is not possible logically
        if (maxAge < 1) {
            maxAge = 1;
        }
        
        return maxAge;
    }
      
    /**
     * Attempts to spread by creating seedling in neighbouring free location
     * Spreading probability affected by weather
     * - Rain increases spreading probability while storm decreases it.
     * 
     * @param currentField The current simulation field (used to query adjacent fields and weather)
     * @param nextFieldState The next simulation field where new seedlings are placed
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
        
        if (rand.nextDouble() <= spreadChance) {
            List<Location> freeFields = currentField.getFreeAdjacentLocations(getLocation());
            
            if (!freeFields.isEmpty()) {
                Location free = freeFields.get(0);
                Plant seedling = newSeedling(free);
                nextFieldState.place(seedling, free);
            }
    
        }
    }
    
    /**
     * Displays plant's behaviour for one step in the simulation
     * The plant ages, might die if reaches or exceeds weather adjusted maximum age,
     * remains in its current location in the next field state and could potentially
     * spread to nearby free location through new seedling being produced.
     * 
     * @param currentField The field representing the current state
     * @param nextFieldState The field into which this organism places itself and any any offspring for next step
     */
    public void act(Field currentField, Field nextFieldState, TimeOfDay currentTime)
    {
        incrementAge();
        
        // check to prevent dead plants from doing anything
        if (!isAlive()) {
            return;           
        }
                
        WeatherType weather = currentField.getWeather();
        int maxAge = getWeatherAffectedMaxAge(weather);
        
        // if the age has reached max age then the plant will die 
        // this is since the max age could be exceeded due to weather reducing it
        if (age >= maxAge) {
            setDead();
            return;
        }
        
        nextFieldState.place(this, getLocation());
        spread(currentField, nextFieldState);
    }    
}