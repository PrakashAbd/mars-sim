/**
 * Mars Simulation Project
 * LandmarkConfig.java
 * @version 2.81 2007-08-26
 * @author Scott Davis
 */
package org.mars_sim.msp.simulation.mars;

import java.io.Serializable;
import java.util.*;

import org.mars_sim.msp.simulation.Coordinates;
import org.w3c.dom.*;

/**
 * Provides configuration information about landmarks.
 * Uses a DOM document to get the information. 
 */
public class LandmarkConfig implements Serializable {

	// Element names
	private static final String LANDMARK = "landmark";
	private static final String NAME = "name";
	private static final String LATITUDE = "latitude";
	private static final String LONGITUDE = "longitude";

	private Document landmarkDoc;
	private List<Landmark> landmarkList;

	/**
	 * Constructor
	 * @param landmarkDoc DOM document of landmark configuration.
	 */
	public LandmarkConfig(Document landmarkDoc) {
		this.landmarkDoc = landmarkDoc;
	}
	
	/**
	 * Gets a list of landmarks.
	 * @return list of landmarks
	 * @throws Exception when landmarks can not be parsed.
	 */
	public List getLandmarkList() throws Exception {
		
		if (landmarkList == null) {
			landmarkList = new ArrayList<Landmark>();
			
			Element root = landmarkDoc.getDocumentElement();
			NodeList landmarks = root.getElementsByTagName(LANDMARK);
			for (int x=0; x < landmarks.getLength(); x++) {
				String name = "";
				
				try {
					Element landmark = (Element) landmarks.item(x);
					
					// Get landmark name.
					name = landmark.getAttribute(NAME);
					
					// Get latitude.
					String latitude = landmark.getAttribute(LATITUDE);
					
					// Get longitude.
					String longitude = landmark.getAttribute(LONGITUDE);
					
					// Create location coordinate.
					Coordinates location = new Coordinates(latitude, longitude);
					
					// Create landmark.
					landmarkList.add(new Landmark(name, location));
				}
				catch (Exception e) {
					throw new Exception("Error reading landmark " + name + ": " + e.getMessage());
				}
			}
		}
		
		return landmarkList;
	}
}