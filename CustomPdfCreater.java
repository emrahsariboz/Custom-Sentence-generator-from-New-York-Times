
package custompdfcreater;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileOutputStream;
import java.text.BreakIterator;
import java.util.Locale;
import java.util.Scanner;
import org.jsoup.Jsoup;

public class CustomPdfCreater {
    public static void main(String[] args) throws Exception {
        org.jsoup.nodes.Document doc = Jsoup.connect("https://www.nytimes.com/").get(); //connection to the nytimes
 
        Scanner sn=new Scanner(System.in);  
        System.out.println("Enter the word");
        String word=sn.next();
        String keyWordConcat=":containsOwn(" + word + ")"; //concatination of the word with the jsoup Pseudo selectors
        
          
        BreakIterator iterator = BreakIterator.getSentenceInstance(Locale.US);
        
         
        String content = doc.select(keyWordConcat).text(); // doc.selects the selectors and starts to bring the sentence
        
        // this while loop will break the paragraph into the sentences. 
        iterator.setText(content);
        int index=0;
        
        while(iterator.next() != BreakIterator.DONE){
            String sentence=content.substring(index, iterator.current());
            System.out.println("Sentence: " + sentence);
            index=iterator.current();

        }

        Document document = new Document();
        //try catch protects from thrown error. Without try-catch, it will not compile.
        try {
            PdfWriter.getInstance(document, new FileOutputStream("SentenceExample.pdf")); //creates a pdf file 
            document.open();
            Paragraph p1 = new Paragraph("Hello! Welcome to sentence generator with java!");  //first paragraph in the pdf file

            System.out.println("The word is: " + word);
            Paragraph p2 = new Paragraph("The first Sentence is: \n ***" + content); // content is the sentence's
            document.add(p1);//adds the paragraph to the document (pdf file)
            document.add(p2);
        } catch (Exception e) {
            System.out.println(e);
        }
        document.close();

    }
 

}
