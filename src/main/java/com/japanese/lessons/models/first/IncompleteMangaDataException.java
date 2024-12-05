package com.japanese.lessons.models.first;

public class IncompleteMangaDataException extends RuntimeException {
    public IncompleteMangaDataException(String message) {
        super(message);
    }
}