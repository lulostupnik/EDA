import java.io.*;
import java.util.function.Function;

public class StringsMain {
    public static void main(String[] args) throws IOException {
        Function<String, Integer> prehash1 = s->(int) s.charAt(0);
        Function<String, Integer> prehash2 = new Function<String, Integer>() {
            @Override
            public Integer apply(String s) {
                int ans = 0;
                for(int i=0; i<s.length() ; i++){
                    ans += (int) s.charAt(i);
                }
                return ans;
            };
        };

        String fileName = "amazon-categories30.txt";
        InputStream is = ClosedHashing.class.getClassLoader().getResourceAsStream(fileName);
        Reader in = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(in);

        String line;
        while((line = br.readLine()) != null){
            String[] ans = line.split("#");
            System.out.println(ans[0]); // este es el key
        }
    }
}
