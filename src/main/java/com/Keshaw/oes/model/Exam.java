package com.Keshaw.oes.model;

public class Exam {

    private int examId;
    private String title;
    private String description;
    private int durationMinutes;
    private boolean active;

    public Exam() {
    }

    @Override
    public String toString() {
        return title;
    }

    public Exam(int examId, String title, String description,
                int durationMinutes, boolean active) {
        this.examId = examId;
        this.title = title;
        this.description = description;
        this.durationMinutes = durationMinutes;
        this.active = active;
    }

    public int getExamId() {
        return examId;
    }

    public void setExamId(int examId) {
        this.examId = examId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }

    public void setDurationMinutes(int durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}

