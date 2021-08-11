package com.example.web_stduy_project2;

import com.example.web_stduy_project2.entity.Board;
import com.example.web_stduy_project2.repository.BoardRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@AllArgsConstructor
public class WebStduyProject2Application {
    private BoardRepository boardRepository;


    public static void main(String[] args) {
        SpringApplication.run(WebStduyProject2Application.class, args);
    }
    @Bean
    ApplicationRunner applicationRunner() {
        return args -> {
            for(int i = 1; i <= 10; i++) {
                boardRepository.save(new Board("유저" + i, "유저" + i + "의 게시판 내용"));
            }
        };
    }

}
