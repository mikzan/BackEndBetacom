package com.betacom.dischi.utilities.mail;

import java.io.ByteArrayOutputStream;
import java.io.StringReader;

import org.springframework.stereotype.Service;

import com.lowagie.text.Document;
import com.lowagie.text.PageSize;
import com.lowagie.text.html.simpleparser.HTMLWorker;
import com.lowagie.text.pdf.PdfWriter;

@Service
public class PDFGenerator {
	
	 public byte[] generatePDF(String body) throws Exception {
	        Document document = new Document(PageSize.A4);  
	        ByteArrayOutputStream out = new ByteArrayOutputStream();
	        try {
	            PdfWriter.getInstance(document, out);
	            document.open();
	            
	            document.open();
	            String htmlContent = body;
	            HTMLWorker htmlWorker = new HTMLWorker(document);
	            htmlWorker.parse(new StringReader(htmlContent));
	            
	        } catch (Exception e) {
	            e.printStackTrace();
	            throw new RuntimeException("Errore nella generazione del PDF", e);
	        } finally {
	            document.close();
	        }

	        return out.toByteArray();
	    }

}
