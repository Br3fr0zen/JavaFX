package ch.makery.address;
import java.io.File;

import java.io.IOException;
import java.util.prefs.Preferences;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;


import org.controlsfx.dialog.Dialogs;

import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import ch.makery.address.model.Person;
import ch.makery.address.model.PersonListWrapper;
import ch.makery.address.view.BirthdayStatisticsController;
import ch.makery.address.view.PersonEditDialogController;
import ch.makery.address.view.PersonOverviewController;
import ch.makery.address.view.RootLayoutController;
import insidefx.undecorator.Undecorator;

public class MainApp extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;
    private RootLayoutController rootController;
    
    
    /**
     * The data as an observable list of Persons.
     */
    private ObservableList<Person> personData = FXCollections.observableArrayList();

    /**
     * Constructor
     */
    public MainApp() {
        // Add some sample data
        personData.add(new Person("notenof", "mineral", "#FFFFFF", "2.jpg"));
        personData.add(new Person("jamon", "monja","#FFFFFF", "1.jpg"));
        personData.add(new Person("Heinz", "Kurz", "#FFFFFF", ""));
        personData.add(new Person("Cornelia", "Meier", "#FFFFFF", ""));
        personData.add(new Person("Werner", "Meyer", "#FFFFFF", ""));
        personData.add(new Person("Lydia", "Kunz","#FFFFFF", ""));
        personData.add(new Person("Anna", "Best","#FFFFFF", ""));
        personData.add(new Person("Stefan", "Meier","#FFFFFF", ""));
        personData.add(new Person("Martin", "Mueller","#FFFFFF", ""));
        personData.add(new Person("","","#FFFFFF", ""));
    }
    
    /**
     * Inicia los metodos initRootLayout y showPersonOverview
     * @see initRootLayout() showPersonOverview()
     */
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("AddressApp");
        this.primaryStage.getIcons().add(new Image("file:resources/images/address_book_32.png"));
        this.primaryStage.initStyle(StageStyle.UNDECORATED);
        
        
        
        initRootLayout();

        showPersonOverview();
    }
    
    /**
     * Inicia el root layout en la escena
     * @see rootLayoutController
     */
    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class
                    .getResource("view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();
         
          
            
           
          
          
          

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
           
            
        
      
            
            
            primaryStage.setScene(scene);

            // Give the controller access to the main app.
            rootController = loader.getController();
            rootController.setMainApp(this);

            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Try to load last opened person file.
        File file = getPersonFilePath();
        if (file != null) {
            loadPersonDataFromFile(file);
        }
    }

    /**
     * Enseña PersonOverview en la escena
     * @see PersonOverviewController
     */
    public void showPersonOverview() {
        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/PersonOverview.fxml"));
            AnchorPane personOverview = (AnchorPane) loader.load();
            
            //Animacion de face out al iniciar la pantalla
            FadeTransition ft = new FadeTransition(Duration.millis(700), personOverview);
            ft.setFromValue(0.0);
            ft.setToValue(1.0);
            ft.play();
            // Set person overview into the center of root layout.
            rootLayout.setCenter(personOverview);
            
            // Give the controller access to the main app.
            PersonOverviewController controller = loader.getController();
            controller.setMainApp(this);
            controller.setRootLayout(rootController);
            
          
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
   
    /**
     * Cambia PersonOverview por PersonEditDialog para poder editar a la persona 
     * que se a echo click pasandole la id de la persona
     * @param bid
     * @see PersonEditDialogController
     */
    public void cambiareditar(int bid){
    	
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/PersonEditDialog.fxml"));
            AnchorPane personOverview;
			try {
								
				personOverview = (AnchorPane) loader.load();
				//animacion de face out entre pantallas
				FadeTransition ft = new FadeTransition(Duration.millis(700), personOverview);
	            ft.setFromValue(0.0);
	            ft.setToValue(1.0);
	            ft.play();
				rootLayout.setCenter(personOverview);
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            
			
            PersonEditDialogController controller = loader.getController();
            controller.setMainApp(this);
            controller.setPerson(personData.get(bid));
            
            
       
    }
        
    /**
     * Returns the main stage.
     * @return
     */
    public Stage getPrimaryStage() {
    	return primaryStage;
    }
    
    /**
     * Returns the data as an observable list of Persons. 
     * @return
     */
    public ObservableList<Person> getPersonData() {
    	return personData;
    }
    
    /**
     * Returns the person file preference, i.e. the file that was last opened.
     * The preference is read from the OS specific registry. If no such
     * preference can be found, null is returned.
     * 
     * @return
     */
    public File getPersonFilePath() {
        Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
        String filePath = prefs.get("filePath", null);
        if (filePath != null) {
            return new File(filePath);
        } else {
            return null;
        }
    }

    /**
     * Sets the file path of the currently loaded file. The path is persisted in
     * the OS specific registry.
     * 
     * @param file the file or null to remove the path
     */
    public void setPersonFilePath(File file) {
        Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
        if (file != null) {
            prefs.put("filePath", file.getPath());

            // Update the stage title.
            primaryStage.setTitle("AddressApp - " + file.getName());
        } else {
            prefs.remove("filePath");

            // Update the stage title.
            primaryStage.setTitle("AddressApp");
        }
    }
    
    /**
     * Loads person data from the specified file. The current person data will
     * be replaced.
     * 
     * @param file
     */
    public void loadPersonDataFromFile(File file) {
        try {
            JAXBContext context = JAXBContext
                    .newInstance(PersonListWrapper.class);
            Unmarshaller um = context.createUnmarshaller();

            // Reading XML from the file and unmarshalling.
            PersonListWrapper wrapper = (PersonListWrapper) um.unmarshal(file);

            personData.clear();
            personData.addAll(wrapper.getPersons());

            // Save the file path to the registry.
            setPersonFilePath(file);

        } catch (Exception e) { // catches ANY exception
        	//PESAOOOOOOOOOOOOO
//            Dialogs.create()
//                    .title("Error")
//                    .masthead("Could not load data from file:\n" + file.getPath())
//                    .showException(e);
        }
    }
    
    /**
     * Saves the current person data to the specified file.
     * 
     * @param file
     */
    public void savePersonDataToFile(File file) {
        try {
            JAXBContext context = JAXBContext
                    .newInstance(PersonListWrapper.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            // Wrapping our person data.
            PersonListWrapper wrapper = new PersonListWrapper();
            wrapper.setPersons(personData);

            // Marshalling and saving XML to the file.
            m.marshal(wrapper, file);

            // Save the file path to the registry.
            setPersonFilePath(file);
        } catch (Exception e) { // catches ANY exception
            Dialogs.create().title("Error")
                    .masthead("Could not save data to file:\n" + file.getPath())
                    .showException(e);
        }
    }
    
 

    public static void main(String[] args) {
        launch(args);
    }
}