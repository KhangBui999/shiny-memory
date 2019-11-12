package clarify.Model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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

    private ObservableList<Task> stuffToDo = FXCollections
            .observableArrayList();

    public Task() {

    }

    public Task(int taskId, String title, String desc, String doDate, String dueDate, int priority, int complete) {
        this.taskId = new SimpleIntegerProperty(taskId);
        this.title = new SimpleStringProperty(title);
        this.desc = new SimpleStringProperty(desc);
        this.doDate = new SimpleStringProperty(doDate);
        this.dueDate = new SimpleStringProperty(dueDate);
        this.priority = new SimpleIntegerProperty(priority);
        this.complete = new SimpleIntegerProperty(complete);
    }

    public final IntegerProperty getTaskId() {
        return taskId;
    }

    public final void setTaskId(IntegerProperty taskId) {
        this.taskId = taskId;
    }

    public final String getTitle() {
        return titleProperty().get();
    }

    public final void setTitle(String value) {
        titleProperty().set(value);
    }

    public StringProperty titleProperty() {
        if (title == null) {
            title = new SimpleStringProperty();
        }
        return title;
    }

    public final String getDesc() {
        return descProperty().get();
    }

    public final void setDesc(String value) {
        descProperty().set(value);
    }

    public StringProperty descProperty() {
        if (desc == null) {
            desc = new SimpleStringProperty();
        }
        return desc;
    }

    public ObservableList<Task> stuffToDo() {
        return stuffToDo;
    }

    public Task(String taskTitle, String taskDesc) {
        setTitle(taskTitle);
        setDesc(taskDesc);

    }

    @Override
    public String toString() {
        return " " + this.getTitle();
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

    public final IntegerProperty getPriorityProperty() {
        return priority;
    }

    public final void setPriority(IntegerProperty priority) {
        this.priority = priority;
    }

    public final IntegerProperty getCompleteProperty() {
        return complete;
    }

    public final void setComplete(IntegerProperty complete) {
        this.complete = complete;
    }

    public final String getString(StringProperty value) {
        return value.getValue();
    }

    public final int getInt(IntegerProperty value) {
        return value.getValue();
    }

}
