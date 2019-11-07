/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clarify.View;

import clarify.Util.Database;
import clarify.Util.PageSwitchHelper;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import static java.time.LocalDate.now;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

/**
 * FXML Controller class
 *
 * @author khang
 */
public class AddEntryController implements Initializable {

    private PageSwitchHelper p = new PageSwitchHelper();
    private Database d = new Database();

    @FXML
    private TextField entryField;

    @FXML
    private Label entryError;

    @FXML
    private DatePicker startField;

    @FXML
    private Label startError;

    @FXML
    private DatePicker endField;

    @FXML
    private Label endError;

    @FXML
    private Slider categorySlider;

    @FXML
    private TextField categoryField;

    @FXML
    private Label status;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        categoryField.textProperty().bind(categorySlider.valueProperty().asString());
    }

    public void handleCreateButton(ActionEvent event) throws IOException {
        if (checkError() == true) {
            String entDesc = entryField.getText();
            String startTime = startField.getValue().toString();
            String endTime = endField.getValue().toString();
            int category = (int) categorySlider.getValue();

            try {
                String insert = "INSERT INTO ENTRIES (entryDescription, startTime, endTime, category) "
                        + "VALUES("
                        + "'" + entDesc
                        + "','" + startTime
                        + "','" + endTime
                        + "','" + category
                        + "');";
                d.insertStatement(insert);
                System.out.println("SQL statement was inserted successfully");
                this.status.setText("Entry was created successfully. Click 'BACK' to go to previous page.");
                this.status.setTextFill(Color.web("#00e500"));
                this.status.setVisible(true);
                p.changeCenter(event, "/clarify/View/Home.fxml");

            } catch (SQLException e) {
                this.status.setText("Entry failed to create due to a system SQL error. Please try again.");
                this.status.setTextFill(Color.web("#ff0000"));
                this.status.setVisible(true);
                System.out.println("SQL statement could not be inserted successfully");

            }

        }

    }

    public void handleBackButtonK(ActionEvent event) throws IOException {
        p.changeCenter(event, "/clarify/View/Home.fxml");
    }

    //Catches any inappropriate input and warns user
    public boolean checkError() {
        boolean errorStatus = true;

        startError.setText("Error: Please select a date");
        endError.setText("Error: Please select a date");

        if (entryField.getText() == null || entryField.getText().equals("")) {
            entryError.setVisible(true);
            errorStatus = false;
        } else {
            entryError.setVisible(false);
        }

        if (startField.getValue() == null) {
            startError.setVisible(true);
            errorStatus = false;
        } else {
            startError.setVisible(false);
        }

        if (endField.getValue() == null) {
            endError.setVisible(true);
            errorStatus = false;
        } else {
            endError.setVisible(false);
        }

        if (errorStatus == true) {
            errorStatus = dateCheck();
        }

        if (errorStatus == false) {
            status.setText("Entry failed to create because of above errors");
            status.setTextFill(Color.web("#ff0000"));
            status.setVisible(true);
            return false;
        } else {
            return true;
        }
    }

    //Date checker to ensure do-date is before due-date
    public boolean dateCheck() {
        LocalDate startDate = startField.getValue();
        LocalDate endDate = endField.getValue();

        if (startDate.isAfter(endDate)) {
            startError.setText("Error: Start date must be BEFORE end date");
            endError.setText("Error: Start date must be BEFORE end date");
            startError.setVisible(true);
            endError.setVisible(true);
            return false;
        } else if (startDate.isBefore(now()) || endDate.isBefore(now())) {
            if (startDate.isBefore(now())) {
                startError.setText("Error: Start date must be before current date");
                startError.setVisible(true);
            }
            if (endDate.isBefore(now())) {
                endError.setText("Error: End date must be before current date");
                endError.setVisible(true);
            }
            return false;
        } else {
            return true;
        }
    }

}
