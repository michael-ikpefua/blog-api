package com.michael.utils;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
public class ErrorMessage {

    private Date timeStamp;
    private String message;
}
