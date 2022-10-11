package com.practice.todolist.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangePassword {

    private String email;
    private String oldPasssword;
    private String newPassword;

}
