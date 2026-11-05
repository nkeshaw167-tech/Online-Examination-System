package com.Keshaw.oes.ui;

import com.Keshaw.oes.dao.ExamDAO;
import com.Keshaw.oes.dao.ResultDAO;
import com.Keshaw.oes.model.Exam;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class AdminResultFrame extends JFrame {

    private JTable resultTable;
    private DefaultTableModel tableModel;

    private JTextField searchField;
    private JComboBox<Exam> examComboBox;

    private final Color DARK_BLUE = new Color(25, 55, 100);
    private final Color PRIMARY_BLUE = new Color(45, 105, 190);
    private final Color LIGHT_BACKGROUND = new Color(242, 246, 252);
    private final Color TEXT_COLOR = new Color(45, 55, 75);
    private final Color BORDER_COLOR = new Color(215, 225, 238);

    public AdminResultFrame() {

        setTitle("Student Examination Results");
        setSize(1200, 650);
        setMinimumSize(new Dimension(950, 600));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout(0, 18));
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

        JLabel heading = new JLabel("Student Examination Results");
        heading.setFont(new Font("Arial", Font.BOLD, 27));
        heading.setForeground(Color.WHITE);

        JLabel subtitle = new JLabel(
                "Monitor student performance and examination history"
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

        // Search and Filter Panel
        JPanel controlPanel = new JPanel(new BorderLayout(15, 0));
        controlPanel.setBackground(Color.WHITE);
        controlPanel.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(BORDER_COLOR),
                        BorderFactory.createEmptyBorder(15, 18, 15, 18)
                )
        );

        // Search Panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        searchPanel.setBackground(Color.WHITE);

        JLabel searchLabel = new JLabel("Search Student:");
        searchLabel.setFont(new Font("Arial", Font.BOLD, 14));
        searchLabel.setForeground(TEXT_COLOR);

        searchField = new JTextField(18);
        searchField.setFont(new Font("Arial", Font.PLAIN, 14));
        searchField.setPreferredSize(new Dimension(220, 36));

        JButton searchButton = new JButton("Search");
        styleButton(searchButton, PRIMARY_BLUE);

        searchButton.addActionListener(e -> applyFilters());

        searchField.addActionListener(e -> applyFilters());

        searchPanel.add(searchLabel);
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        // Exam Filter Panel
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        filterPanel.setBackground(Color.WHITE);

        JLabel examLabel = new JLabel("Select Exam:");
        examLabel.setFont(new Font("Arial", Font.BOLD, 14));
        examLabel.setForeground(TEXT_COLOR);

        examComboBox = new JComboBox<>();
        examComboBox.setFont(new Font("Arial", Font.PLAIN, 14));
        examComboBox.setPreferredSize(new Dimension(190, 36));

        JButton filterButton = new JButton("Apply Filter");
        styleButton(filterButton, new Color(35, 145, 115));

        filterButton.addActionListener(e -> applyFilters());

        filterPanel.add(examLabel);
        filterPanel.add(examComboBox);
        filterPanel.add(filterButton);

        controlPanel.add(searchPanel, BorderLayout.WEST);
        controlPanel.add(filterPanel, BorderLayout.EAST);

        // Table Columns
        String[] columns = {
                "Result ID",
                "Student Name",
                "Email",
                "Exam Name",
                "Score",
                "Total Questions",
                "Correct",
                "Wrong",
                "Attempt Date"
        };

        tableModel = new DefaultTableModel(columns, 0) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        resultTable = new JTable(tableModel);

        resultTable.setFont(new Font("Arial", Font.PLAIN, 13));
        resultTable.setRowHeight(38);
        resultTable.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );
        resultTable.setSelectionBackground(
                new Color(215, 230, 250)
        );
        resultTable.setSelectionForeground(TEXT_COLOR);
        resultTable.setGridColor(BORDER_COLOR);
        resultTable.setShowVerticalLines(false);
        resultTable.setIntercellSpacing(new Dimension(0, 1));
        resultTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        resultTable.setFillsViewportHeight(true);

        // Table Header
        JTableHeader tableHeader = resultTable.getTableHeader();

        tableHeader.setFont(new Font("Arial", Font.BOLD, 13));
        tableHeader.setBackground(DARK_BLUE);
        tableHeader.setForeground(Color.WHITE);
        tableHeader.setPreferredSize(new Dimension(0, 42));
        tableHeader.setReorderingAllowed(false);

        // Column Widths
        int[] columnWidths = {
                85, 160, 210, 170, 90, 130, 85, 85, 180
        };

        for (int i = 0; i < columnWidths.length; i++) {
            resultTable.getColumnModel()
                    .getColumn(i)
                    .setPreferredWidth(columnWidths[i]);
        }

        // Center Alignment
        DefaultTableCellRenderer centerRenderer =
                new DefaultTableCellRenderer();

        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        int[] centerColumns = {0, 4, 5, 6, 7};

        for (int column : centerColumns) {
            resultTable.getColumnModel()
                    .getColumn(column)
                    .setCellRenderer(centerRenderer);
        }

        JScrollPane scrollPane = new JScrollPane(resultTable);
        scrollPane.setBorder(
                BorderFactory.createLineBorder(BORDER_COLOR)
        );
        scrollPane.getViewport().setBackground(Color.WHITE);

        // Results Area
        JPanel resultsPanel = new JPanel(new BorderLayout(0, 12));
        resultsPanel.setBackground(Color.WHITE);
        resultsPanel.setBorder(
                BorderFactory.createEmptyBorder(12, 12, 12, 12)
        );

        JLabel resultsHeading = new JLabel("Examination Records");
        resultsHeading.setFont(new Font("Arial", Font.BOLD, 17));
        resultsHeading.setForeground(DARK_BLUE);

        resultsPanel.add(resultsHeading, BorderLayout.NORTH);
        resultsPanel.add(scrollPane, BorderLayout.CENTER);

        // Center Content
        JPanel centerPanel = new JPanel(new BorderLayout(0, 15));
        centerPanel.setBackground(LIGHT_BACKGROUND);

        centerPanel.add(controlPanel, BorderLayout.NORTH);
        centerPanel.add(resultsPanel, BorderLayout.CENTER);

        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // Bottom Buttons
        JButton resetButton = new JButton("Reset Filters");
        JButton backButton = new JButton("Back");

        styleButton(resetButton, new Color(100, 110, 125));
        styleButton(backButton, DARK_BLUE);

        resetButton.addActionListener(e -> resetFilters());
        backButton.addActionListener(e -> dispose());

        JPanel bottomPanel = new JPanel(
                new FlowLayout(FlowLayout.RIGHT, 12, 0)
        );

        bottomPanel.setBackground(LIGHT_BACKGROUND);
        bottomPanel.add(resetButton);
        bottomPanel.add(backButton);

        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);

        // Load Data
        loadExams();
        loadResults();

        setVisible(true);
    }

    // Button Styling
    private void styleButton(JButton button, Color color) {

        button.setFont(new Font("Arial", Font.BOLD, 13));
        button.setBackground(color);
        button.setForeground(Color.WHITE);

        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setContentAreaFilled(true);

        button.setPreferredSize(new Dimension(125, 38));

        button.setCursor(
                new Cursor(Cursor.HAND_CURSOR)
        );
    }

    // Load All Results
    private void loadResults() {

        tableModel.setRowCount(0);

        try {

            ResultDAO resultDAO = new ResultDAO();

            List<Object[]> results = resultDAO.getAllResults();

            for (Object[] row : results) {
                tableModel.addRow(row);
            }

        } catch (SQLException | IOException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error loading results: " + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );

            e.printStackTrace();
        }
    }

    // Load Exams
    private void loadExams() {

        try {

            ExamDAO examDAO = new ExamDAO();

            List<Exam> exams = examDAO.getAllExams();

            examComboBox.removeAllItems();

            Exam allExams = new Exam();
            allExams.setExamId(-1);
            allExams.setTitle("All Exams");

            examComboBox.addItem(allExams);

            for (Exam exam : exams) {
                examComboBox.addItem(exam);
            }

        } catch (SQLException | IOException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error loading exams: " + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );

            e.printStackTrace();
        }
    }

    // Apply Search and Exam Filters Together
    private void applyFilters() {

        String searchText = searchField.getText()
                .trim()
                .toLowerCase();

        Exam selectedExam = (Exam) examComboBox.getSelectedItem();

        tableModel.setRowCount(0);

        try {

            ResultDAO resultDAO = new ResultDAO();

            List<Object[]> results = resultDAO.getAllResults();

            for (Object[] row : results) {

                String studentName = row[1].toString().toLowerCase();
                String email = row[2].toString().toLowerCase();
                String examName = row[3].toString();

                boolean matchesStudent =
                        studentName.contains(searchText) ||
                                email.contains(searchText);

                boolean matchesExam =
                        selectedExam == null ||
                                selectedExam.getExamId() == -1 ||
                                examName.equalsIgnoreCase(
                                        selectedExam.getTitle()
                                );

                if (matchesStudent && matchesExam) {
                    tableModel.addRow(row);
                }
            }

        } catch (SQLException | IOException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error filtering results: " + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );

            e.printStackTrace();
        }
    }

    // Reset Filters
    private void resetFilters() {

        searchField.setText("");

        if (examComboBox.getItemCount() > 0) {
            examComboBox.setSelectedIndex(0);
        }

        loadResults();
    }
}