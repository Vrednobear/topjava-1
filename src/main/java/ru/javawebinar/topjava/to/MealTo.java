package ru.javawebinar.topjava.to;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class MealTo {
    private final int id;

    private final LocalDateTime dateTime;

    private final String description;

    private final int calories;

    private final boolean excess;

    public MealTo(int id,LocalDateTime dateTime, String description, int calories, boolean excess) {
        this.id = id;
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
        this.excess = excess;
    }

//    public MealTo(Meal meal, boolean excess) {
//        this.id = meal.getId();
//        this.dateTime = meal.getDateTime();
//        this.description = meal.getDescription();
//        this.calories = meal.getCalories();
//        this.excess = excess;
//    }

    @Override
    public String toString() {
        return "MealTo{" +
                "dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                ", excess=" + excess +
                '}';
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }

    public boolean isExcess() {
        return excess;
    }

    public LocalDate getDate(){
        return dateTime.toLocalDate();
    }
    public LocalTime getTime(){
        return dateTime.toLocalTime();
    }

    public int getId() {
        return id;
    }
}
