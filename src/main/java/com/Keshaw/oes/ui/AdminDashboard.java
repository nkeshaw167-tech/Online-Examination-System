
package com.Keshaw.oes.ui;

import com.Keshaw.oes.model.User;

import javax.swing.*;
import java.awt.*;

public class AdminDashboard extends JFrame {

    private final User loggedInUser;

    // Theme colors
    private static final Color PRIMARY = new Color(37, 99, 235);
    private static final Color DARK_BLUE = new Color(30, 58, 138);
    private static final Color BACKGROUND = new Color(245, 247, 251);
    private static final Color CARD_BACKGROUND = Color.WHITE;
    private static final Color TEXT_COLOR = new Color(31, 41, 55);
    private static final Color SECONDARY_TEXT = new Color(107, 114, 128);
    private static final Color BORDER_COLOR = new Color(229, 231, 235);

    public AdminDashboard(User user) {

        this.loggedInUser = user;

        setTitle("Online Examination System - Admin Dashboard");
        setSize(1100, 700);
        setMinimumSize(new Dimension(900, 600));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        getContentPane().setBackground(BACKGROUND);

        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout(0, 25));
        mainPanel.setBackground(BACKGROUND);
        mainPanel.setBorder(
                BorderFactory.createEmptyBorder(25, 30, 25, 30)
        );

        // Header
        mainPanel.add(createHeaderPanel(), BorderLayout.NORTH);

        // Center content
        JPanel centerPanel = new JPanel(new BorderLayout(0, 20));
        centerPanel.setBackground(BACKGROUND);

        JLabel sectionHeading = new JLabel("Administration");
        sectionHeading.setFont(
                new Font("Segoe UI", Font.BOLD, 23)
        );
        sectionHeading.setForeground(TEXT_COLOR);

        JLabel sectionSubtitle = new JLabel(
                "Manage examinations, questions and student performance."
        );
        sectionSubtitle.setFont(
                new Font("Segoe UI", Font.PLAIN, 14)
        );
        sectionSubtitle.setForeground(SECONDARY_TEXT);

        JPanel headingPanel = new JPanel();
        headingPanel.setLayout(
                new BoxLayout(headingPanel, BoxLayout.Y_AXIS)
        );
        headingPanel.setBackground(BACKGROUND);

        headingPanel.add(sectionHeading);
        headingPanel.add(Box.createVerticalStrut(6));
        headingPanel.add(sectionSubtitle);

        centerPanel.add(headingPanel, BorderLayout.NORTH);

        // Dashboard cards
        JPanel cardPanel = new JPanel(
                new GridLayout(2, 2, 22, 22)
        );
        cardPanel.setBackground(BACKGROUND);

        cardPanel.add(createActionCard(
                "Add New Examination",
                "Create a new examination by adding its title, description and duration.",
                "Add Exam",
                () -> new AddExamFrame(),
                PRIMARY
        ));

        cardPanel.add(createActionCard(
                "Manage Examinations",
                "View, edit, activate, deactivate or delete existing examinations.",
                "Manage Exams",
                () -> new ManageExamFrame(),
                new Color(22, 163, 74)
        ));

        cardPanel.add(createActionCard(
                "Manage Questions",
                "Add, update and delete questions belonging to examinations.",
                "Manage Questions",
                () -> new ManageQuestionFrame(),
                new Color(124, 58, 237)
        ));

        cardPanel.add(createActionCard(
                "Student Results",
                "View student examination records and analyze their performance.",
                "View Results",
                () -> new AdminResultFrame(),
                new Color(234, 88, 12)
        ));

        centerPanel.add(cardPanel, BorderLayout.CENTER);

        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // Footer
        JLabel footerLabel = new JLabel(
                "Online Examination System | Administrator Panel"
        );

        footerLabel.setFont(
                new Font("Segoe UI", Font.PLAIN, 12)
        );
        footerLabel.setForeground(SECONDARY_TEXT);

        JPanel footerPanel = new JPanel(
                new BorderLayout()
        );
        footerPanel.setBackground(BACKGROUND);
        footerPanel.add(footerLabel, BorderLayout.WEST);

        mainPanel.add(footerPanel, BorderLayout.SOUTH);

        add(mainPanel);

