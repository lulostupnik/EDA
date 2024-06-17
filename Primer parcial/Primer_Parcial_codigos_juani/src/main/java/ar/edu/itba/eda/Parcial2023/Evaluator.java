package ar.edu.itba.eda.Parcial2023;

import java.util.Objects;
import java.util.Scanner;
import java.util.Stack;

public class Evaluator {
    private Stack<String> stack = new Stack<>();

    public String evaluate(String exprIn){
        Scanner lineScanner = new Scanner(exprIn).useDelimiter("\\s+");

        while (lineScanner.hasNext()) {
            String token = lineScanner.next();
            //System.out.print(token);
            //System.out.print(" ");

            if (token.matches("[UIDS]")){
                if(stack.isEmpty())
                    throw new RuntimeException("Faltan operandos");
                String b = stack.pop();
                if(stack.isEmpty())
                    throw new RuntimeException("Faltan operandos");
                String a = stack.pop();
                switch (token) {
                    case "U" -> {
                        stack.push(union(a,b));
                    }
                    case "I" -> {

                        stack.push(inter(a,b));
                    }
                    case "D" -> {
                        stack.push(delete(a,b));
                    }
                    case "S" -> {
                        stack.push(diff(a,b));
                    }
                }
            }
            else if (token.matches("([0-9]+,)*[0-9]+")){
                stack.push(token);
            }
            else
                throw new RuntimeException("No es un símbolo válido");

        }
        if(stack.isEmpty())
            throw new RuntimeException("Expresión inválida");
        String  toReturn = stack.pop();
        if(stack.isEmpty())
            return toReturn;
        else
            throw new RuntimeException("Faltan operadores");
    }

    private String union(String a , String b){
        StringBuilder sb = new StringBuilder();
        sb.append(a).append(",").append(b);
        Conjunto toStack = new Conjunto(sb.toString());
        return toStack.toString();
    }

    private String inter(String a,String b){
        Conjunto ca = new Conjunto(a);
        Conjunto cb = new Conjunto(b);
        StringBuilder sb = new StringBuilder();
        for(int i = 0;i<ca.qtyItems;i++){
            for(int j = 0;j<cb.qtyItems;j++){
                if(ca.items[i].equals(cb.items[j])){
                    sb.append(ca.items[i]).append(",");
                    break;
                }
            }
        }
        if(!sb.isEmpty()) sb.deleteCharAt(sb.length()-1);
        return sb.toString();
    }

    private String delete(String a,String b){
        Conjunto ca = new Conjunto(a);
        Conjunto cb = new Conjunto(b);
        boolean flag = true;
        StringBuilder sb = new StringBuilder();
        for(int i = 0;i<ca.qtyItems;i++){
            flag = true;
            for(int j = 0;j<cb.qtyItems;j++){
                if(ca.items[i].equals(cb.items[j])){
                    flag = false;
                    break;
                }
            }
            if(flag) sb.append(ca.items[i]).append(",");
        }
        if(!sb.isEmpty()) sb.deleteCharAt(sb.length()-1);
        return sb.toString();
    }

    private String diff(String a, String b){
        return union(delete(a,b),delete(b,a));
    }


    private final class Conjunto{

        private Integer items[];
        private int qtyItems = 0;
        private final int maxItems = 20;

        Conjunto( String csvItems ){
            items = new Integer [maxItems];

            Scanner csvScanner = new Scanner( csvItems ).useDelimiter(",");
            while( csvScanner.hasNext() ) {
                String token = csvScanner.next();
                addItem( Integer.valueOf( token ) );
            }
        }

        public String toString(){
            if (qtyItems == 0)
                return "";

            StringBuilder sb = new StringBuilder();
            sb.append(items[0]);
            for (int i = 1; i < qtyItems; ++i)
                sb.append(",").append(items[i]);

            return sb.toString();
        }

        private void addItem( int item ){
            for (int i = 0; i < qtyItems; ++i ){
                if ( items[i] == item )
                    return;
            }

            items[qtyItems] = item;
            ++qtyItems;
        }
    }


    public static void main(String[] args) {
        Evaluator e = new Evaluator();
        System.out.println(e.evaluate("1,3,4,5,5 7,8,9,5 U"));
        System.out.println(e.evaluate("1,3,4,5,5 7,8,9,5 I"));
        System.out.println(e.evaluate("1,3,4,5,5 7,8,9,5 D"));
        System.out.println(e.evaluate("1,3,4,5,5 7,8,9,5 S"));
        System.out.println(e.evaluate("11,12,13,4 5,11,13 U 5,15,12,14,8 6,14,12,15 I S"));
        System.out.println(e.evaluate("1,2,3 6,7,8,1,2 S 1,2,3,4 5,6,7 U I"));
        System.out.println(e.evaluate("11,2,13,4 5,11,13 D 1,3,4 6,1,2 S I"));
    }

}
