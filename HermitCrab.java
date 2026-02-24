import java.util.List;
import java.util.Set;
/**
 * Write a description of class Parrotfish here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class HermitCrab extends Prey
{
    private static final int MAX_FOOD_LEVEL = 60;
    
    private static final int FOOD_VALUE = 10;
    
    private static final int MAX_LITTER_SIZE = 4;
    
    private static final double BREEDING_PROBABILITY = 0.05;
    
    private static final int BREEDING_AGE = 80;
    
    private static final int PREGNANCY_DURATION = 4;

    private static final int DEATH_AGE = 360; // figure out if 10 years/ 10 hours/ 10 seconds
    // probability of being infected initially (beginning of simulation)
    private static final double INITIAL_INFECTION_CHANCE = 0.01;
    // probability of disease spreading
    private static final double DISEASE_SPREAD = 0.1;
    // how long the infection lasts before death (terminal) -> could later make this vary per animal but for now constant
    private static final int DISEASE_DURATION = 20;
    
    private static final Set<TimeOfDay> ACTIVE_TIMES = Set.of(TimeOfDay.DAWN, TimeOfDay.DUSK);
    
    private static final List<Class<?>> EDIBLE_ORGANISMS = List.of(MarineAlgae.class, Kelp.class);
    
    public HermitCrab (Location location)
    {
        super(location);
    }
    
    @Override
    protected int foodValue() 
    {
        return FOOD_VALUE;
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
        return new HermitCrab(location);
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
