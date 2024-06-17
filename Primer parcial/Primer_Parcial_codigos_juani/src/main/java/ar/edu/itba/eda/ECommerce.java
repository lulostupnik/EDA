package ar.edu.itba.eda;

import info.debatty.java.stringsimilarity.Levenshtein;
import info.debatty.java.stringsimilarity.QGram;
import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.language.Metaphone;
import org.apache.commons.codec.language.Soundex;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

public class ECommerce {
    private final String[] products;

    public ECommerce(String[] prods) {
        this.products = prods;
    }

    private String normalizeString(String str) {
        String s = str;
        s = s.toLowerCase();
        s = StringUtils.normalizeSpace(s);
        s = StringUtils.stripAccents(s);
        s = s.replace('ñ', 'n');
        return s;
    }

    public ArrayList<Elem> lookProduct(String s) throws EncoderException {
        s = normalizeString(s);
        ArrayList<Elem> list  = new ArrayList<>();

        for(String product : products) {
            Levenshtein l = new Levenshtein();
            double levenDist = 1.0 - l.distance(s, product) / Math.max(s.length(), product.length());

            Soundex soundex = new Soundex();
            double soundexDist = (double)soundex.difference(s, product) / 4.0;

            Metaphone metaphone = new Metaphone();
            metaphone.setMaxCodeLen(20);
            String metaS = metaphone.encode(s);
            String metaProd = metaphone.encode(product);
            double metaphoneDist = 1.0 - l.distance(metaS, metaProd) / Math.max(metaProd.length(), metaS.length());

            String QGrams = tokenizeQGram(s);
            String QgramProd = tokenizeQGram(product);
            QGram qGram = new QGram(3);
            double qGramDist = 1 - qGram.distance(QgramProd, QGrams) / (QGrams.length() - 2 + QgramProd.length() - 2);

            double maxi = Math.max(levenDist, Math.max(soundexDist, Math.max(metaphoneDist, qGramDist)));
            if(maxi == levenDist) {
                list.add(new Elem(levenDist, product, "Levenshtein"));
            } else if(maxi == soundexDist) {
                list.add(new Elem(soundexDist, product, "Soundex"));
            } else if(maxi == metaphoneDist) {
                list.add(new Elem(metaphoneDist, product, "Metaphone"));
            } else {
                list.add(new Elem(qGramDist, product, "QGram"));
            }
        }

        list.sort(new Comparator<Elem>() {
            @Override
            public int compare(Elem o1, Elem o2) {
                return Double.compare(o2.dist, o1.dist);
            }
        });

        return list;
    }

    private String tokenizeQGram(String s) {
        StringBuilder ret = new StringBuilder();
        for(int i = 0; i < 3 - 1; i++) {
            ret.append('#');
        }
        ret.append(s);
        for(int i = 0; i < 3 - 1; i++) {
            ret.append('#');
        }
        return ret.substring(0);
    }

    public static class Elem{
        public double dist;
        public String prod;
        public String algo;

        public Elem(double dist, String prod, String algo) {
            this.dist = dist;
            this.prod = prod;
            this.algo = algo;
        }

        public String toString() {
            return "Distance: %.3f Product: %s Algorithm: %s".formatted(dist, prod, algo);
        }
    }

}
