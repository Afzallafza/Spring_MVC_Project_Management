package org.babor.proj.dao;

import org.babor.proj.configuration.DbConfig;
import org.babor.proj.entity.User;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Component
public class UserDao {


    public void save(User user) throws SQLException,ClassNotFoundException {
        String query= "insert into User(name,email,password,username) values(?,?,?,?)";
        try(Connection con= DbConfig.getConnection();
            PreparedStatement preparedStatement= con.prepareStatement(query)
        ){
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setString(4, user.getUsername());
            preparedStatement.executeUpdate();
        }

    }
}
