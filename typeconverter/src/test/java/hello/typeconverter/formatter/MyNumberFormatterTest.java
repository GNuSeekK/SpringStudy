package hello.typeconverter.formatter;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.text.ParseException;
import java.util.Locale;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class MyNumberFormatterTest {


    MyNumberFormatter formatter = new MyNumberFormatter();

    @Test
    void parse() throws ParseException {
        assertThat(formatter.parse("1,000", Locale.KOREA)).isEqualTo(1000L); // Long 타입 주의
    }

    @Test
    void print() {
        assertThat(formatter.print(1000, Locale.KOREA)).isEqualTo("1,000");
    }
}