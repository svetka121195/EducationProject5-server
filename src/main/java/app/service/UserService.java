package app.service;

import app.model.User;

import java.util.List;


public interface UserService {

    String getUserId(String login);

    void addUser(User user);

    User getUser(String id);

    User getUserByLogin(String login);

    void deleteUser(String id);

    void updateUser(User newUser);

    List<User> getAllUsers();
}
