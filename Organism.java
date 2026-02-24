/**
 * This is a superclass for anything which exists in the field and updates on each step.
 * Plants and Animals can both exist. We could not have Plant Class inside Animal Class as that does not make sense so we separated it and put it under a shared superclass called Organism.
 *
 * Inside this class, we have the location of each organism, whether they are alive/dead and also their behaviour since each organism updates after each step.
 *
 * Abstract since it is a general concept and we are not able to create it directly. 
 * Like we cannot do new Organism(location);
 * 
 * @author Jushan and Adarsh
 * @version v1
 */
public abstract class Organism
{
    private boolean alive;
    private Location location;
    
    /**
     * Constructor for objects of class Organism
     */
    protected Organism(Location location)
    {
        // initialise instance variables
        alive = true;                       // all organisms are alive to begin with
        this.location = location;           // represents the location which they are currently in 
    }
    
    /**
     * NEED TO HAVE the act() method in here somehow 
     * 
     * *DELETE AFTER* We do not put implementation inside the organism.act() since the behaviour is different for predators, prey, etc.
     * We are essentially telling Java that anything that is an organism should implement this method, but all subclassess will provide a diff version of act()
     */
    public abstract void act(Field currentField, Field nextFieldState);

    /**
     * Returns whether or not the organism is alive or not
     */
    public boolean isAlive()
    {
        return alive;
    }
    
    /**
     * Marking an organism as dead and preventing logic from placing dead organisms into nextFieldState
     */
    protected void setDead()                                        // we use protected here since animals/plants need to be able to access it, but we do not want everything to access it
    {
        alive = false;
        location = null;
    }
    
    /**
     * Returns the current location of the organism 
     * and it returns "null" if dead
     */
    public Location getLocation()
    {
        return location;
    }
    
    /**
     * Update the organism's location
     * Only call when the organism has moved locations
     */
    protected void setLocation(Location newLocation)                
    {
        this.location = newLocation;
    }
    
    
    
}