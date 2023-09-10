package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class UserTestData {
    public static final int USER_ID = START_SEQ;
    public static final int ADMIN_ID = START_SEQ + 1;

    public static final User USER = new User(USER_ID, "User", "user@gmail.com","password", Role.USER);
    public static final User ADMIN = new User(ADMIN_ID, "Admin", "admin@gmail.com","password", Role.ADMIN);

    public static User getNewUser(){
        return new User(null,"NewUser","new@gmail.com", "newPass", 1599,
                true, new Date(), Collections.singletonList(Role.USER));
    }

    public static User getUpdated() {
        User updated = new User(USER);
        updated.setEmail("update@gmail.com");
        updated.setName("UpdatedName");
        updated.setCaloriesPerDay(330);
        updated.setPassword("newPass");
        updated.setEnabled(false);
        updated.setRoles(Collections.singletonList(Role.ADMIN));
        return updated;
    }

    public static void assertMatch(Iterable<User> actual, User... expected){
        assertMatch(actual,Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<User> actual, Iterable<User> expected){
        assertThat(actual).usingElementComparatorIgnoringFields("registered", "roles", "meals").isEqualTo(expected);
    }

    public static void assertMatch(User actual, User expected){
            assertThat(actual).isEqualToIgnoringGivenFields(expected, "registered", "roles", "meals");
    }

}
