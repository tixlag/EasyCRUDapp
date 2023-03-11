package app.service;

import app.dao.UserDao;
import app.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Component
@Transactional
public class UserServiceImp implements UserService {

    @Autowired
    UserDao userDao;
    @Override
    public void add(User user) {
        userDao.add(user);
    }

    @Override
    public User get(int id) {
        return userDao.get(id);
    }

    @Override
    public List<User> getAll() {
        return userDao.getAll();
    }

    @Override
    public void edit(Long id, User user) {
        userDao.edit(id, user);
    }

    @Override
    public void edit(Long id, String name, String lastName, byte age) {
        userDao.edit(id, name, lastName, age);
    }

    @Override
    public void delete(Long id) {
        userDao.delete(id);
    }
}
