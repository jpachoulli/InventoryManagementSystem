
/**
 * Sample Skeleton for 'MainScreen.fxml' Controller Class
 */

package View;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import Main.MainApp;
import Model.Inventory;
import Model.Part;
import Model.Product;


public class MainScreenController {
    
 // Table and column declarations for Part table.
// fx:id partTableMain
    @FXML
    private TableView<Part> partTableMain;
    
// fx:ids are respective...ie partIdColumnMain, partInvColumnMain, etc.
    @FXML
    private TableColumn<Part, Integer> partIdColumnMain, partInvColumnMain;
    
// fx:id partNameColumnMain;
    @FXML
    private TableColumn<Part, String> partNameColumnMain;
    
// fx:id partCostColumnMain
    @FXML
    private TableColumn<Part, Double> partCostColumnMain;

// Table and column declarations for Product table.
// fx:id productTableMain
    @FXML
    private TableView<Product> productTableMain;
    
// fx:ids are respective...ie productIdColumnMain, productInvColumnMain, etc.
    @FXML
    private TableColumn<Product, Integer> productIdColumnMain, productInvColumnMain;
    
// fx:id productNameColumnMain
    @FXML
    private TableColumn<Product, String> productNameColumnMain;
    
// fx:id productCostColumnMain
    @FXML
    private TableColumn<Product, Double> productCostColumnMain;
    
// fx:ids are respective...ie addPartButtonMain, modPartButtonMain, etc.
    @FXML
    private Button addPartButtonMain, modPartButtonMain, delPartButtonMain, searchPartButtonMain,
       addProductButtonMain, modProductButtonMain, delProductButtonMain, searchProductButtonMain,
            exitButton;
    
// fx:ids are respective...ie searchPartFieldMain, searchProductFieldMain, etc.
    @FXML
    private TextField searchPartFieldMain, searchProductFieldMain;

    private boolean okClicked = false;
    
// Reference to the main application...
    private MainApp mainApp;
    
    
    /**
     * The MainScreenController (MSC) no-argument Constructor
     * The constructor is called before the initialize() method.
     */
    public MainScreenController() {
    }
    
