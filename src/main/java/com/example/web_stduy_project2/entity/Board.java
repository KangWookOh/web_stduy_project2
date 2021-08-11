package com.example.web_stduy_project2.entity;

import com.example.web_stduy_project2.param.AddBoardParam;
import lombok.*;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.persistence.*;

import java.time.LocalDateTime;

import static javax.persistence.GenerationType.IDENTITY;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
public class Board {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String  username;

    @Column(length = 300)
    private String content;

    @Column(name = "create_at",nullable = false,updatable = false)
    private LocalDateTime createAt;

    public Board(String username,String content)
    {
        this.username=username;
        this.content=content;
        this.createAt=LocalDateTime.now();
    }
    public Board(AddBoardParam param)
    {
        this.username =param.getUsername();
        this.content=param.getContent();
        this.createAt=LocalDateTime.now();
    }

}
