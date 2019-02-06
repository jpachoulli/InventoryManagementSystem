/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import javafx.beans.property.*;
import View.AddPartController;


/**
 *
 * @author JJ_2
 */
public abstract class Part {
    AddPartController apc;
    
    public Part() {
        this(0, "", 0, 0.00, 0, 0);
    }
    
    public Part(int partId, String partName, int partInv, double partCost, int max, int min) {
        this.partID.setValue(partId);
        this.partName.set(partName);
        this.partCost.setValue(partCost);
        this.partInv.setValue(partInv);
        this.max.setValue(max);            
        this.min.setValue(min);
    } 

    // private encapsulates
    // public allows access through methods
    private SimpleStringProperty partName = new SimpleStringProperty("");
        public SimpleStringProperty partNameProperty = partName;

    private SimpleIntegerProperty partID = new SimpleIntegerProperty(0);
        public SimpleIntegerProperty partIdProperty = partID;
    
    private SimpleDoubleProperty partCost = new SimpleDoubleProperty(0.00);
        public SimpleDoubleProperty partCostProperty = partCost;    
    
    private SimpleIntegerProperty partInv = new SimpleIntegerProperty(0);
        public SimpleIntegerProperty partInvProperty = partInv;
    
    private SimpleIntegerProperty min = new SimpleIntegerProperty(0);
        public SimpleIntegerProperty minProperty = min;
    
    private SimpleIntegerProperty max = new SimpleIntegerProperty(0);
        public SimpleIntegerProperty maxProperty = max;
    
    // @return the partName
    public String getPartName() {
        return partName.getValue();
    }
    

    /**
     * Set partName
     * @param partName
     */
    public void setPartName(String partName) {
        partNameProperty.setValue(partName);
    }
    // define a getter for partNameProperty
    public SimpleStringProperty partNameProperty() {
        return partName;
    }
    
    // @return the partID
    public int getPartId() {
        return partID.getValue();
    }
    // @param partID the partID to set
    public void setPartId(int partId) {
        this.partID.setValue(partId);
    }
    // accesses the partId
    public SimpleIntegerProperty partIdProperty() {
        return partID;
    }
    
    
    // @return the partCost
    public double getPartCost() {
        return partCost.getValue();
    }
    // @param PartCost the partCost to set
    public void setPartCost(double partCost) {
        this.partCost.setValue(partCost);
    }
    // define a getter for partCostProperty
    public SimpleDoubleProperty partCostProperty() {
        return partCost;
    }
    
    
    // @return the value of partInv
    public int getPartInv() {
        return partInv.getValue();
    }
    // @param PartInv the partInv to set
    public void setPartInv(int partInv) {
        this.partInv.setValue(partInv);
    }
    // define a getter for partInvProperty
    public SimpleIntegerProperty partInvProperty() {
        return partInv;
    }
    
    // return the value of min
    public int getMin() {
        return min.getValue();
    }
    // set the value of min
    public void setMin(int min) {
        this.min.setValue(min);
    }
    // assign minProperty the value of min for use in jfx
    public SimpleIntegerProperty minProperty() {
        return min;
    }
    
    // return the value of max
    public int getMax() {
        return max.getValue();
    }
    // set the value of max
    public void setMax(int max) {
        this.max.setValue(max);
    }
    // assign maxProperty the value of max for use in jfx
    public SimpleIntegerProperty maxProperty() {
        return max;
    }
    
    
    
}
