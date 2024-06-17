package ar.edu.itba.eda.P1_2022_1C;

public class ProximityIndex {
    private String[] elements;
    private int size = 0;

    public void initialize(String[] elements) {
        if (elements == null) {
            throw new IllegalArgumentException("elements no puede ser null");
        }

        for(int rec= 0; rec < elements.length-1; rec++) {
            if (elements[rec].compareTo(elements[rec+1]) >= 0)
                throw new IllegalArgumentException("hay repetidos o no está ordenado");
        }

        this.elements = elements;
        this.size = elements.length;
    }
    private int busquedaBinaria(int izq,int der,String element){
        if(izq>der) return -1;
        int mid=(der+izq)/2;
        String midElement = elements[mid];
        if(element.equals(midElement)) return mid;
        if(element.compareTo(midElement)<0){
            return busquedaBinaria(izq,mid-1,element);
        }
        return busquedaBinaria(mid+1,der,element);
    }

    //Ana ,Carlos, Juan, Yolanda
    // 0      1      2      3
    public String search(String element, int distance) {
        // distance % elments.length
        int posElement = busquedaBinaria(0, elements.length-1,element);
        if(posElement == -1) return null;
        int i = (distance + posElement) % elements.length;
        if(i<0) i+=elements.length;
        return elements[i];
    }


    public static void main(String[] args) {
        ProximityIndex proximityIndex = new ProximityIndex();
        String[] strings = {"Ana","Carlos","Juan","Yolanda"};
        proximityIndex.initialize(strings);
        System.out.println(proximityIndex.search("Carlos",0)); // Carlos
        System.out.println(proximityIndex.search("Carlos",3)); // Ana
        System.out.println(proximityIndex.search("Ana",14)); // Juan
        System.out.println(proximityIndex.search("Ana",-2)); // Juan
        System.out.println(proximityIndex.search("XXXX",0)); // null

    }


}