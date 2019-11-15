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
    private StringProperty answer; 
    private StringProperty frequency; 

    public Reflection() {
    }
    
    public Reflection(String answer, String frequency) {
        this.answer = new SimpleStringProperty(answer);
        this.frequency = new SimpleStringProperty(frequency);
    }

    public StringProperty getAnswer() {
        return answer;
    }

    public void setAnswer(StringProperty answer) {
        this.answer = answer;
    }

    public StringProperty getFrequency() {
        return frequency;
    }

    public void setFrequency(StringProperty frequency) {
        this.frequency = frequency;
    }
    
    public final String getString(StringProperty value) {
        return value.getValue();
    }
    
    public final int getInt(IntegerProperty value) {
        return value.getValue();
    }
   

}
