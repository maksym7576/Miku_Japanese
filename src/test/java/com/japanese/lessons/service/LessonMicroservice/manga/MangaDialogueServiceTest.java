package com.japanese.lessons.service.LessonMicroservice.manga;

import com.japanese.lessons.models.LessonMicroservice.manga.Manga;
import com.japanese.lessons.models.LessonMicroservice.manga.MangaDialogue;
import com.japanese.lessons.repositories.LessonMisroservice.IMangaTextRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MangaDialogueServiceTest {

    @Mock
    IMangaTextRepository iMangaTextRepository;

    @InjectMocks
    MangaDialogueService mangaDialogueService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getMangaDialogueById_ShouldReturnMangaDialogue_WhenExists() {
        Long id = 1L;
        MangaDialogue mangaDialogue = new MangaDialogue();
        mangaDialogue.setId(id);

        when(iMangaTextRepository.findById(id)).thenReturn(Optional.of(mangaDialogue));

        MangaDialogue foundMangaDialogue = mangaDialogueService.getMangaDialogueById(id);

        assertNotNull(foundMangaDialogue);
        assertEquals(id, foundMangaDialogue.getId());
    }

    @Test
    void getMangaDialogueById_ShouldThrowException_WhenNotExists() {
        Long id = 1L;

        when(iMangaTextRepository.findById(id)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            mangaDialogueService.getMangaDialogueById(id);
        });

        assertEquals("MangaDialogue not found with id: " + id, exception.getMessage());
    }

    @Test
    void saveAllMangaDialogues_ShouldSaveAll_WhenValid() {
        MangaDialogue mangaDialogue1 = new MangaDialogue();
        MangaDialogue mangaDialogue2 = new MangaDialogue();
        List<MangaDialogue> mangaDialogueList = Arrays.asList(mangaDialogue1, mangaDialogue2);

        mangaDialogueService.saveAllMangaDialogues(mangaDialogueList);

        verify(iMangaTextRepository, times(1)).saveAll(mangaDialogueList);
    }

    @Test
    void saveAllMangaDialogues_ShouldThrowException_WhenListIsNullOrEmpty() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            mangaDialogueService.saveAllMangaDialogues(null);
        });
        assertEquals("MangaDialogue list cannot be null or empty.", exception.getMessage());

        exception = assertThrows(IllegalArgumentException.class, () -> {
            mangaDialogueService.saveAllMangaDialogues(Arrays.asList());
        });
        assertEquals("MangaDialogue list cannot be null or empty.", exception.getMessage());
    }

    @Test
    void saveMangaDialogue_ShouldSave_WhenValid() {
        Manga manga = new Manga();
        MangaDialogue mangaDialogue = new MangaDialogue();
        mangaDialogue.setDialogue("Valid dialogue");
        mangaDialogue.setTurn(1);
        mangaDialogue.setManga(manga);
        when(iMangaTextRepository.save(mangaDialogue)).thenReturn(mangaDialogue);

        mangaDialogueService.saveMangaDialogue(mangaDialogue);

        verify(iMangaTextRepository, times(1)).save(mangaDialogue);
    }

    @Test
    void saveMangaDialogue_ShouldThrowException_WhenIncomplete() {
        MangaDialogue incompleteMangaDialogue = new MangaDialogue();

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            mangaDialogueService.saveMangaDialogue(incompleteMangaDialogue);
        });

        assertEquals("MangaDialogue is not complete.", exception.getMessage());
    }

    @Test
    void deleteMangaDialogue_ShouldDelete_WhenExists() {
        Long id = 1L;

        when(iMangaTextRepository.existsById(id)).thenReturn(true);

        mangaDialogueService.deleteMangaDialogue(id);

        verify(iMangaTextRepository, times(1)).deleteById(id);
    }

    @Test
    void deleteMangaDialogue_ShouldThrowException_WhenNotExists() {
        Long id = 1L;

        when(iMangaTextRepository.existsById(id)).thenReturn(false);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            mangaDialogueService.deleteMangaDialogue(id);
        });

        assertEquals("MangaDialogue doesn't exist.", exception.getMessage());
    }

    @Test
    void updateMangaDialogue_ShouldUpdate_WhenExists() {
        Long id = 1L;
        Manga manga = new Manga();
        MangaDialogue existingMangaDialogue = new MangaDialogue();
        existingMangaDialogue.setId(id);
        existingMangaDialogue.setDialogue("Old dialogue");
        existingMangaDialogue.setTurn(1);
        existingMangaDialogue.setManga(manga);

        MangaDialogue updatedMangaDialogue = new MangaDialogue();
        updatedMangaDialogue.setDialogue("Updated dialogue");

        when(iMangaTextRepository.findById(id)).thenReturn(Optional.of(existingMangaDialogue));
        when(iMangaTextRepository.save(updatedMangaDialogue)).thenReturn(updatedMangaDialogue);
        mangaDialogueService.updateMangaDialogue(id, updatedMangaDialogue);

        assertEquals("Updated dialogue", existingMangaDialogue.getDialogue());
        verify(iMangaTextRepository, times(1)).save(existingMangaDialogue);
    }

    @Test
    void updateMangaDialogue_ShouldThrowException_WhenNotExists() {
        Long id = 1L;
        MangaDialogue updatedMangaDialogue = new MangaDialogue();

        when(iMangaTextRepository.findById(id)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            mangaDialogueService.updateMangaDialogue(id, updatedMangaDialogue);
        });

        assertEquals("MangaDialogue not found with id: " + id, exception.getMessage());
    }
}
