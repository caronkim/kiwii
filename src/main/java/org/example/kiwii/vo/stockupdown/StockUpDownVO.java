package org.example.kiwii.vo.stockupdown;

public class StockUpDownVO {
    private String uuid;
    private String ticker;
    private String predictGameId;
    private double prevClose;
    private double todayClose;

    public StockUpDownVO() {
    }

    public StockUpDownVO(String ticker, String predictGameId, double prevClose, double todayClose) {
        this.ticker = ticker;
        this.predictGameId = predictGameId;
        this.prevClose = prevClose;
        this.todayClose = todayClose;
    }

    // 문제 출제용 VO
    public StockUpDownVO(String ticker, double prevClose) {
        this.ticker = ticker;
        this.prevClose = prevClose;
    }

    // 정답 입력용 VO
    public StockUpDownVO(String predictGameId, String ticker, double todayClose) {
        this.predictGameId = predictGameId;
        this.ticker = ticker;
        this.todayClose = todayClose;
    }
}
