package com.medcodeai.autocoder.service.implementation;

import com.medcodeai.autocoder.service.GPTCallerService;
import com.medcodeai.autocoder.util.Constants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class GPTCallerServiceImpl implements GPTCallerService {

    @Override
    public String getResponseJsonForText(final String medicalNoteTxt) {
        final String prompt = Constants.PROMPT + medicalNoteTxt;
        // logic to send prompt to open ai and get json response
        return "{\n" +
                "  \"diagnoses\": [\n" +
                "    { \"condition\": \"Chronic asthma\", \"code\": \"J45.40\" },\n" +
                "    { \"condition\": \"Syncope\", \"code\": \"R55\" }\n" +
                "  ],\n" +
                "  \"procedures\": [\n" +
                "    { \"procedure\": \"Pulmonary function test\", \"code\": \"94010\" },\n" +
                "    { \"procedure\": \"EKG\", \"code\": \"93000\" }\n" +
                "  ]\n" +
                "}\n";
    }
}
