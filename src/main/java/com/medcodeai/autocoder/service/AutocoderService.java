package com.medcodeai.autocoder.service;

import com.medcodeai.autocoder.dto.HistoryDto;
import com.medcodeai.autocoder.dto.Response;
import com.medcodeai.autocoder.dto.UserContext;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public interface AutocoderService {

    Response getSuggestionResponse(final MultipartFile note, final UserContext userContext) throws IOException;

    HistoryDto getUserHistory(final UserContext userContext);
}
