/**
 * Mars Simulation Project
 * PowerGridTabPanel.java
 * @version 2.75 2003-06-03
 * @author Scott Davis
 */

package org.mars_sim.msp.ui.standard.unit_window.structure;

import org.mars_sim.msp.simulation.structure.Settlement;
import org.mars_sim.msp.simulation.structure.building.*;
import org.mars_sim.msp.simulation.structure.building.function.PowerGeneration;
import org.mars_sim.msp.ui.standard.*;
import org.mars_sim.msp.ui.standard.unit_window.TabPanel;
import java.awt.*;
import java.text.DecimalFormat;
import java.util.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.*;

/** 
 * The PowerGridTabPanel is a tab panel for a settlement's power grid information.
 */
public class PowerGridTabPanel extends TabPanel {
    
    // Data Members
    private JLabel powerGeneratedLabel; // The total power generated label.
    private JLabel powerUsedLabel; // The total power used label.
    private PowerTableModel powerTableModel; // Table model for power info.
    
    // Data cache
    private double powerGeneratedCache; // The total power generated cache.
    private double powerUsedCache; // The total power used cache.
    
    private DecimalFormat formatter = new DecimalFormat("0.0");
    
    /**
     * Constructor
     *
     * @param proxy the UI proxy for the unit.
     * @param desktop the main desktop.
     */
    public PowerGridTabPanel(UnitUIProxy proxy, MainDesktopPane desktop) { 
        
        // Use the TabPanel constructor
        super("Power", null, "Power Grid", proxy, desktop);
        
        Settlement settlement = (Settlement) proxy.getUnit();
        
        // Prepare power grid label panel.
        JPanel powerGridLabelPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        topContentPanel.add(powerGridLabelPanel);
        
        // Prepare power grid label.
        JLabel powerGridLabel = new JLabel("Power Grid", JLabel.CENTER);
        powerGridLabelPanel.add(powerGridLabel);
        
        // Prepare power info panel.
        JPanel powerInfoPanel = new JPanel(new GridLayout(2, 1, 0, 0));
        powerInfoPanel.setBorder(new MarsPanelBorder());
        topContentPanel.add(powerInfoPanel);
        
        // Prepare power generated label.
        powerGeneratedCache = getTotalPowerGenerated();
        powerGeneratedLabel = new JLabel("Total Power Generated: " + 
            formatter.format(powerGeneratedCache) + " kW.", JLabel.CENTER);
        powerInfoPanel.add(powerGeneratedLabel);
        
        // Prepare power used label.
        powerUsedCache = getTotalPowerUsed();
        powerUsedLabel = new JLabel("Total Power Used: " + 
            formatter.format(powerUsedCache) + " kW.", JLabel.CENTER);
        powerInfoPanel.add(powerUsedLabel);
        
        // Prepare outer table panel.
        JPanel outerTablePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        outerTablePanel.setBorder(new MarsPanelBorder());
        topContentPanel.add(outerTablePanel);
        
        // Prepare power table panel.
        JPanel powerTablePanel = new JPanel(new BorderLayout(0, 0));
        outerTablePanel.add(powerTablePanel);
        
        // Prepare power table model.
        powerTableModel = new PowerTableModel(proxy);
        
        // Prepare power table.
        JTable powerTable = new JTable(powerTableModel);
        powerTable.setCellSelectionEnabled(false);
        powerTable.setDefaultRenderer(Double.class, new NumberCellRenderer());
        powerTable.setDefaultRenderer(JPanel.class, new JPanelCellRenderer());
        powerTable.getColumnModel().getColumn(0).setPreferredWidth(10);
        powerTable.getColumnModel().getColumn(1).setPreferredWidth(120);
        powerTable.getColumnModel().getColumn(2).setPreferredWidth(40);
        powerTable.getColumnModel().getColumn(3).setPreferredWidth(40);
        powerTablePanel.add(powerTable.getTableHeader(), BorderLayout.NORTH);
        powerTablePanel.add(powerTable, BorderLayout.CENTER);
    }
    
    /**
     * Updates the info on this panel.
     */
    public void update() {
        Settlement settlement = (Settlement) proxy.getUnit();
        
        // Update power generated label.
        if (powerGeneratedCache != getTotalPowerGenerated()) {
            powerGeneratedCache = getTotalPowerGenerated();
            powerGeneratedLabel.setText("Total Power Generated: " + 
                formatter.format(powerGeneratedCache) + " kW.");
        }
        
        // Update power used label.
        if (powerUsedCache != getTotalPowerUsed()) {
            powerUsedCache = getTotalPowerUsed();
            powerUsedLabel.setText("Total Power Used: " + 
                formatter.format(powerUsedCache) + " kW.");
        }
        
        // Update power table.
        powerTableModel.update();
    }
    
