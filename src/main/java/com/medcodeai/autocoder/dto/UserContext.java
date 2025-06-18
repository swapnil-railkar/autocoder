package com.medcodeai.autocoder.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// TODO implement Authentication to get auth username
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserContext {

    private String username;
}
