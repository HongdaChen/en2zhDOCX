package utils;

import java.awt.Color;
import java.io.*;
import java.util.*;

import org.apache.pdfbox.cos.COSArray;
import org.apache.pdfbox.cos.COSString;
import org.apache.pdfbox.contentstream.operator.Operator;
import org.apache.pdfbox.pdfparser.PDFStreamParser;
import org.apache.pdfbox.pdfwriter.ContentStreamWriter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.common.PDStream;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

public class SimpleReplace {

    public static void main (String[] args) throws Exception {

        String fileName = "C:\\Users\\chenhongda\\Desktop\\FLPPT.pdf";
        PDDocument document = PDDocument.load(new File(fileName));

        String outputFileName = "C:\\Users\\chenhongda\\Desktop\\new.pdf";
//        // the encoding will need to be adapted to your circumstances
        String encoding = "UTF-8";
//
//        // Create a document and add a page to it
//        PDDocument document = new PDDocument();
//        PDPage page1 = new PDPage(PDRectangle.A4);
//        // PDRectangle.LETTER and others are also possible
//        PDRectangle rect = page1.getMediaBox();
//        // rect can be used to get the page width and height
//        document.addPage(page1);
//
//        // Create a new font object selecting one of the PDF base fonts
//        PDFont fontPlain = PDType1Font.HELVETICA;
//
//        // Start a new content stream which will "hold" the to be created content
//        PDPageContentStream cos = new PDPageContentStream(document, page1);
//
//        // Define a text content stream using the selected font, move the cursor and draw some text
//        cos.beginText();
//        cos.setFont(fontPlain, 12);
//        cos.newLineAtOffset(100, rect.getHeight() - 50);
//        // add 'Hello World' twice
//        cos.showText("Hello World, Hello World");
//        cos.endText();

        // Make sure that the content stream is closed
//        cos.close();

        // Note that search and replace can be regular expressions


        searchReplace("Hello", "Hi", encoding, true, document);
        // replace only first occurrence of 'World'
        searchReplace("World", "Earth", encoding, false, document);

        // Save the results and ensure that the document is properly closed
        document.save(outputFileName);
        document.close();
    }

    private static void searchReplace (String search, String replace,
                                       String encoding, boolean replaceAll, PDDocument doc) throws IOException {
        PDPageTree pages = doc.getDocumentCatalog().getPages();
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
