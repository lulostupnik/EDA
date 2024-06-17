import ar.edu.itba.eda.Soundex.Soundex;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SoundexTest {
    @Test
    void soundexRepresentation_threshold() {
        assertEquals("T624",Soundex.representation("threshold"));
    }

    @Test
    void soundexRepresentation_hold_Test() {
        assertEquals("H430",Soundex.representation("hold"));
    }

    @Test
    void soundexRepresentation_phone_Test() {
        assertEquals("P500",Soundex.representation("phone"));
    }

    @Test
    void soundexRepresentation_foun_Test() {
        assertEquals("F500",Soundex.representation("foun"));
    }

    @Test
    void soundexSimilarity_threshold_hold_Test() {
        assertEquals(0.0, Soundex.similarity("threshold", "hold"));
    }

    @Test
    void soundexSimilarity_phone_foun_Test() {
        assertEquals(0.75, Soundex.similarity("phone", "foun"));
    }
}