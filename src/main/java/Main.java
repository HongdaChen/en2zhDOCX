import utils.*;

import java.io.File;
import java.nio.file.Files;

public class Main {
    public static void main(String[] args) throws Exception {
        //filename
        String inPath = args[0];
        //path
        String outPath = args[1];
        //youdaoapi
        String APP_KEY = args[2];
        String APP_SECRET = args[3];

        //split pdf
        String inperFix = System.getProperty("user.dir")+"\\src\\main\\resources\\inpdf\\";
        String outperFix = System.getProperty("user.dir")+"\\src\\main\\resources\\outpdf\\";
        int pagNum = SplitPages.main(inPath, inperFix);
        String fullName = null;

        for(int i=1;i<=pagNum;i++) {
            fullName = inperFix + "single" + i + ".pdf";
            ConvertPdfToDocx.main(fullName);
            try{
                Translate.main();
                ConvertDocxToPdf.main(outperFix+"single"+i+".pdf");
            }catch (Exception e){
                //不翻译了
                File source = new File(inperFix + "single" + i + ".pdf");
                File dest = new File(outperFix + "single" + i + ".pdf");
                Files.copy(source.toPath(),dest.toPath());
                e.printStackTrace();
            }
            File f = new File("Find-And-Replace-Text.docx");
            f.delete();
            File f2= new File("resultant.docx");
            f2.delete();
        }
        MergePDFs.main(outperFix,outPath);
        //delete inpdf/* outpdf/*
        DeleteTempFiles.main(new File(inperFix));
        DeleteTempFiles.main(new File(outPath));
    }
}
