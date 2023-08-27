package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import java.util.List;

@Repository
@Qualifier("data")
public class DataJpaUserRepositoryImpl implements UserRepository {

    private static final Sort SORT_NAME_EMAIL = Sort.by(Sort.Direction.ASC, "name", "email");

    @Autowired
    ProxyUserRepository proxyUserRepository;

    @Override
    public User save(User user) {
        return proxyUserRepository.save(user);
    }

    @Override
    public boolean delete(int id) {
       return proxyUserRepository.delete(id) != 0;
    }

    @Override
    public User get(int id) {
        return proxyUserRepository.findById(id).orElse(null);
    }

    @Override
    public User getByEmail(String email) {
        User a = proxyUserRepository.getByEmail(email);
        return a;
    }

    @Override
    public List<User> getAll() {
        return proxyUserRepository.findAll(SORT_NAME_EMAIL);
    }
}
