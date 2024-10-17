import javafx.scene.paint.Color; 
import java.util.List;
import java.util.Random;

/**
 * Vaccinated Cell Type
 * They are vaccinated by doctors, forming a mutalistic symbiotic relationship with them,
 * furthermore have a reduced chance of infection around recovered people also forming the 
 * same mutualistic relationship, representing herd immunity.
 *
 * Note that Vaccinated cell types still have a chance of catching the disease, although
 * reduced.
 * 
 * William Costales & Ye Win 
 *
 */

public class Vaccinated extends Cell {
    
    //chance of reinfection when around an diseased but not around a vaccinated
    private double infectionChance = 0.2;
    
    //reduced chance of reinfection when around both a disease and a vaccinated
    private double reducedChance = 0.1;
    
    
    /**
     * Create a person who is vaccinated.
     *
     * @param field The field currently occupied.
     * @param location The location within the field.
     * @param state The current health state of the person occupying the cell.
     */
    public Vaccinated(Field field, Location location, State state) {
        super(field, location, state);
        setColor(Color.PURPLE);
    }

    /**
    * This is how a vaccinated person behaves during the simulation
    * If next to a diseased, there's a probability of becoming diseased
    * However, this probability is reduced when next to a recovered person 
    */
    public void act() {
        List<Cell> neighbours = getField().getLivingNeighbours(getLocation());
        
        Random rand = Randomizer.getRandom();
        double random= rand.nextDouble();
        
        if (getNeighbourTypeCount(State.DISEASED) >= 1) {
            if (getNeighbourTypeCount(State.RECOVERED) >= 1) {
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