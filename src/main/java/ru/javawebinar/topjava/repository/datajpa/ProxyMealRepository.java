package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.List;

@Transactional(readOnly = true)
public interface ProxyMealRepository extends JpaRepository<Meal,Integer> {

    @Transactional
    @Modifying
    @Query("DELETE FROM Meal m WHERE m.id=:id AND m.user.id=:userId")
    int delete(@Param("id") int id, @Param("userId") int userId);

    @Query("SELECT m FROM Meal m WHERE m.user.id=:userId ORDER BY m.id, m.dateTime DESC")
    List<Meal> getAll(@Param("userId") int userId);

    @Query( "SELECT m FROM Meal m WHERE m.user.id=:userId AND m.dateTime >= :startTime AND m.dateTime < :endTime " +
            "ORDER BY m.dateTime DESC")
    List<Meal> getInInterval(@Param("userId") int userId,
                             @Param("startTime") LocalDateTime startTime,
                             @Param("endTime") LocalDateTime endTime);

    @Query("SELECT m FROM Meal m LEFT JOIN FETCH m.user WHERE m.id=:id and  m.user.id=:userId")
    Meal getWithUser(@Param("id") int id, @Param("userId") int userId);
}
