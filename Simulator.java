import java.util.*;

/**
 * A simple predator-prey simulator, based on a rectangular field containing 
 * rabbits and foxes.
 * 
 * @author David J. Barnes and Michael Kölling
 * @version 7.1
 */
public class Simulator
{
    // Constants representing configuration information for the simulation.
    // The default width for the grid.
    private static final int DEFAULT_WIDTH = 200;
    // The default depth of the grid.
    private static final int DEFAULT_DEPTH = 200;
    // The probability that a tiger shark will be created in any given grid position.
    private static final double TIGERSHARK_CREATION_PROBABILITY = 0.02;
    //The probability that an octopus will be created in any given grid position.
    private static final double OCTOPUS_CREATION_PROBABILITY = 0.15;
    // The probability that a parrot fish will be created in any given position.
    private static final double PARROTFISH_CREATION_PROBABILITY = 0.10;  
    // The probability that a krill will be created in any given position.
    private static final double KRILL_CREATION_PROBABILITY = 0.10; 
    // The probability that a hermit crab will be created in any given position.
    private static final double HERMITCRAB_CREATION_PROBABILITY = 0.10;
    // The probability that a kelp will be created in any given position.
    private static final double KELP_CREATION_PROBABILITY = 0.15;
    // The probability that marine algae will be created in any given position.
    private static final double MARINEALGAE_CREATION_PROBABILITY = 0.15;


    //clock
    private Clock clock = new Clock();
;
    
    
    
    
    // The current state of the field.
    private Field field;
    // The current step of the simulation.
    // A graphical view of the simulation.
    private final SimulatorView view;
    // Weather System for weather
    private Weather weather;

    /**
     * Construct a simulation field with default size.
     */
    public Simulator()
    {
        this(DEFAULT_DEPTH, DEFAULT_WIDTH);
        weather = new Weather(); 
        field.setWeather(weather.getWeather());
    }
    
    /**
     * Create a simulation field with the given size.
     * @param depth Depth of the field. Must be greater than zero.
     * @param width Width of the field. Must be greater than zero.
     */
    public Simulator(int depth, int width)
    {
        if(width <= 0 || depth <= 0) {
            System.out.println("The dimensions must be >= zero.");
            System.out.println("Using default values.");
            depth = DEFAULT_DEPTH;
            width = DEFAULT_WIDTH;
        }
        
        field = new Field(depth, width);
        view = new SimulatorView(depth, width);

        // setting up weather 
        weather = new Weather();
        field.setWeather(weather.getWeather());
        
        reset();
    }
    
    /**
     * Run the simulation from its current state for a reasonably long 
     * period (4000 steps).
     */
    public void runLongSimulation()
    {
        simulate(700);
    }
    
    /**
     * Run the simulation for the given number of steps.
     * Stop before the given number of steps if it ceases to be viable.
     * @param numSteps The number of steps to run for.
     */
    public void simulate(int numSteps)
    {
        reportStats();
        for(int n = 1; n <= numSteps && field.isViable(); n++) {
            simulateOneStep();
            delay(50);         // adjust this to change execution speed
        }
    }
    
    /**
     * Run the simulation from its current state for a single step.
     * Iterate over the whole field updating the state of each fox and rabbit.
     */
    public void simulateOneStep()
    {
        clock.tick();
        
        // if step count mod 8 == 0 then we will update weather
        if ((clock.getStepCount() % 8) == 0) {
            weather.updateWeather();    
        }
        

        // Use a separate Field to store the starting state of
        // the next step.
        Field nextFieldState = new Field(field.getDepth(), field.getWidth());

        // sets Weather for a field
        nextFieldState.setWeather(weather.getWeather());
        
        List<Organism> organisms = field.getOrganisms();
        for (Organism anOrganism : organisms) {
            anOrganism.act(field, nextFieldState, clock.getCurrentTime());
        }
        
        // Replace the old state with the new one.
        field = nextFieldState;

        reportStats();
        view.showStatus(clock.getStepCount(), clock.getFormattedTime(), field);
    }
        
    /**
     * Reset the simulation to a starting position.
     */
    public void reset()
    {
        clock.reset();
        
        weather.reset();
        
        field.clear();
        field.setWeather(weather.getWeather());         // sets current weather when reset which should be clear
        populate();
        view.showStatus(clock.getStepCount(), clock.getFormattedTime(), field);
    }
    
    /**
     * Randomly populate the field with foxes and rabbits.
     */
    private void populate()
    {
        Random rand = Randomizer.getRandom();
        field.clear();
        for(int row = 0; row < field.getDepth(); row++) {
            for(int col = 0; col < field.getWidth(); col++) {
                
                double probability = rand.nextDouble();
                
                if(rand.nextDouble() <= TIGERSHARK_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    TigerShark tigershark = new TigerShark(location);
                    field.place(tigershark, location);
                }
                    
                else if (rand.nextDouble() <= OCTOPUS_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Octopus octopus = new Octopus(location);
                    field.place(octopus, location);
                }
                                
                else if(rand.nextDouble() <= PARROTFISH_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Parrotfish parrotfish = new Parrotfish(location);
                    field.place(parrotfish, location);
                }
                
                else if (rand.nextDouble() <= KRILL_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Krill krill = new Krill(location);
                    field.place(krill, location);
                }
                else if (rand.nextDouble() <= HERMITCRAB_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    HermitCrab crab = new HermitCrab(location);
                    field.place(crab, location);
                }
                else if (rand.nextDouble() <= MARINEALGAE_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    MarineAlgae algae = new MarineAlgae(location);
                    field.place(algae, location);
                }
                else if (rand.nextDouble() <= KELP_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Kelp kelp = new Kelp(location);
                    field.place(kelp, location);
                }
                // else leave the location empty.
            }
        }
    }
    

    /**
     * Report on the number of each type of animal in the field.
     */
    public void reportStats()
    {
        //System.out.print("Step: " + step + " ");
        field.fieldStats();
    }
    
    /**
     * Pause for a given time.
     * @param milliseconds The time to pause for, in milliseconds
     */
    private void delay(int milliseconds)
    {
        try {
            Thread.sleep(milliseconds);
        }
        catch(InterruptedException e) {
            // ignore
        }
    }
}
