package ru.javawebinar.topjava.repository.jpa;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Qualifier("jpa")
@Transactional(readOnly = true)
public class HibernateUserRepositoryImpl implements UserRepository {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    @Transactional
    public User save(User user) {
       if(user.isNew())
           entityManager.persist(user);
       else
           entityManager.merge(user);

       return user;
    }

    @Override
    @Transactional
    public boolean delete(int id) {
        //1st meth
//        User reference = entityManager.getReference(User.class, id);
//        entityManager.remove(reference);

        //2nd
//        TypedQuery<User> query = entityManager.createQuery("DELETE from User u where u.id=:id", User.class);
//        return query.setParameter("id", id).executeUpdate() != 0;

        //3d
        return entityManager.createNamedQuery(User.DELETE).setParameter("id", id).executeUpdate() != 0;
    }

    @Override
    public User get(int id) {
      return entityManager.find(User.class,id);
    }

    @Override
    public User getByEmail(String email) {
      return entityManager.createNamedQuery(User.BY_EMAIL, User.class).setParameter("email", email).getSingleResult();
    }

    @Override
    public List<User> getAll() {
        return entityManager.createNamedQuery(User.ALL_SORTED, User.class).getResultList();
    }
}
