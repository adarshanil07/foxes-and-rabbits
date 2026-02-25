
/**
 * MarineAlgae is a plant organism within the simulation.
 * 
 * @author Jushan and Adarsh
 * @version v1
 */
public class MarineAlgae extends Plant
{    
    // Maximum age (in steps) that MarineAlgae can reach
    private static final int MAX_AGE = 120;
    // Default probability that MarineAlgae will spread in a step
    private static final double CHANCE_OF_SPREAD = 0.03;
    // Food units/points provided when the plant is eaten.
    private static final int FOOD_VALUE = 15;
    
    /**
     * Creates a MarineAlgae plant at a specific location
     * 
     * @param location The initial location of the MarineAlgae
     */
    public MarineAlgae(Location location)
    {
        super(location);
    }

    /**
     * Returns the maximum age of MarineAlgae
     * 
     * @return The maximum age of MarineAlgae in steps
     */
    @Override
    protected int getMaxAge()
    {
        return MAX_AGE;
    }
    
    /**
     * Returns default probability that MarineAlgae spreads per step
     * 
     * @return chance of spread within range 0.0 - 1.0
     */
    @Override
    protected double getChanceOfSpread()
    {
        return CHANCE_OF_SPREAD;
    }
    
    /**
     * Returns the food value of the MarineAlgae
     * 
     * @return The number of food units/points provided when eaten
     */
    protected int foodValue()
    {
        return FOOD_VALUE;
    }
    
    /**
     * Creates a seedling of MarineAlgae at a specific location
     * 
     * @param location The location for the just created seedling
     * @return A MarineAlgae instance 
     */
    protected Plant newSeedling(Location location)
    {
        Plant seedling = new MarineAlgae(location);
        return seedling;
    }
}