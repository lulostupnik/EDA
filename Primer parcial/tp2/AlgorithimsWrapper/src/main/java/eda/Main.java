package eda;

import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.language.Metaphone;
import org.apache.commons.codec.language.Soundex;

public class Main {
    public static void main(String[] args) throws EncoderException {
        System.out.println("Hello world!");
        Soundex s = new Soundex();
        System.out.println(s.difference("maven", "meibem"));


        Metaphone m = new Metaphone();
        System.out.println(m.encode("meaven"));
    }
}