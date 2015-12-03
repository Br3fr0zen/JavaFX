package ch.makery.address.view;

import javafx.collections.ObservableList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.OverrunStyle;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;

import java.io.IOException;

import org.controlsfx.dialog.Dialogs;

import com.jfoenix.controls.JFXButton;

import ch.makery.address.MainApp;
import ch.makery.address.model.Person;
import ch.makery.address.util.DateUtil;

public class PersonOverviewController {

	@FXML
	private AnchorPane PersonOverview;
	@FXML
	private TilePane tilepane;

	@FXML
	private Label firstNameLabel;
	@FXML
	private Label lastNameLabel;
	@FXML
	private Label streetLabel;
	@FXML
	private Label postalCodeLabel;
	@FXML
	private Label cityLabel;
	@FXML
	private Label birthdayLabel;

	// Reference to the main application.
	private MainApp mainApp;
	private RootLayoutController rootLayout;
	

	/**
	 * The constructor. The constructor is called before the initialize()
	 * method.
	 */
	public PersonOverviewController() {
	}

	/**
	 * Initializes the controller class. This method is automatically called
	 * after the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {

		// tilepane.getChildren().add(birthdayLabel);

		// ANTIGUO
		// Initialize the person table with the two columns.
		// firstNameColumn.setCellValueFactory(
		// cellData -> cellData.getValue().firstNameProperty());
		// lastNameColumn.setCellValueFactory(
		// cellData -> cellData.getValue().lastNameProperty());
		//
		// // Clear person details.
		// showPersonDetails(null);
		//
		// // Listen for selection changes and show the person details when
		// changed.
		// personTable.getSelectionModel().selectedItemProperty().addListener(
		// (observable, oldValue, newValue) -> showPersonDetails(newValue));
	}
	
	public void setRootLayout(RootLayoutController rootLayout) {
		this.rootLayout = rootLayout;

	}

	/**
	 * Is called by the main application to give a reference back to itself.
	 * 
	 * @param mainApp
	 */
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
		
		
		//
		// // Add observable list data to the table
		// personTable.setItems(mainApp.getPersonData());
		for (int i = 0; i < mainApp.getPersonData().size(); i++) {
			
			 
			JFXButton b = new JFXButton();
			  
			if (i != mainApp.getPersonData().size() - 1) {
				
				  ImageView imageView = new ImageView(
					      new Image(MainApp.class.getResourceAsStream("view/"+0+".jpg"))
					    );
				 Circle clip = new Circle(25, 25, 25);
				 
			        imageView.setClip(clip);
			        imageView.autosize();
				
				String nombre = mainApp.getPersonData().get(i).getFirstName() + " \n "
						+ mainApp.getPersonData().get(i).getLastName();
				String colortext = mainApp.getPersonData().get(i).getcolortext();
				
			         
				
				imageView.setFitWidth(50);
				imageView.setFitHeight(50);
			
				
				b.setMinHeight(150);
				b.setMinWidth(120);
				b.setMaxHeight(150);
				b.setMaxWidth(120);
				b.setText(nombre);
				b.setId(String.valueOf(i));
				b.setTextFill(Color.web(colortext));
				b.setGraphic(imageView);
				;
				
				b.setContentDisplay(ContentDisplay.TOP);
				
			} else {
				
				b.setText("+");
				b.setMinHeight(90);
				b.setMinWidth(90);
				b.setMaxHeight(90);
				b.setMinHeight(90);
				b.setStyle("-fx-background-radius: 100em; " + "-fx-font-size: 25pt; -fx-alignment: CENTER;");
				b.setId(String.valueOf(i));
				b.setTextFill(Color.WHITE);
				
				
				
				
				
			
			}

			b.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					 //Depurar id button
					//System.out.println(b.getId());
					
					if(rootLayout.deleteMode()){
						//EN CASO DE QUE NO SEA EL BOTON DE AÑADIR
						if(mainApp.getPersonData().size() - 1 != Integer.parseInt(b.getId())){
						mainApp.getPersonData().remove(Integer.parseInt(b.getId()));
						
						tilepane.getChildren().clear();
						
						setMainApp(mainApp);
						}
						
					}else{
					
					if (mainApp.getPersonData().size() - 1 == Integer.parseInt(b.getId())) {
						handleNewPerson();
						mainApp.cambiareditar(Integer.parseInt(b.getId()));
					} else {
						mainApp.cambiareditar(Integer.parseInt(b.getId()));
					}

				}
				
				}		
			});

			tilepane.getChildren().add(b);
		}
	}

	/**
	 * Fills all text fields to show details about the person. If the specified
	 * person is null, all text fields are cleared.
	 * 
	 * @param person
	 *            the person or null
	 */
	private void showPersonDetails(Person person) {
		if (person != null) {
			// Fill the labels with info from the person object.
			firstNameLabel.setText(person.getFirstName());
			lastNameLabel.setText(person.getLastName());
			streetLabel.setText(person.getStreet());
			postalCodeLabel.setText(Integer.toString(person.getPostalCode()));
			cityLabel.setText(person.getCity());
			birthdayLabel.setText(DateUtil.format(person.getBirthday()));
		} else {
			// Person is null, remove all the text.
			firstNameLabel.setText("");
			lastNameLabel.setText("");
			streetLabel.setText("");
			postalCodeLabel.setText("");
			cityLabel.setText("");
			birthdayLabel.setText("");
		}
	}


	/**
	 * Called when the user clicks the new button. Opens a dialog to edit
	 * details for a new person.
	 */
	@FXML
	private void handleNewPerson() {
		Person tempPerson = new Person("", "", "#ffffff");

		mainApp.getPersonData().add(tempPerson);
		
	}
	
}
