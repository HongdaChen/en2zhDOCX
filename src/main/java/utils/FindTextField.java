//package utils;
//
//import org.apache.pdfbox.cos.*;
//import org.apache.pdfbox.pdmodel.PDDocument;
//import org.apache.pdfbox.pdmodel.PDPage;
//import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
//
//import java.io.IOException;
//
//public class FindTextField {
//    public static void main(String[] args) throws IOException {
//        PDDocument doc = new PDDocument();
//        PDPage page = new PDPage();
//        doc.addPage(page);
//        PDAcroForm acroForm = new PDAcroForm(doc);
//        doc.getDocumentCatalog().setAcroForm(acroForm);
//        COSDictionary cosDict = new COSDictionary();
//
//        COSArray rect = new COSArray();
//        rect.add(new COSFloat(250f)); // lower x boundary
//        rect.add(new COSFloat(75f)); // lower y boundary
//        rect.add(new COSFloat(500f)); // upper x boundary
//        rect.add(new COSFloat(125f)); // upper y boundary
//
//        cosDict.setItem(COSName.RECT, rect);
//        cosDict.setItem(COSName.FT, COSName.getPDFName("Tx")); // Field Type
//        cosDict.setItem(COSName.TYPE, COSName.ANNOT);
//        cosDict.setItem(COSName.SUBTYPE, COSName.getPDFName("Widget"));
//        cosDict.setItem(COSName.T, new COSString("yourFieldName"));
//
//        PDTextbox textField = new PDTextbox(acroForm, cosDict);
//
//        acroForm.getFields().add(textField);
//        page.getAnnotations().add(textField.getWidget());
//    }
//}
