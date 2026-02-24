// maybe use whole package, not sure yet will figure later
//NOTES
//- Try add a current location parameter -> Reduces the number of times adjacent locations has to be used.

import java.util.List;
import java.util.Random;            // MAYBE NO NEED

/**
 * Common elements of all animals (Prey and Predators)
 * Animals are organisms which may move, breed, age, etc.
 *
 * @author David J. Barnes, Michael Kölling, Jushan and Adarsh
 * @version 7.0
 */
public abstract class Animal extends Organism
{
    // animal's age
    private int age;
    // Animal's Sex
    private Sex gender;
    //countdown until birth of child
    protected int pregnancyCounter = -1;
    //number of expected births in current cycle
    protected int numBirths = 0;
    //indicates whether animal pregnant or not, initially NOT pregnant
    protected boolean pregnant = false;
    //infected Animals
    private boolean infected;
    private int infectionDays;
    // initial food level
    private static final int INITAL_FOOD_LEVEL = 30;
    // hunger level 
    protected int hungerLevel;
    //Random
    Random rand = Randomizer.getRandom();                   // MAYBE STAY CONSISTENT WITH LIKE RAND/ RANDOM across the whole project?
    
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
    
    protected abstract boolean isEdible(Organism organism);             // check if Prey or Organism
    
    protected abstract int foodValue();

    
    protected abstract double initialInfectionChance();
    
    protected abstract double diseaseSpread();
    
    protected abstract int diseaseDuration();
    
    // DISEASES
    // probability of being infected initially (beginning of simulation)
    private static final double INITIAL_INFECTION_CHANCE = 0.01;
    // probability of disease spreading
    private static final double DISEASE_SPREAD = 0.1;
    // how long the infection lasts before death (terminal) -> could later make this vary per animal but for now constant
    private static final int DISEASE_DURATION = 20;

    
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
        gender = chooseGender();
        hungerLevel = INITAL_FOOD_LEVEL;
        infected = isInitiallyInfected();
    }
    

    public Sex chooseGender() {
        Sex[] genderValues = Sex.values();                          // 
        return genderValues[rand.nextInt(genderValues.length)];
    }
    
    /**
     * GETTER METHOD FOR AGE
     */
    public int getAge()
    {
        return age;
    }
    
    /**
     * GET SEX METHOD
     */
    public Sex getSex()
    {        
        return gender;
    }

    /**
     * 
     */
    public void eat(int foodValue)
    {
        hungerLevel += foodValue;
        
    
        if (hungerLevel > maxFoodLevel()) {
            hungerLevel = maxFoodLevel();
        }
    }
    
    /**
     * chance of being initially infected
     */
    private boolean isInitiallyInfected() 
    {
        infected = (rand.nextDouble() <= INITIAL_INFECTION_CHANCE);
        return infected;
    }
    
    /**
     * CHECK IF INFECTED
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
     * Update the infection per step 
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
     * Weather affects the spread probability, this returns the spread probability
     * after weather's effects
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
     * INCREMENT AGE
     */
    public void incrementAge()
    {
        age++;
        
        // >= to make robust
        if (age >= deathAge())
        {
            setDead();
        }
    }

    /**
     * Decrement hunger level
     */
    protected void decrementHunger()
    {
        hungerLevel--;
        
        if (hungerLevel <= 0)
        {
            setDead();
        }
    }
    
    /**
     * Act.
     * @param currentField The current state of the field.
     * @param nextFieldState The new state being built.
     */
    
    
    /**
     * Weather affects pregnancy aswell
     * 
     * This returns the breeding probability after the effects of weather
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
        if (breedChance < 1.0) {
            breedChance = 0.0;
        }
        
        // returns the breeding probability after effects of weather
        return breedChance;
    }
    
    
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
        
        for (Location location : adjacentLocations) {
            Organism organism = currentField.getOrganismAt(location);
            if (organism != null 
                && organism.isAlive() 
                && organism.getClass() == this.getClass() 
                && organism instanceof Animal other
                && other.getSex() == Sex.MALE) {
                return true;
            }
        }
        return false;
    }
    
    
    public void startPregnancy() {
        pregnancyCounter = pregnancyDuration();
        pregnant = true;
        numBirths = rand.nextInt(maxLitterSize()) + 1;
    }
    
    public void endPregnancy(Field  nextFieldState) {
        List<Location> freeLocations = nextFieldState.getFreeAdjacentLocations(getLocation());
        for (int b = 0; b < numBirths && ! freeLocations.isEmpty(); b++) 
        {
            Location newLocation = freeLocations.remove(0);
            Animal offspring = createYoung(newLocation);
            nextFieldState.place(offspring, newLocation);
        }
        pregnancyCounter = - 1;
        numBirths = 0;
        pregnant = false;
    }
    
    protected abstract Animal createYoung(Location location);
    
}
