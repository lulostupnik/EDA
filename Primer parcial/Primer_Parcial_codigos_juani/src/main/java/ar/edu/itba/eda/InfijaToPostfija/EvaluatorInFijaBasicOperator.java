package ar.edu.itba.eda.InfijaToPostfija;


import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Stack;

import static java.lang.Math.pow;

public class EvaluatorInFijaBasicOperator {
    private static Map<String, Boolean> precendeceMap= new HashMap<String, Boolean>()
    {	{
        put("+_+", true);  	put("+_-", true); 	put("+_*", false); 	put("+_/", false); 	put("+_^", false);	put("+_(", false);	put("+_)", true);
        put("-_+", true);  	put("-_-", true); 	put("-_*", false); 	put("-_/", false); 	put("-_^", false);	put("-_(", false);	put("-_)", true);
        put("*_+", true);  	put("*_-", true); 	put("*_*", true); 	put("*_/", true); 	put("*_^", false);	put("*_(", false);	put("*_)", true);
        put("/_+", true);  	put("/_-", true); 	put("/_*", true); 	put("/_/", true); 	put("/_^", false);	put("/_(", false);	put("/_)", true);
        put("^_+", true);  	put("^_-", true);  	put("^_*", true); 	put("^_/", true); 	put("^_^", false);	put("^_(", false);	put("^_)", true);
        put("(_+", false); 	put("(_-", false); 	put("(_*", false); 	put("(_/", false); 	put("(_^", false);	put("(_(", false);	put("(_)", false);
    }  };

    private final static String extraSymbol= "_";
    private boolean getPrecedence(String tope, String current)
    {
        Boolean rta= precendeceMap.get(String.format("%s%s%s", tope, extraSymbol, current));
        if (rta == null)
            throw new RuntimeException(String.format("operator %s or %s not found", tope, current));

        return rta;
    }



    private Scanner scannerLine;
    private HashMap<String, Double> variables;
    private boolean hasVars = false;

    public EvaluatorInFijaBasicOperator()  {
        Scanner input = new Scanner(System.in).useDelimiter("\\n");
        System.out.print("Introduzca la expresión en notación infija: ");
        String line= input.nextLine();
        input.close();

        scannerLine = new Scanner(line).useDelimiter("\\s+");
    }

    public EvaluatorInFijaBasicOperator(HashMap<String, Double> variables)  {
        this.variables = variables;
        hasVars = true;
        Scanner input = new Scanner(System.in).useDelimiter("\\n");
        System.out.print("Introduzca la expresión en notación infija: ");
        String line= input.nextLine();
        input.close();

        scannerLine = new Scanner(line).useDelimiter("\\s+");
    }



    public Double evaluate()
    {
        String exp = infijaToPostfija();
        scannerLine = new Scanner(exp).useDelimiter("\\s+");

        Stack<Double> aux = new Stack<>();

        while (scannerLine.hasNext()) {
            String token = scannerLine.next();
            if (token.matches("[0-9]+(.[0-9]+)?"))
                aux.push(Double.parseDouble(token));
            else if(token.matches("\\+|\\*|/|-|\\^")){
                if(aux.size() < 2)
                    throw new IllegalArgumentException("Missing operand before " + token);
                Double op2 = aux.pop();
                Double op1 = aux.pop();
                switch (token){
                    case "+" : aux.push(op1 + op2);
                        break;
                    case "-" : aux.push(op1 - op2);
                        break;
                    case "*" : aux.push(op1 * op2);
                        break;
                    case "/" : aux.push(op1 / op2);
                        break;
                    case "^": aux.push(pow(op1, op2));
                        break;
                }
            }
            else
                throw new IllegalArgumentException(token + " is an invalid operand and/or operator");
        }
        if(aux.size() != 1){
            throw new IllegalArgumentException("Invalid number of operators");
        }
        return aux.pop();
    }

    private String infijaToPostfija()
    {
        StringBuilder postfija= new StringBuilder();
        Stack<String> aux= new Stack<>();

        while(scannerLine.hasNext()) {
            String current = scannerLine.next();
            if(hasVars && !current.matches("[0-9]+(.[0-9]+)?|\\+|\\*|/|-|\\^|\\(|\\)")) {
                if(!current.matches(variables.keySet().toString())) {
                    throw new IllegalArgumentException(current + " is not a variable");
                }
                postfija.append(variables.get(current)).append(" ");
            }
            if (current.matches("[0-9]+(.[0-9]+)?")) {
                postfija.append(current).append(" ");
            }
            else if(current.matches("\\+|\\*|/|-|\\^|\\(|\\)")) {
                while (!aux.isEmpty() && getPrecedence(aux.peek(), current)) {
                    postfija.append(aux.pop()).append(" ");
                }
                if (current.equals(")")) {
                    if (!aux.isEmpty() && aux.peek().equals("(")) {
                        aux.pop();
                    } else {
                        throw new IllegalArgumentException("( missing");
                    }
                }
                else {
                    aux.push(current);
                }
            }
        }
        while(!aux.isEmpty()){
            if(aux.peek().equals("(")) {
                throw new IllegalArgumentException(") missing");
            }
            else {
                postfija.append(aux.pop()).append(" ");
            }
        }
        System.out.println("Postfija = " + postfija);
        return postfija.toString().trim();
    }




    public static void main(String[] args) {
        HashMap<String,Double> vars = new HashMap<>();
        vars.put("a", 1.0);
        vars.put("b", 2.0);
        vars.put("c", 3.0);
        vars.put("d", 4.0);
        vars.put("e", 5.0);
        EvaluatorInFijaBasicOperator e = new EvaluatorInFijaBasicOperator(vars);
        System.out.println(e.evaluate());
    }
}