package Entities;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDate;
import java.util.Date;


@XmlRootElement()
public class Exercise{
    private Exercise_Type type;
    private long duration;
    private int exercise_consumption;

    public Exercise(Exercise_Type type) {
        this.type = type;
    }

    public Exercise_Type getType() {
        return type;
    }


    public int getExercise_consumption() {
        return exercise_consumption;
    }


    public long getDuration() {
        return duration;
    }


    public Exercise() {
    }


    public void setType(Exercise_Type type) {
        this.type = type;
    }



    public void setDuration(long duration) {
        this.duration = duration;
        if(type != null)
            this.exercise_consumption = (int)(type.getConsumption()/3600 * duration);
    }


    public void setExercise_consumption(int exercise_consumption) {
        this.exercise_consumption = exercise_consumption;
    }
}
