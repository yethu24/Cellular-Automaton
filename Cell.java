import javafx.scene.paint.Color; 
import java.util.List;
import java.util.HashMap;

/**
 * A class representing the shared characteristics of all forms of life
 * 
 * William Costales & Ye Win
 *
 */

public abstract class Cell {

    //private boolean alive;    
    //private boolean nextAlive; // The state of the cell in the next iteration
    
    // Field of the cell
    private Field field;
    
    // Location of the cell
    private Location location;
    
    // Colour of the cell
    private Color color = Color.WHITE;
    
    // Boolean to indicate whether the person occupying the cell is diseased or not
    private boolean hasDisease;

    // The current health state of the person occupying the cell
    private State currentState;
    
    // The next health state of the person occupying teh cell
    private State nextState;
    
    // Generations survived during the simulation
    // Default value = zero;
    private int age = 0;
    
    //HashMap to store the neighbour types of the cell and the respective count
    HashMap<State, Integer> neighbourTypes = new HashMap<>();
    
    /**
     * Create a new cell at location in field.
     *
     * @param field The field currently occupied.
     * @param location The location within the field.
     * @param state The current health state of the person occupying the cell.
     */
    public Cell(Field field, Location location, State state) {
        this.field = field;
        setLocation(location);
        currentState = state;
        nextState = state;
    }

    /**
     * Make this cell act - that is: the cell decides it's status in the
     * next generation.
     */
    abstract public void act();

    /**
     * Check whether the person occupying the cell is alive or not.
     * @return true if the person is still alive.
     */
    protected boolean isAlive() {
        if (currentState == State.VACANTCELL) { 
            return false; // Vacant Cell is our equivalent of being 'dead'
        }
        else return true;
    }

    /**
     * Sets the next state of the person as dead
     */
    protected void setDead() {
        nextState = State.VACANTCELL;
    }

    /**
     * Sets the health state of the person in the next generation.
     */
    protected void setNextState(State state) {
        nextState = state;
    }

    /**
     * Changes the color of the cell
     */
    public void setColor(Color col) {
        color = col;
    }

    /**
     * Returns the cell's color
     */
    public Color getColor() {
        return color;
        }

    /**
     * Return the cell's location.
     * @return The cell's location.
     */
    protected Location getLocation() {
        return location;
    }

    /**
     * Place the cell at the new location in the given field.
     * @param location The cell's location.
     */
    protected void setLocation(Location location) {
        this.location = location;
        field.place(this, location);
    }

    /**
     * Return the cell's field.
     * @return The cell's field.
     */
    protected Field getField() {
        return field;
    }
    
    /**
     * Return the cell's current state
     */
    protected State getState() {
        return currentState;
    }
    
    /**
     * Return the cell's next state
     */
    protected State getNextState() {
        return nextState;
    }
    
    /**
     * Increments the age
     */
    protected void incrementAge() {
        age++;
    }
    
    /**
     * Returns the age
     */
    protected int getAge() {
        return age;
    }
    
    /**
     * Returns the count of a certain type of neighbour which is passed in as a parameter
     */
    protected int getNeighbourTypeCount(State para) {
        neighbourTypes.clear();
        neighbourTypes.put(State.DOCTOR, 0);
        neighbourTypes.put(State.UNVACCINATED, 0);
        neighbourTypes.put(State.VACCINATED, 0);
        neighbourTypes.put(State.RECOVERED, 0);
        neighbourTypes.put(State.ATRISK, 0);
        neighbourTypes.put(State.DISEASED, 0);
        // Initialises each type of neighbour as zero
        List<Cell> neighbours = getField().getLivingNeighbours(getLocation());
        for (Cell neighbour : neighbours) {
            State state = neighbour.getState();
            if (neighbourTypes.containsKey(state)) {
                int count = neighbourTypes.get(state);
                neighbourTypes.replace(state, count, count+1);
            }
        }
        // Iterates through each indiviual cell's neighbours and places them in a list if found, incrementing the initial neighbour types by 1
        return neighbourTypes.get(para);
    }
}
