/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clarify.View;

import clarify.Model.Task;
import clarify.Util.Database;
import clarify.Util.PageSwitchHelper;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author khang
 */
public class DeleteEntryController implements Initializable {

    private int id;
    private final PageSwitchHelper p = new PageSwitchHelper();
    private final Database d = new Database();
    
    @FXML
    private Text prompt;
    
    @FXML
    private Text warning;
    
    @FXML
    private Button yesBtn;
    
    @FXML
    private Button noBtn;
    
    @FXML
    private Label result;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public void handleBackButton(ActionEvent event) throws IOException {
        p.changeCenter(event, "/clarify/View/TimeLogging.fxml");
    }
    
    public void handleNoButton() {
        disappearButtons();
        this.result.setText("Delete cancelled. Please click on BACK!");
        this.result.setVisible(true);
    }
    
    public void handleYesButton() {
        String st = "DELETE FROM ENTRIES "
                + "WHERE ent_id = '"+getId()+"';";
            try{
                d.insertStatement(st);
                System.out.println("Entry was deleted successfully");
                this.result.setText("Entry deleted. Please click on BACK.");
                result.setVisible(true);
            }
            catch (SQLException e){
                System.out.println("Entry was unable to be deleted due to an SQL error.");
                this.result.setText("An error occured. Please click on BACK.");
                result.setVisible(true);
            }
            disappearButtons();
    }
    
    public void disappearButtons() {
        this.prompt.setVisible(false);
        this.warning.setVisible(false);
        this.yesBtn.setVisible(false);
        this.noBtn.setVisible(false);
    }
    
}

