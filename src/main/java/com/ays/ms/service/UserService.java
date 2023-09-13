package com.ays.ms.service;

import com.ays.ms.controller.dto.request.UserRegisterRequest;
import com.ays.ms.model.User;
import com.ays.ms.respository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<User> getUsers() {
        return userRepository.getUsers();
    }

    public void save(UserRegisterRequest userRegisterRequest) {
        User user = modelMapper.map(userRegisterRequest, User.class);
        this.userRepository.save(user);
    }
}
