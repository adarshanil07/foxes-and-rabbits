
/**
 * Kelp is a plant organism within the simulation
 *
 * @author Jushan and Adarsh
 * @version v1
 */
public class Kelp extends Plant
{
    // Maximum age (in steps) that Kelp can reach
    private static final int MAX_AGE = 120;
    // Default probability that Kelp spreads in a step
    private static final double CHANCE_OF_SPREAD = 0.032;
    // Food value provided when this plant is eaten
    private static final int FOOD_VALUE = 14;

    /**
     * Creates a Kelp plant at that specific location
     */
    public Kelp(Location location)
    {
        super(location);
    }

    /**
     * Returns the maximum age of Kelp
     * 
     * @return The maximum age in steps
     */
    @Override
    protected int getMaxAge()
    {
        return MAX_AGE;
    }
    
    /**
     * Returns the default probability that Kelp spreads in a step
     * 
     * @return The chance of spreading within range 0.0 - 1.0
     */
    @Override
    protected double getChanceOfSpread()
    {
        return CHANCE_OF_SPREAD;
    }
    
    /**
     * Returns the food value of Kelp
     * 
     * @return The number of food units/points provided when eaten
     */
    protected int foodValue()
    {
        return FOOD_VALUE;
    }
    
    /**
     * Creates a new seedling of Kelp at that specific location
     * 
     * @param location The location of the new Kelp seedling
     * @return An instance of Kelp 
     */
    protected Plant newSeedling(Location location)
    {
        Plant seedling = new Kelp(location);
        return seedling;
    }
}