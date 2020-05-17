package Entities;

import java.io.Serializable;

public enum Exercise_Type  implements Serializable {
    Push_up("отжимания", 3000*60),     // 100*30 за одно * 30 в минуту
    Jump_rope("скакалка", 1500*60),    // 100 прыжков за 1 минуту
    Squatting("приседания", 5000*60);  // минуту

    private final String name;
    private final int consumption;

    Exercise_Type(String name, int consumption) {
        this.name = name;
        this.consumption = consumption;
    }

    public int getConsumption() {
        return consumption;
    }
}
