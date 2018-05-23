package com.froggy.blog.dto;

public class EditUserDTO {

    private String username;
    private String email;
    private String passwd;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswd() { return passwd; }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }
}
