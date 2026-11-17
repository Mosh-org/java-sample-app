package com.example;

import java.util.regex.Pattern;

public class User {
    public static final int MIN_AGE = 0;
    public static final int MAX_AGE = 150;

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*\\.[A-Za-z]{2,}$");

    private String name;
    private int age;
    private String email;

    public User(String name, int age, String email) {
        validate(name, age, email);
        this.name = name;
        this.age = age;
        this.email = email;
    }

    /**
     * Validates the given values; throws {@link IllegalArgumentException} when invalid.
     * Also used by {@link App} on objects built by Gson, which bypasses the constructor.
     */
    static void validate(String name, int age, String email) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("name must not be blank");
        }
        if (age < MIN_AGE || age > MAX_AGE) {
            throw new IllegalArgumentException("age must be between " + MIN_AGE + " and " + MAX_AGE);
        }
        if (email == null || !EMAIL_PATTERN.matcher(email).matches()) {
            throw new IllegalArgumentException("email format is invalid");
        }
    }

    void validate() {
        validate(name, age, email);
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "User{name='" + name + "', age=" + age + ", email='" + email + "'}";
    }
}
