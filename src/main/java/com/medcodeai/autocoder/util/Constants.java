package com.medcodeai.autocoder.util;

public interface Constants {
    String PROMPT = """
            You are a medical coding assistant.
            
            Extract all diagnoses and procedures from the following medical note. For each diagnosis, provide the appropriate ICD-10 code. For each procedure, provide the appropriate CPT code.
            
            Return the result as a JSON object with this exact structure:
            {
              "diagnoses": [
                { "condition": "<condition name>", "icd_code": "<ICD-10 code>" }
              ],
              "procedures": [
                { "procedure": "<procedure name>", "cpt_code": "<CPT code>" }
              ]
            }
            
            Only include valid diagnoses and procedures. Do not include explanations, and do not return anything except the JSON object.
            
            Here is the medical note text:
            """;
    String EMPTY_NOTE = "note is empty";
    String INCORRECT_FORMAT_ERR = "note should be in PDF format";
    String CANNOT_EXTRACT_TEXT = "Error occurred while extracting text";
}
