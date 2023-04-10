package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealTo;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

public interface MealRepository {
    public Meal save(Meal meal, int userId);

    public Meal get(int id, int userId);

    public Meal delete(int id, int userId);

    public List<Meal> getAll(int userId);

    public List<Meal> getInInterval(int userId, LocalDateTime startTime, LocalDateTime endTime);
}
