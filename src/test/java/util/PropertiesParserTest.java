package util;

import com.ccat.util.PropertiesParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.*;

public class PropertiesParserTest {
    PropertiesParser INSTANCE;
    String PROPERTY_KEY_1 = "testProp";
    String PROPERTY_KEY_2 = "test2Property";

    String PROPERTY_FILE_1 = "test1.properties";
    String PROPERTY_FILE_2 = "test2.properties";

    @BeforeEach
    void beforeEach() {
        INSTANCE = PropertiesParser.getInstance(PROPERTY_FILE_1);
    }

    @Test
    public void test_getExistingProperty_returnStringProperty() {
        // given
        String should = "abc";
        // when
        String property = INSTANCE.getProperty(PROPERTY_KEY_1);

        // then
        assertThat(property).isNotNull();
        assertThat(property).isEqualTo(should);
    }

    @Test
    public void test_getNonexistentProperty_returnNull() {
        // given

        // when
        PropertiesParser newParserInstance = PropertiesParser.getInstance(PROPERTY_FILE_2);
        String in1 = newParserInstance.getProperty(PROPERTY_KEY_1);

        // then
        assertThat(in1).isNull();
    }

    @Test
    public void test_getDifferentParserFileInstance_returnStringProperty() {
        //given
        String valueIn2 = "123";

        // when
        PropertiesParser newParserInstance = PropertiesParser.getInstance(PROPERTY_FILE_2);
        String in1 = newParserInstance.getProperty(PROPERTY_KEY_1);
        String in2 = newParserInstance.getProperty(PROPERTY_KEY_2);

        // then
        assertThat(in1).isNull();
        assertThat(in2).isEqualTo(valueIn2);
    }

    @Test
    public void test_getNonexistentFileParserInstance_throwNullPointerException() {
        // when
        assertThatThrownBy(() -> {
            //given
                    String fileNameThatDoesNotExist = "NAN.properties";
                    PropertiesParser.getInstance(fileNameThatDoesNotExist);
        }
        // then
        ).isInstanceOf(NullPointerException.class);
    }
}
