package org.babor.dumo.service;


import lombok.RequiredArgsConstructor;
import org.babor.dumo.dao.UserDao;
import org.babor.dumo.dto.LoginDto;
import org.babor.dumo.dto.UserRegistrationDto;
import org.babor.dumo.entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class UserService {
    private final UserDao userDao;
    private final ModelMapper modelMapper;

    public void save(UserRegistrationDto userRegistrationDto) {
        userDao.save(modelMapper.map(userRegistrationDto, User.class));
    }

    public boolean validateLogin(LoginDto loginDto) {
        User user = userDao.findByUsername(loginDto.getUsername());
        return (user.getPassword().equals(loginDto.getPassword()));
    }

    public List<User> findAll() {
        return userDao.findAll();
    }

    public User findById(Integer id) {
        return userDao.findById(id);
    }

    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    public List<User> findUserByProjectId(int projectId) {
        return userDao.findByProjectId(projectId);
    }
}
