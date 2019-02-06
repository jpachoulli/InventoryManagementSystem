package View;

import Main.MainApp;
import Model.InHouse;
import Model.Inventory;
import Model.Outsourced;
import Model.Part;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;


public class AddPartController {
    
    public AddPartController() {
        // No argument constructor
    }
    
    // References Main.MainApp
    private MainApp mainApp;
    // Creates the dialogueStage for the pop-up AddPartScreen
    public Stage dialogueStage = new Stage();
    // Variable declaration for access
    private Part part; //** IS ABSTRACT, CANNOT BE INSTANTIATED DIRECTLY ***
    // Creates boolean for handleSave() method
    private boolean saveClicked = false;
    // Creates boolean for launching AddPartScreen
    private boolean okClicked = false;
    // References MainScreenController for access
    private MainScreenController msc;
    // Stores the results of generatePartID() in the variable partID
    int partID = generatePartID();
    
    private int totalPartInv = 0;

    /*
    * Elements of the AddPartScreen
    */
    @FXML // fx:id="a"
    private Label a; // Value injected by FXMLLoader

    @FXML // fx:id="saveButtonAP"
    public Button saveButtonAP, cancelButtonAP; // Value injected by FXMLLoader

    @FXML // fx:id="radioInHouseAP"
    public RadioButton radioInHouseAP; // Value injected by FXMLLoader

    @FXML // fx:id="radioOutsrcAP"
    public RadioButton radioOutsourcedAP; // Value injected by FXMLLoader

    @FXML // fx:id="idFieldAP"
    public TextField partIdFieldAP; // Value injected by FXMLLoader

    @FXML // fx:id="partNameFieldAP"
    public TextField partNameFieldAP; // Value injected by FXMLLoader

    @FXML // fx:id="partInvFieldAP"
    public TextField partInvFieldAP; // Value injected by FXMLLoader

    @FXML // fx:id="partCostFieldAP"
    public TextField partCostFieldAP; // Value injected by FXMLLoader

    @FXML // fx:id="machIdField"
    public TextField machCompFieldAP; // Value injected by FXMLLoader

//    @FXML // fx:id="companyNameFieldAP"
//    private TextField companyNameFieldAP;

    @FXML // fx:id="maxFieldAP"
    public TextField maxFieldAP; // Value injected by FXMLLoader

    @FXML // fx:id="minFieldAP"
    public TextField minFieldAP; // Value injected by FXMLLoader

    @FXML // fx:id="saveBtnAP"
    public Button saveBtnAP; // Value injected by FXMLLoader

    @FXML // fx:id="cancelBtnAP"
    public Button cancelBtnAP; // Value injected by FXMLLoader

    @FXML // fx:id="labelMachIdAP"
    private Label labelMachCompAP; // Value injected by FXMLLoader

    
    
    /**
     * Initializes the controller class.  This method is automatically called 
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        // Initializes text field so it can be auto-populated
        // with the part number upon loading.
        partIdFieldAP.setText(Integer.toString(partID));
    }

    /**
     * Sets the stage of this dialogue
     * 
     * @param dialogueStage 
     */
    @FXML
    public void SetDialogueStage(Stage dialogueStage) {
        this.dialogueStage = dialogueStage;
    }
    // References Main.MainApp
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
    
    /**
     * for use in handleSave() method
     * 
     * @return
     */
    public boolean isSaveClicked() {
        
        return saveClicked;
        
    }
    
    public int generatePartID() {
        int partIdInt = 0;
        
        try { 
           if(Inventory.getAllParts().isEmpty()) {
               partIdInt = 1;
           } else {
               for(Part p : Inventory.getAllParts()) {
                   p.getPartId();
                   partIdInt = p.getPartId() + 1;
                   
               }
               return partIdInt;
           }
           
        } catch (NullPointerException e) {        
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error...");
            alert.setHeaderText("No Data Found.");
            alert.setContentText("No data could be found in the table.");
            alert.showAndWait();
            
            partIdInt = 1;
        }
//        int partIdInt = (int) (Math.random() * 50 + 1);   
        String partIdStr = String.valueOf(partIdInt);

        return partIdInt;
        
    }
        
