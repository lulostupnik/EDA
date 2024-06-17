package eda;

public class MorrisPrat {
    private static int[] nextComputation(char[] query) {
        int[] next = new int[query.length];

        int border=0;  // Length of the current border

        int rec=1;
        while(rec < query.length){
            if(query[rec]!=query[border]){
                if(border!=0)
                    border=next[border-1];
                else
                    next[rec++]=0;
            }
            else{
                border++;
                next[rec]=border;
                rec++;
            }
        }
        return next;
    }


    /*private static int[] nextComputation2(char[] query) {
        int[] next = new int[query.length];
        next[0] = 0;     // Always. There's no proper border.
        int border = 0;  // Length of the current border
        for (int rec = 1; rec < query.length; rec++) {
            while ((border > 0) && (query[border] != query[rec]))
                border = next[border - 1];     // Improving previous computation
            if (query[border] == query[rec])
                border++;
            next[rec] = border;
        }
        return next;
    }*/


    public static int indexOf( char[] query, char[] target) {
        int[] nextComp = nextComputation(query);
        int ptarget =0, pquery = 0;

        while (ptarget < target.length) {
            if (query[pquery] == target[ptarget]) {
                pquery++;
                ptarget++;
                if (pquery == query.length) {
                    // Match found
                    return ptarget - pquery;
                }
            } else {
                if (pquery != 0) {
                    pquery = nextComp[pquery - 1];
                } else {
                    ptarget++;
                }
            }
        }


        return -1;
    }



}
