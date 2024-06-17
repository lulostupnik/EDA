package ar.edu.itba.eda;

import info.debatty.java.stringsimilarity.QGram;
import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.language.Soundex;
import org.apache.commons.text.similarity.LevenshteinDetailedDistance;
import org.apache.commons.text.similarity.LevenshteinDistance;
import org.apache.commons.text.similarity.LevenshteinResults;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class StringSimilarityConApache {
    public static void main(String[] args) throws EncoderException {
        String s = "maven";
        String s2 = "meiben";
        Soundex soun = new Soundex();
        System.out.println(soun.encode(s));
        System.out.println(soun.encode(s2));
        System.out.println(soun.difference(s, s2));
        LevenshteinDistance l = new LevenshteinDistance();
        LevenshteinDetailedDistance l1 = new LevenshteinDetailedDistance();
        LevenshteinResults ret = l1.apply("exkusa", "ex-amigo");
        System.out.println(ret);
        System.out.println(1-(double)l.apply("exkusa", "ex-amigo") / 8.0);
        QGram qg = new QGram( 2 );
        System.out.println(qg.distance( "Hello", "Alo" ));
        System.out.println(qg.getProfile( "Hello" ));
    }
}