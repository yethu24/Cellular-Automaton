import javafx.scene.paint.Color; 
import java.util.List;
import java.util.Random;

/**
 * Recovered Cell Type
 * 
 * Recovering from the disease enables Recovered cell types to have a reduced chance of 
 * catching the disease again, due to their immune system becoming accustomed to fighting
 * off the illness. 
 * 
 * Forms a mutualistic symbiotic relationship with Vaccinated cell types leading to a reduced
 * chance of infection, representing herd immunity.
 * 
 * William Costales & Ye Win 
 *
 */

public class Recovered extends Cell {
    
    //chance of reinfection when around a diseased but not around a vaccinated
    private double infectionChance = 0.25;
    
    //reduced chance of reinfection when around both a diseased and a vaccinated
    private double reducedChance = 0.2;
    
    /**
     * Create a person who has recovered from the diseased
     *
     * @param field The field currently occupied.
     * @param location The location within the field.
     * @param state The current health state of the person occupying the cell.
     */
    public Recovered(Field field, Location location, State state) {
        super(field, location, state);
        setColor(Color.MAGENTA);
    }

    /**
    * This is how a recovered person behaves during the simulation
    * When next to a doctor, a recovered person becomes vaccinated
    * Else if next to a diseased, there's a probability of becoming diseased again
    * However, this probability is reduced when next to a vaccinated 
    */
    public void act() {
        List<Cell> neighbours = getField().getLivingNeighbours(getLocation());
        
        Random rand = Randomizer.getRandom();
        double random= rand.nextDouble();
        
        if (getNeighbourTypeCount(State.DOCTOR) >= 1) {
            setNextState(State.VACCINATED);
        }
        else if (getNeighbourTypeCount(State.DISEASED) >= 1) {
                if (getNeighbourTypeCount(State.VACCINATED) >= 1) {
                    if (random < reducedChance) {
                        setNextState(State.DISEASED);
                    }
                    else {
                        incrementAge();
                    }
                }
                else {
                    if (random < infectionChance) {
                        setNextState(State.DISEASED);
                    }
                    else {
                        incrementAge();
                    }
                }
                
        }
    }
}