package com.example.entity;

/**
 * by y.
 * <p>
 * Description:
 */

public class ExampleNetEntity {
    public String title;
    public String titleImage;
    public int slug;
    public Author author;

    public static class Author {
        public String profileUrl;
        public String bio;
        public String name;
    }
}
