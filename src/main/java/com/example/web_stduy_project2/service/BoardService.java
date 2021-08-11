package com.example.web_stduy_project2.service;

import com.example.web_stduy_project2.entity.Board;
import com.example.web_stduy_project2.param.AddBoardParam;
import com.example.web_stduy_project2.param.EditBoardParam;
import com.example.web_stduy_project2.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;
    public List<Board> getBoardList() throws Exception{
        return boardRepository.findAll();
    }
    public Board getBoard(Long id) throws Exception
    {
        return boardRepository.findById(id).orElseGet(() ->null);
    }
    @Transactional
    public Board editBoard(EditBoardParam param,Long id) throws Exception
    {
        Board board =boardRepository.findById(id).orElseGet(()-> null);
        if(board != null)
        {
            board.setContent(param.getContent());
        }
        return board;
    }
    @Transactional
    public Board addBoard(AddBoardParam param) throws Exception
    {
        return boardRepository.save(new Board(param));
    }
    @Transactional
    public boolean deleteBoard(Long id) throws Exception
    {
        Board board =boardRepository.findById(id).orElse(null);
        if (board == null)
        {
            return false;
        }
        boardRepository.delete(board);
        return true;
    }
}
