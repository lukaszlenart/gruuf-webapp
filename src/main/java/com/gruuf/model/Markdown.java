package com.gruuf.model;

public class Markdown {

    private String content;

    public static Markdown of(String string) {
        return new Markdown(string);
    }

    Markdown() {
    }

    Markdown(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return "Markdown{" +
                "content='" + content + '\'' +
                '}';
    }
}
