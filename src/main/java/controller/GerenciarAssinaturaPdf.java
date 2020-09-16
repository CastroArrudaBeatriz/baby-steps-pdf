package controller;

import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import template.*;

public class GerenciarAssinaturaPdf {

    public void assinarPdf(Image image, PdfReader pdfReader, PdfStamper pdfStamper) {

        try {
            int pagesNumber = pdfReader.getNumberOfPages();

            for (int i = 1; i <= pagesNumber; i++) {

                String pageContent = PdfTextExtractor.getTextFromPage(pdfReader, i);
                if (pageContent == null || pageContent.isEmpty()) continue;

                TemplateFactory templateFactory = new TemplateFactory();
                Template template = templateFactory.factory(pageContent);
                if (template == null) continue;

                concantenarAssinatura(i, image, pdfStamper, template);
            }
            pdfStamper.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void concantenarAssinatura(int numeroPagina, Image imagem, PdfStamper pdfStamper, Template template) throws Exception {
        PdfContentByte content = pdfStamper.getOverContent(numeroPagina);

        float scaler = ((content.getPdfDocument().getPageSize().getWidth()) / imagem.getWidth()) * 12;
        imagem.scalePercent(scaler);
        imagem.setAbsolutePosition(template.posicaoX(), template.posicaoY());
        content.addImage(imagem);
    }

}
