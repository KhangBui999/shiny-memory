/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clarify.View;

import clarify.Model.Category;
import clarify.Model.Entry;
import clarify.Util.Database;
import clarify.Util.PageSwitchHelper;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author khang
 */
public class TimeLoggingController implements Initializable {

    PageSwitchHelper p = new PageSwitchHelper();
    Database d = new Database();
    private int taskId;
    private int catId;
    
    @FXML
    private TreeView treeItem;
    
    @FXML
    private Text catName;
    
    @FXML
    private Text taskName;
    
    @FXML
    private Text startField;
    
    @FXML
    private Text endField;
    
    @FXML
    private TextArea descArea;
    
    @FXML
    private Button uE;
    @FXML
    private Button uC;
    @FXML
    private Button dE;
    @FXML
    private Button dC;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            loadTreeView();
        } catch (IOException ex) {
            Logger.getLogger(TimeLoggingController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
    public void userClickedTree() {
        userSelectTree();
    }
    
    @FXML
    public void pressedUpdateCategory(ActionEvent event) throws IOException{
        p.changeToUpdateCategory(event, catId);
    }
    
    @FXML
    public void pressedUpdateEntry (ActionEvent event) throws IOException {
        p.changeToUpdateEntry(event, catId);
    }
    
    @FXML
    public void userSelectTree() {
        try{
            String object = treeItem.getSelectionModel().getSelectedItem().toString();
            boolean entryType = checkType(object);
            if(entryType == true){
                String entryNo = object.substring(25, object.indexOf('|')-1);
                taskId = Integer.parseInt(entryNo);
                ResultSet rs = d.getResultSet("SELECT * FROM ENTRIES e, Categories c, TASKS t "
                        + "WHERE e.category = c.cat_id "
                        + "AND e.task = t.task_id "
                        + "AND e.ent_id = '"+entryNo+"';");
                
                catId = rs.getInt(5);
                catName.setText(rs.getString(8));
                taskId = Integer.parseInt(entryNo);
                taskName.setText(rs.getString(11));
                String startDate = rs.getString(2).substring(8, 10)+"-"+rs.getString(2).substring(5,7)+"-"+rs.getString(2).substring(0,4)+" "+rs.getString(2).substring(11);        
                startField.setText(startDate);
                String endDate = rs.getString(3).substring(8, 10)+"-"+rs.getString(3).substring(5,7)+"-"+rs.getString(3).substring(0,4)+" "+rs.getString(3).substring(11);
                endField.setText(endDate);
                descArea.setText(rs.getString(4));
                
                uC.setVisible(true);
                dC.setVisible(true);
                uE.setVisible(true);
                dE.setVisible(true);
            } 
            
            else if(entryType == false){
                String categoryName = object.substring(18, object.indexOf(']')-1);
                ResultSet rs = d.getResultSet("SELECT * FROM Categories WHERE category_name = '"+categoryName+"';");
                
                catId = rs.getInt(1);
                
                String newCatName = rs.getString(2);
                catName.setText(newCatName);
                String youCan = "You can select a task!";
                
                taskName.setText(youCan);
                startField.setText(youCan);
                endField.setText(youCan);
                descArea.setPromptText(youCan);
                
                uC.setVisible(true);
                dC.setVisible(true);
                uE.setVisible(false);
                dE.setVisible(false);
            }
        } catch(Exception e) {
            //DO NOTHING
        }
    }
    //YYYY-MM-DD
    //TreeItem [ value: Work ] 18, k-1
    
    
    @FXML
    public boolean checkType(String object){
        if(object.contains("[ value: EntID:")){
            return true;
        } else {
            return false;
        }
    }
    
    @FXML
    public void loadTreeView() throws IOException {
        TreeItem dummyRoot = new TreeItem();
        ArrayList<Category> categoryList = getCategory();
        for(Category cat: categoryList) {
            //Creates Categories
            TreeItem newCategory = new TreeItem(cat);
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("ColourIcon.fxml"));
            AnchorPane icon = (AnchorPane) loader.load();
            ColourIconController cont = loader.getController();
            cont.setBackground(cat.getString(cat.getColourRGB()));
            newCategory.setGraphic(icon);
            
            //Adds entries to TreeItem
            addEntries(newCategory, cat);
            dummyRoot.getChildren().add(newCategory);
        }
        treeItem.setRoot(dummyRoot);
        treeItem.setShowRoot(false);
    }
    
    public ArrayList<Category> getCategory(){
        System.out.println("getCategory method initialised");
        ArrayList<Category> categories = new ArrayList<>();
        String statement = "SELECT * FROM Categories;";
        try {
            ResultSet rs = d.getResultSet(statement);
            while(rs.next()){
                categories.add(new Category(rs.getInt(1), rs.getString(2), rs.getString(3)));
            }
        System.out.println("getCategory method executed successfully!");
        }
        catch (SQLException e){
            System.out.println("Could not load categories");
        }
        return categories;
    }
    
    @FXML
    public void addEntries(TreeItem item, Category category){
        System.out.println("addEntries method intialised");
        ArrayList<Entry> entries = new ArrayList<>();
        String statement = "SELECT * FROM ENTRIES WHERE category = '"+category.getInt(category.getId())+"';";
        try {
            ResultSet rs = d.getResultSet(statement);
            while(rs.next()){
                Entry newEntry = new Entry(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5), rs.getInt(6));
                TreeItem entry = new TreeItem(newEntry);
                item.getChildren().add(entry);
            }
        }
        catch(SQLException e){
            //TODO
        }
    }
    
}
