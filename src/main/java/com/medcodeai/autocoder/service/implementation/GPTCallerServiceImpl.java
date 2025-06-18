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
        // logic to send prompt to openai and get json response
        return """
                {
                  "diagnoses": [
                    { "condition": "Chronic asthma", "code": "J45.40" },
                    { "condition": "Syncope", "code": "R55" }
                  ],
                  "procedures": [
                    { "procedure": "Pulmonary function test", "code": "94010" },
                    { "procedure": "EKG", "code": "93000" }
                  ]
                }
                """;
    }
}
