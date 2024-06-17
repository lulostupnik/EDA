package eda.core;



import org.apache.lucene.search.IndexSearcher;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.similarities.ClassicSimilarity;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;


import java.io.IOException;
import java.nio.file.Paths;



/*


Query de un termino: (Para los queryparser creo)
Dada una colección de N documentos D= {DOC1, DOC2, … DOCn}  y una query=term, para aquellos documentos que matchean la consulta:
Score(DOCi, query) =
FormulaLocal(DOCi,term) * FormulaGlobal(D, term)

FormulaGlobal: quiere calcular que tan relevante es esa query en el conjunto de documentos.

FormulaLocal(DOCi,query) = √(( #𝒇𝒓𝒆𝒒(𝒕𝒆𝒓𝒎 𝒊𝒏 𝑫𝑶𝑪𝒊))/(#𝒕𝒆𝒓𝒎 𝒆𝒙𝒊𝒔𝒕𝒆𝒏𝒕𝒆𝒔 𝒆𝒏 𝑫𝑶𝑪𝒊))

FormulaGlobal(DOC,query) = 1 + log_𝒆[(𝟏+ #𝒅𝒐𝒄𝒔 𝒆𝒏 𝒍𝒂 𝒄𝒐𝒍𝒆𝒄𝒄𝒊𝒐𝒏)/(𝟏+ #𝒅𝒐𝒄𝒔 𝒒𝒖𝒆 𝒄𝒐𝒏𝒕𝒊𝒆𝒏𝒆𝒏 𝒕𝒆𝒓𝒎)]

Query Multi-término:
Score(DOCi, query) = ∑_(𝑡𝑒𝑟𝑚 𝑖𝑛 𝑞𝑢𝑒𝑟𝑦 𝑦 𝑛𝑜 𝑡𝑖𝑒𝑛𝑒 𝑁𝑂𝑇) [FormulaLocal(DOCi,term) ∗ FormulaGlobal(D, term)]



 */


public class TheSearcherQueryParser {

    private static IndexReader getIndexReader() throws IOException {

        // target index directory
        Directory indexDir = FSDirectory.open( Paths.get(Utils.getPrefixDir() + "/index/"));

        return DirectoryReader.open( indexDir );

    }


    public static void main( String[] args ) {

        try {
            IndexReader index = getIndexReader();
            IndexSearcher searcher= new IndexSearcher(index);
            searcher.setSimilarity(new ClassicSimilarity());

            String queryStr;
            //queryStr= "content:bla";
            //queryStr= "content:Game";
            //queryStr= "content:[gaming TO gum]";
            //queryStr = "content:\"store game\"";
            queryStr = "content:g??e";
            queryStr="content:gano~2";

            QueryParser queryparser = new QueryParser(null, new StandardAnalyzer() );
            Query query= queryparser.parse(queryStr);

            // run the query
            long startTime = System.currentTimeMillis();
            TopDocs topDocs = searcher.search(query, 20);
            long endTime = System.currentTimeMillis();

            // show the resultset
            System.out.println(String.format("Query=> %s\n", query));
            System.out.println(String.format("%d topDocs documents found in %d ms.\n", topDocs.totalHits,
                    (endTime - startTime) ) );

            ScoreDoc[] orderedDocs = topDocs.scoreDocs;

            int position= 1;
            System.out.println("Resultset=> \n");

            for (ScoreDoc aD : orderedDocs) {

                // print info about finding
                int docID= aD.doc;
                double score = aD.score;
                System.out.println(String.format("position=%-10d  score= %10.7f", position, score ));

                // print docID, score
                System.out.println(aD);

                // obtain the stored fields
                Document aDoc = searcher.doc(docID);
                System.out.println("stored fields: " + aDoc);
//				Explanation rta = searcher.explain(query, docID);
//	            System.out.println(rta);

                position++;
                System.out.println();
            }

            index.close();
        }
        catch ( Exception e ) {
            e.printStackTrace();
        }
    }


}