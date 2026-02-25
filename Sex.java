import java.util.Random;
/**
 * The Sex enum represents the biological sex of an organism.
 * Provides two possible values: MALE and FEMALE
 * A helper method is included to generate a random sex value
 *
 * @author Jushan and Adarsh
 * @version v1
 */
public enum Sex
{
    MALE, FEMALE;
    
    // Random number generator which is used to select a random sex
    private static Random rand = Randomizer.getRandom();
    /**
     * Return a randomly selected Sex value (MALE or FEMALE)
     * 
     * @return A randomly chosen Sex constant
     */
    public Sex getSex()
    {
        Sex[] sexes = values();                            
        return sexes[rand.nextInt(sexes.length)];           
    }
}

 