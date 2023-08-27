package ru.javawebinar.topjava.repository.jpa;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@Transactional
@Qualifier("jpa")
public class HibernateMealRepositoryImpl implements MealRepository {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    @Transactional
    public Meal save(Meal meal, int userId) {
        User ref = entityManager.getReference(User.class, userId);
        meal.setUser(ref);
        if(meal.isNew()){
            entityManager.persist(meal);
        }else{
            //TODO:maybe with @NamedQuery
            entityManager.merge(meal);
        }
        return meal;
    }

    @Override
    public Meal get(int id, int userId) {
        Meal meal = entityManager.find(Meal.class, id);
        if(meal==null)
            return null;
        Integer uId = meal.getUser().getId();
        return uId==userId? meal : null;
    }

    @Override
    @Transactional
    public boolean delete(int id, int userId) {
        Query namedQuery = entityManager.createNamedQuery(Meal.DELETE);
        List<Meal> all = getAll(userId);
        namedQuery.setParameter("id", id);
        namedQuery.setParameter("userId", userId);
        return namedQuery.executeUpdate() != 0;
    }

    @Override
    public List<Meal> getAll(int userId) {
        TypedQuery<Meal> namedQuery = entityManager.createNamedQuery(Meal.ALL_BY_USER_ID_SORTED, Meal.class);
        namedQuery.setParameter("userId", userId);
        return namedQuery.getResultList();
    }

    @Override
    public List<Meal> getInInterval(int userId, LocalDateTime startTime, LocalDateTime endTime) {
        TypedQuery<Meal> namedQuery = entityManager.createNamedQuery(Meal.IN_INTERVAL, Meal.class);
        namedQuery.setParameter("userId", userId);
        namedQuery.setParameter("startTime", startTime);
        namedQuery.setParameter("endTime", endTime);
        return namedQuery.getResultList();
    }
}
