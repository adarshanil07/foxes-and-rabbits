import java.util.List;
import java.util.Iterator;
/**
 * Write a description of class Prey here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public abstract class Prey extends Animal
{
    /**
     * Constructor for objects of class Prey
     */
    public Prey(Location location)
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
            
            // added: organism != null: ensures somethign is at the location since you can get null error 
            
            if ((organism != null) && isEdible(organism) && organism.isAlive()) {
                
                if (organism instanceof Plant plant) {
                    
                    if (checkHungerMax(plant)) {
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
    
    public void changeHungerValues(Plant plant) {
        eat(plant.foodValue());
    }
    
    public boolean checkHungerMax(Plant plant)
    {  
        return (plant.foodValue() + this.hungerLevel) >= this.maxFoodLevel();
    }
    
    public void act(Field currentField, Field nextFieldState, TimeOfDay currentTime) {
        incrementAge();
        decrementHunger();    
        
        // weather can affect hunger levels ----- NEED TO FIGURE OUT IF THIS IS BAD PROGRAMMING PRACTICE AND IF HELPER METHOD NEEDED
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