package jinookk.ourlms.backdoor;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
@AutoConfigureMockMvc
class BackdoorControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void setupCourses() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/backdoor/setup-course-db"))
                .andExpect(status().isOk());
    }

    @Test
    void setupLectures() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/backdoor/setup-lecture-db"))
                .andExpect(status().isOk());
    }
}
