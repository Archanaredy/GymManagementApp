package com.example.gymmanagementapp.pojo;

public class AddUserScheduleRequest {
    private String userId;
    private String startTime;
    private String endTime;
    private String day;

    public AddUserScheduleRequest(String userId, String startTime, String endTime, String day) {
        this.userId = userId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.day = day;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }
}
