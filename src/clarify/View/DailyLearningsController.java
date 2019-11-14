/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clarify.View;

import clarify.Util.Database;
import clarify.Util.PageSwitchHelper;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import java.time.LocalDate;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Button;






public class DailyLearningsController{
    
@FXML 
private Label date;

@FXML
private Label well;

@FXML 
private Label better;

@FXML 
private TextArea wellness;

@FXML
private TextArea betterness;

@FXML 
private Label wellMessage; 

@FXML
private Label betterMessage;

@FXML
private Button submit;

Database d = new Database();
PageSwitchHelper ps = new PageSwitchHelper();


public void initialise() throws ParseException {
SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
Date dateDay = new Date();
String dateValue = formatter.format(dateDay);
boolean notUsed = true;

if (notUsed==false) {
    try {
        ResultSet rs = d.getResultSet("SELECT min(currentDay) from Reflection;");
        LocalDate now = LocalDate.now();
        Date latest = formatter.parse(rs.getString(1));
        
        for (int i=0;i<=29;i++){
        String dt = dateValue;  // Start date
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Calendar c = Calendar.getInstance();
        c.setTime(sdf.parse(dt));
        c.add(Calendar.DATE, 1);  // number of days to add
        dt = sdf.format(c.getTime());
        }
        
    } catch (SQLException ex) {
       ex.printStackTrace();
    }
    
        

  
    
} else {
    date.setText(dateValue);
    LocalDate now = LocalDate.now();
    date.setText(now.toString());
}

    
    
}
@FXML
public void handleSubmitButton(ActionEvent event) {
    if (wellness.getText() == null || wellness.getText().length() == 0) {
        wellMessage.setVisible(true);
    }
    else if (betterness.getText() == null || betterness.getText().length()==0){
        betterMessage.setVisible(true);
    
    }
    else {
        String insert = "INSERT INTO REFLECTION (well,better,currentDay)"
                +"VALUES ('"+wellness.getText()+"','"
                        +betterness.getText()+"','"
                        +date.getText()+"');";
    
    try {
                d.insertStatement(insert);
                ps.switcher(event, "/Clarify/view/Reflection.fxml");
                
                   
            } catch (Exception e) {
                e.printStackTrace();
            }
    
}


   
    

    
}
}

