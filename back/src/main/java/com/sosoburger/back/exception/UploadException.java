package com.sosoburger.back.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UploadException extends Exception{
    private String message;
}
