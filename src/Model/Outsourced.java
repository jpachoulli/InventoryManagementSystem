/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Alert;
/**
 *
 * @author JJ_2
 */
public class Outsourced extends Part {
    
    private SimpleStringProperty companyName = new SimpleStringProperty("");
        public SimpleStringProperty companyNameProperty = new SimpleStringProperty();
        
    public Outsourced() {
        this(0, "", 0, 0.00, 0, 0, "");
    }
    
    public Outsourced(int partId, String partName, int partInv, double partCost, int min, int max, String companyName) {        
        super(partId, partName, partInv, partCost, min, max);
        this.companyName.setValue(companyName);
    }
    
    public String getCompanyName() {
        return companyName.getValue();
    }

    public void setCompanyName(String companyName) {
        this.companyName.setValue(companyName);
    }
    
    public SimpleStringProperty companyNameProperty() {
        return companyName;
    }
    
    public void showOutsourced(Outsourced part) {
        
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Mfgr Info");
        alert.setHeaderText("Manufacturer Information");
        alert.setContentText("The company who mfgr'd part "
                + part.partNameProperty()
                + ", is "
                + part.getCompanyName());
        alert.showAndWait();
    }
    
}