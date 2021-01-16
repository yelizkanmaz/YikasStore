package com.example.yikasstore;

class UserModel {
    private String userMail;
    private String userPass;
    private String userName;
    private String userSurname;
    private String userId;
    public UserModel(String uId, String name, String mail, String pass) {
        this.userId = uId;
        this.userName = name;
        this.userMail = mail;
        this.userPass = pass;
    }

    public String getUserMail() {
        return userMail;
    }

    public void setUserMail(String userMail) {
        this.userMail = userMail;
    }

    public String getUserPass() {
        return userPass;
    }

    public void setUserPass(String userPass) {
        this.userPass = userPass;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserSurname() {
        return userSurname;
    }

    public void setUserSurname(String userSurname) {
        this.userSurname = userSurname;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
