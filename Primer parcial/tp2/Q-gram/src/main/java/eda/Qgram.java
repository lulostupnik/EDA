package eda;

import java.util.ArrayList;
import java.util.HashMap;



//Obs : haciendo una clase para los mapas me ahorraria hacer el map.keySet y podria imprimir todo en orden como se debe, pero paja.
public class Qgram {
    private int n;
    public Qgram(int n){
        if(n <= 0){
            throw new RuntimeException();
        }
        this.n = n;
    }
    private int mapSize(HashMap<String, Integer> map){
        int ans = 0;
        for(String s_ : map.keySet()){
            ans += map.get(s_);
        }
        return ans;
    }
    public double similarity(String s1, String s2){
        HashMap<String, Integer> maps1 = getToken(s1);
        HashMap<String, Integer> maps2 = getToken(s2);
        int size_s1 = mapSize(maps1), size_s2 = mapSize(maps2);
        int cant_diff = size_s1+size_s2;

        for(String s_ : maps2.keySet()){
            int other_map_cant = maps1.getOrDefault(s_, 0);
            int this_map_cant = maps2.get(s_);
            cant_diff -= Math.min(other_map_cant, this_map_cant)*2;
        }

        return 1 - (double)cant_diff/(size_s1+size_s2);

    }
    public void printTokens(String s){
        //si quisiera ordenado no deberia pedir el keySet
        HashMap<String, Integer> map = getToken(s);


        for(String s_ : map.keySet()){
            System.out.println(s_ + " %d".formatted(map.get(s_)));
        }
    }
    private HashMap<String, Integer> getToken(String s){
        HashMap<String, Integer> map = new HashMap<>();
        for(int i=0 ; i<n-1 && i<s.length() ; i++){
            String s_ = getSubStringLeft(s, i);
            map.put(s_, map.getOrDefault(s_, 0) + 1);
        }

        for(int i = n-2; i < s.length() ; i++){
            String s_ = getSubString(s, i);
            map.put(s_, map.getOrDefault(s_, 0) + 1);
        }
        return map;
    }
    private String getSubString(String s, int i){
        StringBuilder sb = new StringBuilder();
        for(int j=0; j<n ; j++){
            if(i+j < s.length()){
                sb.append(s.charAt(i+j));
            }else{
                sb.append('#');
            }
        }
        return sb.toString();
    }
    private String getSubStringLeft(String s, int i){
        StringBuilder sb = new StringBuilder();
        for(int j=0; j< n-i-1 ; j++){
            sb.append('#');
        }
        for(int x = 0; x< i+1; x++){
            sb.append(s.charAt(x));
        }
        return sb.toString();
    }
}
