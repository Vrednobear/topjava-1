package ru.javawebinar.topjava.service;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.config.AppConfiguration;
import ru.javawebinar.topjava.config.DbConfiguration;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.junit.Assert.*;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.*;

@ContextConfiguration(classes = {AppConfiguration.class, DbConfiguration.class})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    @Autowired
    MealService service;

    @Test
    public void create() {
        Meal mealSaved = service.create(getNewMeal(), USER_ID);
        int id = mealSaved.getId();
        Meal newMeal = getNewMeal();
        newMeal.setId(id);
        Assertions.assertThat(mealSaved).isEqualToComparingFieldByFieldRecursively(newMeal);
    }

    @Test
    public void delete() {
        int id = MEAL_1.getId();
        service.delete(id, USER_ID);
        assertThrows(NotFoundException.class,() -> service.get(id, USER_ID));

    }

    @Test
    public void get() {
        Meal meal = service.get(MEAL_1.getId(), USER_ID);
        Assertions.assertThat(meal).isEqualToComparingFieldByFieldRecursively(MEAL_1);
    }

    @Test
    public void getWithWrongUser() {
        Integer id = MEAL_1.getId();
        assertThrows(NotFoundException.class,() -> service.get(id, ADMIN_ID));
    }

    @Test
    public void getAll() {
        List<Meal> all = service.getAll(USER_ID);
        Assertions.assertThat(USER_MEAL_LIST).usingRecursiveFieldByFieldElementComparator().isEqualTo(USER_MEAL_LIST);
    }

    @Test
    public void update() {
        Meal updated = MealTestData.getUpdated();
        service.update(updated, USER_ID);
        Assertions.assertThat(service.get(MEAL_1.getId(),USER_ID)).isEqualToComparingFieldByFieldRecursively(updated);
    }

    @Test
    public void getInInterval() {
        //From 9 till 16:30
        List<Meal> filtered = service.getInInterval(ADMIN_ID, LocalDateTime.of(2023, 6, 20, 10, 30),
                LocalDateTime.of(2023, 6, 20, 16, 30));
        Assertions.assertThat(filtered).usingRecursiveFieldByFieldElementComparator().isEqualTo(ADMIN_MEAL_LIST_INTERVAL);

    }
}