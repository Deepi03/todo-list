package com.practice.todolist.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TodoResponse {
    String name;
    String description;
    String status;
    private String createdTimeStamp;
    private String updatedTimeStamp;
}
