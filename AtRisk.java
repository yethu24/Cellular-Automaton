import javafx.scene.paint.Color; 
import java.util.List;
import java.util.Random;

/**
 * AtRisk Cell Type - 
 * represents the Mycoplasma life form and its base tasks.
 * They are unlikely to survive long periods time with the disease.
 * Such as the elderly, low resistance people, children and babies
 * 
 * William Costales & Ye Win 
 *
 */

public class AtRisk extends Cell {
    // higher chance of infection due, as well as no shielding from vaccinated, recovered
    private double infectionChance = 0.8;
    
    /**
     * Create a new person who is at risk.
     *
     * @param field The field currently occupied.
     * @param location The location within the field.
     * @param state The current health state of the person occupying the cell.
     */
    public AtRisk(Field field, Location location, State state) {
        super(field, location, state);
        setColor(Color.GREY);
    }

    /**
    * This is how a person at risk behaves during the simulation
    * When next to a doctor,the person at risk becomes vaccinated
    * Else if next to a diseased, there's a probability of becoming diseased again
    * Else if not next to a diseased, the person at risk dies when there's less than
    * 2 neighbours or more than 3 neighbours
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
            else {
                incrementAge();
            }
        }    
        else if (neighbours.size() < 2 || neighbours.size() > 3) {
            setNextState(State.VACANTCELL);
        }
        else {
            incrementAge();
        }
    
    }
}    
