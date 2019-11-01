package clarify.Model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/*
* TODO:
* Just make this a normal object class. Haha. I'll add any logical thing we
* need from this class later on.
*/

public class Task {
    
    private StringProperty title;
    private StringProperty desc;
    private StringProperty doDate;
    private StringProperty dueDate;
    private StringProperty priority;
    
     public Task() {
        this("","","","","");
    }

    public Task(String title, String desc, String doDate, String dueDate, String priority) {
        this.title = new SimpleStringProperty(title);
        this.desc= new SimpleStringProperty(desc);
        this.doDate= new SimpleStringProperty(doDate);
        this.dueDate= new SimpleStringProperty(dueDate);
        this.priority = new SimpleStringProperty(priority);

    }
    public final StringProperty getTitleProperty() {
        return title;
    }

    public final void setTitle(StringProperty title) {
        this.title = title;
    }

    public final StringProperty getDescriptionProperty() {
        return desc;
    }

    public final void setDescription(StringProperty desc) {
        this.desc = desc;
    }

    public final StringProperty getDoDateProperty() {
        return doDate;
    }

    public final void setDoDate(StringProperty doDate) {
        this.doDate = doDate;
    }

    public final StringProperty getDueDateProperty() {
        return dueDate;
    }

    public final void setDueDate(StringProperty dueDate) {
        this.dueDate = dueDate;
    }
    public final StringProperty getPriorityProperty() {
        return priority;
    }

    public final void setPriority(StringProperty priority) {
        this.priority = priority;
    }
    
    



    
}
