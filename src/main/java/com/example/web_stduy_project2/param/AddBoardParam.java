package com.example.web_stduy_project2.param;

import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class AddBoardParam {
    private String username;
    private String content;
}