    /**
     * Gets the total power generated by all the buildings in the settlement.
     *
     * @return power generated in kW.
     */
    private double getTotalPowerGenerated() {
        
        double result = 0D;
        Settlement settlement = (Settlement) proxy.getUnit();
        Iterator i = settlement.getBuildingManager().getBuildings(PowerGeneration.class).iterator();
        while (i.hasNext()) {
            PowerGeneration generator = (PowerGeneration) i.next();
            result += generator.getGeneratedPower();
        }
        
        return result;
    }
    
    /**
     * Gets the total power used by all the buildings in the settlement.
     *
     * @return power used in kW.
     */
    private double getTotalPowerUsed() {
        
        double result = 0D;
        Settlement settlement = (Settlement) proxy.getUnit();
        Iterator i = settlement.getBuildingManager().getBuildings().iterator();
        while (i.hasNext()) {
            Building building = (Building) i.next();
            double powerUsed = 0D;
            if (building.getPowerMode().equals(Building.FULL_POWER)) 
                powerUsed = building.getFullPowerRequired();
            else if (building.getPowerMode().equals(Building.POWER_DOWN))
                powerUsed = building.getPoweredDownPowerRequired();
            result += powerUsed;
        }
        
        return result;
    }
    
    /** 
     * Internal class used as model for the power table.
     */
    private class PowerTableModel extends AbstractTableModel {
        
        Settlement settlement;
        java.util.List buildings;
        
        private PowerTableModel(UnitUIProxy proxy) {
            this.settlement = (Settlement) proxy.getUnit();
            buildings = settlement.getBuildingManager().getBuildings();
        }
        
        public int getRowCount() {
            return buildings.size();
        }
        
        public int getColumnCount() {
            return 4;
        }
        
        public Class getColumnClass(int columnIndex) {
            Class dataType = super.getColumnClass(columnIndex);
            if (columnIndex == 0) dataType = JPanel.class;
            else if (columnIndex == 1) dataType = String.class;
            else if (columnIndex == 2) dataType = Double.class;
            else if (columnIndex == 3) dataType = Double.class;
            return dataType;
        }
        
        public String getColumnName(int columnIndex) {
            if (columnIndex == 0) return "S";
            else if (columnIndex == 1) return "Building";
            else if (columnIndex == 2) return "Gen.";
            else if (columnIndex == 3) return "Used";
            else return "unknown";
        }
        
        public Object getValueAt(int row, int column) {
            Building building = (Building) buildings.get(row);
            String powerMode = building.getPowerMode();
            
            if (column == 0) {
                JPanel statusPanel = new JPanel();
                statusPanel.setOpaque(true);
                if (powerMode.equals(Building.FULL_POWER)) statusPanel.setBackground(Color.green);
                else if (powerMode.equals(Building.POWER_DOWN)) statusPanel.setBackground(Color.yellow);
                else if (powerMode.equals(Building.NO_POWER)) statusPanel.setBackground(Color.red);
                return statusPanel;
            }
            else if (column == 1) return buildings.get(row);
            else if (column == 2) {
                double generated = 0D;
                if (building instanceof PowerGeneration) 
                    generated = ((PowerGeneration) building).getGeneratedPower();
                return new Double(generated);
            }
            else if (column == 3) {
                double used = 0D;
                if (powerMode.equals(Building.FULL_POWER)) 
                    used = building.getFullPowerRequired();
                else if (powerMode.equals(Building.POWER_DOWN))
                    used = building.getPoweredDownPowerRequired();
                return new Double(used);
            }
            else return "unknown";
        }
  
        public void update() {
            if (!buildings.equals(settlement.getBuildingManager().getBuildings())) 
                buildings = settlement.getBuildingManager().getBuildings();
                
            fireTableDataChanged();
        }
    }
    
    /**
     * Internal JPanel cell renderer.
     */
    private class JPanelCellRenderer implements TableCellRenderer {
        
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                boolean hasFocus, int row, int column) {
            if (value instanceof JPanel) {
                JPanel panel = new JPanel(new BorderLayout());
                panel.setBorder(new EmptyBorder(2, 2, 2, 2));
                panel.setBackground(Color.white);
                panel.setOpaque(true);
                panel.add((JPanel) value, BorderLayout.CENTER);
                return panel;
            }
            else return null;
        }
    }
}
