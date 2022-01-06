import com.aspose.pdf.DocSaveOptions;
import com.aspose.words.Document;
import com.aspose.words.FindReplaceDirection;
import com.aspose.words.FindReplaceOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.json.JSONArray;
import org.json.JSONObject;
import utils.DeleteTempFiles;
import utils.FanyiV3Demo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class Test {
    public static void main(String[] args) throws IOException {
//        File file = new File("C:\\Users\\chenhongda\\Downloads\\陈鸿达简历.docx");
//        File file1 = new File("C:\\Users\\chenhongda\\Desktop\\chen.docx");
//        Files.copy(file.toPath(),file1.toPath());

        String inperFix = System.getProperty("user.dir")+"\\src\\main\\resources\\inpdf\\";
//        File file = new File(inperFix);
//        File[] files = file.listFiles();
//        for( File f : files){
//            f.delete();
//        }
        DeleteTempFiles.main(new File(inperFix));
    }
}
