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
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

/**
 *
 * @author JJ_2
 */
public class ModifyProductController {
    
    private IntegerProperty partIdMP;
    private IntegerProperty partNumberMP;
    private StringProperty partNameMP;
    private IntegerProperty partInvMP;
    private IntegerProperty partCostMP;
    
        
    public ModifyProductController() {        
    }
    private MainApp mainApp;
    
    private Label productIdLbl, productNameLbl, productInvLbl, productCostLbl, maxLbl, minLbl;
    
    @FXML
    private TextField productIdField, productNameField, productInvField, productCostField, minField, maxField, searchFieldModProds;
    
    private final ObservableList <Part> originalPartsList = FXCollections.observableArrayList();
    private ObservableList <Part> newPartsList = FXCollections.observableArrayList();
    
    // Parts to choose from, to add to product
    // fx:id partTableAdd
    @FXML
    private TableView<Part> partTableAdd;   // Stores the found parts to add to the product.
    // Declaration for partID & partInv columns
    // The fx:id equivalents are their respective names.
    @FXML
    private TableColumn<Part, Integer> partIdColumnAdd, partInvColumnAdd;    // Holds the part ID and part Inv.
    // Declaration for partName column.
    // fx:id partNameColumnAdd
    @FXML
    private TableColumn<Part, String> partNameColumnAdd;   // Holds the part Name.
    
    // Declaration for partCost column.
    // fx:id partCostColumnAdd
    @FXML
    private TableColumn<Part, Double> partCostColumnAdd;   // Holds the manufacturing cost of specified part.
    
    // Parts to include in product, from partTableAdd.
    // fx:id partTableDel
    @FXML
    private TableView<Part> partTableDel;   // Stores the found parts to delete from the product.
    // Declaration for partID and partInv columns.
    @FXML
    private TableColumn<Part, Integer> partIdColumnDel, partInvColumnDel;    // Holds the part ID and part Inv.
    // Declaration for partName column.
    @FXML
    private TableColumn<Part, String> partNameColumnDel;   // Holds the part Name.
    // Declaration for partCost column.
    @FXML
    private TableColumn<Part, Double> partCostColumnDel;   // Holds the manufacturing cost of specified part.
    
    // Modify Product Screen buttons.
    // The fx:id equivalents are their respective names.
    @FXML
    private Button saveButtonModProds, cancelButtonModProds, delButtonModProds, addButtonModProds, searchButtonModProds;
    
    // Declaration of dialogueStage to hold pop-up windows.
    @FXML
    private Stage dialogueStage;
    
    // Returns true if save is clicked, otherwise returns false.
    @FXML
    private boolean saveClicked = false;
    
    // Returns true if add is clicked, otherwise returns false.
    @FXML
    private boolean addClicked = false;
    
    // Holds the selected part.
    private final Product selectedProduct = Inventory.selectedProduct;
    
    // Holds the cumulative cost of all parts in partTableDel,
    // which are ultimately attached to the newly created product.
    private double totalPartCost = 0.00;
    
    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        originalPartsList.addAll(selectedProduct.getPartsList());
    // Set text fields.
        setTextFields();        
    // Set the tables.
        setTables();
        
        for(Part p:partTableDel.getItems()) {
            totalPartCost += p.partCostProperty().doubleValue();
        }
        
