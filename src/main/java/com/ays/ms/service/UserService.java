package com.ays.ms.service;

import com.ays.ms.controller.dto.request.UserRegisterRequest;
import com.ays.ms.model.User;
import com.ays.ms.respository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getUsers() {
        return userRepository.getUsers();
    }

    public void save(UserRegisterRequest userRegisterRequest) {

        //Mapeo a mano, hay que mirar ModelMapper
        User user = new User();
        user.setEmail(userRegisterRequest.getEmail());
        //pas
        //pas

        this.userRepository.save(user);
    }
}
