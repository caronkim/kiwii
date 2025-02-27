package org.example.kiwii.vo.stockupdown;

public class StockUpDownTrialVO {
    private String uuid;
    private String predictGameId;
    private String userAnswer;
    private String isCor;

    public StockUpDownTrialVO() {
    }

    public StockUpDownTrialVO(String uuid, String predictGameId, String userAnswer, String isCor) {
        this.uuid = uuid;
        this.predictGameId = predictGameId;
        this.userAnswer = userAnswer;
        this.isCor = isCor;
    }

    // PREDICT_STOCK_LOG 입력를 위한 VO
    public StockUpDownTrialVO(String uuid, String isCor){
        this.uuid = uuid;
        this.isCor = isCor;
    }

    // PREDICT_STOCK_TRIALS 입력을 위한 VO
    public StockUpDownTrialVO(String uuid, String predictGameId, String userAnswer){
        this.uuid = uuid;
        this.predictGameId = predictGameId;
        this.userAnswer = userAnswer;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getPredictGameId() {
        return predictGameId;
    }

    public void setPredictGameId(String predictGameId) {
        this.predictGameId = predictGameId;
    }

    public String getUserAnswer() {
        return userAnswer;
    }

    public void setUserAnswer(String userAnswer) {
        this.userAnswer = userAnswer;
    }

    public String getIsCor() {
        return isCor;
    }

    public void setIsCor(String isCor) {
        this.isCor = isCor;
    }
}
