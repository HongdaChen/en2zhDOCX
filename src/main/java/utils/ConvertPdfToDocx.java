package utils;

import com.aspose.pdf.DocSaveOptions;
import com.aspose.pdf.Document;

public class ConvertPdfToDocx {
    public static void main(String pdfPath) {
        // Load source PDF file
        Document doc = new Document(pdfPath);

// Instantiate DocSaveOptions instance
        DocSaveOptions saveOptions = new DocSaveOptions();

// Set output format
        saveOptions.setFormat(DocSaveOptions.DocFormat.DocX);

// Set the recognition mode as Flow
        saveOptions.setMode(DocSaveOptions.RecognitionMode.Flow);

// Set the horizontal proximity as 2.5
        saveOptions.setRelativeHorizontalProximity(2.5f);

// Enable bullets recognition during conversion process
        saveOptions.setRecognizeBullets(true);

// Save resultant DOCX file
        doc.save("resultant.docx", saveOptions);
    }
}
