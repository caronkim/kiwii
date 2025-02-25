package org.example.kiwii.vo;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

public class WordleTrialVO {
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

    public List<Character> getCharacterList() {
        return Arrays.stream(this.characters.split(","))
                .map(s -> s.charAt(0))
                .collect(Collectors.toList());
    }

    public void setCharacters(String characters) {
        this.characters = characters;
    }


    public String getStrikes() {
        return strikes;
    }

    public List<Boolean> getStrikeList() {
        return Arrays.stream(this.strikes.split(","))
                .map(s -> s.equals("1"))
                .collect(Collectors.toList());
    }

    public void setStrikes(String strikes) {
        this.strikes = strikes;
    }

    public String getBalls() {
        return balls;
    }

    public List<Boolean> getBallList() {
        return Arrays.stream(this.balls.split(","))
                .map(s -> s.equals("1"))
                .collect(Collectors.toList());
    }

    public void setBalls(String balls) {
        this.balls = balls;
    }

    public String getNones() {
        return nones;
    }

    public List<Boolean> getNoneList() {
        return Arrays.stream(this.nones.split(","))
                .map(s -> s.equals("1"))
                .collect(Collectors.toList());
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

    public boolean checkAnswer(List<Character> answer) {
        if (answer == null || this.characters == null) {
            return false;
        }

        if (answer.isEmpty() || this.characters.isEmpty()) {
            return false;
        }

        List<Character> tryCharacters = this.getCharacterList();
        if (answer.size() != tryCharacters.size()) {
            return false;
        }

        StringBuilder strikes = new StringBuilder();
        StringBuilder balls = new StringBuilder();
        StringBuilder nones = new StringBuilder();
        Set<Character> answerSet = new HashSet<>(answer);
        int strikesCount = 0;
        for (int i = 0; i < answer.size(); i++) {
            if (answer.get(i).equals(tryCharacters.get(i))) {
                strikes.append('1');
                balls.append('0');
                nones.append('0');
                strikesCount++;
            } else if (answerSet.contains(tryCharacters.get(i))) {
                strikes.append('0');
                balls.append('1');
                nones.append('0');
            } else {
                strikes.append('0');
                balls.append('0');
                nones.append('1');
            }
            strikes.append(',');
            balls.append(',');
            nones.append(',');
        }

        strikes.deleteCharAt(strikes.length() - 1);
        balls.deleteCharAt(balls.length() - 1);
        nones.deleteCharAt(nones.length() - 1);
        this.strikes = strikes.toString();
        this.balls = balls.toString();
        this.nones = nones.toString();
        this.isAnswer = strikesCount == answer.size();
        return true;
    }
}
