package com.example.web_stduy_project2.controller;

import com.example.web_stduy_project2.entity.Board;
import com.example.web_stduy_project2.param.AddBoardParam;
import com.example.web_stduy_project2.param.EditBoardParam;
import com.example.web_stduy_project2.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.atmosphere.config.service.Get;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.net.URI;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RequestMapping
@RequiredArgsConstructor
@RestController
public class BoardController {
    private final BoardService boardService;

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE,consumes = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity addBoard(@RequestBody AddBoardParam param) throws Exception
    {

        Board board =boardService.addBoard(param);
        URI createdURI = linkTo(BoardController.class).slash(board.getId()).toUri();
        return ResponseEntity.created(createdURI).body(board);
    }
    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity getBoardList() throws Exception
    {
        return ResponseEntity.ok(boardService.getBoardList());
    }
    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity getBoard(@PathVariable("id") Long id) throws Exception {
        Board board = boardService.getBoard(id);
        if (board == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(board);
        }
    }
    @PutMapping(value = "/{id}",produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity editBoard(@PathVariable("id") Long id, @RequestBody EditBoardParam param) throws  Exception
    {
        Board board =boardService.editBoard(param,id);
        if(board ==null)
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);

        }
        else
        {
            return ResponseEntity.ok(board);
        }
    }



    @DeleteMapping(value = "{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity deleteBoard(@PathVariable("id") Long seq) throws Exception {
        if (boardService.deleteBoard(seq)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }


}
