import javafx.scene.paint.Color; 
import java.util.List;
import java.util.Random;

/**
 * Diseased Cells.
 * 
 * When a cell is in contact with diseased cells, they have a chance of catching the disease,
 * updating their type to Diseased.
 * They have a shorter life-span, changing their colour when near to their deaths.
 *
 * William Costales & Ye Win
 *
 */

public class Diseased extends Cell {
    
    //Recovery rate from the disease without a doctor around
    private double recoveryRate = 0.3;
    
    //Recovery rate from the disease with a doctor around
    private double treatedRate = 0.6;

    //Fatality rate during the early stages of a diseased
    private double orangeFatalityRate = 0.2;
    
    //Fatality rate during the late stages of a diseased
    private double redFatalityRate = 0.8;
    
    /**
     * Create a person who is infected with the disease.
     *
     * @param field The field currently occupied.
     * @param location The location within the field.
     * @param state The current health state of the person occupying the cell.
     */
    public Diseased(Field field, Location location, State state) {
        super(field, location, state);
        setColor(Color.ORANGE);
    }

    /**
    * This is how a diseased person behaves
    * During the earlier stage of being diseased which is indiciated by color orange and age being less than 6
    * when next to a doctor, there's a higher chance of recovery than without one next to it
    * However, there is still a chance of fatality
    * When the diseased person reaches the age 6, the color changes to red
    * At this stage, they can no longer recover nor be saved by the doctor
    * Fatality during this stage is almost certain
    */
    public void act() {
        List<Cell> neighbours = getField().getLivingNeighbours(getLocation());
        
        Random rand = Randomizer.getRandom();
        double random= rand.nextDouble();
        
        if (getAge() <= 3) {
            if (getNeighbourTypeCount(State.DOCTOR) >= 1) {
                if (random < orangeFatalityRate) {
                    setNextState(State.VACANTCELL);
                }
                else if (random < treatedRate) {
                    setNextState(State.RECOVERED);
                }
                else {
                    incrementAge(); 
                }
            }
            else {
                if (random < orangeFatalityRate) {
                    setNextState(State.VACANTCELL);
                }
                else if (random < recoveryRate) {
                    setNextState(State.RECOVERED);
                }
                else {
                    incrementAge();
                }
            }
            }
        else {
            setColor(Color.RED);
            if (random < redFatalityRate) {
                setNextState(State.VACANTCELL);
            }
            else {
                incrementAge();
            }
        }
        
    }
}