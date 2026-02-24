import java.util.*;

/**
 * Represent a rectangular grid of field positions.
 * Each position is able to store a single animal/object.
 * 
 * @author David J. Barnes and Michael Kölling
 * @version 7.0
 */
public class Field
{
    // A random number generator for providing random locations.
    private static final Random rand = Randomizer.getRandom();
    
    // stores weather
    private WeatherType weather = WeatherType.CLEAR;
    
    // The dimensions of the field.
    private final int depth, width;
    // Animals mapped by location.
    private final Map<Location, Organism> field = new HashMap<>();
    // The animals.
    private final List<Organism> organisms = new ArrayList<>();

    /**
     * Represent a field of the given dimensions.
     * @param depth The depth of the field.
     * @param width The width of the field.
     */
    public Field(int depth, int width)
    {
        this.depth = depth;
        this.width = width;
    }

    /**
     * Place an animal at the given location.
     * If there is already an animal at the location it will
     * be lost.
     * @param anAnimal The animal to be placed.
     * @param location Where to place the animal.
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
     * Return the animal at the given location, if any.
     * @param location Where in the field.
     * @return The animal at the given location, or null if there is none.
     */
    public Organism getOrganismAt(Location location)
    {
        return field.get(location);
    }

    /**
     * SETTER Method for weather
     */
    public void setWeather(WeatherType weather)
    {
        this.weather = weather;
    }
    
    /**
     * GETTER METHOD FOR WEATHER
     */
    public WeatherType getWeather()
    {
        return this.weather;
    }
    /**
     * Get a shuffled list of the free adjacent locations.
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
     * @param location The location from which to generate adjacencies.
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
     * Print out the number of foxes and rabbits in the field.
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
     * Empty the field.
     */
    public void clear()
    {
        field.clear();
        organisms.clear();
    }

    /**
     * Return whether there is at least one predator and one prey in the field.
     * @return true if there is at least one predator and one prey in the field.
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
     * Get the list of animals.
     */
    public List<Organism> getOrganisms()
    {
        return organisms;
    }

    /**
     * Return the depth of the field.
     * @return The depth of the field.
     */
    public int getDepth()
    {
        return depth;
    }
    
    /**
     * Return the width of the field.
     * @return The width of the field.
     */
    public int getWidth()
    {
        return width;
    }
}
