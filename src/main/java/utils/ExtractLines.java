package utils;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import youdaoAPI.FanyiV3Demo;

import java.io.File;
import java.io.IOException;

public class ExtractLines {

    public static void main(String[] args) throws IOException {

        File file = new File("C:\\Users\\chenhongda\\Desktop\\FLPPT.pdf");
        PDDocument document = PDDocument.load(file);

        PDFTextStripper pdfStripper = new PDFTextStripper();
        String text = pdfStripper.getText(document);
        document.close();
        FanyiV3Demo fanyiV3Demo = new FanyiV3Demo();
        String main = fanyiV3Demo.main(text);

        System.out.println(text.substring(0));
    }
}
