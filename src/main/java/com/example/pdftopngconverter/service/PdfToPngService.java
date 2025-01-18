package com.example.pdftopngconverter.service;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

@Service
public class PdfToPngService {

    public byte[] convertPdfToPng(File file, int width, int height) throws IOException {

        try(PDDocument document = Loader.loadPDF(file)){
            PDFRenderer pdfRenderer = new PDFRenderer(document);
            BufferedImage bim = pdfRenderer.renderImageWithDPI(0,300, ImageType.BGR);

            BufferedImage resizedBim = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            resizedBim.getGraphics().drawImage(bim, 0,0, width, height,null);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(resizedBim, "png", baos);
            return baos.toByteArray();
        }

    }

}
