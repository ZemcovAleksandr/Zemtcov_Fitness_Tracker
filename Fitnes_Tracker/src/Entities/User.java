package Entities;

import sample.LocalDateAdapter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;



@XmlRootElement
@XmlType(propOrder = {"id","name","daysArrayList", "start_of_trainings", "last_training"})
public class User {
    private int id;
    private String name;
    private ArrayList<Day> daysArrayList = new ArrayList<Day>();
    private LocalDate start_of_trainings;
    private LocalDate last_training;

    public User() {
    }

    public User(int id, String name) {
        this.id = id;
        this.name = name;
        this.start_of_trainings = LocalDate.now();
        this.last_training = start_of_trainings;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Day> getDaysList() {
        return daysArrayList;
    }

    public void addDay(Day day) {
        this.daysArrayList.add(day);
        this.last_training = day.getDate();
    }

    public LocalDate getStart_of_trainings() {
        return start_of_trainings;
    }

    public LocalDate getLast_training() {
        return last_training;
    }


    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<Day> getDaysArrayList() {
        return daysArrayList;
    }


    public void setDaysArrayList(ArrayList<Day> daysArrayList) {
        this.daysArrayList = daysArrayList;
    }

    @XmlJavaTypeAdapter(value = LocalDateAdapter.class)
    public void setStart_of_trainings(LocalDate start_of_trainings) {
        this.start_of_trainings = start_of_trainings;
    }

    @XmlJavaTypeAdapter(value = LocalDateAdapter.class)
    public void setLast_training(LocalDate last_training) {
        this.last_training = last_training;
    }

    @Override
    public String toString() {
        return  name + '\'' +
                ", last training=" + last_training +
                '}';
    }
}