    /**
     * Called when the user clicks 'Save'
     */
    @FXML
    private void handleSave() {
        
            if(isInputValid()) {
//                if(invSizeOK()) {                
                    int partId = Integer.parseInt(partIdFieldAP.getText());
                    String partName = partNameFieldAP.getText();
                    partName = partName.substring(0, 1).toUpperCase() + partName.substring(1).toLowerCase();
                    int partInv = Integer.parseInt(partInvFieldAP.getText());
                    double partCost = Double.parseDouble(partCostFieldAP.getText());
                    int min = Integer.parseInt(minFieldAP.getText());
                    int max = Integer.parseInt(maxFieldAP.getText());                

                    if(radioInHouseAP.isSelected()) {
                        int machineId = Integer.parseInt(machCompFieldAP.getText());
                        InHouse partIH = new InHouse(partId, partName, partInv, partCost, max, min, machineId);
                        part = partIH;
                        partIH.partIdProperty.setValue(partId);
                        partIH.partNameProperty.setValue(partName);
                        partIH.partInvProperty.setValue(partInv);
                        partIH.partCostProperty.setValue(partCost);
                        partIH.minProperty.setValue(min);
                        partIH.maxProperty.setValue(max);
                        partIH.machineIdProperty.setValue(machineId);

                        Inventory.getAllParts().add(partIH);

                    } else if(radioOutsourcedAP.isSelected()) {
                        String companyName = machCompFieldAP.getText().substring(0, 1).toUpperCase()
                                           + machCompFieldAP.getText().substring(1).toLowerCase();

                        Outsourced partOS = new Outsourced(partID, partName, partInv, partCost, min, max, companyName);
                        part = partOS;
                        partOS.partIdProperty.setValue(partId);
                        partOS.partNameProperty.setValue(partName);
                        partOS.partInvProperty.setValue(partInv);
                        partOS.partCostProperty.setValue(partCost);
                        partOS.minProperty.setValue(min);
                        partOS.maxProperty.setValue(max);
                        partOS.companyNameProperty.setValue(companyName);
                        Inventory.getAllParts().add(partOS);
                        
//                        dialogueStage.close();
                    }
//                }
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
        alert.setContentText("You have chosen to cancel the \"Add Part\" operation. \n"
                            +"No new parts will be created. \n\n");
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
        
        // NO check needed for partID as it's auto-generated.
        
        if(partNameFieldAP.getText().isEmpty() || partNameFieldAP.getText().matches("\\d+")) {
            errorMessage += "Name: \t\t\t No valid part name!\n"
                                 + "\t\t\t\t Only letters are allowed. \n\n";
            
        }
        
        if(partInvFieldAP.getText().isEmpty() || !partInvFieldAP.getText().matches("\\d+")) {
                    errorMessage += "Inv: \t\t\t\t No valid inventory level!\n"
                                    + "\t\t\t\t Only numbers are allowed, \n" 
                                    + "\t\t\t\t and Inv cannot be zero (0). \n\n";
                
            } else if(partInvFieldAP.getText().compareToIgnoreCase(maxFieldAP.getText()) >= 1) {
                    errorMessage += "Inv: \t\t\t\t Inv cannot exceed Max and \n"
                                 + "\t\t\t\t Max cannot be zero (0). \n\n";
                    
                    partInvFieldAP.clear();
            
            } else if(partInvFieldAP.getText().compareToIgnoreCase(minFieldAP.getText()) <= 0) {
                    errorMessage += "Inv: \t\t\t\t Inv cannot deceed or equal Min, \n" 
                                 + " \t\t\t\t and Min cannot be zero (0). \n\n";
                    
                    partInvFieldAP.clear();
        }
        
        if(partCostFieldAP .getText().isEmpty()) {
            if(!partCostFieldAP .getText().matches("\\d+\\.\\d+") ||
                    !partCostFieldAP .getText().matches("\\d+\\.\\d+\\d+")) {
            errorMessage += "Price/Cost: \t\t No valid cost figures!\n"
                        + "\t\t\t\t Only numbers are allowed. \n\n";
            }
        }
        
        if(maxFieldAP.getText().isEmpty() || !maxFieldAP.getText().matches("\\d+")
                || maxFieldAP.getText().contains("0")) {
                errorMessage += "Max: \t\t\t No valid maximum!\n"
                            + "\t\t\t\t Only numbers are allowed, \n"
                            + "\t\t\t\t and Max cannot be zero (0).\n\n";
                        
            } else if(maxFieldAP.getText().compareToIgnoreCase(minFieldAP.getText()) < 0) {
                errorMessage += "Max: \t\t\t Max may not deceed Min. \n\n";                        
        }
        
        if(minFieldAP.getText().isEmpty() || !maxFieldAP.getText().matches("\\d+")
                || minFieldAP.getText().contains("0")) {
                errorMessage += "Min: \t\t\t No valid minimum!\n"
                            + "\t\t\t\t Only numbers are allowed, \n"
                            + "\t\t\t\t and Max cannot be zero (0). \n\n";
         
            } else if(minFieldAP.getText().compareToIgnoreCase(maxFieldAP.getText()) > 0) {
                errorMessage += "Min: \t\t\t Min may not exceed Max. \n\n";
        }
        
        if(radioInHouseAP.isSelected()) {
            if(machCompFieldAP.getText().isEmpty() || !machCompFieldAP.getText().matches("\\d+")
                    || machCompFieldAP.getText().contains("0")) {
                errorMessage += "Machine ID: \t\t No valid machine id!\n"
                            + "\t\t\t\t Only numbers are allowed, \n"
                            + "\t\t\t\t and the Machine ID cannot be zero (0). \n\n";
            }
        } else if(radioOutsourcedAP.isSelected()) {
            if(machCompFieldAP.getText().isEmpty() || machCompFieldAP.getText().matches("^\\d+")) {
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
        if(radioInHouseAP.isSelected()) {                                   //**
                                                                            //**
            labelMachCompAP.setText("Machine ID");                          //**
            machCompFieldAP.setPromptText("Input Machine ID");                          //**
                                                                            //**
        } else if(radioOutsourcedAP.isSelected()){                          //**
                                                                            //**
            labelMachCompAP.setText("Company Name");                        //**
            machCompFieldAP.setPromptText("Input Company Name");            //**
        }                                                                   //**
    }                                                                       //**
    // do not touch this code, it works. delete comment upon project completion.
//    
//    private boolean invSizeOK() {
//        String errorMessage = "";
//        
//        
//        for(Part p:Inventory.getAllParts()) {
//            if(p.partInvProperty().get() > p.maxProperty.get()) {
//                errorMessage += "Inv: \t\t The part's Inv cannot exceed its Max, but has. \n"
//                            + "\t\t\t The difference is "
//                            + "\t\t\t" + (p.partInvProperty().get() - p.maxProperty().get()) + ". \n\n"
//                            + "\t\t\t Please correct this in order to proceed. \n\n";
//                return false;
//                
//            } else if(p.partInvProperty().get() < p.minProperty().get()) {
//                errorMessage += "Inv: \t\t The part's Inv cannot deceed its Min, but has. \n"
//                            + "\t\t\t The difference is "
//                            + "\t\t\t" + (p.minProperty().get() - p.partInvProperty().get()) + ". \n\n"
//                            + "\t\t\t Please correct this in order to proceed. \n\n";
//                return false;
//                
//            } else if(p.partInvProperty().get() > totalPartInv) {
//                errorMessage += "Inv: \t\t The part's Inv cannot exceed its total Inv, but has. \n"
//                        + "\t\t\t The difference is "
//                        +  "\t\t\t" + (totalPartInv - p.partInvProperty().get()) + ". \n\n"
//                        + "\t\t\t Please correct this in order to proceed. \n\n";
//                return false;
//            }
//                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
//                alert.setTitle("Error");
//                alert.setHeaderText("Inventory Error");
//                alert.setContentText(errorMessage);
//                alert.showAndWait()
//                        .ifPresent(response -> {
//                            if(response == ButtonType.OK) {
//                                alert.close();
//                            } else if(response == ButtonType.CANCEL) {
//                                dialogueStage.close();
//                            }
//                        });
//                
//        }
//        return true;
//    }
}
