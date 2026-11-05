package com.Keshaw.oes.ui;

import com.Keshaw.oes.model.User;
import com.Keshaw.oes.model.Result;
import com.Keshaw.oes.dao.ResultDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ResultHistoryFrame extends JFrame {

    private final User loggedInUser;
    private JTable resultTable;
    private JLabel attemptsValueLabel;
    private JLabel correctValueLabel;
    private JLabel accuracyValueLabel;

    // Theme colors
    private static final Color PRIMARY = new Color(37, 99, 235);
    private static final Color DARK_BLUE = new Color(30, 58, 138);
    private static final Color BACKGROUND = new Color(245, 247, 251);
    private static final Color CARD_BACKGROUND = Color.WHITE;
    private static final Color TEXT_COLOR = new Color(31, 41, 55);
    private static final Color SECONDARY_TEXT = new Color(107, 114, 128);
    private static final Color BORDER_COLOR = new Color(229, 231, 235);

    public ResultHistoryFrame(User user) {

        this.loggedInUser = user;

        setTitle("Online Examination System - Result History");
        setSize(1100, 680);
        setMinimumSize(new Dimension(900, 550));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        getContentPane().setBackground(BACKGROUND);

        JPanel mainPanel = new JPanel(new BorderLayout(0, 22));
        mainPanel.setBackground(BACKGROUND);
        mainPanel.setBorder(
                BorderFactory.createEmptyBorder(25, 30, 25, 30)
        );

        // Header
        JPanel headerPanel = createHeaderPanel();
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Center section
        JPanel centerPanel = new JPanel(new BorderLayout(0, 20));
        centerPanel.setBackground(BACKGROUND);

        // Summary cards
        JPanel summaryPanel = new JPanel(
                new GridLayout(1, 3, 18, 0)
        );
        summaryPanel.setBackground(BACKGROUND);

        attemptsValueLabel = new JLabel("0");
        correctValueLabel = new JLabel("0");
        accuracyValueLabel = new JLabel("0%");

        summaryPanel.add(createSummaryCard(
                "Total Attempts", attemptsValueLabel, PRIMARY
        ));

        summaryPanel.add(createSummaryCard(
                "Total Correct Answers",
                correctValueLabel,
                new Color(22, 163, 74)
        ));

        summaryPanel.add(createSummaryCard(
                "Average Accuracy",
                accuracyValueLabel,
                new Color(124, 58, 237)
        ));

        centerPanel.add(summaryPanel, BorderLayout.NORTH);

        // Result table
        String[] columns = {
                "Result ID",
                "Exam Name",
                "Score",
                "Total Questions",
                "Correct",
                "Wrong",
                "Attempt Date"
        };

        DefaultTableModel tableModel = new DefaultTableModel(
                columns, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        resultTable = new JTable(tableModel);

        styleResultTable();

        JScrollPane scrollPane = new JScrollPane(resultTable);
        scrollPane.setBorder(
                BorderFactory.createLineBorder(BORDER_COLOR)
        );
        scrollPane.getViewport().setBackground(Color.WHITE);

        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(BACKGROUND);
        tablePanel.setBorder(
                BorderFactory.createEmptyBorder(0, 0, 0, 0)
        );

        JLabel tableHeading = new JLabel("Examination Records");
        tableHeading.setFont(
                new Font("Segoe UI", Font.BOLD, 19)
        );
        tableHeading.setForeground(TEXT_COLOR);
        tableHeading.setBorder(
                BorderFactory.createEmptyBorder(0, 0, 12, 0)
        );

        tablePanel.add(tableHeading, BorderLayout.NORTH);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        centerPanel.add(tablePanel, BorderLayout.CENTER);

        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // Footer
        JButton backButton = new JButton("Back to Dashboard");

        backButton.setFont(
                new Font("Segoe UI", Font.BOLD, 14)
        );
        backButton.setForeground(Color.WHITE);
        backButton.setBackground(PRIMARY);
        backButton.setFocusPainted(false);
        backButton.setBorderPainted(false);
        backButton.setCursor(
                new Cursor(Cursor.HAND_CURSOR)
        );
        backButton.setPreferredSize(
                new Dimension(180, 42)
        );

        backButton.addActionListener(e -> {
            new StudentDashboard(loggedInUser);
            dispose();
        });

        JPanel footerPanel = new JPanel(
                new FlowLayout(FlowLayout.CENTER)
        );
        footerPanel.setBackground(BACKGROUND);
        footerPanel.add(backButton);

        mainPanel.add(footerPanel, BorderLayout.SOUTH);

        add(mainPanel);

        loadResultHistory();

        setVisible(true);
    }

    // Header panel
    private JPanel createHeaderPanel() {

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(DARK_BLUE);
        headerPanel.setBorder(
                BorderFactory.createEmptyBorder(20, 25, 20, 25)
        );

        JLabel heading = new JLabel("My Examination History");
        heading.setFont(
                new Font("Segoe UI", Font.BOLD, 25)
        );
        heading.setForeground(Color.WHITE);

        JLabel subtitle = new JLabel(
                "Track your examination performance and progress"
        );
        subtitle.setFont(
                new Font("Segoe UI", Font.PLAIN, 13)
        );
        subtitle.setForeground(new Color(219, 234, 254));

        JPanel textPanel = new JPanel();
        textPanel.setLayout(
                new BoxLayout(textPanel, BoxLayout.Y_AXIS)
        );
        textPanel.setBackground(DARK_BLUE);

        textPanel.add(heading);
        textPanel.add(Box.createVerticalStrut(5));
        textPanel.add(subtitle);

        headerPanel.add(textPanel, BorderLayout.WEST);

        return headerPanel;
    }

    // Summary card
    private JPanel createSummaryCard(
            String title,
            JLabel valueLabel,
            Color accentColor) {

        JPanel card = new JPanel(new BorderLayout(5, 12));
        card.setBackground(CARD_BACKGROUND);

        card.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(BORDER_COLOR),
                        BorderFactory.createEmptyBorder(18, 20, 18, 20)
                )
        );

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(
                new Font("Segoe UI", Font.BOLD, 13)
        );
        titleLabel.setForeground(SECONDARY_TEXT);

        valueLabel.setFont(
                new Font("Segoe UI", Font.BOLD, 27)
        );
        valueLabel.setForeground(accentColor);

        card.add(titleLabel, BorderLayout.NORTH);
        card.add(valueLabel, BorderLayout.CENTER);

        return card;
    }

    // Table styling
    private void styleResultTable() {

        resultTable.setRowHeight(38);
        resultTable.setFont(
                new Font("Segoe UI", Font.PLAIN, 13)
        );

        resultTable.setForeground(TEXT_COLOR);
        resultTable.setBackground(Color.WHITE);
        resultTable.setSelectionBackground(
                new Color(219, 234, 254)
        );
        resultTable.setSelectionForeground(TEXT_COLOR);

        resultTable.setShowGrid(false);
        resultTable.setIntercellSpacing(
                new Dimension(0, 0)
        );

        resultTable.setFillsViewportHeight(true);
        resultTable.setAutoCreateRowSorter(true);

        JTableHeader header = resultTable.getTableHeader();

        header.setPreferredSize(new Dimension(0, 42));
        header.setFont(
                new Font("Segoe UI", Font.BOLD, 13)
        );
        header.setBackground(DARK_BLUE);
        header.setForeground(Color.WHITE);
        header.setReorderingAllowed(false);

        DefaultTableCellRenderer headerRenderer =
                new DefaultTableCellRenderer() {

                    @Override
                    public Component getTableCellRendererComponent(
                            JTable table,
                            Object value,
                            boolean isSelected,
                            boolean hasFocus,
                            int row,
                            int column) {

                        JLabel label = (JLabel) super
                                .getTableCellRendererComponent(
                                        table, value, isSelected,
                                        hasFocus, row, column
                                );

                        label.setBackground(DARK_BLUE);
                        label.setForeground(Color.WHITE);
                        label.setFont(
                                new Font("Segoe UI", Font.BOLD, 13)
                        );
                        label.setHorizontalAlignment(
                                SwingConstants.CENTER
                        );
                        label.setBorder(
                                BorderFactory.createEmptyBorder(5, 8, 5, 8)
                        );

                        return label;
                    }
                };

        for (int i = 0; i < resultTable.getColumnModel()
                .getColumnCount(); i++) {

            resultTable.getColumnModel()
                    .getColumn(i)
                    .setHeaderRenderer(headerRenderer);
        }

        // Center-align table cell values
        DefaultTableCellRenderer cellRenderer =
                new DefaultTableCellRenderer() {

                    @Override
                    public Component getTableCellRendererComponent(
                            JTable table,
                            Object value,
                            boolean isSelected,
                            boolean hasFocus,
                            int row,
                            int column) {

                        JLabel label = (JLabel) super
                                .getTableCellRendererComponent(
                                        table, value, isSelected,
                                        hasFocus, row, column
                                );

                        if (!isSelected) {
                            label.setBackground(
                                    row % 2 == 0
                                            ? Color.WHITE
                                            : new Color(248, 250, 252)
                            );
                        }

                        label.setForeground(TEXT_COLOR);
                        label.setHorizontalAlignment(
                                SwingConstants.CENTER
                        );

                        label.setBorder(
                                BorderFactory.createEmptyBorder(5, 8, 5, 8)
                        );

                        return label;
                    }
                };

        for (int i = 0; i < resultTable.getColumnModel()
                .getColumnCount(); i++) {

            resultTable.getColumnModel()
                    .getColumn(i)
                    .setCellRenderer(cellRenderer);
        }
    }

    // Load result history
    private void loadResultHistory() {

        try {

            ResultDAO resultDAO = new ResultDAO();

            List<Result> results = resultDAO.getResultsByUserId(
                    loggedInUser.getUserId()
            );

            DefaultTableModel model =
                    (DefaultTableModel) resultTable.getModel();

            model.setRowCount(0);

            if (results.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "You have not attempted any examinations yet.",
                        "No Results",
                        JOptionPane.INFORMATION_MESSAGE
                );

                return;
            }

            int totalCorrect = 0;
            int totalQuestions = 0;

            DateTimeFormatter formatter =
                    DateTimeFormatter.ofPattern(
                            "dd MMM yyyy, hh:mm a"
                    );

            for (Result result : results) {

                totalCorrect += result.getCorrectAnswers();
                totalQuestions += result.getTotalQuestions();

                String attemptDate =
                        result.getAttemptedAt() == null
                                ? "N/A"
                                : result.getAttemptedAt().format(formatter);

                model.addRow(new Object[]{

                        result.getResultId(),
                        result.getExamTitle(),
                        result.getScore(),
                        result.getTotalQuestions(),
                        result.getCorrectAnswers(),
                        result.getWrongAnswers(),
                        attemptDate
                });
            }

            // Update summary cards directly

            int attempts = results.size();

            double accuracy = totalQuestions == 0
                    ? 0
                    : (totalCorrect * 100.0) / totalQuestions;

            attemptsValueLabel.setText(String.valueOf(attempts));

            correctValueLabel.setText(String.valueOf(totalCorrect));

            accuracyValueLabel.setText(
                    String.format("%.2f%%", accuracy)
            );
        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to load result history.",
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );

            e.printStackTrace();
        }
    }
}
