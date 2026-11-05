package com.Keshaw.oes.Service;

import com.Keshaw.oes.dao.QuestionDAO;
import com.Keshaw.oes.model.Question;
import com.Keshaw.oes.model.Result;
import com.Keshaw.oes.util.DBconnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class ExamEvaluator {

    private final QuestionDAO questionDAO = new QuestionDAO();

    public Result evaluateExam(
            int userId,
            int examId,
            Map<Integer, String> selectedAnswers)
            throws SQLException, IOException {

        List<Question> questions =
                questionDAO.getQuestionsByExamId(examId);

        int score = 0;
        int correctAnswers = 0;
        int wrongAnswers = 0;

        String sql = "SELECT correct_option, marks "
                + "FROM questions WHERE question_id = ? "
                + "AND exam_id = ?";

        try (Connection con = DBconnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            for (Question question : questions) {

                String selectedOption =
                        selectedAnswers.get(question.getQuestionId());

                ps.setInt(1, question.getQuestionId());
                ps.setInt(2, examId);

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        String correctOption =
                                rs.getString("correct_option");
                        int marks = rs.getInt("marks");

                        if (selectedOption != null) {
                            if (selectedOption.equalsIgnoreCase(
                                    correctOption)) {
                                score += marks;
                                correctAnswers++;
                            } else {
                                wrongAnswers++;
                            }
                        }
                    }
                }
            }
        }

        Result result = new Result();
        result.setUserId(userId);
        result.setExamId(examId);
        result.setScore(score);
        result.setTotalQuestions(questions.size());
        result.setCorrectAnswers(correctAnswers);
        result.setWrongAnswers(wrongAnswers);

        return result;
    }

    public boolean evaluateAndSaveExam(
            int userId,
            int examId,
            Map<Integer, String> selectedAnswers)
            throws SQLException, IOException {

        Result result = evaluateExam(
                userId, examId, selectedAnswers);

        com.Keshaw.oes.dao.ResultDAO resultDAO =
                new com.Keshaw.oes.dao.ResultDAO();

        return resultDAO.saveResult(result);
    }
}
