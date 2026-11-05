package com.Keshaw.oes.ui;

import com.Keshaw.oes.dao.UserDAO;
import com.Keshaw.oes.model.User;

import javax.swing.*;
import java.awt.*;

public class RegisterFrame extends JFrame {

    private JTextField nameField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;

    private JButton registerButton;
    private JButton loginButton;

    public RegisterFrame() {

        setTitle("Online Examination System - Registration");
        setSize(500, 430);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(
                20, 30, 20, 30));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Heading
        JLabel heading = new JLabel(
                "Student Registration",
                SwingConstants.CENTER);

        heading.setFont(new Font("Arial", Font.BOLD, 22));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(heading, gbc);

        gbc.gridwidth = 1;

        // Full Name
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Full Name:"), gbc);

        nameField = new JTextField(20);

        gbc.gridx = 1;
        panel.add(nameField, gbc);

        // Email
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Email:"), gbc);

        emailField = new JTextField(20);

        gbc.gridx = 1;
        panel.add(emailField, gbc);

        // Password
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(new JLabel("Password:"), gbc);

        passwordField = new JPasswordField(20);

        gbc.gridx = 1;
        panel.add(passwordField, gbc);

        // Confirm Password
        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(new JLabel("Confirm Password:"), gbc);

        confirmPasswordField = new JPasswordField(20);

        gbc.gridx = 1;
        panel.add(confirmPasswordField, gbc);

        // Register Button
        registerButton = new JButton("Register");

        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(registerButton, gbc);

        // Login Button
        loginButton = new JButton("Back to Login");

        gbc.gridx = 1;
        panel.add(loginButton, gbc);

        registerButton.addActionListener(e -> registerUser());

        loginButton.addActionListener(e -> {
            new LoginFrame();
            dispose();
        });

        add(panel);
        setVisible(true);
    }

    private void registerUser() {

        String fullName = nameField.getText().trim();
        String email = emailField.getText().trim();

        String password =
                new String(passwordField.getPassword());

        String confirmPassword =
                new String(confirmPasswordField.getPassword());

        // Empty field validation
        if (fullName.isEmpty() || email.isEmpty()
                || password.isEmpty()
                || confirmPassword.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please fill in all fields.",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        // Password validation
        if (!password.equals(confirmPassword)) {

            JOptionPane.showMessageDialog(
                    this,
                    "Passwords do not match.",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        // Email validation
        if (!email.matches(
                "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a valid email address.",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        try {

            User user = new User();

            user.setFullName(fullName);
            user.setEmail(email);

            // UserDAO hashes this password using BCrypt.
            user.setPasswordHash(password);

            UserDAO userDAO = new UserDAO();

            boolean registered = userDAO.registerUser(user);

            if (registered) {

                JOptionPane.showMessageDialog(
                        this,
                        "Registration successful! Please login."
                );

                new LoginFrame();
                dispose();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Registration failed."
                );
            }

        } catch (java.sql.SQLIntegrityConstraintViolationException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "This email is already registered.",
                    "Registration Error",
                    JOptionPane.ERROR_MESSAGE
            );

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to complete registration.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );

            e.printStackTrace();

        } finally {

            passwordField.setText("");
            confirmPasswordField.setText("");
        }
    }
}
