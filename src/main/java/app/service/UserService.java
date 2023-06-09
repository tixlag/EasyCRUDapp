package app.service;

import app.model.User;

import java.util.List;

public interface UserService {
    void add(User user);
    User get(int id);
    List<User> getAll();

    void edit(Long id, String name, String lastName, byte age);

    void delete(Long id);
}
