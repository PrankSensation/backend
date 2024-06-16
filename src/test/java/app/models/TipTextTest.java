package app.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.shadow.com.univocity.parsers.common.TextParsingException;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
public class TipTextTest {

    @Test
    void placeholders_are_replaced(){
        TipText tipText = new TipText("Hello, {name}! Today is {day}.");

        Map<String, String> placeholder_mapping = Map.of(
                "name", "Martin",
                "day", "Monday"
        );

        String text_with_placeholders = tipText.getTextWithReplacedPlaceholders(placeholder_mapping);
        String expected_text = "Hello, Martin! Today is Monday.";
        assertEquals(text_with_placeholders, expected_text);
    }

    @Test
    void placeholders_are_correctly_replaced_when_multiple(){
        TipText tipText = new TipText("Hello, {name} is {name}. {day} is {day}.");

        Map<String, String> placeholder_mapping = Map.of(
                "name", "Martin",
                "day", "Monday"
        );

        String text_with_placeholders = tipText.getTextWithReplacedPlaceholders(placeholder_mapping);
        String expected_text = "Hello, Martin is Martin. Monday is Monday.";
        assertEquals(text_with_placeholders, expected_text);
    }

    @Test
    void placeholders_can_be_next_to_each_other(){
        TipText tipText = new TipText("{name}{name}");

        Map<String, String> placeholder_mapping = Map.of(
                "name", "Martin"
        );

        String text_with_placeholders = tipText.getTextWithReplacedPlaceholders(placeholder_mapping);
        String expected_text = "MartinMartin";
        assertEquals(text_with_placeholders, expected_text);
    }

    @Test
    void doesnt_crash_when_text_doesnt_contain_placeholder(){
        TipText tipText = new TipText("Hello, {name}!");

        Map<String, String> placeholder_mapping = Map.of(
                "name", "Martin",
                "day", "Monday"
        );
        tipText.getTextWithReplacedPlaceholders(placeholder_mapping);
    }

}
