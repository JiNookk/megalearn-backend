package jinookk.ourlms.controllers;

import jinookk.ourlms.applications.inquiry.CreateInquiryService;
import jinookk.ourlms.applications.inquiry.DeleteInquiryService;
import jinookk.ourlms.applications.inquiry.GetInquiryService;
import jinookk.ourlms.applications.inquiry.UpdateInquiryService;
import jinookk.ourlms.dtos.InquiriesDto;
import jinookk.ourlms.dtos.InquiryDeleteDto;
import jinookk.ourlms.dtos.InquiryDto;
import jinookk.ourlms.fixtures.Fixture;
import jinookk.ourlms.models.entities.Inquiry;
import jinookk.ourlms.models.vos.UserName;
import jinookk.ourlms.models.vos.ids.CourseId;
import jinookk.ourlms.models.vos.ids.InquiryId;
import jinookk.ourlms.models.vos.ids.LectureId;
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

@WebMvcTest(InquiryController.class)
class InquiryControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GetInquiryService getInquiryService;

    @MockBean
    private CreateInquiryService createInquiryService;

    @MockBean
    private UpdateInquiryService updateInquiryService;

    @MockBean
    private DeleteInquiryService deleteInquiryService;

    @SpyBean
    private JwtUtil jwtUtil;

    private String accessToken;

    @BeforeEach
    void setup() {
        accessToken = jwtUtil.encode(new UserName("userName@email.com"));
    }

    @Test
    void inquiry() throws Exception {
        InquiryDto inquiryDto = Fixture.inquiry("test").toInquiryDto();

        given(getInquiryService.detail(new InquiryId(1L))).willReturn(inquiryDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/inquiries/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"content\":\"test\"")
                ));
    }

    @Test
    void list() throws Exception {
        InquiryDto inquiryDto = Fixture.inquiry("test").toInquiryDto();

        InquiriesDto inquiriesDto = new InquiriesDto(List.of(inquiryDto));
        given(getInquiryService.list(new LectureId(1L), null, null)).willReturn(inquiriesDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/lectures/1/inquiries"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"inquiries\":[")
                ));
    }

    @Test
    void myInquiries() throws Exception {
        InquiryDto inquiryDto = Fixture.inquiry("test").toInquiryDto();

        InquiriesDto inquiriesDto = new InquiriesDto(List.of(inquiryDto));

        given(getInquiryService.myInquiries(new UserName("userName@email.com"))).willReturn(inquiriesDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/inquiries/me")
                        .header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"inquiries\":[")
                ));
    }

    @Test
    void listByCourseId() throws Exception {
        InquiryDto inquiryDto = Fixture.inquiry("test").toInquiryDto();

        InquiriesDto inquiriesDto = new InquiriesDto(List.of(inquiryDto));

        given(getInquiryService.listByCourseId(new CourseId(1L))).willReturn(inquiriesDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/courses/1/inquiries"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"inquiries\":[")
                ));
    }

    @Test
    void post() throws Exception {
        InquiryDto inquiryDto = Fixture.inquiry("test").toInquiryDto();

        given(createInquiryService.create(any(), any())).willReturn(inquiryDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/inquiries")
                        .header("Authorization", "Bearer " + accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"hashTag\": \"hashtag\", " +
                                "\"lectureId\": 1, " +
                                "\"courseId\": 1, " +
                                "\"title\": \"title\", " +
                                "\"content\": \"test\", " +
                                "\"publisher\": \"tester\", " +
                                "\"anonymous\": true" +
                                "}"))
                .andExpect(status().isCreated())
                .andExpect(content().string(
                        containsString("\"content\":\"test\"")));
    }

    @Test
    void postWithBlankProperties() throws Exception {
        InquiryDto inquiryDto = Fixture.inquiry("test").toInquiryDto();

        given(createInquiryService.create(any(), any())).willReturn(inquiryDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/inquiries")
                        .header("Authorization", "Bearer " + accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"hashTag\": \"hashtag\", " +
                                "\"content\": \"test\", " +
                                "\"isAnonymous\": true" +
                                "}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(
                        containsString("Property is Missing")
                ));
    }

    @Test
    void update() throws Exception {
        InquiryDto inquiryDto = Fixture.inquiry("test").toInquiryDto();

        given(updateInquiryService.update(any(), any())).willReturn(inquiryDto);

        mockMvc.perform(MockMvcRequestBuilders.patch("/inquiries/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"hashTag\": [\"hashtag\",\"hashtag2\",\"hashtag3\"], " +
                                "\"title\": \"title\", " +
                                "\"content\": \"test\" " +
                                "}"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"content\":\"test\"")));
    }

    @Test
    void increaseHits() throws Exception {
        Inquiry inquiry = Fixture.inquiry("test");

        Inquiry increased = inquiry.increaseHits();

        InquiryDto inquiryDto = increased.toInquiryDto();

        given(updateInquiryService.increaseHits(any())).willReturn(inquiryDto);

        mockMvc.perform(MockMvcRequestBuilders.patch("/inquiries/1/hits"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"hits\":1")));
    }

    @Test
    void delete() throws Exception {
        InquiryDeleteDto inquiryDto = Fixture.inquiry("test").toInquiryDeleteDto();

        given(deleteInquiryService.delete(any())).willReturn(inquiryDto);

        mockMvc.perform(MockMvcRequestBuilders.delete("/inquiries/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"inquiryId\":1")
                ));
    }
}
