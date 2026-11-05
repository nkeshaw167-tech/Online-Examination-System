package com.Keshaw.oes.ui;

import com.Keshaw.oes.model.Question;
import com.Keshaw.oes.dao.QuestionDAO;
import javax.swing.JOptionPane;
import java.sql.SQLException;
import java.io.IOException;

import javax.swing.*;
import java.awt.*;

public class EditQuestionFrame extends JFrame {

    private JTextArea questionField;
    private JTextField optionAField;
    private JTextField optionBField;
    private JTextField optionCField;
    private JTextField optionDField;
    private JComboBox<String> correctOptionBox;
    private JTextField marksField;

    private Question question;

    public EditQuestionFrame(Question question) {

        this.question = question;

        setTitle("Edit Question");
        setSize(500, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setLayout(new GridLayout(8, 2, 10, 10));

        // Question
        add(new JLabel("Question:"));

        questionField = new JTextArea(question.getQuestionText());
        questionField.setLineWrap(true);
        questionField.setWrapStyleWord(true);

        add(new JScrollPane(questionField));

        // Option A
        add(new JLabel("Option A:"));
        optionAField = new JTextField(question.getOptionA());
        add(optionAField);

        // Option B
        add(new JLabel("Option B:"));
        optionBField = new JTextField(question.getOptionB());
        add(optionBField);

        // Option C
        add(new JLabel("Option C:"));
        optionCField = new JTextField(question.getOptionC());
        add(optionCField);

        // Option D
        add(new JLabel("Option D:"));
        optionDField = new JTextField(question.getOptionD());
        add(optionDField);

        // Correct Option
        add(new JLabel("Correct Option:"));

        correctOptionBox = new JComboBox<>(
                new String[]{"A", "B", "C", "D"}
        );

        correctOptionBox.setSelectedItem(question.getCorrectOption());
        add(correctOptionBox);

        // Marks
        add(new JLabel("Marks:"));
        marksField = new JTextField(String.valueOf(question.getMarks()));
        add(marksField);

        // Update Button
        JButton updateButton = new JButton("Update Question");
        add(new JLabel());
        add(updateButton);

        updateButton.addActionListener(e -> updateQuestion());

        setVisible(true);
    }

    private void updateQuestion() {

        String questionText = questionField.getText().trim();
        String optionA = optionAField.getText().trim();
        String optionB = optionBField.getText().trim();
        String optionC = optionCField.getText().trim();
        String optionD = optionDField.getText().trim();

        String correctOption =
                (String) correctOptionBox.getSelectedItem();

        String marksText = marksField.getText().trim();

        // Validate empty fields
        if (questionText.isEmpty() ||
                optionA.isEmpty() ||
                optionB.isEmpty() ||
                optionC.isEmpty() ||
                optionD.isEmpty() ||
                marksText.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please fill all fields."
            );
            return;
        }

        // Validate marks
        int marks;

        try {
            marks = Integer.parseInt(marksText);

            if (marks <= 0) {
                JOptionPane.showMessageDialog(
                        this,
                        "Marks must be greater than zero."
                );
                return;
            }

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter valid marks."
            );
            return;
        }

        // Update Question object
        question.setQuestionText(questionText);
        question.setOptionA(optionA);
        question.setOptionB(optionB);
        question.setOptionC(optionC);
        question.setOptionD(optionD);
        question.setCorrectOption(correctOption);
        question.setMarks(marks);

        try {

            QuestionDAO questionDAO = new QuestionDAO();

            boolean updated = questionDAO.updateQuestion(question);

            if (updated) {

                JOptionPane.showMessageDialog(
                        this,
                        "Question updated successfully!"
                );

                dispose();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Failed to update question."
                );
            }

        } catch (SQLException | IOException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Database Error: " + e.getMessage()
            );

            e.printStackTrace();
        }
    }
}