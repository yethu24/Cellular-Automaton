import javafx.scene.paint.Color; 
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;


/**
 * A Life (Game of Life) simulator, first described by British mathematician
 * John Horton Conway in 1970.
 * 
 * William Costales & Ye Win
 *
 */

public class Simulator {

    private static final double ATRISK_ALIVE_PROB = 0.05;
    private static final double DOCTOR_ALIVE_PROB = 0.1;
    private static final double UNVACCINATED_ALIVE_PROB = 0.6;
    private static final double DISEASED_ALIVE_PROB = 0.6;
    private List<Cell> cells;
    private List<Cell> updatedCells;
    private Field field;
    private int generation;

    /**
     * Construct a simulation field with default size.
     */
    public Simulator() {
        this(SimulatorView.GRID_HEIGHT, SimulatorView.GRID_WIDTH);
    }

    /**
     * Create a simulation field with the given size.
     * @param depth Depth of the field. Must be greater than zero.
     * @param width Width of the field. Must be greater than zero.
     */
    public Simulator(int depth, int width) {
        cells = new ArrayList<>();
        field = new Field(depth, width);
        reset();
    }

    /**
     * Run the simulation from its current state for a single generation.
     * Iterate over the whole field updating the state of each life form.
     */
    public void simOneGeneration() {
        generation++;
        for (Iterator<Cell> it = cells.iterator(); it.hasNext(); ) {
            Cell cell = it.next();
            cell.act();
        }
        
        updatedCells = new ArrayList<>();
        
        for (Iterator<Cell> it = cells.iterator(); it.hasNext(); ) {
            Cell cell = it.next();
            Location location = cell.getLocation();
            if (cell.getState() == cell.getNextState() && cell != null){
                updatedCells.add(cell);//adds the life forms which unchanged state into a new array
            }
            else {
                switch(cell.getNextState()) {//adds the newly changed life forms into the new array   
                    case VACANTCELL:
                        VacantCell vacancy = new VacantCell(field, location, State.VACANTCELL);
                        field.place(vacancy, location);
                        updatedCells.add(vacancy);
                        break;
                        
                    case ATRISK:
                        AtRisk risky = new AtRisk(field, location, State.ATRISK);
                        field.place(risky, location);
                        updatedCells.add(risky);
                        break;
                        
                    case VACCINATED:
                        Vaccinated vaccinated = new Vaccinated(field, location, State.VACCINATED);
                        field.place(vaccinated, location);
                        updatedCells.add(vaccinated);
                        break;
                        
                    case DISEASED:
                        Diseased diseased = new Diseased(field, location, State.DISEASED);
                        field.place(diseased, location);
                        updatedCells.add(diseased);
                        break;
                    
                    case RECOVERED:
                        Recovered recovered = new Recovered(field, location, State.RECOVERED);
                        field.place(recovered, location);
                        updatedCells.add(recovered);
                        break;
                }
            }
        }
        
        cells = updatedCells; //the old arraylist is replaced by the new one
    }
    

    /**
     * Reset the simulation to a starting position.
     */
    public void reset() {
        generation = 0;
        cells.clear();
        populate();
    }

    /**
     * Randomly populate the field with different life forms
     */
    private void populate() {
      Random rand = Randomizer.getRandom();
      field.clear();
      for (int row = 0; row < field.getDepth(); row++) {
        for (int col = 0; col < field.getWidth(); col++) {
          Location location = new Location(row, col);
          if (rand.nextDouble() <= ATRISK_ALIVE_PROB) {
            AtRisk risky = new AtRisk(field, location, State.ATRISK);
            cells.add(risky);
          }
          else if (rand.nextDouble() <= UNVACCINATED_ALIVE_PROB) {
            Unvaccinated unvaccinated = new Unvaccinated(field, location, State.UNVACCINATED);
            cells.add(unvaccinated);
          }
          else if (rand.nextDouble() <= DISEASED_ALIVE_PROB) {
            Diseased diseased = new Diseased(field, location, State.DISEASED);
            cells.add(diseased);
          }
          else if (rand.nextDouble() <= DOCTOR_ALIVE_PROB) {
            Doctor doctor = new Doctor(field, location, State.DOCTOR);
            cells.add(doctor);
          }
          else {
            VacantCell vacancy = new VacantCell(field, location, State.VACANTCELL);
            cells.add(vacancy);
          }
        }
      }
    }

    /**
     * Pause for a given time.
     * @param millisec  The time to pause for, in milliseconds
     */
    public void delay(int millisec) {
        try {
            Thread.sleep(millisec);
        }
        catch (InterruptedException ie) {
            // wake up
        }
    }
    
    public Field getField() {
        return field;
    }

    public int getGeneration() {
        return generation;
    }
}
