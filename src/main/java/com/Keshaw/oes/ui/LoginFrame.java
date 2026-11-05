package com.Keshaw.oes.ui;

import com.Keshaw.oes.dao.UserDAO;
import com.Keshaw.oes.model.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class LoginFrame extends JFrame {

    private JTextField emailField;
    private JPasswordField passwordField;

    private JButton loginButton;
    private JButton registerButton;

    // Theme colors
    private static final Color PRIMARY = new Color(37, 99, 235);
    private static final Color DARK_BLUE = new Color(30, 58, 138);
    private static final Color BACKGROUND = new Color(245, 247, 251);
    private static final Color TEXT_COLOR = new Color(31, 41, 55);
    private static final Color SECONDARY_TEXT = new Color(107, 114, 128);
    private static final Color BORDER_COLOR = new Color(209, 213, 219);

    public LoginFrame() {

        setTitle("Online Examination System - Login");
        setSize(1000, 650);
        setMinimumSize(new Dimension(850, 550));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new GridLayout(1, 2));
        mainPanel.setBackground(BACKGROUND);

        // Left welcome section
        mainPanel.add(createWelcomePanel());

        // Right login section
        mainPanel.add(createLoginPanel());

        add(mainPanel);

        setVisible(true);
    }

    // Left welcome panel
    private JPanel createWelcomePanel() {

        JPanel welcomePanel = new JPanel(new GridBagLayout());
        welcomePanel.setBackground(DARK_BLUE);
        welcomePanel.setBorder(
                new EmptyBorder(40, 40, 40, 40)
        );

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(
                new BoxLayout(contentPanel, BoxLayout.Y_AXIS)
        );
        contentPanel.setBackground(DARK_BLUE);

        JLabel logoLabel = new JLabel("OES");

        logoLabel.setFont(
                new Font("Segoe UI", Font.BOLD, 48)
        );
        logoLabel.setForeground(Color.WHITE);
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel titleLabel = new JLabel(
                "Online Examination System"
        );

        titleLabel.setFont(
                new Font("Segoe UI", Font.BOLD, 23)
        );
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitleLabel = new JLabel(
                "Smart. Simple. Secure."
        );

        subtitleLabel.setFont(
                new Font("Segoe UI", Font.PLAIN, 15)
        );
        subtitleLabel.setForeground(
                new Color(219, 234, 254)
        );
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextArea description = new JTextArea(
                "A modern platform for conducting examinations, "
                        + "managing questions and tracking student performance."
        );

        description.setFont(
                new Font("Segoe UI", Font.PLAIN, 15)
        );
        description.setForeground(
                new Color(219, 234, 254)
        );
        description.setBackground(DARK_BLUE);
        description.setLineWrap(true);
        description.setWrapStyleWord(true);
        description.setEditable(false);
        description.setFocusable(false);
        description.setOpaque(true);
        description.setMaximumSize(
                new Dimension(350, 100)
        );
        description.setAlignmentX(Component.CENTER_ALIGNMENT);

        contentPanel.add(logoLabel);
        contentPanel.add(Box.createVerticalStrut(25));
        contentPanel.add(titleLabel);
        contentPanel.add(Box.createVerticalStrut(12));
        contentPanel.add(subtitleLabel);
        contentPanel.add(Box.createVerticalStrut(35));
        contentPanel.add(description);

        welcomePanel.add(contentPanel);

        return welcomePanel;
    }

    // Right login panel
    private JPanel createLoginPanel() {

        JPanel loginPanel = new JPanel(new GridBagLayout());
        loginPanel.setBackground(BACKGROUND);
        loginPanel.setBorder(
                new EmptyBorder(35, 40, 35, 40)
        );

        JPanel formPanel = new JPanel();
        formPanel.setLayout(
                new BoxLayout(formPanel, BoxLayout.Y_AXIS)
        );
        formPanel.setBackground(BACKGROUND);

        // Heading
        JLabel heading = new JLabel("Welcome Back!");

        heading.setFont(
                new Font("Segoe UI", Font.BOLD, 30)
        );
        heading.setForeground(TEXT_COLOR);
        heading.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel subtitle = new JLabel(
                "Please login to continue"
        );

        subtitle.setFont(
                new Font("Segoe UI", Font.PLAIN, 14)
        );
        subtitle.setForeground(SECONDARY_TEXT);
        subtitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Email
        JLabel emailLabel = createFieldLabel("Email Address");

        emailField = new JTextField();
        styleTextField(emailField);

        // Password
        JLabel passwordLabel = createFieldLabel("Password");

        passwordField = new JPasswordField();
        styleTextField(passwordField);

        // Show password checkbox
        JCheckBox showPassword = new JCheckBox("Show password");

        showPassword.setFont(
                new Font("Segoe UI", Font.PLAIN, 12)
        );
        showPassword.setForeground(SECONDARY_TEXT);
        showPassword.setBackground(BACKGROUND);
        showPassword.setFocusPainted(false);
        showPassword.setAlignmentX(Component.LEFT_ALIGNMENT);

        char defaultEchoChar = passwordField.getEchoChar();

        showPassword.addActionListener(e -> {
            if (showPassword.isSelected()) {
                passwordField.setEchoChar((char) 0);
            } else {
                passwordField.setEchoChar(defaultEchoChar);
            }
        });

        // Login button
        loginButton = new JButton("Login");

        styleButton(loginButton, PRIMARY, Color.WHITE);

        loginButton.addActionListener(e -> loginUser());

        // Register section
        JLabel accountLabel = new JLabel(
                "Don't have an account?"
        );

        accountLabel.setFont(
                new Font("Segoe UI", Font.PLAIN, 13)
        );
        accountLabel.setForeground(SECONDARY_TEXT);

        registerButton = new JButton("Register Now");

        registerButton.setFont(
                new Font("Segoe UI", Font.BOLD, 13)
        );
        registerButton.setForeground(PRIMARY);
        registerButton.setBackground(BACKGROUND);
        registerButton.setBorderPainted(false);
        registerButton.setFocusPainted(false);
        registerButton.setCursor(
                new Cursor(Cursor.HAND_CURSOR)
        );

        registerButton.addActionListener(e -> {
            new RegisterFrame();
            dispose();
        });

        JPanel registerPanel = new JPanel(
                new FlowLayout(FlowLayout.CENTER, 3, 0)
        );

        registerPanel.setBackground(BACKGROUND);
        registerPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        registerPanel.add(accountLabel);
        registerPanel.add(registerButton);

        // Add components
        formPanel.add(heading);
        formPanel.add(Box.createVerticalStrut(10));

        formPanel.add(subtitle);
        formPanel.add(Box.createVerticalStrut(35));

        formPanel.add(emailLabel);
        formPanel.add(Box.createVerticalStrut(8));
        formPanel.add(emailField);

        formPanel.add(Box.createVerticalStrut(22));

        formPanel.add(passwordLabel);
        formPanel.add(Box.createVerticalStrut(8));
        formPanel.add(passwordField);

        formPanel.add(Box.createVerticalStrut(10));
        formPanel.add(showPassword);

        formPanel.add(Box.createVerticalStrut(28));
        formPanel.add(loginButton);

        formPanel.add(Box.createVerticalStrut(25));
        formPanel.add(registerPanel);

        // Center form
        loginPanel.add(formPanel);

        return loginPanel;
    }

    // Field label styling
    private JLabel createFieldLabel(String text) {

        JLabel label = new JLabel(text);

        label.setFont(
                new Font("Segoe UI", Font.BOLD, 13)
        );

        label.setForeground(TEXT_COLOR);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);

        return label;
    }

    // Text field styling
    private void styleTextField(JTextField field) {

        field.setFont(
                new Font("Segoe UI", Font.PLAIN, 14)
        );

        field.setForeground(TEXT_COLOR);
        field.setBackground(Color.WHITE);

        field.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(BORDER_COLOR),
                        new EmptyBorder(10, 12, 10, 12)
                )
        );

        field.setPreferredSize(
                new Dimension(350, 45)
        );

        field.setMaximumSize(
                new Dimension(350, 45)
        );

        field.setAlignmentX(Component.LEFT_ALIGNMENT);
    }

    // Button styling
    private void styleButton(
            JButton button,
            Color background,
            Color foreground) {

        button.setFont(
                new Font("Segoe UI", Font.BOLD, 15)
        );

        button.setForeground(foreground);
        button.setBackground(background);
        button.setFocusPainted(false);
        button.setBorderPainted(false);

        button.setCursor(
                new Cursor(Cursor.HAND_CURSOR)
        );

        button.setPreferredSize(
                new Dimension(350, 46)
        );

        button.setMaximumSize(
                new Dimension(350, 46)
        );

        button.setAlignmentX(Component.LEFT_ALIGNMENT);
    }

    // Login functionality
    private void loginUser() {

        String email = emailField.getText().trim();

        String password =
                new String(passwordField.getPassword());

        if (email.isEmpty() || password.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter email and password.",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        try {

            UserDAO userDAO = new UserDAO();

            User user = userDAO.loginUser(email, password);

            if (user != null) {

                if ("STUDENT".equalsIgnoreCase(user.getRole())) {

                    new StudentDashboard(user);

                } else if ("ADMIN".equalsIgnoreCase(user.getRole())) {

                    new AdminDashboard(user);

                } else {

                    JOptionPane.showMessageDialog(
                            this,
                            "Invalid user role.",
                            "Login Error",
                            JOptionPane.ERROR_MESSAGE
                    );

                    return;
                }

                dispose();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Invalid email or password.",
                        "Login Failed",
                        JOptionPane.ERROR_MESSAGE
                );
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to connect to the database.",
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );

            e.printStackTrace();

        } finally {

            passwordField.setText("");
        }
    }
}
