package ar.edu.itba.eda.InfijaToPostfija;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Stack;

public class EvaluateMatrizMapa {

    private Stack<Double> stack = new Stack<>();

    private Map<String,Double> mapVars;

    // opcion 1

    private static Map<String, Integer> mapping = new HashMap<String, Integer>()
    {   { put("+", 0); put("-", 1); put("*", 2); put("/", 3);  put("^",4); put("(",5); put(")", 6); }
    };

    private static boolean[][] precedenceMatriz=
            {       { true,  true,  false, false, false, false, true },
                    { true,  true,  false, false, false, false, true },
                    { true,  true,  true,  true,  false, false, true },
                    { true,  true,  true,  true,  false, false, true },
                    { true,  true,  true,  true,  false, false, true },
                    { false, false, false, false, false, false, false }
            };

    public EvaluateMatrizMapa(Map<String, Double> mapVars) {
        this.mapVars = mapVars;
    }

    public EvaluateMatrizMapa() {
        this.mapVars = null;
    }


    /*
No se usa
    private boolean getPrecedence(String tope, String current) {
        Integer topeIndex;
        Integer currentIndex;

        if ((topeIndex= mapping.get(tope))== null)
            throw new RuntimeException(String.format("tope operator %s not found", tope));

        if ((currentIndex= mapping.get(current)) == null)
            throw new RuntimeException(String.format("current operator %s not found", current));

        return precedenceMatriz[topeIndex][currentIndex];
    }

 */

    public Double evaluate() {
        System.out.print("Introduzca la expresión en notación infija: ");
        Scanner lineScanner = new Scanner(infijaToPostfija()).useDelimiter("\\s+");

        while (lineScanner.hasNext()) {
            String token = lineScanner.next();
            System.out.print(token);
            System.out.print(" ");

            if (token.matches("[+/*\\-^]")){
                if(stack.isEmpty())
                    throw new RuntimeException("Faltan operandos");
                Double b = stack.pop();
                if(stack.isEmpty())
                    throw new RuntimeException("Faltan operandos");
                Double a = stack.pop();
                switch (token) {
                    case "+" -> stack.push(a + b);
                    case "-" -> stack.push(a - b);
                    case "/" -> stack.push(a / b);
                    case "*" -> stack.push(a * b);
                    case "^" -> stack.push(Math.pow(a,b));
                }
            }
            else if (token.matches("-?[0-9]+(\\.[0-9]*)?")){
                stack.push(Double.parseDouble(token));
            }
            else
                throw new RuntimeException("No es un símbolo válido");

        }
        if(stack.isEmpty())
            throw new RuntimeException("Expresión inválida");
        Double toReturn = stack.pop();
        if(stack.isEmpty())
            return toReturn;
        else
            throw new RuntimeException("Faltan operadores");
    }



    public String infijaToPostfija() {
        StringBuilder s = new StringBuilder();
        Stack<String> theStack = new Stack<>();

        Scanner inputScanner = new Scanner(System.in).useDelimiter("\\n");
        String line;
        if(inputScanner.hasNextLine())
            line = inputScanner.nextLine();
        else
            throw new IllegalArgumentException();
        if (mapVars != null)
            for (String var:mapVars.keySet())
                line = line.replaceAll(var,mapVars.get(var).toString());

        Scanner lineScanner = new Scanner(line).useDelimiter("\\s+");
        while (lineScanner.hasNext()) {
            String token = lineScanner.next();
            if (token.matches("[+/*\\-^(]")) {
                while (!theStack.isEmpty() && precedenceMatriz[mapping.get(theStack.peek())][mapping.get(token)])
                    s.append(theStack.pop()).append(" ");
                theStack.push(token);
            }
            else if (token.matches("\\)")) {
                while (!theStack.isEmpty() && !theStack.peek().equals("("))
                    s.append(theStack.pop()).append(" ");
                if(!theStack.isEmpty())
                    theStack.pop();
                else
                    throw new RuntimeException("Falta (");
            }
            else if (token.matches("-?[0-9]+(\\.[0-9]*)?"))
                s.append(token).append(" ");
            else
                throw new RuntimeException("No es un símbolo válido");
        }
        while(!theStack.isEmpty())
            s.append(theStack.pop()).append(" ");

        return s.toString();
    }
    public static void main(String[] args) {
        EvaluateMatrizMapa e = new EvaluateMatrizMapa();
        System.out.println(e.infijaToPostfija());
    }

    /*


    public String infijaToPrefija(){
        StringBuilder s = new StringBuilder();
        Stack<String> theStack = new Stack<>();

        Scanner inputScanner = new Scanner(System.in).useDelimiter("\\n");
        StringBuilder sb = new StringBuilder();
        if(inputScanner.hasNextLine())
            sb.append(inputScanner.nextLine());
        else
            throw new IllegalArgumentException();
        sb.reverse();
        String line = sb.toString();
        if (mapVars != null)
            for (String var:mapVars.keySet())
                line = line.replaceAll(var,mapVars.get(var).toString());

        Scanner lineScanner = new Scanner(line).useDelimiter("\\s+");
        while (lineScanner.hasNext()) {
            String token = lineScanner.next();
            if (token.matches("[+/*\\-^)]")) {  // Tengo que cambiar la matriz de Precedencia
                while (!theStack.isEmpty() && precedenceMatriz[mapping.get(theStack.peek())][mapping.get(token)])
                    s.append(theStack.pop()).append(" ");
                theStack.push(token);
            }
            else if (token.matches("\\(")) {
                while (!theStack.isEmpty() && !theStack.peek().equals(")"))
                    s.append(theStack.pop()).append(" ");
                if(!theStack.isEmpty())
                    theStack.pop();
                else
                    throw new RuntimeException("Falta )");
            }
            else if (token.matches("-?[0-9]+(\\.[0-9]*)?"))
                s.append(token).append(" ");
            else
                throw new RuntimeException("No es un símbolo válido");
        }
        while(!theStack.isEmpty())
            s.append(theStack.pop()).append(" ");

        return s.reverse().toString();
    }
*/




}
