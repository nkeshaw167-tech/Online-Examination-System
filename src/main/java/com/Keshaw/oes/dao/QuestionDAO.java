package com.Keshaw.oes.dao;

import com.Keshaw.oes.model.Question;
import com.Keshaw.oes.util.DBconnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;


public class QuestionDAO {

    public List<Question> getQuestionsByExamId(int examId)
            throws SQLException, IOException {

        List<Question> questions = new ArrayList<>();

        String sql = "SELECT question_id, exam_id, question_text, "
                + "option_a, option_b, option_c, option_d, "
                + "correct_option, marks "
                + "FROM questions WHERE exam_id = ? "
                + "ORDER BY question_id";

        try (Connection con = DBconnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, examId);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    Question question = new Question();

                    question.setQuestionId(
                            rs.getInt("question_id"));
                    question.setExamId(rs.getInt("exam_id"));
                    question.setQuestionText(
                            rs.getString("question_text"));
                    question.setOptionA(rs.getString("option_a"));
                    question.setOptionB(rs.getString("option_b"));
                    question.setOptionC(rs.getString("option_c"));
                    question.setOptionD(rs.getString("option_d"));
                    question.setCorrectOption(
                            rs.getString("correct_option"));
                    question.setMarks(rs.getInt("marks"));

                    questions.add(question);
                }
            }
        }

        return questions;
    }

    public boolean addQuestion(Question question)
            throws SQLException, IOException {

        String sql = "INSERT INTO questions " +
                "(exam_id, question_text, option_a, option_b, " +
                "option_c, option_d, correct_option, marks) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = DBconnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, question.getExamId());
            ps.setString(2, question.getQuestionText());
            ps.setString(3, question.getOptionA());
            ps.setString(4, question.getOptionB());
            ps.setString(5, question.getOptionC());
            ps.setString(6, question.getOptionD());
            ps.setString(7, question.getCorrectOption());
            ps.setInt(8, question.getMarks());

            return ps.executeUpdate() > 0;
        }
    }

    public boolean updateQuestion(Question question)
            throws SQLException, IOException {

        String sql = "UPDATE questions SET " +
                "question_text = ?, " +
                "option_a = ?, " +
                "option_b = ?, " +
                "option_c = ?, " +
                "option_d = ?, " +
                "correct_option = ?, " +
                "marks = ? " +
                "WHERE question_id = ? AND exam_id = ?";

        try (Connection con = DBconnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, question.getQuestionText());
            ps.setString(2, question.getOptionA());
            ps.setString(3, question.getOptionB());
            ps.setString(4, question.getOptionC());
            ps.setString(5, question.getOptionD());
            ps.setString(6, question.getCorrectOption());
            ps.setInt(7, question.getMarks());
            ps.setInt(8, question.getQuestionId());
            ps.setInt(9, question.getExamId());

            return ps.executeUpdate() > 0;
        }
    }

    public boolean deleteQuestion(int questionId, int examId)
            throws SQLException, IOException {

        String sql = "DELETE FROM questions " +
                "WHERE question_id = ? AND exam_id = ?";

        try (Connection con = DBconnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, questionId);
            ps.setInt(2, examId);

            return ps.executeUpdate() > 0;
        }
    }
}
