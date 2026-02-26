import java.util.List;
import java.util.Random;           

/**
 * Common elements of all animals (Prey and Predators)
 * Animals are organisms which may move, breed, age, etc.
 *
 * @author David J. Barnes, Michael Kölling, Jushan and Adarsh
 * @Date 25.02.25 14:54
 */
public abstract class Animal extends Organism
{
    // animal's age
    private int age;
    // Animal's Sex
    private Sex gender;
    //countdown until birth of child
    protected int pregnancyCounter = -1; // (-1 signifies the animal is not pregnant yet)
    //number of expected births in current cycle
    protected int numBirths = 0;
    //indicates whether animal pregnant or not, initially NOT pregnant
    protected boolean pregnant = false;
    //whether or not the animal is infected
    private boolean infected;
    // number of days the animal can last after being infected
    private int infectionDays;
    // hunger level (number of days animal can last with no food)
    protected int hungerLevel;
    //Random
    Random rand = Randomizer.getRandom();                   

    
    /**
     * Getters for constants that differ between each animal
     */
    // max Animal age
    protected abstract int deathAge();
    // breeding age 
    protected abstract int breedingAge();               // every animal needs to define its breeding age.
    // breeding probability
    protected abstract double breedingProbability();
    //pregnancy duration
    protected abstract int pregnancyDuration();
    // max litter size
    protected abstract int maxLitterSize();
    // max food level
    protected abstract int maxFoodLevel();
    // active at what times of day.
    protected abstract boolean isActiveAt(TimeOfDay currentTime);
    // checks whether parameter organism is edible for the animal calling the method
    protected abstract boolean isEdible(Organism organism);             
    // the amount of food points an animal gains by eating this animal
    protected abstract int foodValue();
    // the initial food level an animal is given (amount of steps from the beginning of the simulation an animal can last eating no food and dying of no other diseases)
    protected abstract int initialFoodLevel();
    // chance of being infected from the beginning of the simulation
    protected abstract double initialInfectionChance();
    // probability of disease being spread to adjacent organisms
    protected abstract double diseaseSpread();
    // how long the animal survives before dying due to illness
    protected abstract int diseaseDuration();
    
    
    /**
     * Constructor for objects of class Animal.
     * @param location The animal's location.
     */
    protected Animal(Location location)
    {
        super(location);
        age = 0;
        infected = false;
        infectionDays = 0;
        gender = chooseGender(); // assigned a random gender
        hungerLevel = initialFoodLevel();
        infected = isInitiallyInfected(); // animal may start of with infection
    }
    
    /**
     * Returns a random gender, either male or female.
     * @return Sex enum - male or female
     */
    public Sex chooseGender() {
        Sex[] genderValues = Sex.values();                          // 
        return genderValues[rand.nextInt(genderValues.length)];
    }
    
    /**
     * @return current age (int)
     */
    public int getAge()
    {
        return age;
    }
    
    /**
     * @return gender of animal 
     */
    public Sex getSex()
    {        
        return gender;
    }

    /**
     *  Updates the values for hunger.
     *  @param the amount of "food points" gained from eating the organism
     */
    public void eat(int foodValue)
    {
        hungerLevel += foodValue;
    }
    
    /**
     * Uses probability to determine whether an animal is infected from the beginning of the progam,  
     * @return whether or not the animal is infected at the beginning
     */
    private boolean isInitiallyInfected() 
    {
        infected = (rand.nextDouble() <= initialInfectionChance()); // if random value is smaller than assigned constant, it is infected.
        return infected;
    }
    
    /**
     * @return whether or not animal is infected currently
     */
    public boolean isInfected()
    {
        return infected;
    }
    
    /**
     * Infect an animal (through disease spread) on the basis that the animal is not already infected (conditional check)
     */
    protected void infect()
    {
        if (!infected) {
            infected = true;            // becomes infected
            infectionDays = 0;          // just became infected so infected days is only 0
        }
    }

    /**
     * Updates infection fields after each step.
     */
    protected void updateInfection()
    {
        if (infected) {
            infectionDays++;            // number of infected days increment
            
            // think about what other features we can add like hunger decrease, movement slowed?
            
            // if animal has reached max number of infection days then it will automatically pass away
            if (infectionDays >= diseaseDuration()) {
                setDead();
            }
        }
    }
    
    
    /**
     * Weather affects the spread probability
     * @return the spread probability after weather's effects
     */
    protected double getWeatherAffectedDiseaseSpread(WeatherType weather)
    {
        double spreadChance = diseaseSpread();
        
        // if it is a storm then there is a greater chance of storm spreading
        if (weather == WeatherType.STORM) {
            spreadChance *= 2.0;
        }
        
        // if it is cloudy, the spread chance is decreased
        else if (weather == WeatherType.CLOUDY) {
            spreadChance *= 0.7;
        }
        
        // checks to ensure that probability is less than 1
        // this is since the storm can make chance greater than 1
        if (spreadChance > 1.0) {
            spreadChance = 1.0;
        }
        
        return spreadChance;
    }
    /**
     * Spreads infection to animals in adjacent fields, on the basis that probability of disease exceeded
     */
    protected void spreadInfection(Field field)
    {
        // if not infected or not alive then nothing happens
        if (!infected || !isAlive()) {
            return;
        }
        
        
        for (Location loc : field.getAdjacentLocations(getLocation())) {
            Organism organism = field.getOrganismAt(loc);
            
            // check if organism is animal since plants cannot get infected (as of yet)
            if (organism instanceof Animal animal) {
                // can only infect living creatures who are not infected
                if ((animal.isAlive()) && (!animal.isInfected())) {
                    
                    // might remove weather variable and put straight into parameter 
                    // depends if good programming practice
                    WeatherType weather = field.getWeather();
                    double spreadChance = getWeatherAffectedDiseaseSpread(weather);
                    
                    if (rand.nextDouble() <= spreadChance) {
                        animal.infect();
                    }
                    
                }
                
            }
        }
    }
    
