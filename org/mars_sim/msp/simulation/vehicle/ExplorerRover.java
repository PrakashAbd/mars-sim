/**
 * Mars Simulation Project
 * ExplorerRover.java
 * @version 2.75 2004-01-12
 */

package org.mars_sim.msp.simulation.vehicle;

import java.io.Serializable;

import org.mars_sim.msp.simulation.Mars;
import org.mars_sim.msp.simulation.Resource;
import org.mars_sim.msp.simulation.structure.Settlement;

/**
 * The ExplorerRover class is a rover designed for exploration and collecting
 * rock samples.
 */
public class ExplorerRover extends Rover implements Medical, Serializable {
	
    // Static data members
    private static final double RANGE = 3000D; // Operating range of rover in km.
    private static final int CREW_CAPACITY = 4; // Max number of crewmembers.
    private static final double CARGO_CAPACITY = 5500D; // Cargo capacity of rover in kg.
    private static final double METHANE_CAPACITY = 1600D; // Methane capacity of rover in kg.
    private static final double OXYGEN_CAPACITY = 140D; // Oxygen capacity of rover in kg.
    private static final double WATER_CAPACITY = 560D; // Water capacity of rover in kg.
    private static final double FOOD_CAPACITY = 210D; // Food capacity of rover in kg.
    private static final double ROCK_SAMPLES_CAPACITY = 2000; // Rock samples capacity of rover in kg.
    private static final double ICE_CAPACITY = 2000; // Ice capacity of rover in kg.
    private static final int SICKBAY_LEVEL = 1; // Treatment level of sickbay.
    private static final int SICKBAY_BEDS = 1; // Number of beds in sickbay.

    // Data members
    private MobileLaboratory lab; // The rover's lab.
    private SickBay sickBay; // The rover's sick bay.
    
    /** 
     * Constructs an ExplorerRover object at a given settlement.
     * @param name the name of the rover
     * @param settlement the settlementt he rover is parked at
     * @param mars the mars instance
     */
    public ExplorerRover(String name, Settlement settlement, Mars mars) {
        // Use the Rover constructor
	    super(name, settlement, mars);

	    initExplorerRoverData();

	    // Add EVA Suits
	    addEVASuits();
    }

    /**
     * Initialize rover data
     */
    private void initExplorerRoverData() {
       
       // Set the description.
        description = "Explorer Rover";
       
        // Add scope to malfunction manager.
	    malfunctionManager.addScopeString("ExplorerRover");
	    malfunctionManager.addScopeString("Laboratory");
	    
        // Set the operating range of rover.
        range = RANGE;
        
        // Set crew capacity
	    crewCapacity = CREW_CAPACITY;

        // Set inventory total mass capacity.
        inventory.setTotalCapacity(CARGO_CAPACITY);
	
        // Set inventory resource capacities.
        inventory.setResourceCapacity(Resource.METHANE, METHANE_CAPACITY);
        inventory.setResourceCapacity(Resource.OXYGEN, OXYGEN_CAPACITY);
        inventory.setResourceCapacity(Resource.WATER, WATER_CAPACITY);
        inventory.setResourceCapacity(Resource.FOOD, FOOD_CAPACITY);
        inventory.setResourceCapacity(Resource.ROCK_SAMPLES, ROCK_SAMPLES_CAPACITY);
        inventory.setResourceCapacity(Resource.ICE, ICE_CAPACITY);
        
	    // Construct mobile lab.
	    String[] techSpeciality = { "Aerology" };
	    lab = new MobileLaboratory(1, 1, techSpeciality);
        
        // Construct sick bay.
        sickBay = new SickBay(this, SICKBAY_LEVEL, SICKBAY_BEDS);
    }

    /**
     * Gets the explorer rover's lab.
     * @return laboratory
     */
    public MobileLaboratory getLab() {
        return lab; 
    }
	/**
	 * @see org.mars_sim.msp.simulation.vehicle.Medical#getSickBay()
	 */
	public SickBay getSickBay() {
		return sickBay;
	}
}