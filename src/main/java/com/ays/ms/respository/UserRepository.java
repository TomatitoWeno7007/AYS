package com.ays.ms.respository;

import com.ays.ms.model.User;
import com.ays.ms.respository.springdata.UserSpringDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepository {

    @Autowired
    private UserSpringDataRepository userSpringDataRepository;

    public List<User> getUsers() {
        return userSpringDataRepository.findAll();
    }



}
