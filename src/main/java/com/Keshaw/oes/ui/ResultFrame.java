package com.Keshaw.oes.ui;

import com.Keshaw.oes.model.Result;

import javax.swing.*;
import java.awt.*;

public class ResultFrame extends JFrame {

    private Result result;

    public ResultFrame(Result result) {

        this.result = result;

        setTitle("Examination Result");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        mainPanel.setBorder(
                BorderFactory.createEmptyBorder(25, 30, 25, 30)
        );

        JLabel titleLabel = new JLabel("Examination Result");

        titleLabel.setFont(
                new Font("Arial", Font.BOLD, 25)
        );

        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel scoreLabel = new JLabel(
                "Score: " + result.getScore()
        );

        scoreLabel.setFont(
                new Font("Arial", Font.BOLD, 22)
        );

        JLabel totalLabel = new JLabel(
                "Total Questions: " + result.getTotalQuestions()
        );

        JLabel correctLabel = new JLabel(
                "Correct Answers: " + result.getCorrectAnswers()
        );

        JLabel wrongLabel = new JLabel(
                "Wrong Answers: " + result.getWrongAnswers()
        );

        int unattempted = result.getTotalQuestions()
                - result.getCorrectAnswers()
                - result.getWrongAnswers();

        JLabel unattemptedLabel = new JLabel(
                "Unattempted: " + unattempted
        );

        mainPanel.add(titleLabel);
        mainPanel.add(Box.createVerticalStrut(25));
        mainPanel.add(scoreLabel);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(totalLabel);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(correctLabel);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(wrongLabel);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(unattemptedLabel);

        add(mainPanel);

        setVisible(true);
    }
}