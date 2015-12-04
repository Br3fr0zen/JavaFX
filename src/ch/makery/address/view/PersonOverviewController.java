package ch.makery.address.view;

import javafx.beans.property.StringProperty;
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
	
	
	private String avatar;
	
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
	}
	
	public void setRootLayout(RootLayoutController rootLayout) {
		this.rootLayout = rootLayout;

	}

	/**
	 * Crea un numero de botones dependiendo de la listade personas y les day una id segun el orden
	 * para pasarlo a PersonEditDialogController y poder editarlos.
	 * tambien añade el nombre yla imagen de la persona al boton y un EventLisener al boton para activar cambiareditar
	 * y pasarle la id del boton pulsado para poder coger la informacion de la persona pulsada.
	 * En caso de que el mdo borrar esteactivado el boton pulsado sera borrado junto a la persona al que pertenecia de la lista
	 * 
	 * @param mainApp
	 * @see MainApp PersonEditDialogController RootLayoutController
	 */
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
		
		
		//
		// // Add observable list data to the table
		// personTable.setItems(mainApp.getPersonData());
		for (int i = 0; i < mainApp.getPersonData().size(); i++) {
			
			 
			JFXButton b = new JFXButton();
			  
			/*
			 * Compuerba que no es el ultimo de la lista por que al final de esta
			 * se añadira un boton para añadir nuevas personas
			 */
			if (i != mainApp.getPersonData().size() - 1) {
				
				
				ImageView imageView = null;
				if(mainApp.getPersonData().get(i).getAvatar() == ""){
				   imageView = new ImageView(
					      new Image(MainApp.class.getResourceAsStream("view/0.png"))
					    );
				}else{
					imageView = new ImageView(
						      new Image(MainApp.class.getResourceAsStream("view/"+mainApp.getPersonData().get(i).getAvatar()))
						      );
				}
				//Circulo para dar forma circular ala imagen
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
				b.setStyle("-fx-background-radius: 100em; " + "-fx-font-size: 35pt; -fx-alignment: CENTER;");
				b.setId(String.valueOf(i));
				b.setTextFill(Color.WHITE);
				
				
				
				
				
			
			}

			b.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					 //syso para mostrar la id del boton que se ha pulsado
					//System.out.println(b.getId());
					
					if(rootLayout.deleteMode()){
						//Comprobacion para impedir que el boton de añadir sea borrado
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
	 * Creauna nuevo persona vacia y la añade a la lista de personas
	 */
	@FXML
	private void handleNewPerson() {
		Person tempPerson = new Person("", "", "#ffffff",null);

		mainApp.getPersonData().add(tempPerson);
		
	}
	
}
