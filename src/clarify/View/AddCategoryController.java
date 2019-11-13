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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;

/**
 * FXML Controller class
 *
 * @author khang
 */
public class AddCategoryController implements Initializable {

    PageSwitchHelper p = new PageSwitchHelper();
    Database d = new Database();
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    public void handleBackButton(ActionEvent event) throws IOException {
        p.changeCenter(event, "TimeLogging.fxml");
    }
    
    public void handleCreateButton(ActionEvent event) throws SQLException {
        //TODO
    }
    
}
