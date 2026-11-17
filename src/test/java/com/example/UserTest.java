package com.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class UserTest {

    @Test
    void validUser() {
        User u = new User("Alice", 30, "alice@example.com");
        assertEquals("Alice", u.getName());
        assertEquals(30, u.getAge());
        assertEquals("alice@example.com", u.getEmail());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "   ", "\t\n"})
    void blankNameRejected(String name) {
        assertThrows(IllegalArgumentException.class, () -> new User(name, 30, "a@b.com"));
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 149, 150})
    void ageBoundariesAccepted(int age) {
        assertEquals(age, new User("A", age, "a@b.com").getAge());
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 151, Integer.MIN_VALUE, Integer.MAX_VALUE})
    void ageOutOfRangeRejected(int age) {
        assertThrows(IllegalArgumentException.class, () -> new User("A", age, "a@b.com"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"a@b.co", "first.last+tag@sub.example.org", "x_y-z@ex-ample.com"})
    void validEmailsAccepted(String email) {
        assertEquals(email, new User("A", 1, email).getEmail());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"plain", "@example.com", "a@", "a@b", "a@b.", "a b@c.com", "a@@b.com", "a@b.c"})
    void invalidEmailsRejected(String email) {
        assertThrows(IllegalArgumentException.class, () -> new User("A", 1, email));
    }

    @Test
    void toStringFormat() {
        assertEquals("User{name='Alice', age=30, email='alice@example.com'}",
                new User("Alice", 30, "alice@example.com").toString());
    }
}
