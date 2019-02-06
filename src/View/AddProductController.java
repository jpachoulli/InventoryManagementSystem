/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Main.MainApp;
import Model.Inventory;
import Model.Part;
import Model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

/**
 *
 * @author jbernsd
 */
public class AddProductController {
    int productID = generateProductID();
    
    Product draftProduct;
    
    Inventory inv;
    
    ObservableList<Part> partsList = FXCollections.observableArrayList();
    
    // References MainAPP.
    private MainApp mainApp;
    
    //  Creates the dialogueStage for the pop-up AddProduct screen.
    private Stage dialogueStage = new Stage();
    
    public AddProductController() {
        // No argument constructor
    }
   
    // Labels to know what the fields are.
    private Label productIdLbl, productNameLbl, productInvLbl, productCostLbl, maxLbl, minLbl;
    
    // Text fields for input.  Their respective fx:id equivalents are their names.
    @FXML
    private TextField productIdField, productNameField, productInvField, productCostField, minField, maxField, searchFieldAProd;

    // Parts imported from the main screen partTableMain.
    @FXML
    private TableView<Part> partTableAdd;
    
    // Table columns to show partID and PartInv. Their respective fx:id equivalents are their names.
    @FXML
    private TableColumn<Part, Integer> partIdColumnAdd, partInvColumnAdd;
    
    // Table column to show the partName. Its respective fx:id equivalent is its name.
    @FXML
    private TableColumn<Part, String> partNameColumnAdd;
    
    // Table column to show the partCost. Its respective fx:id equivalent is its name.
    @FXML
    private TableColumn<Part, Double> partCostColumnAdd;

    // Parts to be included in the creation of the Product.
    @FXML
    private TableView<Part> partTableDel;
    
    // Table columns to show the partID and partInv. Their respective fx:id equivalents are their names.
    @FXML
    private TableColumn<Part, Integer> partIdColumnDel, partInvColumnDel;
    
    // Table column to show the partName. Its fx:id equivalent is its name.
    @FXML
    private TableColumn<Part, String> partNameColumnDel;
    
    // Table column to show the partName.  Its fx:id equivalent is its name.
    @FXML
    private TableColumn<Part, Double> partCostColumnDel;
    
    // Add Product Screen buttons. The fx:id equivalents are their respective names.
    @FXML
    private Button saveButtonAProds, cancelButtonAProds, delButtonAProds, addButtonAProds, searchButtonAProds;
    // Boolean created for use with handleSave method.
    @FXML
    private boolean saveClicked = false;
    
    // Boolean created for use with handleAdd method.
    @FXML
    private final boolean addClicked = false;
    
    private ObservableList<Part> getPartsList() {
        return partsList;
    }
    
    // Reference for access to things such as isSearchInputValid, etc.
    private MainScreenController msc;
    
    // Holds the total of all part costs/prices.
    private double totalPartCost = 0.00;
    
    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {        
        // Initialize the table columns.
        setTables();
        // set product ID fieldusing generator
        productIdField.setText(Integer.toString(productID));
    }

    /**
     * Sets the stage of this dialogue.
     * 
     * @param dialogueStage
     */
    public void setDialogStage(Stage dialogueStage) {
        this.dialogueStage = dialogueStage;
    }
    
    // Returns true if the user clicks save, otherwise returns false.
    public boolean isSaveClicked() {
        return saveClicked;
    }
    
    // Returns true if the user clicks add, otherwise returns false.
    public boolean isAddClicked() {
        return addClicked;
    }

