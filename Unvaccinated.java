import javafx.scene.paint.Color; 
import java.util.List;
import java.util.Random;

/**
 * Unvaccinated Cells
 * 
 * They are likely to become infected when in contact with diseased cell types.
 * 
 * William Costales & Ye Win
 *
 */

public class Unvaccinated extends Cell {
    
    // Chance of this cell catching the disease when next to a diseased cell type
    private double infectionChance = 0.7;
    
    /**
     * Create a person who is unvaccinated.
     *
     * @param field The field currently occupied.
     * @param location The location within the field.
     * @param state The current health state of the person occupying the cell.
     */
    public Unvaccinated(Field field, Location location, State state) {
        super(field, location, state);
        setColor(Color.GREEN);
    }

    /**
    * This is how an unvaccinated person behaves during the simulation
    * When next to a doctor, the person becomes vaccinated
    * Else if next to a diseased, there's a high probability of becoming diseased
    */
    public void act() {
        List<Cell> neighbours = getField().getLivingNeighbours(getLocation());
        
        Random rand = Randomizer.getRandom();
        double random= rand.nextDouble();
        
        if (getNeighbourTypeCount(State.DOCTOR) >= 1) {
            setNextState(State.VACCINATED);
        }
        else if (getNeighbourTypeCount(State.DISEASED) >= 1) {
            if (random < infectionChance) {
                setNextState(State.DISEASED);
            }
        }
        else incrementAge();
    }
}