package com.Keshaw.oes.ui;

import com.Keshaw.oes.dao.ExamDAO;
import com.Keshaw.oes.model.Exam;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

public class AddExamFrame extends JFrame {

    private JTextField titleField;
    private JTextArea descriptionArea;
    private JTextField durationField;

    private JButton saveButton;
    private JButton cancelButton;

    // Theme colors
    private final Color DARK_BLUE = new Color(25, 55, 100);
    private final Color PRIMARY_BLUE = new Color(45, 105, 190);
    private final Color LIGHT_BACKGROUND = new Color(242, 246, 252);
    private final Color TEXT_COLOR = new Color(45, 55, 75);
    private final Color BORDER_COLOR = new Color(215, 225, 238);

    public AddExamFrame() {

        setTitle("Create New Examination");
        setSize(750, 650);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Main background
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(LIGHT_BACKGROUND);

        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setBackground(DARK_BLUE);
        headerPanel.setBorder(new EmptyBorder(28, 40, 28, 40));

        JLabel heading = new JLabel("Create New Examination");
        heading.setFont(new Font("Segoe UI", Font.BOLD, 27));
        heading.setForeground(Color.WHITE);
        heading.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitle = new JLabel(
                "Enter the examination details below"
        );
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitle.setForeground(new Color(215, 228, 245));
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        headerPanel.add(heading);
        headerPanel.add(Box.createVerticalStrut(8));
        headerPanel.add(subtitle);

        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Form card
        JPanel cardPanel = new JPanel(new GridBagLayout());
        cardPanel.setBackground(Color.WHITE);
        cardPanel.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(BORDER_COLOR, 1, true),
                new EmptyBorder(30, 40, 30, 40)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 8, 12, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        // Form heading
        JLabel formHeading = new JLabel("Examination Information");
        formHeading.setFont(new Font("Segoe UI", Font.BOLD, 19));
        formHeading.setForeground(DARK_BLUE);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;

        cardPanel.add(formHeading, gbc);

        // Exam title
        gbc.gridwidth = 1;
        gbc.gridy = 1;
        gbc.gridx = 0;
        gbc.weightx = 0;

        cardPanel.add(createLabel("Exam Title"), gbc);

        titleField = new JTextField();
        styleTextField(titleField);

        gbc.gridx = 1;
        gbc.weightx = 1;

        cardPanel.add(titleField, gbc);

        // Description
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.NORTH;

        cardPanel.add(createLabel("Description"), gbc);

        descriptionArea = new JTextArea(5, 20);
        descriptionArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        descriptionArea.setForeground(TEXT_COLOR);
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        descriptionArea.setBorder(new EmptyBorder(10, 12, 10, 12));

        JScrollPane scrollPane = new JScrollPane(descriptionArea);
        scrollPane.setBorder(new LineBorder(BORDER_COLOR, 1, true));
        scrollPane.setPreferredSize(new Dimension(400, 120));

        gbc.gridx = 1;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.BOTH;

        cardPanel.add(scrollPane, gbc);

        // Duration
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;

        cardPanel.add(createLabel("Duration (minutes)"), gbc);

        durationField = new JTextField();
        styleTextField(durationField);

        gbc.gridx = 1;
        gbc.weightx = 1;

        cardPanel.add(durationField, gbc);

        // Buttons
        JPanel buttonPanel = new JPanel(
                new GridLayout(1, 2, 15, 0)
        );
        buttonPanel.setBackground(Color.WHITE);

        saveButton = new JButton("Save Examination");
        cancelButton = new JButton("Cancel");

        styleButton(saveButton, PRIMARY_BLUE, Color.WHITE);
        styleButton(cancelButton, new Color(235, 239, 245), DARK_BLUE);

        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        cardPanel.add(buttonPanel, gbc);

        // Center the card
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(LIGHT_BACKGROUND);
        centerPanel.setBorder(new EmptyBorder(25, 35, 25, 35));
        centerPanel.add(cardPanel);

        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // Button actions
        saveButton.addActionListener(e -> saveExam());
        cancelButton.addActionListener(e -> dispose());

        add(mainPanel);

        setVisible(true);
    }

    // Create form labels
    private JLabel createLabel(String text) {

        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.BOLD, 14));
        label.setForeground(TEXT_COLOR);

        return label;
    }

    // Style text fields
    private void styleTextField(JTextField field) {

        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setForeground(TEXT_COLOR);
        field.setPreferredSize(new Dimension(400, 42));

        field.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(BORDER_COLOR, 1, true),
                new EmptyBorder(5, 12, 5, 12)
        ));
    }

    private void styleButton(
            JButton button,
            Color background,
            Color foreground
    ) {

        button.setFont(new Font("Segoe UI", Font.BOLD, 13));
        button.setBackground(background);
        button.setForeground(foreground);

        button.setOpaque(true);
        button.setContentAreaFilled(true);
        button.setFocusPainted(false);
        button.setBorderPainted(false);

        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(165, 43));
    }

    // Save exam into database
    private void saveExam() {

        String title = titleField.getText().trim();

        String description = descriptionArea.getText().trim();

        String durationText = durationField.getText().trim();

        // Validation
        if (title.isEmpty() ||
                description.isEmpty() ||
                durationText.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please fill all fields.",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        int duration;

        try {

            duration = Integer.parseInt(durationText);

            if (duration <= 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "Duration must be greater than zero.",
                        "Validation Error",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a valid numeric duration.",
                    "Invalid Duration",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        // Create Exam object
        Exam exam = new Exam();

        exam.setTitle(title);
        exam.setDescription(description);
        exam.setDurationMinutes(duration);
        exam.setActive(true);

        // Save using DAO
        ExamDAO examDAO = new ExamDAO();

        boolean saved = examDAO.addExam(exam);

        if (saved) {

            JOptionPane.showMessageDialog(
                    this,
                    "Exam added successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

            dispose();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to add exam. Please try again.",
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}


