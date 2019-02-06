package Main; /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

 import java.io.IOException;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import Model.InHouse;
import Model.Inventory;
import Model.Outsourced;
import Model.Part;
import Model.Product;
import View.AddPartController;
import View.AddProductController;
import View.MainScreenController;
import View.ModifyPartController;
import View.ModifyProductController;


/**
 *
 * @author jbernsd
 */

// This is where the loaders, stages, and scenes are created.
// The loaders call the .fxml files for display rules.
// The stages are loaded into their panes, and their scenes are set.
// Methods are tried and exceptions caught.
// The root layout, as well as the rest of the screens, are configued
// to load here, and passed to their respective controllers.
//
// This Main.MainApp class must be referenced here, as well as in the other screens,
// to provide access to this class' methods.
// For example:
//   private void setMainApp(Main.MainApp mainApp) {
//        this.mainApp = mainApp;
//    }
// Is how the reference should be set in all controller screens.

public class MainApp extends Application {
    private final ObservableList<Part> PartData = FXCollections.observableArrayList();
    private final ObservableList<Product> ProductData = FXCollections.observableArrayList();
    private final ObservableList<Part> samplePartData = FXCollections.observableArrayList(
        new InHouse(1, "Washer", 100, 0.35, 100, 10, 1234),
        new InHouse(2, "Bolt", 124, 0.45, 124, 10, 1234),
        new InHouse(3, "Nut", 137, 0.45, 137, 10, 1234),
        new Outsourced(4, "Flange", 94, 0.77, 94, 10, "Acme Hardware"),
        new Outsourced(5, "Cotter Pin", 87, 0.20, 87, 10, "Acme Hardware"),
        new Outsourced(6, "Lock Washer", 72, 0.40, 72, 10, "Acme Hardware")
    );
    
    private final ObservableList<Product> sampleProductData = FXCollections.observableArrayList(
        new Product(1, "Carriage", 100, 1.47, 100, 1, samplePartData),
        new Product(2, "Under Carriage", 140, 1.99, 140, 1, samplePartData),
        new Product(3, "Lamp", 114, 1.12, 114, 1, samplePartData),
        new Product(4, "Table", 123, 1.17, 123, 1, samplePartData)
    );
    private Stage primaryStage;
    private BorderPane rootLayout;    
    private MainApp mainApp;    
    private AddPartController apc;    
    private MainScreenController msc;    
    private ModifyPartController mpc;
    
    public MainApp() {
        
       // Add some sample data
       /* To enable, simply uncomment.
        * A re-import of jbernsd_IMS.Model.Inventory
        * may be necessary.
        */
        Inventory.getAllParts().addAll(samplePartData);
        Inventory.getProducts().addAll(sampleProductData);
    }    
    
    // Start method.
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("JBernsd_IMS™ - Ɏǣĥ Ɓąƥŷ!!");

