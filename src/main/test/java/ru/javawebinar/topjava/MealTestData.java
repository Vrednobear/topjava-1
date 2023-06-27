package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.AbstractBaseEntity;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {

    public static final Meal MEAL_1 = new Meal(START_SEQ + 2,LocalDateTime.of(2023,6,19, 10,0),"breakfast",1000);
    public static final Meal MEAL_2 = new Meal(START_SEQ + 3,LocalDateTime.of(2023,6,19, 15,10),"lunch",600);
    public static final Meal MEAL_3 = new Meal(START_SEQ + 4,LocalDateTime.of(2023,6,19, 20,0),"dinner",500);
    public static final Meal MEAL_4 = new Meal(START_SEQ + 5,LocalDateTime.of(2023,6,20, 11,20),"breakfast",1200);
    public static final Meal MEAL_5 = new Meal(START_SEQ + 6,LocalDateTime.of(2023,6,20, 16,30),"dinner",700);

    public static final List<Meal> USER_MEAL_LIST = Arrays.asList(MEAL_1, MEAL_2, MEAL_3);
    public static final List<Meal> ADMIN_MEAL_LIST = Arrays.asList(MEAL_4, MEAL_5);
    public static final List<Meal> ADMIN_MEAL_LIST_INTERVAL = Arrays.asList(MEAL_4);

    public static Meal getNewMeal(){
       return new Meal(null,LocalDateTime.of(2023,6,25,14,50), "lunch",1000);
    }

    public static Meal getUpdated(){
        Meal meal = new Meal(MEAL_1);
        meal.setCalories(1250);
        meal.setDescription("One more breakfast");
        return meal;
    }
}