package app.dao;

import org.springframework.stereotype.Component;
import app.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Component
public class UserDaoImp implements UserDao {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public void add(User user) {
        manager.persist(user);
    }

    @Override
    public User get(int id) {
        return manager.find(User.class, (long) id);
    }

    @Override
    public List<User> getAll() {
        return (List<User>) manager.createQuery("from User").getResultList();
    }

    @Override
    public void edit(Long id, String name, String lastName, byte age) {
        User currentUser = manager.find(User.class, id);
        currentUser.setName(name);
        currentUser.setLastName(lastName);
        currentUser.setAge(age);
    }

    @Override
    public void delete(Long id) {
        manager.createQuery("delete User where id=?1").setParameter("1", id);
    }
}