        initRootLayout();
        showMainScreen();        
    }
    
    /**
     * Initializes the root layout.
     */
    public void initRootLayout() {
        try {
             // Create a new FXML Loader.
            FXMLLoader loader = new FXMLLoader();
            // Load rootLayout from fxml file.
            loader.setLocation(MainApp.class.getResource("View/RootLayout.fxml"));
            // Load  the RootLayout fxml file into the BorderPane.
            rootLayout = (BorderPane) loader.load();

            // Create the scene for the AnchorPane, 'rootLayout'.
            Scene scene = new Scene(rootLayout);
            // Set the scene for primaryStage.
            primaryStage.setScene(scene);
            // Show the stage.
            primaryStage.show();            
             
        } catch (IOException e) {
            // If it fails, print the stackTrace.
            e.printStackTrace();
        }
    }
    
    /*
    * setMainApp provides pass-through access to the
    * main app from the controllers
    */
    private void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
    
    /*
    * Sets the dialogueStage for the other screens inside the root layout. 
    */
    public void setDialogueStage(Stage dialogueStage) {
        // Set primaryStage to dialogueStage
        this.primaryStage = dialogueStage;
        // Show the stage and wait for user input.
        dialogueStage.showAndWait();
    }
    
    /**
     * Shows the mainScreen inside the root layout.
     */
    public void showMainScreen() {
        try {
            // Create a new FXML Loader.
            FXMLLoader loader = new FXMLLoader();
            // Load MainScreen from fxml file.
            loader.setLocation(MainApp.class.getResource("View/MainScreen.fxml"));
            // Load the MainScreen fxml file into the AnchorPane.
            AnchorPane mainScreen = (AnchorPane) loader.load();
            
            // Set main screen in center of scene.
            rootLayout.setCenter(mainScreen);
            
            // Give the controller access to the scene.
            MainScreenController controller = loader.getController();
            // Set MainScreen as Main.MainApp.
            controller.setMainApp(this);

        } catch (IOException e) {
            // If it fails, print the stackTrace.
            e.printStackTrace();
        }
    }

    /**
     * Shows the addPartScreen inside the root layout.
     * @return returns the screen if true, otherwise throws an exception.
     */
    public boolean showAddPartScreen() {
        try {            
            // Create a new FXML Loader.
            FXMLLoader loader = new FXMLLoader();
            // Load AddPart from fxml file.
            loader.setLocation(MainApp.class.getResource("View/AddPart.fxml"));
            // Load the AddPart fxml file into the AnchorPane.
            AnchorPane addPartScreen = (AnchorPane) loader.load();
            
            
            // Give the controller access to the main app.
            AddPartController controller01 = loader.getController();
            // Set AddPartScreen as Main.MainApp.
            controller01.setMainApp(this);
            // Create the dialogue stage.
            Stage dialogueStage = new Stage();
            // Set the stage title.
            dialogueStage.setTitle("Add New Part    ...if you wanna keep your job...");
            // Set the window modality. (Disallows input to other windows)
            dialogueStage.initModality(Modality.WINDOW_MODAL);
            // Set primaryStage as the owner of dialogueStage. (Links them)
            dialogueStage.initOwner(primaryStage);
            // Create the scene for the AnchorPane, 'addPartScreen'.
            Scene sceneAP = new Scene(addPartScreen);
            // Set the scene for dialogueStage.
            dialogueStage.setScene(sceneAP);
            
            // Give the controller access to the AddPart screen
            AddPartController controller02 = loader.getController();
            // Sets the stage for controller02.
            controller02.SetDialogueStage(dialogueStage);
            // Sets AddPartScreen as Main.MainApp.
            controller02.setMainApp(this);
            // Show the stage and wait for user input.
            dialogueStage.showAndWait();
            // return true if the save button is clicked.
            return controller01.isSaveClicked();
            
            } catch (IOException e) {
                e.printStackTrace();
                
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Error...");
                alert.setHeaderText("Error loading screen...");
                alert.setContentText("There was an error loading the screen.\n"
                                    + "Check the Stack Trace for details...");
                return false;
            }
        }
    
    /*
    * Shows the modPartScreen inside the root layout. 
    */
    public boolean showModPartScreen() {
        try {            
        // Create a new FXML Loader.
        FXMLLoader loader = new FXMLLoader();
        // Load ModifyPart from fxml file.
        loader.setLocation(MainApp.class.getResource("View/ModifyPart.fxml"));
        // Load the ModifyPart fxml file into the AnchorPane.
        AnchorPane modifyPartScreen = (AnchorPane) loader.load();
        
        // Give the controller access to the main app.
        ModifyPartController controller01 = loader.getController();
        // Set ModifyPartScreen as Main.MainApp.
        controller01.setMainApp(this);
        // Create the dialogue stage.
        Stage dialogueStage = new Stage();
        
        // Set the stage title.
        dialogueStage.setTitle("Modify Existing Part    ...cuz you didn't do it right in the first place...");
        // Set the window modality. (Disallows input to other windows)
        dialogueStage.initModality(Modality.WINDOW_MODAL);
        // Set primaryStage as the owner of dialogueStage. (Links them)
        dialogueStage.initOwner(primaryStage);
        // Create the scene for the AnchorPane, 'modifyPartScreen'.
        Scene sceneAP = new Scene(modifyPartScreen);
        // Set the scene for dialogueStage.
        dialogueStage.setScene(sceneAP);

        // Give the controller access to the ModifyPart screen
        ModifyPartController controller02 = loader.getController();
        // Sets the stage for controller02.
        controller02.setDialogueStage(dialogueStage);
        // Sets ModifyPartScreen as Main.MainApp.
        controller02.setMainApp(this);
        // Show the stage and wait for user input.
        dialogueStage.showAndWait();
        // return true if the save button is clicked.

        } catch (IOException e) {
            e.printStackTrace();
            
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Error...");
                alert.setHeaderText("Error loading screen...");
                alert.setContentText("There was an error loading the screen.\n"
                                    + "Check the Stack Trace for details...");
            return false;
        }

        return true;
    }
    
    /*
    * Shows the addProductScreen inside the root layout. 
    */    
    public boolean showAddProductScreen() {
        
       try {            
            // Create a new FXML Loader.
            FXMLLoader loader = new FXMLLoader();
            // Load AddProduct from fxml file.
            loader.setLocation(MainApp.class.getResource("View/AddProduct.fxml"));
            // Load the AddProduct fxml file into the AnchorPane.
            AnchorPane addProductScreen = (AnchorPane) loader.load();
            
            
            // Give the controller access to the main app.
            AddProductController controller01 = loader.getController();
            // Set AddProductScreen as Main.MainApp.
            controller01.setMainApp(this);
            // Create the dialogue stage.
            Stage dialogueStage = new Stage();
            // Set the stage title.
            dialogueStage.setTitle("Add New Product    ...if you wanna keep your job...");
            // Set the window modality. (Disallows input to other windows)
            dialogueStage.initModality(Modality.WINDOW_MODAL);
            // Set primaryStage as the owner of dialogueStage. (Links them)
            dialogueStage.initOwner(primaryStage);
            // Create the scene for the AnchorPane, 'addProductScreen'.
            Scene sceneAProd = new Scene(addProductScreen);
            // Set the scene for dialogueStage.
            dialogueStage.setScene(sceneAProd);
            
            // Give the controller access to the AddProduct screen
            AddProductController controller02 = loader.getController();
            // Sets the stage for controller02.
            controller02.setDialogStage(dialogueStage);
            // Sets AddProductScreen as Main.MainApp.
            controller02.setMainApp(this);
            // Show the stage and wait for user input.
            dialogueStage.showAndWait();
            // return true if the save button is clicked.
            return controller01.isSaveClicked();
            
            } catch (IOException e) {
                e.printStackTrace();
                
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Error...");
                alert.setHeaderText("Error loading screen...");
                alert.setContentText("There was an error loading the screen.\n"
                                    + "Check the Stack Trace for details...");
                return false;
            }
    }
    
    /*
    * Shows the modProductScreen inside the root layout. 
    */
    public boolean showModProductScreen() {
        try {            
        // Create a new FXML Loader.
        FXMLLoader loader = new FXMLLoader();
        // Load ModifyProduct from fxml file.
        loader.setLocation(MainApp.class.getResource("View/ModifyProduct.fxml"));
        // Load the ModifyProduct fxml file into the AnchorPane.
        AnchorPane modifyProductScreen = (AnchorPane) loader.load();



        // Give the controller access to the main app.
        ModifyProductController controller01 = loader.getController();
        // Set ModifyProductScreen as Main.MainApp.
        controller01.setMainApp(this);
        // Create the dialogue stage.
        Stage dialogueStage = new Stage();
        // Set the stage title.
        dialogueStage.setTitle("Modify Existing Product    ...cuz you didn't do it right in the first place...");
        // Set the window modality. (Disallows input to other windows)
        dialogueStage.initModality(Modality.WINDOW_MODAL);
        // Set primaryStage as the owner of dialogueStage. (Links them)
        dialogueStage.initOwner(primaryStage);
        // Create the scene for the AnchorPane, 'modifyProductScreen'.
        Scene sceneAP = new Scene(modifyProductScreen);
        // Set the scene for dialogueStage.
        dialogueStage.setScene(sceneAP);

        // Give the controller access to the ModifyProduct screen
        ModifyProductController controller02 = loader.getController();
        // Sets the stage for controller02.
        controller02.setDialogueStage(dialogueStage);
        // Sets ModifyProductScreen as Main.MainApp.
        controller02.setMainApp(this);
        // Show the stage and wait for user input.
        dialogueStage.showAndWait();
        // return true if the save button is clicked.

        } catch (IOException e) {
            e.printStackTrace();
            
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Error...");
                alert.setHeaderText("Error loading screen...");
                alert.setContentText("There was an error loading the screen.\n"
                                    + "Check the Stack Trace for details...");
            return false;
        }

        return true;
    }
    
    /**
     * Returns the main stage.
     * @return returns the primary stage.
     */
    public Stage getPrimaryStage() {
            return primaryStage;
    } 
    
    /**
     * Returns the data as an observable list of Parts.
     * @return
     */
    public ObservableList<Part> getPartData() {
        return PartData;
    }
    
    /**
     * Returns the data as an observable list of Parts.
     * @return
     */
    public ObservableList<Product> getProductData() {
        return ProductData;
    }
    
}