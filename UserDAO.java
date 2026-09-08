package com.codegnan.dao;
import java.util.HashMap;
import java.util.Map;

import com.codegnan.model.User;
public class UserDAO {
	private Map<String, User> users = new HashMap<>();

    private int nextUserId = 1001;

    // Save user
    public User save(String username, String passwordHash) {

        User user = new User( nextUserId,username,passwordHash);
        users.put(username, user);
        nextUserId++;
        return user;
    }
    // Find user by username
    public User findByUsername(String username) {
        return users.get(username);
    }
    // Check whether username exists
    public boolean exists(String username) {

        return users.containsKey(username);
    }
}
