package utils;

import org.apache.pdfbox.contentstream.operator.Operator;
import org.apache.pdfbox.cos.COSArray;
import org.apache.pdfbox.cos.COSString;
import org.apache.pdfbox.pdfparser.PDFStreamParser;
import org.apache.pdfbox.pdfwriter.ContentStreamWriter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.apache.pdfbox.pdmodel.common.PDStream;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.TextPosition;
import org.json.JSONArray;
import org.json.JSONObject;
import youdaoAPI.FanyiV3Demo;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This is an example on how to extract text line by line from pdf document
 */
public class TranslateLines extends PDFTextStripper {

    static List<String> lines = new ArrayList<String>();

    public TranslateLines() throws IOException {
    }

    /**
     * @throws IOException If there is an error parsing the document.
     */
    public static void main( String[] args ) throws IOException {
        PDDocument document = null;
        String encoding = "ISO-8859-1";
        String fileName = "C:\\Users\\chenhongda\\Desktop\\dd.pdf";
        try {
            document = PDDocument.load( new File(fileName) );
            PDFTextStripper stripper = new TranslateLines();
            stripper.setSortByPosition( true );
            stripper.setStartPage( 0 );
            stripper.setEndPage( document.getNumberOfPages() );

            Writer dummy = new OutputStreamWriter(new ByteArrayOutputStream());
            stripper.writeText(document, dummy);

            // print lines
            for(String line:lines){
                FanyiV3Demo fanyiV3Demo = new FanyiV3Demo();
                String lin = fanyiV3Demo.main(line);
                JSONObject jsonObject = new JSONObject(lin);
                JSONArray translation = jsonObject.getJSONArray("translation");
                String re = translation.getString(0);
                System.out.println(re);
                try{
                    searchReplace(line, "Hello World", encoding, true, document);
                }finally {
                    continue;
                }

            }
        }
        finally {
            if( document != null ) {
                document.save(fileName);
                document.close();
            }
        }
    }

    /**
     * Override the default functionality of PDFTextStripper.writeString()
     */
    @Override
    protected void writeString(String str, List<TextPosition> textPositions) throws IOException {
        lines.add(str);
        // you may process the line here itself, as and when it is obtained
    }



    private static void searchReplace (String search, String replace, String encoding, boolean replaceAll, PDDocument doc) throws IOException {
//        PDPageTree pages = doc.getDocumentCatalog().getPages();
        PDPageTree pages = doc.getPages();
        for (PDPage page : pages) {

            PDFStreamParser parser = new PDFStreamParser(page);
            parser.parse();
            List tokens = parser.getTokens();
            for (int j = 0; j < tokens.size(); j++) {
                Object next = tokens.get(j);
                if (next instanceof Operator) {
                    Operator op = (Operator) next;
                    // Tj and TJ are the two operators that display strings in a PDF
                    // Tj takes one operator and that is the string to display so lets update that operator
                    if (op.getName().equals("Tj")) {
                        COSString previous = (COSString) tokens.get(j-1);
                        String string = previous.getString();
                        if (replaceAll)
                            string = string.replaceAll(search, replace);
                        else
                            string = string.replaceFirst(search, replace);
                        previous.setValue(string.getBytes());
                    } else if (op.getName().equals("TJ")) {
                        COSArray previous = (COSArray) tokens.get(j-1);
                        for (int k = 0; k < previous.size(); k++) {
                            Object arrElement = previous.getObject(k);
                            if (arrElement instanceof COSString) {
                                COSString cosString = (COSString) arrElement;
                                String string = cosString.getString();
                                if (replaceAll)
                                    string = string.replaceAll(search, replace);
                                else
                                    string = string.replaceFirst(search, replace);
                                cosString.setValue(string.getBytes());
                            }
                        }
                    }
                }
            }
            // now that the tokens are updated we will replace the page content stream.
            PDStream updatedStream = new PDStream(doc);
            OutputStream out = updatedStream.createOutputStream();
            ContentStreamWriter tokenWriter = new ContentStreamWriter(out);
            tokenWriter.writeTokens(tokens);
            out.close();
            page.setContents(updatedStream);
        }
    }
}