package com.student.council.electionSystem.Model;

public class Vote {
    private String Name, Party;

    public Vote() {
    }

    public Vote(String name, String party) {
        Name = name;
        Party = party;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getParty() {
        return Party;
    }

    public void setParty(String party) {
        Party = party;
    }
}
