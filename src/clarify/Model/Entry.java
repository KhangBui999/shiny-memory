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
public class Entry {
    private IntegerProperty id;
    private StringProperty startTime;
    private StringProperty endTime;
    private StringProperty entryDesc;
    private IntegerProperty catId;
    
    public Entry(int id, String startTime, String endTime, String entryDesc, int catId) {
        this.id = new SimpleIntegerProperty(id);
        this.startTime = new SimpleStringProperty(startTime);
        this.endTime = new SimpleStringProperty(endTime);
        this.entryDesc = new SimpleStringProperty(entryDesc);
        this.catId = new SimpleIntegerProperty(catId);
    }
    
    public IntegerProperty getId() {
        return id;
    }

    public void setId(IntegerProperty id) {
        this.id = id;
    }

    public StringProperty getStartTime() {
        return startTime;
    }

    public void setStartTime(StringProperty startTime) {
        this.startTime = startTime;
    }

    public StringProperty getEndTime() {
        return endTime;
    }

    public void setEndTime(StringProperty endTime) {
        this.endTime = endTime;
    }

    public StringProperty getEntryDesc() {
        return entryDesc;
    }

    public void setEntryDesc(StringProperty entryDesc) {
        this.entryDesc = entryDesc;
    }

    public IntegerProperty getCatId() {
        return catId;
    }

    public void setCatId(IntegerProperty catId) {
        this.catId = catId;
    }
    
}
