/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import Main.MainApp;
/**
 *
 * @author JJ_2
 */
public class Inventory {
    
    private static ObservableList <Part> allParts = FXCollections.observableArrayList();
    private static ObservableList <Part> delParts = FXCollections.observableArrayList();
    private static ObservableList <Product> allProducts = FXCollections.observableArrayList();
    
    public MainApp mainApp;
    
    public static Part selectedPart;
    
    public static Product selectedProduct;
    
      

    public Inventory () {
        this(0, "", 0, 0.00);
    }
    public Inventory(int partId, String partName, int partInv, double partCost) {
        
        // Add some sample Part data
    allParts.add(new Outsourced(01, "Washer 1/2 in.", 12, 0.35, 1, 100,"Acme Hardware Co."));
    allParts.add(new Outsourced(02,"Bolt 1/2 in.", 12, 0.45, 1, 100, "Acme Hardware Co."));
    allParts.add(new InHouse(03, "Bolt 3/4 in.", 12, 0.24, 1, 100, 8675));
    allParts.add(new InHouse(04, "Washer 3/4 in.", 12, 0.10, 1, 100, 2349));

//    // Add some sample Product data
//    allProducts.add(new Product(01, "Silly Goose", 12, 1.00, 1, 100));
//    allProducts.add(new Product(02, "Goofy Mutt", 2, 5.00, 1, 100)); 
//    allProducts.add(new Product(03, "Stupid Cat",3, 3.00, 1, 100));
//    allProducts.add(new Product(04, "Stinky Ferret", 4, 2.00, 1, 100));
 
    }
    
    public void addProduct(Product newProduct) {
        // Insert functional code here
    }

    public boolean removeProduct(int productId) {
        boolean result = false;
        // Insert functional code here
        return result;
    }
    
    public Product lookupProduct(int productId) {
        int lookup = productId;
        Product found = null;
        // Insert functional code here
        return found;
    }
    
    public void updateProduct(int productId) {
        // Insert functional code here
    }
    
    public void showInventoryLvl(Inventory inv) {
        System.out.print("The inventory level for this item is at " + inv + " items.");
    }
    
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
    
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }
    
    public static ObservableList<Part> getDelParts() {
        return delParts;
    }
    
    public static ObservableList<Product> getProducts() {
        return allProducts;
    }
    
    public void setAllParts(ObservableList<Part> allParts) {
        this.allParts = allParts;
    }
    
    public void setDelParts(ObservableList<Part> delParts) {
        this.delParts = delParts;
    }
    
    public void setProducts(ObservableList<Product> products) {
        this.allProducts = products;
    }
    
    
}
