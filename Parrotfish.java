import java.util.List;
import java.util.Set;
/**
 * The Parrotfish class creates a Prey with specifc featues which match a parrotfish. Built with constant fields
 * with specific methods that reflect the behavior of an parrotfish.
 *
 * @Adarsh and Jushan
 * @Date 25.02.26 13:08
 */
public class Parrotfish extends Prey
{
    // How much a parrotfish can eat before being full i.e. at its stomach capacity 
    private static final int MAX_FOOD_LEVEL = 90;
    // The hunger level increase caused by eating a parrotfish
    private static final int FOOD_VALUE = 14;
    // The max number of children that be can produced in one pregnancy
    private static final int MAX_LITTER_SIZE = 8;
    // The chance of a successful pregnancy, considering a partner a valid partner has been found
    private static final double BREEDING_PROBABILITY = 0.26;
    // The minimum age required for breeding to take place
    private static final int BREEDING_AGE = 40;
    // Time required for a pregnancy to complete
    private static final int PREGNANCY_DURATION = 3;
    // The age at which a parrotfish dies, if they have not died of other cause
    private static final int DEATH_AGE = 320;
    // probability of being infected initially (beginning of simulation)
    private static final double INITIAL_INFECTION_CHANCE = 0.01;
    // probability of disease spreading
    private static final double DISEASE_SPREAD = 0.1;
    // how long the infection lasts before death (terminal) 
    private static final int DISEASE_DURATION = 20;
    
    // Times of the day that a parrotfish eats (is active)
    private static final Set<TimeOfDay> ACTIVE_TIMES = Set.of(TimeOfDay.MIDDAY, TimeOfDay.DAWN);
    // The types of species that a parrotfish can consume
    private static final List<Class<?>> EDIBLE_ORGANISMS = List.of(MarineAlgae.class, Kelp.class);
    
    /**
     * Constructor for objects of class Parrotfish
     */
    public Parrotfish (Location location)
    {
        super(location);
    }
    
    /**
     * @return - value gained by organism eating a parrotfish.
     */
    @Override
    protected int foodValue() 
    {
        return FOOD_VALUE;
    }
    
    /**
     * Class to check whether the organism attempted to being eaten is consumable for a parrotfish.
     * @param organism - the organism that is being eaten
     * @return boolean - whether or not the organism can be eaten or not
     */
    @Override
    protected boolean isEdible(Organism organism) 
    {
        for ( Class<?> organismClass : EDIBLE_ORGANISMS) {
            if (organismClass.isInstance(organism)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Class to check whether a parrotfish is active at the current time.
     * @param currentTime - the current time in the simulation
     * @return - whether or not the parrotfish is active during this time of day
     */
    public boolean isActiveAt(TimeOfDay currentTime)
    {
        if (ACTIVE_TIMES.contains(currentTime)) {
            return true;
        }
        else {
            return false;
        }
    }
    
    /**
     * GETTER for MAX_FOOD_LEVEL
     * @return - constant max food level
     */
    @Override
    public int maxFoodLevel() 
    {
        return MAX_FOOD_LEVEL;
    }
    
    /**
     * GETTER for MAX_LITTER_SIZE
     * @return - max number of children born after a pregnancy
     */
    @Override
    public int maxLitterSize()
    {
        return MAX_LITTER_SIZE;
    }
    
    /**
     * GETTER for BREEDING_PROBABILITY
     * @return - the chance the parrotfish can breed, considering that they have found a suitable partner
     */
    @Override
    public double breedingProbability()
    {
        return BREEDING_PROBABILITY;
    }
    
    /**
     * GETTER for PREGNANCY_DURATION
     * @return - number of days untill animal gives birth
     */
    @Override
    public int pregnancyDuration()
    {
        return PREGNANCY_DURATION;
    }
    
    /**
     * GETTER for BREEDING_AGE
     * @return - minimum age required to breed
     */
    @Override   
    public int breedingAge()
    {
        return BREEDING_AGE;
    }
    
    /**
     * Creates a new parrotfish object (an offspring) after pregnancy
     * @param - The location for a child
     * @return - Parrotfish object at adjacent location to next
     */
    @Override
    public Animal createYoung(Location location) {
        return new Parrotfish(location);
    }
    
    /**
     * @return - the maximum age a parrotfish can live up to
     */
    @Override
    public int deathAge()
    {
        return DEATH_AGE;
    }
    
    /**
     * @return - chance of being infected from the start of the simulation
     */
    @Override
    public double initialInfectionChance()
    {
        return INITIAL_INFECTION_CHANCE;
    }
    
    /**
     * @return - the probability that an adjacent animal will get infected
     */
    @Override
    public double diseaseSpread()
    {
        return DISEASE_SPREAD;
    }
    
    /**
     * @return - the duration a parrotfish lasts once a terminal disease has got them
     */
    @Override
    public int diseaseDuration()
    {
        return DISEASE_DURATION;
    }
}
