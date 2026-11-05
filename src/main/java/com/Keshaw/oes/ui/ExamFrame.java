package com.Keshaw.oes.ui;

import com.Keshaw.oes.dao.QuestionDAO;
import com.Keshaw.oes.model.Exam;
import com.Keshaw.oes.model.Question;
import com.Keshaw.oes.model.User;
import com.Keshaw.oes.Service.ExamEvaluator;
import com.Keshaw.oes.model.Result;
import com.Keshaw.oes.dao.ResultDAO;

import javax.swing.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExamFrame extends JFrame {

    // Theme colors
    private static final Color BACKGROUND =
            new Color(241, 245, 249);

    private static final Color CARD_BACKGROUND =
            Color.WHITE;

    private static final Color PRIMARY =
            new Color(37, 99, 235);

    private static final Color DARK_BLUE =
            new Color(23, 37, 84);

    private static final Color TEXT_COLOR =
            new Color(30, 41, 59);

    private static final Color BORDER_COLOR =
            new Color(226, 232, 240);

    private static final Color SUCCESS =
            new Color(22, 163, 74);

    private static final Color UNANSWERED =
            new Color(226, 232, 240);

    private static final Color REVIEW =
            new Color(245, 158, 11);

    private JLabel timerLabel;
    private javax.swing.Timer countdownTimer;
    private int remainingSeconds;
    private boolean submitted = false;

    private final User loggedInUser;
    private final Exam exam;

    private List<Question> questions;

    private final Map<Integer, String> selectedAnswers =
            new HashMap<>();

    private int currentQuestion = 0;

    private JLabel questionNumberLabel;
    private JLabel questionLabel;

    private JRadioButton optionA;
    private JRadioButton optionB;
    private JRadioButton optionC;
    private JRadioButton optionD;

    private ButtonGroup optionGroup;

    private JButton previousButton;
    private JButton nextButton;
    private JButton submitButton;

    // Question navigation
    private JPanel navigationPanel;
    private JButton[] questionButtons;

    // Questions marked for review
    private Map<Integer, Boolean> markedForReview =
            new HashMap<>();

    public ExamFrame(User user, Exam exam) {

        this.loggedInUser = user;
        this.exam = exam;

        setTitle("Online Examination - " + exam.getTitle());
        setSize(1100, 700);
        setMinimumSize(new Dimension(950, 600));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        getContentPane().setBackground(BACKGROUND);
        getContentPane().setLayout(
                new BorderLayout(0, 0)
        );

        // Main header
        createHeader();

        // Main content panel
        JPanel mainPanel = new JPanel(
                new BorderLayout(15, 15)
        );

        mainPanel.setBackground(BACKGROUND);

        mainPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        20, 25, 20, 25
                )
        );

        // Question area
        JPanel centerPanel = createQuestionPanel();

        mainPanel.add(
                centerPanel,
                BorderLayout.CENTER
        );

        // Navigation buttons
        JPanel buttonPanel = createButtonPanel();

        mainPanel.add(
                buttonPanel,
                BorderLayout.SOUTH
        );

        add(mainPanel, BorderLayout.CENTER);

        // Load questions
        loadQuestions();
    }

    // Create header
    private void createHeader() {

        JPanel headerPanel = new JPanel(
                new BorderLayout()
        );

        headerPanel.setBackground(DARK_BLUE);

        headerPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        18, 25, 18, 25
                )
        );

        JLabel titleLabel = new JLabel(
                exam.getTitle()
        );

        titleLabel.setFont(
                new Font("Arial", Font.BOLD, 24)
        );

        titleLabel.setForeground(Color.WHITE);

        timerLabel = new JLabel("Time: 00:00");

        timerLabel.setFont(
                new Font("Arial", Font.BOLD, 18)
        );

        timerLabel.setForeground(Color.WHITE);

        timerLabel.setOpaque(true);
        timerLabel.setBackground(
                new Color(30, 64, 175)
        );

        timerLabel.setBorder(
                BorderFactory.createEmptyBorder(
                        8, 15, 8, 15
                )
        );

        headerPanel.add(
                titleLabel,
                BorderLayout.WEST
        );

        headerPanel.add(
                timerLabel,
                BorderLayout.EAST
        );

        add(headerPanel, BorderLayout.NORTH);
    }

    // Create question panel
    private JPanel createQuestionPanel() {

        JPanel centerPanel = new JPanel();

        centerPanel.setLayout(
                new BoxLayout(
                        centerPanel,
                        BoxLayout.Y_AXIS
                )
        );

        centerPanel.setBackground(CARD_BACKGROUND);

        centerPanel.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                BORDER_COLOR
                        ),
                        BorderFactory.createEmptyBorder(
                                30, 30, 30, 30
                        )
                )
        );

        questionNumberLabel = new JLabel();

        questionNumberLabel.setFont(
                new Font("Arial", Font.BOLD, 18)
        );

        questionNumberLabel.setForeground(PRIMARY);

        questionLabel = new JLabel();

        questionLabel.setFont(
                new Font("Arial", Font.BOLD, 19)
        );

        questionLabel.setForeground(TEXT_COLOR);

        // Radio buttons
        optionA = createOptionButton();
        optionB = createOptionButton();
        optionC = createOptionButton();
        optionD = createOptionButton();

        optionGroup = new ButtonGroup();

        optionGroup.add(optionA);
        optionGroup.add(optionB);
        optionGroup.add(optionC);
        optionGroup.add(optionD);

        centerPanel.add(questionNumberLabel);

        centerPanel.add(
                Box.createVerticalStrut(30)
        );

        centerPanel.add(questionLabel);

        centerPanel.add(
                Box.createVerticalStrut(30)
        );

        centerPanel.add(optionA);

        centerPanel.add(
                Box.createVerticalStrut(12)
        );

        centerPanel.add(optionB);

        centerPanel.add(
                Box.createVerticalStrut(12)
        );

        centerPanel.add(optionC);

        centerPanel.add(
                Box.createVerticalStrut(12)
        );

        centerPanel.add(optionD);

        return centerPanel;
    }

    // Style answer options
    private JRadioButton createOptionButton() {

        JRadioButton button = new JRadioButton();

        button.setFont(
                new Font("Arial", Font.PLAIN, 16)
        );

        button.setForeground(TEXT_COLOR);

        button.setBackground(CARD_BACKGROUND);

        button.setOpaque(true);

        button.setFocusPainted(false);

        button.setCursor(
                new Cursor(Cursor.HAND_CURSOR)
        );

        button.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                BORDER_COLOR
                        ),
                        BorderFactory.createEmptyBorder(
                                12, 15, 12, 15
                        )
                )
        );

        button.setAlignmentX(Component.LEFT_ALIGNMENT);

        button.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, 55)
        );

        return button;
    }

    // Create navigation button panel
    private JPanel createButtonPanel() {

        JPanel buttonPanel = new JPanel(
                new FlowLayout(
                        FlowLayout.CENTER,
                        20,
                        10
                )
        );

        buttonPanel.setBackground(BACKGROUND);

        previousButton = new JButton("Previous");
        nextButton = new JButton("Next");
        submitButton = new JButton("Submit Exam");

        styleButton(
                previousButton,
                new Color(71, 85, 105),
                Color.WHITE
        );

        styleButton(
                nextButton,
                PRIMARY,
                Color.WHITE
        );

        styleButton(
                submitButton,
                new Color(220, 38, 38),
                Color.WHITE
        );

        previousButton.addActionListener(e -> {

            saveSelectedAnswer();

            if (currentQuestion > 0) {

                currentQuestion--;

                displayQuestion();
            }
        });

        nextButton.addActionListener(e -> {

            saveSelectedAnswer();

            if (currentQuestion < questions.size() - 1) {

                currentQuestion++;

                displayQuestion();
            }
        });

        submitButton.addActionListener(
                e -> submitExam(false)
        );

        buttonPanel.add(previousButton);
        buttonPanel.add(nextButton);
        buttonPanel.add(submitButton);

        return buttonPanel;
    }

    // Common button styling
    private void styleButton(
            JButton button,
            Color background,
            Color foreground
    ) {

        button.setFont(
                new Font("Arial", Font.BOLD, 15)
        );

        button.setBackground(background);
        button.setForeground(foreground);

        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);

        button.setCursor(
                new Cursor(Cursor.HAND_CURSOR)
        );

        button.setPreferredSize(
                new Dimension(140, 45)
        );
    }

    // Load questions
    private void loadQuestions() {

        try {

            QuestionDAO questionDAO = new QuestionDAO();

            questions = questionDAO.getQuestionsByExamId(
                    exam.getExamId()
            );

            if (questions.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "No questions available for this examination."
                );

                dispose();

                new com.Keshaw.oes.ui.StudentDashboard(
                        loggedInUser
                );

                return;
            }

            // Create navigation panel
            createNavigationPanel();

            JScrollPane navigationScrollPane =
                    new JScrollPane(navigationPanel);

            navigationScrollPane.setPreferredSize(
                    new Dimension(245, 450)
            );

            navigationScrollPane.setBorder(
                    BorderFactory.createEmptyBorder(
                            10, 5, 10, 10
                    )
            );

            navigationScrollPane.setHorizontalScrollBarPolicy(
                    JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
            );

            navigationScrollPane.getViewport().setBackground(
                    CARD_BACKGROUND
            );

            add(
                    navigationScrollPane,
                    BorderLayout.EAST
            );

            // Display first question
            displayQuestion();

            // Initialize countdown
            remainingSeconds =
                    exam.getDurationMinutes() * 60;

            updateTimerLabel();

            countdownTimer = new javax.swing.Timer(
                    1000,
                    e -> {

                        remainingSeconds--;

                        updateTimerLabel();

                        if (remainingSeconds <= 0) {

                            countdownTimer.stop();

                            submitExam(true);
                        }
                    }
            );

            countdownTimer.start();

            setVisible(true);

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to load examination questions."
            );

            e.printStackTrace();

            dispose();

            new com.Keshaw.oes.ui.StudentDashboard(
                    loggedInUser
            );
        }
    }

    // Create question navigation panel
    private void createNavigationPanel() {

        navigationPanel = new JPanel(
                new GridBagLayout()
        );

        navigationPanel.setBackground(CARD_BACKGROUND);

        navigationPanel.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createTitledBorder(
                                BorderFactory.createLineBorder(
                                        BORDER_COLOR
                                ),
                                "Question Navigation",
                                TitledBorder.LEFT,
                                TitledBorder.TOP,
                                new Font("Arial", Font.BOLD, 16),
                                DARK_BLUE
                        ),
                        BorderFactory.createEmptyBorder(
                                10, 5, 10, 5
                        )
                )
        );

        questionButtons = new JButton[questions.size()];

        GridBagConstraints gbc =
                new GridBagConstraints();

        gbc.fill = GridBagConstraints.NONE;

        gbc.anchor = GridBagConstraints.NORTHWEST;

        gbc.insets = new Insets(7, 7, 7, 7);

        for (int i = 0; i < questions.size(); i++) {

            final int questionIndex = i;

            JButton button = new JButton(
                    String.valueOf(i + 1)
            );

            button.setPreferredSize(
                    new Dimension(55, 55)
            );

            button.setFont(
                    new Font("Arial", Font.BOLD, 16)
            );

            button.setFocusPainted(false);
            button.setBorderPainted(false);
            button.setOpaque(true);

            button.setCursor(
                    new Cursor(Cursor.HAND_CURSOR)
            );

            gbc.gridx = i % 3;
            gbc.gridy = i / 3;

            button.addActionListener(e -> {

                saveSelectedAnswer();

                currentQuestion = questionIndex;

                displayQuestion();
            });

            questionButtons[i] = button;

            navigationPanel.add(button, gbc);
        }

        // Keep buttons at the top
        GridBagConstraints filler =
                new GridBagConstraints();

        filler.gridx = 0;

        filler.gridy = (questions.size() + 2) / 3;

        filler.weightx = 1.0;
        filler.weighty = 1.0;

        filler.gridwidth = 3;

        filler.fill = GridBagConstraints.BOTH;

        navigationPanel.add(
                Box.createGlue(),
                filler
        );
    }

    // Update question navigation button colors
    private void updateNavigationButtons() {

        for (int i = 0; i < questions.size(); i++) {

            int questionId =
                    questions.get(i).getQuestionId();

            JButton button = questionButtons[i];

            if (i == currentQuestion) {

                button.setBackground(PRIMARY);
                button.setForeground(Color.WHITE);

            } else if (
                    Boolean.TRUE.equals(
                            markedForReview.get(questionId)
                    )
            ) {

                button.setBackground(REVIEW);
                button.setForeground(Color.WHITE);

            } else if (
                    selectedAnswers.containsKey(questionId)
            ) {

                button.setBackground(SUCCESS);
                button.setForeground(Color.WHITE);

            } else {

                button.setBackground(UNANSWERED);
                button.setForeground(TEXT_COLOR);
            }
        }
    }

    // Update timer
    private void updateTimerLabel() {

        int minutes = remainingSeconds / 60;

        int seconds = remainingSeconds % 60;

        timerLabel.setText(
                String.format(
                        "Time: %02d:%02d",
                        minutes,
                        seconds
                )
        );

        // Highlight timer when less than five minutes remain
        if (remainingSeconds <= 300) {

            timerLabel.setBackground(
                    new Color(185, 28, 28)
            );
        }
    }

    // Display question
    private void displayQuestion() {

        Question question =
                questions.get(currentQuestion);

        questionNumberLabel.setText(
                "Question " + (currentQuestion + 1)
                        + " of " + questions.size()
        );

        questionLabel.setText(
                "<html><div style='width:580px'>"
                        + question.getQuestionText()
                        + "</div></html>"
        );

        optionA.setText(
                "A. " + question.getOptionA()
        );

        optionB.setText(
                "B. " + question.getOptionB()
        );

        optionC.setText(
                "C. " + question.getOptionC()
        );

        optionD.setText(
                "D. " + question.getOptionD()
        );

        optionGroup.clearSelection();

        String selected = selectedAnswers.get(
                question.getQuestionId()
        );

        if ("A".equals(selected)) {

            optionA.setSelected(true);

        } else if ("B".equals(selected)) {

            optionB.setSelected(true);

        } else if ("C".equals(selected)) {

            optionC.setSelected(true);

        } else if ("D".equals(selected)) {

            optionD.setSelected(true);
        }

        previousButton.setEnabled(
                currentQuestion > 0
        );

        nextButton.setEnabled(
                currentQuestion < questions.size() - 1
        );

        // Update navigation colors
        updateNavigationButtons();
    }

    // Save selected answer
    private void saveSelectedAnswer() {

        Question question =
                questions.get(currentQuestion);

        if (optionA.isSelected()) {

            selectedAnswers.put(
                    question.getQuestionId(),
                    "A"
            );

        } else if (optionB.isSelected()) {

            selectedAnswers.put(
                    question.getQuestionId(),
                    "B"
            );

        } else if (optionC.isSelected()) {

            selectedAnswers.put(
                    question.getQuestionId(),
                    "C"
            );

        } else if (optionD.isSelected()) {

            selectedAnswers.put(
                    question.getQuestionId(),
                    "D"
            );
        }

        updateNavigationButtons();
    }

    // Submit examination
    private void submitExam(boolean automatic) {

        // Prevent multiple submissions
        if (submitted) {
            return;
        }

        saveSelectedAnswer();

        // Manual submission confirmation
        if (!automatic) {

            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to submit the examination?",
                    "Confirm Submission",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirm != JOptionPane.YES_OPTION) {
                return;
            }
        }

        submitted = true;

        // Stop countdown
        if (countdownTimer != null) {
            countdownTimer.stop();
        }

        try {

            ExamEvaluator evaluator =
                    new ExamEvaluator();

            Result result = evaluator.evaluateExam(
                    loggedInUser.getUserId(),
                    exam.getExamId(),
                    selectedAnswers
            );

            ResultDAO resultDAO = new ResultDAO();

            boolean saved = resultDAO.saveResult(result);

            if (saved) {

                dispose();

                new ResultFrame(result);

            } else {

                submitted = false;

                JOptionPane.showMessageDialog(
                        this,
                        "Unable to save examination result."
                );
            }

        } catch (Exception e) {

            submitted = false;

            JOptionPane.showMessageDialog(
                    this,
                    "Error submitting examination."
            );

            e.printStackTrace();
        }
    }
}
