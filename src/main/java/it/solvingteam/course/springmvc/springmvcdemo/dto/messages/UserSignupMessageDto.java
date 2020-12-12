package it.solvingteam.course.springmvc.springmvcdemo.dto.messages;

import javax.validation.constraints.NotEmpty;

public class UserSignupMessageDto {


    @NotEmpty(message = "Username Required")
    private String username;

    @NotEmpty(message = "Password Required")
    private String password;

//    @NotEmpty(message = "Please Repeat Password")
    private String repeatePassword;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRepeatePassword() {
        return repeatePassword;
    }

    public void setRepeatePassword(String repeatePassword) {
        this.repeatePassword = repeatePassword;
    }
}
