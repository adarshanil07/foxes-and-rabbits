import java.util.List;
import java.util.Iterator;

/**
 * Write a description of class Predator here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public abstract class Predator extends Animal
{
    /**
     * Constructor for objects of class Predator
     */
    
    public Predator(Location location)
    {
        super(location);
    }
    
    public Location getFoodLocation(Field field) {
        List<Location> adjacentLocations = field.getAdjacentLocations(getLocation());
        Iterator<Location> it = adjacentLocations.iterator();
        
        Location foodLocation = null;
        while (foodLocation == null && it.hasNext()) {
            Location nextLocation = it.next();
            Organism organism = field.getOrganismAt(nextLocation);
            if (organism != null && isEdible(organism) && organism.isAlive()) {
                
                if (organism instanceof Animal animal)
                {                     
                    if (checkHungerMax(animal)) {
                        return null;
                    }
                    animal.setDead();
                    changeHungerValues(animal);
                    foodLocation = nextLocation;
                }
            }
        }
        return foodLocation;
    }
    
    /**
     * returns true/false depending if hunger i full or not 
     */
    public boolean checkHungerMax(Animal animal)
    {  
        return (animal.foodValue() + this.hungerLevel) >= this.maxFoodLevel();
    }
    
    public void changeHungerValues(Animal animal) {
            eat(animal.foodValue());
    }
    
    
    public void act(Field currentField, Field nextFieldState) {
        
        // NEED TO CHECK IF THESE ARE BAD PROGRAMMING PRACTICE SINCE WE ARE CALLING METHODS
        // FROM THE CLASS ABOVE IT IN THE HIERARCHY (ANIMAL)
        incrementAge();
        decrementHunger();    
        updateInfection();
        spreadInfection(currentField);
        
        // weather can affect hunger levels ----- NEED TO FIGURE OUT IF THIS IS BAD PROGRAMMING PRACTICE AND IF HELPER METHOD NEEDED
        WeatherType weather = currentField.getWeather();
        // hunger hit
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
            
                if (pregnancyCounter <= 0) {
                    endPregnancy(nextFieldState);
                }}
            else if (checkPregnancyPossible(currentField)) {startPregnancy();}
            
            
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