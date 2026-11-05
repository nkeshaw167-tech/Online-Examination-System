package com.Keshaw.oes.dao;

import com.Keshaw.oes.model.Result;
import com.Keshaw.oes.util.DBconnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class ResultDAO {

    public boolean saveResult(Result result)
            throws SQLException, IOException {

        String sql = "INSERT INTO results "
                + "(user_id, exam_id, score, total_questions, "
                + "correct_answers, wrong_answers) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection con = DBconnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, result.getUserId());
            ps.setInt(2, result.getExamId());
            ps.setInt(3, result.getScore());
            ps.setInt(4, result.getTotalQuestions());
            ps.setInt(5, result.getCorrectAnswers());
            ps.setInt(6, result.getWrongAnswers());

            return ps.executeUpdate() > 0;
        }
    }
    public List<Result> getResultsByUserId(int userId)
            throws SQLException, IOException {

        List<Result> results = new ArrayList<>();

        String sql = "SELECT r.result_id, r.user_id, r.exam_id, " +
                "r.score, r.total_questions, r.correct_answers, " +
                "r.wrong_answers, r.attempted_at, e.title " +
                "FROM results r " +
                "JOIN exams e ON r.exam_id = e.exam_id " +
                "WHERE r.user_id = ? " +
                "ORDER BY r.attempted_at DESC";

        try (Connection con = DBconnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {

                    Result result = new Result();

                    result.setResultId(rs.getInt("result_id"));
                    result.setUserId(rs.getInt("user_id"));
                    result.setExamId(rs.getInt("exam_id"));
                    result.setExamTitle(rs.getString("title"));
                    result.setScore(rs.getInt("score"));
                    result.setTotalQuestions(rs.getInt("total_questions"));
                    result.setCorrectAnswers(rs.getInt("correct_answers"));
                    result.setWrongAnswers(rs.getInt("wrong_answers"));

                    result.setAttemptedAt(
                            rs.getTimestamp("attempted_at").toLocalDateTime()
                    );

                    results.add(result);
                }
            }
        }

        return results;
    }

    public List<Object[]> getAllResults()
            throws SQLException, IOException {

        List<Object[]> results = new ArrayList<>();

        String sql = "SELECT r.result_id, u.full_name, u.email, " +
                "e.title, r.score, r.total_questions, " +
                "r.correct_answers, r.wrong_answers, r.attempted_at " +
                "FROM results r " +
                "JOIN users u ON r.user_id = u.user_id " +
                "JOIN exams e ON r.exam_id = e.exam_id " +
                "ORDER BY r.attempted_at DESC";

        try (Connection con = DBconnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Object[] row = {
                        rs.getInt("result_id"),
                        rs.getString("full_name"),
                        rs.getString("email"),
                        rs.getString("title"),
                        rs.getInt("score"),
                        rs.getInt("total_questions"),
                        rs.getInt("correct_answers"),
                        rs.getInt("wrong_answers"),
                        rs.getTimestamp("attempted_at")
                };

                results.add(row);
            }
        }

        return results;
    }
}
