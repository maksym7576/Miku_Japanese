package com.japanese.lessons.service.Lesson;

import com.japanese.lessons.models.Lesson.Manga;
import com.japanese.lessons.repositories.Lesson.IMangaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MangaServiceTest {

    @Mock
    private IMangaRepository iMangaRepository;

    @InjectMocks
    private MangaService mangaService;

    private Manga manga;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        manga = new Manga();
        manga.setId(1L);
        manga.setName("Naruto");
        manga.setStartDialog("First dialog");
    }

    @Test
    void getMangaById_Success() {
        when(iMangaRepository.findById(1L)).thenReturn(Optional.of(manga));

        Manga foundManga = mangaService.getMangaById(1L);

        assertNotNull(foundManga);
        assertEquals(manga.getId(), foundManga.getId());
    }

    @Test
    void getMangaById_NotFound() {
        when(iMangaRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            mangaService.getMangaById(1L);
        });

        assertEquals("Manga not found with id: 1", exception.getMessage());
    }

    @Test
    void saveManga_Success() {
        when(iMangaRepository.save(manga)).thenReturn(manga);

        Manga savedManga = mangaService.saveManga(manga);

        assertNotNull(savedManga);
        assertEquals(manga.getId(), savedManga.getId());
    }

    @Test
    void saveManga_Null() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            mangaService.saveManga(null);
        });

        assertEquals("Manga cannot be null.", exception.getMessage());
    }

    @Test
    void updateManga_Success() {
        when(iMangaRepository.findById(1L)).thenReturn(Optional.of(manga));
        when(iMangaRepository.save(manga)).thenReturn(manga);

        Manga updatedManga = new Manga();
        updatedManga.setName("Updated Naruto");
        updatedManga.setStartDialog("Updated dialog");

        mangaService.updateManga(1L, updatedManga);

        assertEquals("Updated Naruto", manga.getName());
    }

    @Test
    void updateManga_NotFound() {
        when(iMangaRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            mangaService.updateManga(1L, manga);
        });

        assertEquals("Manga not found with id: 1", exception.getMessage());
    }

    @Test
    void deleteManga_Success() {
        when(iMangaRepository.existsById(1L)).thenReturn(true);

        mangaService.deleteManga(1L);

        verify(iMangaRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteManga_NotFound() {
        when(iMangaRepository.existsById(1L)).thenReturn(false);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            mangaService.deleteManga(1L);
        });

        assertEquals("Manga doesn't exist.", exception.getMessage());
    }
}
