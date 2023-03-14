package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;


public class InMemoryRepository implements MealRepository {
	private final AtomicInteger counter = new AtomicInteger();
	private final Map<Integer, Meal> repository = new ConcurrentHashMap<>();

	{
		MealsUtil.MEAL_LIST.forEach(this::save);
	}

	@Override
	public Meal save(Meal meal) {
		if(meal.isNew(meal)) {
			meal.setId(counter.incrementAndGet());
			return repository.put(meal.getId(), meal);
		}
		//??? why is not just put
		return repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
	}

	@Override
	public Meal get(int id) {
		return repository.get(id);
	}

	@Override
	public Meal delete(int id) {
		return repository.remove(id);
	}

	@Override
	public Collection<Meal> getAll() {
		return repository.values();
	}
}
