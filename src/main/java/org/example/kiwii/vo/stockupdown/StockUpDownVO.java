package org.example.kiwii.vo.stockupdown;

public class StockUpDownVO {
    private int uuid;
    private String ticker;
    private String gameId;
    private  String companyName;
    private double prevClose;
    private double todayClose;

    public StockUpDownVO() {
    }

    public StockUpDownVO(String ticker, String gameId, double prevClose, double todayClose) {
        this.ticker = ticker;
        this.gameId = gameId;
        this.prevClose = prevClose;
        this.todayClose = todayClose;
    }

    // 문제 출제용 VO
    public StockUpDownVO(String ticker, double prevClose) {
        this.ticker = ticker;
        this.prevClose = prevClose;
    }

    // 정답 입력용 VO
    public StockUpDownVO(String gameId, String ticker, double todayClose) {
        this.gameId = gameId;
        this.ticker = ticker;
        this.todayClose = todayClose;
    }

    public int getUuid() {
        return uuid;
    }

    public void setUuid(int uuid) {
        this.uuid = uuid;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public double getPrevClose() {
        return prevClose;
    }

    public void setPrevClose(double prevClose) {
        this.prevClose = prevClose;
    }

    public double getTodayClose() {
        return todayClose;
    }

    public void setTodayClose(double todayClose) {
        this.todayClose = todayClose;
    }
}
