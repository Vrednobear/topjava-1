package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@Qualifier("data")
public class DataJpaMealRepositoryImpl implements MealRepository {

	@Autowired
	ProxyMealRepository proxy;

	@Autowired
	ProxyUserRepository proxyUserRepository;

	@Override
	public Meal save(Meal meal, int userId) {
		User user = proxyUserRepository.findById(userId).orElse(new User());
		meal.setUser(user);
		return proxy.save(meal);
	}

	@Override
	public Meal get(int id, int userId) {
		Meal meal = proxy.findById(id).orElse(null);
		if(meal != null)
			return meal.getUser().getId() == userId? meal : null;

		return null;
	}

	@Override
	public boolean delete(int id, int userId) {
	    return proxy.delete(id, userId) != 0;

	}

	@Override
	public List<Meal> getAll(int userId) {
	 return proxy.getAll(userId);
	}

	@Override
	public List<Meal> getInInterval(int userId, LocalDateTime startTime, LocalDateTime endTime) {
		return proxy.getInInterval(userId, startTime, endTime);
	}
}
