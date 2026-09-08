package com.codegnan.service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

import com.codegnan.dao.UserDAO;
import com.codegnan.exception.AuthenticationException;
import com.codegnan.model.User;


public class AuthenticationService {
	 private UserDAO userDAO = new UserDAO();

	    // Register new user
	    public User register(String username, String password) {

	        if (username == null || username.trim().isEmpty()) {
	            throw new IllegalArgumentException(
	                    "Username cannot be empty.");
	        }

	        if (password == null || password.length() < 6) {
	            throw new IllegalArgumentException(
	                    "Password must contain at least 6 characters.");
	        }

	        if (userDAO.findByUsername(username) != null) {
	            throw new IllegalArgumentException(
	                    "Username already exists.");
	        }

	        String passwordHash = hashPassword(password);

	        return userDAO.save(username, passwordHash);
	    }

	    // Login user
	    public User login(String username, String password)
	            throws AuthenticationException {

	        User user = userDAO.findByUsername(username);

	        if (user == null ||
	            !user.getPasswordHash()
	                 .equals(hashPassword(password))) {

	            throw new AuthenticationException(
	                    "Invalid username or password.");
	        }

	        return user;
	    }

	    // Password hashing
	    private String hashPassword(String password) {

	        try {

	            MessageDigest md =
	                    MessageDigest.getInstance("SHA-256");

	            byte[] result =
	                    md.digest(password.getBytes(
	                            StandardCharsets.UTF_8));

	            StringBuilder hex = new StringBuilder();

	            for (byte b : result) {
	                hex.append(String.format("%02x", b));
	            }

	            return hex.toString();

	        } catch (Exception e) {

	            throw new RuntimeException(
	                    "Password hashing failed.");
	        }
	    }
}
