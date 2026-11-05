package com.Keshaw.oes.model;

import java.time.LocalDateTime;

public class Result {

    private String examTitle;
    private int resultId;
    private int userId;
    private int examId;
    private int score;
    private int totalQuestions;
    private int correctAnswers;
    private int wrongAnswers;
    private LocalDateTime attemptedAt;

    public String getExamTitle() {
        return examTitle;
    }
    public Result() {
    }

    public void setExamTitle(String examTitle) {
        this.examTitle = examTitle;
    }

    public Result(int resultId, int userId, int examId,
                  int score, int totalQuestions,
                  int correctAnswers, int wrongAnswers,
                  LocalDateTime attemptedAt) {
        this.resultId = resultId;
        this.userId = userId;
        this.examId = examId;
        this.score = score;
        this.totalQuestions = totalQuestions;
        this.correctAnswers = correctAnswers;
        this.wrongAnswers = wrongAnswers;
        this.attemptedAt = attemptedAt;
    }

    public int getResultId() {
        return resultId;
    }

    public void setResultId(int resultId) {
        this.resultId = resultId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getExamId() {
        return examId;
    }

    public void setExamId(int examId) {
        this.examId = examId;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getTotalQuestions() {
        return totalQuestions;
    }

    public void setTotalQuestions(int totalQuestions) {
        this.totalQuestions = totalQuestions;
    }

    public int getCorrectAnswers() {
        return correctAnswers;
    }

    public void setCorrectAnswers(int correctAnswers) {
        this.correctAnswers = correctAnswers;
    }

    public int getWrongAnswers() {
        return wrongAnswers;
    }

    public void setWrongAnswers(int wrongAnswers) {
        this.wrongAnswers = wrongAnswers;
    }

    public LocalDateTime getAttemptedAt() {
        return attemptedAt;
    }

    public void setAttemptedAt(LocalDateTime attemptedAt) {
        this.attemptedAt = attemptedAt;
    }
}
