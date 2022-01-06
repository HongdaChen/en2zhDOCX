package utils;

import com.aspose.words.Document;
import com.aspose.words.FindReplaceDirection;
import com.aspose.words.FindReplaceOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.util.List;

public class Translate {
    public static void main() throws Exception {

        //1.extract
        String path = "resultant.docx";
        FileInputStream fin = new FileInputStream(path);
        List<XWPFParagraph> paragraphs = null;
        XWPFDocument document = new XWPFDocument(fin);
        paragraphs = document.getParagraphs();//有图出现问题
        Document doc = new Document(path);
        //2.translate and replace
        for (XWPFParagraph para : paragraphs) {
            try{
                FanyiV3Demo fanyiV3Demo = new FanyiV3Demo();
                String lin = fanyiV3Demo.main(para.getText());
                JSONObject jsonObject = new JSONObject(lin);
                JSONArray translation = jsonObject.getJSONArray("translation");
                String re = translation.getString(0);
                System.out.println(re);
                //replace
                doc.getRange().replace(para.getText(),re, new FindReplaceOptions(FindReplaceDirection.FORWARD));
            }catch (Exception e){
                e.printStackTrace();
                continue;
            }
        }
            doc.save("Find-And-Replace-Text.docx");
        }


}
