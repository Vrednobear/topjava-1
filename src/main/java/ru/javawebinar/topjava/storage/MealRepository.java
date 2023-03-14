package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;

import java.util.Collection;

public interface MealRepository {

    public Meal save(Meal meal);

    public Meal get(int id);

    public Meal delete(int id);

    public Collection<Meal> getAll();
}
