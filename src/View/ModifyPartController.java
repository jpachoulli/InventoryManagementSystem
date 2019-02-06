/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Main.MainApp;
import Model.InHouse;
import Model.Inventory;
import Model.Outsourced;
import Model.Part;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;

/**
 *
 * @author JJ_2
 */
public class ModifyPartController {
    private Part selectedPart = Inventory.selectedPart;
    
    private InHouse selPartIH;
    
    private Outsourced selPartOS;
    
    // References Main.MainApp
    private MainApp mainApp;
    // Creates the dialogueStage for the pop-up AddPartScreen
    public Stage dialogueStage = new Stage();
    
// Variable declaration for access
    // Creates boolean for use with methods.
    private boolean saveClicked = false;
    // Creates boolean for use with methods.
    private boolean okClicked = false;
    // List for holding original part data
    private ObservableList <Part> originalPartsList = FXCollections.observableArrayList();
    // List for holding replacement part data
    private ObservableList <Part> replacementPartsList = FXCollections.observableArrayList();

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    /*
    * Elements of the ModPart Screen
    */
    @FXML // fx:id="a"
    private Label a; // Value injected by FXMLLoader

    @FXML // fx:id="saveButtonMP"
    public Button saveButtonMP, cancelButtonMP; // Value injected by FXMLLoader

    @FXML // fx:id="radioInHouseMP"
    public RadioButton radioInHouseMP; // Value injected by FXMLLoader

    @FXML // fx:id="radioOutsrcMP"
    public RadioButton radioOutsourcedMP; // Value injected by FXMLLoader

    @FXML // fx:id="idFieldMP"
    public TextField partIdFieldMP; // Value injected by FXMLLoader

    @FXML // fx:id="partNameFieldMP"
    public TextField partNameFieldMP; // Value injected by FXMLLoader

    @FXML // fx:id="partInvFieldMP"
    public TextField partInvFieldMP; // Value injected by FXMLLoader

    @FXML // fx:id="partCostFieldMP"
    public TextField partCostFieldMP; // Value injected by FXMLLoader

    @FXML // fx:id="machIdField"
    public TextField machCompFieldMP; // Value injected by FXMLLoader

    @FXML // fx:id="maxFieldMP"
    public TextField maxFieldMP; // Value injected by FXMLLoader

    @FXML // fx:id="minFieldMP"
    public TextField minFieldMP; // Value injected by FXMLLoader

    @FXML // fx:id="saveBtnMP"
    public Button saveBtnMP; // Value injected by FXMLLoader

    @FXML // fx:id="cancelBtnMP"
    public Button cancelBtnMP; // Value injected by FXMLLoader

    @FXML // fx:id="labelMachIdMP"
    private Label labelMachCompMP; // Value injected by FXMLLoader

    // No argument constructor
    public ModifyPartController() {
    }
    
