package jinookk.ourlms.controllers;

import jinookk.ourlms.applications.note.CreateNoteService;
import jinookk.ourlms.applications.note.DeleteNoteService;
import jinookk.ourlms.applications.note.GetNoteService;
import jinookk.ourlms.applications.note.UpdateNoteService;
import jinookk.ourlms.dtos.NoteDeleteDto;
import jinookk.ourlms.dtos.NoteDto;
import jinookk.ourlms.dtos.NotesDto;
import jinookk.ourlms.models.entities.Note;
import jinookk.ourlms.models.vos.UserName;
import jinookk.ourlms.models.vos.ids.NoteId;
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

@WebMvcTest(NoteController.class)
class NoteControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GetNoteService getNoteService;

    @MockBean
    private CreateNoteService createNoteService;

    @MockBean
    private UpdateNoteService updateNoteService;

    @MockBean
    private DeleteNoteService deleteNoteService;

    @SpyBean
    private JwtUtil jwtUtil;

    private String accessToken;

    @BeforeEach
    void setup() {
        accessToken = jwtUtil.encode(new UserName("userName@email.com"));
    }

    @Test
    void post() throws Exception {
        NoteDto noteDto = Note.fake("hi").toNoteDto();
        given(createNoteService.create(any(), any())).willReturn(noteDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/notes")
                        .header("Authorization", "Bearer " + accessToken)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"content\":\"hi\"," +
                                "\"lectureId\":1," +
                                "\"lectureTime\":{" +
                                "\"minute\":1," +
                                "\"second\":24" +
                                "}" +
                                "}"))
                .andExpect(status().isCreated())
                .andExpect(content().string(
                        containsString("\"minute\":1")
                ));
    }
    @Test
    void list() throws Exception {
        NoteDto noteDto = Note.fake("hi").toNoteDto();
        given(getNoteService.list(any(), any())).willReturn(new NotesDto(List.of(noteDto)));

        mockMvc.perform(MockMvcRequestBuilders.get("/lectures/1/notes")
                        .header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"notes\":[")
                ));
    }

    @Test
    void update() throws Exception {
        NoteDto noteDto = Note.fake("updated").toNoteDto();
        given(updateNoteService.update(any(), any())).willReturn(noteDto);

        mockMvc.perform(MockMvcRequestBuilders.patch("/notes/1")
                        .header("Authorization", "Bearer " + accessToken)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"content\":\"updated\"" +
                                "}"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"content\":\"updated\"")
                ));
    }

    @Test
    void delete() throws Exception {
        NoteDeleteDto noteDeleteDto = new NoteDeleteDto(1L);
        given(deleteNoteService.delete(new NoteId(1L))).willReturn(noteDeleteDto);

        mockMvc.perform(MockMvcRequestBuilders.delete("/notes/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"noteId\":1")
                ));
    }
}
