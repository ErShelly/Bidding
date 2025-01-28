package com.intuit.bidding.service;

import com.intuit.bidding.exception.ErrorMessages;
import com.intuit.bidding.exception.UserNotFoundException;
import com.intuit.bidding.model.User;
import com.intuit.bidding.repository.UserRepository;
import org.aspectj.apache.bcel.classfile.Module;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User user) throws Exception {
        Optional<User> userExists = userRepository.findByEmailId(user.getEmailId());

        if (userExists.isPresent()) throw new Exception("User already exists with this email.");

        return userRepository.save(user);
    }

    public User findById(long id) throws UserNotFoundException {
        Optional<User> user = userRepository.findById(id);

        if (!user.isPresent()) throw new UserNotFoundException(ErrorMessages.USER_NOT_FOUND);
        return user.get();
    }
}
