package com.example.web_stduy_project2;

import com.example.web_stduy_project2.entity.Board;
import com.example.web_stduy_project2.param.AddBoardParam;
import com.example.web_stduy_project2.param.EditBoardParam;
import com.example.web_stduy_project2.repository.BoardRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class TestBoardController {
    private final String BASE_URL = "/boards";
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    WebApplicationContext ctx;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(ctx)
                // 2.2 버전 이후 mock 객체에서 한글 인코딩 처리해야함. -> 필터추가
                .addFilter(new CharacterEncodingFilter("UTF-8", true))
                .alwaysDo(print())
                .build();
    }

    @Order(1)
    @DisplayName("게시글 등록")
    @Test
    public void addBoard() throws Exception {
        AddBoardParam param = AddBoardParam.builder()
                .content("게시글을 등록합니다.")
                .username("유저1")
                .build();

        MvcResult mvcResult = mockMvc.perform(post(BASE_URL)
                        .accept(MediaTypes.HAL_JSON_VALUE)
                        .contentType(MediaTypes.HAL_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(param)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();
        Board board = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Board.class);
        Assertions.assertEquals(board.getContent(), param.getContent());
        Assertions.assertEquals(board.getUsername(), param.getUsername());
        Board savedBoard = boardRepository.findById(board.getId()).orElse(null);
        Assertions.assertEquals(savedBoard.getContent(), param.getContent());
        Assertions.assertEquals(savedBoard.getUsername(), param.getUsername());
    }

    @Order(2)
    @DisplayName("게시글 리스트 조회")
    @Test
    public void getBoardList() throws Exception {
        mockMvc.perform(get(BASE_URL)
                        .accept(MediaTypes.HAL_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Order(3)
    @DisplayName("게시글 1건 조회(OK)")
    @Test
    public void getBoard() throws Exception {
        mockMvc.perform(get(BASE_URL + "/{id}", 1L)
                        .accept(MediaTypes.HAL_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Order(4)
    @DisplayName("게시글 1건 조회(NOF FOUND)")
    @Test
    public void getBoard_NotFound() throws Exception {
        mockMvc.perform(get(BASE_URL + "/{id}", -1L)
                        .accept(MediaTypes.HAL_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Order(5)
    @DisplayName("게시글 수정(OK)")
    @Test
    public void editBoard() throws Exception {
        EditBoardParam param = EditBoardParam.builder().content("수정하는 게시글입니다.").build();

        MvcResult mvcResult = mockMvc.perform(put(BASE_URL + "/{id}", 1L)
                        .contentType(MediaTypes.HAL_JSON_VALUE)
                        .accept(MediaTypes.HAL_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(param)))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        Board board = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Board.class);
        Assertions.assertEquals(param.getContent(), board.getContent());
        Assertions.assertEquals(param.getContent(), boardRepository.findById(1L).orElse(null).getContent());
    }

    @Order(6)
    @DisplayName("게시글 수정(Not Found)")
    @Test
    public void editBoard_NotFound() throws Exception {
        EditBoardParam param = EditBoardParam.builder().content("수정하는 게시글입니다.").build();

        mockMvc.perform(put(BASE_URL + "/{id}", -2L)
                        .contentType(MediaTypes.HAL_JSON_VALUE)
                        .accept(MediaTypes.HAL_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(param)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Order(10)
    @DisplayName("게시글 삭제")
    @Test
    public void deleteBoard() throws Exception {
        mockMvc.perform(delete(BASE_URL + "/{id}", 1L)
                        .accept(MediaTypes.HAL_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk());

        Assertions.assertNull(boardRepository.findById(1L).orElse(null));
    }

    @Order(11)
    @DisplayName("게시글 삭제(Not Found)")
    @Test
    public void deleteBoard_NotFound() throws Exception {
        mockMvc.perform(delete(BASE_URL + "/{id}", -1L)
                        .accept(MediaTypes.HAL_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

}