    /**
     * Initializes the controller class.  This method is automatically called 
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        setTextFields();        
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
    // References Main.MainApp
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
    // Accesses originalPartsList
    public ObservableList<Part> getOriginalPartsList() {
        return originalPartsList;
    }
    // Sets originalPartsList
    public void setOriginalPartsList(ObservableList<Part> partsList) {
        originalPartsList.setAll(partsList);
    }
    // Accesses replacementPartsList
    public ObservableList<Part> getReplacementPartsList() {
        return replacementPartsList;
    }
    // Sets replacementPartsList
    public void setReplacementPartsList(ObservableList<Part> partsList) {
        replacementPartsList.setAll(partsList);
    }
    
    public void setTextFields() {
                
        if(selectedPart instanceof InHouse) {

            radioInHouseMP.setSelected(true);
            labelMachCompMP.setText("Machine ID:");

            selectedPart = ((InHouse) selectedPart);

            partIdFieldMP.setText(Integer.toString(selectedPart.partIdProperty().get()));
            partNameFieldMP.setText(selectedPart.partNameProperty().getValueSafe());
            partInvFieldMP.setText(Integer.toString(selectedPart.partInvProperty().get()));
            partCostFieldMP.setText(Double.toString(selectedPart.partCostProperty().get()));
            maxFieldMP.setText(Integer.toString(selectedPart.maxProperty().get()));
            minFieldMP.setText(Integer.toString(selectedPart.minProperty().get()));
            machCompFieldMP.setText(Integer.toString(((InHouse) selectedPart).machineIdProperty().get()));
//            System.out.println("Yay, I'm in the InHouse Block!!!");

        } else if(selectedPart instanceof Outsourced){

            radioOutsourcedMP.setSelected(true);
            labelMachCompMP.setText("Company Name:");

            selectedPart = ((Outsourced) selectedPart);

            partIdFieldMP.setText(Integer.toString(selectedPart.partIdProperty().get()));
            partNameFieldMP.setText(selectedPart.partNameProperty().getValueSafe());
            partInvFieldMP.setText(Integer.toString(selectedPart.partInvProperty().get()));
            partCostFieldMP.setText(Double.toString(selectedPart.partCostProperty().get()));
            maxFieldMP.setText(Integer.toString(selectedPart.maxProperty().get()));
            minFieldMP.setText(Integer.toString(selectedPart.minProperty().get()));
            machCompFieldMP.setText(((Outsourced) selectedPart).companyNameProperty().getValueSafe());
//            System.out.println("Yay, I'm in the Outsourced Block!!!");

        }        
    }    
    
    @FXML
    public void handleSave() {
        
        if(isInputValid()) {
            
            if(radioInHouseMP.isSelected()) {                
                
                selPartIH = new InHouse();

                labelMachCompMP.setText("Machine ID:");
                
                selPartIH.setPartId(Integer.parseInt(partIdFieldMP.getText()));
                selPartIH.setPartName(partNameFieldMP.getText());
                selPartIH.setPartInv(Integer.parseInt(partInvFieldMP.getText()));
                selPartIH.setPartCost(Double.parseDouble(partCostFieldMP.getText()));
                selPartIH.setMax(Integer.parseInt(maxFieldMP.getText()));
                selPartIH.setMin(Integer.parseInt(minFieldMP.getText()));
                selPartIH.setMachineId(Integer.parseInt(machCompFieldMP.getText()));
                
                Inventory.getAllParts().remove(selectedPart);
                Inventory.getAllParts().add(selPartIH);
            }
            
            if(radioOutsourcedMP.isSelected()) {         
                selPartOS = new Outsourced();

                labelMachCompMP.setText("Company Name:");
                
                selPartOS.setPartId(Integer.parseInt(partIdFieldMP.getText()));
                selPartOS.setPartName(partNameFieldMP.getText());
                selPartOS.setPartInv(Integer.parseInt(partInvFieldMP.getText()));
                selPartOS.setPartCost(Double.parseDouble(partCostFieldMP.getText()));
                selPartOS.setMax(Integer.parseInt(maxFieldMP.getText()));
                selPartOS.setMin(Integer.parseInt(minFieldMP.getText()));
                selPartOS.setCompanyName(machCompFieldMP.getText().substring(0, 1).toUpperCase()
                                       + machCompFieldMP.getText().substring(1).toLowerCase());
                
                Inventory.getAllParts().remove(selectedPart);
                Inventory.getAllParts().add(selPartOS);

            }

            dialogueStage.close();
            
        }

    }
    
    /**
     * Called when the user clicks cancel.
     */
    @FXML
    private void handleCancel() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Canceling");
        alert.setHeaderText("Canceling Operation");
        alert.setContentText("You have chosen to cancel the \"Modify Part\" operation. \n\n"
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

    /**
     * Validates user input in the text fields.
     * 
     * @return true if the input is valid
     */
    private boolean isInputValid() {
        String errorMessage = "";
        
        if(partIdFieldMP.getText().isEmpty()) {
            errorMessage += "ID: No valid part ID!\n";
        }
        
        if(partNameFieldMP.getText().isEmpty() || partNameFieldMP.getText().matches("\\d+")) {
            errorMessage += "Name: \t\t\t No valid part name!\n"
                         + "\t\t\t\t Only letters are allowed. \n\n";
        }
        
        if(partInvFieldMP.getText().isEmpty() || !partInvFieldMP.getText().matches("\\d+")) {
            if(Integer.parseInt(partInvFieldMP.getText()) > Integer.parseInt(maxFieldMP.getText()) ||
                    Integer.parseInt(partInvFieldMP.getText()) < Integer.parseInt(minFieldMP.getText())) {
                        errorMessage += "Inv: \t\t\t\t No valid inventory level!\n"
                                            + "\t\t\t\t Inv cannot exceed Max."
                                            + "\t\t\t\t Inv cannot deceed Min."
                                            + "\t\t\t\t Only numbers are allowed. \n\n";
            }
        }
        
        if(partCostFieldMP.getText().isEmpty()) {
            if(!partCostFieldMP.getText().matches("\\d+\\.\\d+") ||
                    !partCostFieldMP.getText().matches("\\d+\\.\\d+\\d+")) {
            errorMessage += "Price/Cost: \t\t No valid cost figures!\n"
                        + "\t\t\t\t Only numbers are allowed. \n\n";
            }
        }
        
        if(maxFieldMP.getText().isEmpty() || !maxFieldMP.getText().matches("\\d+")) {
            if(Integer.parseInt(maxFieldMP.getText()) < Integer.parseInt(minFieldMP.getText())) {
                errorMessage += "Max: \t\t\t No valid maximum!\n"
                        + "\t\t\t\t Max may not deceed Min."
                        + "\t\t\t\t Only numbers are allowed. \n\n";
            }
        }
        
        if(minFieldMP.getText().isEmpty() || !minFieldMP.getText().matches("\\d+")) {
            if(Integer.parseInt(minFieldMP.getText()) > Integer.parseInt(maxFieldMP.getText())) {
                
                errorMessage += "Min: \t\t\t No valid minimum!\n"
                            + "Min may not exceed Max."
                            + "\t\t\t\t Only numbers are allowed. \n\n";
            }
        }
        if(radioInHouseMP.isSelected()) {
            if(machCompFieldMP.getText().isEmpty() || !machCompFieldMP.getText().matches("\\d+")) {
                errorMessage += "Machine ID: \t\t No valid machine id!\n"
                        + "\t\t\t\t Only numbers are allowed. \n\n";
            }
        } else if(radioOutsourcedMP.isSelected()) {
            if(machCompFieldMP.getText().isEmpty() || machCompFieldMP.getText().matches("^\\d+")) {
                errorMessage += "Company Name: \t No valid company name!\n"
                            + "\t\t\t\t Please begin Company Name with a letter. \n\n";
            }
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
    
    // do not touch this code, it works. delete comment upon project completion.
    @FXML                                                                   //**
    private void changeLabel() {                                            //**
                                                                            //**
        if(radioInHouseMP.isSelected()) {                                   //**
                                                                            //**
            labelMachCompMP.setText("Machine ID");                          //**
            machCompFieldMP.setPromptText("Input Machine ID");                          //**
                                                                            //**
        } else if(radioOutsourcedMP.isSelected()){                          //**
                                                                            //**
            labelMachCompMP.setText("Company Name");                        //**
            machCompFieldMP.setPromptText("Input Company Name");            //**
        }                                                                   //**
    }                                                                       //**
    // do not touch this code, it works. delete comment upon project completion.

}
