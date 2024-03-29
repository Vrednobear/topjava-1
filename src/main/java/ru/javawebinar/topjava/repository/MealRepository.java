package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealTo;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

public interface MealRepository {

    Meal save(Meal meal, int userId);

    Meal get(int id, int userId);

    boolean delete(int id, int userId);

    List<Meal> getAll(int userId);

    List<Meal> getInInterval(int userId, LocalDateTime startTime, LocalDateTime endTime);

    default Meal getWithUser(int id, int userId) {
        throw new UnsupportedOperationException();
    }
}
