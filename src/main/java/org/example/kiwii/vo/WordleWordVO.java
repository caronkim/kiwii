package org.example.kiwii.vo;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class WordleWordVO {
    private long id;
    private String fullText;
    private String characters;
    private int characterLength;
    private boolean canAnswer;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private Timestamp deletedAt;

    public WordleWordVO() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFullText() {
        return fullText;
    }

    public void setFullText(String fullText) {
        this.fullText = fullText;
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

    public int getCharacterLength() {
        return characterLength;
    }

    public void setCharacterLength(int characterLength) {
        this.characterLength = characterLength;
    }

    public boolean isCanAnswer() {
        return canAnswer;
    }

    public void setCanAnswer(boolean canAnswer) {
        this.canAnswer = canAnswer;
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