        productCostField.setText(Double.toString(totalPartCost));
    }

    /**
     * Returns true if the user clicked save, otherwise returns false.
     * 
     * @return true/false
     */
    public boolean isSaveClicked() {
        return saveClicked;
    }
    
    /**
     * Returns true if the user clicked add, otherwise returns false.
     * 
     * @return true/false
     */
    public boolean isAddClicked() {
        return addClicked;
    }
    
    /**
     * Called when the user clicks Add.
     */
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
     * Sets the stage of this dialogue
     * 
     * @param dialogueStage 
     */
    @FXML
    public void setDialogueStage(Stage dialogueStage) {
        this.dialogueStage = dialogueStage;
    }
    
    // References Main.MainApp for pass-through access
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
    
    // Sets the text fields.
    public void setTextFields() {        
        productIdField.setText(Integer.toString(selectedProduct.productIdProperty().get()));
        productNameField.setText(selectedProduct.productNameProperty().getValueSafe());
        productInvField.setText(Integer.toString(selectedProduct.productInvProperty().get()));
        productCostField.setText(Double.toString(selectedProduct.productCostProperty().get()));
        maxField.setText(Integer.toString(selectedProduct.maxProperty().get()));
        minField.setText(Integer.toString(selectedProduct.minProperty().get()));
        
    }
    
    // Sets the tables (Top & Bottom)
    public void setTables() {
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
        partTableDel.setItems(selectedProduct.getPartsList());
        
        
    }
    
    // Saves the modified product to the inventory.
    @FXML
    public void handleSave() {
        
            if(isInputValid()) {  
                int productId = Integer.parseInt(productIdField.getText());
                String productName = productNameField.getText().substring(0, 1).toUpperCase()
                                   + productNameField.getText().substring(1).toLowerCase();            
                int productInv = Integer.parseInt(productInvField.getText());
                double productCost = Double.parseDouble(productCostField.getText());
                int min = Integer.parseInt(minField.getText());
                int max = Integer.parseInt(maxField.getText());

                Product originalProduct = new Product(productId, productName, productInv, productCost, max, min, newPartsList);

                originalProduct.productIdProperty.setValue(productId);
                originalProduct.productNameProperty.setValue(productName);
                originalProduct.productInvProperty.setValue(productInv);
                originalProduct.productCostProperty.setValue(productCost * 1.25);
                originalProduct.maxProperty.setValue(max);
                originalProduct.minProperty.setValue(min);
                originalProduct.setParts(partTableDel.getItems());

                Inventory.getProducts().add(originalProduct);                    
                dialogueStage.close();
            }
    }

    
    /**
     * Called when the user clicks cancel.
     */
    @FXML
    private void handleCancel() {
        
        int productId = Integer.parseInt(productIdField.getText());
        String productName = productNameField.getText().substring(0, 1).toUpperCase()
                           + productNameField.getText().substring(1).toLowerCase();            
        int productInv = Integer.parseInt(productInvField.getText());
        double productCost = Double.parseDouble(productCostField.getText());
        int min = Integer.parseInt(minField.getText());
        int max = Integer.parseInt(maxField.getText());

        selectedProduct.productIdProperty.setValue(productId);
        selectedProduct.productNameProperty.setValue(productName);
        selectedProduct.productInvProperty.setValue(productInv);
        selectedProduct.productCostProperty.setValue(productCost * 1.25);
        selectedProduct.maxProperty.setValue(max);
        selectedProduct.minProperty.setValue(min);
        selectedProduct.setParts(originalPartsList);
        
        partTableDel.getItems().clear();
        partTableDel.getItems().addAll(originalPartsList);
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Canceling");
        alert.setHeaderText("Canceling Operation");
        alert.setContentText("You have chosen to cancel the \"Modify Product\" operation. \n\n"
                            +"The changes made will be discarded and the original \n" 
                            +"values will remain unchanged, or will be restored. \n\n");
         alert.showAndWait().ifPresent(response -> {
            if(response == ButtonType.OK) {
                dialogueStage.close();
            } else if(response == ButtonType.CANCEL) {
                alert.close();
            }            
        });
        
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
                        + "\t\t\t\t Only numbers are allowed. \n\n";

            } else if(productInvField.getText().compareToIgnoreCase(maxField.getText()) > 0) {
                    errorMessage += "Inv: \t\t\t\t Inv cannot exceed Max. \n\n";
            
            } else if(productInvField.getText().compareToIgnoreCase(minField.getText()) < 0) {
                    errorMessage += "Inv: \t\t\t\t Inv cannot deceed Min. \n\n";  
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
    
    // Searches partTableAdd to requested parts.
    // Displays the result, alone, back in the same table.
    @FXML
    void handleSearch() {
        String searchItem = searchFieldModProds.getText();

        // Validate the search string input.
        if(isSearchInputValid(searchItem)) {
            FilteredList<Part> searchPartResults = searchParts(searchItem);
            SortedList<Part> sortedParts = new SortedList<>(searchPartResults);
            sortedParts.comparatorProperty().bind(partTableAdd.comparatorProperty());
            partTableAdd.setItems(sortedParts);
            searchFieldModProds.clear();
        }    
    }
    
    // Validates the search input.
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
    
    // FilteredList for holding the searchParts
    public FilteredList<Part> searchParts (String s) {
        return Inventory.getAllParts().filtered(p -> p.getPartName().toLowerCase().contains(s.toLowerCase()));
    }
    
   /**
     * Called when the user clicks on the delete button.
     */
    @FXML//------------------Working code, **** DO NOT DELETE **** -----------------------------------------------
    private void handleDelete() {                                                                     
        
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
                                totalPartCost -= selectedPart.partCostProperty().doubleValue();
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
    }   //------------------Working code, **** DO NOT DELETE **** -------------------------------------------------


}