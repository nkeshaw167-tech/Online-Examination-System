package com.Keshaw.oes.ui;

import com.Keshaw.oes.dao.ExamDAO;
import com.Keshaw.oes.model.Exam;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.List;

public class ManageExamFrame extends JFrame {

    private JTable examTable;
    private DefaultTableModel tableModel;

    private final Color DARK_BLUE = new Color(25, 55, 100);
    private final Color PRIMARY_BLUE = new Color(45, 105, 190);
    private final Color LIGHT_BACKGROUND = new Color(242, 246, 252);
    private final Color TEXT_COLOR = new Color(45, 55, 75);
    private final Color BORDER_COLOR = new Color(215, 225, 238);

    public ManageExamFrame() {

        setTitle("Manage Examinations");
        setSize(1100, 650);
        setMinimumSize(new Dimension(900, 550));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

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

        JLabel heading = new JLabel("Manage Examinations");
        heading.setFont(new Font("Arial", Font.BOLD, 27));
        heading.setForeground(Color.WHITE);

        JLabel subtitle = new JLabel(
                "View, edit, activate, deactivate and delete examinations"
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

        // Table
        String[] columns = {
                "Exam ID",
                "Exam Title",
                "Description",
                "Duration (Minutes)",
                "Status"
        };

        tableModel = new DefaultTableModel(columns, 0) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        examTable = new JTable(tableModel);

        examTable.setFont(new Font("Arial", Font.PLAIN, 14));
        examTable.setRowHeight(38);
        examTable.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );
        examTable.setSelectionBackground(
                new Color(215, 230, 250)
        );
        examTable.setSelectionForeground(TEXT_COLOR);
        examTable.setGridColor(BORDER_COLOR);
        examTable.setShowVerticalLines(false);
        examTable.setIntercellSpacing(new Dimension(0, 1));
        examTable.setFillsViewportHeight(true);
        examTable.setAutoCreateRowSorter(true);

        // Table Header
        JTableHeader tableHeader = examTable.getTableHeader();

        tableHeader.setFont(
                new Font("Arial", Font.BOLD, 14)
        );
        tableHeader.setBackground(DARK_BLUE);
        tableHeader.setForeground(Color.WHITE);
        tableHeader.setPreferredSize(new Dimension(0, 42));
        tableHeader.setReorderingAllowed(false);

        // Column Widths
        examTable.getColumnModel().getColumn(0).setPreferredWidth(80);
        examTable.getColumnModel().getColumn(1).setPreferredWidth(190);
        examTable.getColumnModel().getColumn(2).setPreferredWidth(400);
        examTable.getColumnModel().getColumn(3).setPreferredWidth(160);
        examTable.getColumnModel().getColumn(4).setPreferredWidth(120);

        // Center Alignment
        DefaultTableCellRenderer centerRenderer =
                new DefaultTableCellRenderer();

        centerRenderer.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        examTable.getColumnModel().getColumn(0)
                .setCellRenderer(centerRenderer);

        examTable.getColumnModel().getColumn(3)
                .setCellRenderer(centerRenderer);

        examTable.getColumnModel().getColumn(4)
                .setCellRenderer(centerRenderer);

        JScrollPane scrollPane = new JScrollPane(examTable);
        scrollPane.setBorder(
                BorderFactory.createLineBorder(BORDER_COLOR)
        );
        scrollPane.getViewport().setBackground(Color.WHITE);

        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(Color.WHITE);
        tablePanel.setBorder(
                BorderFactory.createEmptyBorder(12, 12, 12, 12)
        );
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        mainPanel.add(tablePanel, BorderLayout.CENTER);

        // Buttons
        JButton editButton = new JButton("Edit Exam");
        JButton toggleButton = new JButton("Activate / Deactivate");
        JButton deleteButton = new JButton("Delete Exam");
        JButton backButton = new JButton("Back");

        styleButton(editButton, PRIMARY_BLUE);
        styleButton(toggleButton, new Color(35, 145, 115));
        styleButton(deleteButton, new Color(195, 65, 65));
        styleButton(backButton, new Color(100, 110, 125));

        JPanel buttonPanel = new JPanel(
                new GridLayout(1, 4, 15, 0)
        );

        buttonPanel.setBackground(LIGHT_BACKGROUND);
        buttonPanel.setBorder(
                BorderFactory.createEmptyBorder(5, 0, 0, 0)
        );

        buttonPanel.add(editButton);
        buttonPanel.add(toggleButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(backButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Button Actions
        editButton.addActionListener(e -> editExam());

        toggleButton.addActionListener(e -> toggleExamStatus());

        deleteButton.addActionListener(e -> deleteExam());

        backButton.addActionListener(e -> dispose());

        add(mainPanel);

        loadExams();

        setVisible(true);
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

    // Load All Exams
    private void loadExams() {

        tableModel.setRowCount(0);

        try {

            ExamDAO examDAO = new ExamDAO();

            List<Exam> exams = examDAO.getAllExams();

            for (Exam exam : exams) {

                Object[] row = {
                        exam.getExamId(),
                        exam.getTitle(),
                        exam.getDescription(),
                        exam.getDurationMinutes(),
                        exam.isActive() ? "Active" : "Inactive"
                };

                tableModel.addRow(row);
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to load exams.",
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );

            e.printStackTrace();
        }
    }

    // Edit Exam
    private void editExam() {

        int selectedRow = examTable.getSelectedRow();

        if (selectedRow == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select an exam to edit."
            );

            return;
        }

        int modelRow = examTable.convertRowIndexToModel(selectedRow);

        int examId = (int) tableModel.getValueAt(modelRow, 0);

        try {

            ExamDAO examDAO = new ExamDAO();

            for (Exam exam : examDAO.getAllExams()) {

                if (exam.getExamId() == examId) {

                    EditExamFrame editFrame =
                            new EditExamFrame(exam);

                    editFrame.addWindowListener(
                            new java.awt.event.WindowAdapter() {

                                @Override
                                public void windowClosed(
                                        java.awt.event.WindowEvent e
                                ) {
                                    loadExams();
                                }
                            }
                    );

                    return;
                }
            }

            JOptionPane.showMessageDialog(
                    this,
                    "Exam not found."
            );

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error: " + ex.getMessage()
            );
        }
    }

    // Activate / Deactivate Exam
    private void toggleExamStatus() {

        int selectedRow = examTable.getSelectedRow();

        if (selectedRow == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select an exam."
            );

            return;
        }

        int modelRow = examTable.convertRowIndexToModel(selectedRow);

        int examId = (int) tableModel.getValueAt(modelRow, 0);

        try {

            ExamDAO examDAO = new ExamDAO();

            for (Exam exam : examDAO.getAllExams()) {

                if (exam.getExamId() == examId) {

                    boolean newStatus = !exam.isActive();

                    int choice = JOptionPane.showConfirmDialog(
                            this,
                            "Are you sure you want to " +
                                    (newStatus ? "activate" : "deactivate") +
                                    " this exam?",
                            "Confirm Status Change",
                            JOptionPane.YES_NO_OPTION
                    );

                    if (choice != JOptionPane.YES_OPTION) {
                        return;
                    }

                    if (examDAO.updateExamStatus(examId, newStatus)) {

                        JOptionPane.showMessageDialog(
                                this,
                                "Exam status updated successfully!"
                        );

                        loadExams();

                    } else {

                        JOptionPane.showMessageDialog(
                                this,
                                "Unable to update exam status."
                        );
                    }

                    return;
                }
            }

            JOptionPane.showMessageDialog(
                    this,
                    "Exam not found."
            );

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error: " + ex.getMessage()
            );
        }
    }

    // Delete Exam
    private void deleteExam() {

        int selectedRow = examTable.getSelectedRow();

        if (selectedRow == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select an exam to delete."
            );

            return;
        }

        int modelRow = examTable.convertRowIndexToModel(selectedRow);

        int examId = (int) tableModel.getValueAt(modelRow, 0);

        String examTitle =
                tableModel.getValueAt(modelRow, 1).toString();

        int choice = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete '" + examTitle + "'?\n\n"
                        + "All questions associated with this exam "
                        + "will also be deleted.\n\n"
                        + "Existing student results will be preserved, "
                        + "and deletion may be blocked if results exist.",
                "Confirm Exam Deletion",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (choice != JOptionPane.YES_OPTION) {
            return;
        }

        try {

            ExamDAO examDAO = new ExamDAO();

            if (examDAO.deleteExam(examId)) {

                JOptionPane.showMessageDialog(
                        this,
                        "Exam deleted successfully!"
                );

                loadExams();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Exam could not be deleted."
                );
            }

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to delete exam.\n"
                            + "It may have existing student results.\n\n"
                            + "Details: " + ex.getMessage()
            );
        }
    }
}
