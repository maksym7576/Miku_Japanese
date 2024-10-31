package com.japanese.lessons.models.lesson.mangaExercise;

public class IncompleteMangaDataException extends RuntimeException {
    public IncompleteMangaDataException(String message) {
        super(message);
    }
}