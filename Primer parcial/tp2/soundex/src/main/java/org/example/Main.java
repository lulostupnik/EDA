package org.example;

public class Main {
    public static void main(String[] args) {
        System.out.println(Soundex.representation("hold"));
        System.out.println(Soundex.metric("threshold", "hold"));
        System.out.println(Soundex.metric("phone", "foune"));
    }
}
