import ar.edu.itba.eda.InfijaToPostfija.EvaluateMatrizMapa;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class EvaluateTest {
    EvaluateMatrizMapa evaluate;


    @Test
    void infijaToPostfija() {
        evaluate = new EvaluateMatrizMapa();
        String input = "( 1 * ( 2 - 3 ) ) + ( 4 + 5 )\n"; // Simula que el usuario ingresa 5 y luego 3

        // Convertir la cadena en un flujo de entrada
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        String ans = evaluate.infijaToPostfija();
        assertEquals("1 2 3 - * 4 5 + + ",ans);
    }

    @Test
    void evaluate(){
        evaluate = new EvaluateMatrizMapa();
        String input = "( 1 * ( 2 - 3 ) ) + ( 4 + 5 )\n"; // Simula que el usuario ingresa 5 y luego 3

        // Convertir la cadena en un flujo de entrada
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        Double ans = evaluate.evaluate();
        assertEquals(8.0,ans);
    }

    @Test
    void evaluateVars(){
        HashMap<String,Double> vars = new HashMap<>();
        vars.put("a", 1.0);
        vars.put("b", 2.0);
        evaluate = new EvaluateMatrizMapa(vars);
        String input = "( a * ( b - 3 ) ) + ( 4 + 5 )\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        Double ans = evaluate.evaluate();
        assertEquals(8.0,ans);
    }

}