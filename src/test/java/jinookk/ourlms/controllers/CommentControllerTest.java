package jinookk.ourlms.controllers;

import jinookk.ourlms.applications.comment.CreateCommentService;
import jinookk.ourlms.applications.comment.DeleteCommentService;
import jinookk.ourlms.applications.comment.GetCommentService;
import jinookk.ourlms.applications.comment.UpdateCommentService;
import jinookk.ourlms.dtos.CommentDeleteDto;
import jinookk.ourlms.dtos.CommentDto;
import jinookk.ourlms.dtos.CommentsDto;
import jinookk.ourlms.fixtures.Fixture;
import jinookk.ourlms.models.entities.Comment;
import jinookk.ourlms.models.vos.UserName;
import jinookk.ourlms.models.vos.ids.InquiryId;
import jinookk.ourlms.utils.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
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
    private GetCommentService getCommentService;

    @MockBean
    private CreateCommentService createCommentService;

    @MockBean
    private UpdateCommentService updateCommentService;

    @MockBean
    private DeleteCommentService deleteCommentService;

    @SpyBean
    private JwtUtil jwtUtil;

    private String accessToken;

    @BeforeEach
    void setup() {
        accessToken = jwtUtil.encode(new UserName("userName@email.com"));
    }

    @Test
    void list() throws Exception {
        CommentDto commentDto = Fixture.comment("hi").toCommentDto();

        CommentsDto commentsDto = new CommentsDto(List.of(commentDto));
        given(getCommentService.list(new InquiryId(1L), new UserName("userName@email.com"))).willReturn(commentsDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/inquiries/1/comments")
                        .header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isOk()
                )
                .andExpect(content().string(
                        containsString("\"comments\":[")
                ));
    }

    @Test
    void post() throws Exception {
        CommentDto commentDto = Fixture.comment("hi").toCommentDto();

        given(createCommentService.create(any(), any())).willReturn(commentDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/comments")
                        .header("Authorization", "Bearer " + accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"content\":\"hi\", " +
                                "\"inquiryId\":1" +
                                "}")
                )
                .andExpect(status().isCreated())
                .andExpect(content().string(
                        containsString("\"content\":\"hi\"")
                ));
    }

    @Test
    void update() throws Exception {
        CommentDto commentDto = Fixture.comment("updated").toCommentDto();

        given(updateCommentService.update(any(), any(), any())).willReturn(commentDto);

        mockMvc.perform(MockMvcRequestBuilders.patch("/comments/11")
                        .header("Authorization", "Bearer " + accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("\"content\":\"updated\"")
                )
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"content\":\"updated\"")
                ));
    }

    @Test
    void delete() throws Exception {
        CommentDeleteDto commentDeleteDto = Fixture.comment("hi").toCommentDeleteDto();
        given(deleteCommentService.delete(any(), any())).willReturn(commentDeleteDto);

        mockMvc.perform(MockMvcRequestBuilders.delete("/comments/11")
                        .header("Authorization", "Bearer " + accessToken)
                )
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"commentId\":11")
                ));
    }
}
