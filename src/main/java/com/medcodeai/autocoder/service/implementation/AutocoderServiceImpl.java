package com.medcodeai.autocoder.service.implementation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.medcodeai.autocoder.dto.HistoryDto;
import com.medcodeai.autocoder.dto.NoteResultJoinDto;
import com.medcodeai.autocoder.dto.Response;
import com.medcodeai.autocoder.dto.UserContext;
import com.medcodeai.autocoder.exception.ValidationException;
import com.medcodeai.autocoder.model.MedicalNote;
import com.medcodeai.autocoder.model.Result;
import com.medcodeai.autocoder.model.User;
import com.medcodeai.autocoder.repository.MedicalNoteEmRepository;
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
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class AutocoderServiceImpl implements AutocoderService {

    private final PdfTextExtractionService textExtractionService;
    private final GPTCallerService gptCallerService;
    private final UserRepository userRepository;
    private final MedicalNoteRepository medicalNoteRepository;
    private final ResultRepository resultRepository;
    private final MedicalNoteEmRepository medicalNoteEmRepository;

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
                .resultJson(response)
                .processedAt(LocalDateTime.now())
                .build();
        resultRepository.save(result);
        final ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(response, Response.class);
    }

    @Override
    public List<HistoryDto> getUserHistory(final UserContext userContext) {
        final List<NoteResultJoinDto> noteResultJoinDtos = medicalNoteEmRepository
                .getMedicalNotesForUser(userContext.getUsername());
        final ObjectMapper objectMapper = new ObjectMapper();
        final Map<String, Response> jsonObjMap = noteResultJoinDtos.stream()
                .map(NoteResultJoinDto::getResultJson)
                .collect(Collectors.toMap(
                        json -> json,
                        json -> {
                            try {
                                return objectMapper.readValue(json, Response.class);
                            } catch (JsonProcessingException e) {
                                throw new RuntimeException(e);
                            }
                        }
                ));
        return noteResultJoinDtos.stream()
                .map(noteResultJoinDto -> HistoryDto.builder()
                        .fileName(noteResultJoinDto.getFileName())
                        .content(noteResultJoinDto.getContent())
                        .response(jsonObjMap.get(noteResultJoinDto.getResultJson()))
                        .processedAt(noteResultJoinDto.getProcessedAt())
                        .build())
                .collect(Collectors.toList());
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
