package controller;

import model.Place;
import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import service.ExportCSVService;
import service.SearchingService;

/**
 *
 * @author Md. Rajib Hossain
 */
public class FXMLSearchController implements Initializable {

    private Stage stage;

    @FXML
    private AnchorPane anchor;
    @FXML
    private Button search;
    @FXML
    private TextField searchTxt;
    @FXML
    private TableView<Place> placeTable;
    @FXML
    private TableColumn<Place, String> name;
    @FXML
    private TableColumn<Place, String> address;
    @FXML
    private TableColumn<Place, String> city;
    @FXML
    private TableColumn<Place, String> state;
    @FXML
    private TableColumn<Place, String> zipcode;
    @FXML
    private Button exprtbtn;

    private SearchingService ss;
    private Alert a;
    private List<Place> list;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        address.setCellValueFactory(new PropertyValueFactory<>("address"));
        city.setCellValueFactory(new PropertyValueFactory<>("city"));
        state.setCellValueFactory(new PropertyValueFactory<>("state"));
        zipcode.setCellValueFactory(new PropertyValueFactory<>("zipCode"));
    }

    @FXML
    private void searchbtnAction(ActionEvent event) {
        
         placeTable.setItems(null);
        
        String query = searchTxt.getText();

        if ("".equals(query) || null==query) {
            a = new Alert(AlertType.ERROR);
            a.setHeaderText("Search");
            a.setContentText("Search bar cann't be empty!");
            a.show();
            return;
        }
       
        search(query);
        searchTxt.setText(null);
    }

    @FXML
    private void keyReleaseSearchAction(KeyEvent event) {

        String query = searchTxt.getText();
        if (0 < query.length() == true) {
            search(query);
        } else {
            placeTable.setItems(null);
        }
    }

    @FXML
    private void exportbtnAction(ActionEvent event) {

        stage = (Stage) anchor.getScene().getWindow();

        if (list != null) {
            //Creating a File chooser
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save");
            fileChooser.getExtensionFilters().addAll(new ExtensionFilter("CSV", "*.csv"));
            File file = fileChooser.showSaveDialog(stage);

            if (file != null) {
                ExportCSVService.saveCSVFile(list, file);
                a = new Alert(Alert.AlertType.CONFIRMATION);
                a.setHeaderText("Success");
                a.setContentText("File save Successfuly at " + file.getAbsoluteFile());
                a.show();
            }

        } else {
            a = new Alert(AlertType.ERROR);
            a.setHeaderText("Error");
            a.setContentText("First search your query!");
            a.show();
        }
    }

    private void search(String query) {

        query = query.replaceAll(" ", "%20");
        ss = new SearchingService();
        list = ss.getplaces(query);
        placeTable.setItems(FXCollections.observableArrayList(list));
    }

}
