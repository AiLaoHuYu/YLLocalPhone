package com.yl.yllocalphone.model;

public class HistoryModel {

    private String historyName;
    private String num;
    private String historyTime;

    public HistoryModel(String historyName, String historyTime) {
        this.historyName = historyName;
        this.historyTime = historyTime;
    }

    public HistoryModel(String historyName, String num, String historyTime) {
        this.historyName = historyName;
        this.num = num;
        this.historyTime = historyTime;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getHistoryName() {
        return historyName;
    }

    public void setHistoryName(String historyName) {
        this.historyName = historyName;
    }

    public String getHistoryTime() {
        return historyTime;
    }

    public void setHistoryTime(String historyTime) {
        this.historyTime = historyTime;
    }
}
