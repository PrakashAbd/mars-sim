/**
 * Mars Simulation Project
 * DirectionDisplayPanel.java
 * @version 2.75 2003-06-28
 * @author Scott Davis
 */

package org.mars_sim.msp.ui.standard.unit_window.vehicle;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import org.mars_sim.msp.simulation.Direction;
import org.mars_sim.msp.simulation.vehicle.Vehicle;


/** 
 * The DirectionDisplayPanel class displays the compass direction
 * a vehicle is currently travelling.
 */
public class DirectionDisplayPanel extends JPanel {

    // Static members
    private final static int CIRCLE_DIAMETER = 44;
    private final static int CIRCLE_RADIUS = CIRCLE_DIAMETER / 2;
    
	// Data members
    private Vehicle vehicle;

	/** 
     * Constructor
     *
     * @param vehicle the vehicle to track
     */
	public DirectionDisplayPanel(Vehicle vehicle) {
        // Use JPanel constructor
		super();
        
        // Initialize data members
        this.vehicle = vehicle;
		
		// Set preferred component size.
		setPreferredSize(new Dimension(52, 52));
        
        // Add border
        setBorder(new LineBorder(Color.green));
        
        // Set panel to be opaque.
        setOpaque(true);
        
        // Set background to black
        setBackground(Color.black);
	}

	/** 
     * Update this panel.
     */
    public void update() {
        repaint();
    }

	/** Override paintComponent method 
     *  @param g graphics context
     */
	public void paintComponent(Graphics g) {

        super.paintComponent(g);
        
        // Get component height and width
        int height = getHeight();
        int width = getWidth();
        int centerX = width / 2;
        int centerY = height / 2;
        
		// Draw dark green background circle 
		g.setColor(new Color(0, 62, 0));
        g.fillOval(centerX - CIRCLE_RADIUS, centerY - CIRCLE_RADIUS, 
            CIRCLE_DIAMETER, CIRCLE_DIAMETER);

		// Draw bright green out circle
		g.setColor(Color.green);
		g.drawOval(centerX - CIRCLE_RADIUS, centerY - CIRCLE_RADIUS, 
            CIRCLE_DIAMETER, CIRCLE_DIAMETER);

		// Draw center dot
		g.drawRect(centerX, centerY, 1, 1);

		// Prepare letter font
		Font tempFont = new Font("SansSerif", Font.PLAIN, 9);
		g.setFont(tempFont);
		FontMetrics tempMetrics = getFontMetrics(tempFont);
		int fontHeight = tempMetrics.getAscent();
        int letterRadius = CIRCLE_RADIUS - 7;

		// Draw 'N'
		int nWidth = tempMetrics.charWidth('N');
        g.drawString("N", centerX - (nWidth / 2), centerY - letterRadius + (fontHeight / 2));

		// Draw 'S'
		int sWidth = tempMetrics.charWidth('S');
		g.drawString("S", centerX - (sWidth / 2), centerY + letterRadius + (fontHeight / 2));

		// Draw 'W'
		int wWidth = tempMetrics.charWidth('W');
		g.drawString("W", centerX - letterRadius - (wWidth / 2), centerY + (fontHeight / 2));

		// Draw 'E'
		int eWidth = tempMetrics.charWidth('E');
		g.drawString("E", centerX + letterRadius - (eWidth / 2), centerY + (fontHeight / 2));

		// Draw direction line if necessary
		if (vehicle.getStatus().equals(Vehicle.MOVING)) {
            Direction direction = vehicle.getDirection();
			double hyp = (double) (CIRCLE_RADIUS);
			int newX = (int) Math.round(hyp * direction.getSinDirection());
			int newY = -1 * (int) Math.round(hyp * direction.getCosDirection());
			g.drawLine(centerX, centerY, centerX + newX, centerY + newY);
		}
	}
}
