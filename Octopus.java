import java.util.List;
import java.util.Set;
/**
 * The Octopus class creates a Predator with specfic features which match an octopus. Built with constant fields
 * with specific methods that reflect the behavior of an octopus.
 *
 * @Adarsh and Jushan 
 * @Date 23.02.2026 23:29
 */
public class Octopus extends Predator
{
    // How much a octopus can eat before being full i.e. at its stomach capacity
    private static final int MAX_FOOD_LEVEL = 120;
    // The max number of children that be can produced in one pregnancy
    private static final int MAX_LITTER_SIZE = 8;
    // The chance of a successful pregnancy, considering a partner a valid partner has been found    
    private static final double BREEDING_PROBABILITY = 0.6;
    // The minimum age required for breeding to take place
    private static final int BREEDING_AGE = 60;
    // Time required for a pregnancy to complete
    private static final int PREGNANCY_DURATION = 6;
    // The age at which an octopus dies, if they have not died of other causes
    private static final int DEATH_AGE = 480; 
    // probability of being infected initially (beginning of simulation)
    private static final double INITIAL_INFECTION_CHANCE = 0.01;
    // probability of disease spreading
    private static final double DISEASE_SPREAD = 0.1;
    // how long the infection lasts before death (terminal)
    private static final int DISEASE_DURATION = 20;
    
    // Times of day that an octopus hunts (is active)
    private final Set<TimeOfDay> ACTIVE_TIMES = Set.of(TimeOfDay.MIDNIGHT, TimeOfDay.DAWN);
    // The types of species that a tiger shark can consume.
    private final List<Class<?>> EDIBLE_ORGANISMS = List.of(HermitCrab.class, Krill.class); //Krill.class);

    

    /**
     * Constructor for objects of class Octopus
     */
    public Octopus(Location location)
    {
        super(location);
    }
    
    /**
     * Class to check whether the organism attempted to being eaten is consumable for a octopus.
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
     * Class to check whether a octopus is active at the current time.
     * @param currentTime - the current time in the simulation
     * @return - whether or not the octopus is active during this time of day
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
     * Returns 0 (as predators cannot be eaten)
     * @return 0 - an octopus is a predator that cannot be eaten
     */
    @Override
    protected int foodValue() {
        return 0; 
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
     * @return - the chance the octopus can breed, considering that they have found a suitable partner
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
     * Creates a new Octopus object (an offspring) after pregnancy
     * @param - The location for a child
     * @return - Octopus object at adjacent location to next
     */
    @Override
    public Animal createYoung(Location location) {
        return new Octopus(location); // new object in specified locationil07
    }
    
    /**
     * @return - the maximum age a tiger shark can live up to
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
     * @return - the duration an octopus lasts once a terminal disease has infected them
     */
    @Override
    public int diseaseDuration()
    {
        return DISEASE_DURATION;
    }
}