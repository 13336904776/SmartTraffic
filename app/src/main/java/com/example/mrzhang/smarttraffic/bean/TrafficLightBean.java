package com.example.mrzhang.smarttraffic.bean;

public class TrafficLightBean {

    /**
     * ERRMSG : 成功
     * GreenTime : 55
     * YellowTime : 5
     * RESULT : S
     * RedTime : 25
     */


    private int GreenTime;
    private int YellowTime;
    private int RedTime;
    private int roadId;

    public TrafficLightBean() {
    }


    public TrafficLightBean(int greenTime, int yellowTime, int redTime,int roadId) {
        GreenTime = greenTime;
        YellowTime = yellowTime;
        RedTime = redTime;
        this.roadId = roadId;
    }

    public int getRoadId() {
        return roadId;
    }

    public void setRoadId(int roadId) {
        this.roadId = roadId;
    }
    public int getGreenTime() {
        return GreenTime;
    }

    public void setGreenTime(int GreenTime) {
        this.GreenTime = GreenTime;
    }

    public int getYellowTime() {
        return YellowTime;
    }

    public void setYellowTime(int YellowTime) {
        this.YellowTime = YellowTime;
    }

    public int getRedTime() {
        return RedTime;
    }

    public void setRedTime(int RedTime) {
        this.RedTime = RedTime;
    }
}
