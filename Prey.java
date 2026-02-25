import java.util.List;
import java.util.Iterator;

/**
 * The Prey class inherits the Animal class, making methods specific towards how prey should
 * act.
 * 
 * @Adarsh and Jushan
 * @Date: 25.02.26 15:00
 */
public abstract class Prey extends Animal
{
    // The initial hunger level (Prey can last 65 steps with no food from the beginning of the
    // simulation
    private static final int INITIAL_FOOD_LEVEL = 65;
    
    /**
     * Constructor for objects of class Prey
     */
    public Prey(Location location)
    {
        super(location);
    }
    
    /**
     * @return - inital hunger level.
     */
    public int initialFoodLevel() 
    {
        return INITIAL_FOOD_LEVEL;
    }
    
    /**
     * Checks whether there is a consumable organism nearby to eat and move to its location.
     * The class also updates the hunger values of the organism, if the attempt to eat was successful.
     * @param - the current field (allowing us to see adjacent organisms)
     * @return - either a valid location where food is or no location (if no organism is found)
     * 
     */
    public Location getFoodLocation(Field field) {
        List<Location> adjacentLocations = field.getAdjacentLocations(getLocation()); // creates a list of the adjacent locations, from the current location
        Iterator<Location> it = adjacentLocations.iterator();
        
        Location foodLocation = null;
        while (foodLocation == null && it.hasNext()) {// goes through each adjacent location until either a location is found or there are no more places in the list 
            Location nextLocation = it.next(); // stores the next location in the list.
            Organism organism = field.getOrganismAt(nextLocation); // finds the organism at specified location "nextLocation"
        
            if ((organism != null) && isEdible(organism) && organism.isAlive()) { // checks whether there is a valid organism that is alive and can be eaten.
                
                if (organism instanceof Plant plant) {
                    
                    if (checkHungerMax(plant)) { // if eating the organism breaches the max value, it cannot eat it
                        return null;
                    }
                    organism.setDead();             // eaten so mark dead so not placed in nextFieldState
                    changeHungerValues(plant);   // updating the hunger level 
                    foodLocation = nextLocation;    // returns location of food so prey moves there
                }
            }
        }
        return foodLocation;
    }
    
    /**
     * Updates the hunger level depending on how much FOOD_VALUE the eaten animal gives.
     * @param - the animal that is being eaten
     */
    public void changeHungerValues(Plant plant) {
        eat(plant.foodValue());
    }
    
    /**
     * @param - the animal that is trying to be eaten.
     * @return - whether or not eating this specfic animal would take it over its max food level
     */
    public boolean checkHungerMax(Plant plant)
    {  
        return (plant.foodValue() + this.hungerLevel) >= this.maxFoodLevel();
    }
    
    /**
     * After each step, this method updates the character: increasing its age, increasing hunger, checking and updating its infection
     * and finally checking and updating pregnancy for potential offspring.
     * @param - the current field, giving access to the organisms currently adjacent to the animal
     * @param - the next field, allowing the method to know where to move and place itself for the next step
     * @param - the current time, which affects how certain animals act at different times of the day (e.g. Nocturnal only eat at night)
     */
    public void act(Field currentField, Field nextFieldState, TimeOfDay currentTime) {
        incrementAge();
        decrementHunger(); 
        updateInfection();
        spreadInfection(currentField);
        
        WeatherType weather = currentField.getWeather();
        if ((weather == WeatherType.STORM) || (weather == WeatherType.CLOUDY)) {
            decrementHunger();              // these cause the hunger to decrement an extra bit since more energy
        }
        
        
        if (isAlive()) {
            
             // Storm reduces movement which means the animal will stay in its place sometimes
            if ((weather == WeatherType.STORM) && rand.nextDouble() < 0.5) {
                // 50% chance of moving
                nextFieldState.place(this, getLocation());
                return;
                
            }     
            
            
            List<Location> freeLocations = nextFieldState.getFreeAdjacentLocations(getLocation());
            // Pregnancy cycle.
            if (pregnant) {
                pregnancyCounter--;
            
                if (pregnancyCounter <= 0) {
                    endPregnancy(nextFieldState);
                }}
            else if (checkPregnancyPossible(currentField)) {startPregnancy();}
            
            if(!isActiveAt(currentTime)) {
                nextFieldState.place(this, getLocation());
                return;
            }
            
            // Logic behind searching and finding food 
            Location nextLocation = getFoodLocation(currentField);
            if (nextLocation == null && ! freeLocations.isEmpty()) { // No food location found AND there is an  empty space to move to
                nextLocation = freeLocations.remove(0);              // The program moves to the animal to that location.
            }
            
            // Logic behind moving position -> If a position was found, it is set as the new location.
            if(nextLocation != null) {
                setLocation(nextLocation);
                nextFieldState.place(this, nextLocation);
            }
            
            else { // No free positions means the animal dies.
                setDead();
            }
        }
    }
    
    
    
 
}