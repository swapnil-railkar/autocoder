package com.medcodeai.autocoder.service.implementation;

import com.medcodeai.autocoder.exception.TextExtractionException;
import com.medcodeai.autocoder.service.PdfTextExtractionService;
import com.medcodeai.autocoder.util.Constants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
@RequiredArgsConstructor
public class PdfTextExtractionImpl implements PdfTextExtractionService {

    @Override
    public String getPdfText(MultipartFile medicalNote) {
        try (PDDocument document = Loader.loadPDF(medicalNote.getBytes())){
            final PDFTextStripper textStripper = new PDFTextStripper();
            return textStripper.getText(document);
        } catch (Exception e) {
            throw new TextExtractionException(Constants.CANNOT_EXTRACT_TEXT, e);
        }
    }
}
