/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import javafx.beans.property.SimpleIntegerProperty;

/**
 *
 * @author JJ_2
 */
public class InHouse extends Part {
    
    private SimpleIntegerProperty machineId = new SimpleIntegerProperty(0);
    public SimpleIntegerProperty machineIdProperty = machineId;
    
    public InHouse() {
        this(0, "", 0, 0.00, 0, 0, 0);
    
    }
    
    public InHouse(int partId, String partName, int partInv, double partCost, int min, int max, int machId) {
        super(partId, partName, partInv, partCost, min, max);
        this.machineId.setValue(machId);
    }
    
    // Setting, Getting, and Property methods
    public int getMachineId() {
        return machineId.getValue();
    }

    public void setMachineId(int machineID) {
        this.machineId.setValue(machineID);
    }

    public SimpleIntegerProperty machineIdProperty() {
        return machineId;
    }
 
    public void showInHouse(InHouse part) {
        System.out.println("The machine ID of the part " + part.partNameProperty() + " , is: " + part.getMachineId());
    }
}