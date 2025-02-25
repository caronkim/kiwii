package org.example.kiwii.dto;

public class QuizAnswerDTO {
    private int userId;
    private int correctAnswer;

    public QuizAnswerDTO(int userId, int correctAnswer) {
        this.userId = userId;
        this.correctAnswer = correctAnswer;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(int correctAnswer) {
        this.correctAnswer = correctAnswer;
    }
}
