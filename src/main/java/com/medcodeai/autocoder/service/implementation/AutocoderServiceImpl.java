package com.medcodeai.autocoder.service.implementation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.medcodeai.autocoder.dto.HistoryDto;
import com.medcodeai.autocoder.dto.Response;
import com.medcodeai.autocoder.dto.UserContext;
import com.medcodeai.autocoder.exception.ValidationException;
import com.medcodeai.autocoder.model.MedicalNote;
import com.medcodeai.autocoder.model.Result;
import com.medcodeai.autocoder.model.User;
import com.medcodeai.autocoder.repository.MedicalNoteRepository;
import com.medcodeai.autocoder.repository.ResultRepository;
import com.medcodeai.autocoder.repository.UserRepository;
import com.medcodeai.autocoder.service.AutocoderService;
import com.medcodeai.autocoder.service.GPTCallerService;
import com.medcodeai.autocoder.service.PdfTextExtractionService;
import com.medcodeai.autocoder.util.Constants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class AutocoderServiceImpl implements AutocoderService {

    private final PdfTextExtractionService textExtractionService;
    private final GPTCallerService gptCallerService;
    private final UserRepository userRepository;
    private final MedicalNoteRepository medicalNoteRepository;
    private final ResultRepository resultRepository;

    @Override
    public Response getSuggestionResponse(final MultipartFile note, final UserContext userContext) throws JsonProcessingException {
        validateNote(note);
        final String pdfText = textExtractionService.getPdfText(note);
        final Optional<User> user = userRepository.findById(userContext.getUsername());
        if (user.isEmpty()) {
            throw new ValidationException("Invalid user");
        }
        final MedicalNote medicalNote = MedicalNote.builder()
                .extractedText(pdfText)
                .fileName(note.getName())
                .username(user.get())
                .uploadedAt(LocalDateTime.now())
                .build();
        final MedicalNote savedNote = medicalNoteRepository.save(medicalNote);
        final String response = gptCallerService.getResponseJsonForText(pdfText);
        final Result result = Result.builder()
                .medicalNote(savedNote)
                .ResultJson(response)
                .processedAt(LocalDateTime.now())
                .build();
        resultRepository.save(result);
        final ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(response, Response.class);
    }

    @Override
    public HistoryDto getUserHistory(final UserContext userContext) {
        return null;
    }

    private void validateNote(final MultipartFile note) {
        if (note == null || note.isEmpty()) {
            throw new ValidationException(Constants.EMPTY_NOTE);
        }
        final String contentType = note.getContentType();
        final String filename = note.getOriginalFilename();

        if (contentType != null && !contentType.equalsIgnoreCase("application/pdf")
                && filename != null && !filename.toLowerCase().endsWith(".pdf")) {
            throw new ValidationException(Constants.INCORRECT_FORMAT_ERR);
        }
    }

}
