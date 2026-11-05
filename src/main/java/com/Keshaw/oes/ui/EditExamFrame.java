package com.Keshaw.oes.ui;

import com.Keshaw.oes.dao.ExamDAO;
import com.Keshaw.oes.model.Exam;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class EditExamFrame extends JFrame {

    private final Exam exam;

    private JTextField titleField;
    private JTextArea descriptionArea;
    private JTextField durationField;

    private final Color DARK_BLUE = new Color(25, 55, 100);
    private final Color PRIMARY_BLUE = new Color(45, 105, 190);
    private final Color LIGHT_BACKGROUND = new Color(242, 246, 252);
    private final Color TEXT_COLOR = new Color(45, 55, 75);
    private final Color BORDER_COLOR = new Color(215, 225, 238);

    public EditExamFrame(Exam exam) {

        this.exam = exam;

        setTitle("Edit Examination");
        setSize(750, 650);
        setMinimumSize(new Dimension(650, 550));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Main Panel
        JPanel mainPanel = new JPanel(new BorderLayout(0, 20));
        mainPanel.setBackground(LIGHT_BACKGROUND);
        mainPanel.setBorder(
                BorderFactory.createEmptyBorder(20, 25, 20, 25)
        );

        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(DARK_BLUE);
        headerPanel.setBorder(
                BorderFactory.createEmptyBorder(22, 25, 22, 25)
        );

        JLabel heading = new JLabel("Edit Examination");
        heading.setFont(new Font("Arial", Font.BOLD, 27));
        heading.setForeground(Color.WHITE);

        JLabel subtitle = new JLabel(
                "Update examination details and save your changes"
        );
        subtitle.setFont(new Font("Arial", Font.PLAIN, 14));
        subtitle.setForeground(new Color(220, 230, 245));

        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.setBackground(DARK_BLUE);

        titlePanel.add(heading);
        titlePanel.add(Box.createVerticalStrut(7));
        titlePanel.add(subtitle);

        headerPanel.add(titlePanel, BorderLayout.WEST);

        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Form Card
        JPanel formCard = new JPanel(new BorderLayout(0, 20));
        formCard.setBackground(Color.WHITE);
        formCard.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(BORDER_COLOR),
                        BorderFactory.createEmptyBorder(25, 30, 25, 30)
                )
        );

        JLabel formHeading = new JLabel("Examination Information");
        formHeading.setFont(new Font("Arial", Font.BOLD, 19));
        formHeading.setForeground(DARK_BLUE);

        formCard.add(formHeading, BorderLayout.NORTH);

        // Form Fields
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(12, 5, 12, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        // Exam Title
        JLabel titleLabel = createLabel("Exam Title");

        titleField = new JTextField(exam.getTitle());
        styleTextField(titleField);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        formPanel.add(titleLabel, gbc);

        gbc.gridy = 1;
        formPanel.add(titleField, gbc);

        // Description
        JLabel descriptionLabel = createLabel("Description");

        descriptionArea = new JTextArea(
                exam.getDescription() == null
                        ? ""
                        : exam.getDescription()
        );

        descriptionArea.setFont(
                new Font("Arial", Font.PLAIN, 14)
        );

        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        descriptionArea.setBorder(
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        );

        JScrollPane descriptionScroll = new JScrollPane(descriptionArea);
        descriptionScroll.setPreferredSize(new Dimension(400, 110));
        descriptionScroll.setBorder(
                BorderFactory.createLineBorder(BORDER_COLOR)
        );

        gbc.gridy = 2;
        formPanel.add(descriptionLabel, gbc);

        gbc.gridy = 3;
        formPanel.add(descriptionScroll, gbc);

        // Duration
        JLabel durationLabel = createLabel("Duration (Minutes)");

        durationField = new JTextField(
                String.valueOf(exam.getDurationMinutes())
        );

        styleTextField(durationField);

        gbc.gridy = 4;
        formPanel.add(durationLabel, gbc);

        gbc.gridy = 5;
        formPanel.add(durationField, gbc);

        formCard.add(formPanel, BorderLayout.CENTER);

        mainPanel.add(formCard, BorderLayout.CENTER);

        // Buttons
        JButton saveButton = new JButton("Save Changes");
        JButton cancelButton = new JButton("Cancel");

        styleButton(saveButton, PRIMARY_BLUE);
        styleButton(cancelButton, new Color(100, 110, 125));

        JPanel buttonPanel = new JPanel(
                new GridLayout(1, 2, 15, 0)
        );

        buttonPanel.setBackground(LIGHT_BACKGROUND);
        buttonPanel.setOpaque(true);

        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Actions
        saveButton.addActionListener(e -> updateExam());
        cancelButton.addActionListener(e -> dispose());

        add(mainPanel);

        setVisible(true);
    }

    // Label Styling
    private JLabel createLabel(String text) {

        JLabel label = new JLabel(text);

        label.setFont(
                new Font("Arial", Font.BOLD, 14)
        );

        label.setForeground(TEXT_COLOR);

        return label;
    }

    // Text Field Styling
    private void styleTextField(JTextField field) {

        field.setFont(
                new Font("Arial", Font.PLAIN, 14)
        );

        field.setPreferredSize(new Dimension(400, 42));

        field.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(BORDER_COLOR),
                        BorderFactory.createEmptyBorder(5, 10, 5, 10)
                )
        );
    }

    // Button Styling
    private void styleButton(JButton button, Color color) {

        button.setFont(
                new Font("Arial", Font.BOLD, 14)
        );

        button.setBackground(color);
        button.setForeground(Color.WHITE);

        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setContentAreaFilled(true);

        button.setPreferredSize(new Dimension(180, 45));

        button.setCursor(
                new Cursor(Cursor.HAND_CURSOR)
        );
    }

    // Update Exam
    private void updateExam() {

        String title = titleField.getText().trim();

        String description = descriptionArea.getText().trim();

        String durationText = durationField.getText().trim();

        if (title.isEmpty() || durationText.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Title and duration are required."
            );

            return;
        }

        try {

            int duration = Integer.parseInt(durationText);

            if (duration <= 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "Duration must be greater than zero."
                );

                return;
            }

            exam.setTitle(title);
            exam.setDescription(description);
            exam.setDurationMinutes(duration);

            ExamDAO examDAO = new ExamDAO();

            if (examDAO.updateExam(exam)) {

                JOptionPane.showMessageDialog(
                        this,
                        "Exam updated successfully!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE
                );

                dispose();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Exam could not be updated.",
                        "Update Failed",
                        JOptionPane.ERROR_MESSAGE
                );
            }

        } catch (NumberFormatException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a valid duration."
            );

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error updating exam: " + ex.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}