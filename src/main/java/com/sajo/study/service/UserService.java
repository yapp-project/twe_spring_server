package com.sajo.study.service;

import com.sajo.study.model.User;
import com.sajo.study.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class UserService {
    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserByIdx(long userIdx) {
        return userRepository.findById(userIdx).orElse(new User());
    }

    public User makeUser(User u) {
        return userRepository.save(u);
    }

}
