package com.medcodeai.autocoder.service;

import com.medcodeai.autocoder.dto.Response;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public interface AutocoderService {

    Response getSuggestionResponse(final MultipartFile note) throws IOException;
}
