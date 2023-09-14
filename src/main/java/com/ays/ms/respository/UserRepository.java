package com.ays.ms.respository;

import com.ays.ms.exceptions.ResourceNotFoundException;
import com.ays.ms.model.User;
import com.ays.ms.respository.springdata.UserSpringDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository {

    @Autowired
    private UserSpringDataRepository userSpringDataRepository;

    public List<User> getUsers() {
        return userSpringDataRepository.findAll();
    }

    public void save(User user) {
        this.userSpringDataRepository.save(user);
    }

    public User getByEmailAndPassword(String email, String password) {
        Optional<User> optionalUser = this.userSpringDataRepository.findByEmailAndPassword(email, password);
        return optionalUser.orElse(null);
    }

    public User getById(long id) {
        Optional<User> optionalUser = this.userSpringDataRepository.findById(id);

        if(optionalUser.isEmpty()) {
            throw new ResourceNotFoundException("El usuario no existe.");
        }

        return optionalUser.get();
    }

}
