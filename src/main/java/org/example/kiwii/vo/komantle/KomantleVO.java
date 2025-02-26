package org.example.kiwii.vo.komantle;

public class KomantleVO {
    private String word;
    private int rank;
    private double cosineSimilarity;
    private String uuid;

    public KomantleVO() {
    }

    public KomantleVO(String word, int rank, double cosineSimilarity, String uuid) {
        this.word = word;
        this.rank = rank;
        this.cosineSimilarity = cosineSimilarity;
        this.uuid = uuid;
    }

    public KomantleVO(int rank, double cosineSimilarity) {
        this.rank = rank;
        this.cosineSimilarity = cosineSimilarity;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public double getCosineSimilarity() {
        return cosineSimilarity;
    }

    public void setCosineSimilarity(double cosineSimilarity) {
        this.cosineSimilarity = cosineSimilarity;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
