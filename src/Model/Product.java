/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author jbernsd
 */
public class Product {      
       
    public Product() {
        this(0, "", 0, 0.00, 0, 0, null);       
    }
    
    public Product(int productId, String productName, int productInv, double productCost, int max, int min, ObservableList<Part> partsList) {
               this.productId.setValue(productId);
               this.productName.setValue(productName);
               this.productInv.setValue(productInv);
               this.productCost.setValue(productCost);
               this.max.setValue(max);
               this.min.setValue(min);
               this.partsList.setAll(partsList);
           }

    // private encapsulates
    // public allows access through methods
    private SimpleStringProperty productName = new SimpleStringProperty("");
        public SimpleStringProperty productNameProperty = productName;

    private SimpleIntegerProperty productId = new SimpleIntegerProperty(0);
        public SimpleIntegerProperty productIdProperty = productId;
    
    private SimpleDoubleProperty productCost = new SimpleDoubleProperty(0.00);
        public SimpleDoubleProperty productCostProperty = productCost;    
    
    private SimpleIntegerProperty productInv = new SimpleIntegerProperty(0);
        public SimpleIntegerProperty productInvProperty = productInv;
    
    private SimpleIntegerProperty min = new SimpleIntegerProperty(0);
        public SimpleIntegerProperty minProperty = min;
    
    private SimpleIntegerProperty max = new SimpleIntegerProperty(0);
        public SimpleIntegerProperty maxProperty = max;
        
    private ObservableList<Part> partsList = FXCollections.observableArrayList();
    
// Set, Get, Property of productId
    public void setProductId(int productId) {
           this.productId.setValue(productId);
        }
    public int getProductId() {
           return productId.get();
        }
    public IntegerProperty productIdProperty() {
            return productId;
        }

// Set, Get, Property of productName
    public void setProductName(String productName) {
        this.productName.setValue(productName);
    }
    public String getProductName() {
        return productName.getValue();
    }
    public StringProperty productNameProperty() {
        return productName;
    }

// Set, Get, Property of productInv
    public void setProductInv(int productInv) {
        this.productInv.setValue(productInv);
    }
    public int getProductInv() {
        return productInv.getValue();
    }
    public IntegerProperty productInvProperty() {
        return productInv;
    }

// Set, Get, Property of productCost
    public void setProductCost(double productCost) {
        this.productCost.setValue(productCost);
    }
    public double getProductCost() {
        return productCost.getValue();
    }
    public DoubleProperty productCostProperty() {
        return productCost;
    }

// Set, Get, Property of max
    public void setMax(int max) {
        this.max.setValue(max);
    }
    
    public int getMax() {
        return max.getValue();
    }
    
    public IntegerProperty maxProperty() {
        return max;
    }
    

// Set, Get, Property of min
    public void setMin(int min) {
        this.min.setValue(min);
    }
    
    public int getMin() {
        return min.getValue();
    }
    
    public IntegerProperty minProperty() {
        return min;
    }
    
// Set, Get partsList
    public ObservableList<Part> getPartsList() {
        return partsList;
    }
    
    public void setParts(ObservableList<Part> partList) {
        partsList.addAll(partList);
    }
}