    // Sets the top and bottom tables.
    private void setTables() {
    // part table (top)        
        partIdColumnAdd.setCellValueFactory(cellData -> cellData.getValue().partIdProperty().asObject());
        partNameColumnAdd.setCellValueFactory(cellData -> cellData.getValue().partNameProperty());
        partInvColumnAdd.setCellValueFactory(cellData -> cellData.getValue().partInvProperty().asObject());
        partCostColumnAdd.setCellValueFactory(cellData -> cellData.getValue().partCostProperty().asObject());
        partCostColumnAdd.setCellFactory(col -> 
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
        partTableAdd.setItems(Inventory.getAllParts());
    
    // part table (bottom)
        partIdColumnDel.setCellValueFactory(cellData -> cellData.getValue().partIdProperty().asObject());
        partNameColumnDel.setCellValueFactory(cellData -> cellData.getValue().partNameProperty());
        partInvColumnDel.setCellValueFactory(cellData -> cellData.getValue().partInvProperty().asObject());
        partCostColumnDel.setCellValueFactory(cellData -> cellData.getValue().partCostProperty().asObject());
        partCostColumnDel.setCellFactory(col -> 
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
        partCostColumnDel.setCellValueFactory(cellData -> cellData.getValue().partCostProperty().asObject());
    }
    
    // Automatically generates the product id.
    public int generateProductID() {
        int productIdInt = 0;
        
        try { 
           if(Inventory.getProducts().isEmpty()) {
               productIdInt = 1;
           } else {
               for(Product p : Inventory.getProducts()) {
                   p.getProductId();
                   productIdInt = p.getProductId() + 1;
                   
               }
               return productIdInt;
           }
           
        } catch (NullPointerException e) {        
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error...");
            alert.setHeaderText("No Data Found.");
            alert.setContentText("No data could be found in the table.");
            alert.showAndWait();
            
            productIdInt = 1;
        }
//        int productIdInt = (int) (Math.random() * 50 + 1);   // generates psuedo-random ID number.

        return productIdInt;
        
    }
    
    // Validates the user input.
    private boolean isInputValid() {
        String errorMessage = "";
        
        // NO check needed for partID as it's auto-generated.
        
        if(productNameField.getText().isEmpty() || productNameField.getText().matches("\\d+")) {
            errorMessage += "Name: \t\t\t No valid product name!\n"
                         + "\t\t\t\t Only letters are allowed. \n\n";
        }
        
        if(productInvField.getText().isEmpty() || !productInvField.getText().matches("\\d+")) {

                    errorMessage += "Inv: \t\t\t\t No valid inventory level!\n"
                                            + "\t\t\t\t Only numbers are allowed, \n" 
                                            + "\t\t\t\t and Inv cannot be zero (0). \n\n";
                    
                    productInvField.clear();
                    
            } else if(productInvField.getText().compareToIgnoreCase(maxField.getText())  >= 1) {
                     errorMessage += "Inv: \t\t\t\t Inv cannot exceed Max and \n"
                                 + "\t\t\t\t Max cannot be zero (0). \n\n";
            
                     productInvField.clear();
                     
            } else if(productInvField.getText().compareToIgnoreCase(minField.getText()) <= 0) {
                    errorMessage += "Inv: \t\t\t\t Inv cannot deceed or equal Min, \n" 
                                 + " \t\t\t\t and Min cannot be zero (0). \n\n";
                    
                    productInvField.clear();
        }
        
        
        if(productCostField.getText().isEmpty()) {
            if(!productCostField.getText().matches("\\d+\\.\\d+") ||
                    !productCostField.getText().matches("\\d+\\.\\d+\\d+")) {
            errorMessage += "Price/Cost: \t\t No valid cost figures!\n"
                        + "\t\t\t\t Only numbers are allowed. \n\n";
            }
        }        
        
        if(maxField.getText().isEmpty() || !maxField.getText().matches("\\d+")) {
                errorMessage += "Max: \t\t\t No valid maximum!\n"
                        + "\t\t\t\t Only numbers are allowed. \n\n";
            
            } else if(maxField.getText().compareToIgnoreCase(minField.getText()) < 0) {
                errorMessage += "Max: \t\t\t Max may not deceed Min. \n\n";                        
        }
        
        if(minField.getText().isEmpty() || !minField.getText().matches("\\d+")) {
                errorMessage += "Min: \t\t\t No valid minimum!\n"
                        + "\t\t\t\t Only numbers are allowed. \n\n";
                
            } else if(minField.getText().compareToIgnoreCase(maxField.getText()) > 0) {
                errorMessage += "Min: \t\t\t Min may not exceed Max. \n\n";                        
        }
        
        if(partTableAdd.getItems().isEmpty()) {
            errorMessage += "Imported Parts: \t There are currently no parts imported. \n"
                    + "\t\t\t\t There must be parts imported to select\n"
                    + "\t\t\t\t for inclusion in a new product!\n\n";
        }
        
        if(partTableDel.getItems().isEmpty()) {
            errorMessage += "Product Parts: \t\t There are currently no parts to include. \n"
                    + "\t\t\t\t There must be parts selected to\n"
                    + "\t\t\t\t for inclusion to create a new product!\n\n";
        }
       
        if(errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogueStage);
            alert.setTitle("Empty or Invalid Fields");
            alert.setHeaderText("Please correct the empty or invalid fields");
            alert.setContentText(errorMessage);

            alert.show();

            return false;
        }
    }
    
    // Validates the search input
    private boolean isSearchInputValid(String searchItem) {
        
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
   
    // Searches partTableAdd(top table) for parts to add to partTableDel(bottom table)
    @FXML
    void handleSearch() {
        String searchItem = searchFieldAProd.getText();
//        boolean isSearchInputValid = msc.isSearchInputValid(searchItem);
        // Validate the search string input.
        if(isSearchInputValid(searchItem)) {
            // List to hold filtered search results.
            FilteredList<Part> searchPartResults = searchParts(searchItem);
            // List to hold sortedParts.
            SortedList<Part> sortedParts = new SortedList<>(searchPartResults);
            // Compares part to search item.
            sortedParts.comparatorProperty().bind(partTableAdd.comparatorProperty());
            // Sets the sorted part into partTableAdd.
            partTableAdd.setItems(sortedParts);
            // Clears the search field to reset the part stack,
            // and make ready for next search term without user effort.
            searchFieldAProd.clear();
        }    
    }
    
    // Return filtered list of parts to search
    public FilteredList<Part> searchParts (String s) {
        return Inventory.getAllParts().filtered(p -> p.getPartName().toLowerCase().contains(s.toLowerCase()));
    }
    
    // Called when the user clicks add.
    @FXML
    private void handleAdd() {
                 
         Part selectedPart = partTableAdd.getSelectionModel().getSelectedItem();
         if (selectedPart != null) {
             partTableDel.getItems().add(selectedPart);
             totalPartCost += selectedPart.partCostProperty().get();
             productCostField.setText(Double.toString(totalPartCost));
         }
        else {//part not selected
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText("No part is selected");
            alert.setContentText("Please select a part from the top table.");
            alert.showAndWait();
        }
     }
    
    /**
     * Called when the user clicks on the delete button.
     */
    @FXML
    private void handleDelete(ActionEvent e) {                                                                     
        
        Part selectedPart = partTableDel.getSelectionModel().getSelectedItem();
                                                                                                                   
        if(selectedPart != null) {                                                                                 
                // Confirm deletion                                                                                    
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);                                                 
                alert.setTitle("Confirm Deletion");                                                                    
                alert.setHeaderText("Deleting...");                                                                    
                alert.setContentText("Are you sure you want to delete the part: " + "\'" + selectedPart.getPartName() + "\'" + "?"); //
                alert.showAndWait()
                        .ifPresent(response -> {
                            if(response  ==  ButtonType.OK) {
                                totalPartCost -= selectedPart.partCostProperty().get();
                                partTableDel.getItems().remove(selectedPart);
                                productCostField.setText(Double.toString(totalPartCost));
                            } else if(response  ==  ButtonType.CANCEL) {
                                alert.close();
                            }
                        });
                // Update productTableMain                                                                                
                partTableDel.getItems();
                
            }else {                                                          //                                       //
                // Nothing selected...                                                                                 
                Alert alert = new Alert(Alert.AlertType.WARNING);                                                      
                alert.setTitle("No selection");                                                                        
                alert.setHeaderText("No product selected");                                                               
                alert.setContentText("Please select a product in the table.");                                            
                alert.showAndWait();
            }                                                                                                          
    }
       
    // Called when the user clicks save.
    @FXML
    private void handleSave() {
       if (isInputValid()) {
           
            int productId = Integer.parseInt(productIdField.getText());
            String productName = productNameField.getText().substring(0, 1).toUpperCase()
                               + productNameField.getText().substring(1).toLowerCase();            
            int productInv = Integer.parseInt(productInvField.getText());
            double productCost = Double.parseDouble(productCostField.getText());
            int min = Integer.parseInt(minField.getText());
            int max = Integer.parseInt(maxField.getText());
            
            Product newProduct = new Product(productId, productName, productInv, productCost, max, min, partsList);
            
            newProduct.productIdProperty.setValue(productId);
            newProduct.productNameProperty.setValue(productName);
            newProduct.productInvProperty.setValue(productInv);
            newProduct.productCostProperty.setValue(productCost * 1.25);
            newProduct.maxProperty.setValue(max);
            newProduct.minProperty.setValue(min);
            newProduct.setParts(partTableDel.getItems());
            
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Markup");
            alert.setHeaderText("Product Markup");
            alert.setContentText("A 25% markup will be added to all newly created products. \n"
                                +"This is mandatory to maintain a reasonable margin. \n\n");
            alert.showAndWait();
            
            Inventory.getProducts().addAll(newProduct);
            
            saveClicked = true;
            dialogueStage.close();
        }
    }
    
    // Called when the user clicks cancel.
    @FXML
    private void handleCancel() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Canceling");
        alert.setHeaderText("Canceling Operation");
        alert.setContentText("You have chosen to cancel the \"Add Product\" operation. \n"
                            +"No new products will be created. \n\n");
         alert.showAndWait().ifPresent(response -> {
            if(response == ButtonType.OK) {
                dialogueStage.close();
            } else if(response == ButtonType.CANCEL) {
                alert.close();
            }            
        });
    }
    
    // Provides pass-through reference to mainApp
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
}

    




