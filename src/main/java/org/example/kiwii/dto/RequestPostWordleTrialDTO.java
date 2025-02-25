package org.example.kiwii.dto;

public class RequestPostWordleTrialDTO {
    private int userId;
    private int quizId;
    private String characters;

    public RequestPostWordleTrialDTO() {
    }

    public RequestPostWordleTrialDTO(int userId, int quizId, String characters) {
        this.userId = userId;
        this.quizId = quizId;
        this.characters = characters;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getQuizId() {
        return quizId;
    }

    public void setQuizId(int quizId) {
        this.quizId = quizId;
    }

    public String getCharacters() {
        return characters;
    }

    public void setCharacters(String characters) {
        this.characters = characters;
    }
}
