package ru.javawebinar.topjava.web.user;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@ContextConfiguration("classpath:spring/spring-app.xml")
@RunWith(SpringRunner.class)
public class InMemoryAdminRestControllerSpringTest {
//    private static final Logger log = LoggerFactory.getLogger(InMemoryAdminRestControllerSpringTest.class);
//
//    @Autowired
//    private AdminRestController controller;
//    @Autowired
//    private InMemoryUserRepository repository;
//
//    @Before
//    public void setUp() {
//        repository.init();
//	}
//
//	@Test
//    public void delete() {
//		controller.delete(USER_ID);
//		Assert.assertNull(repository.get(USER_ID));
//		}
//
//	@Test
//    public void deleteNotFound() {
//		Assert.assertThrows(NotFoundException.class, () -> controller.delete(NOT_FOUND));
//		}
}
