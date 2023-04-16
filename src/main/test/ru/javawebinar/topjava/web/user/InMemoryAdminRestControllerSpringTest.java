package ru.javawebinar.topjava.web.user;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.repository.inmemory.InMemoryUserRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import static ru.javawebinar.topjava.web.SecurityUtil.NOT_FOUND;
import static ru.javawebinar.topjava.web.SecurityUtil.USER_ID;

@ContextConfiguration("classpath:spring/spring-app.xml")
@RunWith(SpringRunner.class)
public class InMemoryAdminRestControllerSpringTest {
    private static final Logger log = LoggerFactory.getLogger(InMemoryAdminRestControllerSpringTest.class);

    @Autowired
    private AdminRestController controller;
    @Autowired
    private InMemoryUserRepository repository;

    @Before
    public void setUp() {
        repository.init();
	}

	@Test
    public void delete() {
		controller.delete(USER_ID);
		Assert.assertNull(repository.get(USER_ID));
		}

	@Test
    public void deleteNotFound() {
		Assert.assertThrows(NotFoundException.class, () -> controller.delete(NOT_FOUND));
		}
}
