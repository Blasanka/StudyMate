package com.mad.studymate.cardview.model;

public class User {
    private int userId;
    private int imgUrl;
    private String userName;
    private String email;
    private String password;
    private int birthYear;
    private int score;

    public User(int userId, int imgUrl, String userName, String email, String password, int birthYear) {
        this.userId = userId;
        this.imgUrl = imgUrl;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.birthYear = birthYear;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getImgId() {
        return imgUrl;
    }

    public void getImgId(int imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(int birthYear) {
        this.birthYear = birthYear;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
