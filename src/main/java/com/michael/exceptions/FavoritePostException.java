package com.michael.exceptions;

public class FavoritePostException extends RuntimeException{
    public FavoritePostException(String message) {
        super(message);
    }
}
