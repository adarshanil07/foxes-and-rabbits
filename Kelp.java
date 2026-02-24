
/**
 * Write a description of class Kelp here.
 *
 * @author Jushan and Adarsh
 * @version v1
 */
public class Kelp extends Plant
{
    // max age of kelp
    private static final int MAX_AGE = 120;
    // chance of spreading per step
    private static final double CHANCE_OF_SPREAD = 0.032;
    // food value for marine algae
    private static final int FOOD_VALUE = 14;

    /**
     * Constructor for objects of class Kelp
     */
    public Kelp(Location location)
    {
        // initialise instance variables
        super(location);
    }

    /**
     * GETTER METHOD FOR GETTING MAX AGE
     * 
     * gonna use this method in spread and grow logic in plant class 
     */
    protected int getMaxAge()
    {
        return MAX_AGE;
    }
    
    /**
     * Get probability of seeds spreading 
     */
    protected double getChanceOfSpread()
    {
        return CHANCE_OF_SPREAD;
    }
    
    /**
     * food value
     */
    protected int foodValue()
    {
        return FOOD_VALUE;
    }
    
    /**
     * creating a new seedling
     */
    protected Plant newSeedling(Location location)
    {
        Plant seedling = new Kelp(location);
        return seedling;
    }
}