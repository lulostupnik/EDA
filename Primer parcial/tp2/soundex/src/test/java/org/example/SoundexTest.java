package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SoundexTest {
    @Test
    void soundexRepresentation_threshold_Test() {
        assertEquals("T624",Soundex.representation("threshold"));}
}