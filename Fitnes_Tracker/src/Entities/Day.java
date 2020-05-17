package Entities;

import sample.LocalDateAdapter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

@XmlRootElement()
public class Day {
    private LocalDate date;
    private ArrayList<Exercise> exercises;
    private int total_Calories;

    public Day(LocalDate date) {
        this.date = date;
        exercises = new ArrayList<Exercise>();
    }

    public Day(LocalDate date, int total_Calories) {
        this.date = date;
        this.total_Calories = total_Calories;
        exercises = new ArrayList<Exercise>();
    }

    @XmlJavaTypeAdapter(value = LocalDateAdapter.class)
    public LocalDate getDate() {
        return date;
    }


    public ArrayList<Exercise> getExercises() {
        return exercises;
    }

    @XmlElement
    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Day() {
    }

    @XmlElementWrapper
    public void setExercises(ArrayList<Exercise> exercises) {
        this.exercises = exercises;
    }

    @XmlElement
    public void setTotal_Calories(int total_Calories) {
        this.total_Calories = total_Calories;
    }

    public int getTotal_Calories() {
        return total_Calories;
    }


    public void addExercise(Exercise exercise) {
        exercises.add(exercise);
        total_Calories = 0;
        for (Exercise ex : exercises)
            total_Calories += ex.getExercise_consumption();

    }
}
