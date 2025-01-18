package com.example.pdftopngconverter.controller;

import com.example.pdftopngconverter.service.PdfToPngService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
public class PdfToPngController {

    @Autowired
    private PdfToPngService pdfToPngService;

    @PostMapping("/convert/pdf-to-png")
    public ResponseEntity<Resource> convertPdfToPng(@RequestParam("file") MultipartFile file,
                                                    @RequestParam("width") int width,
                                                    @RequestParam("height") int height){
        try{
            File tempPdfFile = File.createTempFile("uploaded", ".pdf");
            file.transferTo(tempPdfFile);

            byte[] pngBytes = pdfToPngService.convertPdfToPng(tempPdfFile, width, height);

            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=first_page.png");
            headers.add(HttpHeaders.CONTENT_TYPE, MediaType.IMAGE_PNG_VALUE);

            Resource resource = new ByteArrayResource(pngBytes);
            return ResponseEntity.ok()
                    .headers(headers)
                    .contentLength(pngBytes.length)
                    .body(resource);
        }catch(IOException e){
            return ResponseEntity.status(500).body(null);
        }
    }
}
