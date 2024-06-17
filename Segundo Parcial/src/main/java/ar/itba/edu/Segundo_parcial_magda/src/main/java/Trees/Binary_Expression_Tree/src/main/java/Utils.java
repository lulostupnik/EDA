package Trees.Binary_Expression_Tree.src.main.java;
public class Utils {

    static public boolean isConstant(String data) {
        try {
            Double.valueOf(data);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    static public double getDoubleConstant(String data) {
        return Double.parseDouble(data);
    }


    static public boolean isOperator(String data) {
        return data.matches("[+-^*/]");
    }

    public static boolean isOpenParenthesis(String data) {
        return data.contentEquals("(");
    }

    public static boolean isCloseParenthesis(String data) {
        return data.contentEquals(")");
    }
}
