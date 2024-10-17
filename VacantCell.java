import javafx.scene.paint.Color; 
import java.util.List;

/**
 * Vacant Cell indicating that either
 * the cell is empty or the person just died
 * 
 * William Costales & Ye Win
 *
 */

public class VacantCell extends Cell {

    /**
     * Create a new vacant cell.
     *
     * @param field The field currently occupied.
     * @param location The location within the field.
     * @param state The current health state of the person occupying the cell.
     */
    public VacantCell(Field field, Location location, State state) {
        super(field, location, state);
        setColor(Color.WHITE);
    }

    /**
    * This is how a vacant cell behaves in the simulation
    * If there are 3 live neighbours, a person at risk can be respawned
    * If there are 2 live neighbours, a person unvaccinated can be respawned
    * This indicates reproduction
    */
    public void act() {
        List<Cell> neighbours = getField().getLivingNeighbours(getLocation());
        
        if (neighbours.size() == 3) {
            setNextState(State.ATRISK);
        }
        else if (neighbours.size() == 2) {
            setNextState(State.UNVACCINATED);
        }
        
        incrementAge();
    }
}
