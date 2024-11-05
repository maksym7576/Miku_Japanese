package com.japanese.lessons.service.Lesson;

import com.japanese.lessons.dtos.AnswerMangaDTO;
import com.japanese.lessons.dtos.ImagesHasRightLeftDTO;
import com.japanese.lessons.dtos.MangaContentDTO;
import com.japanese.lessons.dtos.MangaDetailsDTO;
import com.japanese.lessons.models.lesson.mangaExercise.AnswerManga;
import com.japanese.lessons.models.lesson.mangaExercise.Images;
import com.japanese.lessons.models.lesson.mangaExercise.IncompleteMangaDataException;
import com.japanese.lessons.models.lesson.mangaExercise.Manga;
import com.japanese.lessons.repositories.Lesson.IMangaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MangaService {

    @Autowired
    IMangaRepository iMangaRepository;

    @Autowired
    LessonService lessonService;

    public Manga getMangaById(Long id) {
        Manga manga = iMangaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Manga not found with id: " + id));

        manga.validateCompletion();

        if (manga.getImages().isEmpty()) {
            throw new IncompleteMangaDataException("Images collection is empty.");
        }
        if (manga.getMangaDialogues().isEmpty()) {
            throw new IncompleteMangaDataException("MangaDialogues collection is empty.");
        }
        if (manga.getQuestionMangas().isEmpty()) {
            throw new IncompleteMangaDataException("QuestionMangas collection is empty.");
        }

        return manga;
    }
    public Manga getMangaByLessonID(Long lessonId) {
        Manga manga = iMangaRepository.findByLessonId(lessonId)
                .orElseThrow(() -> new IllegalArgumentException("Manga not found with id: " + lessonId));

        manga.validateCompletion();

        if (manga.getImages().isEmpty()) {
            throw new IncompleteMangaDataException("Images collection is empty.");
        }
        if (manga.getMangaDialogues().isEmpty()) {
            throw new IncompleteMangaDataException("MangaDialogues collection is empty.");
        }
        if (manga.getQuestionMangas().isEmpty()) {
            throw new IncompleteMangaDataException("QuestionMangas collection is empty.");
        }

        return manga;
    }

    public List<MangaContentDTO> sortMangaByQueue(Long lessonId) {
        Manga reponseManga = getMangaByLessonID(lessonId);
        List<MangaContentDTO> mangaContentDTOList = new ArrayList<>();

        MangaDetailsDTO mangaDetailsDTO = new MangaDetailsDTO(reponseManga.getName(),reponseManga.getStartDialog());

        mangaContentDTOList.add(new MangaContentDTO(0, "manga", mangaDetailsDTO));

        List<ImagesHasRightLeftDTO> imagesHasRightLeftDTOList = new ArrayList<>();
        List<Images> imagesList = new ArrayList<>();
        for (Images images : reponseManga.getImages()) {
            if(images.getPosition().equals("center")) {
                imagesList.add(images);
            }
            if(images.getPosition().equals("right")) {
                imagesHasRightLeftDTOList.add(new ImagesHasRightLeftDTO(images, null));
            }
            if(images.getPosition().equals("left")) {
                if (imagesHasRightLeftDTOList.size() > 0 && imagesHasRightLeftDTOList.get(imagesHasRightLeftDTOList.size() - 1).getImageLeft() == null) {
                    imagesHasRightLeftDTOList.get(imagesHasRightLeftDTOList.size() - 1).setImageLeft(images);
                } else {
                    new IllegalArgumentException("Has occurred an error when was selecting sides left and right");
                }
            }
        }

        imagesList.forEach(images -> mangaContentDTOList.add(
                new MangaContentDTO(images.getQueue(), "image", images)
        ));
        imagesHasRightLeftDTOList.forEach(imagesHasRightLeftDTO -> mangaContentDTOList.add(
                new MangaContentDTO(imagesHasRightLeftDTO.getImageRight().getQueue(), "images_together", imagesHasRightLeftDTO)
        ));
        reponseManga.getMangaDialogues().forEach(dialogue -> mangaContentDTOList.add(
                new MangaContentDTO(dialogue.getQueue(), "dialogue", dialogue)
        ));
        reponseManga.getQuestionMangas().forEach(question -> {
            MangaContentDTO questionContent = new MangaContentDTO(question.getQueue(), "question", question);
            mangaContentDTOList.add(questionContent);
            AnswerManga correctAnswer = new AnswerManga();
            correctAnswer.setAnswer_hiragana_katakana_kanji(question.getCorrect_answer_hiragana_katakana_kanji());
            correctAnswer.setAnswer_hiragana_katakana(question.getCorrect_answer_hiragana_or_katakana());
            correctAnswer.setAnswer_romanji(question.getCorrect_answer_romanized());
            correctAnswer.setQuestionManga(question);

            question.getAnswerMangas().add(correctAnswer);

            question.getAnswerMangas().forEach(answer -> {
                AnswerMangaDTO answerDTO = new AnswerMangaDTO(
                        answer.getId(),
                        answer.getAnswer_hiragana_katakana_kanji(),
                        answer.getAnswer_hiragana_katakana(),
                        answer.getAnswer_romanji()
                );

//                mangaContentDTOList.add(new MangaContentDTO(question.getQueue(), "answer", answerDTO));
            });
        });

        return mangaContentDTOList.stream()
                .sorted(Comparator.comparingInt(MangaContentDTO::getQueue))
                .collect(Collectors.toList());
    }



    public Manga saveManga(Manga manga) {
        if (manga == null) {
            throw new IllegalArgumentException("Manga cannot be null.");
        } else {
            manga.validateCompletion();
            lessonService.getLessonById(manga.getLesson().getId());
                return iMangaRepository.save(manga);
        }
    }

    @Transactional
    public void updateManga(Long id, Manga updatedManga) {
        Manga existingManga = getMangaById(id);
        existingManga.copyNonNullProperties(updatedManga);
        saveManga(existingManga);
    }

    public void deleteManga(Long id) {
        if (iMangaRepository.existsById(id)) {
            iMangaRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Manga doesn't exist.");
        }
    }
}
