package eda;

public class FromScratch {
    public static void main(String[] args) {
        char[] target= "abracadabra".toCharArray();
        char[] query= "ra".toCharArray();
        System.out.println( FromScratch.indexOf( query, target) );  //2

        target= "abracadabra".toCharArray();
        query= "abra".toCharArray();
        System.out.println(FromScratch.indexOf( query, target) );  //0

        target= "abracadabra".toCharArray();
        query= "aba".toCharArray();
        System.out.println(FromScratch.indexOf( query, target) );  //-1

        target= "ab".toCharArray();
        query= "aba".toCharArray();
        System.out.println(FromScratch.indexOf( query, target) );  //-1

        target="xa".toCharArray();
        query= "aba".toCharArray();
        System.out.println(FromScratch.indexOf( query, target) );  //-1

        target= "abracadabras".toCharArray();
        query= "abras".toCharArray();
        System.out.println(FromScratch.indexOf( query, target) );  //7


    }
    public static int indexOf(char[] query, char[] target) {
        for (int i = 0; i <= target.length - query.length; i++) {
            boolean flag = true;
            for (int j = 0; j < query.length; j++) {
                if (target[i + j] != query[j]) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                return i;
            }
        }
        return -1;
    }

}
