package org.example;

import java.util.Scanner;
import java.util.Stack;

public class StringMatch {
    public String evaluate(String expression){
        String ans;
        Scanner inputScanner = new Scanner(expression).useDelimiter("\\s+");
        Stack<String> stack = new Stack<>();

        while(inputScanner.hasNext()){
            String s = inputScanner.next();
            if(isAlphabet(s)){
                stack.push(s);
            } else if (isOperator(s)) {
                String operand2;
                String operand1;
                if(stack.empty()){
                    throw new RuntimeException("Operand missing");
                }
                operand2 = stack.pop();
                if(stack.empty()){
                    throw new RuntimeException("Operand missing");
                }
                operand1 = stack.pop();
                stack.push(eval(s, operand1,operand2));
            }else {
                throw new RuntimeException();
            }
        }

        ans = stack.pop();
        if (stack.empty())
            return ans;

        throw new RuntimeException("Operator missing");

    }

    private boolean isAlphabet(String s){
        s = s.toUpperCase();
        for( int i =0; i< s.length() ; i++){
            if(s.charAt(i) > 'Z' || s.charAt(i) < 'A'){
                return false;
            }
        }
        return true;
    }
    private boolean isOperator(String s)
    {
        return s.length() == 1 && (s.charAt(0) == '*' || s.charAt(0)=='/' || s.charAt(0) == '^' || s.charAt(0)=='-' || s.charAt(0)=='+');
    }

    private String eval(String op, String s1, String s2)
    {
        StringBuilder sb = new StringBuilder();
        switch (op)
        {

            case "+":
                sb.append(s1).append(s2);
                return sb.toString();
            case "-":
                sb.append(s1);
                return sb.toString().replaceFirst(s2,"");
            case "*":
                for(int i=0; i< s1.length() ; i++){
                    sb.append(s1.charAt(i));
                    if(s2.length() > i){
                        sb.append(s2.charAt(i));
                    }
                }
                for (int i=s1.length() ; i<s2.length() ; i++){
                    sb.append(s2.charAt(i));
                }
                return sb.toString();
            case "^":
                for(int i=0; i<=s2.length() ; i++){
                    sb.append(s2.substring(0,i));
                    sb.append(s1);
                }
                sb.setLength(sb.length() - s1.length());
                return sb.toString();
            case "/":
                sb.append(s1);
                String ans = sb.toString();

                for(int i=0; i<s2.length() ; i++){
                    ans = ans.replaceAll(String.format("%c", s2.charAt(i)),"");
                }
                return ans;

        };

        throw new RuntimeException("invalid operator" +  op);
    }

    public static void main(String[] args) {
        String exp = "AA BB CC DEF ^ * AE / + BC -";
        StringMatch sm = new StringMatch();
        System.out.println(sm.evaluate(exp));
    }
}
