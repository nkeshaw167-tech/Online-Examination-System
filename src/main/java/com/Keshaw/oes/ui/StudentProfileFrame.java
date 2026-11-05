package com.Keshaw.oes.ui;

import com.Keshaw.oes.model.User;

import javax.swing.*;
import java.awt.*;

public class StudentProfileFrame extends JFrame {

    private final User loggedInUser;

    private static final Color PRIMARY = new Color(37, 99, 235);
    private static final Color DARK_BLUE = new Color(30, 58, 138);
    private static final Color BACKGROUND = new Color(245, 247, 251);
    private static final Color TEXT_COLOR = new Color(31, 41, 55);
    private static final Color SECONDARY_TEXT = new Color(107, 114, 128);

    public StudentProfileFrame(User user) {

        this.loggedInUser = user;

        setTitle("My Profile - Online Examination System");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(BACKGROUND);

        JPanel mainPanel = new JPanel(new BorderLayout(15, 20));
        mainPanel.setBackground(BACKGROUND);
        mainPanel.setBorder(
                BorderFactory.createEmptyBorder(25, 35, 25, 35)
        );

        // Header
        JLabel heading = new JLabel("My Profile");
        heading.setFont(new Font("Segoe UI", Font.BOLD, 25));
        heading.setForeground(DARK_BLUE);

        JLabel subtitle = new JLabel(
                "View your account information"
        );
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        subtitle.setForeground(SECONDARY_TEXT);

        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(
                new BoxLayout(headerPanel, BoxLayout.Y_AXIS)
        );
        headerPanel.setBackground(BACKGROUND);

        headerPanel.add(heading);
        headerPanel.add(Box.createVerticalStrut(5));
        headerPanel.add(subtitle);

        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Profile card
        JPanel profileCard = new JPanel();
        profileCard.setLayout(
                new BoxLayout(profileCard, BoxLayout.Y_AXIS)
        );
        profileCard.setBackground(Color.WHITE);

        profileCard.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(229, 231, 235)
                        ),
                        BorderFactory.createEmptyBorder(
                                25, 30, 25, 30
                        )
                )
        );

        // Profile icon
        JLabel profileIcon = new JLabel("STUDENT");
        profileIcon.setFont(
                new Font("Segoe UI", Font.BOLD, 15)
        );
        profileIcon.setForeground(Color.WHITE);
        profileIcon.setBackground(PRIMARY);
        profileIcon.setOpaque(true);
        profileIcon.setBorder(
                BorderFactory.createEmptyBorder(12, 20, 12, 20)
        );
        profileIcon.setAlignmentX(Component.CENTER_ALIGNMENT);

        profileCard.add(profileIcon);
        profileCard.add(Box.createVerticalStrut(25));

        addProfileField(profileCard, "Full Name",
                loggedInUser.getFullName());

        addProfileField(profileCard, "Email Address",
                loggedInUser.getEmail());

        addProfileField(profileCard, "Account Role",
                loggedInUser.getRole());

        mainPanel.add(profileCard, BorderLayout.CENTER);

        JButton changePasswordButton = new JButton("Change Password");

        changePasswordButton.setFont(
                new Font("Segoe UI", Font.BOLD, 14)
        );

        changePasswordButton.setForeground(Color.WHITE);
        changePasswordButton.setBackground(PRIMARY);
        changePasswordButton.setFocusPainted(false);
        changePasswordButton.setBorderPainted(false);
        changePasswordButton.setCursor(
                new Cursor(Cursor.HAND_CURSOR)
        );

        changePasswordButton.addActionListener(e -> {
            openChangePasswordDialog();
        });

        // Back button
        JButton backButton = new JButton("Back to Dashboard");
        backButton.setFont(
                new Font("Segoe UI", Font.BOLD, 14)
        );
        backButton.setForeground(Color.WHITE);
        backButton.setBackground(PRIMARY);
        backButton.setFocusPainted(false);
        backButton.setBorderPainted(false);
        backButton.setCursor(
                new Cursor(Cursor.HAND_CURSOR)
        );

        backButton.addActionListener(e -> dispose());

        JPanel buttonPanel = new JPanel(
                new FlowLayout(FlowLayout.CENTER)
        );
        buttonPanel.setBackground(BACKGROUND);
        buttonPanel.add(changePasswordButton);
        buttonPanel.add(backButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);

        setVisible(true);
    }

    private void addProfileField(
            JPanel panel,
            String fieldName,
            String fieldValue) {

        JLabel nameLabel = new JLabel(fieldName);
        nameLabel.setFont(
                new Font("Segoe UI", Font.BOLD, 13)
        );
        nameLabel.setForeground(SECONDARY_TEXT);

        JLabel valueLabel = new JLabel(
                fieldValue == null ? "Not available" : fieldValue
        );
        valueLabel.setFont(
                new Font("Segoe UI", Font.PLAIN, 16)
        );
        valueLabel.setForeground(TEXT_COLOR);

        panel.add(nameLabel);
        panel.add(Box.createVerticalStrut(5));
        panel.add(valueLabel);
        panel.add(Box.createVerticalStrut(20));
    }


    private void openChangePasswordDialog() {

        JPasswordField currentPasswordField = new JPasswordField(20);
        JPasswordField newPasswordField = new JPasswordField(20);
        JPasswordField confirmPasswordField = new JPasswordField(20);

        JPanel panel = new JPanel(new GridLayout(0, 1, 5, 5));

        panel.add(new JLabel("Current Password:"));
        panel.add(currentPasswordField);

        panel.add(new JLabel("New Password:"));
        panel.add(newPasswordField);

        panel.add(new JLabel("Confirm New Password:"));
        panel.add(confirmPasswordField);

        int choice = JOptionPane.showConfirmDialog(
                this,
                panel,
                "Change Password",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (choice != JOptionPane.OK_OPTION) {
            return;
        }

        String currentPassword =
                new String(currentPasswordField.getPassword());

        String newPassword =
                new String(newPasswordField.getPassword());

        String confirmPassword =
                new String(confirmPasswordField.getPassword());

        // Validate empty fields
        if (currentPassword.isEmpty()
                || newPassword.isEmpty()
                || confirmPassword.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please fill in all password fields.",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        // Check new password length
        if (newPassword.length() < 8) {

            JOptionPane.showMessageDialog(
                    this,
                    "New password must contain at least 8 characters.",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        // Check password confirmation
        if (!newPassword.equals(confirmPassword)) {

            JOptionPane.showMessageDialog(
                    this,
                    "New password and confirm password do not match.",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        // Prevent reusing the current password
        if (currentPassword.equals(newPassword)) {

            JOptionPane.showMessageDialog(
                    this,
                    "New password must be different from current password.",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        // Update password using UserDAO
        try {

            com.Keshaw.oes.dao.UserDAO userDAO =
                    new com.Keshaw.oes.dao.UserDAO();

            boolean updated = userDAO.changePassword(
                    loggedInUser.getUserId(),
                    currentPassword,
                    newPassword
            );

            if (updated) {

                JOptionPane.showMessageDialog(
                        this,
                        "Password changed successfully!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE
                );

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Current password is incorrect.",
                        "Password Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to change password. Please try again.",
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );

            e.printStackTrace();
        }
    }

}
