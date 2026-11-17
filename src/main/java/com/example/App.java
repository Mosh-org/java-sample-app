package com.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

public class App {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    /**
     * Parses JSON into a validated {@link User}.
     *
     * @throws IllegalArgumentException if the JSON is malformed, empty/null, or the user is invalid
     */
    public static User parseUser(String json) {
        User user;
        try {
            user = GSON.fromJson(json, User.class);
        } catch (JsonSyntaxException e) {
            throw new IllegalArgumentException("Malformed JSON: " + e.getMessage(), e);
        }
        if (user == null) {
            throw new IllegalArgumentException("JSON did not contain a user");
        }
        // Gson bypasses the constructor, so validate explicitly.
        user.validate();
        return user;
    }

    public static String toJson(User user) {
        return GSON.toJson(user);
    }

    public static void main(String[] args) {
        User user = new User("Alice", 30, "alice@example.com");
        String json = toJson(user);
        System.out.println("Serialized to JSON:");
        System.out.println(json);

        User parsed = parseUser(json);
        System.out.println("\nDeserialized back:");
        System.out.println(parsed);
    }
}
