package ar.edu.itba.eda.QGram;

import java.util.HashMap;
import java.util.Map;

public class QGram {
    private final int N;

    public QGram(int N) {
        this.N = N;
    }

    public void printTokens(String s) {
        HashMap<String, Integer> count = printTokensMap(s);

        for(Map.Entry<String, Integer> r : count.entrySet()) {
            System.out.println("%s %d".formatted(r.getKey(), r.getValue()));
        }
    }

    public HashMap<String, Integer> printTokensMap(String s) {
        StringBuilder ret = new StringBuilder();
        HashMap<String, Integer> count = new HashMap<>();
        for(int i = 0; i < N - 1; i++) {
            ret.append('#');
        }
        ret.append(s);
        for(int i = 0; i < N - 1; i++) {
            ret.append('#');
        }

        for(int i = 0; i < ret.length() - N + 1; i++) {
            String cur = ret.substring(i, i + N);
            count.put(cur, (count.containsKey(cur) ? count.get(cur) + 1 : 1));
        }

        return count;
    }

    public double similarity(String s1, String s2) {
        HashMap<String, Integer> m1 = printTokensMap(s1);
        HashMap<String, Integer> m2 = printTokensMap(s2);

        double notCommon = 0.0;
        double quan = 0.0;

        for(Map.Entry<String, Integer> e : m1.entrySet()) {
            quan += e.getValue();
            notCommon += (Math.max(0, e.getValue() - m2.getOrDefault(e.getKey(), 0)));
        }
        for(Map.Entry<String, Integer> e : m2.entrySet()) {
            quan += e.getValue();
            notCommon += (Math.max(0, e.getValue() - m1.getOrDefault(e.getKey(), 0)));
        }
        return (quan - notCommon) / quan;
    }
}
