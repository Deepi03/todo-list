package com.practice.todolist.payload.response;

import lombok.Data;

@Data
public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private Long id;
    private String email;

    public JwtResponse(String accessToken, Long id, String username, String email) {
        this.token = accessToken;
        this.id = id;
        this.email = email;
    }

}
