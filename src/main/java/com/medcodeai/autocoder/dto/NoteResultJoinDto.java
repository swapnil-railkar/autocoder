package com.medcodeai.autocoder.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class NoteResultJoinDto {

    private String fileName;
    private String content;
    private String resultJson;
    private LocalDateTime processedAt;
}