    /**
     * Initializes the controller class. This method is automatically called
     * after the FXML file has been loaded.
     */
    @FXML
    private void initialize() {
        partIdColumnMain.setCellValueFactory(cellData -> cellData.getValue().partIdProperty().asObject());
        partNameColumnMain.setCellValueFactory(cellData -> cellData.getValue().partNameProperty());
        partInvColumnMain.setCellValueFactory(cellData -> cellData.getValue().partInvProperty().asObject());
        partCostColumnMain.setCellValueFactory(cellData -> cellData.getValue().partCostProperty().asObject());
        partCostColumnMain.setCellFactory(col -> 
        {
            return new TableCell<Part, Double>() {
                @Override
                public void updateItem(Double partCost, boolean empty) {
                    super.updateItem(partCost, empty);
                    if (empty) {
                        setText(null);
                    } else {
                        setText(String.format("$%.2f", partCost));
                    }
                }
            };
        });
        
        
                
        productIdColumnMain.setCellValueFactory(cellData -> cellData.getValue().productIdProperty().asObject());
        productNameColumnMain.setCellValueFactory(cellData -> cellData.getValue().productNameProperty());
        productInvColumnMain.setCellValueFactory(cellData -> cellData.getValue().productInvProperty().asObject());
        productCostColumnMain.setCellValueFactory(cellData -> cellData.getValue().productCostProperty().asObject());
        productCostColumnMain.setCellFactory(col -> 
        {
            return new TableCell<Product, Double>() {
                @Override
                public void updateItem(Double partCost, boolean empty) {
                    super.updateItem(partCost, empty);
                    if (empty) {
                        setText(null);
                    } else {
                        setText(String.format("$%.2f", partCost));
                    }
                }
            };
        });

    }
//    setText(String.format("%0.2f", value.doubleValue()));
    /**
     * Is called by the main application to give reference to itself.
     * 
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
        // Add observable list data to the table
        partTableMain.setItems(Inventory.getAllParts());
        productTableMain.setItems(Inventory.getProducts());
    }
    
// onAction="#handleNewPart"
    @FXML
    private void handleNewPart() {
        okClicked = mainApp.showAddPartScreen();
    }
// onAction="#handleNewProduct"
    @FXML
    private void handleNewProduct() {
        okClicked = mainApp.showAddProductScreen();
    }
    
// onAction="#handleModPart"    
    @FXML
    private void handleModPart(ActionEvent e) {

        Inventory.selectedPart = partTableMain.getSelectionModel().getSelectedItem();
        Part selectedPart = Inventory.selectedPart;

        if(selectedPart != null) {
            
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Selection Confirmation");
            alert.setHeaderText("Please confirm the part you selected...");
            alert.setContentText("You have selected: \n\n"
                    + "Part ID: \t\t" + selectedPart.partIdProperty().getValue() + "\n"
                    + "Part Name: \t" + selectedPart.partNameProperty().getValueSafe() + "\n\n");            
            alert.showAndWait()
                    .filter(response -> response == ButtonType.OK)
                    .ifPresent(response -> mainApp.showModPartScreen());

            okClicked = true;
            
        } else {                                                          //                                       //
                // Nothing selected...                                                                                 
                Alert alert = new Alert(Alert.AlertType.WARNING);                                                      
                alert.setTitle("No selection");                                                                        
                alert.setHeaderText("No part selected");                                                               
                alert.setContentText("Please select a part in the table.");                                            
                alert.showAndWait()
                        .filter(response -> response == ButtonType.OK)
                        .ifPresent(response -> alert.close());
                
                okClicked = false;
        }
    }
     
    // onAction="#handleModProduct"    
    @FXML
    private void handleModProduct(ActionEvent e) {
        
        Inventory.selectedProduct = productTableMain.getSelectionModel().getSelectedItem();
        Product selectedProduct = Inventory.selectedProduct;

        if(selectedProduct != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Selection Confirmation");
            alert.setHeaderText("Please confirm the product you selected...");
            alert.setContentText("You have selected: \n\n"
                    + "Product ID: \t\t" + selectedProduct.productIdProperty().getValue() + "\n"
                    + "Product Name: \t" + selectedProduct.productNameProperty().getValueSafe() + "\n\n");
            alert.showAndWait()
                    .filter(response -> response == ButtonType.OK)
                    .ifPresent(response -> mainApp.showModProductScreen());
            okClicked = true;
        
        } else {                                                          //                                       //
                // Nothing selected...                                                                                 
                Alert alert = new Alert(Alert.AlertType.WARNING);                                                      
                alert.setTitle("No selection");                                                                        
                alert.setHeaderText("No products selected");                                                               
                alert.setContentText("Please select a product in the table.");                                            
                alert.showAndWait()
                        .filter(response -> response == ButtonType.OK)
                        .ifPresent(response -> alert.close());
                okClicked = false;
        }                                                                                                          
    }
    
        // fx:id foundMe
    @FXML
    private void foundMe() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Easter Egg!! (Illegal after 9/11)");
        alert.setHeaderText("Congratulations!!!");
        alert.setContentText("Congratulations!! You found my Easter Egg!!!  :) ");
        alert.showAndWait();
    }
    
    
    @FXML
    private void handleClose(ActionEvent e) {
        Platform.exit();        
    }
    
    /**
     * Called when the user clicks on the delete button.
     */
//------------------Working code, **** DO NOT DELETE **** -----------------------------------------------
    @FXML
    private void handleDelete() {
        
        String foundInProduct = "";
        Part selectedPart = partTableMain.getSelectionModel().getSelectedItem();
//        ObservableList<Product> productsList = FXCollections.observableArrayList();
        ObservableList<Part> partsList = FXCollections.observableArrayList();
        
            if(selectedPart != null) {
                if(!hasPart(selectedPart)) {
                    
                // Confirm deletion                                                                                    
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);                                                 
                    alert.setTitle("Confirm Deletion");                                                                    
                    alert.setHeaderText("Deleting...");                                                                    
                    alert.setContentText("WARNING!\t YOU ARE ABOUT TO DELETE A PART!!\n"
                            + "\t\t\t THIS CANNOT BE UNDONE!!\n\n"
                            + "Are you sure you want to delete the part? \n\n"
                            + "Part ID: \t\t" + selectedPart.partIdProperty().getValue() + "\n"
                            + "Part Name: \t" + selectedPart.partNameProperty().getValueSafe() + "\n\n\n");
                    alert.showAndWait()
                            .ifPresent(response -> {
                                if(response  ==  ButtonType.OK) {
                                    partTableMain.getItems().remove(selectedPart);
                                } else if(response  ==  ButtonType.CANCEL) {
                                    alert.close();
                                }
                            });      

                    // Update partTableMain                                                                                
                    partTableMain.setItems(Inventory.getAllParts());
//                } else {
//                    Alert alert = new Alert(Alert.AlertType.WARNING);
//                    alert.setTitle("Error Deleting Part");
//                    alert.setHeaderText("The Part Cannot Be Deleted!");
//                    alert.setContentText("The part is currently attached to products in inventory. \n\n"
//                                       + "The part cannot be deleted from inventory...");
//                    alert.showAndWait();
                }
            } else {
                // Nothing selected...                                                                                 
                Alert alert = new Alert(Alert.AlertType.WARNING);                                                      
                alert.setTitle("No selection");                                                                        
                alert.setHeaderText("No part selected");                                                               
                alert.setContentText("Please select a part in the table.");                                            
                alert.showAndWait();
        }                                                                                                          
    }
