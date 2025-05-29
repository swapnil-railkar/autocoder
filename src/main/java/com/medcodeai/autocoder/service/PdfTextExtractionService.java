package com.medcodeai.autocoder.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface PdfTextExtractionService {

    String getPdfText(final MultipartFile medicalNote);
}
