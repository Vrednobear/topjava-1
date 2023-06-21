package ru.javawebinar.topjava.repository.db;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class JdbcMealRepositoryImpl implements MealRepository {
    @Override
    public Meal save(Meal meal, int userId) {
        return null;
    }

    @Override
    public Meal get(int id, int userId) {
        return null;
    }

    @Override
    public Meal delete(int id, int userId) {
        return null;
    }

    @Override
    public List<Meal> getAll(int userId) {
        return null;
    }

    @Override
    public List<Meal> getInInterval(int userId, LocalDateTime startTime, LocalDateTime endTime) {
        return null;
    }
}
