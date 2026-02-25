import java.util.*;

/**
 * Field class represents a rectangular grid of field positions.
 * Each location can contain at most one Organism.
 The field also stores the current weather which affects organisms.
 * 
 * @author David J. Barnes, Michael Kölling, Jushan and Adarsh
 * @version 7.0
 */
public class Field
{
    private static final Random rand = Randomizer.getRandom();
    
    // Current weather condition which affects this field
    private WeatherType weather = WeatherType.CLEAR;
    
    // The dimensions of the field.
    private final int depth, width;
    
    // Mapping from location to organism stored at that location
    private final Map<Location, Organism> field = new HashMap<>();
    
    // List of organisms currently known to the field
    private final List<Organism> organisms = new ArrayList<>();

    /**
     * Represent a field of the given dimensions.
     * 
     * @param depth The depth of the field. 
     * @param width The width of the field.
     */
    public Field(int depth, int width)
    {
        this.depth = depth;
        this.width = width;
    }

    /**
     * Place an organism at the specific location.
     * If there is already an animal at the location it will
     * be replaced.
     * 
     * @param anAnimal The organism to be placed.
     * @param location Where to place the organism.
     */
    public void place(Organism anOrganism, Location location)
    {
        assert location != null;
        Object other = field.get(location);
        if(other != null) {
            organisms.remove(other);
        }
        field.put(location, anOrganism);
        organisms.add(anOrganism);
    }
    
    /**
     * Return the organism at the specific location, if any.
     * 
     * @param location Where in the field.
     * @return The organism at the given location, or null if there is none.
     */
    public Organism getOrganismAt(Location location)
    {
        return field.get(location);
    }

    /**
     * Set the current weather condition for this field
     * 
     * @param weather The new weather condition
     */
    public void setWeather(WeatherType weather)
    {
        this.weather = weather;
    }
    
    /**
     * Returns the current weather condition of this field
     * 
     * return The current WeatherType
     */
    public WeatherType getWeather()
    {
        return this.weather;
    }
    
    /**
     * Return a shuffled list of the free adjacent locations.
     * A location is free if it contains no organism or it contains an organism
     * which is no longer alive
     * 
     * @param location Get locations adjacent to this.
     * @return A list of free adjacent locations.
     */
    public List<Location> getFreeAdjacentLocations(Location location)
    {
        List<Location> free = new LinkedList<>();
        List<Location> adjacent = getAdjacentLocations(location);
        for(Location next : adjacent) {
            Organism anOrganism = field.get(next);
            if(anOrganism == null) {
                free.add(next);
            }
            else if(!anOrganism.isAlive()) {
                free.add(next);
            }
        }
        return free;
    }

    /**
     * Return a shuffled list of locations adjacent to the given one.
     * The list will not include the location itself.
     * All locations will lie within the grid.
     * 
     * @param location The location from which to generate adjacent locations.
     * @return A list of locations adjacent to that given.
     */
    public List<Location> getAdjacentLocations(Location location)
    {
        // The list of locations to be returned.
        List<Location> locations = new ArrayList<>();
        if(location != null) {
            int row = location.row();
            int col = location.col();
            for(int roffset = -1; roffset <= 1; roffset++) {
                int nextRow = row + roffset;
                if(nextRow >= 0 && nextRow < depth) {
                    for(int coffset = -1; coffset <= 1; coffset++) {
                        int nextCol = col + coffset;
                        // Exclude invalid locations and the original location.
                        if(nextCol >= 0 && nextCol < width && (roffset != 0 || coffset != 0)) {
                            locations.add(new Location(nextRow, nextCol));
                        }
                    }
                }
            }
            
            // Shuffle the list. Several other methods rely on the list
            // being in a random order.
            Collections.shuffle(locations, rand);
        }
        return locations;
    }

    /**
     * Print the population statistics for each organism type within the field
     */
    public void fieldStats()
    {
        int numTigerShark = 0,
            numOctopus = 0,
            numParrotfish = 0,
            numKrill = 0,
            numHermitCrab = 0,
            numMarineAlgae = 0,
            numKelp = 0;
            
        for(Organism organism : field.values()) {
            if(organism instanceof TigerShark shark) {
                if(shark.isAlive()) {
                    numTigerShark++;
                }
            }
            if(organism instanceof Parrotfish fish) {
                if(fish.isAlive()) {
                    numParrotfish++;
                }
            }
            if (organism instanceof Octopus octopus) {
                if(octopus.isAlive()) {
                    numOctopus++;
                }
            }
            if (organism instanceof Krill krill) {
                if(krill.isAlive()) {
                    numKrill++;
                }
            }
            if (organism instanceof HermitCrab crab) {
                if(crab.isAlive()) {
                    numHermitCrab++;
                }
            }
            if (organism instanceof MarineAlgae algae) {
                if(algae.isAlive()) {
                    numMarineAlgae++;
                }
            }
            if (organism instanceof Kelp kelp) {
                if(kelp.isAlive()) {
                    numKelp++;
                }
            }
        }
        System.out.println("Tiger Sharks: " + numTigerShark +
                           " Octopuses : " + numOctopus +
                           " Parrotfishes : " + numParrotfish +
                           " Krill: " + numKrill +
                           " Hermit Crab: " + numHermitCrab +
                           " Kelp: " + numKelp +
                           " Marine Algae: " + numMarineAlgae);
    }

    /**
     * Removes all organisms from the field
     */
    public void clear()
    {
        field.clear();
        organisms.clear();
    }

    /**
     * Return whether the simulation is still viable, therefore containing at least one
     * living predator and one living prey
     * 
     * @return true if there is at least one predator and one prey are alive, false otherwise
     */
    public boolean isViable()
    {
        boolean predatorFound = false;
        boolean preyFound = false;
        Iterator<Organism> it = organisms.iterator();
        while(it.hasNext() && ! (predatorFound && preyFound)) {
            Organism organism = it.next();
            if(organism instanceof Predator predator) {
                if(predator.isAlive()) {
                    predatorFound = true;
                }
            }
            else if(organism instanceof Prey prey) {
                if(prey.isAlive()) {
                    preyFound = true;
                }
            }
        }
        return predatorFound && preyFound;
    }
    
    /**
     * Return the list of organisms currently stored within the field
     * 
     * @return The organism list for iteration
     */
    public List<Organism> getOrganisms()
    {
        return organisms;
    }

    /**
     * Return the depth of the field.
     * 
     * @return The depth of the field.
     */
    public int getDepth()
    {
        return depth;
    }
    
    /**
     * Return the width of the field.
     * 
     * @return The width of the field.
     */
    public int getWidth()
    {
        return width;
    }
}
