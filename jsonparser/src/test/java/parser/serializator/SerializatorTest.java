package parser.serializator;

import classes.Person;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SerializatorTest {
    @Test
    void toJsonPerson() {
        String p = "{\n\"firstName\": \"Anna\", \n\"lastName\": \"Bergova\", \n\"age\": 18\n}";
        Person person = new Person("Anna", "Bergova", 18);
        assertEquals(p, Serializator.toJson(person));
    }

    @Test
    void toJsonString() {
        String test = "cat";
        assertEquals("\"cat\"", Serializator.toJson(test));
    }

    @Test
    void toJsonInt() {
        int test = 100;
        assertEquals("100", Serializator.toJson(test));
    }

    @Test
    void toJsonBoolean() {
        boolean test = true;
        assertEquals("true", Serializator.toJson(test));
    }

    @Test
    void toJsonArray() {
        String[] test = {"cat", "dog", "mouse"};
        assertEquals("[ \"cat\", \"dog\", \"mouse\" ]", Serializator.toJson(test));
    }

    @Test
    void toJsonCollection() {
        String[] test = {"cat", "dog", "mouse"};
        assertEquals("[ \"cat\", \"dog\", \"mouse\" ]", Serializator.toJson(test));
    }

    @Test
    void toJsonMap() {
        String[] test = {"cat", "dog", "mouse"};
        assertEquals("[ \"cat\", \"dog\", \"mouse\" ]", Serializator.toJson(test));
    }


}