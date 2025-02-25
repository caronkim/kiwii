package org.example.kiwii.dto.wordle;

import org.example.kiwii.vo.wordle.WordleTrialVO;

import java.sql.Timestamp;

public class ResponsePostWordleTrialDTO {
    private int id;
    private int quizId;
    private int uuid;
    private String characters;
    private String strikes;
    private String balls;
    private String nones;
    private boolean isAnswer;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private Timestamp deletedAt;

    public ResponsePostWordleTrialDTO() {
    }

    public ResponsePostWordleTrialDTO(WordleTrialVO wordleTrialVO) {
        this.id = wordleTrialVO.getId();
        this.quizId = wordleTrialVO.getQuizId();
        this.uuid = wordleTrialVO.getUuid();
        this.characters = wordleTrialVO.getCharacters();
        this.strikes = wordleTrialVO.getStrikes();
        this.balls = wordleTrialVO.getBalls();
        this.nones = wordleTrialVO.getNones();
        this.isAnswer = wordleTrialVO.isAnswer();
        this.createdAt = wordleTrialVO.getCreatedAt();
        this.updatedAt = wordleTrialVO.getUpdatedAt();
        this.deletedAt = wordleTrialVO.getDeletedAt();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuizId() {
        return quizId;
    }

    public void setQuizId(int quizId) {
        this.quizId = quizId;
    }

    public int getUuid() {
        return uuid;
    }

    public void setUuid(int uuid) {
        this.uuid = uuid;
    }

    public String getCharacters() {
        return characters;
    }

    public void setCharacters(String characters) {
        this.characters = characters;
    }

    public String getStrikes() {
        return strikes;
    }

    public void setStrikes(String strikes) {
        this.strikes = strikes;
    }

    public String getBalls() {
        return balls;
    }

    public void setBalls(String balls) {
        this.balls = balls;
    }

    public String getNones() {
        return nones;
    }

    public void setNones(String nones) {
        this.nones = nones;
    }

    public boolean isAnswer() {
        return isAnswer;
    }

    public void setAnswer(boolean answer) {
        isAnswer = answer;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Timestamp getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Timestamp deletedAt) {
        this.deletedAt = deletedAt;
    }
}
