import java.util.List;
import java.util.Iterator;

/**
 * The Predator class inherits the Animal class, making methods specific towards how predators should
 * act.
 * 
 * @Adarsh and Jushan
 * @Date: 25.02.26 14:14
 */
public abstract class Predator extends Animal
{
    // The initial hunger level (Predators can last 30 steps with no food from the beginning of the
    // simulation.
    private static final int INITIAL_FOOD_LEVEL = 30;

    /**
     * Constructor for objects of class Predator
     */
    
    public Predator(Location location)
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
        while (foodLocation == null && it.hasNext()) { // goes through each adjacent location until either a location is found or there are no more places in the list 
            Location nextLocation = it.next(); // stores the next location in the list.
            Organism organism = field.getOrganismAt(nextLocation); // finds the organism at specified location "nextLocation"
            if (organism != null && isEdible(organism) && organism.isAlive()) { // checks whether there is a valid organism that is alive and can be eaten.
                
                if (organism instanceof Animal animal)
                {                     
                    if (checkHungerMax(animal)) { // if eating the organism breaches the max value, it cannot eat it
                        return null;
                    }
                    animal.setDead(); // kills the animal being eating
                    changeHungerValues(animal); // updates the animals hunger
                    foodLocation = nextLocation; // changes the location to the eaten animals location
                }
            }
        }
        return foodLocation;
    }
    
    /**
     * @param - the animal that is trying to be eaten.
     * @return - whether or not eating this specfic animal would take it over its max food level
     */
    public boolean checkHungerMax(Animal animal)
    {  
        return (animal.foodValue() + this.hungerLevel) >= this.maxFoodLevel();
    }
    
    
    /**
     * Updates the hunger level depending on how much FOOD_VALUE the eaten animal gives.
     * @param - the animal that is being eaten
     */
    public void changeHungerValues(Animal animal) {
            eat(animal.foodValue());
    }
    
    
    /**
     * After each step, this method updates the character: increasing its age, increasing hunger, checking and updating its infection
     * and finally checking and updating pregnancy for potential offspring.
     * @param - the current field, giving access to the organisms currently adjacent to the animal
     * @param - the next field, allowing the method to know where to move and place itself for the next step
     * @param - the current time, which affects how certain animals act at different times of the day (e.g. Nocturnal only eat at night)
     */
    public void act(Field currentField, Field nextFieldState, TimeOfDay currentTime) {
        
        incrementAge(); // Increases the age by 1
        decrementHunger();  // Decreases food level by 1  
        updateInfection(); // checks and updates infection (already 
        spreadInfection(currentField); // spreads infection to adjacent peopke
        
        WeatherType weather = currentField.getWeather(); // current weather
        
        int hungerHit = getWeatherAffectedHungerHit(weather);
        for (int i = 0; i < hungerHit; i++) {
            decrementHunger();
        }
        
        // all logic for decreasing hunger inside decrementHunger 
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
            
                if (pregnancyCounter <= 0) { // if pregnancy ends, birth is given and cycle reset
                    endPregnancy(nextFieldState);
                }}
                // if pregnancy hasn't started, a check is done to see if animal can get pregnant.
            else if (checkPregnancyPossible(currentField)) {startPregnancy();}
            
            // if the animal isn't active, it stays still in its position and doesn't eat.
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
            
            else { // No free positions means the animal dies due to overcrowding
                setDead();
            }
        }
    }
}