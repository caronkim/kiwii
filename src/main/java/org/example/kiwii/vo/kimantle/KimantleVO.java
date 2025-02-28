package org.example.kiwii.vo.kimantle;

public class KimantleVO {
    private String word;
    private int rank;
    private double cosineSimilarity;
    private int uuid;

    public KimantleVO() {
    }

    public KimantleVO(String word, int rank, double cosineSimilarity, int uuid) {
        this.word = word;
        this.rank = rank;
        this.cosineSimilarity = cosineSimilarity;
        this.uuid = uuid;
    }

    public KimantleVO(int rank, double cosineSimilarity) {
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

    public int getUuid() {
        return uuid;
    }

    public void setUuid(int uuid) {
        this.uuid = uuid;
    }
}
