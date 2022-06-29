package com.student.council.electionSystem.Model;

public class VoteTimeSet {
    private String startDate;
    private String endDate;

    public VoteTimeSet() {
    }

    public VoteTimeSet(String startDate, String endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