        setVisible(true);
    }

    // Header panel
    private JPanel createHeaderPanel() {

        JPanel headerPanel = new JPanel(new BorderLayout());

        headerPanel.setBackground(DARK_BLUE);
        headerPanel.setBorder(
                BorderFactory.createEmptyBorder(22, 25, 22, 25)
        );

        // Welcome text
        JLabel welcomeLabel = new JLabel(
                "Welcome, " + loggedInUser.getFullName()
        );

        welcomeLabel.setFont(
                new Font("Segoe UI", Font.BOLD, 26)
        );
        welcomeLabel.setForeground(Color.WHITE);

        JLabel subtitleLabel = new JLabel(
                "Administrator Dashboard"
        );

        subtitleLabel.setFont(
                new Font("Segoe UI", Font.PLAIN, 14)
        );
        subtitleLabel.setForeground(new Color(219, 234, 254));

        JPanel textPanel = new JPanel();
        textPanel.setLayout(
                new BoxLayout(textPanel, BoxLayout.Y_AXIS)
        );
        textPanel.setBackground(DARK_BLUE);

        textPanel.add(welcomeLabel);
        textPanel.add(Box.createVerticalStrut(5));
        textPanel.add(subtitleLabel);

        headerPanel.add(textPanel, BorderLayout.WEST);

        // Logout button
        JButton logoutButton = new JButton("Logout");

        logoutButton.setFont(
                new Font("Segoe UI", Font.BOLD, 14)
        );
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setBackground(new Color(220, 53, 69));
        logoutButton.setFocusPainted(false);
        logoutButton.setBorderPainted(false);
        logoutButton.setCursor(
                new Cursor(Cursor.HAND_CURSOR)
        );
        logoutButton.setPreferredSize(
                new Dimension(115, 42)
        );

        logoutButton.addActionListener(e -> {
            new LoginFrame();
            dispose();
        });

        headerPanel.add(logoutButton, BorderLayout.EAST);

        return headerPanel;
    }

    // Dashboard action card
    private JPanel createActionCard(
            String title,
            String description,
            String buttonText,
            Runnable action,
            Color accentColor) {

        JPanel card = new JPanel(new BorderLayout(10, 12));

        card.setBackground(CARD_BACKGROUND);

        card.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(BORDER_COLOR),
                        BorderFactory.createEmptyBorder(22, 22, 22, 22)
                )
        );

        // Accent line
        JPanel accentPanel = new JPanel();
        accentPanel.setBackground(accentColor);
        accentPanel.setPreferredSize(
                new Dimension(5, 1)
        );

        // Card title
        JLabel titleLabel = new JLabel(title);

        titleLabel.setFont(
                new Font("Segoe UI", Font.BOLD, 19)
        );
        titleLabel.setForeground(TEXT_COLOR);

        // Card description
        JLabel descriptionLabel = new JLabel(
                "<html><div style='width:300px;'>"
                        + description +
                        "</div></html>"
        );

        descriptionLabel.setFont(
                new Font("Segoe UI", Font.PLAIN, 13)
        );
        descriptionLabel.setForeground(SECONDARY_TEXT);

        // Button
        JButton actionButton = new JButton(buttonText);

        actionButton.setFont(
                new Font("Segoe UI", Font.BOLD, 13)
        );
        actionButton.setForeground(Color.WHITE);
        actionButton.setBackground(accentColor);
        actionButton.setFocusPainted(false);
        actionButton.setBorderPainted(false);
        actionButton.setCursor(
                new Cursor(Cursor.HAND_CURSOR)
        );
        actionButton.setPreferredSize(
                new Dimension(160, 40)
        );

        actionButton.addActionListener(e -> action.run());

        // Card content
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(
                new BoxLayout(contentPanel, BoxLayout.Y_AXIS)
        );
        contentPanel.setBackground(CARD_BACKGROUND);

        contentPanel.add(titleLabel);
        contentPanel.add(Box.createVerticalStrut(12));
        contentPanel.add(descriptionLabel);
        contentPanel.add(Box.createVerticalGlue());
        contentPanel.add(Box.createVerticalStrut(15));
        contentPanel.add(actionButton);

        card.add(accentPanel, BorderLayout.WEST);
        card.add(contentPanel, BorderLayout.CENTER);

        return card;
    }
}