//------------------Working code, **** DO NOT DELETE **** -------------------------------------------------

     @FXML
    private void handleDeleteProd(ActionEvent e) {                                                                     
        
        Product selectedProduct = productTableMain.getSelectionModel().getSelectedItem();
                                                                                                                   
            if(selectedProduct != null) {                                                                                 
                // Confirm deletion                                                                                    
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);                                                 
                alert.setTitle("Confirm Deletion");                                                                    
                alert.setHeaderText("Deleting...");                                                                    
                alert.setContentText("WARNING!\t YOU ARE ABOUT TO DELETE A PRODUCT!! \n"
                        + "\t\t\t THIS CANNOT BE UNDONE!!\n\n"
                        +"Are you sure you want to delete the product? \n\n"
                        + "Part ID: \t\t" + selectedProduct.productIdProperty().getValue() + "\n"
                        + "Part Name: \t" + selectedProduct.productNameProperty().getValueSafe() + "\n\n");
                alert.showAndWait()
                        .ifPresent(response -> {
                            if(response  ==  ButtonType.OK) {
                                productTableMain.getItems().remove(selectedProduct);
                            } else if(response  ==  ButtonType.CANCEL) {
                                alert.close();
                            }
                        });

                // Update partTableMain                                                                                
                productTableMain.setItems(Inventory.getProducts());
                
            } else {                                                          //                                       //
                // Nothing selected...                                                                                 
                Alert alert = new Alert(Alert.AlertType.WARNING);                                                      
                alert.setTitle("No selection");                                                                        
                alert.setHeaderText("No product selected");                                                               
                alert.setContentText("Please select a product in the table.");                                            
                alert.showAndWait();
        }                                                                                                          
    }
    
    @FXML
    private void searchPartTable() {
        String searchItem = searchPartFieldMain.getText();
        if(isSearchInputValid(searchItem)) {        
            FilteredList<Part> searchPartResults = searchParts(searchItem);
            SortedList<Part> sortedParts = new SortedList<>(searchPartResults);
            sortedParts.comparatorProperty().bind(partTableMain.comparatorProperty());
            partTableMain.setItems(sortedParts);
            searchPartFieldMain.clear();
        }
    }
    private FilteredList<Part> searchParts (String s) {
        return Inventory.getAllParts().filtered(p -> p.getPartName().toLowerCase().contains(s.toLowerCase()));
    }
    
    
    @FXML
    void searchProductTable() {        
        String searchItem = searchProductFieldMain.getText();
        
        if(isSearchInputValid(searchItem)) {
            FilteredList<Product> searchProductResults = searchProducts(searchItem);
            SortedList<Product> sortedProducts = new SortedList<>(searchProductResults);
            sortedProducts.comparatorProperty().bind(productTableMain.comparatorProperty());
            productTableMain.setItems(sortedProducts);
        }
    }
    public FilteredList<Product> searchProducts (String s) {
        return Inventory.getProducts().filtered(p -> p.getProductName().contains(s.substring(0, 1).toUpperCase()
                                                                               + s.substring(1).toLowerCase()));
    } 
    
    // Working code, do not delete ********************* DO NOT DELETE *********************
    public boolean isSearchInputValid(String searchItem) {
        
        ObservableList<Part> list = FXCollections.observableArrayList(Inventory.getAllParts());
        
        String errorMessage = "";
        String partName;
        
        if(Inventory.getAllParts().isEmpty()) {
            errorMessage += "Inv: \t\t There are no parts in inventory to search. \n\n";
        }
        
        if(!searchItem.equalsIgnoreCase("")) {
            int count = 0;
            for(Part p:Inventory.getAllParts()) {
                String name = p.partNameProperty().getValueSafe();
                
                if(!searchItem.equalsIgnoreCase(name)  || searchItem.equals(null)) {
                    count++;
                    
                    if(count == Inventory.getAllParts().size()) {
                    errorMessage += "Inv: \t\t The search item, " + "\"" + searchItem + "\"" + " does not match \n" 
                            + "\t\t any known items in the inventory. \n\n"
                            + "\t\t The item cannot be found, or it does not exist. \n";
                    }
                } 
            }
        }
        
        if(errorMessage.length() == 0) {    
            return true;
        
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Data Error Exists");
            alert.setContentText(errorMessage);
            alert.showAndWait();
            
            return false;
        }
    }
    // Working code, do not delete ********************* DO NOT DELETE *********************   
    
    public boolean hasPart(Part selectedPart) {
        

        boolean hasPart = false;
        String searchPart = selectedPart.partNameProperty().get();
        String foundIn = "";
        String ownedBy = "";
        
        if(productTableMain.getItems() != null) {
            for(Product prod : productTableMain.getItems()) {
                for (Part p : prod.getPartsList()) {
                    if(p.getPartName().matches(searchPart)) {
                        foundIn += "Product: \t" + prod.productNameProperty().getValueSafe() + "\n";
                        
                        hasPart = true;
                    }
                }
            }
        }
        
        if(!foundIn.equalsIgnoreCase("")) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText("Cannot Delete Part!");
            alert.setContentText("The part: \n\n\'" + searchPart + "\'\n\n"
                            + "has been found attached to the following products in inventory... \n\n"
                            + foundIn + " \n\n"
                            + "Parts attached to products cannot be deleted from inventory.\n\n");
            alert.showAndWait();
        }
        
        return hasPart;    
    }
}