    /**
     * Weather affects animal's hunger as well, as in the storm more energy is burned therefore more hunger
     * 
     * This returns additional hunger reduction which is caused by the weather (STORM/RAIN)
    */ 
    protected int getWeatherAffectedHungerHit(WeatherType weather)
    {
        if ((weather == WeatherType.STORM) || (weather == WeatherType.CLOUDY)) {
            return 1;
        }
        return 0;
    }
    
    /**
     * Increment the age after each step
     */
    public void incrementAge()
    {
        age++;
        // If the age exceeds or equals its death age, it dies.
        if (age >= deathAge())
        {
            setDead();
        }
    }

    /**
     * Decrement hunger level after each step
     */
    protected void decrementHunger()
    {
        hungerLevel--;
        // If the hunger level reaches 0 (or less) , it dies.
        if (hungerLevel <= 0)
        {
            setDead();
        }
    }
    
    /**
     * Weather affects pregnancy aswell by changing the chances of breeding in different weather conditions
     * @return The breeding probability after the effects of weather
     */
    protected double getWeatherAffectedBreedingProbability(WeatherType weather) 
    {
        double breedChance = breedingProbability();
        
        // storm heavy impact on breeding
        if (weather == WeatherType.STORM) {
            breedChance *= 0.5;     
        }
        
        // cloudy slight negative impact on breeding
        else if (weather == WeatherType.CLOUDY) {
            breedChance *= 0.8;
        }
        
        // rain slight positive impact on breeding
        else if (weather == WeatherType.RAIN) {
            breedChance *= 1.1;
        }
        
        // checks to keep within correct range
        if (breedChance > 1.0) {
            breedChance = 1.0;
        }
        if (breedChance < 0.0) {
            breedChance = 0.0;
        }
        
        // returns the breeding probability after effects of weather
        return breedChance;
    }
    
    /**
     * Checks whether this animal can reproduce in its current situation
     * Factors considered: gender, age, currently pregnant
     * @return whether or not the animal can reproduce currently
     */
    public boolean checkPregnancyPossible(Field currentField) 
    {
        // Checks whether the organism is a FEMALE, is above the breeding age, and not already pregnant.
        if (gender.equals(Sex.MALE) || getAge() < breedingAge() || pregnancyCounter != -1) {return false;}
        
        
        // Checks whether they are lucky enough to reproduce.
        // weather affects the breeding probability
        WeatherType weather = currentField.getWeather();
        double adjustedBreedChance = getWeatherAffectedBreedingProbability(weather);
        if (rand.nextDouble() > adjustedBreedChance) {return false;}
        
        
        Location currentLocation = this.getLocation();
        List<Location> adjacentLocations = currentField.getAdjacentLocations(currentLocation);
        
        // Cycles through each adjacent location, and checks whether a valid partner is found under the basis that the partner:
        // is alive, is of the same species, is also above the breeding age and is a male
        for (Location location : adjacentLocations) {
            Organism organism = currentField.getOrganismAt(location);
            if (organism != null 
                && organism.isAlive() 
                && organism.getClass() == this.getClass() 
                && organism instanceof Animal partner
                && partner.getSex() == Sex.MALE) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Starts the pregnancy, by changing fields to match the new condition.
     */
    public void startPregnancy() {
        pregnancyCounter = pregnancyDuration();
        pregnant = true;
        numBirths = rand.nextInt(maxLitterSize()) + 1;
    }
    
    /**
     * Ends the pregnancy, returning the animal back to it's original state and creating offspring.
     * @param The field for the next step, so offspring can be placed
     */
    public void endPregnancy(Field  nextFieldState) {
        List<Location> freeLocations = nextFieldState.getFreeAdjacentLocations(getLocation()); // List of free locations
        // Creates offspring until there are no free locations or the maximum number of offspring from one birth is reached for this animal
        for (int b = 0; b < numBirths && ! freeLocations.isEmpty(); b++)
        {
            Location newLocation = freeLocations.remove(0); 
            Animal offspring = createYoung(newLocation);
            nextFieldState.place(offspring, newLocation);
        }
        // Preganncy fields set back to default
        pregnancyCounter = - 1;
        numBirths = 0;
        pregnant = false;
    }
    
    // Creates an object of the same class at a specified location
    protected abstract Animal createYoung(Location location);
    
}
