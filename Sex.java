import java.util.Random;
/**
 * Enumeration class Sex - write a description of the enum class here
 *
 * @author (your name her   e)
 * @version (version number or date here)
 */
public enum Sex
{
    MALE, FEMALE;
    
    private static Random rand = Randomizer.getRandom();
    /**
     * METHOD
     */
    public Sex getSex()
    {
        Sex[] sexes = values();                             // values() is a method which returns an array of both MALE, FEMALE
        return sexes[rand.nextInt(sexes.length)];           // length of sexes is 2, generates a random number between those which is 0/1 and then goes into array sexes and returns either 
                                                            // sexes[0] or sexes[1] which is either MALE or FEMALE
    }
}

 