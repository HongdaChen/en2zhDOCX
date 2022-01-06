package utils;

import com.aspose.words.Document;
import com.aspose.words.PdfImageCompression;
import com.aspose.words.PdfSaveOptions;
import com.aspose.words.PdfTextCompression;

public class ConvertDocxToPdf {
    public static void main(String outName) throws Exception {
        // Load the Word document from disk
        Document doc = new Document("Find-And-Replace-Text.docx");
        PdfSaveOptions options = new PdfSaveOptions();
        // Text and image compression
        options.setTextCompression(PdfTextCompression.FLATE);
        options.setImageCompression(PdfImageCompression.AUTO);
        // Save Word as PDF
        doc.save(outName, options);

    }
}
