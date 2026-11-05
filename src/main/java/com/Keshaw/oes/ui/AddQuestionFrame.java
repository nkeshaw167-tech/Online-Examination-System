package com.Keshaw.oes.ui;

import com.Keshaw.oes.dao.QuestionDAO;
import com.Keshaw.oes.model.Question;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

public class AddQuestionFrame extends JFrame {

    private final int examId;

    private JTextArea questionArea;
    private JTextField optionAField;
    private JTextField optionBField;
    private JTextField optionCField;
    private JTextField optionDField;
    private JComboBox<String> correctOptionBox;
    private JTextField marksField;

    // Theme colors
    private final Color DARK_BLUE = new Color(25, 55, 100);
    private final Color PRIMARY_BLUE = new Color(45, 105, 190);
    private final Color LIGHT_BACKGROUND = new Color(242, 246, 252);
    private final Color TEXT_COLOR = new Color(45, 55, 75);
    private final Color BORDER_COLOR = new Color(215, 225, 238);

    public AddQuestionFrame(int examId) {

        this.examId = examId;

        setTitle("Add New Question");
        setSize(800, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(LIGHT_BACKGROUND);

        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setBackground(DARK_BLUE);
        headerPanel.setBorder(new EmptyBorder(25, 40, 25, 40));

        JLabel heading = new JLabel("Add New Question");
        heading.setFont(new Font("Segoe UI", Font.BOLD, 27));
        heading.setForeground(Color.WHITE);
        heading.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitle = new JLabel(
                "Create a question with multiple answer options"
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
                new EmptyBorder(25, 35, 25, 35)
        ));

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(10, 8, 10, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        // Form heading
        JLabel formHeading = new JLabel("Question Information");
        formHeading.setFont(new Font("Segoe UI", Font.BOLD, 19));
        formHeading.setForeground(DARK_BLUE);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;

        cardPanel.add(formHeading, gbc);

        // Question
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;

        cardPanel.add(createLabel("Question"), gbc);

        questionArea = new JTextArea(4, 20);
        styleTextArea(questionArea);

        JScrollPane questionScroll = new JScrollPane(questionArea);
        questionScroll.setBorder(new LineBorder(BORDER_COLOR, 1, true));
        questionScroll.setPreferredSize(new Dimension(430, 100));

        gbc.gridx = 1;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.BOTH;

        cardPanel.add(questionScroll, gbc);

        // Option A
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        cardPanel.add(createLabel("Option A"), gbc);

        optionAField = new JTextField();
        styleTextField(optionAField);

        gbc.gridx = 1;
        gbc.weightx = 1;

        cardPanel.add(optionAField, gbc);

        // Option B
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0;

        cardPanel.add(createLabel("Option B"), gbc);

        optionBField = new JTextField();
        styleTextField(optionBField);

        gbc.gridx = 1;
        gbc.weightx = 1;

        cardPanel.add(optionBField, gbc);

        // Option C
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weightx = 0;

        cardPanel.add(createLabel("Option C"), gbc);

        optionCField = new JTextField();
        styleTextField(optionCField);

        gbc.gridx = 1;
        gbc.weightx = 1;

        cardPanel.add(optionCField, gbc);

        // Option D
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.weightx = 0;

        cardPanel.add(createLabel("Option D"), gbc);

        optionDField = new JTextField();
        styleTextField(optionDField);

        gbc.gridx = 1;
        gbc.weightx = 1;

        cardPanel.add(optionDField, gbc);

        // Correct option
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.weightx = 0;

        cardPanel.add(createLabel("Correct Answer"), gbc);

        correctOptionBox = new JComboBox<>(
                new String[]{"A", "B", "C", "D"}
        );

        correctOptionBox.setFont(
                new Font("Segoe UI", Font.PLAIN, 14)
        );
        correctOptionBox.setBackground(Color.WHITE);
        correctOptionBox.setForeground(TEXT_COLOR);
        correctOptionBox.setPreferredSize(new Dimension(430, 42));

        gbc.gridx = 1;
        gbc.weightx = 1;

        cardPanel.add(correctOptionBox, gbc);

        // Marks
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.weightx = 0;

        cardPanel.add(createLabel("Marks"), gbc);

        marksField = new JTextField("1");
        styleTextField(marksField);

        gbc.gridx = 1;
        gbc.weightx = 1;

        cardPanel.add(marksField, gbc);

        // Buttons
        JPanel buttonPanel = new JPanel(
                new GridLayout(1, 2, 15, 0)
        );
        buttonPanel.setBackground(Color.WHITE);

        JButton saveButton = new JButton("Save Question");
        JButton cancelButton = new JButton("Cancel");

        styleButton(saveButton, PRIMARY_BLUE, Color.WHITE);
        styleButton(cancelButton, new Color(235, 239, 245), DARK_BLUE);

        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 2;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        cardPanel.add(buttonPanel, gbc);

        // Center the card
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(LIGHT_BACKGROUND);
        centerPanel.setBorder(new EmptyBorder(20, 35, 20, 35));

        centerPanel.add(cardPanel);

        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // Button actions
        saveButton.addActionListener(e -> saveQuestion());
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
        field.setPreferredSize(new Dimension(430, 42));

        field.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(BORDER_COLOR, 1, true),
                new EmptyBorder(5, 12, 5, 12)
        ));
    }

    // Style question area
    private void styleTextArea(JTextArea area) {

        area.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        area.setForeground(TEXT_COLOR);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setBorder(new EmptyBorder(10, 12, 10, 12));
    }

    // Style buttons
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

    // Save question into database
    private void saveQuestion() {

        String questionText = questionArea.getText().trim();
        String optionA = optionAField.getText().trim();
        String optionB = optionBField.getText().trim();
        String optionC = optionCField.getText().trim();
        String optionD = optionDField.getText().trim();

        String correctOption =
                (String) correctOptionBox.getSelectedItem();

        // Validation
        if (questionText.isEmpty() ||
                optionA.isEmpty() ||
                optionB.isEmpty() ||
                optionC.isEmpty() ||
                optionD.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please fill in all question and option fields.",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        try {

            int marks = Integer.parseInt(marksField.getText().trim());

            if (marks <= 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "Marks must be greater than zero.",
                        "Validation Error",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }

            // Create Question object
            Question question = new Question();

            question.setExamId(examId);
            question.setQuestionText(questionText);
            question.setOptionA(optionA);
            question.setOptionB(optionB);
            question.setOptionC(optionC);
            question.setOptionD(optionD);
            question.setCorrectOption(correctOption);
            question.setMarks(marks);

            // Save using DAO
            QuestionDAO questionDAO = new QuestionDAO();

            if (questionDAO.addQuestion(question)) {

                JOptionPane.showMessageDialog(
                        this,
                        "Question added successfully!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE
                );

                dispose();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Unable to add question. Please try again.",
                        "Database Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }

        } catch (NumberFormatException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a valid marks value.",
                    "Invalid Marks",
                    JOptionPane.ERROR_MESSAGE
            );

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error adding question: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}
