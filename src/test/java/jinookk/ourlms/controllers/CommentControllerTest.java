package jinookk.ourlms.controllers;

import jinookk.ourlms.dtos.CommentDeleteDto;
import jinookk.ourlms.dtos.CommentDto;
import jinookk.ourlms.dtos.CommentsDto;
import jinookk.ourlms.models.entities.Comment;
import jinookk.ourlms.models.vos.AccountId;
import jinookk.ourlms.models.vos.InquiryId;
import jinookk.ourlms.services.CommentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CommentController.class)
class CommentControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CommentService commentService;

    @Test
    void list() throws Exception {
        CommentDto commentDto = Comment.fake("hi").toCommentDto();

        CommentsDto commentsDto = new CommentsDto(List.of(commentDto));
        given(commentService.list(new InquiryId(1L), new AccountId(1L))).willReturn(commentsDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/inquiries/1/comments")
                        .header("Authorization", "Bearer ACCESS.TOKEN"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"comments\":[")
                ));
    }

    @Test
    void post() throws Exception {
        CommentDto commentDto = Comment.fake("hi").toCommentDto();

        given(commentService.create(any(), any())).willReturn(commentDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/comments")
                        .header("Authorization", "Bearer ACCESS.TOKEN")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"content\":\"hi\", " +
                                "\"inquiryId\":1" +
                                "}"))
                .andExpect(status().isCreated())
                .andExpect(content().string(
                        containsString("\"content\":\"hi\"")
                ));
    }

    @Test
    void update() throws Exception {
        CommentDto commentDto = Comment.fake("updated").toCommentDto();

        given(commentService.update(any(), any())).willReturn(commentDto);

        mockMvc.perform(MockMvcRequestBuilders.patch("/comments/11")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("\"content\":\"updated\""))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"content\":\"updated\"")
                ));
    }

    @Test
    void delete() throws Exception {
        CommentDeleteDto commentDeleteDto = Comment.fake("hi").toCommentDeleteDto();
        given(commentService.delete(any())).willReturn(commentDeleteDto);

        mockMvc.perform(MockMvcRequestBuilders.delete("/comments/11"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"commentId\":11")
                ));
    }
}
