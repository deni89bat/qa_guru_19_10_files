package guru.qa;

import com.fasterxml.jackson.databind.ObjectMapper;
import guru.qa.parsing.data.JsonFileToObj;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.io.InputStreamReader;


public class JsonTest {
    @Test
    void jsonBenefitsTest() throws Exception {
        ObjectMapper mapper = new ObjectMapper();


        try (InputStream is = getClass().getClassLoader().getResourceAsStream("benefits.json");
             InputStreamReader isr = new InputStreamReader(is)) {

            JsonFileToObj jsonFileToObj = mapper.readValue(isr, JsonFileToObj.class);
            Assertions.assertEquals("16555692", jsonFileToObj.patientId);
            Assertions.assertArrayEquals((new int[]{666, 777}), jsonFileToObj.benefits);
        }
    }
}
