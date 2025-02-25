package org.example.kiwii.dto.wordle;

import org.example.kiwii.vo.wordle.WordleQuizVO;

import java.sql.Timestamp;

public class ResponseGetWordleQuizDTO {
    private int quizId;
    private int characterLength;
    private Timestamp startedAt;
    private Timestamp finishedAt;

    public ResponseGetWordleQuizDTO() {
    }

    public ResponseGetWordleQuizDTO(int quizId, int characterLength, Timestamp startedAt, Timestamp finishedAt) {
        this.quizId = quizId;
        this.characterLength = characterLength;
        this.startedAt = startedAt;
        this.finishedAt = finishedAt;
    }

    public ResponseGetWordleQuizDTO(WordleQuizVO wordleQuizVO) {
        this.quizId = wordleQuizVO.getId();
        this.characterLength = wordleQuizVO.getWord().getCharacterLength();
        this.startedAt = wordleQuizVO.getStartedAt();
        this.finishedAt = wordleQuizVO.getFinishedAt();
    }

    public int getQuizId() {
        return quizId;
    }

    public void setQuizId(int quizId) {
        this.quizId = quizId;
    }

    public int getCharacterLength() {
        return characterLength;
    }

    public void setCharacterLength(int characterLength) {
        this.characterLength = characterLength;
    }

    public Timestamp getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(Timestamp startedAt) {
        this.startedAt = startedAt;
    }

    public Timestamp getFinishedAt() {
        return finishedAt;
    }

    public void setFinishedAt(Timestamp finishedAt) {
        this.finishedAt = finishedAt;
    }
}
