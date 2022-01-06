package utils;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FieldLevel {
    public static void main(String[] args) throws IOException {

//        PDDocument pdDoc = PDDocument.loadNonSeq(new File(""), null );
////        PDDocumentCatalog pdCatalog = pdDoc.getDocumentCatalog();
////        PDAcroForm pdAcroForm = pdCatalog.getAcroForm();
////
////        for(PDField pdField : pdAcroForm.getFields()){
////            System.out.println(pdField.getValue());
////        }
        update();



    }

    private static void update() throws InvalidPasswordException, IOException {
        Map<String, String> map = new HashMap<>();
        map.put("fieldname", "value to update");
        File template = new File("C:\\Users\\chenhongda\\Desktop\\dd.pdf");
        PDDocument document = PDDocument.load(template);

        PDAcroForm form = new PDAcroForm(document);
        document.getDocumentCatalog().setAcroForm(form);


        PDDocumentCatalog documentCatalog = document.getDocumentCatalog();
        PDAcroForm acroForm = documentCatalog.getAcroForm();
        List<PDField> fields = acroForm.getFields();
        for (PDField field : fields) {
            System.out.println(field.toString());
//            for (Map.Entry<String, String> entry : map.entrySet()) {
////                if (entry.getKey().equals(field.getFullyQualifiedName())) {
////                    field.setValue(entry.getValue());
////                    field.setReadOnly(true);
////                }
////            }
//        }
////        File out = new File("out.pdf");
////        document.save(out);
        document.close();
    }
}
}
