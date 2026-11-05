
package com.Keshaw.oes.ui;

import com.Keshaw.oes.dao.ExamDAO;
import com.Keshaw.oes.model.Exam;
import com.Keshaw.oes.model.User;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class StudentDashboard extends JFrame {

    private final User loggedInUser;
    private final JPanel examPanel;

    // Theme colors
    private static final Color PRIMARY = new Color(37, 99, 235);
    private static final Color DARK_BLUE = new Color(30, 58, 138);
    private static final Color BACKGROUND = new Color(245, 247, 251);
    private static final Color CARD_BACKGROUND = Color.WHITE;
    private static final Color TEXT_COLOR = new Color(31, 41, 55);
    private static final Color SECONDARY_TEXT = new Color(107, 114, 128);
    private static final Color BORDER_COLOR = new Color(229, 231, 235);

    public StudentDashboard(User user) {

        this.loggedInUser = user;

        setTitle("Online Examination System - Student Dashboard");
        setSize(1050, 700);
        setMinimumSize(new Dimension(850, 600));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(BACKGROUND);

        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout(0, 25));
        mainPanel.setBackground(BACKGROUND);
        mainPanel.setBorder(
                BorderFactory.createEmptyBorder(25, 35, 30, 35)
        );

        // Header
        JPanel headerPanel = createHeaderPanel();
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Center content
        JPanel centerPanel = new JPanel(new BorderLayout(0, 18));
        centerPanel.setBackground(BACKGROUND);

        // Section heading
        JPanel headingPanel = new JPanel(new BorderLayout());
        headingPanel.setBackground(BACKGROUND);

        JLabel examHeading = new JLabel("Available Examinations");
        examHeading.setFont(new Font("Segoe UI", Font.BOLD, 23));
        examHeading.setForeground(TEXT_COLOR);

        JLabel subtitle = new JLabel(
                "Choose an examination and test your knowledge."
        );
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        subtitle.setForeground(SECONDARY_TEXT);

        JPanel headingTextPanel = new JPanel();
        headingTextPanel.setLayout(
                new BoxLayout(headingTextPanel, BoxLayout.Y_AXIS)
        );
        headingTextPanel.setBackground(BACKGROUND);

        headingTextPanel.add(examHeading);
        headingTextPanel.add(Box.createVerticalStrut(5));
        headingTextPanel.add(subtitle);

        headingPanel.add(headingTextPanel, BorderLayout.WEST);

        centerPanel.add(headingPanel, BorderLayout.NORTH);

        // Examination cards
        examPanel = new JPanel();
        examPanel.setLayout(
                new BoxLayout(examPanel, BoxLayout.Y_AXIS)
        );
        examPanel.setBackground(BACKGROUND);

        JScrollPane scrollPane = new JScrollPane(examPanel);
        scrollPane.setBorder(null);
        scrollPane.setBackground(BACKGROUND);
        scrollPane.getViewport().setBackground(BACKGROUND);
        scrollPane.setHorizontalScrollBarPolicy(
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        );
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        centerPanel.add(scrollPane, BorderLayout.CENTER);

        mainPanel.add(centerPanel, BorderLayout.CENTER);

        add(mainPanel);

        loadActiveExams();

        setVisible(true);
    }

    // Header panel
    private JPanel createHeaderPanel() {

        JPanel headerPanel = new JPanel(new BorderLayout(15, 10));
        headerPanel.setBackground(DARK_BLUE);
        headerPanel.setBorder(
                BorderFactory.createEmptyBorder(22, 25, 22, 25)
        );

        // Welcome section
        JLabel welcomeLabel = new JLabel(
                "Welcome, " + loggedInUser.getFullName()
        );
        welcomeLabel.setFont(
                new Font("Segoe UI", Font.BOLD, 23)
        );
        welcomeLabel.setForeground(Color.WHITE);

        JLabel roleLabel = new JLabel(
                "Student Dashboard"
        );
        roleLabel.setFont(
                new Font("Segoe UI", Font.PLAIN, 13)
        );
        roleLabel.setForeground(new Color(219, 234, 254));

        JPanel welcomePanel = new JPanel();
        welcomePanel.setLayout(
                new BoxLayout(welcomePanel, BoxLayout.Y_AXIS)
        );
        welcomePanel.setBackground(DARK_BLUE);

        welcomePanel.add(welcomeLabel);
        welcomePanel.add(Box.createVerticalStrut(5));
        welcomePanel.add(roleLabel);

        // Header buttons
        JButton historyButton = new JButton("Result History");
        JButton profileButton = new JButton("My Profile");
        JButton logoutButton = new JButton("Logout");

        styleHeaderButton(historyButton);
        styleHeaderButton(profileButton);
        styleLogoutButton(logoutButton);

        historyButton.addActionListener(e -> {
            System.out.println("Logged-in User ID: " + loggedInUser.getUserId());
            new ResultHistoryFrame(loggedInUser);
            dispose();
        });

        profileButton.addActionListener(e -> {
            new StudentProfileFrame(loggedInUser);
        });

        logoutButton.addActionListener(e -> {
            new LoginFrame();
            dispose();
        });

        JPanel buttonPanel = new JPanel(
                new FlowLayout(FlowLayout.RIGHT, 10, 5)
        );
        buttonPanel.setBackground(DARK_BLUE);

        buttonPanel.add(profileButton);
        buttonPanel.add(historyButton);
        buttonPanel.add(logoutButton);

        headerPanel.add(welcomePanel, BorderLayout.WEST);
        headerPanel.add(buttonPanel, BorderLayout.EAST);

        return headerPanel;
    }

    // Load active examinations
    private void loadActiveExams() {

        try {

            ExamDAO examDAO = new ExamDAO();
            List<Exam> exams = examDAO.getActiveExams();

            examPanel.removeAll();

            if (exams.isEmpty()) {

                JLabel message = new JLabel(
                        "No active examinations available."
                );

                message.setFont(
                        new Font("Segoe UI", Font.PLAIN, 16)
                );
                message.setForeground(SECONDARY_TEXT);

                JPanel emptyPanel = new JPanel(
                        new FlowLayout(FlowLayout.CENTER, 10, 40)
                );
                emptyPanel.setBackground(BACKGROUND);
                emptyPanel.add(message);

                examPanel.add(emptyPanel);

            } else {

                for (Exam exam : exams) {

                    examPanel.add(createExamCard(exam));
                    examPanel.add(Box.createVerticalStrut(18));
                }
            }

            examPanel.revalidate();
            examPanel.repaint();

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to load examinations.",
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );

            e.printStackTrace();
        }
    }

    // Create individual examination card
    private JPanel createExamCard(Exam exam) {

        JPanel card = new JPanel(new BorderLayout(20, 10));
        card.setBackground(CARD_BACKGROUND);

        card.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                BORDER_COLOR, 1
                        ),
                        BorderFactory.createEmptyBorder(
                                20, 22, 20, 22
                        )
                )
        );

        card.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, 145)
        );
        card.setPreferredSize(new Dimension(850, 145));

        // Exam details
        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(
                new BoxLayout(detailsPanel, BoxLayout.Y_AXIS)
        );
        detailsPanel.setBackground(CARD_BACKGROUND);

        JLabel titleLabel = new JLabel(exam.getTitle());
        titleLabel.setFont(
                new Font("Segoe UI", Font.BOLD, 19)
        );
        titleLabel.setForeground(TEXT_COLOR);

        String description = exam.getDescription();

        JLabel descriptionLabel = new JLabel(
                description == null || description.trim().isEmpty()
                        ? "No description available."
                        : description
        );

        descriptionLabel.setFont(
                new Font("Segoe UI", Font.PLAIN, 13)
        );
        descriptionLabel.setForeground(SECONDARY_TEXT);

        JLabel durationLabel = new JLabel(
                "Duration: " + exam.getDurationMinutes() + " minutes"
        );

        durationLabel.setFont(
                new Font("Segoe UI", Font.BOLD, 13)
        );
        durationLabel.setForeground(PRIMARY);

        detailsPanel.add(titleLabel);
        detailsPanel.add(Box.createVerticalStrut(10));
        detailsPanel.add(descriptionLabel);
        detailsPanel.add(Box.createVerticalStrut(12));
        detailsPanel.add(durationLabel);

        // Start button
        JButton startButton = new JButton("Start Exam");

        startButton.setFont(
                new Font("Segoe UI", Font.BOLD, 14)
        );
        startButton.setForeground(Color.WHITE);
        startButton.setBackground(PRIMARY);
        startButton.setFocusPainted(false);
        startButton.setBorderPainted(false);
        startButton.setCursor(
                new Cursor(Cursor.HAND_CURSOR)
        );
        startButton.setPreferredSize(
                new Dimension(125, 42)
        );

        startButton.addActionListener(e -> {
            new ExamFrame(loggedInUser, exam);
            dispose();
        });

        // Button hover effect
        startButton.addMouseListener(new java.awt.event.MouseAdapter() {

            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                startButton.setBackground(DARK_BLUE);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                startButton.setBackground(PRIMARY);
            }
        });

        JPanel buttonPanel = new JPanel(
                new GridBagLayout()
        );
        buttonPanel.setBackground(CARD_BACKGROUND);
        buttonPanel.add(startButton);

        card.add(detailsPanel, BorderLayout.CENTER);
        card.add(buttonPanel, BorderLayout.EAST);

        return card;
    }

    // Style Result History button
    private void styleHeaderButton(JButton button) {

        button.setFont(
                new Font("Segoe UI", Font.BOLD, 13)
        );
        button.setForeground(DARK_BLUE);
        button.setBackground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(
                new Cursor(Cursor.HAND_CURSOR)
        );
        button.setPreferredSize(
                new Dimension(125, 38)
        );
    }

    // Style Logout button
    private void styleLogoutButton(JButton button) {

        button.setFont(
                new Font("Segoe UI", Font.BOLD, 13)
        );
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(220, 53, 69));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(
                new Cursor(Cursor.HAND_CURSOR)
        );
        button.setPreferredSize(
                new Dimension(90, 38)
        );
    }
}
