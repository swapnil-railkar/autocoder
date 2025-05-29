package com.medcodeai.autocoder.service;

import org.springframework.stereotype.Service;

@Service
public interface GPTCallerService {

    String getResponseJsonForText(final String medicalNoteTxt);
}
