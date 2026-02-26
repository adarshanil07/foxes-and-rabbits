import java.util.List;
import java.util.Set;
/**
 * The Krill class creates a Prey with specifc featues which match a krill. Built with constant fields
 * with specific methods that reflect the behavior of an krill.
 *
 * @Adarsh and Jushan
 * @Date 19.02.26 11:12
 */
public class Krill extends Prey
{
    // How much a krill can eat before being full i.e. at its stomach capacity
    private static final int MAX_FOOD_LEVEL = 75;
    // The hunger level increase caused by eating a krill
    private static final int FOOD_VALUE = 6;
    // The max number of children that be can produced in one pregnancy
    private static final int MAX_LITTER_SIZE = 8;
    // The chance of a successful pregnancy, considering a partner a valid partner has been found
    private static final double BREEDING_PROBABILITY = 0.22;
    // The minimum age required for breeding to take place
    private static final int BREEDING_AGE = 16;
    // Time required for a pregnancy to complete g
    private static final int PREGNANCY_DURATION = 2;
    // probability of being infected initially (beginning of simulation)
    private static final double INITIAL_INFECTION_CHANCE = 0.01;
    // probability of disease spreading
    private static final double DISEASE_SPREAD = 0.1;
    // how long the infection lasts before death (terminal) 
    private static final int DISEASE_DURATION = 20;
    // The age at which a krill dies, if they have not died of other cause
    private static final int DEATH_AGE = 160; 
    
    // Times of the day that a krill eats (is active)
    private static final Set<TimeOfDay> ACTIVE_TIMES = Set.of(TimeOfDay.DAWN, TimeOfDay.MIDDAY, TimeOfDay.DUSK, TimeOfDay.MIDNIGHT);
    // The types of species that a krill can consume
    private static final List<Class<?>> EDIBLE_ORGANISMS = List.of(MarineAlgae.class, Kelp.class);
    
    /**
     * Constructor for objects of class Krill
     */
    public Krill (Location location)
    {
        super(location);
    }
    
    /**
     * @return - value gained by organism eating a krill.
     */
    @Override
    protected int foodValue() 
    {
        return FOOD_VALUE;
    }
    
    /**
     * Class to check whether the organism attempted to being eaten is consumable for a krill.
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
     * Class to check whether a krill is active at the current time.
     * @param currentTime - the current time in the simulation
     * @return - whether or not the krill is active during this time of day
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
     * @return - the chance the krill can breed, considering that they have found a suitable partner
     */
    @Override
    public double breedingProbability()
    {
        return BREEDING_PROBABILITY;
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
     * GETTER for PREGNANCY_DURATION
     * @return - number of days untill animal gives birth
     */
    @Override
    public int pregnancyDuration()
    {
        return PREGNANCY_DURATION;
    }
    
    /**
     * Creates a new krill object (an offspring) after pregnancy
     * @param - The location for a child
     * @return - krill object at adjacent location to next
     */
    @Override
    public Animal createYoung(Location location) {
        return new Krill(location);
    }
    
    /**
     * @return - the maximum age a krill can live up to
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
     * @return - the duration a krill lasts once a terminal disease has got them
     */
    @Override
    public int diseaseDuration()
    {
        return DISEASE_DURATION;
    }
}
