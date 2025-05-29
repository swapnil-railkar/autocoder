package com.medcodeai.autocoder.controller;

import com.medcodeai.autocoder.dto.Response;
import com.medcodeai.autocoder.service.AutocoderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping(path = "/")
public class AutocoderController {

    @Autowired
    private AutocoderService autocoderService;

    @PostMapping("/upload")
    public Response getSuggestions(final @RequestPart("note") MultipartFile note) throws IOException {
        return autocoderService.getSuggestionResponse(note);
    }
}
