package parser.parsing.SAX;

import classes.Person;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ParserSAXTest {
    @Test
    void fromJsonString() {
        ParserSAX ps = new ParserSAX();
        String jsonTest = "\"cat\"";
        assertEquals("cat", ps.fromJson(jsonTest, String.class));
    }

    @Test
    void fromJsonInt() {
        ParserSAX ps = new ParserSAX();
        String jsonTest = "100";
        assertEquals(100, ps.fromJson(jsonTest, Integer.class));
    }

    @Test
    void fromJsonBoolean() {
        ParserSAX ps = new ParserSAX();
        String jsonTest = "true";
        assertEquals(true, ps.fromJson(jsonTest, Boolean.class));
    }

    @Test
    void fromJsonArray(){
        ParserSAX ps = new ParserSAX();
        String jsonTest = "[ 1, 2, 3, 4, 5 ]";
        Integer[] mas = {1, 2, 3, 4, 5};
        Integer[] mas2 = ps.fromJson(jsonTest, mas.getClass());
        assertArrayEquals(mas, mas2);
    }

    @Test
    void fromJsonObjectUser(){
        String p = "{\n\"firstName\": \"Anna\", \n\"lastName\": \"Bergova\", \n\"age\": 18\n}";
        ParserSAX ps = new ParserSAX();
        String jsonTest ="{\n\"firstName\": \"Anna\", \n\"lastName\": \"Bergova\", \n\"age\": 18\n}";
        Person person2 = new Person("Anna", "Bergova", 18);
        assertEquals(person2, ps.fromJson(jsonTest, Person.class));
    }
}