package jinookk.ourlms.models.vos;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class NameTest {
    @Test
    void randomizeName() {
        Name name = new Name("tester", true);

        String randomName = name.randomizeName();

        System.out.println(randomName);
    }
}