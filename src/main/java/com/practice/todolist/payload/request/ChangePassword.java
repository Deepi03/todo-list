package com.practice.todolist.payload.request;

import java.text.SimpleDateFormat;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor

public class ChangePassword {

    private String email;
    private String oldPasssword;
    private String newPassword;

    public ChangePassword(String email, String oldPasssword, String newPassword) {
        this.email = email;
        this.oldPasssword = oldPasssword;
        this.newPassword = newPassword;

    }
}
