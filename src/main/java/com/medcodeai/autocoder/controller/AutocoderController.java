package com.medcodeai.autocoder.controller;

import com.medcodeai.autocoder.dto.HistoryDto;
import com.medcodeai.autocoder.dto.Response;
import com.medcodeai.autocoder.dto.UserContext;
import com.medcodeai.autocoder.service.AutocoderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class AutocoderController {

    @Autowired
    private final AutocoderService autocoderService;

    @PostMapping("/upload")
    public Response getSuggestions(final @RequestPart("note") MultipartFile note,
                                   @RequestBody final UserContext userContext) throws IOException {
        return autocoderService.getSuggestionResponse(note, userContext);
    }

    @GetMapping("/history")
    public HistoryDto getHistory(@RequestBody final UserContext userContext) {
        return autocoderService.getUserHistory(userContext);
    }
}
