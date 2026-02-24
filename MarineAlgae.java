
/**
 * Write a description of class MarineAlgae here.
 *
 * @author Jushan and Adarsh
 * @version v1s
 */
public class MarineAlgae extends Plant
{
    // DECIDE ALL AGES AND STUFF FOR ANIMALS/PLANTS (this is just rough)
    
    // max age of plant
    private static final int MAX_AGE = 120;
    // chance of spreading per step
    private static final double CHANCE_OF_SPREAD = 0.03;
    // food value of kelp
    private static final int FOOD_VALUE = 15;
    
    /**
     * Constructor for objects of class MarineAlgae
     */
    public MarineAlgae(Location location)
    {
        // initialise instance variables
        super(location);
    }

    /**
     * GETTER METHOD FOR GETTING MAX AGE
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
        Plant seedling = new MarineAlgae(location);
        return seedling;
    }
}