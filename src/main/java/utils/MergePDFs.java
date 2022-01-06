package utils;

import org.apache.pdfbox.multipdf.PDFMergerUtility;
import java.io.File;
import java.io.IOException;
public class MergePDFs {
    public static void main(String[] args) throws IOException {

//        System.out.println(System.getProperty("user.dir"));

        String perFix = System.getProperty("user.dir")+"\\src\\main\\resources\\";
        PDFMergerUtility PDFmerger = new PDFMergerUtility();
        String fullName = null;
        File file = new File(perFix);
        File[] tempList = file.listFiles();
        for(int i=1;i<=tempList.length;i++){
            fullName = perFix+"single"+i+".pdf";
            File f = new File(fullName);

            //Setting the destination file
            PDFmerger.setDestinationFileName("merged.pdf");

            //adding the source files
            PDFmerger.addSource(f);
            //delete temp pdf
            f.deleteOnExit();

        }
        //Merging the two documents
        PDFmerger.mergeDocuments();

        System.out.println("Documents merged");


    }
}
