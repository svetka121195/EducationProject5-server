package app.dao;

import app.model.User;

import java.util.List;

/**
 * Created by Светлана on 16.07.2018.
 */
public interface UsersDao {
    String getUserId(String login) throws Exception;

    void addUser(User user) throws Exception;

    User getUserByLogin(String login) throws Exception;

    User getUserById(String id) throws Exception;

    void deleteUser(String id) throws Exception;

    void updateUser(User user) throws Exception;

    List<User> getAllUsers() throws Exception;

    /*void createTable() throws DBException;

    void dropTable() throws DBException;*/
}


