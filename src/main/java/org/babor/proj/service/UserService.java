package org.babor.proj.service;

import org.babor.proj.dao.UserDao;
import org.babor.proj.entity.User;
import org.springframework.stereotype.Component;

import java.sql.SQLException;

@Component
public class UserService {
    private final UserDao userDao;
    public UserService(UserDao userDao){
        this.userDao = userDao;
    }
    public void save(User user) throws SQLException, ClassNotFoundException {
        userDao.save(user);
    }
}
