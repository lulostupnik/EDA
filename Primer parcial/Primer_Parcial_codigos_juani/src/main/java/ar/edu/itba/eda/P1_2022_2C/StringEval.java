package ar.edu.itba.eda.P1_2022_2C;

import java.util.Scanner;
import java.util.Stack;

public class StringEval {

    private Stack<String> stack = new Stack<>();

    //Era Double
    public String evaluate(String expression){
        Scanner lineScanner = new Scanner(expression).useDelimiter("\\s+");

        while (lineScanner.hasNext()) {
            String token = lineScanner.next();
            //System.out.print(token);
            //System.out.print(" ");

            if (token.matches("[+/*\\-^]")){
                if(stack.isEmpty())
                    throw new RuntimeException("Faltan operandos");
                String b = stack.pop();
                if(stack.isEmpty())
                    throw new RuntimeException("Faltan operandos");
                String a = stack.pop();
                switch (token) {
                    case "+" -> stack.push(a.concat(b));
                    case "-" -> stack.push(a.replaceFirst(b,""));
                    case "/" -> {
                        for(int i=0;i<b.length();i++){
                            a = a.replaceAll(b.charAt(i)+"","");
                        }
                        stack.push(a);
                    }
                    case "*" -> {
                        int i=0,j=0;
                        StringBuilder s = new StringBuilder();
                        while(i<a.length() || j<b.length()){
                            if(i<a.length()){
                                s.append(a.charAt(i++));
                            }
                            if(j<b.length()){
                                s.append(b.charAt(j++));
                            }
                        }
                        stack.push(s.toString());
                    }
                    case "^" -> {
                        int i=0;
                        StringBuilder s = new StringBuilder();
                        StringBuilder aux = new StringBuilder();
                        // EE ABCD ^
                        while(i<b.length() ){
                            s.append(a);
                            aux.append(b.charAt(i++));
                            s.append(aux);
                        }

                        stack.push(s.toString());
                }
                }
            }
            else if (token.matches("[a-zA-Z]+")){
                stack.push(token);
            }
            else
                throw new RuntimeException("No es un símbolo válido");

        }
        if(stack.isEmpty())
            throw new RuntimeException("Expresión inválida");
        String toReturn = stack.pop();
        if(stack.isEmpty())
            return toReturn;
        else
            throw new RuntimeException("Faltan operadores");
    }


    /*
    Postfija:
    AA BB CC DEF ^ * AE / + BC -
    Resultado: AABCDCCDCCDF
    Postfija:
    HOLA QUE + TAL COMO ^ ESTAS / BIEN * + BIEN -
    Resultado: HOLAQUELBCILECNOLCOMLCOMO
     */
    public static void main(String[] args) {
        StringEval stringEval = new StringEval();
        System.out.println(stringEval.evaluate("AA BB CC DEF ^ * AE / + BC -"));
        System.out.println(stringEval.evaluate("HOLA QUE + TAL COMO ^ ESTAS / BIEN * + BIEN -"));
    }


}
