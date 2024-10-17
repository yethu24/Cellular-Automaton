import javafx.scene.paint.Color; 
import java.util.List;

/**
 * Doctor Cell Type
 * Doctors are unaffected by the disease, they administer vaccines as well as treat 
 * the ill, turning them into a Recovered type.
 * 
 * William Costales & Ye Win
 *
 */

public class Doctor extends Cell {

    /**
     * Create a new Doctor.
     *
     * @param field The field currently occupied.
     * @param location The location within the field.
     * @param state The current health state of the person occupying the cell.
     */
    public Doctor(Field field, Location location, State state) {
        super(field, location, state);
        setColor(Color.BLUE);
    }

    /**
    * This is how a doctor behaves
    * Although the method is quite empty the presence of a doctor
    * plays a major role in other life forms
    */
    public void act() {
        List<Cell> neighbours = getField().getLivingNeighbours(getLocation());
    }
}
