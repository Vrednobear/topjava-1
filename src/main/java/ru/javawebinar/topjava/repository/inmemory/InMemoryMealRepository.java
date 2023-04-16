package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.util.TimeUtil.isBetweenHalfOpen;

@Repository
public class InMemoryMealRepository implements MealRepository {
	private final AtomicInteger counter = new AtomicInteger();
	private final Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();

	{
		MealsUtil.MEAL_LIST.forEach(meal -> save(meal, SecurityUtil.authUserId()));
	}

	@Override
	public Meal save(Meal meal, int userId) {
		Map<Integer, Meal> meals = repository.computeIfAbsent(userId, (id) -> new ConcurrentHashMap<>());
		if(meal.isNew()) {
			meal.setId(counter.incrementAndGet());
			return meals.put(meal.getId(), meal);
		}
		return meals.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
	}

	@Override
	public Meal get(int id, int userId) {
		Map<Integer, Meal>  meals = repository.get(userId);
		return meals==null ? null :meals.get(id);
	}

	@Override
	public Meal delete(int id, int userId) {
		Map<Integer,Meal> meals = repository.get(userId);
		return meals==null ? null : meals.remove(id);
	}

	public List<Meal> getAllFiltered(int userId, Predicate<Meal> filter) {
		Map<Integer, Meal> meals = repository.get(userId);
		return CollectionUtils.isEmpty(meals) ? Collections.emptyList() :
				meals.values().stream()
						.filter(filter)
						.sorted(Comparator.comparing((Meal::getDateTime)))
						.collect(Collectors.toList());
	}

	@Override
	public List<Meal> getAll(int userId) {
		return getAllFiltered(userId, m -> true);
	}

	@Override
	public List<Meal> getInInterval(int userId, LocalDateTime startTime, LocalDateTime endTime) {
		return getAllFiltered(userId, m -> isBetweenHalfOpen(m.getDateTime(),startTime, endTime));
	}
}
