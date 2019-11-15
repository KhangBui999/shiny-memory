package clarify.Model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/*
* TODO:
* Just make this a normal object class. Haha. I'll add any logical thing we
* need from this class later on.
*/

public class Task {
    
    private IntegerProperty taskId;
    private StringProperty title;
    private StringProperty desc;
    private StringProperty doDate;
    private StringProperty dueDate;
    private IntegerProperty priority;
    private IntegerProperty complete;
    
    public Task() {
    }

    public Task(int taskId, String title, String desc, String doDate, String dueDate, int priority, int complete) {
        this.taskId = new SimpleIntegerProperty(taskId);
        this.title = new SimpleStringProperty(title);
        this.desc= new SimpleStringProperty(desc);
        this.doDate= new SimpleStringProperty(doDate);
        this.dueDate= new SimpleStringProperty(dueDate);
        this.priority = new SimpleIntegerProperty(priority);
        this.complete = new SimpleIntegerProperty(complete);
    }
    
    public IntegerProperty getTaskId() {
        return taskId;
    }
    
    public void setTasId(IntegerProperty taskId) {
        this.taskId = taskId;
    }
    
    public StringProperty getTitleProperty() {
        return title;
    }

    public void setTitle(StringProperty title) {
        this.title = title;
    }

    public StringProperty getDescriptionProperty() {
        return desc;
    }

    public void setDescription(StringProperty desc) {
        this.desc = desc;
    }

    public StringProperty getDoDateProperty() {
        return doDate;
    }

    public void setDoDate(StringProperty doDate) {
        this.doDate = doDate;
    }

    public StringProperty getDueDateProperty() {
        return dueDate;
    }

    public void setDueDate(StringProperty dueDate) {
        this.dueDate = dueDate;
    }
    public IntegerProperty getPriorityProperty() {
        return priority;
    }

    public void setPriority(IntegerProperty priority) {
        this.priority = priority;
    }
    
    public IntegerProperty getCompleteProperty() {
        return complete;
    }
    
    public void setComplete(IntegerProperty complete) {
        this.complete = complete;
    }
    
    public String getString(StringProperty value) {
        return value.getValue();
    }
    
    public int getInt(IntegerProperty value) {
        return value.getValue();
    }
    
    @Override
    public String toString() {
        return this.getString(getTitleProperty());
    }
        
}
