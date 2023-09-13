package ru.javawebinar.topjava.service;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.config.DbConfiguration;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.assertThrows;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.*;

@ContextConfiguration(classes = DbConfiguration.class)
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
//@ActiveProfiles(resolver = ActiveDbProfileResolver.class, profiles = {"data", "jdbc"})
@ActiveProfiles(profiles = {"Prod", "data", "jdbc"})
public class MealServiceTest {

    @Autowired
    MealService service;

    @Test
    public void create() {
        Meal mealSaved = service.create(getNewMeal(), USER_ID);
        int id = mealSaved.getIdNotNull();
        Meal newMeal = getNewMeal();
        newMeal.setId(id);
        Assertions.assertThat(mealSaved).usingRecursiveComparison().ignoringFields("user").isEqualTo(newMeal);
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
        Assertions.assertThat(meal).usingRecursiveComparison().ignoringFields("user").isEqualTo(MEAL_1);
    }

    @Test
    public void getWithWrongUser() {
        Integer id = MEAL_1.getId();
        assertThrows(NotFoundException.class,() -> service.get(id, ADMIN_ID));
    }

    @Test
    public void getAll() {
        List<Meal> all = service.getAll(USER_ID);
        Assertions.assertThat(all)
                .usingRecursiveFieldByFieldElementComparatorIgnoringFields("user")
                .isEqualTo(USER_MEAL_LIST);
    }

    @Test
    public void update() {
        Meal updated = MealTestData.getUpdated();
        service.update(updated, USER_ID);
        Assertions.assertThat(service.get(MEAL_1.getId(),USER_ID))
                .usingRecursiveComparison()
                .ignoringFields("user")
                .isEqualTo(updated);
    }

    @Test
    public void getInInterval() {
        //From 9 till 16:30
        List<Meal> filtered = service.getInInterval(ADMIN_ID, LocalDateTime.of(2023, 6, 20, 10, 30),
                LocalDateTime.of(2023, 6, 20, 16, 30));
        Assertions.assertThat(filtered)
                .usingRecursiveFieldByFieldElementComparatorIgnoringFields("user")
                .isEqualTo(ADMIN_MEAL_LIST_INTERVAL);

    }

    @Test
    public void getWithUser(){
        Meal withUser = service.getWithUser(MEAL_1.getId(), USER_ID);
        Assertions.assertThat(withUser).usingRecursiveComparison().ignoringFields("user").isEqualTo(MEAL_1);
        assertMatch(withUser.getUser(),USER);
    }
}