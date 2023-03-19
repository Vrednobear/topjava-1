package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;

import java.util.Collection;

// TODO add userId
public interface MealRepository {
    // null if updated meal does not belong to userId
    public Meal save(Meal meal);

    // null if meal does not belong to userId
    public Meal get(int id);

    // false if meal does not belong to userId
    public Meal delete(int id);

    // ORDERED dateTime desc
    public Collection<Meal> getAll();
}
