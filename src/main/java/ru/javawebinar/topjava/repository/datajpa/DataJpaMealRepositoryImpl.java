package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.service.MealService;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@Qualifier("data")
public class DataJpaMealRepositoryImpl implements MealRepository {

	@Lazy
	@Autowired
	MealService mealService;

	private final ProxyMealRepository proxyMealRepository;

	private final ProxyUserRepository proxyUserRepository;

	public DataJpaMealRepositoryImpl(ProxyMealRepository proxyMealRepository, ProxyUserRepository proxyUserRepository) {
		this.proxyMealRepository = proxyMealRepository;
		this.proxyUserRepository = proxyUserRepository;
	}

	@Override
	public Meal save(Meal meal, int userId)
	{
		if (!meal.isNew() && get(meal.getId(), userId) == null)
			return null;

		meal.setUser(proxyUserRepository.getReferenceById(userId));
		return proxyMealRepository.save(meal);
	}

	@Override
	public Meal get(int id, int userId) {
		Meal meal = proxyMealRepository.findById(id).orElse(null);
		if(meal != null)
			return meal.getUser().getId() == userId? meal : null;

		return null;
	}

	@Override
	public boolean delete(int id, int userId) {
	    return proxyMealRepository.delete(id, userId) != 0;

	}

	@Override
	public List<Meal> getAll(int userId) {
	 return proxyMealRepository.getAll(userId);
	}

	@Override
	public List<Meal> getInInterval(int userId, LocalDateTime startTime, LocalDateTime endTime) {
		return proxyMealRepository.getInInterval(userId, startTime, endTime);
	}

	@Override
	public Meal getWithUser(int id, int userId) {
		return proxyMealRepository.getWithUser(id, userId);
	}
}
