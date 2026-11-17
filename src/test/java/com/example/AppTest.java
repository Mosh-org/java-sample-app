package com.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.Test;

class AppTest {

    @Test
    void roundTrip() {
        User original = new User("Alice", 30, "alice@example.com");
        User parsed = App.parseUser(App.toJson(original));
        assertEquals(original.toString(), parsed.toString());
    }

    @Test
    void toJsonContainsFields() {
        String json = App.toJson(new User("Bob", 5, "bob@example.com"));
        assertTrue(json.contains("\"name\": \"Bob\""));
        assertTrue(json.contains("\"age\": 5"));
    }

    @Test
    void malformedJsonRejected() {
        assertThrows(IllegalArgumentException.class, () -> App.parseUser("{not json"));
    }

    @Test
    void emptyOrNullJsonRejected() {
        assertThrows(IllegalArgumentException.class, () -> App.parseUser(""));
        assertThrows(IllegalArgumentException.class, () -> App.parseUser("null"));
        assertThrows(IllegalArgumentException.class, () -> App.parseUser(null));
    }

    @Test
    void invalidValuesInJsonRejectedDespiteGsonBypassingConstructor() {
        assertThrows(IllegalArgumentException.class,
                () -> App.parseUser("{\"name\":\"\",\"age\":30,\"email\":\"a@b.com\"}"));
        assertThrows(IllegalArgumentException.class,
                () -> App.parseUser("{\"name\":\"A\",\"age\":200,\"email\":\"a@b.com\"}"));
        assertThrows(IllegalArgumentException.class,
                () -> App.parseUser("{\"name\":\"A\",\"age\":30,\"email\":\"bad\"}"));
        assertThrows(IllegalArgumentException.class, () -> App.parseUser("{}"));
    }

    @Test
    void mainPrintsRoundTrip() {
        PrintStream old = System.out;
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        System.setOut(new PrintStream(buf));
        try {
            App.main(new String[0]);
        } finally {
            System.setOut(old);
        }
        String out = buf.toString();
        assertTrue(out.contains("Serialized to JSON:"));
        assertTrue(out.contains("Deserialized back:"));
        assertTrue(out.contains("User{name='Alice', age=30, email='alice@example.com'}"));
    }
}
