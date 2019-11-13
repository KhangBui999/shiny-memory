/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clarify.View;

import clarify.Util.PageSwitchHelper;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

/**
 * FXML Controller class
 *
 * @author khang
 */
public class TimeLoggingController implements Initializable {

    PageSwitchHelper p = new PageSwitchHelper();
    
    @FXML
    private TreeView treeItem;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadTreeView();
    }
    
    @FXML
    public void handleCreateCategory(ActionEvent event) throws IOException {
        p.changeCenter(event, "AddCategory.fxml");
    }
    
    @FXML
    public void handleCreateEntry(ActionEvent event) throws IOException {
        p.changeCenter(event, "AddEntry.fxml");
    }
    
    @FXML
    public void loadTreeView() {
        TreeItem dummyRoot = new TreeItem();
        
        dummyRoot.getChildren().add(new TreeItem("Category: Work"));
        treeItem.setRoot(dummyRoot);
        treeItem.setShowRoot(false);
    }
    
}
