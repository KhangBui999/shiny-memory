/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clarify.Model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author BRIAN
 */
public class Reflection {
    private IntegerProperty id; 
    private StringProperty well; 
    private StringProperty better; 
    private StringProperty currentDay;
    
    
public Reflection() {
    }

    public Reflection(int id, String well, String better, String currentDay) {
        this.id = new SimpleIntegerProperty(id);
        this.well = new SimpleStringProperty(well);
        this.better= new SimpleStringProperty(better);
        this.currentDay= new SimpleStringProperty(currentDay);
        
    }
    
    public final IntegerProperty getId() {
        return id;
    }
    
    public final void setId(IntegerProperty id) {
        this.id = id;
    }
    
    public final StringProperty getWellProperty() {
        return well;
    }

    public final void setWell(StringProperty well) {
        this.well = well;
    }

    public final StringProperty getBetter() {
        return better;
    }

    public final void setBetter(StringProperty better) {
        this.better = better;
    }

    public final StringProperty getCurrentDay() {
        return currentDay;
    }

    public final void setCurrentDay(StringProperty currentDay) {
        this.currentDay = currentDay;
    }
    public final String getString(StringProperty value) {
        return value.getValue();
    }
    
    public final int getInt(IntegerProperty value) {
        return value.getValue();
    }
   

    }
    
  
        
    
    
    

