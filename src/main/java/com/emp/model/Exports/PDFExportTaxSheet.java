package com.emp.model.Exports;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.List;

import com.emp.model.domain.Employee;
import com.emp.model.domain.Tax;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import rst.pdfbox.layout.elements.Document;
import rst.pdfbox.layout.elements.Paragraph;
import rst.pdfbox.layout.elements.render.VerticalLayoutHint;
import rst.pdfbox.layout.text.Alignment;
import rst.pdfbox.layout.util.WordBreakerFactory;

public class PDFExportTaxSheet {

    public PDFExportTaxSheet()
    {
        super();
    }

    public void buildPDFSingle(Tax taxInterval) throws IOException {
        DecimalFormat df2 = new DecimalFormat(".##");

        Document document = new Document(40,60,40,60);
        Paragraph paragraph = new Paragraph();
        paragraph.addText("TIME SHEET",14,PDType1Font.HELVETICA_BOLD);
        paragraph.setAlignment(Alignment.Center);
        paragraph.setMaxWidth(140);
        document.add(paragraph, VerticalLayoutHint.CENTER);

        paragraph = new Paragraph();
        paragraph.addText("EMPLOYEE: "+taxInterval.getEmployee().getName(), 13,PDType1Font.HELVETICA_BOLD);
        paragraph.setAlignment(Alignment.Left);
        paragraph.setMaxWidth(440);
        document.add(paragraph, VerticalLayoutHint.LEFT);



        paragraph = new Paragraph();
        paragraph.addText("PERIOD", 13,PDType1Font.HELVETICA);
        paragraph.setAlignment(Alignment.Left);
        paragraph.setMaxWidth(440);
        document.add(paragraph, VerticalLayoutHint.LEFT);
        String dateOne = taxInterval.getDateOne().getDayOfMonth()+"/"+taxInterval.getDateOne().getMonthValue()+"/"+taxInterval.getDateOne().getYear();
        String dateTwo = taxInterval.getDateTwo().getDayOfMonth()+"/"+taxInterval.getDateTwo().getMonthValue()+"/"+taxInterval.getDateTwo().getYear();
        paragraph = new Paragraph();
        paragraph.addText("Start Date: "+dateOne+"       "+"Final Date: "+dateTwo, 11,PDType1Font.HELVETICA);
        paragraph.setAlignment(Alignment.Left);
        paragraph.setMaxWidth(440);
        document.add(paragraph, VerticalLayoutHint.LEFT);


        paragraph = new Paragraph();
        paragraph.addText("GROSS: $"+df2.format(taxInterval.getGross()), 11,PDType1Font.HELVETICA);
        paragraph.setAlignment(Alignment.Left);
        paragraph.setMaxWidth(340);
        document.add(paragraph, VerticalLayoutHint.LEFT);

        paragraph = new Paragraph();
        paragraph.addText("TAX:       $"+df2.format(taxInterval.getTaxIncome()), 11,PDType1Font.HELVETICA);
        paragraph.setAlignment(Alignment.Left);
        paragraph.setMaxWidth(340);
        document.add(paragraph, VerticalLayoutHint.LEFT);

        paragraph = new Paragraph();
        paragraph.addText("NET:       $"+df2.format(taxInterval.getNet()), 11,PDType1Font.HELVETICA);
        paragraph.setAlignment(Alignment.Left);
        paragraph.setMaxWidth(340);
        document.add(paragraph, VerticalLayoutHint.LEFT);
        String fileName = taxInterval.getEmployee().getName()+"-"+taxInterval.getDateOne();
        File file = new File(fileName);
        file.getAbsoluteFile();

        final OutputStream outputStream = new FileOutputStream(file+".pdf");
        document.save(outputStream);
    }
    public void buildPDFAll(String file, List<Employee> employees, List<Tax> taxes) throws IOException {
        DecimalFormat df2 = new DecimalFormat(".##");

        Document document = new Document(40,60,40,60);
        Paragraph paragraph = new Paragraph();
        paragraph.addText("Time Sheet ",12,PDType1Font.HELVETICA);
        paragraph.setAlignment(Alignment.Left);
        paragraph.setMaxWidth(140);
        document.add(paragraph, VerticalLayoutHint.LEFT);

        for(int i=0; i<employees.size(); i++){

        }
    }
}
