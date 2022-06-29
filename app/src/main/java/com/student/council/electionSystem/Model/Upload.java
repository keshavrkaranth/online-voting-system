package com.student.council.electionSystem.Model;

public class Upload {
    private  int votes;
    private String name;
    private String imageUrl;
    private String category;
    private String description;
    private String pid;

    public Upload(){
        //empty constructor
    }

    public Upload(String name, String imageUrl, String category, String description, String pid,int votes) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.category = category;
        this.description = description;
        this.pid = pid;
        this.votes = votes;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }


    public String getName() {
        return name;
    }

    public int getVotes(){
        return votes;

   }

    public void setVotes(int votes) {
        this.votes = votes;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
