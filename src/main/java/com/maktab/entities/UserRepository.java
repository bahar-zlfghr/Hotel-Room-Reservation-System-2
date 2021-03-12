package com.maktab.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRepository {
    private static final List<User> users = new ArrayList<>();

    public void addUser(List<User> users) {
        UserRepository.users.addAll(users);
    }

    public User findUser(String userName, String password) {
        Optional<User> user = users.stream().filter(u -> u.getUserName().equals(userName) && u.getPassword().equals(password)).findFirst();
        if (user.isPresent()) {
            return user.get();
        }
        else {
            return null;
        }
    }

    public Optional<User> getUserByNationalCode(String userName) {
        return users.stream().filter(user -> user.getUserName().equals(userName)).findFirst();
    }

    public void clearUsers() {
        users.clear();
    }
}
