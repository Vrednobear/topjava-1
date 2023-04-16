package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.web.SecurityUtil.*;
@Repository
public class InMemoryUserRepository implements UserRepository {
	Map<Integer, User> userRepository = new HashMap<>();
	AtomicInteger counter = new AtomicInteger();
	private static final Logger log = LoggerFactory.getLogger(InMemoryUserRepository.class);

	public void init(){
		userRepository.put(USER_ID, user);
		userRepository.put(ADMIN_ID, admin);
	}

	@Override
	public boolean delete(int id) {
		log.info("delete {}", id);
		return (userRepository.remove(id) != null);
	}

	@Override
	public User save(User user) {
		log.info("save {}", user);
		if(user.isNew()) {
			user.setId(counter.incrementAndGet());
		   return userRepository.put(user.getId(), user);
		}
		else {
		   return userRepository.computeIfPresent(user.getId(), (id, oldUser) -> user);
		}
	}

	@Override
	public User get(int id) {
		log.info("get {}", id);
		return userRepository.get(id);
	}

	@Override
	public List<User> getAll() {
		log.info("getAll");
		return userRepository.values().stream()
				.sorted(Comparator.comparing((User::getName))
				.thenComparing(User::getEmail))
				.collect(Collectors.toList());
	}

	@Override
	public User getByEmail(String email) {
		log.info("getByEmail {}", email);
		return userRepository.values().stream()
				.filter(u -> u.getEmail()
				.equals(email))
                .findFirst().orElse(null);
	}
}
