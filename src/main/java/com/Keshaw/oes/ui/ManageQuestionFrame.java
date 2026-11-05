package com.Keshaw.oes.ui;

import com.Keshaw.oes.dao.ExamDAO;
import com.Keshaw.oes.dao.QuestionDAO;
import com.Keshaw.oes.model.Exam;
import com.Keshaw.oes.model.Question;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.io.IOException;
import java.util.List;

public class ManageQuestionFrame extends JFrame {

    private JComboBox<Exam> examComboBox;
    private JTable questionTable;
    private DefaultTableModel tableModel;

    // Theme colors
    private final Color DARK_BLUE = new Color(25, 55, 100);
    private final Color PRIMARY_BLUE = new Color(45, 105, 190);
    private final Color LIGHT_BACKGROUND = new Color(242, 246, 252);
    private final Color TEXT_COLOR = new Color(45, 55, 75);
    private final Color BORDER_COLOR = new Color(215, 225, 238);

    public ManageQuestionFrame() {

        setTitle("Manage Questions");
        setSize(1150, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout(0, 0));
        mainPanel.setBackground(LIGHT_BACKGROUND);

        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setBackground(DARK_BLUE);
        headerPanel.setBorder(new EmptyBorder(25, 35, 25, 35));

        JLabel heading = new JLabel("Manage Questions");
        heading.setFont(new Font("Segoe UI", Font.BOLD, 27));
        heading.setForeground(Color.WHITE);
        heading.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitle = new JLabel(
                "Add, edit and manage examination questions"
        );
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitle.setForeground(new Color(215, 228, 245));
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        headerPanel.add(heading);
        headerPanel.add(Box.createVerticalStrut(8));
        headerPanel.add(subtitle);

        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Center panel
        JPanel centerPanel = new JPanel(new BorderLayout(0, 15));
        centerPanel.setBackground(LIGHT_BACKGROUND);
        centerPanel.setBorder(new EmptyBorder(22, 25, 10, 25));

        // Exam selection panel
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 15));
        filterPanel.setBackground(Color.WHITE);

        filterPanel.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(BORDER_COLOR, 1, true),
                new EmptyBorder(5, 15, 5, 15)
        ));

        JLabel examLabel = new JLabel("Select Examination:");
        examLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        examLabel.setForeground(DARK_BLUE);

        examComboBox = new JComboBox<>();
        examComboBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        examComboBox.setBackground(Color.WHITE);
        examComboBox.setPreferredSize(new Dimension(350, 40));

        filterPanel.add(examLabel);
        filterPanel.add(examComboBox);

        centerPanel.add(filterPanel, BorderLayout.NORTH);

        // Question table
        String[] columns = {
                "Question ID",
                "Question",
                "Option A",
                "Option B",
                "Option C",
                "Option D",
                "Correct Option",
                "Marks"
        };

        tableModel = new DefaultTableModel(columns, 0) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        questionTable = new JTable(tableModel);

        questionTable.setRowHeight(38);
        questionTable.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        questionTable.setForeground(TEXT_COLOR);
        questionTable.setBackground(Color.WHITE);
        questionTable.setSelectionBackground(new Color(220, 235, 255));
        questionTable.setSelectionForeground(DARK_BLUE);
        questionTable.setShowGrid(true);
        questionTable.setGridColor(new Color(230, 235, 243));
        questionTable.setIntercellSpacing(new Dimension(1, 1));
        questionTable.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        questionTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        // Table header styling
        JTableHeader tableHeader = questionTable.getTableHeader();

        tableHeader.setFont(new Font("Segoe UI", Font.BOLD, 13));
        tableHeader.setBackground(DARK_BLUE);
        tableHeader.setForeground(Color.WHITE);
        tableHeader.setPreferredSize(new Dimension(100, 42));
        tableHeader.setReorderingAllowed(false);

        // Column widths
        int[] columnWidths = {
                95, 300, 150, 150, 150, 150, 120, 65
        };

        for (int i = 0; i < columnWidths.length; i++) {
            questionTable.getColumnModel()
                    .getColumn(i)
                    .setPreferredWidth(columnWidths[i]);
        }

        // Center selected columns
        DefaultTableCellRenderer centerRenderer =
                new DefaultTableCellRenderer();

        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        questionTable.getColumnModel().getColumn(0)
                .setCellRenderer(centerRenderer);

        questionTable.getColumnModel().getColumn(6)
                .setCellRenderer(centerRenderer);

        questionTable.getColumnModel().getColumn(7)
                .setCellRenderer(centerRenderer);

        // Table scroll pane
        JScrollPane scrollPane = new JScrollPane(questionTable);

        scrollPane.setBorder(new LineBorder(BORDER_COLOR, 1, true));
        scrollPane.getViewport().setBackground(Color.WHITE);

        centerPanel.add(scrollPane, BorderLayout.CENTER);

        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // Bottom button panel
        JPanel buttonPanel = new JPanel(
                new FlowLayout(FlowLayout.CENTER, 15, 15)
        );

        buttonPanel.setBackground(LIGHT_BACKGROUND);
        buttonPanel.setBorder(new EmptyBorder(0, 10, 10, 10));

        JButton addButton = new JButton("Add Question");
        JButton editButton = new JButton("Edit Question");
        JButton deleteButton = new JButton("Delete Question");
        JButton backButton = new JButton("Back");

        styleButton(addButton, PRIMARY_BLUE, Color.WHITE);
        styleButton(editButton, new Color(55, 125, 175), Color.WHITE);
        styleButton(deleteButton, new Color(190, 65, 65), Color.WHITE);
        styleButton(backButton, new Color(235, 239, 245), DARK_BLUE);

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(backButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);

        // Load exams first
        loadExams();

        System.out.println("ManageQuestionFrame opened");

        // Exam selection action
        examComboBox.addActionListener(e -> loadQuestions());

        // Initial question loading
        loadQuestions();

        // Button actions
        addButton.addActionListener(e -> openAddQuestionFrame());

        editButton.addActionListener(e -> openEditQuestionFrame());

        deleteButton.addActionListener(e -> deleteSelectedQuestion());

        backButton.addActionListener(e -> dispose());

        setVisible(true);
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
        button.setPreferredSize(new Dimension(170, 44));
    }

    // Delete selected question
    private void deleteSelectedQuestion() {

        int selectedRow = questionTable.getSelectedRow();

        if (selectedRow == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a question to delete.",
                    "Selection Required",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete this question?",
                "Confirm Deletion",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        int questionId = (int) tableModel.getValueAt(selectedRow, 0);

        Exam selectedExam =
                (Exam) examComboBox.getSelectedItem();

        if (selectedExam == null) {
            return;
        }

        try {

            QuestionDAO questionDAO = new QuestionDAO();

            boolean deleted = questionDAO.deleteQuestion(
                    questionId,
                    selectedExam.getExamId()
            );

            if (deleted) {

                JOptionPane.showMessageDialog(
                        this,
                        "Question deleted successfully!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE
                );

                loadQuestions();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Failed to delete question.",
                        "Database Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }

        } catch (SQLException | IOException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Database Error: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );

            e.printStackTrace();
        }
    }

    // Open edit question frame
    private void openEditQuestionFrame() {

        int selectedRow = questionTable.getSelectedRow();

        if (selectedRow == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a question to edit.",
                    "Selection Required",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        int questionId = (int) tableModel.getValueAt(selectedRow, 0);

        try {

            Exam selectedExam =
                    (Exam) examComboBox.getSelectedItem();

            if (selectedExam == null) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please select an exam first."
                );

                return;
            }

            QuestionDAO questionDAO = new QuestionDAO();

            List<Question> questions =
                    questionDAO.getQuestionsByExamId(
                            selectedExam.getExamId()
                    );

            Question selectedQuestion = null;

            for (Question question : questions) {

                if (question.getQuestionId() == questionId) {

                    selectedQuestion = question;
                    break;
                }
            }

            if (selectedQuestion != null) {

                EditQuestionFrame editFrame =
                        new EditQuestionFrame(selectedQuestion);

                editFrame.addWindowListener(
                        new WindowAdapter() {

                            @Override
                            public void windowClosed(WindowEvent e) {
                                loadQuestions();
                            }
                        }
                );
            }

        } catch (SQLException | IOException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error loading question: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );

            e.printStackTrace();
        }
    }

    // Open add question frame
    private void openAddQuestionFrame() {

        Exam selectedExam =
                (Exam) examComboBox.getSelectedItem();

        if (selectedExam == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select an exam first.",
                    "Selection Required",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        AddQuestionFrame addFrame =
                new AddQuestionFrame(selectedExam.getExamId());

        addFrame.addWindowListener(
                new WindowAdapter() {

                    @Override
                    public void windowClosed(WindowEvent e) {
                        loadQuestions();
                    }
                }
        );
    }

    // Load all exams
    private void loadExams() {

        try {

            ExamDAO examDAO = new ExamDAO();

            List<Exam> exams = examDAO.getAllExams();

            examComboBox.removeAllItems();

            for (Exam exam : exams) {
                examComboBox.addItem(exam);
            }

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error loading exams: " + ex.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // Load questions for selected exam
    private void loadQuestions() {

        Exam selectedExam =
                (Exam) examComboBox.getSelectedItem();

        tableModel.setRowCount(0);

        if (selectedExam == null) {
            return;
        }

        try {

            QuestionDAO questionDAO = new QuestionDAO();

            List<Question> questions =
                    questionDAO.getQuestionsByExamId(
                            selectedExam.getExamId()
                    );

            System.out.println(
                    "Selected Exam ID: " + selectedExam.getExamId()
            );

            System.out.println(
                    "Questions retrieved: " + questions.size()
            );

            for (Question question : questions) {

                Object[] row = {
                        question.getQuestionId(),
                        question.getQuestionText(),
                        question.getOptionA(),
                        question.getOptionB(),
                        question.getOptionC(),
                        question.getOptionD(),
                        question.getCorrectOption(),
                        question.getMarks()
                };

                tableModel.addRow(row);
            }

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error loading questions: " + ex.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}
