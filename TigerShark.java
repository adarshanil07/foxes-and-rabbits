import java.util.List;
import java.util.Set;
/**
 * Write a description of class TigerShark here.
 *
 * @Adarsh and Jushan 
 * @Date 20.02.2026 23:50
 */
public class TigerShark extends Predator
{
    
    private static final int MAX_FOOD_LEVEL = 80;
    
    private static final int MAX_LITTER_SIZE = 8;
    
    private static final double BREEDING_PROBABILITY = 0.08;
    
    private static final int BREEDING_AGE = 240;
    
    private static final int PREGNANCY_DURATION = 12;
    
    private static final int DEATH_AGE = 800; // figure out if 10 years/ 10 hours/ 10 seconds
    // probability of being infected initially (beginning of simulation)
    private static final double INITIAL_INFECTION_CHANCE = 0.01;
    // probability of disease spreading
    private static final double DISEASE_SPREAD = 0.1;
    // how long the infection lasts before death (terminal) -> could later make this vary per animal but for now constant
    private static final int DISEASE_DURATION = 20;
    
    private static Set<TimeOfDay> ACTIVE_TIMES = Set.of(TimeOfDay.MIDDAY, TimeOfDay.DAWN);
    
    private static List<Class<?>> EDIBLE_ORGANISMS = List.of(Parrotfish.class, Krill.class); //Krill.class);

    

    /**
     * Constructor for objects of class TigerShark
     */
    public TigerShark(Location location)
    {
        super(location);
    }
    
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
    
    public boolean isActiveAt(TimeOfDay currentTime)
    {
        if (ACTIVE_TIMES.contains(currentTime)) {
            return true;
        }
        else {
            return false;
        }
    }
    
    @Override
    public int maxFoodLevel() 
    {
        return MAX_FOOD_LEVEL;
    }
    
    @Override
    protected int foodValue() {
        return 0; 
    }
    
    @Override
    public int maxLitterSize()
    {
        return MAX_LITTER_SIZE;
    }
    
    @Override
    public double breedingProbability()
    {
        return BREEDING_PROBABILITY;
    }
    
    @Override   
    public int breedingAge()
    {
        return BREEDING_AGE;
    }
    
    @Override
    public int pregnancyDuration()
    {
        return PREGNANCY_DURATION;
    }
    
    @Override
    public Animal createYoung(Location location) {
        return new TigerShark(location);
    }
    
    @Override
    public int deathAge()
    {
        return DEATH_AGE;
    }
    
    @Override
    public double initialInfectionChance()
    {
        return INITIAL_INFECTION_CHANCE;
    }
    
    @Override
    public double diseaseSpread()
    {
        return DISEASE_SPREAD;
    }
    
    @Override
    public int diseaseDuration()
    {
        return DISEASE_DURATION;
    }
}