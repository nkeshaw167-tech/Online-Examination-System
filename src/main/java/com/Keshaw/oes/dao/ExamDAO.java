package com.Keshaw.oes.dao;

import com.Keshaw.oes.model.Exam;
import com.Keshaw.oes.util.DBconnection;

import java.sql.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExamDAO {

    public List<Exam> getActiveExams()
            throws SQLException, IOException {

        List<Exam> exams = new ArrayList<>();

        String sql = "SELECT exam_id, title, description, " +
                "duration_minutes, is_active " +
                "FROM exams WHERE is_active = TRUE " +
                "ORDER BY exam_id";

        try (Connection con = DBconnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Exam exam = new Exam();

                exam.setExamId(rs.getInt("exam_id"));
                exam.setTitle(rs.getString("title"));
                exam.setDescription(rs.getString("description"));
                exam.setDurationMinutes(
                        rs.getInt("duration_minutes"));
                exam.setActive(rs.getBoolean("is_active"));

                exams.add(exam);
            }
        }

        return exams;
    }


    public List<Exam> getAllExams()
            throws SQLException, IOException {

        List<Exam> exams = new ArrayList<>();

        String sql = "SELECT exam_id, title, description, " +
                "duration_minutes, is_active " +
                "FROM exams ORDER BY exam_id";

        try (Connection con = DBconnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Exam exam = new Exam();

                exam.setExamId(rs.getInt("exam_id"));
                exam.setTitle(rs.getString("title"));
                exam.setDescription(rs.getString("description"));
                exam.setDurationMinutes(
                        rs.getInt("duration_minutes"));
                exam.setActive(rs.getBoolean("is_active"));

                exams.add(exam);
            }
        }

        return exams;
    }


    // Add a new exam
    public boolean addExam(Exam exam) {

        String sql = "INSERT INTO exams " +
                "(title, description, duration_minutes, is_active) " +
                "VALUES (?, ?, ?, ?)";

        try (Connection conn = DBconnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, exam.getTitle());
            pstmt.setString(2, exam.getDescription());
            pstmt.setInt(3, exam.getDurationMinutes());
            pstmt.setBoolean(4, true);

            int rows = pstmt.executeUpdate();

            return rows > 0;

        } catch (SQLException | IOException e) {

            e.printStackTrace();
            return false;
        }
    }
    public boolean updateExam(Exam exam)
            throws SQLException, IOException {

        String sql = "UPDATE exams SET title = ?, description = ?, " +
                "duration_minutes = ? WHERE exam_id = ?";

        try (Connection con = DBconnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, exam.getTitle());
            ps.setString(2, exam.getDescription());
            ps.setInt(3, exam.getDurationMinutes());
            ps.setInt(4, exam.getExamId());

            return ps.executeUpdate() > 0;
        }
    }

    public boolean updateExamStatus(int examId, boolean isActive)
            throws SQLException, IOException {

        String sql = "UPDATE exams SET is_active = ? WHERE exam_id = ?";

        try (Connection con = DBconnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setBoolean(1, isActive);
            ps.setInt(2, examId);

            return ps.executeUpdate() > 0;
        }
    }
    public boolean deleteExam(int examId)
            throws SQLException, IOException {

        String sql = "DELETE FROM exams WHERE exam_id = ?";

        try (Connection con = DBconnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, examId);

            return ps.executeUpdate() > 0;
        }
    }
}
