package jinookk.ourlms.practice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ObjectMapperTest {
    ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setup() {
        objectMapper = new ObjectMapper();
    }

    @Test
    void serialize() throws JsonProcessingException {
        Numbers numbers = new Numbers(1, 2, 3);

        String json = objectMapper.writeValueAsString(numbers);

        assertThat(json).isEqualTo("{" +
                "\"number1\":1," +
                "\"number2\":2," +
                "\"number3\":3" +
                "}");
    }

    @Test
    void serializeWithNullField() throws JsonProcessingException {
        Numbers numbers = new Numbers(1, 2);

        String json = objectMapper.writeValueAsString(numbers);

        assertThat(json).isEqualTo("{" +
                "\"number1\":1," +
                "\"number2\":2," +
                "\"number3\":null" +
                "}");
    }
}