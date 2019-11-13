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
 * @author khang
 */
public class Category {

    private IntegerProperty id;
    private StringProperty name;
    private StringProperty colourRGB;
    
    public Category() {
    }
    
    public Category(int id, String name, String colourRGB) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.colourRGB = new SimpleStringProperty(colourRGB);
    }
    
    public IntegerProperty getId() {
        return id;
    }

    public void setId(IntegerProperty id) {
        this.id = id;
    }

    public StringProperty getName() {
        return name;
    }

    public void setName(StringProperty name) {
        this.name = name;
    }

    public StringProperty getColourRGB() {
        return colourRGB;
    }

    public void setColourRGB(StringProperty colourRGB) {
        this.colourRGB = colourRGB;
    }
    
    //Converts StringProperty to String
    public final String getString(StringProperty value) {
        return value.getValue();
    }
    
    //Convert IntegerProperty to int
    public final int getInt(IntegerProperty value) {
        return value.getValue();
    }
    
}
