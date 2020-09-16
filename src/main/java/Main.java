import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import controller.GerenciarAssinaturaPdf;
import sun.misc.BASE64Decoder;
import utils.Fonts;
import utils.StringFormatter;
import utils.Strings;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Base64;
import java.util.UUID;

public class Main {

    public static void main(String[] args) {

        //assinar documento digitalmente
        String base64documentoAssinado = assinaturaDocumentos();
        decodeBase64Pdf(base64documentoAssinado, true);

        //gerar pdf
        String base64documento = gerarDocumento();
        decodeBase64Pdf(base64documento, false);
    }


    private static String assinaturaDocumentos() {

        try {
            //ler arquivo e guardar base64 imagem assinatura
            InputStream imageStream = Main.class.getClassLoader().getResourceAsStream("base64/image_base64_example.txt");
            BufferedReader imageBufferedReader = new BufferedReader(new InputStreamReader(imageStream));
            String base64image = imageBufferedReader.readLine();
            imageBufferedReader.close();

            Image image = Image.getInstance(Base64.getDecoder().decode(base64image));

            //ler arquivo e guardar base64 pdf
            InputStream pdfStream = Main.class.getClassLoader().getResourceAsStream("base64/pdf_base64_example.txt");
            BufferedReader pdfBufferedReader = new BufferedReader(new InputStreamReader(pdfStream));
            String base64pdf = pdfBufferedReader.readLine();
            pdfBufferedReader.close();

            PdfReader pdfReader = new PdfReader(Base64.getDecoder().decode(base64pdf));
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            PdfStamper pdfStamper = new PdfStamper(pdfReader, outputStream);

            GerenciarAssinaturaPdf gerenciarAssinaturaPdf = new GerenciarAssinaturaPdf();
            gerenciarAssinaturaPdf.assinarPdf(image,pdfReader,pdfStamper);

            return Base64.getEncoder().encodeToString(outputStream.toByteArray());

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void decodeBase64Pdf(String base64, boolean isAssinado) {
        try {
            BASE64Decoder decoder = new BASE64Decoder();
            byte[] decodedBytes = decoder.decodeBuffer(base64);

            File file;
            if(isAssinado) file = new File("C://docs/documentoAssinado.pdf");
            else file = new File("C://docs/documentoEletronico.pdf");

            FileOutputStream fileOutputStream = new FileOutputStream(file);

            fileOutputStream.write(decodedBytes);
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (Exception e) {
            System.err.println("Exception: " + e.getClass().getCanonicalName() +
                    System.lineSeparator() + "Mensagem: " + e.getMessage());
        }
    }


    private static String gerarDocumento(){

        try{
            Document document = new Document();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            PdfWriter writer = PdfWriter.getInstance(document , stream);

            document.open();
            document.setMargins(70,70,70,70);
            document.newPage();
            document.addTitle(Strings.DOCUMENT_TITLE);

            PdfPTable tableHeader = new PdfPTable(new float[] {1, 3});
            tableHeader.setWidthPercentage(100);
            tableHeader.setSpacingAfter(20);

            Image image = Image.getInstance(Main.class.getResource("images/logo.png"));
            image.scaleToFit(70, 70);

            PdfPCell colunaLogo = new PdfPCell(image);
            colunaLogo.setBorder(Rectangle.NO_BORDER);
            colunaLogo.setHorizontalAlignment(Element.ALIGN_LEFT);
            tableHeader.addCell(colunaLogo);


            Phrase titulo = new Phrase(Strings.DOCUMENT_TITLE, Fonts.FONT_TITLE);
            PdfPCell colunaTitulo = new PdfPCell(titulo);
            colunaTitulo.setBorder(Rectangle.NO_BORDER);
            colunaTitulo.setVerticalAlignment(Element.ALIGN_MIDDLE);
            colunaTitulo.setHorizontalAlignment(Element.ANCHOR);
            tableHeader.addCell(colunaTitulo);

            document.add(tableHeader);

            //Descrição Documento
            Paragraph documentDescriptionParagraph = new Paragraph(Strings.DOCUMENT_DESCRIPTION, Fonts.FONT_SUBTITLE);
            PdfPCell colunaDescricao = new PdfPCell(documentDescriptionParagraph);
            colunaDescricao.setBorder(Rectangle.NO_BORDER);
            document.add(documentDescriptionParagraph);
            document.add(Chunk.NEWLINE);

            //Hash Paragraph
            adicionarLinha(Strings.HASH_LABEL, UUID.randomUUID().toString(), document);
            document.add(Chunk.NEWLINE);

            //Informações Cliente
            document.add(new Paragraph(Strings.CLIENT_DATA, Fonts.FONT_SUBTITLE_BOLD));

            adicionarLinha(Strings.CLIENT_NAME, StringFormatter.validarStringParaVazio("Maria Almeida Prado"), document);
            adicionarLinha(Strings.CLIENT_NUMBER, StringFormatter.validarStringParaVazio("81 999999999"), document);
            adicionarLinha(Strings.CLIENT_CPF, StringFormatter.validarStringParaVazio("17100000000"), document);

            ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, new Phrase(Strings.ELETRONIC_DOCUMENT),
                    document.getPageSize().getWidth()/2,
                    30,
                    0);

            document.close();

            return Base64.getEncoder().encodeToString(stream.toByteArray());

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }

    private static void adicionarLinha(String fieldTitle, String fieldValue, Document document) throws DocumentException {
        Chunk label = new Chunk(fieldTitle);
        Chunk value = new Chunk(fieldValue);
        Paragraph paragraph = new Paragraph();
        paragraph.add(label);
        paragraph.add(value);
        document.add(paragraph);
    }

}