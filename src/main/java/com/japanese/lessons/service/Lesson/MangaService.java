package com.japanese.lessons.service.Lesson;

import com.japanese.lessons.dtos.*;
import com.japanese.lessons.dtos.request.AnswersDTO;
import com.japanese.lessons.dtos.response.QuizRewardsDTO;
import com.japanese.lessons.models.Rewards;
import com.japanese.lessons.models.ETargetType;
import com.japanese.lessons.models.lesson.mangaExercise.*;
import com.japanese.lessons.repositories.Lesson.IMangaRepository;
import com.japanese.lessons.service.ImagesService;
import com.japanese.lessons.service.RewardsService;
import com.japanese.lessons.service.UserIncorrectAnswersService;
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

    @Autowired
    UserIncorrectAnswersService userIncorrectAnswersService;

    @Autowired
    RewardsService rewardsService;

    @Autowired
    ImagesService imagesService;

    @Autowired
    MangaPhotoDescriptionService mangaPhotoDescriptionService;

    public Manga getMangaById(Long id) {
        Manga manga = iMangaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Manga not found with id: " + id));

        manga.validateCompletion();

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
        List<Image> responceImageList = imagesService.getImagesByMangaId(reponseManga.getId());
        List<MangaContentDTO> mangaContentDTOList = new ArrayList<>();

        MangaDetailsDTO mangaDetailsDTO = new MangaDetailsDTO(reponseManga.getName(),reponseManga.getStartDialog());

        mangaContentDTOList.add(new MangaContentDTO(0, "manga", mangaDetailsDTO));
        List<ImageMangaDTO> imageMangaDTOList = new ArrayList<>();
        for(Image image : responceImageList) {
            List<MangaPhotoDescription> mangaPhotoDescriptionList = mangaPhotoDescriptionService.getMangaPhotoDescriptionByImageId(image.getId());
            MangaPhotoDescription mangaPhotoDescription = mangaPhotoDescriptionList.get(0);
            imageMangaDTOList.add( new ImageMangaDTO(image, mangaPhotoDescription));
        }
        List<ImagesHasRightLeftDTO> imagesHasRightLeftDTOList = new ArrayList<>();
        List<ImageMangaDTO> imagesToReturn = new ArrayList<>();
        for (ImageMangaDTO imageManga : imageMangaDTOList) {
            if(imageManga.getImage().getPosition().equals("center")) {
                imagesToReturn.add(imageManga);
            }
            if(imageManga.getImage().getPosition().equals("right")) {
                imagesHasRightLeftDTOList.add(new ImagesHasRightLeftDTO(imageManga, null));
            }
            if(imageManga.getImage().getPosition().equals("left")) {
                if (imagesHasRightLeftDTOList.size() > 0 && imagesHasRightLeftDTOList.get(imagesHasRightLeftDTOList.size() - 1).getImageLeft() == null) {
                    imagesHasRightLeftDTOList.get(imagesHasRightLeftDTOList.size() - 1).setImageLeft(imageManga);
                } else {
                    new IllegalArgumentException("Has occurred an error when was selecting sides left and right");
                }
            }
        }

        imagesToReturn.forEach(mangaDTO -> mangaContentDTOList.add(
                new MangaContentDTO(mangaDTO.getImage().getQueue(), "image", mangaDTO)
        ));
        imagesHasRightLeftDTOList.forEach(imagesHasRightLeftDTO -> mangaContentDTOList.add(
                new MangaContentDTO(imagesHasRightLeftDTO.getImageRight().getImage().getQueue(), "images_together", imagesHasRightLeftDTO)
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

    public List<QuizRewardsDTO> concludeManga(AnswersDTO answersDTO) {
            List<QuizRewardsDTO> quizRewardsDTOList = new ArrayList<>();
            MangaResultDetails mangaResultDetails = new MangaResultDetails();
            userIncorrectAnswersService.saveAllIncorrectAnswers(answersDTO.getUserIncorrectAnswersList());
            int numIncorrectAnswers = answersDTO.getUserIncorrectAnswersList().size();
            int percentage = cuntPercentage(answersDTO.getNumCorrectAnswers(), numIncorrectAnswers);
            mangaResultDetails.setPercentageCorrect(percentage);
            int experience = countExperience(answersDTO.getNumCorrectAnswers(), percentage);
            mangaResultDetails.setExperience(experience);
            quizRewardsDTOList.add(new QuizRewardsDTO("Details", mangaResultDetails));

            List<Rewards> rewardsMangaList = rewardsService.getRewardsByTarget(ETargetType.MANGA, answersDTO.getMangaId());
            for (Rewards rewards : rewardsMangaList) {
            }
            return quizRewardsDTOList;
    }

    private int cuntPercentage(int correct,int incorrect) {
        int sum = correct + incorrect;
        int percentage = correct / sum * 100;
        return percentage;
    }
    private int countExperience(int numCorrectAnswer, int percentage) {
        return (numCorrectAnswer * 2) * percentage;
    }
}
