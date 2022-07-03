package com.student.council.electionSystem.Model;

public class VoteTimeSet {
    private String start_date;
    private String end_date;

    public VoteTimeSet() {
    }

    public VoteTimeSet(String start_date, String end_date) {
        this.start_date = start_date;
        this.end_date = end_date;
    }

    public String getStartDate() {
        return start_date;
    }

    public void setStartDate(String start_date) {
        this.start_date = start_date;
    }

    public String getEndDate() {
        return end_date;
    }

    public void setEndDate(String end_date) {
        this.end_date = end_date;
    }
}
