package ru.javawebinar.topjava.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.jcache.JCacheCacheManager;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.config.CacheConfig;
import ru.javawebinar.topjava.config.DbConfiguration;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.JpaUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.List;

import static org.junit.Assert.assertThrows;
import static ru.javawebinar.topjava.UserTestData.*;

@ContextConfiguration(classes = {DbConfiguration.class, CacheConfig.class})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
//@ActiveProfiles(resolver = ActiveDbProfileResolver.class)
@ActiveProfiles(profiles = {"Prod", "data", "jdbc"})
public class UserServiceTest {

    @Autowired
    UserService service;

    @Autowired
    JCacheCacheManager manager;

    @Autowired
    JpaUtil jpaUtil;

    @Before
    public void setup()
    {
        manager.getCache("users").clear();
        jpaUtil.clear2ndLevelHibernateCache();

    }

    @Test
    public void create() {
        User created = service.create(getNewUser());
        User newUser = getNewUser();
        Integer id = created.getIdNotNull();
        newUser.setId(id);
        assertMatch(created,newUser);
        assertMatch(service.get(id),newUser);
    }

    @Test
    public void createDuplicateEmail(){
        assertThrows(DataAccessException.class, () -> service.create(
                new User(null, "Duplicate", "user@gmail.com","newPass", Role.USER)));
    }

    @Test
    public void delete() {
        service.delete(USER_ID);
        assertThrows(NotFoundException.class, () -> service.get(USER_ID));
    }

    @Test
    public void get() {
        User user = service.get(USER_ID);
        assertMatch(user, USER);
    }

    @Test
    public void getByEmail() {
        User userByEmail = service.getByEmail("admin@gmail.com");
        assertMatch(userByEmail, ADMIN);
    }

    @Test
    public void getAll() {
        List<User> allUsers = service.getAll();
        assertMatch(allUsers,ADMIN,USER);
    }

    @Test
    public void update() {
        User updated = getUpdated();
        service.update(updated);
        assertMatch(service.get(USER_ID), getUpdated());

    }

    @Test
    public void getWithMeals(){
        User withMeals = service.getWithMeals(ADMIN_ID);
        assertMatch(withMeals, ADMIN);
    }
}