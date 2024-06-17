import ar.edu.itba.eda.Levenshtein.LevenshteinMatriz;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LevenshteinMatrizTest {
    @Test
    void levenshteinDistance_love_movie_Test() {
        assertEquals(2, LevenshteinMatriz.distance("LOVE", "MOVIE"));
    }

    @Test
    void levenshteinDistance_big_data_bigdata_Test() {
        assertEquals(1, LevenshteinMatriz.distance("big data", "bigdata"));
    }

    @Test
    void levenshteinDistance_Test1() {
        assertEquals(8, LevenshteinMatriz.distance("RFMQRJKJKIA", "NEABJPJOI"));
    }

    @Test
    void levenshteinDistance_Test2() {
        assertEquals(14, LevenshteinMatriz.distance("HSMOWJXKGRWSMD", "JMRTLLNPXKKXZC"));
    }

    @Test
    void levenshteinNormalizedSimilarity_big_data_bigdaa_Test() {
        assertEquals(0.75, LevenshteinMatriz.normalizedSimilarity("big data", "bigdaa"));
    }
}