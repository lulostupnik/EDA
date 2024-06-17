package eda;

public class BorderStrings {
    private String str;
    public BorderStrings(String s){
        str=s;
    }


    static public String[] prefix(String s){
        String[] ans = new String[s.length()+1];
        for(int i=0; i<=s.length() ; i++){
            ans[i] = s.substring(0,i);
        }
        ans[0] = "%EMPTY%";
        return ans;
    }

    static public String[] suffix(String s){
        String[] ans = new String[s.length()+1];
        for(int i=0; i<s.length() ; i++){
            ans[s.length()-i] = s.substring(i);
        }

        ans[0] = "%EMPTY%";
        return ans;
    }

    @Override
    public String toString(){
        StringBuilder ans = new StringBuilder("String = %s\nLos prefijos son:\n".formatted(str));
        for(String s : prefix(str)){
            ans.append(s);
            ans.append(", ");
        }
        ans.setLength(ans.length()-2);
        ans.append("\nLos sufijos son: \n");

        for(String s : suffix(str)){
            ans.append(s);
            ans.append(", ");
        }
        ans.setLength(ans.length()-2);
        return ans.toString();
    }

    public static void main(String[] args) {
        String s = "BCBC";
        System.out.println(new BorderStrings(s));
    }

}
